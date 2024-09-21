package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * 提交一个文件到staged文件夹
 */
public class Add {
    /**
     * 存储地址
     */
    public static final File STAGED_DIR = Repository.STAGED_DIR;

    /**
     * 添加文件的副本到staged文件夹里面
     */
    public void addStageFile(File file) {
        try {
            File blob = Utils.join(STAGED_DIR, file.getName());
            Files.copy(file.toPath(), blob.toPath(), StandardCopyOption.COPY_ATTRIBUTES);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        file.delete();
    }

}
