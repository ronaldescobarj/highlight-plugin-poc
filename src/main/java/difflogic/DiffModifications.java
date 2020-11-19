package difflogic;

import at.aau.softwaredynamics.classifier.entities.SourceCodeChange;
import compare.CompareUtils;
import git.GitLocal;
import models.DiffRow;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffModifications {
    public Map<Integer, Integer> buildNumberOfModifications(String fileName, GitLocal gitLocal) {
        Map<Integer, Integer> amountOfTimes = new HashMap<>();
        Map<Integer, String> diffMap = null;
        List<RevCommit> commits = gitLocal.getSelectedLatestCommits(5);
        for (RevCommit commit : commits) {
            List<DiffRow> diffRows = generateDiffWithPreviousCommit(commit, fileName, gitLocal);
            diffMap = new DiffMapper(diffRows).createDiffMap();
            for (Map.Entry<Integer, String> diffsEntry : diffMap.entrySet()) {
                handleDiffEntry(amountOfTimes, diffMap, diffsEntry);
            }
        }
        return amountOfTimes;
    }

    public void applyAmountOfTimesToDiffMap(Map<Integer, String> diffMap, Map<Integer, Integer> amountOfTimes) {
        for (Map.Entry<Integer, Integer> line: amountOfTimes.entrySet()) {
            if (line.getValue() >= 5) {
                diffMap.put(line.getKey(), "UPD_MULTIPLE_TIMES");
            }
        }
    }

    private List<DiffRow> generateDiffWithPreviousCommit(RevCommit commit, String fileName, GitLocal gitLocal) {
        DiffEntry diff = gitLocal.getFileDiffWithPreviousCommit(commit, fileName);
        String previousCommitFileContent = gitLocal.getPreviousCommitFileContent(diff);
        String currentCommitFileContent = gitLocal.getCurrentCommitFileContent(diff);
        CompareUtils compareUtils = new CompareUtils();
        return compareUtils.getDiffChanges(previousCommitFileContent, currentCommitFileContent);
    }

    private void handleDiffEntry(Map<Integer, Integer> amountOfTimes, Map<Integer, String> diffMap, Map.Entry<Integer, String> diffsEntry) {
        if (diffsEntry.getValue().equals("INS")) {
            amountOfTimes.put(diffsEntry.getKey(), 1);
        } else if (diffsEntry.getValue().equals("UPD")) {
            handleUpdateEntry(amountOfTimes, diffMap, diffsEntry);
        }
    }

    private void handleUpdateEntry(Map<Integer, Integer> amountOfTimes, Map<Integer, String> diffMap, Map.Entry<Integer, String> diffsEntry) {
        int times = amountOfTimes.get(diffsEntry.getKey()) != null ? amountOfTimes.get(diffsEntry.getKey()) : 0;
        times++;
        amountOfTimes.put(diffsEntry.getKey(), times);
    }
}
