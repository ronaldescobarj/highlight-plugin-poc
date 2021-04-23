package changes;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.tree.IElementType;
import difflogic.ModificationDataUtils;
import javalanguage.psi.JavaTypes;
import models.Data;
import org.apache.tools.ant.taskdefs.Java;
import services.EditorService;

import java.util.List;
import java.util.Map;

public class ChangesHighlighter {
    Map<Integer, List<Data>> changesMap;

    public ChangesHighlighter() {
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        EditorService editorService = project.getService(EditorService.class);
        changesMap = editorService.getActiveEditorChanges();
    }

    public IElementType getCharHighlight(int line, long currentOffset) {
        List<Data> actions = changesMap.get(line);
        if (actions != null) {
            for (Data action: actions) {
                if (currentOffset >= action.getStartOffset() && currentOffset <= action.getEndOffset()) {
                    if (action.getType().equals("ADD_PARAMETER")) {
                        return JavaTypes.INSERTED;
                    } else {
                        return JavaTypes.UPDATED;
                    }
                }
            }
        }
        return JavaTypes.NOTMODIFIED;
    }
}
