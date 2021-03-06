package visualelements;

import com.intellij.psi.PsiElement;
import visualelements.actions.DeletedVisualElement;
import visualelements.actions.InsertedVisualElement;
import visualelements.actions.MovedVisualElement;
import visualelements.actions.UpdatedVisualElement;
import visualelements.refactorings.*;

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
            case "RENAME_PARAMETER":
                return new RenameParameterVisualElement(psiElement);
            case "RENAME_METHOD":
                return new RenameMethodVisualElement(psiElement);
            case "RENAME_VARIABLE":
                return new RenameVariableVisualElement(psiElement);
            case "CHANGE_ATTRIBUTE_TYPE":
                return new ChangeAttributeTypeVisualElement(psiElement);
            case "CHANGE_RETURN_TYPE":
                return new ChangeReturnTypeVisualElement(psiElement);
            case "CHANGE_PARAMETER_TYPE":
                return new ChangeParameterTypeVisualElement(psiElement);
            case "CHANGE_VARIABLE_TYPE":
                return new ChangeVariableTypeVisualElement(psiElement);
            case "REMOVE_PARAMETER":
                return new RemoveParameterVisualElement(psiElement);
            case "ADD_PARAMETER":
                return new AddParameterVisualElement(psiElement);
            case "REORDER_PARAMETER":
                return new ReorderParameterVisualElement(psiElement);
            case "GEAR":
                return new GearVisualElement(psiElement);
            default:
                return null;
        }
    }
}
