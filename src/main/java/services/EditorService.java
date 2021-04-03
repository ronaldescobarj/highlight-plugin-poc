package services;

import com.intellij.openapi.editor.Editor;
import models.Data;
import models.DiffRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditorService {
    Map<Integer, List<Data>> diffMap = null;
    Editor lastOpenedEditor;

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
