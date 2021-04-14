package models.refactorings;

public class ChangeAttributeType extends RefactoringData {
    String oldType;
    int startOffset;
    int endOffset;

    public ChangeAttributeType(String... attributes) {
        this.oldType = attributes[0];
        this.startOffset = Integer.parseInt(attributes[1]);
        this.endOffset = Integer.parseInt(attributes[2]);
    }

    @Override
    public String renderData() {
        return "<b>CHANGED ATTRIBUTE TYPE<br>Old attribute type:</b> " + oldType + "<br>";
    }

    @Override
    public String getType() {
        return "CHANGE_ATTRIBUTE_TYPE";
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }
}
