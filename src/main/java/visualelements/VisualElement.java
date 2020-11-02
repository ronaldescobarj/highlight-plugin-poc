package visualelements;

import com.intellij.psi.PsiElement;

import javax.swing.*;

public class VisualElement extends JLabel {
    protected PsiElement psiElement;

    public PsiElement getPsiElement()
    {
        return this.psiElement;
    }

    protected VisualElement(PsiElement psiElement)
    {
        this.psiElement = psiElement;
    }
}
