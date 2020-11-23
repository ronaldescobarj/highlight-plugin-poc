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
//            JLabel artifactComponent = factory.createArtifactLabel();
            JLabel artifactComponent = factory.createLabel();
//            int iconWidth = artifactComponent.getIcon().getIconWidth();
//            width += iconWidth;
            width = artifactComponent.getWidth();
//            int iconHeight = artifactComponent.getIcon().getIconHeight();
//            height = Math.max(height, iconHeight);
            height = artifactComponent.getHeight();
            this.add(artifactComponent);
        }

        setSize(width, height);
    }
}
