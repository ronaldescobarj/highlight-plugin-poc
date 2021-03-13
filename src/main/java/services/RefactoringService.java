package services;

import org.refactoringminer.api.Refactoring;

import java.util.List;

public class RefactoringService {
    List<Refactoring> refactorings;

    public void setRefactorings(List<Refactoring> refactorings) {
        this.refactorings = refactorings;
    }

    public List<Refactoring> getRefactorings() {
        return refactorings;
    }
}
