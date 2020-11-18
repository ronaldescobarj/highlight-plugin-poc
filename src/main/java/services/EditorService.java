package services;

import models.DiffRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditorService {
    List<DiffRow> diffsOfLastOpenedEditor;
    Map<Integer, String> diffMap;

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
