package models.refactorings;

public class ChangeParameterType extends RefactoringData {
    String parameter;
    String oldType;

    public ChangeParameterType(String... attributes) {
        this.parameter = attributes[0];
        this.oldType = attributes[1];
        this.startOffset = Long.parseLong(attributes[2]);
        this.endOffset = Long.parseLong(attributes[3]);
    }

    @Override
    public String renderData() {
        return "<b>CHANGED PARAMETER TYPE<br>Parameter:</b> " + parameter + "<br>"
                + "<b>Old parameter type:</b> " + oldType;
    }

    @Override
    public String getType() {
        return "CHANGE_PARAMETER_TYPE";
    }
}
