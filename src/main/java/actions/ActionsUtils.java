package actions;

import models.Data;
import models.DataFactory;
import models.actions.BeginInserted;
import models.actions.Inserted;
import models.actions.ModificationData;
import models.refactorings.PullUpAttribute;
import models.refactorings.PullUpMethod;
import org.eclipse.jgit.lib.PersonIdent;
import org.hibernate.sql.Insert;
import visualelements.actions.BeginInsertVisualElement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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
        } else {
            Data data = lineActions.stream().filter(lineAction -> lineAction instanceof ModificationData && lineAction.getType().equals(action.getType())).findFirst().get();
            data.setStartOffset(Math.min(action.getStartOffset(), data.getStartOffset()));
            data.setEndOffset(Math.max(action.getEndOffset(), data.getEndOffset()));
            lineActions.set(findIndex(lineActions, action), data);
        }
        actionsMap.put(line, lineActions);
    }

    public static void addActionToLineWithOffsets(Map<Integer, List<Data>> actionsMap, int line, Data action, long startOffset, long endOffset) {
        List<Data> lineActions = actionsMap.get(line);
        if (lineActions == null) {
            lineActions = new ArrayList<>();
        }
        if (lineActions
                .stream()
                .noneMatch(lineAction -> lineAction instanceof ModificationData && lineAction.getType().equals(action.getType()))) {
            lineActions.add(action);
        } else {
            Data data = lineActions.stream().filter(lineAction -> lineAction instanceof ModificationData && lineAction.getType().equals(action.getType())).findFirst().get();
            data.setStartOffset(Math.min(startOffset, data.getStartOffset()));
            data.setEndOffset(Math.max(endOffset, data.getEndOffset()));
            lineActions.set(findIndex(lineActions, action), data);
        }
        actionsMap.put(line, lineActions);
    }

    private static int findIndex(List<Data> lineActions, Data action) {
        return IntStream.range(0, lineActions.size())
                .filter(i -> lineActions.get(i) instanceof ModificationData && lineActions.get(i).getType().equals(action.getType()))
                .findFirst()
     .orElse(-1);
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

    public static Map<Integer, List<Data>> groupInserts(Map<Integer, List<Data>> actions) {
        List<Integer> linesNumbers = new ArrayList<Integer>(actions.keySet());
        Collections.sort(linesNumbers);
        boolean isInsertBlock = false;
        for (int i = 0; i < actions.size(); i++) {
            Integer currentLine = linesNumbers.get(i);
            List<Data> currentLineActions = actions.get(currentLine);
            Integer nextLine;
            try {
                nextLine = linesNumbers.get(i + 1);
            } catch (IndexOutOfBoundsException e) {
                if (isInsertBlock) {
                    Data endInsert = createInsertBlockMark(currentLineActions, false);
                    //insertar esto en vez de lo actual
                    List<Data> newActions = new ArrayList<>();
                    newActions.add(endInsert);
                    actions.put(currentLine, newActions);
                    isInsertBlock = false;
                }
                continue;
            }
            List<Data> nextLineActions = actions.get(nextLine);
            if (isOnlyInsert(currentLineActions)) {
                if (isOnlyInsert(nextLineActions)) {
                    if (isInsertBlock) {
                        actions.put(currentLine, new ArrayList<Data>());
                    } else {
                        Data beginInsert = createInsertBlockMark(currentLineActions, true);
                        List<Data> newActions = new ArrayList<>();
                        newActions.add(beginInsert);
                        actions.put(currentLine, newActions);
                        isInsertBlock = true;
                    }
                } else {
                    if (isInsertBlock) {
                        Data endInsert = createInsertBlockMark(currentLineActions, false);
                        List<Data> newActions = new ArrayList<>();
                        newActions.add(endInsert);
                        actions.put(currentLine, newActions);
                        isInsertBlock = false;
                    }
                }
            }
        }
        return actions;
    }

    private static Data createInsertBlockMark(List<Data> actions, boolean isBegin) {
        Inserted insertAction = (Inserted) actions.get(0);
        PersonIdent author = insertAction.getAuthor();
        LocalDateTime commitDate = insertAction.getDateTime();
        long startOffset = insertAction.getStartOffset();
        long endOffset = insertAction.getEndOffset();
        Data beginInsert = DataFactory.createModificationData(isBegin ? "BEGIN_INS" : "END_INS", author, commitDate, startOffset, endOffset);
        return beginInsert;
    }

    private static boolean isOnlyInsert(List<Data> actions) {
        return actions.size() == 1 && actions.get(0).getType().equals("INS");
    }
}
