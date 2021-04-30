package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class ChangeParameterTypeVisualElement extends VisualElement {
    public ChangeParameterTypeVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" CPT ");
        this.setSize(70, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.GREEN);
        this.setForeground(JBColor.WHITE);
    }
}
