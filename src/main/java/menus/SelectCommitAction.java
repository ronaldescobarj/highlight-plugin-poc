package menus;

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.execution.Executor;
import com.intellij.ide.util.gotoByName.GotoActionModel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBCheckBox;
import difflogic.DiffMapGenerator;
import editor.EditorUtils;
import git.GitLocal;
import models.Data;
import models.EditorData;
import models.actions.ModificationData;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jetbrains.annotations.NotNull;
import services.EditorService;
import services.GitService;
import visualelements.VisualElementsUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SelectCommitAction extends AnAction {
    @Override
    public void update(AnActionEvent e) {
        // Using the event, evaluate the context, and enable or disable the action.
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project currentProject = e.getProject();
        GitService gitService = currentProject.getService(GitService.class);
        Repository repository = gitService.getRepository();
        GitLocal gitLocal = new GitLocal(repository);
        List<RevCommit> commits = gitLocal.getSelectedLatestCommitsDescendant(10);
        EditorService editorService = currentProject.getService(EditorService.class);
        Editor activeEditor = editorService.getActiveEditorData().getEditor();
        EditorData editorData = editorService.getActiveEditorData();
        SelectCommitDialogWrapper dialog = new SelectCommitDialogWrapper(commits, editorData.getSourceCommit(), editorData.getDestinationCommit());
        if (dialog.showAndGet()) {
            String sourceCommitSha = dialog.getSelectedOption();
            RevCommit newSourceCommit = commits.stream().filter(commit -> commit.getName().equals(sourceCommitSha)).findFirst().get();
            RevCommit destinationCommit = editorService.getActiveEditorData().getDestinationCommit();
            Map<Integer, List<Data>> changes = new DiffMapGenerator().generateChangesMapForEditor(activeEditor, newSourceCommit, destinationCommit);
            if (!Arrays.stream(destinationCommit.getParents()).anyMatch(commit -> commit == newSourceCommit)) {
                Map<Integer, List<Data>> changesWithParent = new DiffMapGenerator().generateChangesMapForEditor(activeEditor, destinationCommit.getParents()[0], destinationCommit);
                overrideChanges(changes, changesWithParent);
            }
            editorData.setSourceCommit(newSourceCommit);
            editorData.setChanges(changes);
            editorService.setEditorWithData(activeEditor, editorData);
            EditorUtils.refreshEditor(activeEditor);
            new VisualElementsUtils().addVisualElements(activeEditor, editorService.getActiveEditorChanges());
        }
    }

    void overrideChanges(Map<Integer, List<Data>> changes, Map<Integer, List<Data>> changesWithParent) {
        for (Map.Entry<Integer, List<Data>> entry : changes.entrySet()) {
            List<Data> dataList = entry.getValue();
            for (int i = 0; i < dataList.size(); i++) {
                if (!contains(dataList.get(i), changesWithParent, entry.getKey())) {
                    ModificationData modificationData = (ModificationData) dataList.get(i);
                    modificationData.setOnParent(false);
                    dataList.set(i, modificationData);
                }
            }
            changes.put(entry.getKey(), dataList);
        }
    }

    boolean contains(Data dataToSearch, Map<Integer, List<Data>> changesWithParent, int line) {
        for (Map.Entry<Integer, List<Data>> entry : changesWithParent.entrySet()) {
             if (line == entry.getKey() && entry.getValue().stream().anyMatch(data -> data.getType().equals(dataToSearch.getType()))) {
                 return true;
             }
        }
        return false;
    }
}
