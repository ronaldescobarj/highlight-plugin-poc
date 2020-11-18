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
            if (!diffRow.getChange().equals("DEL")) {
                addToMap(diffMap, diffRow);
            }
        }
        return diffMap;
    }

    private void addToMap(Map<Integer, String> diffMap, DiffRow diffRow) {
        List<Integer> interval = generateIntervalArray(diffRow.getDstStart(), diffRow.getDstEnd());
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
