package models.refactorings;

public class ExtractedMethodCall extends RefactoringData {
    public ExtractedMethodCall() {
    }

    @Override
    public String renderData() {
        return "Extracted method call" + "<br>";
    }

    @Override
    public String getType() {
        return "EXTRACTED_METHOD";
    }
}
