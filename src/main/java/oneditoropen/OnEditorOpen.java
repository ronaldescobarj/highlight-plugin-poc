package oneditoropen;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import compare.CompareUtils;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerItem;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerManager;
import difflogic.DiffMapper;
import difflogic.DiffModifications;
import editor.EditorUtils;
import git.GitLocal;
import models.DiffRow;
import org.jetbrains.annotations.NotNull;
import services.EditorService;
import visualelements.VisualElementFactory;
import visualelements.VisualElementWrapper;

import javax.print.Doc;
import javax.swing.*;
import java.util.*;

public class OnEditorOpen implements EditorFactoryListener {

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        registerEditorToCoverLayerManager(editor);
        Map<Integer, String> diffMap = generateHighlightMapForEditor(editor);
        EditorService editorService = editor.getProject().getService(EditorService.class);
        editorService.setDiffMap(diffMap);
        editorService.setLastOpenedEditor(editor);
        editorService.setIsGenerated(true);
    }

    private void registerEditorToCoverLayerManager(Editor editor) {
        Project project = editor.getProject();
        ApplicationManager.getApplication().runReadAction(() ->
                EditorCoverLayerManager.getInstance(project).registerEditorCoverLayer(editor));
    }

    private Map<Integer, String> generateHighlightMapForEditor(Editor editor) {
        String projectPath = editor.getProject().getBasePath();
        GitLocal gitLocal = new GitLocal(projectPath);
        gitLocal.openRepository();
        String fileName = new EditorUtils().getFileName(editor);
        DiffModifications diffModifications = new DiffModifications();
        Map<Integer, Integer> amountOfTimes = diffModifications.buildNumberOfModifications(fileName, gitLocal);
        Map<Integer, String> diffMap = getLatestDiffMap(editor, gitLocal);
        diffModifications.applyAmountOfTimesToDiffMap(diffMap, amountOfTimes);
//        addVisualElements(editor, diffMap);
        gitLocal.closeRepository();
        return diffMap;
    }

    private void addVisualElements(Editor editor, Map<Integer, String> diffMap) {
        for (Map.Entry<Integer, String> diff : diffMap.entrySet()) {
            if (diff.getValue().equals("UPD")) {
                addVisualElement(editor, diff.getKey());
            }
        }
    }

    private void addVisualElement(Editor editor, int line) {
        Document document = editor.getDocument();
        Project project = editor.getProject();
        int offset = document.getLineStartOffset(line - 1);
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        PsiElement psiElement = psiFile.findElementAt(offset);
        VisualElementFactory factory = new VisualElementFactory();
        JLabel myElement = new VisualElementWrapper(psiElement, factory);
        EditorCoverLayerItem layerItem = new EditorCoverLayerItem(psiElement, myElement);
        EditorCoverLayerManager.getInstance(project).add(layerItem);
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
