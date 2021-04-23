package models.refactorings;

import java.util.Arrays;
import java.util.List;

public class ExtractedMethod extends RefactoringData {

    String[] extractedCodeFragments;
    int startOffset;
    int endOffset;

    public ExtractedMethod(String... attributes) {
        this.startOffset = Integer.parseInt(attributes[0]);
        this.endOffset = Integer.parseInt(attributes[1]);
        List<String> extractedCodeFragments = Arrays.asList(attributes).subList(2, attributes.length);
        this.extractedCodeFragments = extractedCodeFragments.toArray(new String[0]);
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
