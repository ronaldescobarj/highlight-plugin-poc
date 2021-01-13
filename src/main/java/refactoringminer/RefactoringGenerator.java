package refactoringminer;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import git.GitLocal;
import models.refactoringminer.RefactoringMinerOutput;
import org.eclipse.jgit.revwalk.RevCommit;

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
}
