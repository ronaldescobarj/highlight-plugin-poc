package models;

import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;
import java.util.Date;

public abstract class ModificationData implements Data {
    PersonIdent author;
    LocalDateTime dateTime;

    public abstract void setAdditionalData(DiffRow diffRow);

    public String getDateTimeString() {
        return dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue() + "/" +
                dateTime.getYear() + " " + dateTime.getHour() + ":" +
                dateTime.getMinute();
    }
}
