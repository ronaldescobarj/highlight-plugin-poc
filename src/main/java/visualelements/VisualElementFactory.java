package visualelements;

import com.intellij.psi.PsiElement;

import javax.swing.*;

public class VisualElementFactory {
    public static JLabel createVisualElement(String type, PsiElement psiElement) {
        switch (type) {
            case "UPD":
                return new UpdatedVisualElement(psiElement);
            case "INS":
                return new InsertedVisualElement(psiElement);
            case "MOV":
                return new MovedVisualElement(psiElement);
            case "GEAR":
                return new GearVisualElement(psiElement);
            default:
                return null;
        }
    }
}
