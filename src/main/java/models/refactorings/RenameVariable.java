package models.refactorings;

public class RenameVariable extends RefactoringData {
    String oldName;

    public RenameVariable(String... attributes) {
        this.oldName = attributes[0];
    }

    @Override
    public String renderData() {
        return "Old name:" + oldName + "<br>";
    }

    @Override
    public String getType() {
        return "RENAME_VARIABLE";
    }
}
