package refactoringminer;

import actions.ActionsUtils;
import gr.uom.java.xmi.LocationInfo;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.diff.*;
import models.Data;
import models.DataFactory;
import models.refactoringminer.Commit;
import models.refactoringminer.Location;
//import models.refactoringminer.Refactoring;
import models.refactoringminer.RefactoringMinerOutput;
import models.refactorings.PullUpAttribute;
import models.refactorings.PullUpMethod;
import org.refactoringminer.api.Refactoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        for (Refactoring refactoring : refactorings) {
            if (refactoring instanceof ExtractOperationRefactoring) {
                handleExtractOperation(actionsMap, filePath, (ExtractOperationRefactoring) refactoring);
            } else if (refactoring instanceof RenameVariableRefactoring) {
                handleRenameVariable(actionsMap, filePath, (RenameVariableRefactoring) refactoring);
            } else if (refactoring instanceof RenameOperationRefactoring) {
                handleRenameOperation(actionsMap, filePath, (RenameOperationRefactoring) refactoring);
            } else if (refactoring instanceof RenameClassRefactoring) {
                handleRenameClass(actionsMap, filePath, (RenameClassRefactoring) refactoring);
            } else if (refactoring instanceof ChangeAttributeTypeRefactoring) {
                handleChangeAttributeType(actionsMap, filePath, (ChangeAttributeTypeRefactoring) refactoring);
            } else if (refactoring instanceof ChangeReturnTypeRefactoring) {
                handleChangeReturnType(actionsMap, filePath, (ChangeReturnTypeRefactoring) refactoring);
            } else if (refactoring instanceof ChangeVariableTypeRefactoring) {
                handleChangeVariableType(actionsMap, filePath, (ChangeVariableTypeRefactoring) refactoring);
            } else if (refactoring instanceof RemoveParameterRefactoring) {
                handleRemoveParameter(actionsMap, filePath, (RemoveParameterRefactoring) refactoring);
            } else if (refactoring instanceof AddParameterRefactoring) {
                handleAddParameter(actionsMap, filePath, (AddParameterRefactoring) refactoring);
            } else if (refactoring instanceof ReorderParameterRefactoring) {
                handleReorderParameter(actionsMap, filePath, (ReorderParameterRefactoring) refactoring);
            } else if (refactoring instanceof ExtractSuperclassRefactoring) {
                handleExtractSuperclass(actionsMap, filePath, (ExtractSuperclassRefactoring) refactoring);
            } else if (refactoring instanceof PullUpAttributeRefactoring) {
                handlePullUpAttribute(actionsMap, filePath, (PullUpAttributeRefactoring) refactoring);
            } else if (refactoring instanceof PullUpOperationRefactoring) {
                handlePullUpOperation(actionsMap, filePath, (PullUpOperationRefactoring) refactoring);
            } else if (refactoring instanceof PushDownAttributeRefactoring) {
                handlePushDownAttribute(actionsMap, filePath, (PushDownAttributeRefactoring) refactoring);
            } else if (refactoring instanceof PushDownOperationRefactoring) {
                handlePushDownOperation(actionsMap, filePath, (PushDownOperationRefactoring) refactoring);
            }
        }
    }

    private static void handleExtractOperation(Map<Integer, List<Data>> actionsMap, String filePath, ExtractOperationRefactoring extractOperationRefactoring) {
        if (extractOperationRefactoring.getExtractedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createData("EXTRACTED_METHOD", null, null);
            ActionsUtils.addActionToLine(actionsMap, extractOperationRefactoring.getExtractedOperation().getLocationInfo().getStartLine(), action);
        }
        for (OperationInvocation call : extractOperationRefactoring.getExtractedOperationInvocations()) {
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

    private static void handleRenameClass(Map<Integer, List<Data>> actionsMap, String filePath, RenameClassRefactoring renameClassRefactoring) {
        if (renameClassRefactoring.getRenamedClass().getLocationInfo().getFilePath().equals(filePath)) {
            String[] classParts = renameClassRefactoring.getOriginalClassName().split("\\.");
            Data action = DataFactory.createRefactoringData("RENAME_CLASS", classParts[classParts.length - 1]);
            ActionsUtils.addActionToLine(actionsMap, renameClassRefactoring.getRenamedClass().getLocationInfo().getStartLine(), action);
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

    private static void handleRemoveParameter(Map<Integer, List<Data>> actionsMap, String filePath, RemoveParameterRefactoring removeParameterRefactoring) {
        if (removeParameterRefactoring.getOperationAfter().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("REMOVE_PARAMETER", removeParameterRefactoring.getParameter().getType().getClassType(), removeParameterRefactoring.getParameter().getName());
            ActionsUtils.addActionToLine(actionsMap, removeParameterRefactoring.getOperationAfter().getLocationInfo().getStartLine(), action);
        }
    }

    private static void handleAddParameter(Map<Integer, List<Data>> actionsMap, String filePath, AddParameterRefactoring addParameterRefactoring) {
        if (addParameterRefactoring.getOperationAfter().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("ADD_PARAMETER", addParameterRefactoring.getParameter().getType().getClassType(), addParameterRefactoring.getParameter().getName());
            ActionsUtils.addActionToLine(actionsMap, addParameterRefactoring.getOperationAfter().getLocationInfo().getStartLine(), action);
        }
    }

    private static void handleReorderParameter(Map<Integer, List<Data>> actionsMap, String filePath, ReorderParameterRefactoring reorderParameterRefactoring) {
        if (reorderParameterRefactoring.getOperationAfter().getLocationInfo().getFilePath().equals(filePath)) {
            List<String> oldParametersOrder = reorderParameterRefactoring.getParametersBefore().stream().map(parameter -> parameter.getType().getClassType() + " " + parameter.getVariableName()).collect(Collectors.toList());
            Data action = DataFactory.createRefactoringData("REORDER_PARAMETER", oldParametersOrder.toArray(new String[oldParametersOrder.size()]));
            ActionsUtils.addActionToLine(actionsMap, reorderParameterRefactoring.getOperationAfter().getLocationInfo().getStartLine(), action);
        }
    }

    private static void handleExtractSuperclass(Map<Integer, List<Data>> actionsMap, String filePath, ExtractSuperclassRefactoring extractSuperclassRefactoring) {
        final String refactoringType = extractSuperclassRefactoring.getExtractedClass().isInterface() ? "EXTRACT_INTERFACE" : "EXTRACT_SUPERCLASS";
        List<String> subclasses = new ArrayList<>(extractSuperclassRefactoring.getSubclassSet());
        Data action = DataFactory.createRefactoringData(refactoringType, subclasses.toArray(new String[subclasses.size()]));
        for (UMLClass umlClass: extractSuperclassRefactoring.getUMLSubclassSet()) {
            if (umlClass.getLocationInfo().getFilePath().equals(filePath)) {
                ActionsUtils.addActionToLine(actionsMap, umlClass.getLocationInfo().getStartLine(), action);
            }
        }
        LocationInfo extractedClassLocation = extractSuperclassRefactoring.getExtractedClass().getLocationInfo();
        if (extractedClassLocation.getFilePath().equals(filePath)) {
            ActionsUtils.addActionToLine(actionsMap, extractedClassLocation.getStartLine(), action);
        }
    }

    private static void handlePullUpAttribute(Map<Integer, List<Data>> actionsMap, String filePath, PullUpAttributeRefactoring pullUpAttributeRefactoring) {
        if (pullUpAttributeRefactoring.getMovedAttribute().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("PULL_UP_ATTRIBUTE", pullUpAttributeRefactoring.getMovedAttribute().getClassName(), pullUpAttributeRefactoring.getOriginalAttribute().getClassName());
            ActionsUtils.addPullUpAttribute(actionsMap, pullUpAttributeRefactoring.getMovedAttribute().getLocationInfo().getStartLine(), (PullUpAttribute) action);
        }
    }

    private static void handlePullUpOperation(Map<Integer, List<Data>> actionsMap, String filePath, PullUpOperationRefactoring pullUpOperationRefactoring) {
        if (pullUpOperationRefactoring.getMovedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("PULL_UP_METHOD", pullUpOperationRefactoring.getMovedOperation().getClassName(), pullUpOperationRefactoring.getOriginalOperation().getClassName());
            ActionsUtils.addPullUpMethod(actionsMap, pullUpOperationRefactoring.getMovedOperation().getLocationInfo().getStartLine(), (PullUpMethod) action);
        }
    }

    private static void handlePushDownAttribute(Map<Integer, List<Data>> actionsMap, String filePath, PushDownAttributeRefactoring pushDownAttributeRefactoring) {
        if (pushDownAttributeRefactoring.getMovedAttribute().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("PUSH_DOWN_ATTRIBUTE", pushDownAttributeRefactoring.getOriginalAttribute().getClassName());
            ActionsUtils.addActionToLine(actionsMap, pushDownAttributeRefactoring.getMovedAttribute().getLocationInfo().getStartLine(), action);
        }
    }

    private static void handlePushDownOperation(Map<Integer, List<Data>> actionsMap, String filePath, PushDownOperationRefactoring pushDownOperationRefactoring) {
        if (pushDownOperationRefactoring.getMovedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("PUSH_DOWN_METHOD", pushDownOperationRefactoring.getOriginalOperation().getClassName());
            ActionsUtils.addActionToLine(actionsMap, pushDownOperationRefactoring.getMovedOperation().getLocationInfo().getStartLine(), action);
        }
    }

}
