package gitlet;

import java.io.File;
import java.util.List;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *  提供所有对应指令的封装完善的方法
 *  @author chu123122
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** 存储暂存区域（staged）的文件夹. */
    public static final File STAGED_DIR = Utils.join(Repository.GITLET_DIR, "staged");
    /** 存储所有历史commit的文件副本的文件夹. */
    //TODO: 划分为不同commit的文件夹，但只存储同上一commit对比发生变化的文件
    public static final File BLOBS_DIR = Utils.join(Repository.GITLET_DIR, "blobs");
    /** 存储所有提交的历史记录. */
    public static final File COMMITS_DIR = Utils.join(Repository.GITLET_DIR, "commits");//双向链表存储


    /* TODO: fill in the rest of this class. */
    public static void setupPersistence(){
        boolean hasInit=GITLET_DIR.exists();
        if(hasInit){
            message("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        //初始化文件夹
        Init.makeAllDir();
        //初始化提交
        String timeScale=" 00:00:00 UTC, Thursday, 1 January 1970";
        String message="init commit";
        Commit initCommit=new Commit(message, timeScale,null);
        initCommit.submitCommit();
    }

    public static void addGitLet(String fileName){
        List<String> cwdDic=plainFilenamesIn(CWD);
        if(!cwdDic.contains(fileName)){
            message("File does not exist.");
            return;
        }
        File addFile=join(CWD,fileName);
        Add.addStageFile(addFile);
    }

    public static void commitGitLet(String message){
        String timeScale=String.valueOf(System.currentTimeMillis());
        Commit commit=Commit.getNewFromHEAD(message,timeScale);
        commit.submitCommit();
        cleanStagedDic();
    }

    public static void checkOutGitLet(String commitId,String fileName){
        Commit commit=Commit.findCommitWithName(commitId);
        if(commit==null){
            Utils.message("No commit with that id exists.");
            return;
        }
        CheckOut.checkOutTheFile(commit,fileName);
    }


    private static void cleanStagedDic(){
        List<String> stagedDic=plainFilenamesIn(STAGED_DIR);
        for (String name:stagedDic) {
            File file=Utils.join(STAGED_DIR,name);
            file.delete();
        }
    }

}
