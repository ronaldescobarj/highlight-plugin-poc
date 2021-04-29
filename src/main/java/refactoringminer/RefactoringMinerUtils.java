package refactoringminer;

import actions.ActionsUtils;
import com.intellij.openapi.project.Project;
import gr.uom.java.xmi.LocationInfo;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.decomposition.AbstractCodeFragment;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.diff.*;
import models.Data;
import models.DataFactory;
//import models.refactoringminer.Refactoring;
import models.refactorings.PullUpAttribute;
import models.refactorings.PullUpMethod;
import models.refactorings.RefactoringData;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class RefactoringMinerUtils {

    Project project;
    RevCommit commit;

    public RefactoringMinerUtils(Project project, RevCommit commit) {
        this.project = project;
        this.commit = commit;
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
            String startOffsetOfSourceOperation = String.valueOf(extractOperationRefactoring.getSourceOperationAfterExtraction().getLocationInfo().getStartOffset());
            String endOffsetOfSourceOperation = String.valueOf(extractOperationRefactoring.getSourceOperationAfterExtraction().getLocationInfo().getEndOffset());
            String startOffset = String.valueOf(extractOperationRefactoring.getExtractedOperation().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(extractOperationRefactoring.getExtractedOperation().getLocationInfo().getEndOffset());
            List<String> elements = new ArrayList<>(codeExtractedFragments);
            elements.add(0, endOffsetOfSourceOperation);
            elements.add(0, startOffsetOfSourceOperation);
            elements.add(0, endOffset);
            elements.add(0, startOffset);
            Data action = DataFactory.createRefactoringData("EXTRACTED_METHOD", elements.toArray(new String[0]));
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, extractOperationRefactoring.getExtractedOperation().getLocationInfo().getStartLine(), action);
        }
        for (OperationInvocation call : extractOperationRefactoring.getExtractedOperationInvocations()) {
            if (call.getLocationInfo().getFilePath().equals(filePath)) {
                String startOffset = String.valueOf(call.getLocationInfo().getStartOffset());
                String endOffset = String.valueOf(call.getLocationInfo().getEndOffset());
                List<String> elements = new ArrayList<>(codeExtractedFragments);
                elements.add(0, endOffset);
                elements.add(0, startOffset);
                Data action = DataFactory.createRefactoringData("EXTRACTED_METHOD_CALL", elements.toArray(new String[0]));
                addActionData(action);
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
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, renameVariableRefactoring.getRenamedVariable().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleRenameOperation(Map<Integer, List<Data>> actionsMap, String filePath, RenameOperationRefactoring renameOperationRefactoring) {
        if (renameOperationRefactoring.getRenamedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            String startOffset = String.valueOf(renameOperationRefactoring.getRenamedOperation().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(renameOperationRefactoring.getRenamedOperation().getLocationInfo().getEndOffset());
            Data action = DataFactory.createRefactoringData("RENAME_METHOD", renameOperationRefactoring.getOriginalOperation().getName(), startOffset, endOffset);
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, renameOperationRefactoring.getRenamedOperation().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleRenameClass(Map<Integer, List<Data>> actionsMap, String filePath, RenameClassRefactoring renameClassRefactoring) {
        if (renameClassRefactoring.getRenamedClass().getLocationInfo().getFilePath().equals(filePath)) {
            String[] classParts = renameClassRefactoring.getOriginalClassName().split("\\.");
            String startOffset = String.valueOf(renameClassRefactoring.getRenamedClass().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(renameClassRefactoring.getRenamedClass().getLocationInfo().getEndOffset());
            Data action = DataFactory.createRefactoringData("RENAME_CLASS", classParts[classParts.length - 1], startOffset, endOffset);
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, renameClassRefactoring.getRenamedClass().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleChangeAttributeType(Map<Integer, List<Data>> actionsMap, String filePath, ChangeAttributeTypeRefactoring changeAttributeTypeRefactoring) {
        if (changeAttributeTypeRefactoring.getChangedTypeAttribute().getLocationInfo().getFilePath().equals(filePath)) {
            String startOffset = String.valueOf(changeAttributeTypeRefactoring.getChangedTypeAttribute().getType().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(changeAttributeTypeRefactoring.getChangedTypeAttribute().getType().getLocationInfo().getEndOffset());
            Data action = DataFactory.createRefactoringData("CHANGE_ATTRIBUTE_TYPE", changeAttributeTypeRefactoring.getOriginalAttribute().getType().getClassType(), startOffset, endOffset);
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, changeAttributeTypeRefactoring.getChangedTypeAttribute().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleChangeReturnType(Map<Integer, List<Data>> actionsMap, String filePath, ChangeReturnTypeRefactoring changeReturnTypeRefactoring) {
        if (changeReturnTypeRefactoring.getChangedType().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("CHANGE_RETURN_TYPE", changeReturnTypeRefactoring.getOriginalType().getClassType(), String.valueOf(changeReturnTypeRefactoring.getChangedType().getLocationInfo().getStartOffset()), String.valueOf(changeReturnTypeRefactoring.getChangedType().getLocationInfo().getEndOffset()));
            addActionData(action);
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
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, changeVariableTypeRefactoring.getChangedTypeVariable().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleRemoveParameter(Map<Integer, List<Data>> actionsMap, String filePath, RemoveParameterRefactoring removeParameterRefactoring) {
        if (removeParameterRefactoring.getOperationAfter().getLocationInfo().getFilePath().equals(filePath)) {
            Data action = DataFactory.createRefactoringData("REMOVE_PARAMETER", removeParameterRefactoring.getParameter().getType().getClassType(), removeParameterRefactoring.getParameter().getName(), "-1", "-1");
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, removeParameterRefactoring.getOperationAfter().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleAddParameter(Map<Integer, List<Data>> actionsMap, String filePath, AddParameterRefactoring addParameterRefactoring) {
        if (addParameterRefactoring.getOperationAfter().getLocationInfo().getFilePath().equals(filePath)) {
            String startOffset = String.valueOf(addParameterRefactoring.getParameter().getVariableDeclaration().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(addParameterRefactoring.getParameter().getVariableDeclaration().getLocationInfo().getEndOffset());
            Data action = DataFactory.createRefactoringData("ADD_PARAMETER", addParameterRefactoring.getParameter().getType().getClassType(), addParameterRefactoring.getParameter().getName(), startOffset, endOffset);
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, addParameterRefactoring.getOperationAfter().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleReorderParameter(Map<Integer, List<Data>> actionsMap, String filePath, ReorderParameterRefactoring reorderParameterRefactoring) {
        if (reorderParameterRefactoring.getOperationAfter().getLocationInfo().getFilePath().equals(filePath)) {
            List<String> oldParametersOrder = reorderParameterRefactoring.getParametersBefore().stream().map(parameter -> parameter.getType().getClassType() + " " + parameter.getVariableName()).collect(Collectors.toList());
            String startOffset = String.valueOf(reorderParameterRefactoring.getParametersAfter().get(0).getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(reorderParameterRefactoring.getParametersAfter().get(reorderParameterRefactoring.getParametersAfter().size() - 1).getLocationInfo().getEndOffset());
            List<String> allParameters = oldParametersOrder;
            allParameters.add(0, endOffset);
            allParameters.add(0, startOffset);
            Data action = DataFactory.createRefactoringData("REORDER_PARAMETER", allParameters.toArray(new String[allParameters.size()]));
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, reorderParameterRefactoring.getOperationAfter().getLocationInfo().getStartLine(), action);
        }
    }

    private void handleExtractSuperclass(Map<Integer, List<Data>> actionsMap, String filePath, ExtractSuperclassRefactoring extractSuperclassRefactoring) {
        final String refactoringType = extractSuperclassRefactoring.getExtractedClass().isInterface() ? "EXTRACT_INTERFACE" : "EXTRACT_SUPERCLASS";
        List<String> subclasses = new ArrayList<>(extractSuperclassRefactoring.getSubclassSet());
        List<String> attributes = new ArrayList<>(subclasses);
        attributes.add(0, "-1");
        attributes.add(0, "-1");
        Data actionForExtractedClass = DataFactory.createRefactoringData(refactoringType, attributes.toArray(new String[attributes.size()]));
        addActionData(actionForExtractedClass);
        for (UMLClass umlClass : extractSuperclassRefactoring.getUMLSubclassSet()) {
            String startOffset = String.valueOf(umlClass.getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(umlClass.getLocationInfo().getEndOffset());
            List<String> subclassAttributes = new ArrayList<>(subclasses);
            subclassAttributes.add(0, endOffset);
            subclassAttributes.add(0, startOffset);
            Data actionForChildClass = DataFactory.createRefactoringData(refactoringType, subclassAttributes.toArray(new String[subclassAttributes.size()]));
            addActionData(actionForChildClass);
            if (umlClass.getLocationInfo().getFilePath().equals(filePath)) {
                ActionsUtils.addActionToLine(actionsMap, umlClass.getLocationInfo().getStartLine(), actionForChildClass);
            }
        }
        LocationInfo extractedClassLocation = extractSuperclassRefactoring.getExtractedClass().getLocationInfo();
        if (extractedClassLocation.getFilePath().equals(filePath)) {
            ActionsUtils.addActionToLine(actionsMap, extractedClassLocation.getStartLine(), actionForExtractedClass);
        }
    }

    private void handlePullUpAttribute(Map<Integer, List<Data>> actionsMap, String filePath, PullUpAttributeRefactoring pullUpAttributeRefactoring) {
        if (pullUpAttributeRefactoring.getMovedAttribute().getLocationInfo().getFilePath().equals(filePath)) {
            String startOffset = String.valueOf(pullUpAttributeRefactoring.getMovedAttribute().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(pullUpAttributeRefactoring.getMovedAttribute().getLocationInfo().getEndOffset());
            Data action = DataFactory.createRefactoringData("PULL_UP_ATTRIBUTE", pullUpAttributeRefactoring.getMovedAttribute().getClassName(), startOffset, endOffset, pullUpAttributeRefactoring.getOriginalAttribute().getClassName());
            addActionData(action);
            ActionsUtils.addPullUpAttribute(actionsMap, pullUpAttributeRefactoring.getMovedAttribute().getLocationInfo().getStartLine(), (PullUpAttribute) action);
        }
    }

    private void handlePullUpOperation(Map<Integer, List<Data>> actionsMap, String filePath, PullUpOperationRefactoring pullUpOperationRefactoring) {
        if (pullUpOperationRefactoring.getMovedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            String startOffset = String.valueOf(pullUpOperationRefactoring.getMovedOperation().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(pullUpOperationRefactoring.getMovedOperation().getLocationInfo().getEndOffset());
            Data action = DataFactory.createRefactoringData("PULL_UP_METHOD", pullUpOperationRefactoring.getMovedOperation().getClassName(), startOffset, endOffset, pullUpOperationRefactoring.getOriginalOperation().getClassName());
            addActionData(action);
            ActionsUtils.addPullUpMethod(actionsMap, pullUpOperationRefactoring.getMovedOperation().getLocationInfo().getStartLine(), (PullUpMethod) action);
        }
    }

    private void handlePushDownAttribute(Map<Integer, List<Data>> actionsMap, String filePath, PushDownAttributeRefactoring pushDownAttributeRefactoring) {
        if (pushDownAttributeRefactoring.getMovedAttribute().getLocationInfo().getFilePath().equals(filePath)) {
            String url  = "file://" + project.getBasePath() + "/" + pushDownAttributeRefactoring.getOriginalAttribute().getLocationInfo().getFilePath();
            String startOffset = String.valueOf(pushDownAttributeRefactoring.getMovedAttribute().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(pushDownAttributeRefactoring.getMovedAttribute().getLocationInfo().getEndOffset());
            Data action = DataFactory.createRefactoringData("PUSH_DOWN_ATTRIBUTE", pushDownAttributeRefactoring.getOriginalAttribute().getClassName(), url, startOffset, endOffset);
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, pushDownAttributeRefactoring.getMovedAttribute().getLocationInfo().getStartLine(), action);
        }
    }

    private void handlePushDownOperation(Map<Integer, List<Data>> actionsMap, String filePath, PushDownOperationRefactoring pushDownOperationRefactoring) {
        if (pushDownOperationRefactoring.getMovedOperation().getLocationInfo().getFilePath().equals(filePath)) {
            String url  = "file://" + project.getBasePath() + "/" + pushDownOperationRefactoring.getOriginalOperation().getLocationInfo().getFilePath();
            String startOffset = String.valueOf(pushDownOperationRefactoring.getMovedOperation().getLocationInfo().getStartOffset());
            String endOffset = String.valueOf(pushDownOperationRefactoring.getMovedOperation().getLocationInfo().getEndOffset());
            Data action = DataFactory.createRefactoringData("PUSH_DOWN_METHOD", pushDownOperationRefactoring.getOriginalOperation().getClassName(), url, startOffset, endOffset);
            addActionData(action);
            ActionsUtils.addActionToLine(actionsMap, pushDownOperationRefactoring.getMovedOperation().getLocationInfo().getStartLine(), action);
        }
    }

    private void addActionData(Data action) {
        PersonIdent author = commit.getAuthorIdent();
        Date date = author.getWhen();
        LocalDateTime commitDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        action.setAuthor(author);
        action.setDateTime(commitDate);
    }

}
