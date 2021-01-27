package refactoringminer;

import models.Data;
import models.refactoringminer.Commit;
import models.refactoringminer.Location;
import models.refactoringminer.Refactoring;
import models.refactoringminer.RefactoringMinerOutput;

import java.util.List;
import java.util.Map;

public class RefactoringMinerUtils {
    public static void addRefactoringsToMap(RefactoringMinerOutput refactoringMinerOutput, Map<Integer, List<Data>> actionsMap) {
        for (Refactoring refactoring : refactoringMinerOutput.getRefactorings()) {
            if (refactoring.getType().equals("Extract Method")) {
                for (Location location: refactoring.getRightSideLocations()) {
                    if (location.getCodeElementType().equals("METHOD_INVOCATION")) {
                        //addLabel("call to extracted method")
                    } else if (location.getCodeElementType().equals("METHOD_DECLARATION") && location.getDescription().equals("extracted method declaration")) {
                        //addLabel("extracted method")
                    }
                }
            }
        }
    }
}
