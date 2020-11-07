package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GitRemoteService {
    private String remoteUrl;
    private String projectPath;
    private String repoOwner;
    private String repoName;

    public void obtainRemoteUrlFromCmd() {
        final ProcessBuilder pBuilder = new ProcessBuilder("cmd.exe", "/c", "git config --get remote.origin.url");
        String myPath = projectPath.replace("/", "\\");
        pBuilder.directory(new File(myPath));
        try {
            final Process process = pBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            ArrayList<String> strs = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                strs.add(line);
            }
            remoteUrl = strs.get(0);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void obtainRepoData() {
        String[] urlParts = remoteUrl.split("/");
        repoOwner = urlParts[3];
        repoName = urlParts[4].replaceAll(".git", "");
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public String getRepoOwner() {
        return repoOwner;
    }

    public String getRepoName() {
        return repoName;
    }
}
