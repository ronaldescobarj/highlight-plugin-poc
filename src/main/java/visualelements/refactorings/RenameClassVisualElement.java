package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class RenameClassVisualElement extends VisualElement {
    public RenameClassVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" RNC ");
        this.setSize(95, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.CYAN);
        this.setForeground(JBColor.BLACK);
    }
}
