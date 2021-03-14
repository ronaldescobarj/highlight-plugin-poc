package models.refactorings;

public class ExtractedMethod extends RefactoringData {

    String[] extractedCodeFragments;
    public ExtractedMethod(String[] extractedCodeFragments) {
        this.extractedCodeFragments = extractedCodeFragments;
    }

    @Override
    public String renderData() {
        return "<b>EXTRACTED METHOD<br>Extracted code fragments:</b><br>" + renderExtractedCodeFragments();
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
