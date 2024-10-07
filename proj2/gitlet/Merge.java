package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Merge {

    private static final File CWD = Repository.CWD;
    private static final File ADDED_DIR = Repository.ADDED_DIR;
    private static final File REMOVED_DIR = Repository.REMOVED_DIR;
    private static final File REFS_DIR = Repository.REFS_DIR;
    private static final File BLOBS_DIR = Repository.BLOBS_DIR;

    /**
     * 返回HEAD和otherBranch的最近分歧点
     */
    public static Commit findTheSpiltPoint(String branchName) {
        Commit otherBranch = Branch.getBranchFromString(branchName);
        Commit HEAD = Commit.getHEAD();
        if (HEAD.equals(otherBranch)) return HEAD;
        Commit HEADParent = HEAD.parent();
        Commit otherBranchParent = otherBranch.parent();
        while (!HEADParent.equals(otherBranchParent)) {
            HEADParent = HEADParent.parent();
            otherBranchParent = otherBranchParent.parent();
        }
        return HEADParent;
    }

    public static boolean checkAllFiles(Commit spiltPoint, Commit givenBranch) {
        boolean happenConflict=false;
        Set<String> allFiles = getAllFiles(spiltPoint, givenBranch);
        Map<String, String> spiltPointMap = spiltPoint.blobs();//分割点
        Map<String, String> currentBranchMap = Branch.getBranchFromString(Repository.currentBranch).blobs();//现在
        Map<String, String> givenBranchMap = givenBranch.blobs();//given
        List<String > addedList=new ArrayList<>();//staged for added
        List<String > removedList=new ArrayList<>();//staged for removal
        Map<String,String> finalFilesMap=new HashMap<>();
        for (String fileName : allFiles) {
            if (spiltPointMap.containsKey(fileName)) { //spiltPoint里有
                String spiltPointValue=spiltPointMap.get(fileName);
                if (currentBranchMap.containsKey(fileName) && givenBranchMap.containsKey(fileName)) {//当前和given都有
                    String currentValue=currentBranchMap.get(fileName);
                    String givenValue=givenBranchMap.get(fileName);
                    if(!currentValue.equals(spiltPointValue)&&!givenValue.equals(spiltPointValue)){//当前和given的修改与spilt都不一样
                        mergeConflict(currentValue,givenValue);//合并冲突
                        happenConflict=true;
                    } else if (!currentValue.equals(spiltPointValue)) {//当前的修改与spilt不一样
                        finalFilesMap.put(fileName,currentValue);
                    } else if (!givenValue.equals(spiltPointValue)) {//given的修改与spilt不一样
                        finalFilesMap.put(fileName,givenValue);
                    }
                } else if (currentBranchMap.containsKey(fileName)) { //当前有（可能修改可能没有）
                    String currentValue=currentBranchMap.get(fileName);
                    if(currentValue.equals(spiltPointValue)){       //没有修改
                        removedList.add(fileName);
                    }else{                                          //修改了
                        mergeConflict(currentValue,null);//合并冲突
                        happenConflict=true;
                    }
                } else if (givenBranchMap.containsKey(fileName)) {//given有（可能修改可能没有）
                    String givenValue=givenBranchMap.get(fileName);
                    if(givenValue.equals(spiltPointValue)){         //没有修改

                }else{                                              //修改了
                        mergeConflict(null,givenValue);//合并冲突
                        happenConflict=true;
                    }
                } else {
                    removedList.add(fileName);
                }
            } else { //spiltPoint里没有
                if (currentBranchMap.containsKey(fileName) && givenBranchMap.containsKey(fileName)) {//当前和given都修改过/都有
                    String currentValue=currentBranchMap.get(fileName);
                    String givenValue=givenBranchMap.get(fileName);
                    if(currentValue.equals(givenValue)){ //修改一致
                        finalFilesMap.put(fileName,currentValue);
                    }else{ //修改不一致
                        mergeConflict(currentValue,givenValue);//合并冲突
                        happenConflict=true;
                    }
                } else if (currentBranchMap.containsKey(fileName)) {//当前修改过/有
                    finalFilesMap.put(fileName,currentBranchMap.get(fileName));
                } else if (givenBranchMap.containsKey(fileName)) {//given修改过/有（添加进ADDED_DIR）
                    finalFilesMap.put(fileName,givenBranchMap.get(fileName));
                    addedList.add(fileName);
                } else {
                    //都没有修改/都没有，略过
                }
            }
        }

        setTheCWDFiles(finalFilesMap);
        stagedFiles(addedList,removedList);
        return happenConflict;
    }

    /**
     * 依据Map替换CWD里面的全部文件
     * */
    private static void setTheCWDFiles(Map <String,String> map){
        Utils.cleanDic(CWD);
        Utils.copyFromSource(map, CWD, BLOBS_DIR);
    }
    /**
     * 依据addedList添加ADDED_DIR里面的文件，依据removedList添加REMOVED_DIR里面的文件
     * */
    private static void stagedFiles(List <String> addList,List<String> removedList){
        copyFilesFromCWD(addList,ADDED_DIR);
        copyFilesFromCWD(removedList,REMOVED_DIR);
    }
    private static void copyFilesFromCWD(List <String> list,File targetPath){
        for (String fileName:list) {
            File file =Utils.join(CWD,fileName);
            File targetFile=Utils.join(ADDED_DIR,fileName);
            try {
                Files.copy(file.toPath(),targetFile.toPath(),StandardCopyOption.COPY_ATTRIBUTES);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void mergeConflict(String headSHA1,String givenSHA1){
        File headFile=headSHA1!=null?Utils.join(BLOBS_DIR,headSHA1):null;
        File givenFile=givenSHA1!=null?Utils.join(BLOBS_DIR,givenSHA1):null;

        String headString=headFile!=null?Utils.readContentsAsString(headFile):"";
        String givenString=givenFile!=null?Utils.readContentsAsString(givenFile):"";

        System.out.println("<<<<<<< HEAD");
        System.out.println(headString);
        System.out.println("=======");
        System.out.println(givenString);
        System.out.println(">>>>>>>");
    }

    private static Set<String> getAllFiles(Commit spiltPoint, Commit givenBranch) {
        Map<String, String> spiltPointMap = spiltPoint.blobs();
        Map<String, String> currentBranchMap = Branch.getBranchFromString(Repository.currentBranch).blobs();
        Map<String, String> givenBranchMap = givenBranch.blobs();
        Set<String> allFiles = spiltPointMap.keySet();
        allFiles.addAll(currentBranchMap.keySet());
        allFiles.addAll(givenBranchMap.keySet());
        return allFiles;
    }

    /**
     * 更新HEAD和当前分支的引用到给定分支
     */
    public static void fastForwardToBranch(String givenBranch) {
        File file = Utils.join(REFS_DIR, givenBranch);
        Commit.setHEADAndCurrentBranchRefs(file);
    }

}
