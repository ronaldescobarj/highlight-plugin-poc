package visualelements;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public class MovedVisualElement extends VisualElement {
    public MovedVisualElement(PsiElement psiElement) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        JLabel jl = new JLabel(" MOV ");
        jl.setSize(30, 20);
        jl.setOpaque(true);
        jl.setBackground(JBColor.DARK_GRAY);
        jl.setForeground(JBColor.WHITE);
        int width = jl.getWidth();
        int height = jl.getHeight();
        this.add(jl);
        setSize(width, height);
    }
}
