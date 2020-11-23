package visualelements;

import com.intellij.ui.Colors;
import com.intellij.ui.JBColor;
import com.intellij.ui.paint.PaintUtil;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VisualElementFactory {

    public VisualElementFactory() { }

    public JLabel createArtifactLabel()
    {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/mygear.png"));
        GraphicsConfiguration defaultConfiguration =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage image = UIUtil.createImage(defaultConfiguration, imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB, PaintUtil.RoundingMode.CEIL);
        Graphics graphics = image.getGraphics();
        graphics.fillRect(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        graphics.drawImage(imageIcon.getImage(), 0, 0, null);
        ImageIcon imageIcon1 = new ImageIcon(image);
        return new JLabel(imageIcon1);
    }

    public JLabel createLabel()
    {
        JLabel jl = new JLabel(" UPD ");
        jl.setSize(30, 20);
        jl.setOpaque(true);
        jl.setBackground(JBColor.RED);
        jl.setForeground(JBColor.WHITE);
        return jl;
    }
}
