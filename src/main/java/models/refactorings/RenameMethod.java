package models.refactorings;

public class RenameMethod extends RefactoringData {
    String oldName;

    public RenameMethod(String... attributes) {
        this.oldName = attributes[0];
        this.startOffset = Long.parseLong(attributes[1]);
        this.endOffset = Long.parseLong(attributes[2]);
    }

    @Override
    public String renderData() {
        return "<b>RENAMED METHOD<br>Old method name:</b> " + oldName + "<br>";
    }

    @Override
    public String getType() {
        return "RENAME_METHOD";
    }
}
