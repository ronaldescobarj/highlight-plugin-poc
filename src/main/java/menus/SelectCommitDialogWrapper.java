package menus;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.JBMenuItem;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBMenu;
import com.intellij.ui.components.JBRadioButton;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jetbrains.annotations.Nullable;
import utils.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
        addRadioButtons(dialogPanel);
        return dialogPanel;
    }

    private void addRadioButtons(JPanel dialogPanel) {
        for (RevCommit commit: commits) {
            JBRadioButton jbRadioButton = new JBRadioButton(buildCommitLabel(commit));
            jbRadioButton.setActionCommand(commit.getName());
            if (commits.get(0) == commit) {
                jbRadioButton.setSelected(true);
            }
            group.add(jbRadioButton);
            dialogPanel.add(jbRadioButton);
        }
    }

    private String buildCommitLabel(RevCommit commit) {
        Date date = commit.getAuthorIdent().getWhen();
        PersonIdent author = commit.getAuthorIdent();
        String parsedDate = new DateUtils().parseDate(date);
        return "<html><b>SHA:</b> " + commit.getName() +
                "<br><b>Author:</b> " + author.getName() + "&lt;" + author.getEmailAddress() + "&gt;" +
                "<br><b>Datetime:</b> " + parsedDate + "</html>";
    }

    public String getSelectedOption() {
        return group.getSelection().getActionCommand();
    }
}
