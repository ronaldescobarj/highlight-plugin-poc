package refactoringminer;

import cmd.CmdCommandRunner;

import java.io.IOException;

public class RefactoringMinerCmd {

    public void runRefactoringMiner(String projectPath, String commitSha) {
        String command = ".\\RefactoringMiner -c " + projectPath + " " + commitSha;
        String refactoringMinerPath = "C:\\refactoringMiner\\bin";
        try {
            CmdCommandRunner.runCommand(command, refactoringMinerPath);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

//    private String getFolderNameFromPath(String projectPath) {
//        String[] pathParts = projectPath.split("/");
//        return pathParts[pathParts.length - 1];
//    }
}
