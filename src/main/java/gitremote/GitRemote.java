package gitremote;

import com.intellij.ide.ApplicationInitializedListener;
import com.intellij.openapi.components.ServiceManager;
import services.GitRemoteService;

public class GitRemote implements ApplicationInitializedListener {
    @Override
    public void componentsInitialized() {
        GitRemoteService gitRemoteService = ServiceManager.getService(GitRemoteService.class);
        gitRemoteService.obtainRemoteUrlFromCmd();
    }
}
