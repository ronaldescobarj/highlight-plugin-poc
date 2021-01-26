package visualelements;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public class ExtractedMethodVisualElement extends VisualElement {
    public ExtractedMethodVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" EXT ");
        this.setSize(35, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.BLACK);
        this.setForeground(JBColor.WHITE);
    }
}
