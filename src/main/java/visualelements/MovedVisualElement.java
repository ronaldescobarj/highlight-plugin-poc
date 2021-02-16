package visualelements;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public class MovedVisualElement extends VisualElement {
    public MovedVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" MOV ");
        this.setSize(35, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.DARK_GRAY);
        this.setForeground(JBColor.WHITE);
    }
}
