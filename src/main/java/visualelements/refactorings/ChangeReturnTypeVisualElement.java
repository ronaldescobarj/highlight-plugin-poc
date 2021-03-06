package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class ChangeReturnTypeVisualElement extends VisualElement {
    public ChangeReturnTypeVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" CRT ");
        this.setSize(65, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.MAGENTA);
        this.setForeground(JBColor.BLACK);
    }
}
