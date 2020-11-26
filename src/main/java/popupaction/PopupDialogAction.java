package popupaction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerItem;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerManager;
import org.jetbrains.annotations.NotNull;
import simplelanguage.psi.SimpleDiffline;
import visualelements.VisualElementFactory;

import javax.swing.*;

public class PopupDialogAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        // Using the event, evaluate the context, and enable or disable the action.
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project currentProject = event.getProject();
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        int offset1 = document.getLineStartOffset(6);
        int offset2 = document.getLineStartOffset(1);
        int offset3 = document.getLineStartOffset(2);
        int offset4 = document.getLineStartOffset(3);
        int offset5 = document.getLineStartOffset(4);
        int offset = document.getLineEndOffset(6);
        ApplicationManager.getApplication().runReadAction(() ->
                EditorCoverLayerManager.getInstance(currentProject).registerEditorCoverLayer(editor));
        PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
        PsiElement psiElement = psiFile.findElementAt(offset - 1);
        PsiElement psiElement1 = PsiTreeUtil.getParentOfType(psiElement);
        PsiElement psiElement2 = PsiTreeUtil.getParentOfType(psiElement, SimpleDiffline.class);
//        PsiElement psiElement = event.getData(LangDataKeys.PSI_ELEMENT);
        JLabel myElement = VisualElementFactory.createVisualElement("GEAR", psiElement);
        EditorCoverLayerItem layerItem = new EditorCoverLayerItem(psiElement, myElement);
        Boolean result = EditorCoverLayerManager.getInstance(currentProject).add(layerItem);
//        EditorCoverLayerManager.getInstance(currentProject).setEditorCoverLayersVisible(true);
        StringBuilder dlgMsg = new StringBuilder(event.getPresentation().getText() + " Selected!");
        String dlgTitle = event.getPresentation().getDescription();
        Navigatable nav = event.getData(CommonDataKeys.NAVIGATABLE);
        if (nav != null) {
            dlgMsg.append(String.format("\nSelected Element: %s", nav.toString()));
        }
        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon());
    }

}
