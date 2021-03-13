package popupaction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;
import git.GitLocal;
import org.eclipse.jgit.lib.Repository;
import org.jetbrains.annotations.NotNull;
import org.refactoringminer.api.*;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class PopupDialogAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        // Using the event, evaluate the context, and enable or disable the action.
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
//        try {
//            UMLModel model1 = new UMLModelASTReader(new File("C:\\Users\\Dell\\Documents\\refactoringMiner\\rm1")).getUmlModel();
//            UMLModel model2 = new UMLModelASTReader(new File("C:\\Users\\Dell\\Documents\\refactoringMiner\\rm2")).getUmlModel();
//            UMLModelDiff modelDiff = model1.diff(model2);
//            List<Refactoring> refactorings = modelDiff.getRefactorings();
//            System.out.println("done");
//        } catch(IOException | RefactoringMinerTimedOutException e) {
//            String typeOfError = e.getClass().getTypeName();
//            System.out.println("error");
//        }
        //intentar directo con repo
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
//        String projectPath = event.getProject().getBasePath();
//        GitLocal gitLocal = new GitLocal(projectPath);
//        gitLocal.openRepository();
//        String commitSha = gitLocal.getLatestCommit().getName();
//        Repository repo = gitLocal.getRepository();
//        miner.detectAtCommit(repo, commitSha, new RefactoringHandler() {
//            @Override
//            public void handle(String commitId, List<Refactoring> refactorings) {
//                System.out.println("Refactorings at " + commitId);
//                for (Refactoring ref : refactorings) {
//                    System.out.println(ref.toString());
//                }
//            }
//        });

        Repository repo = null;
        try {
            repo = gitService.cloneIfNotExists(
                    "tmp/refactoring-toy-example",
                    "https://github.com/danilofes/refactoring-toy-example.git");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Refactoring> myRefactorings = new ArrayList<>();
        try {
            miner.detectAll(repo, "master", new RefactoringHandler() {
                @Override
                public void handle(String commitId, List<Refactoring> refactorings) {
                    myRefactorings.addAll(refactorings);
                    System.out.println("Refactorings at " + commitId);
                    for (Refactoring ref : refactorings) {
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        Project currentProject = event.getProject();
        StringBuilder dlgMsg = new StringBuilder(event.getPresentation().getText() + " Selected!");
        String dlgTitle = event.getPresentation().getDescription();
        Navigatable nav = event.getData(CommonDataKeys.NAVIGATABLE);
        if (nav != null) {
            dlgMsg.append(String.format("\nSelected Element: %s", nav.toString()));
        }
        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon());
    }

}
