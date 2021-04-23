package models.refactorings;

public class AddParameter extends RefactoringData {
    String addedParameterType;
    String addedParameterName;

    public AddParameter(String... attributes) {
        this.addedParameterType = attributes[0];
        this.addedParameterName = attributes[1];
        this.startOffset = Long.parseLong(attributes[2]);
        this.endOffset = Long.parseLong(attributes[3]);
    }

    @Override
    public String renderData() {
        return "<b>ADDED PARAMETER<br>Added parameter:</b> " + addedParameterType + " " + addedParameterName + "<br>";
    }

    @Override
    public String getType() {
        return "ADD_PARAMETER";
    }

}
