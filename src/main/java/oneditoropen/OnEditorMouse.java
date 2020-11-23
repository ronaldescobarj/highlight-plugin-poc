package oneditoropen;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerItem;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerManager;
import org.jetbrains.annotations.NotNull;
import services.EditorService;
import visualelements.VisualElementFactory;
import visualelements.VisualElementWrapper;

import javax.swing.*;
import java.util.Map;

public class OnEditorMouse implements EditorMouseListener {
    @Override
    public void mouseEntered(@NotNull EditorMouseEvent event) {
        Editor editor = event.getEditor();
        Project project = editor.getProject();
        EditorService editorService = project.getService(EditorService.class);
        boolean isGenerated = editorService.getIsGenerated();
        Map<Integer, String> diffMap = editorService.getDiffMap();
        if (isGenerated) {
            addVisualElements(editor, diffMap);
        }
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

}
