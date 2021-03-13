package difflogic;

import actions.ActionsUtils;
import models.*;
import models.actions.ModificationData;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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

    public Map<Integer, List<Data>> createDiffMap() {
        Map<Integer, List<Data>> diffMap = new HashMap<>();
        for (DiffRow diffRow: diffRows) {
            addToMap(diffMap, diffRow);
        }
        return diffMap;
    }

    private void addToMap(Map<Integer, List<Data>> diffMap, DiffRow diffRow) {
        int startLine = diffRow.getChange().equals("DEL") ? diffRow.getSrcStart() : diffRow.getDstStart();
        int endLine = diffRow.getChange().equals("DEL") ? diffRow.getSrcEnd() : diffRow.getDstEnd();
        List<Integer> interval = generateIntervalArray(startLine, endLine);
        insertByInterval(diffMap, interval, diffRow);
    }

    private List<Integer> generateIntervalArray(int start, int end) {
        List<Integer> interval = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            interval.add(i);
        }
        return interval;
    }

    private void insertByInterval(Map<Integer, List<Data>> diffMap, List<Integer> interval, DiffRow diffRow) {
        PersonIdent author = commit.getAuthorIdent();
        Date date = author.getWhen();
        LocalDateTime commitDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        for (Integer line: interval) {
            ModificationData modification = DataFactory.createModificationData(diffRow.getChange(), author, commitDate);
            modification.setAdditionalData(diffRow);
            ActionsUtils.addActionToLine(diffMap, line, modification);
        }
    }
}
