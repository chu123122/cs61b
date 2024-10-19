package gitlet;

import java.io.File;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static gitlet.Utils.*;

// TODO: any imports you need here

/**
 * Represents a gitlet repository.
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

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /**
     * 存储暂存区域（staged）的文件夹.
     */
    public static final File STAGED_DIR = Utils.join(Repository.GITLET_DIR, "staged");
    public static final File ADDED_DIR = Utils.join(Repository.STAGED_DIR, "added");
    public static final File REMOVED_DIR = Utils.join(Repository.STAGED_DIR, "removed");

    /**
     * 存储所有历史commit的文件副本的文件夹.
     */
    //TODO: 划分为不同commit的文件夹，但只存储同上一commit对比发生变化的文件
    public static final File BLOBS_DIR = Utils.join(Repository.GITLET_DIR, "blobs");
    /**
     * 存储所有提交的历史记录.
     */
    public static final File COMMITS_DIR = Utils.join(Repository.GITLET_DIR, "commits");//双向链表存储

    public static final File REFS_DIR = Utils.join(COMMITS_DIR, "refs");
    public static final File CURRENT_DIR = Utils.join(REFS_DIR, "current");

    public static final String HEAD = "HEAD";
    public static String currentBranch = "";

    /* TODO: fill in the rest of this class. */

    /**
     * init指令对应方法
     */
    public static void setupPersistence() {
        boolean hasInit = GITLET_DIR.exists();
        if (hasInit) {
            Utils.message("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        //初始化文件夹
        Init.makeAllDir();
        //初始化提交
        String timeScale = "Wed Dec 31 16:00:00 1969 -0800";
        String message = "initial commit";
        Commit initCommit = new Commit(message, timeScale, null);
        initCommit.submitCommit();
        //创建初始分支并切换到上面
        Branch.createNewBranch("master");
        CheckOut.changeBranch("master");
    }

    /**
     * add指令对应方法
     */
    public static void addGitLet(String fileName) {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        }

        List<String> cwdDic = plainFilenamesIn(CWD);
        if (!cwdDic.contains(fileName)) {
            message("File does not exist.");
            return;
        }
        File addFile = join(CWD, fileName);
        Add.addStageFile(addFile);
        Add.deleteInREMOVED(fileName);
    }

    /**
     * commit指令对应方法
     */
    public static void commitGitLet(String message) {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        } else if (message == null || message.equals("")) {
            Utils.message("Please enter a commit message.");
            return;
        } else if (dirIsEmpty(ADDED_DIR) && dirIsEmpty(REMOVED_DIR)) {
            Utils.message("No changes added to the commit.");
            return;
        }
        //获取当前的时间戳
        ZoneOffset offset = ZoneOffset.ofHours(-8);
        ZonedDateTime dateTime = ZonedDateTime.now(offset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        String formattedDate = dateTime.format(formatter);

        Commit commit = Commit.getNewFromHEAD(message, formattedDate);
        commit.submitCommit();
        Utils.cleanDic(ADDED_DIR);
        Utils.cleanDic(REMOVED_DIR);
    }

    /**
     * checkout指令对应方法(文件)
     */
    public static void checkOutGitLet(String commitId, String fileName) {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        }
        Commit commit = Commit.findCommitOrBranch(commitId);
        if (commit == null) {
            Utils.message("No commit with that id exists.");
            return;
        }
        File sourceFile = commit.findFileInBlobs(fileName);
        File targetFile = Utils.join(CWD, fileName);
        if (sourceFile == null) {
            Utils.message("File does not exist in that commit.");
            return;
        }

        CheckOut.checkOutFile(sourceFile, targetFile);
    }

    /**
     * checkout指令对应方法(分支)
     */
    public static void checkOutGitLet(String branchName) {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        } else if (!Branch.haveTheBranch(branchName)) {
            Utils.message("No such branch exists.");
            return;
        } else if (branchName.equals(currentBranch)) {
            Utils.message("No need to checkout the current branch.");
            return;
        } else if (haveChangeAndUntrackedFile(branchName)) {
            Utils.message("There is an untracked file in the way; delete it, or add and commit it first.");
            return;
        }

        CheckOut.changeBranch(branchName);
    }

    public static void logGitLet() {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        }

        Log.log();
    }

    public static void globalLogGitLet() {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        }

        Log.globalLog();
    }

    public static void rmGitLet(String fileName) {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        }
        List<String> fileNameInAdded = Utils.plainFilenamesIn(ADDED_DIR);
        for (String name : fileNameInAdded) {
            //如果文件已经add
            if (name.equals(fileName)) {
                Remove.unStagedAddedFile(fileName);
                return;
            }
        }
        //检查是否在HEAD里被追踪
        if (!Commit.checkHaveTheSameFile(fileName,Commit.getHEAD())) {
            Utils.message("No reason to remove the file.");
            return;
        }

        Remove.copyCWDToRemoval(fileName);//标记为待删除（在blobs里）
        Remove.deleteCWDFile(fileName);//从CWD删除
    }

    public static void findGitLet(String message) {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        }
        List<String> commitsSHA1 = Find.findTheCommitsSHA1(message);
        if (commitsSHA1.size() == 0) {
            Utils.message("Found no commit with that message.");
            return;
        }
        for (String SHA1 : commitsSHA1) {
            Find.printfTheFind(SHA1);
        }
    }

    public static void branchGitLet(String name) {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        }
        Branch.createNewBranch(name);
    }

    public static void rmBranchGitLet(String name) {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        } else if (currentBranch.equals(name)) {  //如果删除的分支是当前分支，返回
            Utils.message("Cannot remove the current branch.");
            return;
        } else if (!Branch.haveTheBranch(name)) { //如果删除的分支不存在，返回
            Utils.message("A branch with that name does not exist.");
            return;
        }
        Branch.rmTheBranch(name);
    }

    public static void statusGitLet() {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        }
        Status.printBranches();
        Status.printStaged();
        Status.printRemoved();
        Status.printModifications();
        Status.printUntracked();
        System.out.println();
    }

    public static void reSetGitLet(String commitId) {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        } else if (!Commit.checkHaveTheCommit(commitId)) {
            Utils.message("No commit with that id exists.");
            return;
        } else if (haveChangeAndUntrackedFile(commitId)) {
            Utils.message("There is an untracked file in the way; delete it, or add and commit it first.");
            return;
        }

        Reset.resetFromTheBranch(commitId);
        Utils.cleanDic(ADDED_DIR);
        Utils.cleanDic(REMOVED_DIR);
    }


    public static void mergeGitLet(String givenBranchName) {
        if (!GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            return;
        } else if (dirIsEmpty(ADDED_DIR) && dirIsEmpty(REMOVED_DIR)) {
            Utils.message("You have uncommitted changes.");
            return;
        } else if (Branch.haveTheBranch(givenBranchName)) {
            Utils.message("A branch with that name does not exist.");
            return;
        } else if (givenBranchName.equals(currentBranch)) {
            Utils.message("Cannot merge a branch with itself.");
            return;
        } else if (haveChangeAndUntrackedFile(givenBranchName)) {
            Utils.message("There is an untracked file in the way; delete it, or add and commit it first.");
            return;
        }

        Commit spiltPoint = Merge.findTheSpiltPoint(givenBranchName);
        Commit givenBranch = Branch.getBranchFromString(givenBranchName);
        if (spiltPoint.equals(givenBranch)) {         //分割点和给定分支相同，即给定分支无额外提交（当前最新，无需操作）
            Utils.message("Given branch is an ancestor of the current branch.");
            return;
        } else if (spiltPoint.equals(Commit.getHEAD())) {           //分割点是当前分支，即当前分支无额外提交（给定分支最新，直接将当前分支快进到给定分支）
            Merge.fastForwardToBranch(givenBranchName);
            Utils.message("Current branch fast-forwarded.");
            return;
        }
        boolean conflict = Merge.checkAllFiles(spiltPoint, givenBranch);

        commitGitLet("Merged " + givenBranchName + " into +" + currentBranch + ".");
        if (conflict) Utils.message("Encountered a merge conflict.");
    }

    /**
     * 检测HEAD里是否有追踪该文件,如果未追踪，检查切换到的commit是否会修改该文件
     */
    private static boolean haveChangeAndUntrackedFile(String target) {
        Commit targetCommit = Commit.findCommitOrBranch(target);

        List<String> unStuckFilesInCWD = getUnStuckFilesInCWD();
        for (String fileName : unStuckFilesInCWD) {
            //被修改的情况：1.被删除，targetCommit的Blobs里不含有该文件；2被修改，含有，但是SHA1码不一样。
            if (Commit.checkTheUnstuckWillBeOverride(fileName, targetCommit)) {
                return true;
            }
        }
        return false;
    }

    private static boolean dirIsEmpty(File dir) {
        List<String> filesInAdded = Utils.plainFilenamesIn(dir);
        return filesInAdded.size() == 0;
    }


}
