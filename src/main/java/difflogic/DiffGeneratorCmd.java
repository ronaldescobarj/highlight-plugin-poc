package difflogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DiffGeneratorCmd implements IDiffGenerator {
    public boolean generateDiff() {
        boolean response = true;
        //final ProcessBuilder pBuilder = new ProcessBuilder("cmd.exe", "/c", "java -jar \"C:\\Users\\Dell\\Documents\\UCB\\10mo Semestre\\Seminario de Grado\\pluginPoC\\libs\\at.aau.softwaredynamics.runner-1.0-SNAPSHOT-jar-with-dependencies.jar\" -src \"C:\\Users\\Dell\\Documents\\UCB\\10mo Semestre\\Seminario de Grado\\diffTool\\IJM\\Test_old.java\" -dst \"C:\\Users\\Dell\\Documents\\UCB\\10mo Semestre\\Seminario de Grado\\diffTool\\IJM\\Test.java\" -c None -m IJM -w FS -g OTG");
        final ProcessBuilder pBuilder = new ProcessBuilder("cmd.exe", "/c", "java -jar \"C:\\Users\\Dell\\Documents\\UCB\\10mo Semestre\\Seminario de Grado\\pluginPoC\\libs\\at.aau.softwaredynamics.runner-1.0-SNAPSHOT-jar-with-dependencies.jar\" -src C:\\Users\\Dell\\IdeaProjects\\highlightTest\\src\\com\\company\\test_old.simple -dst C:\\Users\\Dell\\IdeaProjects\\highlightTest\\src\\com\\company\\test.simple -c None -m IJM -w FS -g OTG");
        pBuilder.directory(new File("C:\\Users\\Dell\\Documents\\outputs"));
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
            response = false;
        }
        return response;
    }
}
