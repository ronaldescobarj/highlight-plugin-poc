package visualelements;

import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VisualElementWrapper extends VisualElement {
    public VisualElementWrapper(PsiElement psiElement, List<String> visualElementTypes) {
        super(psiElement);
        this.setLayout(new FlowLayout());
        int width = 0;
        int height = 0;
        for (String visualElementType: visualElementTypes) {
            JLabel visualElement = VisualElementFactory.createVisualElement(visualElementType, psiElement);
            int visualElementWidth = visualElement.getWidth();
            width += visualElementWidth;
            int visualElementHeight = visualElement.getHeight();
            height = Math.max(height, visualElementHeight);
            this.add(visualElement);
        }
//        jl.setOpaque(true);
//        jl.setBackground(JBColor.BLUE);
//        jl.setForeground(JBColor.WHITE);
        setSize(width, height);
    }
}
