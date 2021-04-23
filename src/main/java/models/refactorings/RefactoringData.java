package models.refactorings;

import models.Data;

public abstract class RefactoringData extends Data {

    public abstract String renderData();
    public abstract String getType();

}
