package gitlet;

import java.io.File;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
    public static final File ADDED_DIR = Utils.join(Repository.STAGED_DIR, "added");
    public static final File REMOVED_DIR = Utils.join(Repository.STAGED_DIR, "removed");

    /** 存储所有历史commit的文件副本的文件夹. */
    //TODO: 划分为不同commit的文件夹，但只存储同上一commit对比发生变化的文件
    public static final File BLOBS_DIR = Utils.join(Repository.GITLET_DIR, "blobs");
    /** 存储所有提交的历史记录. */
    public static final File COMMITS_DIR = Utils.join(Repository.GITLET_DIR, "commits");//双向链表存储

    public static final File REFS_DIR=Utils.join(COMMITS_DIR,"refs");
    public static final File CURRENT_DIR=Utils.join(REFS_DIR,"current");

    public static String currentBranch="";

    /* TODO: fill in the rest of this class. */
    /**
     * init指令对应方法
     * */
    public static void setupPersistence(){
        boolean hasInit=GITLET_DIR.exists();
        if(hasInit){
            message("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        //初始化文件夹
        Init.makeAllDir();
        //初始化提交
        String timeScale="Wed Dec 31 16:00:00 1969 -0800";
        String message="initial commit";
        Commit initCommit=new Commit(message, timeScale,null);
        initCommit.submitCommit();
        //创建初始分支并切换到上面
        Branch.createNewBranch("master");
        CheckOut.changeBranch("master");
    }
    /**
     * add指令对应方法
     * */
    public static void addGitLet(String fileName){
        List<String> cwdDic=plainFilenamesIn(CWD);
        if(!cwdDic.contains(fileName)){
            message("File does not exist.");
            return;
        }
        File addFile=join(CWD,fileName);
        Add.addStageFile(addFile);
    }
    /**
     * commit指令对应方法
     * */
    public static void commitGitLet(String message){
        //获取当前的时间戳
        ZoneOffset offset = ZoneOffset.ofHours(-8);
        ZonedDateTime dateTime = ZonedDateTime.now(offset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        String formattedDate = dateTime.format(formatter);

        Commit commit=Commit.getNewFromHEAD(message,formattedDate);
        commit.submitCommit();
        cleanDic(ADDED_DIR);
        cleanDic(REMOVED_DIR);
    }
    /**
     * checkout指令对应方法
     * */
    public static void checkOutGitLet(String commitId,String fileName){
        Commit commit=Commit.findCommitWithName(commitId);
        if(commit==null){
            Utils.message("No commit with that id exists.");
            return;
        }
        CheckOut.checkOutTheFile(commit,fileName);
    }
    public static void checkOutGitLet(String branchName){
        CheckOut.changeBranch(branchName);
    }
    public static void logGitLet(){
        Log.log();
    }
    public static void globalLogGitLet(){
        Log.globalLog();
    }

    public static void rmGitLet(String fileName){
        List<String> fileNameInAdded =Utils.plainFilenamesIn(ADDED_DIR);
        assert fileNameInAdded != null;
        for (String name:fileNameInAdded) {
            //如果文件已经add
            if(name.equals(fileName)){
                Remove.unStagedFile(fileName);
                return;
            }
        }
        //检查是否在HEAD里被追踪
        if(!Commit.getHEAD().blobs().containsKey(fileName)){
            Utils.message("No reason to remove the file.");
            return;
        }
        Remove.copyCWDToRemoval(fileName);
        Remove.deleteCWDFile(fileName);
    }

    public static void findGitLet(String message){
        List<String> commitsSHA1=Find.findTheCommitsSHA1(message);
        if(commitsSHA1.size()==0){
            Utils.message("Found no commit with that message.");
            return;
        }
        for (String SHA1:commitsSHA1) {
            Find.printfTheFind(SHA1);
        }
    }

    public static void branchGitLet(String name){
        Branch.createNewBranch(name);
    }

    public static void rmBranchGitLet(String name){
        //如果删除的分支是当前分支，返回
        if(currentBranch.equals(name)){
            Utils.message("Cannot remove the current branch.");
            return;
        }
        //如果删除的分支不存在，返回
        if(!Branch.checkHaveTheBranch(name)){
            Utils.message("A branch with that name does not exist.");
            return;
        }
        Branch.rmTheBranch(name);
    }
    public static void statusGitLet(){
        Status.printBranches();
        Status.printStaged();
        Status.printRemoved();
        Status.printModifications();
        Status.printUntracked();
        System.out.println();
    }

    public static void reSetGitLet(String commitId){
        if(!Commit.checkHaveTheCommit(commitId)){
            Utils.message("No commit with that id exists.");
            return;
        }
        List<String> filesInCWD=Utils.plainFilenamesIn(CWD);
        List<String> filesInREMOVED=Utils.plainFilenamesIn(REMOVED_DIR);
        List<String> filesInADDED=Utils.plainFilenamesIn(ADDED_DIR);
        for (String name:filesInCWD) {
            if(!filesInADDED.contains(name)&&!filesInREMOVED.contains(name)){
                if(!Commit.checkHaveTheFileInHEAD(name)){
                    Utils.message("There is an untracked file in the way; delete it, or add and commit it first.");
                    return;
                }
            }
        }

        Reset.resetFromTheBranch(commitId);
        cleanDic(ADDED_DIR);
        cleanDic(REMOVED_DIR);
    }

    /**
     * 删除文件夹里所有文件
     * */
    private static void cleanDic(File dir){
        List<String> stagedDic=plainFilenamesIn(dir);
        for (String name:stagedDic) {
            File file=Utils.join(dir,name);
            file.delete();
        }
    }
}
