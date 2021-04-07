package editorevents;

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import difflogic.DiffMapGenerator;
import difflogic.DiffModifications;
import models.Data;
import models.EditorData;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jetbrains.annotations.NotNull;
import services.EditorService;
import visualelements.VisualElementsUtils;

import java.util.*;

public class OnEditorOpen implements EditorFactoryListener {

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        EditorService editorService = editor.getProject().getService(EditorService.class);
        new VisualElementsUtils().registerEditorToCoverLayerManager(editor);
        if (editorService.editorIsOnMap(editor)) {
            EditorData editorData = editorService.getEditorData(editor);
            Map<Integer, List<Data>> changes = new DiffMapGenerator().generateChangesMapForEditor(editor, editorData.getSourceCommit(), editorData.getDestinationCommit());
            editorData.setActive(true);
            editorData.setChanges(changes);
            editorService.setEditorWithData(editor, editorData);
        } else {
            RevCommit latestCommitWithModifications = new DiffModifications().getCommitWithLatestModification(editor);
            RevCommit previousCommit = latestCommitWithModifications.getParents()[0];
            Map<Integer, List<Data>> changes = new DiffMapGenerator().generateChangesMapForEditor(editor, previousCommit, latestCommitWithModifications);
            EditorData editorData = new EditorData(changes, true, previousCommit, latestCommitWithModifications);
            editorService.setEditorWithData(editor, editorData);
        }
//        Map<Integer, List<Data>> diffMap = new DiffMapGenerator().generateHighlightMapForEditor(editor);
//        editorService.setDiffMap(diffMap);
//        editorService.setLastOpenedEditor(editor);
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) { }
}
