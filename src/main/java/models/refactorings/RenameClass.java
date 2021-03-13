package models.refactorings;

public class RenameClass extends RefactoringData {
    String oldName;

    public RenameClass(String... attributes) {
        this.oldName = attributes[0];
    }

    @Override
    public String renderData() {
        return "<b>RENAMED CLASS<br>Old class name:</b> " + oldName + "<br>";
    }

    @Override
    public String getType() {
        return "RENAME_CLASS";
    }
}
