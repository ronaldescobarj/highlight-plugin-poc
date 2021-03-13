package models.refactorings;

public class ChangeParameterType extends RefactoringData {
    String oldType;

    public ChangeParameterType(String... attributes) {
        this.oldType = attributes[0];
    }

    @Override
    public String renderData() {
        return "<b>Old type:</b> " + oldType + "<br>";
    }

    @Override
    public String getType() {
        return "CHANGE_PARAMETER_TYPE";
    }
}
