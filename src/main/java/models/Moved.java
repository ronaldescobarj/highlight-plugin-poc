package models;

import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;

public class Moved extends ModificationData {
    int previousLine;

    public Moved(PersonIdent author, LocalDateTime commitDate) {
        this.author = author;
        this.dateTime = commitDate;
    }

    @Override
    public void setAdditionalData(DiffRow diffRow) {
        this.previousLine = diffRow.srcStart;
    }

    @Override
    public String renderData() {
        return author.getName() + "<br>" +
                author.getEmailAddress() + "<br>" +
                getDateTimeString() + "<br>";
    }

    @Override
    public String getType() {
        return "MOV";
    }
}
