package models;

import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;

public class Deleted extends ModificationData {
    String deletedCode;

    public Deleted(PersonIdent author, LocalDateTime commitDate) {
        this.author = author;
        this.dateTime = commitDate;
    }

    @Override
    public void setAdditionalData(DiffRow diffRow) {
        this.deletedCode = "temp";
    }

    @Override
    public String renderData() {
        return author.getName() + "<br>" +
                author.getEmailAddress() + "<br>" +
                getDateTimeString() + "<br>";
    }

    @Override
    public String getType() {
        return "DEL";
    }
}
