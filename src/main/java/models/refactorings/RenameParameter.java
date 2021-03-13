package models.refactorings;

public class RenameParameter extends RefactoringData {
    String parameterType;
    String oldName;

    public RenameParameter(String... attributes) {
        this.parameterType = attributes[0];
        this.oldName = attributes[1];
    }

    @Override
    public String renderData() {
        return "<b>RENAMED PARAMETER<br>Old parameter name:</b> " + parameterType + " " + oldName + "<br>";
    }

    @Override
    public String getType() {
        return "RENAME_PARAMETER";
    }
}
