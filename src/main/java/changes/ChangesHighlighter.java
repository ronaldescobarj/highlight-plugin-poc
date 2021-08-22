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
        //todo que el buscar insert block se haga solo una vez si es posible
        if (existsInsertBlock()) {
            long startOffsetInsertBlock = getStartOffsetInsertBlock();
            long endOffsetInsertBlock = getEndOffsetInsertBlock();
            if (currentOffset >= startOffsetInsertBlock && currentOffset < endOffsetInsertBlock) {
                return JavaTypes.INSERTED;
            }
        }
        if (actions != null) {
            for (Data action: actions) {
                if (currentOffset >= action.getStartOffset() && currentOffset < action.getEndOffset()) {
                    if (isInsertion(action)) {
                        return JavaTypes.INSERTED;
                    } else if (isUpdate(action)) {
                        return JavaTypes.UPDATED;
                    } else if (isMove(action)) {
                        return JavaTypes.MOVED;
                    } else {
                        return JavaTypes.NOTMODIFIED;
                    }
                }
            }
        }
        return JavaTypes.NOTMODIFIED;
    }

    boolean existsInsertBlock() {
        return changesMap.entrySet().stream().anyMatch(pair -> pair.getValue().stream().anyMatch(action -> action.getType().equals("BEGIN_INS")));
    }

    long getStartOffsetInsertBlock() {
        for (Map.Entry<Integer, List<Data>> entry : changesMap.entrySet()) {
            List<Data> lineActions = entry.getValue();
            for (Data action : lineActions) {
                if (action.getType().equals(("BEGIN_INS"))) {
                    return action.getStartOffset();
                }
            }
        }
        return 1;
        //todo hacer funcional
        // return changesMap.entrySet().stream().filter(pair -> pair.getValue().stream().filter(action -> action.getType().equals("BEGIN_INS")).findFirst().get());
    }

    long getEndOffsetInsertBlock() {
        for (Map.Entry<Integer, List<Data>> entry : changesMap.entrySet()) {
            List<Data> lineActions = entry.getValue();
            for (Data action : lineActions) {
                if (action.getType().equals(("END_INS"))) {
                    return action.getEndOffset();
                }
            }
        }
        return 2;
        //todo hacer funcional
        // return changesMap.entrySet().stream().filter(pair -> pair.getValue().stream().filter(action -> action.getType().equals("BEGIN_INS")).findFirst().get());
    }



    private boolean isInsertion(Data action) {
        return action.getType().equals("INS") || action.getType().equals("ADD_PARAMETER") ||
                action.getType().equals("EXTRACTED_METHOD") ||
                action.getType().equals("EXTRACTED_METHOD_CALL") ||
                action.getType().equals("EXTRACTED_VARIABLE") ||
                action.getType().equals("EXTRACT_INTERFACE") ||
                action.getType().equals("EXTRACT_SUPERCLASS");
    }

    private boolean isUpdate(Data action) {
        return action.getType().equals("UPD") || action.getType().equals("RENAME_METHOD") ||
                action.getType().equals("RENAME_CLASS") || action.getType().equals("RENAME_VARIABLE") ||
                action.getType().equals("RENAME_PARAMETER") || action.getType().equals("RENAME_ATTRIBUTE") ||
                action.getType().equals("RENAME_ATTRIBUTE") ||
                action.getType().equals("CHANGE_VARIABLE_TYPE") ||
                action.getType().equals("EXTRACTED_VARIABLE_USAGE") ||
                action.getType().equals("CHANGE_PARAMETER_TYPE") ||
                action.getType().equals("CHANGE_RETURN_TYPE") ||
                action.getType().equals("CHANGE_ATTRIBUTE_TYPE");
    }

    private boolean isMove(Data action) {
        return action.getType().equals("MOV") || action.getType().equals("PULL_UP_METHOD") ||
                action.getType().equals("PULL_UP_ATTRIBUTE") ||
                action.getType().equals("PUSH_DOWN_METHOD") ||
                action.getType().equals("PUSH_DOWN_ATTRIBUTE") ||
                action.getType().equals("PUSH_DOWN_ATTRIBUTE") ||
                action.getType().equals("REORDER_PARAMETER");
    }
}
