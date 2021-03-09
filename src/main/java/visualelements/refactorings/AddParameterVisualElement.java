package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class AddParameterVisualElement extends VisualElement {
    public AddParameterVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" ADP ");
        this.setSize(85, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.ORANGE);
        this.setForeground(JBColor.WHITE);
    }
}