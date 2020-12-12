package models;

import org.eclipse.jgit.lib.PersonIdent;

public class ModificationData {
    String modification;
    PersonIdent author;
    String date;

    public ModificationData(String modification, PersonIdent author) {
        this.modification = modification;
        this.author = author;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public String getAuthorEmail() {
        return author.getEmailAddress();
    }

    public void setModification(String modification) {
        this.modification = modification;
    }

    public String getModification() {
        return modification;
    }

}
