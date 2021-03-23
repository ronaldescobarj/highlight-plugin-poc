package models.actions;

import models.DiffRow;
import models.actions.ModificationData;
import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;

public class UpdatedMultipleTimes extends ModificationData {
    String previousContent;
    int amountOfTimes;

    public UpdatedMultipleTimes(PersonIdent author, LocalDateTime commitDate) {
        this.author = author;
        this.dateTime = commitDate;
    }

    @Override
    public void setAdditionalData(String... additionalData) {
        this.previousContent = "temp";
    }

    @Override
    public String renderData() {
        return "";
    }

    @Override
    public String getType() {
        return "UPD_MULTIPLE_TIMES";
    }
}
