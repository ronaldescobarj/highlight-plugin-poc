package visualelements.events;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.ui.awt.RelativePoint;
import models.Data;
import visualelements.PopupUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VisualElementMouseEventsHandler extends MouseAdapter {

    JBPopup jbPopup;
    Editor editor;
    Data action;

    public VisualElementMouseEventsHandler(Editor editor, Data action) {
        this.editor = editor;
        this.action = action;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        System.out.println("clicked");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        this.jbPopup = PopupUtils.createPopup(action);
        jbPopup.show(new RelativePoint(e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        jbPopup.cancel();
    }
}
