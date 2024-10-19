package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;


/**
 *
 * */
public class CheckOut {
    private static final File CWD = Repository.CWD;
    private static final File BLOBS_DIR = Repository.BLOBS_DIR;
    private static final File REFS_DIR = Repository.REFS_DIR;
    private static final File CURRENT_DIR = Repository.CURRENT_DIR;
    private static final File COMMITS_DIR = Repository.COMMITS_DIR;
    private static String HEAD="HEAD";

    public static void checkOutFile(File sourceFile, File targetFile) {
        try {
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public static void changeBranch(String branchName) {
        setCurrentDir(branchName);

        copyFileFromBlobs(branchName);
        setTheHEAD(branchName);
    }

    private static void setTheHEAD(String branchName){
        File headCommit = Utils.join(COMMITS_DIR, HEAD);
        File branch =Utils.join(REFS_DIR, branchName);
        try {
            Files.copy(branch.toPath(), headCommit.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void copyFileFromBlobs(String branchName) {
        Utils.cleanDic(CWD);
        File current = Utils.join(REFS_DIR, branchName);
        Commit branch = Utils.readObject(current, Commit.class);
        Map<String, String> blobs = branch.blobs();
        Utils.copyFromSource(blobs, CWD, BLOBS_DIR);
    }

    private static void setCurrentDir(String branchName) {
        Utils.cleanDic(CURRENT_DIR);
        File current = Utils.join(CURRENT_DIR, branchName);
        try {
            current.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
