package models.actions;

import models.DiffRow;
import models.actions.ModificationData;
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
        this.previousLine = diffRow.getSrcStart();
    }

    @Override
    public String renderData() {
        return "<b>Author username:</b> " + author.getName() + "<br>" +
                "<b>Author email:</b> " + author.getEmailAddress() + "<br>" +
                "<b>Commit datetime:</b> " + getDateTimeString() + "<br>";
    }

    @Override
    public String getType() {
        return "MOV";
    }
}
