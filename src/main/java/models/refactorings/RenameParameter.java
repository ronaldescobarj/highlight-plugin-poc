package models.refactorings;

public class RenameParameter extends RefactoringData {
    String parameterType;
    String oldName;
    int startOffset;
    int endOffset;

    public RenameParameter(String... attributes) {
        this.parameterType = attributes[0];
        this.oldName = attributes[1];
        this.startOffset = Integer.parseInt(attributes[2]);
        this.endOffset = Integer.parseInt(attributes[3]);
    }

    @Override
    public String renderData() {
        return "<b>RENAMED PARAMETER<br>Old parameter name:</b> " + parameterType + " " + oldName + "<br>";
    }

    @Override
    public String getType() {
        return "RENAME_PARAMETER";
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }
}
