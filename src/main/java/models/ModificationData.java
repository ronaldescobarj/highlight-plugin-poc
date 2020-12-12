package models;

import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;
import java.util.Date;

public class ModificationData {
    String modification;
    PersonIdent author;
    LocalDateTime dateTime;

    public ModificationData(String modification, PersonIdent author) {
        this.modification = modification;
        this.author = author;
    }

    public ModificationData(String modification, PersonIdent author, LocalDateTime dateTime) {
        this.modification = modification;
        this.author = author;
        this.dateTime = dateTime;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public String getAuthorEmail() {
        return author.getEmailAddress();
    }

    public String getDateTimeString() {
        return dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue() + "/" +
                dateTime.getYear() + " " + dateTime.getHour() + ":" +
                dateTime.getMinute();
    }

    public void setModification(String modification) {
        this.modification = modification;
    }

    public String getModification() {
        return modification;
    }

}
