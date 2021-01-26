package models;

import org.eclipse.jgit.lib.PersonIdent;

import java.time.LocalDateTime;

public class ExtractedMethod extends RefactoringData {

    public ExtractedMethod() {
    }

    @Override
    public String renderData() {
        return "Extracted method" + "<br>";
    }

    @Override
    public String getType() {
        return "EXTRACTED_METHOD";
    }

}
