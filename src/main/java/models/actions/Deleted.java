package models.actions;

import models.DiffRow;
import models.actions.ModificationData;
import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;

public class Deleted extends ModificationData {
    String deletedCode;

    public Deleted(PersonIdent author, LocalDateTime commitDate, String... additionalData) {
        this.author = author;
        this.dateTime = commitDate;
        this.deletedCode = additionalData[0];
    }

    @Override
    public void setAdditionalData(String... additionalData) {
        this.deletedCode = additionalData[0];
    }

    @Override
    public String renderData() {
        return isOnParent ? "<b>Author username:</b> " + author.getName() + "<br>" +
                "<b>Author email:</b> " + author.getEmailAddress() + "<br>" +
                "<b>Commit datetime:</b> " + getDateTimeString() + "<br>" +
                "<b>Deleted code: </b>" + deletedCode :
                "This was modified before<br>" + getDateTimeString();
    }

    @Override
    public String getType() {
        return "DEL";
    }
}
