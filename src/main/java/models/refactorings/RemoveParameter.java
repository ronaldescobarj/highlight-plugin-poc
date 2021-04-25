package models.refactorings;

public class RemoveParameter extends RefactoringData {
    String removedParameterType;
    String removedParameterName;

    public RemoveParameter(String... attributes) {
        this.removedParameterType = attributes[0];
        this.removedParameterName = attributes[1];
        this.startOffset = Long.parseLong(attributes[2]);
        this.endOffset = Long.parseLong(attributes[3]);
    }

    @Override
    public String renderData() {
        return "<b>REMOVED PARAMETER<br>Removed parameter:</b> " + removedParameterType + " " + removedParameterName + "<br>";
    }

    @Override
    public String getType() {
        return "REMOVE_PARAMETER";
    }
}
