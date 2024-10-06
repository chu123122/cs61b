package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

public class Reset {

    private static final File CWD =Repository.CWD;
    private static final File COMMITS_DIR=Repository.COMMITS_DIR;
    private static final File BLOBS_DIR=Repository.BLOBS_DIR;
    private static final String HEAD="HEAD";

    public static void resetFromTheBranch(String commitID){
        rmCWD();
        copyBlobsToCWD(commitID);
        setHEAD(commitID);
    }

    /**
     * 更改HEAD为特定的提交
     * */
    private static void setHEAD(String commitID){
        File headCommit = Utils.join(COMMITS_DIR, HEAD);
        File commit=Utils.join(COMMITS_DIR,commitID);
        try {
            Files.copy(commit.toPath(), headCommit.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从特定的提交复制所有引用到CWD文件夹里
     * */
    private static void copyBlobsToCWD(String commitID){
        File commitFile=Utils.join(COMMITS_DIR,commitID);
        Commit commit=Utils.readObject(commitFile, Commit.class);
        Map<String,String> blobs=commit.blobs();
        for (String key: blobs.keySet()) {
            File file=Utils.join(BLOBS_DIR,blobs.get(key));
            File copyFile=Utils.join(CWD,key);
            try {
                Files.copy(file.toPath(),copyFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * 删除CWD文件夹里面的所有文件
     * */
    private static void rmCWD(){
        List<String> filesInCWD=Utils.plainFilenamesIn(CWD);
        for (String name:filesInCWD) {
            File file=Utils.join(CWD,name);
            file.delete();
        }
    }


}
