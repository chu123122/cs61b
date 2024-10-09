package gitlet;


import java.io.File;
import java.util.List;

/**
 * 对gitlet初始化
 */
public class Init {
    /**
     * .gitlet文件夹
     */
    private static final File GITLET_DIR = Repository.GITLET_DIR;
    private static final File STAGED_DIR = Repository.STAGED_DIR;
    /**
     * 存储暂存区域（staged）的文件夹
     */
    private static final File ADDED_DIR = Repository.ADDED_DIR;
    private static final File REMOVED_DIR = Repository.REMOVED_DIR;

    /**
     * 存储所有历史commit的文件副本的文件夹
     */
    private static final File BLOBS_DIR = Repository.BLOBS_DIR;
    /**
     * 存储所有提交的历史记录
     */
    private static final File COMMITS_DIR = Repository.COMMITS_DIR;
    /**
     * 用于存储各个branch的引用
     */
    private static final File REFS_DIR = Repository.REFS_DIR;
    private static final File CURRENT_DIR = Repository.CURRENT_DIR;

    /**
     * 创建所有需要的文件夹
     */
    public static void makeAllDir() {
        GITLET_DIR.mkdir();
        STAGED_DIR.mkdir();
        BLOBS_DIR.mkdir();
        COMMITS_DIR.mkdir();

        REFS_DIR.mkdir(); //COMMIT_DIR文件夹下
        CURRENT_DIR.mkdir();//REFS_DIR文件夹下

        ADDED_DIR.mkdir(); //STAGED_DIR文件夹下
        REMOVED_DIR.mkdir();
    }

    /**
     * 设置当前所在分支
     */
    public static void setDefault() {
        if (!GITLET_DIR.exists()) return;
        List<String> branchNames = Utils.plainFilenamesIn(REFS_DIR);
        String currentBranch = Utils.plainFilenamesIn(CURRENT_DIR).get(0);
        for (String branchName : branchNames) {
            if (branchName.equals(currentBranch)) {
                Repository.currentBranch = branchName;
                return;
            }
        }
    }
}
