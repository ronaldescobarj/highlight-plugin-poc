package oneditoropen;

import at.aau.softwaredynamics.classifier.AbstractJavaChangeClassifier;
import at.aau.softwaredynamics.classifier.NonClassifyingClassifier;
import at.aau.softwaredynamics.classifier.entities.SourceCodeChange;
import at.aau.softwaredynamics.gen.NodeType;
import at.aau.softwaredynamics.gen.OptimizedJdtTreeGenerator;
import at.aau.softwaredynamics.matchers.JavaMatchers;
import at.aau.softwaredynamics.runner.util.ClassifierFactory;
import com.github.gumtreediff.gen.TreeGenerator;
import com.github.gumtreediff.matchers.Matcher;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import difflogic.DiffMapper;
import git.GitLocal;
import git.GitRemote;
import models.DiffRow;
import models.GitCommit;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.filter.PathSuffixFilter;
import org.eclipse.jgit.util.io.NullOutputStream;
import org.jetbrains.annotations.NotNull;
import services.EditorService;
import services.GitService;

import java.util.*;

public class OnEditorOpen implements EditorFactoryListener {

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        Map<Integer, String> diffMap = generateHighlightMapForEditor(editor);
        EditorService editorService = editor.getProject().getService(EditorService.class);
        editorService.setDiffMap(diffMap);
    }

    public Map<Integer, String> generateHighlightMapForEditor(Editor editor) {
        String projectPath = editor.getProject().getBasePath();
        GitLocal gitLocal = new GitLocal(projectPath);
        gitLocal.openRepository();
        List<RevCommit> commits = gitLocal.getSelectedLatestCommits(5);
        String fileName = getFileName(editor);
        Map<Integer, Integer> amountOfTimes = new HashMap<>();
        Map<Integer, String> diffMap = null;
        for (RevCommit commit : commits) {
            List<DiffRow> diffRows = generateDiffWithPreviousCommit(commit, fileName, gitLocal);
            diffMap = new DiffMapper(diffRows).createDiffMap();
            for (Map.Entry<Integer, String> diffsEntry : diffMap.entrySet()) {
                handleDiffEntry(amountOfTimes, diffMap, diffsEntry);
            }
        }
        gitLocal.closeRepository();
        return diffMap;
    }

    private List<DiffRow> generateDiffWithPreviousCommit(RevCommit commit, String fileName, GitLocal gitLocal) {
        DiffEntry diff = gitLocal.getFileDiffWithPreviousCommit(commit, fileName);
        String previousCommitFileContent = gitLocal.getPreviousCommitFileContent(diff);
        String currentCommitFileContent = gitLocal.getCurrentCommitFileContent(diff);
        List<SourceCodeChange> changes = getChangesBetweenVersions(previousCommitFileContent, currentCommitFileContent);
        List<DiffRow> diffRows = getDiff(changes);
        return diffRows;
    }

    private void handleDiffEntry(Map<Integer, Integer> amountOfTimes, Map<Integer, String> diffMap, Map.Entry<Integer, String> diffsEntry) {
        if (diffsEntry.getValue().equals("INS")) {
            amountOfTimes.put(diffsEntry.getKey(), 1);
        } else if (diffsEntry.getValue().equals("UPD")) {
            handleUpdateEntry(amountOfTimes, diffMap, diffsEntry);
        }
    }

    private void handleUpdateEntry(Map<Integer, Integer> amountOfTimes, Map<Integer, String> diffMap, Map.Entry<Integer, String> diffsEntry) {
        int times = amountOfTimes.get(diffsEntry.getKey()) != null ? amountOfTimes.get(diffsEntry.getKey()) : 0;
        times++;
        amountOfTimes.put(diffsEntry.getKey(), times);
        if (times >= 5) {
            diffMap.put(diffsEntry.getKey(), "UPD_MULTIPLE_TIMES");
        }
    }

    private List<DiffRow> getDiff(List<SourceCodeChange> changes) {
        List<DiffRow> diffs = new ArrayList<>();
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

    private String getPreviousCommitFileContent(Editor editor) {
        String relativePath = getRelativePath(editor);
        String githubApiUrl = getGithubApiUrl(editor);
        GitRemote gitRemote = new GitRemote();
        GitCommit[] commits = gitRemote.getCommits(githubApiUrl);
        String previousCommitSha = commits[1].getSha();
        return gitRemote.getPreviousCommitFileContent(githubApiUrl, previousCommitSha, relativePath);
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
