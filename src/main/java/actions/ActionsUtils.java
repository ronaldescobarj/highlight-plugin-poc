package actions;

import models.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionsUtils {
    public static void addActionToLine(Map<Integer, List<Data>> actionsMap, int line, Data action) {
        List<Data> lineActions = actionsMap.get(line);
        if (lineActions == null) {
            lineActions = new ArrayList<>();
        }
        lineActions.add(action);
        actionsMap.put(line, lineActions);
    }
}
