package models.refactorings;

public class ChangeVariableType extends RefactoringData {
    String oldType;
    int startOffset;
    int endOffset;

    public ChangeVariableType(String... attributes) {
        this.oldType = attributes[0];
        this.startOffset = Integer.parseInt(attributes[1]);
        this.endOffset = Integer.parseInt(attributes[2]);
    }

    @Override
    public String renderData() {
        return "<b>CHANGED VARIABLE TYPE<br>Old variable type:</b> " + oldType + "<br>";
    }

    @Override
    public String getType() {
        return "CHANGE_VARIABLE_TYPE";
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }
}
