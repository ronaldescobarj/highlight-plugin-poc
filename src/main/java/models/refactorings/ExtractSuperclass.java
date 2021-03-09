package models.refactorings;

public class ExtractSuperclass extends RefactoringData {
    String[] subclasses;

    public ExtractSuperclass(String... attributes) {
        this.subclasses = attributes;
    }

    @Override
    public String renderData() {
        return "Classes that extends:<br>" + getSubclasses();
    }

    private String getSubclasses() {
        StringBuilder result = new StringBuilder();
        for (String parameter: subclasses) {
            result.append(parameter).append("<br>");
        }
        return result.toString();
    }

    @Override
    public String getType() {
        return "EXTRACT_SUPERCLASS";
    }
}