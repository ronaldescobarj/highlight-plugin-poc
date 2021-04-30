package visualelements.refactorings;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import javax.swing.*;
import java.awt.*;

public class ExtractedMethodVisualElement extends VisualElement {
    public ExtractedMethodVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" EXT ");
        this.setSize(40, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.CYAN);
        this.setForeground(JBColor.WHITE);
    }
}
