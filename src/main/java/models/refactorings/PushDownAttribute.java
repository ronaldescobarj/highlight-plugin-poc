package models.refactorings;

public class PushDownAttribute extends RefactoringData {
    String oldParentClass;

    public PushDownAttribute(String... attributes) {
        this.oldParentClass = attributes[0];
    }

    @Override
    public String renderData() {
        return "This attribute was previously in:<br>" + oldParentClass;
    }

    @Override
    public String getType() {
        return "PUSH_DOWN_ATTRIBUTE";
    }
}
