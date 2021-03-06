package visualelements;

import com.intellij.psi.PsiElement;
import visualelements.actions.DeletedVisualElement;
import visualelements.actions.InsertedVisualElement;
import visualelements.actions.MovedVisualElement;
import visualelements.actions.UpdatedVisualElement;
import visualelements.refactorings.ExtractedMethodVisualElement;

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
            case "DEL":
                return new DeletedVisualElement(psiElement);
            case "EXTRACTED_METHOD":
                return new ExtractedMethodVisualElement(psiElement);
            case "GEAR":
                return new GearVisualElement(psiElement);
            default:
                return null;
        }
    }
}
