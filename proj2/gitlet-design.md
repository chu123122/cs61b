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

复制`add [fileName]` 命令中的`[fileName]`文件的一份副本到`staged`和`blobs` 文件夹中

#### Field

1. `public static final File STAGED_DIR`  `staged`文件夹,用于存储所有的add文件的副本（每次commit后会被删除）
2. `public static final File BLOBS_DIR`  `blobs`文件夹，用于存储所有的add文件的用sha1码作为名称的文件副本（不会被删除）

---

#### Commit

新建一个节点，引用继承自父节点的引用，提交时，依据`staged` 文件夹里的文件更新/添加`blobs`里的引用，同时设置，`HEAD` 和`NEW` 为当前节点

#### Field

1. `private static final File COMMITS_DIR`  `commits` 文件夹

2. `private static final File STAGED_DIR`  `stahed` 文件夹

3. `public static Commit HEAD` 当前所在的commit

4. `public static Commit NEW`  该分支的最新commit (没考虑到分支的情况啊)

5. `private final String parent`   该commit的父节点(最多2个，为了序列化用String表示)

6. `private final String message`  该commit的信息

7. `private final String timescale`  该commit的时间戳

8. `private final Map<String,String> blobs`  该commit的保存的所有文件名称和对应引用（sha1码表示引用）

---

## Algorithms

---

## Persistence

```
CWD                                    <==== 当前工作的文件夹
└──── .gitlet                            <==== 存储gitlet所有持续化数据的地方
    ├────── staged                       <==== 存储add [fileName]后文件的地方  
    ├────── commits                      <==== 存储序列化后的节点的地方
    └────── blobs                        <==== 存储每一个节点实际指向的版本文件的地方
```

init -----初始化所有文件夹，同时提交一个默认的commit

add -----添加CWD里面的文件的副本到`staged`里面

commit -----序列化一个commit文件到`commits`里面

疑问：

1. add要不要添加副本到`blobs`里面

2. `staged`里面的文件如何命名？

3. 复制前一个commit的所有引用，再更新`staged` 区域的文件，但是如何找到对应的？（字典？）
