package difflogic;

import com.intellij.openapi.editor.Editor;
import compare.CompareUtils;
import editor.EditorUtils;
import git.GitLocal;
import models.DiffRow;
import models.ModificationData;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DiffMapGenerator {
    public Map<Integer, ModificationData> generateHighlightMapForEditor(Editor editor) {
        String projectPath = editor.getProject().getBasePath();
        GitLocal gitLocal = new GitLocal(projectPath);
        gitLocal.openRepository();
        String fileName = EditorUtils.getFileName(editor);
        DiffModifications diffModifications = new DiffModifications();
        Map<Integer, Integer> amountOfTimes = diffModifications.buildNumberOfModifications(fileName, gitLocal);
        Map<Integer, ModificationData> diffMap = getLatestDiffMap(editor, gitLocal);
        diffModifications.applyAmountOfTimesToDiffMap(diffMap, amountOfTimes);
        gitLocal.closeRepository();
        return diffMap;
    }

    private Map<Integer, ModificationData> getLatestDiffMap(Editor editor, GitLocal gitLocal) {
        DiffEntry diffEntry = gitLocal.getDiffEntry(editor);
        String currentFileContent = gitLocal.getCurrentCommitFileContent(diffEntry);
        String previousFileContent = gitLocal.getPreviousCommitFileContent(diffEntry);
        RevCommit latestCommit = gitLocal.getLatestCommit();
        List<DiffRow> diffRows = CompareUtils.getDiffChanges(previousFileContent, currentFileContent);
        return new DiffMapper(diffRows, latestCommit).createDiffMap();
    }

}
