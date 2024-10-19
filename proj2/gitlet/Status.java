package gitlet;

import java.io.File;
import java.util.*;

/**
 *
 */
public class Status {

    private static final File CWD = Repository.CWD;
    private static final File ADDED_DIR = Repository.ADDED_DIR;
    private static final File REFS_DIR = Repository.REFS_DIR;
    private static final File REMOVED_DIR = Repository.REMOVED_DIR;

    /**
     * 输出Branches
     */
    public static void printBranches() {
        System.out.println("=== Branches ===");
        List<String> fileNamesInDir = Utils.plainFilenamesIn(REFS_DIR);
        Collections.sort(fileNamesInDir);
        for (String name : fileNamesInDir) {
            if (name.equals(Repository.currentBranch))
                System.out.print("*");
            System.out.println(name);
        }
        System.out.println();
    }

    /**
     * 输出Staged Files
     */
    public static void printStaged() {
        softFileInDicAndPrint(ADDED_DIR, "=== Staged Files ===");
    }

    /**
     * 输出Removed Files
     */
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


    /**
     * 输出Modifications Not Staged For Commit
     */
    public static void printModifications() {
        System.out.println("=== Modifications Not Staged For Commit ===");
        List<String> filesInModOrDe=getModifiedFiles();
        Collections.sort(filesInModOrDe);
        for (String name : filesInModOrDe) {
            System.out.println(name);
        }
        System.out.println();
    }

    private static List<String> getModifiedFiles() {
        List<String> fileNamesInMod = new ArrayList<>();
        getModifiedFilesInCWD(fileNamesInMod);
        getModifiedFilesNoStaged(fileNamesInMod);
        getDeleteFiles(fileNamesInMod);
        return fileNamesInMod;
    }

    private static void getModifiedFilesInCWD(List<String> finalList) {
        List<String> fileNamesInADD = Utils.plainFilenamesIn(ADDED_DIR);
        List<String> fileNamesInCWD = Utils.plainFilenamesIn(CWD);
        for (String name : fileNamesInADD) {
            File fileInADD = Utils.join(ADDED_DIR, name);
            File fileInCWD = Utils.join(CWD, name);
            if (!fileNamesInCWD.contains(name)) {//对应在ADDED目录里的文件被删除,考虑到文件被删除情况放在SHA1码获取之前
                finalList.add(name + " (deleted)");
                fileInADD.delete();
                continue;
            }
            String sha1InADD = Utils.sha1(Utils.readContentsAsString(fileInADD));
            String sha1InCWD = Utils.sha1(Utils.readContentsAsString(fileInCWD));
            if (!sha1InADD.equals(sha1InCWD)) {//对应在ADDED目录里的文件被修改
                finalList.add(name + " (modified)");
                fileInADD.delete();
            }
        }
    }

    private static void getModifiedFilesNoStaged(List<String> finalList){
        Map<String,String> map=Commit.getHEAD().blobs();
        List<String> fileNamesInCWD = Utils.plainFilenamesIn(CWD);
        for (String name:map.keySet()) {
            if(!fileNamesInCWD.contains(name))continue;//如果检查的文件已经被rm

            File file=Utils.join(CWD,name);
            String currentSHA1=Utils.sha1(Utils.readContentsAsString(file));
            if(!currentSHA1.equals(map.get(name))){ //对应被HEAD追踪但是在CWD修改后未staged
                finalList.add(name + " (modified)");
            }
        }
    }
    private static void getDeleteFiles(List<String> finalList){
        Map<String,String> map=Commit.getHEAD().blobs();
        List<String> fileInCWD=Utils.plainFilenamesIn(CWD);
        List<String> fileInREMOVED=Utils.plainFilenamesIn(REMOVED_DIR);
        for (String name:map.keySet()) {
            if(!fileInCWD.contains(name)&&!fileInREMOVED.contains(name)){//对应Not staged for removal，同时被HEAD追踪且被在CWD删除
                finalList.add(name + " (deleted)");
            }
        }
    }


    /**
     * 输出Untracked Files
     */
    public static void printUntracked() {
        System.out.println("=== Untracked Files ===");
        List<String> untrackedFiles = getUntrackedFiles();
        for (String fileName : untrackedFiles) {
            System.out.println(fileName);
        }
        System.out.println();
    }


    private static List<String> getUntrackedFiles() {
        List<String> untrackedFilesInCWD = Utils.getUnStuckFilesInCWD();
        List<String> fileNamesInADD = Utils.plainFilenamesIn(ADDED_DIR);

        List<String> finalList = new ArrayList<>();

        for (String name : untrackedFilesInCWD) {//未在CWD追踪的且不在ADDED_DIR里面的文件
            if (!fileNamesInADD.contains(name)) {
                finalList.add(name);
            }
        }

        List<String> fileNamesInREMOVED = Utils.plainFilenamesIn(REMOVED_DIR);
        List<String> fileNamesInCWD = Utils.plainFilenamesIn(CWD);
        for (String name : fileNamesInREMOVED) {//在REMOVED_DIR被标记的但是又再次被在CWD里创建
            if (fileNamesInCWD.contains(name)) {
                finalList.add(name);
            }
        }
        return finalList;
    }
}
