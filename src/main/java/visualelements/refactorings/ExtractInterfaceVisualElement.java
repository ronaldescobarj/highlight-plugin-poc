package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import java.awt.*;

public class ExtractInterfaceVisualElement extends VisualElement {
    public ExtractInterfaceVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" EI ");
        this.setSize(100, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.PINK);
        this.setForeground(JBColor.BLACK);
    }
}
