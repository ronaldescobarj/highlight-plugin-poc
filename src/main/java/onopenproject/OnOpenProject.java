package onopenproject;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import git.GitLocal;
import org.jetbrains.annotations.NotNull;
import services.GitService;

public class OnOpenProject implements ProjectManagerListener {

    @Override
    public void projectOpened(@NotNull Project project) {
        String projectPath = project.getBasePath();
        GitService gitService = project.getService(GitService.class);
        GitLocal gitLocal = new GitLocal(projectPath);
        gitLocal.openRepository();
        gitService.setRepository(gitLocal.getRepository());
        gitService.setLatestCommitHash(gitLocal.getLatestCommit().getName());
    }

    @Override
    public void projectClosed(@NotNull Project project) {
    }
}
