package onopenproject;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import git.GitRemote;
import gr.uom.java.xmi.UMLModel;
import gr.uom.java.xmi.UMLModelASTReader;
import gr.uom.java.xmi.diff.UMLModelDiff;
import org.jetbrains.annotations.NotNull;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringMinerTimedOutException;
import services.GitService;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
