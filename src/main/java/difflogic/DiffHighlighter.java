package difflogic;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.tree.IElementType;
import javalanguage.psi.JavaTypes;
import models.DiffRow;
import models.ModificationData;
import services.EditorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiffHighlighter {
    Map<Integer, ModificationData> diffMap;

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
//        ArrayList<DiffRow> diffs = editorService.getDiffsOfLastOpenedEditor();z
        diffMap = editorService.getDiffMap();
//        DiffMapper diffMapper = new DiffMapper(diffs);
//        diffMap = diffMapper.createDiffMap();
    }

    public IElementType getLineHighlight(int line) {
        ModificationData modification = diffMap.get(line);
        String action = modification != null ? modification.getModification() : null;
        if (action == null) {
            return JavaTypes.NOTMODIFIED;
        } else {
            switch (action) {
                case "INS":
                    return JavaTypes.INSERTED;
                case "UPD":
                    return JavaTypes.UPDATED;
                case "UPD_MULTIPLE_TIMES":
                    return JavaTypes.UPDATEDMULTIPLETIMES;
                case "MOV":
                    return JavaTypes.MOVED;
                default:
                    return JavaTypes.NOTMODIFIED;
            }
        }
    }
}
