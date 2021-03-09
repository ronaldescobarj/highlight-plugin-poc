package models.refactorings;

public class ChangeVariableType extends RefactoringData {
    String oldType;

    public ChangeVariableType(String... attributes) {
        this.oldType = attributes[0];
    }

    @Override
    public String renderData() {
        return "Old type:" + oldType + "<br>";
    }

    @Override
    public String getType() {
        return "CHANGE_VARIABLE_TYPE";
    }
}