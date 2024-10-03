package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Log {
    //TODO:合并没实现
    public static Commit HEAD=Commit.getHEAD();
    private static final File COMMITS_DIR=Repository.COMMITS_DIR;
    public static void log(){
       Commit check=HEAD;
        while(check!=null){
            printlnCommit(check);
            check=check.parent();
        }
    }

    //TODO:和实际需要的功能完全不一样
    public static void globalLog(){
        List<String> fileNames= Utils.plainFilenamesIn(COMMITS_DIR);
        for (String fileName:fileNames) {
            System.out.println("commit "+fileName);
        }
    }

    private static void printlnCommit(Commit commit){
        System.out.println("===");
        System.out.println("commit "+commit.SHA1());
        System.out.println("Date: "+commit.timeScale());
        System.out.println(commit.message());
        System.out.println();
    }
}
