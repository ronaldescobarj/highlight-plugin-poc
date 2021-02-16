package difflogic;

import at.aau.softwaredynamics.classifier.entities.SourceCodeChange;
import com.intellij.workspaceModel.storage.bridgeEntities.ModifiableArchivePackagingElementEntity;
import compare.CompareUtils;
import git.GitLocal;
import models.*;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffModifications {
    public Map<Integer, Integer> buildNumberOfModifications(String fileName, GitLocal gitLocal) {
        Map<Integer, Integer> amountOfTimes = new HashMap<>();
        Map<Integer, List<Data>> diffMap;
        List<RevCommit> commits = gitLocal.getSelectedLatestCommits(5);
        for (RevCommit commit : commits) {
            List<DiffRow> diffRows = generateDiffWithPreviousCommit(commit, fileName, gitLocal);
            diffMap = new DiffMapper(diffRows, commit).createDiffMap();
            for (Map.Entry<Integer, List<Data>> diffsEntry : diffMap.entrySet()) {
                handleDiffEntry(amountOfTimes, diffsEntry);
            }
        }
        return amountOfTimes;
    }

    public void applyAmountOfTimesToDiffMap(Map<Integer, List<Data>> diffMap, Map<Integer, Integer> amountOfTimes) {
        for (Map.Entry<Integer, Integer> line: amountOfTimes.entrySet()) {
            if (line.getValue() >= 5) {
                List<Data> modifications = diffMap.get(line.getKey());
                Data modification = modifications.stream().filter(mod -> mod.getType().equals("UPD")).findFirst().get();
                int index = modifications.indexOf(modification);
                modification = DataFactory.createData("UPD_MULTIPLE_TIMES", null, null);
                modifications.set(index, modification);
                diffMap.put(line.getKey(), modifications);
            }
        }
    }

    private List<DiffRow> generateDiffWithPreviousCommit(RevCommit commit, String fileName, GitLocal gitLocal) {
        DiffEntry diff = gitLocal.getFileDiffWithPreviousCommit(commit, fileName);
        if (diff == null) {
            return new ArrayList<>();
        }
        String previousCommitFileContent = gitLocal.getPreviousCommitFileContent(diff);
        String currentCommitFileContent = gitLocal.getCurrentCommitFileContent(diff);
        return CompareUtils.getDiffChanges(previousCommitFileContent, currentCommitFileContent);
    }

    private void handleDiffEntry(Map<Integer, Integer> amountOfTimes, Map.Entry<Integer, List<Data>> diffsEntry) {
        Data modification = ModificationDataUtils.getModificationDataFromLineActions(diffsEntry.getValue());
        if (modification == null) {
            return;
        }
        if (modification instanceof Inserted) {
            amountOfTimes.put(diffsEntry.getKey(), 1);
        } else if (modification instanceof Updated) {
            handleUpdateEntry(amountOfTimes, diffsEntry);
        }
    }

    private void handleUpdateEntry(Map<Integer, Integer> amountOfTimes, Map.Entry<Integer, List<Data>> diffsEntry) {
        int times = amountOfTimes.get(diffsEntry.getKey()) != null ? amountOfTimes.get(diffsEntry.getKey()) : 0;
        times++;
        amountOfTimes.put(diffsEntry.getKey(), times);
    }
}
