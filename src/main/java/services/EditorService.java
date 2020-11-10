package services;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import models.DiffRow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EditorService {
    Editor currentEditor;
    ArrayList<DiffRow> diffs;

    public void setDiffs(ArrayList<DiffRow> diffs) {
        this.diffs = diffs;
    }

    public ArrayList<DiffRow> getDiffs() {
        return diffs;
    }

}
