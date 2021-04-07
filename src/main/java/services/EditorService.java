package services;

import com.intellij.openapi.editor.Editor;
import models.Data;
import models.EditorData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditorService {
    private Map<Editor, EditorData> editors;
    private Map<Integer, List<Data>> diffMap = null;
    private Editor lastOpenedEditor;

    public void initialize() {
        editors = new HashMap<>();
    }

    public boolean editorIsOnMap(Editor editor) {
        return editors.containsKey(editor);
    }

    public void setEditorWithData(Editor editor, EditorData editorData) {
        editors.put(editor, editorData);
    }

    public void setActiveEditor(Editor editor) {
        for (Map.Entry<Editor, EditorData> editorsEntry: editors.entrySet()) {
            EditorData editorData = editorsEntry.getValue();
            editorData.setActive(editorsEntry.getKey() == editor);
            editors.put(editorsEntry.getKey(), editorData);
        }
    }

    public EditorData getEditorData(Editor editor) {
        return editors.get(editor);
    }

    public Map<Integer, List<Data>> getActiveEditorChanges() {
        return editors.values().stream().filter(EditorData::isActive).findFirst().get().getChanges();
    }

    public void setLastOpenedEditor(Editor editor) {
        this.lastOpenedEditor = editor;
    }

    public Editor getLastOpenedEditor() {
        return lastOpenedEditor;
    }

    public void setDiffMap(Map<Integer, List<Data>> diffMap) {
        this.diffMap = diffMap;
    }

    public Map<Integer, List<Data>> getDiffMap() {
        return diffMap;
    }

}
