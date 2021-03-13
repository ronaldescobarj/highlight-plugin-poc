package models.refactorings;

import java.util.Arrays;

public class ReorderParameter extends RefactoringData {
    String[] oldParametersOrder;

    public ReorderParameter(String... attributes) {
        this.oldParametersOrder = attributes;
    }

    @Override
    public String renderData() {
        return "<b>REORDERED PARAMETER<br>Old parameters order:</b><br>" + getOldParametersOrderString() + "<br>";
    }

    private String getOldParametersOrderString() {
        StringBuilder result = new StringBuilder();
        for (String parameter: oldParametersOrder) {
            result.append(parameter).append(", ");
        }
        result.delete(result.length() - 2, result.length());
        return result.toString();
    }

    @Override
    public String getType() {
        return "REORDER_PARAMETER";
    }
}
