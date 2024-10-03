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

复制`add [fileName]` 命令中的`[fileName]`文件的一份副本到`staged`和`blobs` 文件夹中（注意：用`CWD` 文件夹的sha1码命名）

#### Field

1. `public static final File STAGED_DIR`  `staged`文件夹,用于存储所有的add文件的副本（每次commit后会被删除）
2. `public static final File BLOBS_DIR`  `blobs`文件夹，用于存储所有的add文件的用sha1码作为名称的文件副本（不会被删除）

---

#### Commit

新建一个节点，引用继承自父节点的引用，提交时，依据`staged` 文件夹里的文件更新/添加`blobs`里的引用（注意：用`CWD` 中与`staged` 中的文件相同的文件的sha1码），同时设置，`HEAD` 为当前节点

#### Field

1. `private static final File COMMITS_DIR`  `commits` 文件夹

2. `private static final File STAGED_DIR`  `stahed` 文件夹

3. `public static Commit HEAD` 当前所在的commit

4. `public static Commit NEW`  该分支的最新commit (没考虑到分支的情况啊)

5. `private final String parent`   该commit的父节点(最多2个，为了序列化用String表示)

6. `private final String message`  该commit的信息

7. `private final String timescale`  该commit的时间戳

8. `private final Map<String,String> blobs`  该commit的保存的所有文件名称和对应引用（sha1码表示引用）`

---

#### CheckOut

依据`commitId` ，`fileName` 回溯`CWD`中的同名文件在该分支的特定文件版本

#### Field

`private static final File CWD`  当前工作的文件夹

---

#### Log

依据`HEAD` 指针从后往前输出历史提交记录（log）或通过读取`commits` 文件夹里所有提交依次输出（global-log）

#### Field

1. `public static Commit HEAD`

---

#### Find

依据传入的`String` 类型的形参（message）在`commits` 文件夹里面查找所有信息中含有`message` 的提交，并将其输出

#### Field

1. `private static final File COMMITS_DIR`  

---

#### Remove

#### Field

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

流程：

1. init，创建`.gitlet`,`staged` ,`commits` ,`blobs`文件夹，同时提交一个初始提交

2. add，添加对应文件到`staged`文件夹（原名）和`blobs` 文件夹（sha1名）

3. commit，复制上一个commit（仅`blobs` 相同），提交时更新`blobs` （从`staged` 文件夹里查询所有与`bolos` key相同的文件，更新其sha1码，从而可以获取到其在`blobs` 里的sha1码名称的文件存储）将其序列化写入用其sha1码为名的文件中存储到`commits`文件夹里。每次提交时更新当前分支的最新引用。（通过对比HEAD和引用的`SHA1` 码从而确定）
   
      ***问题：如果修改了文件的名称，文件原版本和改名版本都会在中commit存在***
