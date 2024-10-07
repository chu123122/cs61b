package gitlet;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Remove {
    private static final File CWD= Repository.CWD;
    private static final File ADD_DIR =Repository.ADDED_DIR;
    private static final File REMOVED_DIR=Repository.REMOVED_DIR;

    /**
     * 将目标文件从ADD_DIR文件夹里面删除
     * */
    public static void unStagedFile(String fileName){
        File stagedFile= Utils.join(ADD_DIR,fileName);
        stagedFile.delete();
    }

    /**
     * 将目标文件复制一份到REMOVED_DIR
     * */
    public static void copyCWDToRemoval(String fileName){
        File cwdFile=Utils.join(CWD,fileName);
        File removealFile=Utils.join(REMOVED_DIR,fileName);
        //复制到removed文件夹
        try { 
            Files.copy(cwdFile.toPath(),removealFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 删除在CWD的目标文件
     * */
    public static void deleteCWDFile(String fileName){
        File cwdFile=Utils.join(CWD,fileName);
        cwdFile.delete();
    }
}
