package models.refactorings;

public class AddParameter extends RefactoringData {
    String addedParameterType;
    String addedParameterName;

    public AddParameter(String... attributes) {
        this.addedParameterType = attributes[0];
        this.addedParameterName = attributes[1];
    }

    @Override
    public String renderData() {
        return "<b>Added parameter:</b> " + addedParameterType + " " + addedParameterName + "<br>";
    }

    @Override
    public String getType() {
        return "ADD_PARAMETER";
    }
}
