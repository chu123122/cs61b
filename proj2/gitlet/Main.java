package gitlet;


/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author TODO
 */
public class Main {



    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if(args.length==0){
            Utils.message("Please enter a command.");
            return;
        }
        Init.setDefault();
        String firstArg = args[0];

        switch (firstArg) {
            case "init" -> Repository.setupPersistence();
            case "add" -> {
                String fileName = args[1];
                Repository.addGitLet(fileName);
            }
            case "commit" -> {
                String message = args[1];
                Repository.commitGitLet(message);
            }
            case "checkout" -> {
                if (args.length == 3) {
                    Repository.checkOutGitLet("HEAD", args[2]);
                } else if (args.length == 4) {
                    Repository.checkOutGitLet(args[1], args[3]);
                } else if (args.length == 2) {
                    Repository.checkOutGitLet(args[1]);
                }
            }
            case "log" -> Repository.logGitLet();
            case "global-log" -> Repository.globalLogGitLet();
            case "rm" -> {
                String name = args[1];
                Repository.rmGitLet(name);
            }
            case "find" -> {
                String findMessage = args[1];
                Repository.findGitLet(findMessage);
            }
            case "branch" -> {
                String crBranchName = args[1];
                Repository.branchGitLet(crBranchName);
            }
            case "rm-branch" -> {
                String rmBranchName = args[1];
                Repository.rmBranchGitLet(rmBranchName);
            }
            case "status" -> Repository.statusGitLet();
            case "reset" -> {
                String resetCommitId = args[1];
                Repository.reSetGitLet(resetCommitId);
            }
            case "merge" -> {
                String mergeBranchName = args[1];
                Repository.mergeGitLet(mergeBranchName);
            }
            default -> {
                Utils.message("No command with that name exists.");
            }
        }
        return;
    }
}
