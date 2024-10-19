package gitlet;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 *
 * */
public class Remove {
    private static final File CWD = Repository.CWD;
    private static final File ADD_DIR = Repository.ADDED_DIR;
    private static final File REMOVED_DIR = Repository.REMOVED_DIR;
    private static final File BLOBS_DIR = Repository.BLOBS_DIR;

    /**
     * 将目标文件从ADD_DIR文件夹里面删除
     */
    public static void unStagedAddedFile(String fileName) {
        File addedFile = Utils.join(ADD_DIR, fileName);
        addedFile.delete();
    }

    /**
     * 将目标文件复制一份到REMOVED_DIR
     */
    public static void copyCWDToRemoval(String fileName) {
        String SHA1 = Commit.getHEAD().blobs().get(fileName);
        File cwdFileInBLOBS_DIR = Utils.join(BLOBS_DIR, SHA1);
        File removealFile = Utils.join(REMOVED_DIR, fileName);
        //复制到removed文件夹
        try {
            Files.copy(cwdFileInBLOBS_DIR.toPath(), removealFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除在CWD的目标文件（如何还存在）
     */
    public static void deleteCWDFile(String fileName) {
        File cwdFile = Utils.join(CWD, fileName);
        if(cwdFile.exists()) {
            cwdFile.delete();
        }
    }
}
