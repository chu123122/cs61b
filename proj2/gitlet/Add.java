package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Add {
    public static final File BLOBS_DIR = Repository.BLOBS_DIR;
    public static int number=0;

    /** 添加文件的副本到blobs文件夹里面 */
    public void addStageFile(File file){
        try {
            File blob=Utils.join(BLOBS_DIR,Integer.toString(number));
            Files.copy(file.toPath(),blob.toPath(), StandardCopyOption.COPY_ATTRIBUTES);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        number++;
        file.delete();
    }

}
