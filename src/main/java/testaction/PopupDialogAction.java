package testaction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerItem;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerManager;
import org.jetbrains.annotations.NotNull;
import visualelements.VisualElementFactory;
import visualelements.VisualElementWrapper;

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
        ApplicationManager.getApplication().runReadAction(() ->
                EditorCoverLayerManager.getInstance(currentProject).registerEditorCoverLayer(editor));
        PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
        PsiElement psiElement = psiFile.findElementAt(80);
        //PsiElement psiElement = event.getData(LangDataKeys.PSI_ELEMENT);
        VisualElementFactory factory = new VisualElementFactory();
        JLabel myElement = new VisualElementWrapper(psiElement, factory);
        EditorCoverLayerItem layerItem = new EditorCoverLayerItem(psiElement, myElement);
        Boolean result = EditorCoverLayerManager.getInstance(currentProject).add(layerItem);
        EditorCoverLayerManager.getInstance(currentProject).setEditorCoverLayersVisible(true);
        StringBuilder dlgMsg = new StringBuilder(event.getPresentation().getText() + " Selected!");
        String dlgTitle = event.getPresentation().getDescription();
        Navigatable nav = event.getData(CommonDataKeys.NAVIGATABLE);
        if (nav != null) {
            dlgMsg.append(String.format("\nSelected Element: %s", nav.toString()));
        }
        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon());
    }

}
