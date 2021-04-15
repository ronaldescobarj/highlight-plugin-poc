package editorevents;

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorActivityManager;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.util.FileContentUtil;
import difflogic.DiffMapGenerator;
import difflogic.DiffModifications;
import editor.EditorUtils;
import git.GitLocal;
import models.Data;
import models.EditorData;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jetbrains.annotations.NotNull;
import services.EditorService;
import services.GitService;
import visualelements.VisualElementsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnEditorMouse implements EditorMouseListener {
    @Override
    public void mouseEntered(@NotNull EditorMouseEvent event) {
        Editor editor = event.getEditor();
        String editorPath = EditorUtils.getRelativePath(editor);
        Project project = editor.getProject();
        EditorService editorService = project.getService(EditorService.class);
        GitService gitService = project.getService(GitService.class);
        GitLocal gitLocal = new GitLocal(gitService.getRepository());
        String latestCommitHash = gitLocal.getLatestCommit().getName();
        if (!latestCommitHash.equals(gitService.getLatestCommitHash())) {
            RevCommit latestCommitWithModifications = new DiffModifications().getCommitWithLatestModification(editor);
            RevCommit previousCommit = latestCommitWithModifications.getParents()[0];
            Map<Integer, List<Data>> changes = new DiffMapGenerator().generateChangesMapForEditor(editor, previousCommit, latestCommitWithModifications);
            EditorData editorData = new EditorData(changes, true, previousCommit, latestCommitWithModifications);
            editorService.setEditorWithData(editor, editorData);
            gitService.setLatestCommitHash(latestCommitHash);
            EditorUtils.refreshEditor(editor);
        } else if (!editorPath.equals(editorService.getActiveEditorPath())) {
            editorService.setActiveEditor(editor);
            EditorUtils.refreshEditor(editor);
        }
        new VisualElementsUtils().addVisualElements(editor, editorService.getActiveEditorChanges());
    }

}
