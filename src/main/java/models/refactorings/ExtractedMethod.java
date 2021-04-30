package models.refactorings;

import java.util.Arrays;
import java.util.List;

public class ExtractedMethod extends RefactoringData {

    String[] extractedCodeFragments;
    long startOffsetOfSourceOperation;

    long endOffsetOfSourceOperation;

    public ExtractedMethod(String... attributes) {
        super();
        this.startOffset = Long.parseLong(attributes[0]);
        this.endOffset = Long.parseLong(attributes[1]);
        this.startOffsetOfSourceOperation = Long.parseLong(attributes[2]);
        this.endOffsetOfSourceOperation = Long.parseLong(attributes[3]);
        List<String> extractedCodeFragments = Arrays.asList(attributes).subList(4, attributes.length);
        this.extractedCodeFragments = extractedCodeFragments.toArray(new String[0]);
    }

    @Override
    public String renderData() {
        return "<b>EXTRACTED METHOD<br>Extracted code fragments:</b><br>"
                + renderExtractedCodeFragments() + printAdditionalData() + "<br>Click on the label to select the source operation";
    }

    public String renderExtractedCodeFragments() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            result.append(extractedCodeFragments[i]).append("<br>");
        }
        if (extractedCodeFragments.length > 3) {
            result.append("...<br>");
        }
        return result.toString();
    }

    public long getStartOffsetOfSourceOperation() {
        return startOffsetOfSourceOperation;
    }

    public long getEndOffsetOfSourceOperation() {
        return endOffsetOfSourceOperation;
    }

    @Override
    public String getType() {
        return "EXTRACTED_METHOD";
    }


}
