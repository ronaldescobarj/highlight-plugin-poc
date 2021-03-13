package models.refactorings;

public class ChangeReturnType extends RefactoringData {
    String oldType;

    public ChangeReturnType(String... attributes) {
        this.oldType = attributes[0];
    }

    @Override
    public String renderData() {
        return "<b>Changed return type<br>Old return type:</b> " + oldType + "<br>";
    }

    @Override
    public String getType() {
        return "CHANGE_RETURN_TYPE";
    }
}
