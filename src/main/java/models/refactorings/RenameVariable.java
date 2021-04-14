package models.refactorings;

public class RenameVariable extends RefactoringData {
    String oldName;
    int startOffset;
    int endOffset;

    public RenameVariable(String... attributes) {
        this.oldName = attributes[0];
        this.startOffset = Integer.parseInt(attributes[1]);
        this.endOffset = Integer.parseInt(attributes[2]);
    }

    @Override
    public String renderData() {
        return "<b>RENAMED VARIABLE<br>Old variable name:</b> " + oldName + "<br>";
    }

    @Override
    public String getType() {
        return "RENAME_VARIABLE";
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }
}
