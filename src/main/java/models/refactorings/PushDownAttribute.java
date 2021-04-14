package models.refactorings;

public class PushDownAttribute extends RefactoringData {
    String oldParentClass;
    String parentClassUrl;

    public PushDownAttribute(String... attributes) {
        this.oldParentClass = attributes[0];
        this.parentClassUrl = attributes[1];
    }

    @Override
    public String renderData() {
        return "<b>PUSHED DOWN ATTRIBUTE<br>This attribute was previously in:</b><br>" + oldParentClass;
    }

    @Override
    public String getType() {
        return "PUSH_DOWN_ATTRIBUTE";
    }

    public String getParentClassUrl() {
        return parentClassUrl;
    }
}
