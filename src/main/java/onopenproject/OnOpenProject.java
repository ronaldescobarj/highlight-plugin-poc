package onopenproject;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;
import services.GitRemoteService;

public class OnOpenProject implements ProjectManagerListener {

    @Override
    public void projectOpened(@NotNull Project project) {
        String projectPath = project.getBasePath();
        GitRemoteService gitRemoteService = project.getService(GitRemoteService.class);
        gitRemoteService.setProjectPath(projectPath);
        gitRemoteService.obtainRemoteUrlFromCmd();
        gitRemoteService.obtainRepoData();

    }

    @Override
    public void projectClosed(@NotNull Project project) {
        System.out.println("cierra proyecto");
    }
}
