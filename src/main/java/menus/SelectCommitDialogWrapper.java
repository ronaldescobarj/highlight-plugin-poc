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
    public SelectCommitDialogWrapper(List<RevCommit> commits) {
        super(true);
        init();
        setTitle("Select Commit to Compare");
    }
    @Override
    protected @Nullable JComponent createCenterPanel() {
        group = new ButtonGroup();
        JPanel dialogPanel = new JPanel(new BorderLayout());
        JBRadioButton jbRadioButton = new JBRadioButton("hola");
        jbRadioButton.setActionCommand("hola");
        jbRadioButton.setSelected(true);
        JBRadioButton jbRadioButton2 = new JBRadioButton("test2\ntest3\ntest4");
        jbRadioButton2.setActionCommand("test");
        group.add(jbRadioButton);
        group.add(jbRadioButton2);
        dialogPanel.add(jbRadioButton, BorderLayout.NORTH);
        dialogPanel.add(jbRadioButton2, BorderLayout.SOUTH);
//        dialogPanel.add(group, BorderLayout.CENTER);
        return dialogPanel;
    }

    String getSelectedOption() {
        return group.getSelection().getActionCommand();
    }
}
