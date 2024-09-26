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
     * staged存储地址
     */
    public static final File STAGED_DIR = Repository.STAGED_DIR;
    /**
     * blobs存储地址
     */
    public static final File BLOBS_DIR = Repository.BLOBS_DIR;

    /**
     * 添加文件的副本到staged文件夹里面
     */
    public static void addStageFile(File file) {
        File staged = Utils.join(STAGED_DIR,file.getName());//staged文件夹的备份
        String sha1=Utils.sha1(file.toString());
        File blobs = Utils.join(BLOBS_DIR,sha1);//blobs文件夹的备份（用sha1码）
        try {
            Files.copy(file.toPath(), staged.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
            Files.copy(file.toPath(), blobs.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            throw Utils.error("文件复制出现问题");
        }
    }

}
