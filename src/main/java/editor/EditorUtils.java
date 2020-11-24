package editor;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class EditorUtils {

    public static String getFileName(Editor editor) {
        Document document = editor.getDocument();
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);
        return virtualFile.getName();
    }

    public static String getRelativePath(Editor editor) {
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(editor.getDocument());
        String filePath = virtualFile.getPath();
        Project project = editor.getProject();
        String projectPath = project.getBasePath();
        return filePath.replaceAll(projectPath, "");
    }

    public static String getCurrentFileContent(Editor editor) {
        Document document = editor.getDocument();
        return document.getText();
    }
}
