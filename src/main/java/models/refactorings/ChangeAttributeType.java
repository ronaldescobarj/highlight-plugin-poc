package models.refactorings;

public class ChangeAttributeType extends RefactoringData {
    String oldType;

    public ChangeAttributeType(String... attributes) {
        this.oldType = attributes[0];
    }

    @Override
    public String renderData() {
        return "<b>Changed attribute type<br>Old attribute type:</b> " + oldType + "<br>";
    }

    @Override
    public String getType() {
        return "CHANGE_ATTRIBUTE_TYPE";
    }
}
