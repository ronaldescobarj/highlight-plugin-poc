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
        JBRadioButton jbRadioButton = new JBRadioButton("hola");
        jbRadioButton.setActionCommand("hola");
        jbRadioButton.setSelected(true);
        JBRadioButton jbRadioButton2 = new JBRadioButton("test2\ntest3\ntest4");
        jbRadioButton2.setActionCommand("test");
        JBRadioButton jbRadioButton3 = new JBRadioButton("xd");
        jbRadioButton3.setActionCommand("xd");
        group.add(jbRadioButton);
        group.add(jbRadioButton2);
        group.add(jbRadioButton3);
        dialogPanel.add(jbRadioButton);
        dialogPanel.add(jbRadioButton2);
        dialogPanel.add(jbRadioButton3);
//        dialogPanel.add(group, BorderLayout.CENTER);
        return dialogPanel;
    }

    String getSelectedOption() {
        return group.getSelection().getActionCommand();
    }
}
