package onopenproject;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import git.GitLocal;
import git.GitRemote;
import org.jetbrains.annotations.NotNull;
import org.refactoringminer.api.Refactoring;
import refactoringminer.RefactoringGenerator;
import services.GitService;
import services.RefactoringService;

import java.util.List;

public class OnOpenProject implements ProjectManagerListener {

    @Override
    public void projectOpened(@NotNull Project project) {
        String projectPath = project.getBasePath();
        GitService gitService = project.getService(GitService.class);
        GitLocal gitLocal = new GitLocal(projectPath);
        gitLocal.openRepository();
        gitService.setRepository(gitLocal.getRepository());
        gitService.setLatestCommitHash(gitLocal.getLatestCommit().getName());
        RefactoringGenerator refactoringGenerator = new RefactoringGenerator();
        List<Refactoring> myRefactorings = refactoringGenerator.getRefactorings(projectPath);
        RefactoringService refactoringService = project.getService(RefactoringService.class);
        refactoringService.setRefactorings(myRefactorings);
    }

    @Override
    public void projectClosed(@NotNull Project project) {
    }
}
