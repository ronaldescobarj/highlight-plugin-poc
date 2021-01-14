package refactoringminer;

import models.refactoringminer.Commit;
import models.refactoringminer.Location;
import models.refactoringminer.Refactoring;
import models.refactoringminer.RefactoringMinerOutput;

public class RefactoringParser {
    public void parseRefactorings(RefactoringMinerOutput refactorings) {
        for (Refactoring refactoring : refactorings.getRefactorings()) {
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
