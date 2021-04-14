package models.refactorings;

public class AddParameter extends RefactoringData {
    String addedParameterType;
    String addedParameterName;
    int startOffset;
    int endOffset;

    public AddParameter(String... attributes) {
        this.addedParameterType = attributes[0];
        this.addedParameterName = attributes[1];
        this.startOffset = Integer.parseInt(attributes[2]);
        this.endOffset = Integer.parseInt(attributes[3]);
    }

    @Override
    public String renderData() {
        return "<b>ADDED PARAMETER<br>Added parameter:</b> " + addedParameterType + " " + addedParameterName + "<br>";
    }

    @Override
    public String getType() {
        return "ADD_PARAMETER";
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }
}
