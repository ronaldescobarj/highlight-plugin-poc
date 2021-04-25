package models.refactorings;

import java.util.Arrays;
import java.util.List;

public class ExtractedMethodCall extends RefactoringData {

    String[] extractedCodeFragments;

    public ExtractedMethodCall(String[] attributes) {
        this.startOffset = Long.parseLong(attributes[0]);
        this.endOffset = Long.parseLong(attributes[1]);
        List<String> extractedCodeFragments = Arrays.asList(attributes).subList(2, attributes.length);
        this.extractedCodeFragments = extractedCodeFragments.toArray(new String[0]);
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
