package editorevents;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import difflogic.DiffMapGenerator;
import models.ModificationData;
import org.jetbrains.annotations.NotNull;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import services.EditorService;
import visualelements.VisualElementsUtils;

import java.util.*;

public class OnEditorOpen implements EditorFactoryListener {

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        new VisualElementsUtils().registerEditorToCoverLayerManager(editor);
        Map<Integer, ModificationData> diffMap = new DiffMapGenerator().generateHighlightMapForEditor(editor);
        EditorService editorService = editor.getProject().getService(EditorService.class);
        editorService.setDiffMap(diffMap);
        editorService.setLastOpenedEditor(editor);
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) { }
}