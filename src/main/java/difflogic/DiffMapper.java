package difflogic;

import models.DiffRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffMapper {

    List<DiffRow> diffRows;

    public DiffMapper(List<DiffRow> diffRows) {
        this.diffRows = diffRows;
    }

    public Map<Integer, String> createDiffMap() {
        Map<Integer, String> diffMap = new HashMap<>();
        for (DiffRow diffRow: diffRows) {
            addToMap(diffMap, diffRow);
        }
        return diffMap;
    }

    private void addToMap(Map<Integer, String> diffMap, DiffRow diffRow) {
        int startLine = diffRow.getChange().equals("DEL") ? diffRow.getSrcStart() : diffRow.getDstStart();
        int endLine = diffRow.getChange().equals("DEL") ? diffRow.getSrcEnd() : diffRow.getDstEnd();
        List<Integer> interval = generateIntervalArray(startLine, endLine);
        insertByInterval(diffMap, interval, diffRow.getChange());
    }

    private void insertByInterval(Map<Integer, String> diffMap, List<Integer> interval, String action) {
        for (Integer line: interval) {
            diffMap.put(line, action);
        }
    }

    private List<Integer> generateIntervalArray(int start, int end) {
        List<Integer> interval = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            interval.add(i);
        }
        return interval;
    }
}
