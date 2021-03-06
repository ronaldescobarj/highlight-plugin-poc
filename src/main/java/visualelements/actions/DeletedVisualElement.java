package visualelements.actions;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import visualelements.VisualElement;

import javax.swing.*;
import java.awt.*;

public class DeletedVisualElement extends VisualElement {
    public DeletedVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" DEL ");
        this.setSize(30, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.RED);
        this.setForeground(JBColor.WHITE);
    }
}
