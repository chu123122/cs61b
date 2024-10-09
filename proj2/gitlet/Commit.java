package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *  提供对一个指令的基本操作，以及HEAD、NEW的指针
 *
 * @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     * <p>
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    private static final File CWD = Repository.CWD;
    /**
     * commits的总文件夹
     */
    private static final File COMMITS_DIR = Repository.COMMITS_DIR;
    /**
     * staged的总文件夹
     */
    private static final File ADDED_DIR = Repository.ADDED_DIR;
    private static final File BLOBS_DIR = Repository.BLOBS_DIR;

    private static final File REFS_DIR = Repository.REFS_DIR;
    /**
     * 提交链的HEAD指针（当前指针）
     */
    public static final String HEAD = "HEAD";
    /** 提交链的最新提交指针 */
    //public static String NEW;
    /**
     * 当前commit的父提交
     */
    private final String parent;
    /**
     * The message of this Commit.
     */
    private final String message;
    /**
     * 提交的节点的时间戳
     */
    private final String timeScale;
    /**
     * 每个节点的所有文件的引用
     */
    private final Map<String, String> blobs;
    private String sha1;


    /* TODO: fill in the rest of this class. */

    /**
     * 构造函数
     */
    public Commit(String message, String timeScale, String parent) {
        this.message = message;
        this.timeScale = timeScale;
        this.parent = parent;
        if (this.parent != null) this.blobs = this.parent().blobs;
        else this.blobs = new HashMap<>();
    }

    /**
     * 从当前Commit的Blobs中获取其在BLOBS_DIR文件夹里面的文件名（sha1码）
     */
    public File findFileInBlobs(String fileName) {
        String sha1 = blobs.get(fileName);
        if (sha1 == null) return null;
        return Utils.join(BLOBS_DIR, sha1);
    }

    /**
     * 新建一个序列化自身后的文件到commits文件夹，用自身的sha1码命名
     */
    public void submitCommit() {
        refreshBlobs();//更新Blobs的引用
        sha1 = Utils.sha1(this.toString());//更新SHA1的引用

        File commit = Utils.join(COMMITS_DIR, sha1);

        Utils.writeObject(commit, this);
        try {
            commit.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setHEADAndCurrentBranchRefs(commit);
    }

    public static void setHEADAndCurrentBranchRefs(File commit) {
        setTheHEAD(commit);
        setTheCurrentBranch(commit);
    }

    /**
     * 更新HEAD指针的文件的引用（文件名不变化，依旧HEAD）
     */
    private static void setTheHEAD(File commit) {
        File headCommit = Utils.join(COMMITS_DIR, HEAD);
        try {
            Files.copy(commit.toPath(), headCommit.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新当前分支指针的文件的引用（文件名不变化）
     */
    private static void setTheCurrentBranch(File commit) {
        //当初始化时，当前分支还未准备好
        if (Repository.currentBranch.equals("")) return;
        File currentBranch = Utils.join(REFS_DIR, Repository.currentBranch);
        try {
            Files.copy(commit.toPath(), currentBranch.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 依据staged里的文件更新当前commit的blobs引用，已存在的更新，不存在的添加
     */
    private void refreshBlobs() {
        List<String> keys = Utils.plainFilenamesIn(ADDED_DIR);//staged文件夹里add的文件
        if (keys == null) throw new RuntimeException("staged don't have the target files");
        for (String key : keys) {
            File originFile = Utils.join(Repository.CWD, key);
            String sha1 = Utils.sha1(Utils.readContentsAsString(originFile));
            if (blobs.containsKey(key)) blobs.replace(key, sha1);//如果存在则更新，反之添加
            else blobs.put(key, sha1);
        }
    }

    /**
     * 返回一个blobs继承自HEAD的commit
     */
    public static Commit getNewFromHEAD(String message, String timeScale) {
        File HEADFile = Utils.join(COMMITS_DIR, HEAD);
        Commit HEAD = Utils.readObject(HEADFile, Commit.class);
        return new Commit(message, timeScale, HEAD.SHA1());
    }

    /**
     * 返回HEAD（Commit）
     */
    public static Commit getHEAD() {
        File headFile = Utils.join(COMMITS_DIR, HEAD);
        Commit HEAD = Utils.readObject(headFile, Commit.class);
        return HEAD;
    }

    /**
     * 从COMMITS_DIR文件夹里依据SHA1码查找提交
     */
    public static Commit findCommitWithName(String commitId) {
        List<String> commitIds = Utils.plainFilenamesIn(COMMITS_DIR);
        for (String sha1 : commitIds) {
            if (sha1.equals(commitId)) {
                File commit = Utils.join(COMMITS_DIR, commitId);
                return Utils.readObject(commit, Commit.class);
            }
        }
        return null;
    }

    /**
     * 检查COMIMITS_DIR文件夹里面是否有特定提交
     */
    public static boolean checkHaveTheCommit(String commitId) {
        List<String> filesInCOMMITS = Utils.plainFilenamesIn(COMMITS_DIR);
        for (String name : filesInCOMMITS) {
            if (name.equals(commitId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查HEAD中是否有特定的文件
     */
    public static boolean checkHaveTheFileInHEAD(String fileName) {
        Commit HEAD = getHEAD();
        Map<String, String> map = HEAD.blobs();
        if (!map.containsKey(fileName)) return false;//如果blobs中不含有该文件
        else {
            //TODO:记得删除多余的
            File file = Utils.join(CWD, fileName);
            File file1 = Utils.join(BLOBS_DIR, map.get(fileName));
            String string0 = Utils.readContentsAsString(file1);
            String string1 = Utils.readContentsAsString(file);
            String sha0 = map.get(fileName);
            String sha1 = Utils.sha1(string1);
            return sha1.equals(map.get(fileName));//如果blobs中不含有SHA1码相同的文件
        }
    }

    public Commit parent() {
        if (parent == null) return null;
        File commit = Utils.join(COMMITS_DIR, parent);
        return Utils.readObject(commit, Commit.class);
    }

    public String SHA1() {
        return sha1;
    }

    public String message() {
        return message;
    }

    public String timeScale() {
        return timeScale;
    }

    public Map<String, String> blobs() {
        return blobs;
    }

    @Override
    public String toString() {
        return "Commit{" + "message='" + message + '\'' + ", timestamp=" + timeScale + ", parentHash='" + parent + '\'' + ", blobs=" + blobs + '}';
    }

    public boolean equals(Commit otherCommit) {
        return this.SHA1().equals(otherCommit.SHA1());
    }
}
