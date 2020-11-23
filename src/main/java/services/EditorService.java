package services;

import com.intellij.openapi.editor.Editor;
import models.DiffRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditorService {
    List<DiffRow> diffsOfLastOpenedEditor;
    Map<Integer, String> diffMap;
    boolean isGenerated = false;
    Editor lastOpenedEditor;

    public void setLastOpenedEditor(Editor editor) {
        this.lastOpenedEditor = editor;
    }

    public Editor getLastOpenedEditor() {
        return lastOpenedEditor;
    }

    public void setIsGenerated(boolean isGenerated) {
        this.isGenerated = isGenerated;
    }

    public boolean getIsGenerated() {
        return isGenerated;
    }

    public void setDiffsOfLastOpenedEditor(ArrayList<DiffRow> diffs) {
        this.diffsOfLastOpenedEditor = diffs;
    }

    public List<DiffRow> getDiffsOfLastOpenedEditor() {
        return diffsOfLastOpenedEditor;
    }

    public void setDiffMap(Map<Integer, String> diffMap) {
        this.diffMap = diffMap;
    }

    public Map<Integer, String> getDiffMap() {
        return diffMap;
    }

}
