package difflogic;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import compare.CompareUtils;
import editor.EditorUtils;
import git.GitLocal;
import models.Data;
import models.DiffRow;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;
import refactoringminer.RefactoringGenerator;
import refactoringminer.RefactoringMinerUtils;
import services.GitService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffMapGenerator {
    public Map<Integer, List<Data>> generateHighlightMapForEditor(Editor editor) {
        Project project = editor.getProject();
        GitService gitService = project.getService(GitService.class);
        Repository repository = gitService.getRepository();
        GitLocal gitLocal = new GitLocal(repository);
        String fileName = EditorUtils.getFileName(editor);
        String filePath = EditorUtils.getRelativePath(editor);
        DiffModifications diffModifications = new DiffModifications();
        RevCommit modificationCommit = diffModifications.getCommitWithLatestModification(fileName, gitLocal);
//        Map<Integer, Integer> amountOfTimes = diffModifications.buildNumberOfModifications(fileName, gitLocal);
        RefactoringGenerator refactoringGenerator = new RefactoringGenerator();
        List<Refactoring> refactorings = refactoringGenerator.getRefactorings(project, modificationCommit);
        Map<Integer, List<Data>> diffMap = getDiffMapOfCommit(modificationCommit, editor, gitLocal);
//        diffModifications.applyAmountOfTimesToDiffMap(diffMap, amountOfTimes);
        RefactoringMinerUtils.addRefactoringsToMap(refactorings, diffMap, filePath);
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
        return new DiffMapper(diffRows, latestCommit, previousFileContent).createDiffMap();
    }

    private Map<Integer, List<Data>> getDiffMapOfCommit(RevCommit commit, Editor editor, GitLocal gitLocal) {
        DiffEntry diffEntry = gitLocal.getDiffEntryOfCommit(commit, editor);
        if (diffEntry == null) {
            return new HashMap<Integer, List<Data>>();
        }
        String commitFileContent = gitLocal.getCurrentCommitFileContent(diffEntry);
        String previousFileContent = gitLocal.getPreviousCommitFileContent(diffEntry);
        List<DiffRow> diffRows = CompareUtils.getDiffChanges(previousFileContent, commitFileContent);
        return new DiffMapper(diffRows, commit, previousFileContent).createDiffMap();
    }

}
