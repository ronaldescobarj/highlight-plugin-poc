package models.refactorings;

public class RenameParameter extends RefactoringData {
    String oldName;

    public RenameParameter(String... attributes) {
        this.oldName = attributes[0];
    }

    @Override
    public String renderData() {
        return "Old name:" + oldName + "<br>";
    }

    @Override
    public String getType() {
        return "RENAME_PARAMETER";
    }
}
