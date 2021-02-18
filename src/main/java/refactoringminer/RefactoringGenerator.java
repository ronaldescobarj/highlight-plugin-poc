package refactoringminer;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import git.GitLocal;
import models.refactoringminer.RefactoringMinerOutput;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class RefactoringGenerator {
    public RefactoringMinerOutput generateRefactorings(Project project) {
        String projectPath = project.getBasePath();
        GitLocal gitLocal = new GitLocal(projectPath);
        gitLocal.openRepository();
        RevCommit latestCommit = gitLocal.getLatestCommit();
        RefactoringMinerCmd refactoringMinerCmd = new RefactoringMinerCmd();
        return refactoringMinerCmd.runRefactoringMiner(projectPath, latestCommit.getName());
    }

    public List<Refactoring> getRefactorings(String projectPath) {
        GitLocal gitLocal = new GitLocal(projectPath);
        gitLocal.openRepository();
        String commitSha = gitLocal.getLatestCommit().getName();
        Repository repo = gitLocal.getRepository();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        List<Refactoring> myRefactorings = new ArrayList<>();
        miner.detectAtCommit(repo, commitSha, new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                myRefactorings.addAll(refactorings);
            }
        });
        return myRefactorings;
    }
}
