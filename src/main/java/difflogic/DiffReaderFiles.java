package difflogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class DiffReaderFiles {
    public ArrayList<DiffRow> getDiffList(Path filePath) {
        String line = "";
        String cvsSplitBy = ";";
        ArrayList<DiffRow> diffs = new ArrayList<>();
        String filePathString = filePath.toString();
        boolean firstTime = true;
        try (BufferedReader br = new BufferedReader(new FileReader(filePathString))) {
            while ((line = br.readLine()) != null) {
                String[] diff = line.split(cvsSplitBy);
                if (!firstTime) {
                    diffs.add(new DiffRow(diff[0], diff[1], diff[2], diff[3], Integer.parseInt(diff[4]), Integer.parseInt(diff[5]), Integer.parseInt(diff[6]), Integer.parseInt(diff[7]), diff[8]));
                }
                firstTime = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diffs;
    }
}
