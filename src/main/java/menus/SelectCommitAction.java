package menus;

import com.intellij.execution.Executor;
import com.intellij.ide.util.gotoByName.GotoActionModel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBCheckBox;
import git.GitLocal;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jetbrains.annotations.NotNull;
import services.GitService;

import java.util.List;

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
        List<RevCommit> commits = gitLocal.getSelectedLatestCommitsDescendant(5);
        SelectCommitDialogWrapper dialog = new SelectCommitDialogWrapper(commits);
        if (dialog.showAndGet()) {
            String data = dialog.getSelectedOption();
            System.out.println("test");
        }
    }
}
