package models.refactorings;

public class RenameMethod extends RefactoringData {
    String oldName;

    public RenameMethod(String... attributes) {
        this.oldName = attributes[0];
    }

    @Override
    public String renderData() {
        return "Old name:" + oldName + "<br>";
    }

    @Override
    public String getType() {
        return "RENAME_METHOD";
    }
}
