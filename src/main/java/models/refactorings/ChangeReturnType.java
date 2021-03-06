package models.refactorings;

public class ChangeReturnType extends RefactoringData {
    String oldType;

    public ChangeReturnType(String... attributes) {
        this.oldType = attributes[0];
    }

    @Override
    public String renderData() {
        return "Old type:" + oldType + "<br>";
    }

    @Override
    public String getType() {
        return "CHANGE_RETURN_TYPE";
    }
}
