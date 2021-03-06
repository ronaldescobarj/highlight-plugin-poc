package refactoringminer;

import actions.ActionsUtils;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.diff.*;
import models.Data;
import models.DataFactory;
import models.refactoringminer.Commit;
import models.refactoringminer.Location;
//import models.refactoringminer.Refactoring;
import models.refactoringminer.RefactoringMinerOutput;
import org.refactoringminer.api.Refactoring;

import java.util.List;
import java.util.Map;

public class RefactoringMinerUtils {
    /*public static void addRefactoringsToMap(RefactoringMinerOutput refactoringMinerOutput, Map<Integer, List<Data>> actionsMap, String filePath) {
        for (Refactoring refactoring : refactoringMinerOutput.getRefactorings()) {
            if (refactoring.getType().equals("Extract Method")) {
                for (Location location : refactoring.getRightSideLocations()) {
                    if (location.getFilePath().equals(filePath)) {
                        if (location.getCodeElementType().equals("METHOD_INVOCATION")) {
                            //addLabel("call to extracted method")
                            Data action = DataFactory.createData("EXTRACTED_METHOD_CALL", null, null);
                            ActionsUtils.addActionToLine(actionsMap, location.getStartLine(), action);
                        } else if (location.getCodeElementType().equals("METHOD_DECLARATION") && location.getDescription().equals("extracted method declaration")) {
                            //addLabel("extracted method")
                            Data action = DataFactory.createData("EXTRACTED_METHOD", null, null);
                            ActionsUtils.addActionToLine(actionsMap, location.getStartLine(), action);
                        }
                    }
                }
            }
        }
    }*/

    public static void addRefactoringsToMap(List<Refactoring> refactorings, Map<Integer, List<Data>> actionsMap, String filePath) {
        for (Refactoring refactoring: refactorings) {
            if (refactoring instanceof ExtractOperationRefactoring) {
                handleExtractOperation(actionsMap, filePath, (ExtractOperationRefactoring) refactoring);
            } else if (refactoring instanceof RenameVariableRefactoring) {
                handleRenameVariable(actionsMap, filePath, (RenameVariableRefactoring) refactoring);
            } else if (refactoring instanceof RenameOperationRefactoring) {
                handleRenameOperation(actionsMap, filePath, (RenameOperationRefactoring) refactoring);
            } else if (refactoring instanceof ChangeAttributeTypeRefactoring) {
                handleChangeAttributeType(actionsMap, filePath, (ChangeAttributeTypeRefactoring) refactoring);
            } else if (refactoring instanceof ChangeReturnTypeRefactoring) {
                handleChangeReturnType(actionsMap, filePath, (ChangeReturnTypeRefactoring) refactoring);
            } else if (refactoring instanceof ChangeVariableTypeRefactoring) {
                handleChangeVariableType(actionsMap, filePath, (ChangeVariableTypeRefactoring) refactoring);
            }
        }
    }

    private static void handleExtractOperation(Map<Integer, List<Data>> actionsMap, String filePath, ExtractOperationRefactoring extractOperationRefactoring) {
        if (extractOperationRefactoring.getExtractedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createData("EXTRACTED_METHOD", null, null);
            ActionsUtils.addActionToLine(actionsMap, extractOperationRefactoring.getExtractedOperation().getLocationInfo().getStartLine(), action);
        }
        for (OperationInvocation call: extractOperationRefactoring.getExtractedOperationInvocations()) {
            if (call.getLocationInfo().getFilePath().equals(filePath)) {
                Data action = DataFactory.createData("EXTRACTED_METHOD_CALL", null, null);
                ActionsUtils.addActionToLine(actionsMap, call.getLocationInfo().getStartLine(), action);
            }
        }
    }

    private static void handleRenameVariable(Map<Integer, List<Data>> actionsMap, String filePath, RenameVariableRefactoring renameVariableRefactoring) {
        if (renameVariableRefactoring.getRenamedVariable().getLocationInfo().getFilePath().equals(filePath)) {
            final String refactoringType = renameVariableRefactoring.getRenamedVariable().isParameter() ? "RENAME_PARAMETER" : "RENAME_VARIABLE";
            Data action = DataFactory.createRefactoringData(refactoringType, renameVariableRefactoring.getOriginalVariable().getVariableName());
            ActionsUtils.addActionToLine(actionsMap, renameVariableRefactoring.getRenamedVariable().getLocationInfo().getStartLine(), action);
        }
    }

    private static void handleRenameOperation(Map<Integer, List<Data>> actionsMap, String filePath, RenameOperationRefactoring renameOperationRefactoring) {
        if (renameOperationRefactoring.getRenamedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("RENAME_METHOD", renameOperationRefactoring.getOriginalOperation().getName());
            ActionsUtils.addActionToLine(actionsMap, renameOperationRefactoring.getRenamedOperation().getLocationInfo().getStartLine(), action);
        }
    }

    private static void handleChangeAttributeType(Map<Integer, List<Data>> actionsMap, String filePath, ChangeAttributeTypeRefactoring changeAttributeTypeRefactoring) {
        if (changeAttributeTypeRefactoring.getChangedTypeAttribute().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("CHANGE_ATTRIBUTE_TYPE", changeAttributeTypeRefactoring.getOriginalAttribute().getType().getClassType());
            ActionsUtils.addActionToLine(actionsMap, changeAttributeTypeRefactoring.getChangedTypeAttribute().getLocationInfo().getStartLine(), action);
        }
    }

    private static void handleChangeReturnType(Map<Integer, List<Data>> actionsMap, String filePath, ChangeReturnTypeRefactoring changeReturnTypeRefactoring) {
        if (changeReturnTypeRefactoring.getChangedType().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("CHANGE_RETURN_TYPE", changeReturnTypeRefactoring.getOriginalType().getClassType());
            ActionsUtils.addActionToLine(actionsMap, changeReturnTypeRefactoring.getChangedType().getLocationInfo().getStartLine(), action);
        }
    }

    private static void handleChangeVariableType(Map<Integer, List<Data>> actionsMap, String filePath, ChangeVariableTypeRefactoring changeVariableTypeRefactoring) {
        if (changeVariableTypeRefactoring.getChangedTypeVariable().getLocationInfo().getFilePath().equals(filePath)) {
            final String refactoringType = changeVariableTypeRefactoring.getChangedTypeVariable().isParameter() ? "CHANGE_PARAMETER_TYPE" : "CHANGE_VARIABLE_TYPE";
            Data action = DataFactory.createRefactoringData(refactoringType, changeVariableTypeRefactoring.getOriginalVariable().getType().getClassType());
            ActionsUtils.addActionToLine(actionsMap, changeVariableTypeRefactoring.getChangedTypeVariable().getLocationInfo().getStartLine(), action);
        }
    }
}
