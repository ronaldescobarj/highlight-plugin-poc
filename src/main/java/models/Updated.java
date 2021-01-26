package models;

import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;

public class Updated extends ModificationData {

    String previousContent;

    public Updated(PersonIdent author, LocalDateTime commitDate) {
        this.author = author;
        this.dateTime = commitDate;
    }

    @Override
    public void setAdditionalData(DiffRow diffRow) {
        this.previousContent = "temp";
    }

    @Override
    public String renderData() {
        return author.getName() + "<br>" +
                author.getEmailAddress() + "<br>" +
                getDateTimeString() + "<br>";
    }

    @Override
    public String getType() {
        return "UPD";
    }
}
