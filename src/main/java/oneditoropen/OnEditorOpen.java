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
import gitremote.GitRemote;
import models.DiffRow;
import models.GitCommit;
import org.jetbrains.annotations.NotNull;
import services.EditorService;
import services.GitService;

import java.util.ArrayList;
import java.util.List;

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

    private String getPreviousCommitFileContent(Editor editor) {
        String relativePath = getRelativePath(editor);
        String githubApiUrl = getGithubApiUrl(editor);
        GitRemote gitRemote = new GitRemote();
        GitCommit[] commits = gitRemote.getCommits(githubApiUrl);
        String previousCommitSha = commits[1].getSha();
        return gitRemote.getPreviousCommitFileContent(githubApiUrl, previousCommitSha, relativePath);
    }

    private String getRelativePath(Editor editor) {
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(editor.getDocument());
        String filePath = virtualFile.getPath();
        Project project = editor.getProject();
        String projectPath = project.getBasePath();
        return filePath.replaceAll(projectPath, "");
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
