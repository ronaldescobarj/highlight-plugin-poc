package actions;

import models.Data;
import models.ModificationData;
import models.refactorings.PullUpAttribute;
import models.refactorings.PullUpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActionsUtils {
    public static void addActionToLine(Map<Integer, List<Data>> actionsMap, int line, Data action) {
        List<Data> lineActions = actionsMap.get(line);
        if (lineActions == null) {
            lineActions = new ArrayList<>();
        }
        if (lineActions
                .stream()
                .noneMatch(lineAction -> lineAction instanceof ModificationData && lineAction.getType().equals(action.getType()))) {
            lineActions.add(action);
        }
        actionsMap.put(line, lineActions);
    }

    public static void addPullUpAttribute(Map<Integer, List<Data>> actionsMap, int line, PullUpAttribute action) {
        List<Data> lineActions = actionsMap.get(line);
        if (lineActions == null) {
            lineActions = new ArrayList<>();
        }
        if (lineActions.stream().noneMatch(lineAction -> lineAction instanceof PullUpAttribute && ((PullUpAttribute) lineAction).getParentClass().equals(action.getParentClass()))) {
            lineActions.add(action);
        } else {
            boolean hasInserted = false;
            for (int i = 0; i < lineActions.size(); i++) {
                if (lineActions.get(i) instanceof PullUpAttribute) {
                    PullUpAttribute pullUpAttribute = (PullUpAttribute) lineActions.get(i);
                    if (pullUpAttribute.getParentClass().equals(action.getParentClass())) {
                        pullUpAttribute.addOldClass(action.getOldClasses()[0]);
                        lineActions.set(i, pullUpAttribute);
                        hasInserted = true;
                        break;
                    }
                }
            }
            if (!hasInserted) {
                lineActions.add(action);
            }
        }
        actionsMap.put(line, lineActions);
    }

    public static void addPullUpMethod(Map<Integer, List<Data>> actionsMap, int line, PullUpMethod action) {
        List<Data> lineActions = actionsMap.get(line);
        if (lineActions == null) {
            lineActions = new ArrayList<>();
        }
        if (lineActions.stream().noneMatch(lineAction -> lineAction instanceof PullUpMethod && ((PullUpMethod) lineAction).getParentClass().equals(action.getParentClass()))) {
            lineActions.add(action);
        } else {
            boolean hasInserted = false;
            for (int i = 0; i < lineActions.size(); i++) {
                if (lineActions.get(i) instanceof PullUpMethod) {
                    PullUpMethod pullUpMethod = (PullUpMethod) lineActions.get(i);
                    if (pullUpMethod.getParentClass().equals(action.getParentClass())) {
                        pullUpMethod.addOldClass(action.getOldClasses()[0]);
                        lineActions.set(i, pullUpMethod);
                        hasInserted = true;
                        break;
                    }
                }
            }
            if (!hasInserted) {
                lineActions.add(action);
            }
        }
        actionsMap.put(line, lineActions);
    }
}
