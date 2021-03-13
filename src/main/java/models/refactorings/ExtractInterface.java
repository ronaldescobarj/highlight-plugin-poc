package models.refactorings;

public class ExtractInterface extends RefactoringData {
    String[] subclasses;

    public ExtractInterface(String... attributes) {
        this.subclasses = attributes;
    }

    @Override
    public String renderData() {
        return "<b>Classes that implement:</b><br>" + getSubclasses();
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
        return "EXTRACT_INTERFACE";
    }
}
