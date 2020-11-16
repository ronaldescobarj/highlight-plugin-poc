package oneditoropen;

import at.aau.softwaredynamics.classifier.AbstractJavaChangeClassifier;
import at.aau.softwaredynamics.classifier.NonClassifyingClassifier;
import at.aau.softwaredynamics.classifier.entities.SourceCodeChange;
import at.aau.softwaredynamics.gen.NodeType;
import at.aau.softwaredynamics.gen.OptimizedJdtTreeGenerator;
import at.aau.softwaredynamics.matchers.JavaMatchers;
import at.aau.softwaredynamics.runner.util.ClassifierFactory;
import at.aau.softwaredynamics.runner.util.GitHelper;
import com.github.gumtreediff.gen.TreeGenerator;
import com.github.gumtreediff.matchers.Matcher;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.diff.Diff;
import difflogic.DiffMapper;
import gitremote.GitRemote;
import models.DiffRow;
import models.GitCommit;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.filter.PathSuffixFilter;
import org.eclipse.jgit.util.io.NullOutputStream;
import org.jetbrains.annotations.NotNull;
import services.EditorService;
import services.GitService;

import java.io.IOException;
import java.util.*;

public class OnEditorOpen implements EditorFactoryListener {

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        String currentFileContent = getCurrentFileContent(editor);
        String previousCommitFileContent = getPreviousCommitFileContent(editor);
        List<SourceCodeChange> changes = getChangesBetweenVersions(previousCommitFileContent, currentFileContent);
        ArrayList<DiffRow> diffs = getDiff(changes);
        EditorService editorService = editor.getProject().getService(EditorService.class);
        editorService.setDiffsOfLastOpenedEditor(diffs);
//        repoTest(editor.getProject().getBasePath());
    }

    public void repoTest(Editor editor) {
        try {
            Repository repository = GitHelper.openRepository(editor.getProject().getBasePath());
            Collection<RevCommit> commits = GitHelper.getCommits(repository, "HEAD");
            List<RevCommit> commitsList = new ArrayList<>(commits);
            List<RevCommit> slicedCommits = commitsList.subList(0, 5);
            Collections.reverse(slicedCommits);
            String fileName = getFileName(editor);
            ArrayList<DiffRow> diffRows = null;
            Map<Integer, Integer> amountOfTimes = new HashMap<>();
            for (RevCommit commit : slicedCommits) {
                DiffFormatter diffFormatter = createDiffFormatter(repository, fileName);
                RevCommit[] parents = commit.getParents();
                try {
                    if (parents.length != 0) {
                        List<DiffEntry> diffs = diffFormatter.scan(parents[0], commit.getTree());
                        DiffEntry diff = diffs.get(0);
                        String previousCommitFileContent = GitHelper.getFileContent(diff.getOldId(), repository);
                        String currentCommitFileContent = GitHelper.getFileContent(diff.getNewId(), repository);
                        List<SourceCodeChange> changes = getChangesBetweenVersions(previousCommitFileContent, currentCommitFileContent);
                        diffRows = getDiff(changes);
                        DiffMapper diffMapper = new DiffMapper(diffRows);
                        Map<Integer, String> diffMap = diffMapper.createDiffMap();
                        for (Map.Entry<Integer, String> diffsEntry : diffMap.entrySet()) {
                            if (diffsEntry.getValue().equals("INS")) {
                                amountOfTimes.put(diffsEntry.getKey(), 1);
                            } else if (diffsEntry.getValue().equals("UPD")) {
                                int times = amountOfTimes.get(diffsEntry.getKey());
                                times++;
                                amountOfTimes.put(diffsEntry.getKey(), times);
                                if (times >= 5) {
                                    diffMap.put(diffsEntry.getKey(), "UPD_MULTIPLE_TIMES");
                                }
                            }
                        }

                    }
                } catch(IOException e) {
                    System.out.println("exception");
                }
            }
            EditorService editorService = editor.getProject().getService(EditorService.class);
            editorService.setDiffsOfLastOpenedEditor(diffRows);
        } catch (IOException e) {
            System.out.println("exception");
        }
    }

    private ArrayList<DiffRow> getDiff(List<SourceCodeChange> changes) {
        ArrayList<DiffRow> diffs = new ArrayList<>();
        for (SourceCodeChange change : changes) {
            diffs.add(createDiffRow(change));
        }
        return diffs;
    }

    private List<SourceCodeChange> getChangesBetweenVersions(String previousVersion, String latestVersion) {
        AbstractJavaChangeClassifier classifier = createClassifier();
        try {
            classifier.classify(previousVersion, latestVersion);
            return classifier.getCodeChanges();
        } catch (Exception e) {
            return null;
        }
    }

    private String getCurrentFileContent(Editor editor) {
        Document document = editor.getDocument();
        return document.getText();
    }

//    private String getPreviousCommitFileContent(Editor editor) {
//        String relativePath = getRelativePath(editor);
//        String githubApiUrl = getGithubApiUrl(editor);
//        GitRemote gitRemote = new GitRemote();
//        GitCommit[] commits = gitRemote.getCommits(githubApiUrl);
//        String previousCommitSha = commits[1].getSha();
//        return gitRemote.getPreviousCommitFileContent(githubApiUrl, previousCommitSha, relativePath);
//    }

    private String getPreviousCommitFileContent(Editor editor) {
        String fileName = getFileName(editor);
        try {
            Repository repository = GitHelper.openRepository(editor.getProject().getBasePath());
            Collection<RevCommit> commits = GitHelper.getCommits(repository, "HEAD");
            RevCommit commit = commits.iterator().next();
            DiffFormatter diffFormatter = createDiffFormatter(repository, fileName);
            return getPreviousCommitContent(diffFormatter, commit, repository);
        } catch(Exception e) {
            System.out.println("fallo");
            return "";
        }
    }

    private String getPreviousCommitContent(DiffFormatter diffFormatter, RevCommit commit, Repository repository) {
        RevCommit[] parents = commit.getParents();
        try {
            if (parents.length != 0) {
                List<DiffEntry> diffs = diffFormatter.scan(parents[0], commit.getTree());
                DiffEntry diff = diffs.get(0);
                return GitHelper.getFileContent(diff.getOldId(), repository);
            }
            return "";
        } catch(IOException e) {
            System.out.println("fallo2");
            return "";
        }
    }

    private DiffFormatter createDiffFormatter(Repository repository, String fileName) {
        DiffFormatter diffFormatter = new DiffFormatter(NullOutputStream.INSTANCE);
        diffFormatter.setRepository(repository);
        diffFormatter.setPathFilter(PathSuffixFilter.create(fileName));
        diffFormatter.setDetectRenames(true);
        return diffFormatter;
    }

    private String getRelativePath(Editor editor) {
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(editor.getDocument());
        String filePath = virtualFile.getPath();
        Project project = editor.getProject();
        String projectPath = project.getBasePath();
        return filePath.replaceAll(projectPath, "");
    }

    private String getFileName(Editor editor) {
        Document document = editor.getDocument();
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);
        return virtualFile.getName();
    }

    private String getGithubApiUrl(Editor editor) {
        GitService gitService = editor.getProject().getService(GitService.class);
        String repoOwner = gitService.getRepoOwner();
        String repoName = gitService.getRepoName();
        return "https://api.github.com/repos/" + repoOwner + "/" + repoName;
    }

    private AbstractJavaChangeClassifier createClassifier() {
        Class<? extends AbstractJavaChangeClassifier> classifierType = NonClassifyingClassifier.class;
        Class<? extends Matcher> matcher = JavaMatchers.IterativeJavaMatcher_V2.class;
        TreeGenerator generator = new OptimizedJdtTreeGenerator();
        ClassifierFactory classifierFactory = new ClassifierFactory(classifierType, matcher, generator);
        AbstractJavaChangeClassifier classifier = classifierFactory.createClassifier();
        return classifier;
    }

    private DiffRow createDiffRow(SourceCodeChange change) {
        return new DiffRow("NO_COMMIT",
                change.getNode().getLabel(),
                change.getAction().getName(),
                String.valueOf(NodeType.getEnum(change.getNodeType())),
                change.getSrcInfo().getStartLineNumber(),
                change.getSrcInfo().getEndLineNumber(),
                change.getDstInfo().getStartLineNumber(),
                change.getDstInfo().getEndLineNumber(),
                "dst");
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) {
        System.out.println("sale");
    }
}
