package gitlet;

public class Log {
    //TODO:合并没实现
    public static Commit HEAD=Commit.getHEAD();
    public static void log(){
       Commit check=HEAD;
        while(check!=null){
            printlnCommit(check);
            check=check.parent();
        }
    }

    public static void globalLog(){
        Commit check=HEAD;
        while(check!=null){
            System.out.println("===");
            System.out.println("commit "+check.SHA1());
            System.out.println();
            check=check.parent();
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
