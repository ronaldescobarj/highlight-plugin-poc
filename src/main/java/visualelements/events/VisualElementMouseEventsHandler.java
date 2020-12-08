package visualelements.events;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.JBPopup;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VisualElementMouseEventsHandler extends MouseAdapter {

    JBPopup jbPopup;
    Editor editor;

    public VisualElementMouseEventsHandler(JBPopup jbPopup, Editor editor) {
        this.jbPopup = jbPopup;
        this.editor = editor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        jbPopup.showInBestPositionFor(editor);
    }
}
