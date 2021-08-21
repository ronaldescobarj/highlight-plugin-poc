package visualelements.actions;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class EndInsertVisualElement extends VisualElement {
    public EndInsertVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText("<html><b> END INS </b></html>");
        this.setSize(70, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.BLUE);
        this.setForeground(JBColor.WHITE);
    }
}
