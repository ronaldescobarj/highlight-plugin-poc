package popupaction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerItem;
import de.unitrier.st.insituprofiling.core.editorcoverlayer.EditorCoverLayerManager;
import git.GitLocal;
import gr.uom.java.xmi.UMLModel;
import gr.uom.java.xmi.UMLModelASTReader;
import gr.uom.java.xmi.diff.UMLModelDiff;
import models.refactoringminer.RefactoringMinerOutput;
import org.eclipse.jgit.lib.Repository;
import org.jetbrains.annotations.NotNull;
import org.refactoringminer.api.*;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;
import refactoringminer.RefactoringMinerCmd;
import visualelements.VisualElementFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

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

        String projectPath = event.getProject().getBasePath();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        RefactoringMinerCmd refactoringMinerCmd = new RefactoringMinerCmd();
        RefactoringMinerOutput output = refactoringMinerCmd.runRefactoringMiner(projectPath, "b478b9647754dbde43bbe3c6984e1deaa2f65dd7");
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
