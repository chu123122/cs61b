package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CheckOut {
    private static final File CWD= Repository.CWD;

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
}
