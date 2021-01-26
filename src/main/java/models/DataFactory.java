package models;

import com.intellij.psi.PsiElement;
import org.eclipse.jgit.lib.PersonIdent;
import visualelements.*;

import javax.swing.*;
import java.time.LocalDateTime;

public class DataFactory {
    public static Data createData(String type, PersonIdent author, LocalDateTime commitDate) {
        switch (type) {
            case "UPD":
                return new Updated(author, commitDate);
            case "INS":
                return new Inserted(author, commitDate);
            case "MOV":
                return new Moved(author, commitDate);
            case "DEL":
                return new Deleted(author, commitDate);
            case "UPD_MULTIPLE_TIMES":
                return new UpdatedMultipleTimes(author, commitDate);
            case "EXTRACTED_METHOD":
                return new ExtractedMethod();
            default:
                return null;
        }
    }

    public static ModificationData createModificationData(String type, PersonIdent author, LocalDateTime commitDate) {
        switch (type) {
            case "UPD":
                return new Updated(author, commitDate);
            case "INS":
                return new Inserted(author, commitDate);
            case "MOV":
                return new Moved(author, commitDate);
            case "DEL":
                return new Deleted(author, commitDate);
            case "UPD_MULTIPLE_TIMES":
                return new UpdatedMultipleTimes(author, commitDate);
            default:
                return null;
        }
    }

}
