package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Status {

    private static final File CWD=Repository.CWD;
    private static final File ADDED_DIR = Repository.ADDED_DIR;
    private static final File REFS_DIR=Repository.REFS_DIR;
    private static final File REMOVED_DIR = Repository.REMOVED_DIR;

    public static void printBranches() {
        System.out.println("=== Branches ===");
        List<String> fileNamesInDir = Utils.plainFilenamesIn(REFS_DIR);
        Collections.sort(fileNamesInDir);
        for (String name : fileNamesInDir) {
            if(name.equals(Repository.currentBranch))
                System.out.print("*");
            System.out.println(name);
        }
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
//        List<String> filesInModOrDe=getModifiedFiles();
//        filesInModOrDe.addAll(getDeleteFiles());//获取所有文件
//        Collections.sort(filesInModOrDe);
//        for (String name : filesInModOrDe) {
//            System.out.println(name);
//        }
        System.out.println();
    }

    private static List<String> getModifiedFiles(){
        List<String> fileNamesInMod=new ArrayList<>();

        return fileNamesInMod;
    }
    private static List<String> getDeleteFiles(){
        List<String> fileNamesInDel=new ArrayList<>();
        List<String> fileNamesInADD=Utils.plainFilenamesIn(ADDED_DIR);
        List<String> fileNamesInCWD=Utils.plainFilenamesIn(CWD);
        for (String fileName:fileNamesInADD) {              //对于添加到added文件夹后又在CWD中删除的文件
            if(!fileNamesInCWD.contains(fileName)){
                fileNamesInDel.add(fileName+" (deleted)");
            }
        }
        return fileNamesInDel;
    }

    public static void printUntracked() {
        System.out.println("=== Untracked Files ===");

        System.out.println();
    }
}
