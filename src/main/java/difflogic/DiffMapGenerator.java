package difflogic;

import com.intellij.openapi.editor.Editor;
import compare.CompareUtils;
import editor.EditorUtils;
import git.GitLocal;
import models.Data;
import models.DiffRow;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;
import refactoringminer.RefactoringGenerator;
import refactoringminer.RefactoringMinerUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffMapGenerator {
    public Map<Integer, List<Data>> generateHighlightMapForEditor(Editor editor) {
        String projectPath = editor.getProject().getBasePath();
        GitLocal gitLocal = new GitLocal(projectPath);
        gitLocal.openRepository();
        String fileName = EditorUtils.getFileName(editor);
        String filePath = EditorUtils.getRelativePath(editor);
        DiffModifications diffModifications = new DiffModifications();
        Map<Integer, Integer> amountOfTimes = diffModifications.buildNumberOfModifications(fileName, gitLocal);
        RefactoringGenerator refactoringGenerator = new RefactoringGenerator();
//        RefactoringMinerOutput refactoringMinerOutput = refactoringGenerator.generateRefactorings(editor.getProject());
        List<Refactoring> myRefactorings = refactoringGenerator.getRefactorings(projectPath);
        Map<Integer, List<Data>> diffMap = getLatestDiffMap(editor, gitLocal);
        diffModifications.applyAmountOfTimesToDiffMap(diffMap, amountOfTimes);
//        RefactoringMinerUtils.addRefactoringsToMap(refactoringMinerOutput, diffMap, filePath);
        RefactoringMinerUtils.addRefactoringsToMap(myRefactorings, diffMap, filePath);
        gitLocal.closeRepository();
        return diffMap;
    }

    private Map<Integer, List<Data>> getLatestDiffMap(Editor editor, GitLocal gitLocal) {
        DiffEntry diffEntry = gitLocal.getDiffEntry(editor);
        if (diffEntry == null) {
            return new HashMap<Integer, List<Data>>();
        }
        String currentFileContent = gitLocal.getCurrentCommitFileContent(diffEntry);
        String previousFileContent = gitLocal.getPreviousCommitFileContent(diffEntry);
        RevCommit latestCommit = gitLocal.getLatestCommit();
        List<DiffRow> diffRows = CompareUtils.getDiffChanges(previousFileContent, currentFileContent);
        return new DiffMapper(diffRows, latestCommit).createDiffMap();
    }

}
