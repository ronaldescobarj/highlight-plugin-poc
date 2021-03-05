package refactoringminer;

import actions.ActionsUtils;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.diff.ExtractOperationRefactoring;
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
                ExtractOperationRefactoring extractOperationRefactoring = (ExtractOperationRefactoring) refactoring;
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
        }
    }
}
