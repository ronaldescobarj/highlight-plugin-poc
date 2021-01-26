package editorevents;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.project.Project;
import models.Data;
import org.jetbrains.annotations.NotNull;
import services.EditorService;
import visualelements.VisualElementsUtils;

import java.util.List;
import java.util.Map;

public class OnEditorMouse implements EditorMouseListener {
    @Override
    public void mouseEntered(@NotNull EditorMouseEvent event) {
        Editor editor = event.getEditor();
        Project project = editor.getProject();
        EditorService editorService = project.getService(EditorService.class);
        Map<Integer, List<Data>> diffMap = editorService.getDiffMap();
        if (diffMap != null) {
            new VisualElementsUtils().addVisualElements(editor, diffMap);
        }
    }

}
