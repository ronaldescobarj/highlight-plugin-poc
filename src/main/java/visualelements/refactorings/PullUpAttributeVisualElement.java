package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class PullUpAttributeVisualElement extends VisualElement {
    public PullUpAttributeVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" PUA ");
        this.setSize(110, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.GREEN);
        this.setForeground(JBColor.BLACK);
    }
}
