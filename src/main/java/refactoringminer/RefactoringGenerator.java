package refactoringminer;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import git.GitLocal;
import models.refactoringminer.Refactoring;
import models.refactoringminer.RefactoringMinerOutput;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.ArrayList;
import java.util.List;

public class RefactoringGenerator {
    public RefactoringMinerOutput generateRefactorings(Project project) {
        String projectPath = project.getBasePath();
        GitLocal gitLocal = new GitLocal(projectPath);
        gitLocal.openRepository();
        RevCommit latestCommit = gitLocal.getLatestCommit();
        RefactoringMinerCmd refactoringMinerCmd = new RefactoringMinerCmd();
        RefactoringMinerOutput output = refactoringMinerCmd.runRefactoringMiner(projectPath, latestCommit.getName());
        return output;
    }

    public List<Refactoring> getRefactorings(Project project) {
        return new ArrayList<>();
    }
}
