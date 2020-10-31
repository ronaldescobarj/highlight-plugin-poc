package difflogic;

import java.util.ArrayList;
import java.util.HashMap;

public class DiffMapper {

    ArrayList<DiffRow> diffRows;

    public DiffMapper(ArrayList<DiffRow> diffRows) {
        this.diffRows = diffRows;
    }

    public HashMap<Integer, String> createDiffMap() {
        HashMap<Integer, String> diffMap = new HashMap<>();
        for (DiffRow diffRow: diffRows) {
            if (!diffRow.change.equals("DEL")) {
                addToMap(diffMap, diffRow);
            }
        }
        return diffMap;
    }

    private void addToMap(HashMap<Integer, String> diffMap, DiffRow diffRow) {
        ArrayList<Integer> interval = generateIntervalArray(diffRow.dstStart, diffRow.dstEnd);
        insertByInterval(diffMap, interval, diffRow.change);
    }

    private void insertByInterval(HashMap<Integer, String> diffMap, ArrayList<Integer> interval, String action) {
        for (Integer line: interval) {
            diffMap.put(line, action);
        }
    }

    private ArrayList<Integer> generateIntervalArray(int start, int end) {
        ArrayList<Integer> interval = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            interval.add(i);
        }
        return interval;
    }
}
