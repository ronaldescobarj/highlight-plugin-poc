package models.actions;

import models.Data;
import models.DiffRow;
import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;
import java.util.Date;

public abstract class ModificationData implements Data {
    PersonIdent author;
    LocalDateTime dateTime;

    public abstract void setAdditionalData(String... additionalData);

    public String getDateTimeString() {
        return getDayOfMonth() + "/" + getMonth() + "/" +
                dateTime.getYear() + " " + getHour() + ":" +
                getMinute();
    }

    private String getMinute() {
        return dateTime.getMinute() >= 10 ? String.valueOf(dateTime.getMinute()) : "0" + dateTime.getMinute();
    }

    private String getHour() {
        return dateTime.getHour() >= 10 ? String.valueOf(dateTime.getHour()) : "0" + dateTime.getHour();
    }

    private String getMonth() {
        return dateTime.getMonthValue() >= 10 ? String.valueOf(dateTime.getMonthValue()) : "0" + dateTime.getMonthValue();
    }

    private String getDayOfMonth() {
        return dateTime.getDayOfMonth() >= 10 ? String.valueOf(dateTime.getDayOfMonth()) : "0" + dateTime.getDayOfMonth();
    }
}
