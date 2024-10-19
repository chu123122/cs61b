package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * 提交一个文件到staged文件夹
 */
public class Add {
    /**
     * staged存储地址
     */
    public static final File STAGED_DIR = Repository.ADDED_DIR;
    public static final File REMOVED_DIR = Repository.REMOVED_DIR;
    /**
     * blobs存储地址
     */
    public static final File BLOBS_DIR = Repository.BLOBS_DIR;

    /**
     * 添加文件的副本到staged文件夹里面
     */
    public static void addStageFile(File file) {
        File staged = Utils.join(STAGED_DIR, file.getName());//staged文件夹的备份
        String sha1 = Utils.sha1(Utils.readContentsAsString(file));
        File blobs = Utils.join(BLOBS_DIR, sha1);//blobs文件夹的备份（用sha1码）
        try {
            Files.copy(file.toPath(), blobs.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw Utils.error("文件复制出现问题");
        }
        try {
            if(Commit.checkHaveTheSameFile(file.getName(),Commit.getHEAD()))return;//考虑到如果是恢复删除的相同文件
            Files.copy(file.toPath(), staged.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw Utils.error("文件复制出现问题");
        }
    }

    public static void deleteInREMOVED(String addName){
        List<String> fileInREMOVED=Utils.plainFilenamesIn(REMOVED_DIR);
        for (String name:fileInREMOVED) {
            File file=Utils.join(REMOVED_DIR,name);
            if(name.equals(addName)){
                file.delete();
            }
        }
    }

}
