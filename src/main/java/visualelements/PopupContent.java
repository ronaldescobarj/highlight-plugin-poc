package visualelements;

import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public class PopupContent extends JLabel {
    public PopupContent() {
        this.setLayout(new FlowLayout());
        JLabel jl = new JLabel("Test");
        jl.setSize(40, 40);
        jl.setOpaque(true);
        jl.setBackground(JBColor.LIGHT_GRAY);
        jl.setForeground(JBColor.BLACK);
        int width = jl.getWidth();
        int height = jl.getHeight();
        this.add(jl);
        setSize(width, height);
    }
}
