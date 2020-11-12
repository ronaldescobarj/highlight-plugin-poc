package services;

import models.DiffRow;

import java.util.ArrayList;

public class EditorService {
    ArrayList<DiffRow> diffsOfLastOpenedEditor;

    public void setDiffsOfLastOpenedEditor(ArrayList<DiffRow> diffs) {
        this.diffsOfLastOpenedEditor = diffs;
    }

    public ArrayList<DiffRow> getDiffsOfLastOpenedEditor() {
        return diffsOfLastOpenedEditor;
    }

}
