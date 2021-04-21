package fileexplorer;

import com.intellij.ide.FileIconProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import javalanguage.JavaIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ChangesIconProvider implements FileIconProvider {
    @Override
    public @Nullable Icon getIcon(@NotNull VirtualFile file, int flags, @Nullable Project project) {
        if (file.getName().equals("Bicycle.java")) {
            return JavaIcons.TEST;
        } else {
            return JavaIcons.FILE;
        }
//        return null;
    }
}
