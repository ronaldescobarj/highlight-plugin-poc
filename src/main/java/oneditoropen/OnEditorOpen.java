package oneditoropen;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import compare.CompareUtils;
import difflogic.DiffMapper;
import difflogic.DiffModifications;
import editor.EditorUtils;
import git.GitLocal;
import models.DiffRow;
import org.jetbrains.annotations.NotNull;
import services.EditorService;

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
        String fileName = new EditorUtils().getFileName(editor);
        DiffModifications diffModifications = new DiffModifications();
        Map<Integer, Integer> amountOfTimes = diffModifications.buildNumberOfModifications(fileName, gitLocal);
        Map<Integer, String> diffMap = getLatestDiffMap(editor, gitLocal);
        diffModifications.applyAmountOfTimesToDiffMap(diffMap, amountOfTimes);
        gitLocal.closeRepository();
        return diffMap;
    }

    private Map<Integer, String> getLatestDiffMap(Editor editor, GitLocal gitLocal) {
        String currentFileContent = new EditorUtils().getCurrentFileContent(editor);
        String previousFileContent = gitLocal.getPreviousCommitFileContent(editor);
        CompareUtils compareUtils = new CompareUtils();
        List<DiffRow> diffRows = compareUtils.getDiffChanges(previousFileContent, currentFileContent);
        return new DiffMapper(diffRows).createDiffMap();
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) { }
}
