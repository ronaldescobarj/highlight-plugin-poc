package models.refactorings;

public class ExtractedMethodCall extends RefactoringData {

    String[] extractedCodeFragments;

    public ExtractedMethodCall(String[] extractedCodeFragments) {
        this.extractedCodeFragments = extractedCodeFragments;
    }

    @Override
    public String renderData() {
        return "<b>EXTRACTED METHOD CALL<br>Extracted code fragments:</b><br>" + renderExtractedCodeFragments();
    }

    public String renderExtractedCodeFragments() {
        StringBuilder result = new StringBuilder();
        for (String extractedCodeFragment: extractedCodeFragments) {
            result.append(extractedCodeFragment).append("<br>");
        }
        return result.toString();
    }

    @Override
    public String getType() {
        return "EXTRACTED_METHOD";
    }
}
