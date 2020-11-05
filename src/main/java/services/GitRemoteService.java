package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GitRemoteService {
    private String remoteUrl;

    public void obtainRemoteUrlFromCmd() {
        final ProcessBuilder pBuilder = new ProcessBuilder("cmd.exe", "/c", "git config --get remote.origin.url");
        pBuilder.directory(new File("C:\\Users\\Dell\\Documents\\UCB\\10mo Semestre\\Seminario de Grado\\highlightWithDiffPoC"));
        try {
            final Process process = pBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            ArrayList<String> strs = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                strs.add(line);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
