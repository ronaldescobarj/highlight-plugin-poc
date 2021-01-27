package actions;

import models.Data;
import models.ModificationData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionsUtils {
    public static void addActionToLine(Map<Integer, List<Data>> actionsMap, int line, Data action) {
        List<Data> lineActions = actionsMap.get(line);
        if (lineActions == null) {
            lineActions = new ArrayList<>();
        }
        if (!lineActions
                .stream()
                .anyMatch(lineAction -> lineAction instanceof ModificationData && lineAction.getType().equals(action.getType()))) {
            lineActions.add(action);
        }
        actionsMap.put(line, lineActions);
    }
}
