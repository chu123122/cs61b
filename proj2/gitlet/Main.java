package gitlet;

import gitlet.operations.Init;

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
        Init.setDefault();
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                // TODO: handle the `init` command
                Repository.setupPersistence();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                String fileName = args[1];
                Repository.addGitLet(fileName);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                String message = args[1];
                Repository.commitGitLet(message);
                break;
            case "checkout":
                if(args.length==3){
                    Repository.checkOutGitLet("HEAD",args[2]);
                } else if (args.length==4) {
                    Repository.checkOutGitLet(args[1],args[3]);
                }/*else if(args.length==2){

                }*/
                break;
            case "log":
                Repository.logGitLet();
                break;
            case "rm":
                String name=args[1];
                Repository.rmGitLet(name);
                break;
        }
        return;
    }
}
