package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Find {
    private static final File COMMITS_DIR = Repository.COMMITS_DIR;


    public static List<String> findTheCommitsSHA1(String message) {
        List<String> fileNames = Utils.plainFilenamesIn(COMMITS_DIR);
        List<String> findResult = new ArrayList<>();
        for (String fileName : fileNames) {
            File file = Utils.join(COMMITS_DIR, fileName);
            Commit commit = Utils.readObject(file, Commit.class);
            if (commit.message().contains(message) && !file.getName().equals("HEAD"))
                findResult.add(commit.SHA1());
        }
        return findResult;
    }

    public static void printfTheFind(String sha1) {
        System.out.println(sha1);
    }
}
