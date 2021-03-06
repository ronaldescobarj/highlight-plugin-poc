package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class RenameParameterVisualElement extends VisualElement {
    public RenameParameterVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" RNP ");
        this.setSize(45, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.CYAN);
        this.setForeground(JBColor.BLACK);
    }
}
