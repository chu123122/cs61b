package gitlet.operations;

import gitlet.Repository;

import java.io.File;
/**
 * 对gitlet初始化
 * */
public class Init {
    /**
     * .gitlet文件夹
     */
    public static final File GITLET_DIR = Repository.GITLET_DIR;
    public static final File STAGED_DIR = Repository.STAGED_DIR;
    /**
     * 存储暂存区域（staged）的文件夹
     */
    public static final File ADDED_DIR = Repository.ADDED_DIR;
    public static final File REMOVED_DIR = Repository.REMOVED_DIR;

    /**
     * 存储所有历史commit的文件副本的文件夹
     */
    public static final File BLOBS_DIR = Repository.BLOBS_DIR;
    /**
     * 存储所有提交的历史记录
     */
    public static final File COMMITS_DIR = Repository.COMMITS_DIR;

    /**
     * 创建所有需要的文件夹
     */
    public static void makeAllDir() {
        GITLET_DIR.mkdir();
        STAGED_DIR.mkdir();
        BLOBS_DIR.mkdir();
        COMMITS_DIR.mkdir();

        ADDED_DIR.mkdir();
        REMOVED_DIR.mkdir();
    }
    /**
    * 设置
    * */
    public static void setDefault(){

    }
}
