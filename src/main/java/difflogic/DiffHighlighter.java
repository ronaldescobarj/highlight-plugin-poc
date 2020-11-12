package difflogic;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.tree.IElementType;
import models.DiffRow;
import services.EditorService;
import simplelanguage.psi.SimpleTypes;

import java.util.ArrayList;
import java.util.HashMap;

public class DiffHighlighter {
    HashMap<Integer, String> diffMap;

    public DiffHighlighter() {
//        IDiffGenerator diffGenerator = new DiffGeneratorCmd();
//        boolean response = diffGenerator.generateDiff();
//
//        DiffGeneratedManagerFiles diffGeneratedManager = new DiffGeneratedManagerFiles();
//        Optional<Path> lfPath = diffGeneratedManager.getLatestGeneratedDiffPath();
//
//        DiffReaderFiles diffReaderFiles = new DiffReaderFiles();
//        ArrayList<DiffRow> diffs = diffReaderFiles.getDiffList(lfPath.get());
//
//        DiffMapper diffMapper = new DiffMapper(diffs);
//        diffMap = diffMapper.createDiffMap();
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        EditorService editorService = project.getService(EditorService.class);
        ArrayList<DiffRow> diffs = editorService.getDiffsOfLastOpenedEditor();
        DiffMapper diffMapper = new DiffMapper(diffs);
        diffMap = diffMapper.createDiffMap();
        System.out.println("test");
    }

    public IElementType getLineHighlight(int line) {
        String action = diffMap.get(line);
        if (action == null) {
            return SimpleTypes.NOTMODIFIED;
        } else {
            switch (action) {
                case "INS":
                    return SimpleTypes.INSERTED;
                case "UPD":
                    return SimpleTypes.UPDATED;
                case "MOV":
                    return SimpleTypes.MOVED;
                default:
                    return SimpleTypes.NOTMODIFIED;
            }
        }
    }
}
