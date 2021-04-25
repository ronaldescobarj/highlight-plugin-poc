package models.refactorings;

import java.util.Arrays;
import java.util.List;

public class ExtractedMethod extends RefactoringData {

    String[] extractedCodeFragments;
    long startOffsetOfSourceOperation;
    long endOffsetOfSourceOperation;

    public ExtractedMethod(String... attributes) {
        this.startOffset = Long.parseLong(attributes[0]);
        this.endOffset = Long.parseLong(attributes[1]);
        this.startOffsetOfSourceOperation = Long.parseLong(attributes[2]);
        this.endOffsetOfSourceOperation = Long.parseLong(attributes[3]);
        List<String> extractedCodeFragments = Arrays.asList(attributes).subList(4, attributes.length);
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
