package gitlet;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class Status {

    private static final File ADDED_DIR = Repository.ADDED_DIR;
    private static final File REMOVED_DIR = Repository.REMOVED_DIR;

    public static void printBranches() {
        System.out.println("=== Branches ===");

        System.out.println();
    }

    public static void printStaged() {
        softFileInDicAndPrint(ADDED_DIR, "=== Staged Files ===");
    }

    public static void printRemoved() {
        softFileInDicAndPrint(REMOVED_DIR, "=== Removed Files ===");
    }

    private static void softFileInDicAndPrint(File dir, String topic) {
        List<String> fileNamesInDir = Utils.plainFilenamesIn(dir);
        Collections.sort(fileNamesInDir);
        System.out.println(topic);
        for (String name : fileNamesInDir) {
            System.out.println(name);
        }
        System.out.println();
    }

    public static void printModifications() {
        System.out.println("=== Modifications Not Staged For Commit ===");

        System.out.println();
    }

    public static void printUntracked() {
        System.out.println("=== Untracked Files ===");

        System.out.println();
    }
}
