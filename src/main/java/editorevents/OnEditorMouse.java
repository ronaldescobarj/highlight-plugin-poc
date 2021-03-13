package editorevents;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.project.Project;
import difflogic.DiffMapGenerator;
import git.GitLocal;
import models.Data;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jetbrains.annotations.NotNull;
import org.refactoringminer.api.Refactoring;
import refactoringminer.RefactoringGenerator;
import services.EditorService;
import services.GitService;
import services.RefactoringService;
import visualelements.VisualElementsUtils;

import java.util.List;
import java.util.Map;

public class OnEditorMouse implements EditorMouseListener {
    @Override
    public void mouseEntered(@NotNull EditorMouseEvent event) {
        Editor editor = event.getEditor();
        Project project = editor.getProject();
        EditorService editorService = project.getService(EditorService.class);
        Map<Integer, List<Data>> diffMap = null;
        GitService gitService = project.getService(GitService.class);
//        RefactoringService refactoringService = project.getService(RefactoringService.class);
        GitLocal gitLocal = new GitLocal(gitService.getRepository());
        String latestCommitHash = gitLocal.getLatestCommit().getName();
        if (!latestCommitHash.equals(gitService.getLatestCommitHash())) {
//            RefactoringGenerator refactoringGenerator = new RefactoringGenerator();
//            List<Refactoring> refactorings = refactoringGenerator.getRefactorings(project.getProjectFilePath());
//            refactoringService.setRefactorings(refactorings);

            diffMap = new DiffMapGenerator().generateHighlightMapForEditor(editor);
            editorService.setLastOpenedEditor(editor);
            editorService.setDiffMap(diffMap);
            gitService.setLatestCommitHash(latestCommitHash);
        }
        if (editor != editorService.getLastOpenedEditor()) {
            diffMap = new DiffMapGenerator().generateHighlightMapForEditor(editor);
            editorService.setLastOpenedEditor(editor);
            editorService.setDiffMap(diffMap);
        } else {
            diffMap = editorService.getDiffMap();
        }
        if (diffMap != null) {
            new VisualElementsUtils().addVisualElements(editor, diffMap);
        }
    }

}
