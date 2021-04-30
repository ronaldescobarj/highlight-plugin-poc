package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class ChangeVariableTypeVisualElement extends VisualElement {
    public ChangeVariableTypeVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" CVT ");
        this.setSize(75, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.GREEN);
        this.setForeground(JBColor.WHITE);
    }
}
