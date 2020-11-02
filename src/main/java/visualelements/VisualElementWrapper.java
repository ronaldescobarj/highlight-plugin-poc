package visualelements;

import com.intellij.psi.PsiElement;

import javax.swing.*;
import java.awt.*;

public class VisualElementWrapper extends VisualElement {
    public VisualElementWrapper(PsiElement psiElement, VisualElementFactory... factories)
    {
        super(psiElement);
        //this.setLayout(new BottomFlowLayout());
        this.setLayout(new FlowLayout());
        int width = 0;
        int height = 0;

        for (VisualElementFactory factory : factories)
        {
            JLabel artifactComponent = factory.createArtifactLabel();
            int iconWidth = artifactComponent.getIcon().getIconWidth();
            width += iconWidth;
            int iconHeight = artifactComponent.getIcon().getIconHeight();
            height = Math.max(height, iconHeight);
            this.add(artifactComponent);
        }

        setSize(width, height);
    }
}
