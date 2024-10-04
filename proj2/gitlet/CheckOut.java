package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class CheckOut {
    private static final File CWD= Repository.CWD;
    private static final File CURRENT_DIR=Repository.CURRENT_DIR;

    public static void checkOutTheFile(Commit commit, String fileName){
        File file=commit.findFileInBlobs(fileName);
        if(file==null){
            Utils.message("File does not exist in that commit.");
            return;
        }
        File targerFile=Utils.join(CWD,fileName);
        try {
            Files.copy(file.toPath(),targerFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
    public static void changeBranch(String name){
        cleanCURRENT_DIR();
        File current=Utils.join(CURRENT_DIR,name);
        try {
            current.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void cleanCURRENT_DIR(){
        List<String> filesInCURRENT_DIR=Utils.plainFilenamesIn(CURRENT_DIR);
        for (String name:filesInCURRENT_DIR) {
            File file=Utils.join(CURRENT_DIR,name);
            file.delete();
        }
    }
}
