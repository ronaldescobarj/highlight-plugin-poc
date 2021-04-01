package menus;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.JBMenuItem;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBMenu;
import com.intellij.ui.components.JBRadioButton;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SelectCommitDialogWrapper extends DialogWrapper {

    ButtonGroup group;
    List<RevCommit> commits;

    public SelectCommitDialogWrapper(List<RevCommit> commits) {
        super(true);
        this.group = new ButtonGroup();
        this.commits = commits;
        init();
        setTitle("Select Commit to Compare");
    }
    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel();
        BoxLayout layout = new BoxLayout(dialogPanel, BoxLayout.Y_AXIS);
        dialogPanel.setLayout(layout);
        for (RevCommit commit: commits) {
            JBRadioButton jbRadioButton = new JBRadioButton(commit.getName());
            jbRadioButton.setActionCommand(commit.getName());
            if (commits.get(0) == commit) {
                jbRadioButton.setSelected(true);
            }
            group.add(jbRadioButton);
            dialogPanel.add(jbRadioButton);
        }
        return dialogPanel;
    }

    String getSelectedOption() {
        return group.getSelection().getActionCommand();
    }
}
