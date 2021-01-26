package models;

import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;

public class Inserted extends ModificationData {
    String tempMetadata;

    public Inserted(PersonIdent author, LocalDateTime commitDate) {
        this.author = author;
        this.dateTime = commitDate;
    }

    @Override
    public void setAdditionalData(DiffRow diffRow) {
        this.tempMetadata = "temp";
    }

    @Override
    public String renderData() {
        return author.getName() + "<br>" +
                author.getEmailAddress() + "<br>" +
                getDateTimeString() + "<br>";
    }

    @Override
    public String getType() {
        return "INS";
    }
}
