package onopenproject;

import at.aau.softwaredynamics.runner.util.GitHelper;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import gitremote.GitRemote;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jetbrains.annotations.NotNull;
import services.GitService;

import java.util.Collection;

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
        System.out.println("cierra proyecto");
    }
}
