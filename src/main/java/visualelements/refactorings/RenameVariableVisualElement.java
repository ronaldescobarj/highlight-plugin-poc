package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class RenameVariableVisualElement extends VisualElement {
    public RenameVariableVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText("<html><b> RNV </b></html>");
        this.setSize(55, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.YELLOW);
        this.setForeground(JBColor.WHITE);
    }
}
