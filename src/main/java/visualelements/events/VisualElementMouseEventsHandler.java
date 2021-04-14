package visualelements.events;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.awt.RelativePoint;
import models.Data;
import models.refactorings.AddParameter;
import models.refactorings.PushDownAttribute;
import models.refactorings.PushDownMethod;
import models.refactorings.RenameParameter;
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
        if (action instanceof RenameParameter) {
            RenameParameter renameParameter = (RenameParameter) action;
            editor.getSelectionModel().setSelection(renameParameter.getStartOffset(), renameParameter.getEndOffset());
        } else if (action instanceof AddParameter) {
            AddParameter addParameter = (AddParameter) action;
            editor.getSelectionModel().setSelection(addParameter.getStartOffset(), addParameter.getEndOffset());
        } else if (action instanceof PushDownAttribute) {
            PushDownAttribute pushDownAttribute = (PushDownAttribute) action;
            VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(pushDownAttribute.getParentClassUrl());
            OpenFileDescriptor descriptor = new OpenFileDescriptor(editor.getProject(), file);
            FileEditorManager.getInstance(editor.getProject()).openEditor(descriptor, true);
        } else if (action instanceof PushDownMethod) {
            PushDownMethod pushDownMethod = (PushDownMethod) action;
            VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(pushDownMethod.getParentClassUrl());
            OpenFileDescriptor descriptor = new OpenFileDescriptor(editor.getProject(), file);
            FileEditorManager.getInstance(editor.getProject()).openEditor(descriptor, true);
        }
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
