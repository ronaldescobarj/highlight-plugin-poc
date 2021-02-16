package onopenproject;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import git.GitRemote;
import org.jetbrains.annotations.NotNull;
import services.GitService;

public class OnOpenProject implements ProjectManagerListener {

    @Override
    public void projectOpened(@NotNull Project project) {
        String projectPath = project.getBasePath();
        GitService gitService = project.getService(GitService.class);
        GitRemote gitRemote = new GitRemote();
        String gitRemoteUrl = gitRemote.getRemoteUrlFromCmd(projectPath);
        String repoOwner = gitRemote.getRepoOwnerFromRemoteUrl(gitRemoteUrl);
        String repoName = gitRemote.getRepoNameFromRemoteUrl(gitRemoteUrl);
        gitService.setRemoteUrl(gitRemoteUrl);
        gitService.setRepoOwner(repoOwner);
        gitService.setRepoName(repoName);
    }

    @Override
    public void projectClosed(@NotNull Project project) {
    }
}
