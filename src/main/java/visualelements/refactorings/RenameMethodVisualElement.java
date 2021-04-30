package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class RenameMethodVisualElement extends VisualElement {
    public RenameMethodVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" RNM ");
        this.setSize(50, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.GREEN);
        this.setForeground(JBColor.WHITE);
    }
}
