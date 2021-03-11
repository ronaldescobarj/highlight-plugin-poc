package models.refactorings;

public class PushDownMethod extends RefactoringData {
    String oldParentClass;

    public PushDownMethod(String... attributes) {
        this.oldParentClass = attributes[0];
    }

    @Override
    public String renderData() {
        return "This method was previously in:<br>" + oldParentClass;
    }

    @Override
    public String getType() {
        return "PUSH_DOWN_METHOD";
    }
}
