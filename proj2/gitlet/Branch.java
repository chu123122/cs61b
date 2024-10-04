package gitlet;

import org.checkerframework.checker.guieffect.qual.UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Branch {

    public static final File REFS_DIR=Repository.REFS_DIR;
    public static final File COMMITS_DIR=Repository.COMMITS_DIR;
    public static final String HEAD="HEAD";

    /**
     * 将HEAD引用文件复制一份到REFS_DIR文件夹里，重命名为分支名称
     * */
    public static void createNewBranch(String name){
        File headCommit = Utils.join(COMMITS_DIR, HEAD);
        File newBranch=Utils.join(REFS_DIR,name);
        try {
            Files.copy(headCommit.toPath(), newBranch.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void rmTheBranch(String name){
        List<String> filesInRefs=Utils.plainFilenamesIn(REFS_DIR);
        for (String fileName:filesInRefs) {
            if(fileName.equals(name)){
                File file=Utils.join(REFS_DIR,fileName);
                file.delete();
                return;
            }
        }
    }

    public static boolean checkHaveTheBranch(String name){
        List<String> filesInRefs=Utils.plainFilenamesIn(REFS_DIR);
        for (String fileName:filesInRefs) {
            if(fileName.equals(name))return true;
        }
        return false;
    }

}
