package fileexplorer;

import com.intellij.ide.FileIconProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import difflogic.DiffMapGenerator;
import difflogic.DiffModifications;
import git.GitLocal;
import javalanguage.JavaIcons;
import models.Data;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import services.GitService;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class ChangesIconProvider implements FileIconProvider {
    @Override
    public @Nullable Icon getIcon(@NotNull VirtualFile file, int flags, @Nullable Project project) {
        RevCommit latestCommit = getLatestCommit(project);
        RevCommit previousCommit = latestCommit.getParents()[0];
        Map<Integer, List<Data>> changes = new DiffMapGenerator().generateChangesMapForFile(file, previousCommit, latestCommit, project);
        int numberOfChanges = countChanges(changes);
        if (numberOfChanges == 0) {
            return JavaIcons.NO_CHANGES;
        } else if (numberOfChanges < 10) {
            return JavaIcons.FEW_CHANGES;
        } else if (numberOfChanges < 25) {
            return JavaIcons.MEDIUM_CHANGES;
        } else {
            return JavaIcons.MANY_CHANGES;
        }
//        return JavaIcons.NO_CHANGES;

    }

    private RevCommit getLatestCommit(Project project) {
        GitService gitService = project.getService(GitService.class);
        GitLocal gitLocal = new GitLocal(gitService.getRepository());
        return gitLocal.getLatestCommit();
    }

    private int countChanges(Map<Integer, List<Data>> changes) {
        int numberOfChanges = 0;
        for (Map.Entry<Integer, List<Data>> change: changes.entrySet()) {
            numberOfChanges += change.getValue().size();
        }
        return numberOfChanges;
    }
}
