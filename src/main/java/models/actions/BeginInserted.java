package models.actions;

import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;

public class BeginInserted extends ModificationData {
    int lineEndOfBlock;

    public BeginInserted(PersonIdent author, LocalDateTime commitDate, long startOffset, long endOffset) {
        super();
        this.author = author;
        this.dateTime = commitDate;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    @Override
    public void setAdditionalData(String... additionalData) {
        this.lineEndOfBlock = Integer.valueOf(additionalData[0]);
    }

    @Override
    public String renderData() {
        return isOnParent ? "<b>BEGIN INSERTED<br>Commit info<br>Author username:</b> " + author.getName() + "<br>" +
                "<b>Author email:</b> " + author.getEmailAddress() + "<br>" +
                "<b>Commit datetime:</b> " + getDateTimeString() + "<br>" :
                "This was modified before<br>" + getDateTimeString();
    }

    @Override
    public String getType() {
        return "BEGIN_INS";
    }
}
