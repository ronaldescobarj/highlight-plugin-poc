package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class ExtractSuperclassVisualElement extends VisualElement {
    public ExtractSuperclassVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText("<html><b> ESC </b></html>");
        this.setSize(105, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.getHSBColor((float)0.16, (float)0.8, (float)0.83));
        this.setForeground(JBColor.BLACK);
    }
}
