package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class ExtractSuperclassVisualElement extends VisualElement {
    public ExtractSuperclassVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" ESC ");
        this.setSize(105, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.PINK);
        this.setForeground(JBColor.BLACK);
    }
}
