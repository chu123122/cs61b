package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.util.Date; // TODO: You'll likely use this in this class

/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Commit {
    /**
     * TODO: add instance variables here.
     * <p>
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    /** commits的总文件夹 */
    private static final File COMMITS_DIR = Repository.COMMITS_DIR;
    /** 提交链的HEAD指针（当前指针） */
    public static Commit HEAD;
    /** 提交链的最新提交指针 */
    public static Commit NEW;
    /** 当前commit的父提交 */
    public Commit[] parents = new Commit[2];
    /** The message of this Commit. */
    private final String message;
    private final String shaCode;

    /* TODO: fill in the rest of this class. */
    public Commit(String message,String ShaCode,Commit parent1,Commit parent2){
        this.message=message;
        this.shaCode=ShaCode;
        this.parents[0]=parent1;
        this.parents[1]=parent2;
    }

    /**
     * 新建一个提交到commits文件夹里
     */
    public void submitCommit() {
        HEAD=this;//TODO:修改
        NEW=this;
        File commit=Utils.join(COMMITS_DIR,shaCode);
        Utils.writeContents(commit,this);
        try {
            commit.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
