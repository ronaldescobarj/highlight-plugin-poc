package visualelements;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public class InsertedVisualElement extends VisualElement {
    public InsertedVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        this.setText(" INS ");
        this.setSize(30, 20);
        this.setOpaque(true);
        this.setBackground(JBColor.BLUE);
        this.setForeground(JBColor.WHITE);
    }
}
