package editorevents;

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.project.Project;
import difflogic.DiffMapGenerator;
import git.GitLocal;
import models.Data;
import org.jetbrains.annotations.NotNull;
import services.EditorService;
import services.GitService;
import visualelements.VisualElementsUtils;

import java.util.List;
import java.util.Map;

public class OnEditorMouse implements EditorMouseListener {
    @Override
    public void mouseEntered(@NotNull EditorMouseEvent event) {
        Editor editor = event.getEditor();
        Project project = editor.getProject();
        EditorService editorService = project.getService(EditorService.class);
        Map<Integer, List<Data>> diffMap = null;
        GitService gitService = project.getService(GitService.class);
        GitLocal gitLocal = new GitLocal(gitService.getRepository());
        String latestCommitHash = gitLocal.getLatestCommit().getName();
        if (!latestCommitHash.equals(gitService.getLatestCommitHash())) {
            diffMap = new DiffMapGenerator().generateHighlightMapForEditor(editor);
            editorService.setLastOpenedEditor(editor);
            editorService.setDiffMap(diffMap);
            gitService.setLatestCommitHash(latestCommitHash);
            DaemonCodeAnalyzer.getInstance(project).restart();
        }
        if (editor != editorService.getLastOpenedEditor()) {
            diffMap = new DiffMapGenerator().generateHighlightMapForEditor(editor);
            editorService.setLastOpenedEditor(editor);
            editorService.setDiffMap(diffMap);
        } else {
            diffMap = editorService.getDiffMap();
        }
        if (diffMap != null) {
            new VisualElementsUtils().addVisualElements(editor, diffMap);
        }
    }

}
