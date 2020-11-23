package oneditoropen;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import org.jetbrains.annotations.NotNull;

public class OnDocument implements DocumentListener {
    @Override
    public void beforeDocumentChange(@NotNull DocumentEvent event) {
        System.out.println("1");
    }

    @Override
    public void documentChanged(@NotNull DocumentEvent event) {
        System.out.println("1");
    }

    @Override
    public void bulkUpdateStarting(@NotNull Document document) {
        System.out.println("1");
    }

    @Override
    public void bulkUpdateFinished(@NotNull Document document) {
        System.out.println("1");
    }
}
