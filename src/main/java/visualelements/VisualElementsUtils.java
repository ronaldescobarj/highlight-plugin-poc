package visualelements;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerItem;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerManager;
import models.Data;
import models.UpdatedMultipleTimes;
import org.jetbrains.annotations.NotNull;
import visualelements.events.VisualElementMouseEventsHandler;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;

public class VisualElementsUtils {

    public void registerEditorToCoverLayerManager(Editor editor) {
        Project project = editor.getProject();
        ApplicationManager.getApplication().runReadAction(() ->
                EditorCoverLayerManager.getInstance(project).registerEditorCoverLayer(editor));
    }

    public void addVisualElements(Editor editor, Map<Integer, List<Data>> diffMap) {
        for (Map.Entry<Integer, List<Data>> diff : diffMap.entrySet()) {
            for (Data data: diff.getValue()) {
                if (!(data instanceof UpdatedMultipleTimes)) {
                    addVisualElement(editor, diff.getKey(), data);
                }
            }
        }
    }

    private void addVisualElement(Editor editor, int line, Data actionData) {
        Document document = editor.getDocument();
        Project project = editor.getProject();
        int offset = document.getLineStartOffset(line - 1);
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        PsiElement psiElement = psiFile.findElementAt(offset);
        JLabel myElement = VisualElementFactory.createVisualElement(actionData.getType(), psiElement);
        JBPopup popup = createPopup(actionData);
        VisualElementMouseEventsHandler handler = new VisualElementMouseEventsHandler(popup, editor);
        myElement.addMouseListener(handler);
        EditorCoverLayerItem layerItem = new EditorCoverLayerItem(psiElement, myElement);
        EditorCoverLayerManager.getInstance(project).add(layerItem);
    }

    @NotNull
    private JBPopup createPopup(Data modificationData) {
        JBPopupFactory jbPopupFactory = JBPopupFactory.getInstance();
        String content = PopupUtils.createContent(modificationData);
        JComponent visualElement = new PopupContent(content);
        ComponentPopupBuilder popupBuilder = jbPopupFactory.createComponentPopupBuilder(visualElement ,null);
        JBPopup popup = popupBuilder.createPopup();
        popup.setSize(new Dimension(200, 50));
        return popup;
    }
}
