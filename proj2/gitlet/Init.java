package gitlet;

import java.io.File;

public class Init {
    /**
     * 存储暂存区域（staged）的文件夹
     */
    public static final File STAGED_DIR = Repository.STAGED_DIR;
    /**
     * 存储所有历史commit的文件副本的文件夹
     */
    public static final File BLOBS_DIR = Repository.BLOBS_DIR;
    /**
     * 存储所有提交的历史记录
     */
    public static final File COMMITS_DIR = Repository.COMMITS_DIR;//双向链表存储

    /**
     * 创建所有需要的文件夹
     */
    public void makeAllDir() {
        Repository.GITLET_DIR.mkdir();
        STAGED_DIR.mkdir();
        BLOBS_DIR.mkdir();
        COMMITS_DIR.mkdir();
    }
}
