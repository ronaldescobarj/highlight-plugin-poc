package cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CmdCommandRunner {
    public static void runCommand(String command, String directory) throws IOException {
        String comando = "C:\\refactoringMiner\\bin\\RefactoringMiner -c ";
        final ProcessBuilder pBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        pBuilder.directory(new File(directory));
        try {
            final Process process = pBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            List<String> strs = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                strs.add(line);
            }
        } catch(IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
