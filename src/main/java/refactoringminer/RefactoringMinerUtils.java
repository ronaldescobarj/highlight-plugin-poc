package refactoringminer;

import actions.ActionsUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import gr.uom.java.xmi.LocationInfo;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.decomposition.AbstractCodeFragment;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.StatementObject;
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

    Project project;

    public RefactoringMinerUtils(Project project) {
        this.project = project;
    }
    
    public void addRefactoringsToMap(List<Refactoring> refactorings, Map<Integer, List<Data>> actionsMap, String filePath) {
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

    private void handleExtractOperation(Map<Integer, List<Data>> actionsMap, String filePath, ExtractOperationRefactoring extractOperationRefactoring) {
        List<String> codeExtractedFragments = new ArrayList<>();
        for (AbstractCodeFragment abstractCodeFragment: extractOperationRefactoring.getExtractedCodeFragmentsFromSourceOperation()) {
            codeExtractedFragments.add(abstractCodeFragment.getString());
        }
        if (extractOperationRefactoring.getExtractedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("EXTRACTED_METHOD", codeExtractedFragments.toArray(new String[0]));
            ActionsUtils.addActionToLine(actionsMap, extractOperationRefactoring.getExtractedOperation().getLocationInfo().getStartLine(), action);
        }
        for (OperationInvocation call : extractOperationRefactoring.getExtractedOperationInvocations()) {
            if (call.getLocationInfo().getFilePath().equals(filePath)) {
                Data action = DataFactory.createRefactoringData("EXTRACTED_METHOD_CALL", codeExtractedFragments.toArray(new String[0]));
                ActionsUtils.addActionToLine(actionsMap, call.getLocationInfo().getStartLine(), action);
            }
        }
    }

    private void handleRenameVariable(Map<Integer, List<Data>> actionsMap, String filePath, RenameVariableRefactoring renameVariableRefactoring) {
        if (renameVariableRefactoring.getRenamedVariable().getLocationInfo().getFilePath().equals(filePath)) {
            final String refactoringType = renameVariableRefactoring.getRenamedVariable().isParameter() ? "RENAME_PARAMETER" : "RENAME_VARIABLE";
            String startOffset = String.valueOf(renameVariableRefactoring.getRenamedVariable().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(renameVariableRefactoring.getRenamedVariable().getLocationInfo().getEndOffset());
            String[] attributes = refactoringType.equals("RENAME_PARAMETER") ?
                    new String[]{
                            renameVariableRefactoring.getOriginalVariable().getType().getClassType(),
                            renameVariableRefactoring.getOriginalVariable().getVariableName(),
                            startOffset,
                            endOffset
                    }
                    : new String[]{renameVariableRefactoring.getOriginalVariable().getVariableName(), startOffset, endOffset};
            Data action = DataFactory.createRefactoringData(refactoringType, attributes);
            ActionsUtils.addActionToLine(actionsMap, renameVariableRefactoring.getRenamedVariable().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleRenameOperation(Map<Integer, List<Data>> actionsMap, String filePath, RenameOperationRefactoring renameOperationRefactoring) {
        if (renameOperationRefactoring.getRenamedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("RENAME_METHOD", renameOperationRefactoring.getOriginalOperation().getName());
            ActionsUtils.addActionToLine(actionsMap, renameOperationRefactoring.getRenamedOperation().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleRenameClass(Map<Integer, List<Data>> actionsMap, String filePath, RenameClassRefactoring renameClassRefactoring) {
        if (renameClassRefactoring.getRenamedClass().getLocationInfo().getFilePath().equals(filePath)) {
            String[] classParts = renameClassRefactoring.getOriginalClassName().split("\\.");
            Data action = DataFactory.createRefactoringData("RENAME_CLASS", classParts[classParts.length - 1]);
            ActionsUtils.addActionToLine(actionsMap, renameClassRefactoring.getRenamedClass().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleChangeAttributeType(Map<Integer, List<Data>> actionsMap, String filePath, ChangeAttributeTypeRefactoring changeAttributeTypeRefactoring) {
        if (changeAttributeTypeRefactoring.getChangedTypeAttribute().getLocationInfo().getFilePath().equals(filePath)) {
            String startOffset = String.valueOf(changeAttributeTypeRefactoring.getChangedTypeAttribute().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(changeAttributeTypeRefactoring.getChangedTypeAttribute().getLocationInfo().getEndOffset());
            Data action = DataFactory.createRefactoringData("CHANGE_ATTRIBUTE_TYPE", changeAttributeTypeRefactoring.getOriginalAttribute().getType().getClassType(), startOffset, endOffset);
            ActionsUtils.addActionToLine(actionsMap, changeAttributeTypeRefactoring.getChangedTypeAttribute().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleChangeReturnType(Map<Integer, List<Data>> actionsMap, String filePath, ChangeReturnTypeRefactoring changeReturnTypeRefactoring) {
        if (changeReturnTypeRefactoring.getChangedType().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("CHANGE_RETURN_TYPE", changeReturnTypeRefactoring.getOriginalType().getClassType());
            ActionsUtils.addActionToLine(actionsMap, changeReturnTypeRefactoring.getChangedType().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleChangeVariableType(Map<Integer, List<Data>> actionsMap, String filePath, ChangeVariableTypeRefactoring changeVariableTypeRefactoring) {
        if (changeVariableTypeRefactoring.getChangedTypeVariable().getLocationInfo().getFilePath().equals(filePath)) {
            final String refactoringType = changeVariableTypeRefactoring.getChangedTypeVariable().isParameter() ? "CHANGE_PARAMETER_TYPE" : "CHANGE_VARIABLE_TYPE";
            String startOffset = String.valueOf(changeVariableTypeRefactoring.getChangedTypeVariable().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(changeVariableTypeRefactoring.getChangedTypeVariable().getLocationInfo().getEndOffset());
            String[] attributes = refactoringType.equals("CHANGE_PARAMETER_TYPE") ?
                    new String[]{changeVariableTypeRefactoring.getChangedTypeVariable().getVariableName(), changeVariableTypeRefactoring.getOriginalVariable().getType().getClassType(), startOffset, endOffset}
                    : new String[]{changeVariableTypeRefactoring.getOriginalVariable().getType().getClassType(), startOffset, endOffset};
            Data action = DataFactory.createRefactoringData(refactoringType, attributes);
            ActionsUtils.addActionToLine(actionsMap, changeVariableTypeRefactoring.getChangedTypeVariable().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleRemoveParameter(Map<Integer, List<Data>> actionsMap, String filePath, RemoveParameterRefactoring removeParameterRefactoring) {
        if (removeParameterRefactoring.getOperationAfter().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("REMOVE_PARAMETER", removeParameterRefactoring.getParameter().getType().getClassType(), removeParameterRefactoring.getParameter().getName());
            ActionsUtils.addActionToLine(actionsMap, removeParameterRefactoring.getOperationAfter().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleAddParameter(Map<Integer, List<Data>> actionsMap, String filePath, AddParameterRefactoring addParameterRefactoring) {
        if (addParameterRefactoring.getOperationAfter().getLocationInfo().getFilePath().equals(filePath)) {
            String startOffset = String.valueOf(addParameterRefactoring.getParameter().getVariableDeclaration().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(addParameterRefactoring.getParameter().getVariableDeclaration().getLocationInfo().getEndOffset());
            Data action = DataFactory.createRefactoringData("ADD_PARAMETER", addParameterRefactoring.getParameter().getType().getClassType(), addParameterRefactoring.getParameter().getName(), startOffset, endOffset);
            ActionsUtils.addActionToLine(actionsMap, addParameterRefactoring.getOperationAfter().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleReorderParameter(Map<Integer, List<Data>> actionsMap, String filePath, ReorderParameterRefactoring reorderParameterRefactoring) {
        if (reorderParameterRefactoring.getOperationAfter().getLocationInfo().getFilePath().equals(filePath)) {
            List<String> oldParametersOrder = reorderParameterRefactoring.getParametersBefore().stream().map(parameter -> parameter.getType().getClassType() + " " + parameter.getVariableName()).collect(Collectors.toList());
            Data action = DataFactory.createRefactoringData("REORDER_PARAMETER", oldParametersOrder.toArray(new String[oldParametersOrder.size()]));
            ActionsUtils.addActionToLine(actionsMap, reorderParameterRefactoring.getOperationAfter().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleExtractSuperclass(Map<Integer, List<Data>> actionsMap, String filePath, ExtractSuperclassRefactoring extractSuperclassRefactoring) {
        final String refactoringType = extractSuperclassRefactoring.getExtractedClass().isInterface() ? "EXTRACT_INTERFACE" : "EXTRACT_SUPERCLASS";
        List<String> subclasses = new ArrayList<>(extractSuperclassRefactoring.getSubclassSet());
        Data action = DataFactory.createRefactoringData(refactoringType, subclasses.toArray(new String[subclasses.size()]));
        for (UMLClass umlClass : extractSuperclassRefactoring.getUMLSubclassSet()) {
            if (umlClass.getLocationInfo().getFilePath().equals(filePath)) {
                ActionsUtils.addActionToLine(actionsMap, umlClass.getLocationInfo().getStartLine(), action);
            }
        }
        LocationInfo extractedClassLocation = extractSuperclassRefactoring.getExtractedClass().getLocationInfo();
        if (extractedClassLocation.getFilePath().equals(filePath)) {
            ActionsUtils.addActionToLine(actionsMap, extractedClassLocation.getStartLine(), action);
        }
    }

    private void handlePullUpAttribute(Map<Integer, List<Data>> actionsMap, String filePath, PullUpAttributeRefactoring pullUpAttributeRefactoring) {
        if (pullUpAttributeRefactoring.getMovedAttribute().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("PULL_UP_ATTRIBUTE", pullUpAttributeRefactoring.getMovedAttribute().getClassName(), pullUpAttributeRefactoring.getOriginalAttribute().getClassName());
            ActionsUtils.addPullUpAttribute(actionsMap, pullUpAttributeRefactoring.getMovedAttribute().getLocationInfo().getStartLine(), (PullUpAttribute) action);
        }
    }

    private void handlePullUpOperation(Map<Integer, List<Data>> actionsMap, String filePath, PullUpOperationRefactoring pullUpOperationRefactoring) {
        if (pullUpOperationRefactoring.getMovedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("PULL_UP_METHOD", pullUpOperationRefactoring.getMovedOperation().getClassName(), pullUpOperationRefactoring.getOriginalOperation().getClassName());
            ActionsUtils.addPullUpMethod(actionsMap, pullUpOperationRefactoring.getMovedOperation().getLocationInfo().getStartLine(), (PullUpMethod) action);
        }
    }

    private void handlePushDownAttribute(Map<Integer, List<Data>> actionsMap, String filePath, PushDownAttributeRefactoring pushDownAttributeRefactoring) {
        if (pushDownAttributeRefactoring.getMovedAttribute().getLocationInfo().getFilePath().equals(filePath)) {
            String url  = "file://" + project.getBasePath() + "/" + pushDownAttributeRefactoring.getOriginalAttribute().getLocationInfo().getFilePath();
            Data action = DataFactory.createRefactoringData("PUSH_DOWN_ATTRIBUTE", pushDownAttributeRefactoring.getOriginalAttribute().getClassName(), url);
            ActionsUtils.addActionToLine(actionsMap, pushDownAttributeRefactoring.getMovedAttribute().getLocationInfo().getStartLine(), action);
        }
    }

    private void handlePushDownOperation(Map<Integer, List<Data>> actionsMap, String filePath, PushDownOperationRefactoring pushDownOperationRefactoring) {
        if (pushDownOperationRefactoring.getMovedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            String url  = "file://" + project.getBasePath() + "/" + pushDownOperationRefactoring.getOriginalOperation().getLocationInfo().getFilePath();
            Data action = DataFactory.createRefactoringData("PUSH_DOWN_METHOD", pushDownOperationRefactoring.getOriginalOperation().getClassName(), url);
            ActionsUtils.addActionToLine(actionsMap, pushDownOperationRefactoring.getMovedOperation().getLocationInfo().getStartLine(), action);
        }
    }

}
