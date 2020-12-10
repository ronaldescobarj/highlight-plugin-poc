package difflogic;

import com.intellij.openapi.editor.Editor;
import compare.CompareUtils;
import editor.EditorUtils;
import git.GitLocal;
import models.DiffRow;
import org.eclipse.jgit.diff.DiffEntry;

import java.util.List;
import java.util.Map;

public class DiffMapGenerator {
    public Map<Integer, String> generateHighlightMapForEditor(Editor editor) {
        String projectPath = editor.getProject().getBasePath();
        GitLocal gitLocal = new GitLocal(projectPath);
        gitLocal.openRepository();
        String fileName = EditorUtils.getFileName(editor);
        DiffModifications diffModifications = new DiffModifications();
        Map<Integer, Integer> amountOfTimes = diffModifications.buildNumberOfModifications(fileName, gitLocal);
        Map<Integer, String> diffMap = getLatestDiffMap(editor, gitLocal);
        diffModifications.applyAmountOfTimesToDiffMap(diffMap, amountOfTimes);
        gitLocal.closeRepository();
        return diffMap;
    }

    private Map<Integer, String> getLatestDiffMap(Editor editor, GitLocal gitLocal) {
        DiffEntry diffEntry = gitLocal.getDiffEntry(editor);
        String currentFileContent = gitLocal.getCurrentCommitFileContent(diffEntry);
        String previousFileContent = gitLocal.getPreviousCommitFileContent(diffEntry);
        List<DiffRow> diffRows = CompareUtils.getDiffChanges(previousFileContent, currentFileContent);
        return new DiffMapper(diffRows).createDiffMap();
    }

}
