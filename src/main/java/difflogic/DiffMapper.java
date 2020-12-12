package difflogic;

import models.DiffRow;
import models.ModificationData;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffMapper {

    List<DiffRow> diffRows;
    RevCommit commit;

    public DiffMapper(List<DiffRow> diffRows, RevCommit commit) {
        this.diffRows = diffRows;
        this.commit = commit;
    }

    public DiffMapper(List<DiffRow> diffRows) {
        this.diffRows = diffRows;
    }

    public Map<Integer, ModificationData> createDiffMap() {
        Map<Integer, ModificationData> diffMap = new HashMap<>();
        for (DiffRow diffRow: diffRows) {
            addToMap(diffMap, diffRow);
        }
        return diffMap;
    }

    private void addToMap(Map<Integer, ModificationData> diffMap, DiffRow diffRow) {
        int startLine = diffRow.getChange().equals("DEL") ? diffRow.getSrcStart() : diffRow.getDstStart();
        int endLine = diffRow.getChange().equals("DEL") ? diffRow.getSrcEnd() : diffRow.getDstEnd();
        List<Integer> interval = generateIntervalArray(startLine, endLine);
        insertByInterval(diffMap, interval, diffRow.getChange());
    }

    private List<Integer> generateIntervalArray(int start, int end) {
        List<Integer> interval = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            interval.add(i);
        }
        return interval;
    }

    private void insertByInterval(Map<Integer, ModificationData> diffMap, List<Integer> interval, String action) {
        for (Integer line: interval) {
            ModificationData modification = new ModificationData(action, commit.getAuthorIdent());
            diffMap.put(line, modification);
        }
    }
}
