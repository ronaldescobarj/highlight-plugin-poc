package models.refactorings;

public class PushDownMethod extends RefactoringData {
    String oldParentClass;

    public PushDownMethod(String... attributes) {
        this.oldParentClass = attributes[0];
    }

    @Override
    public String renderData() {
        return "<b>This method was previously in:</b><br>" + oldParentClass;
    }

    @Override
    public String getType() {
        return "PUSH_DOWN_METHOD";
    }
}
