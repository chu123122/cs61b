# GitLet设计文档

---

## Class And Data Structure

---

### Main

读取用户输入的指令,识别指令运用对应的`Repository` 里的方法来实现功能

#### Field

暂时无变量,仅仅引用`Repository`里的方法实现功能

---

#### Repository

通过对应的指令类来实现各种指令的功能,将方法封装到,在`Main`中对于不同的指令能够直接调用的程度.

#### Field

1. `public static final File CWD = new File(System.getProperty("user.dir"))`  当前目录

2. `public static final File GITLET_DIR = join(CWD, ".gitlet")`  当前目录下的`.gitlet` 文件夹

3. `public static final File STAGED_DIR = Utils.join(Repository.GITLET_DIR, "staged")` `.gitlet`里面的`staged`文件夹，用于存放经过`add` 命令的文件，即复制的`add` 文件副本.

4. `public static final File BLOBS_DIR = Utils.join(Repository.GITLET_DIR, "blobs")` `.gitlet` 里面的`blobs` 文件夹，用于存放经过`commit` 命令的文件，即当前提交节点的文件。注意！该文件夹下按不同提交节点划分为多个不同子文件夹，子文件夹里面仅存放与上次提交不同的文件.

5. `public static final File COMMITS_DIR = Utils.join(Repository.GITLET_DIR, "commits")` `.gitlet` 里面的`commits` 文件夹,用于存放每次的提交节点的序列化文件,里面存有对于不同提交的文件的引用.

---

#### Init

初始化(创建)`.gitlet`,`staged` ,`blobs`,`commits`文件夹

#### Field

1. `public static final File GITLET_DIR` `.gitlet`文件夹

2. `public static final File STAGED_DIR` `staged`文件夹

3. `public static final File BLOBS_DIR` `blobs` 文件夹

4. `public static final File COMMITS_DIR` `commits` 文件夹

---

#### Add

复制`add [fileName]` 命令中的`[fileName]`文件的一份副本到`staged`文件夹中

#### Field

1. `public static final File STAGED_DIR`  `staged`文件夹,用于存储所有的add文件的副本

---

#### Commit

新建一个HEAD节点的副本,对`staged` 文件夹里面文件的引用更新后,将其序列化提交到`commits` 文件夹里,更新`HEAD` 和`NEW` .

#### Field

1. `private static final File COMMITS_DIR`  `commits` 文件夹

2. `public static Commit HEAD` 当前所在的commit

3. `public static Commit NEW`  该分支的最新commit (没考虑到分支的情况啊)

4. `private final Commit[] parents`  该commit的父节点(最多2个)

5. `private final String message`  该commit的信息

6. `private final String shaCode`  该commit的SHA码

---

## Algorithms

---

## Persistence

---
