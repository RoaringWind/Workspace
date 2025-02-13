```bash
# 克隆
git clone git@github.com:RoaringWind/PracticeCode.git #在当前文件夹下，创建以PracticeCode为名字的文件夹，递归克隆仓库
git clone ssh://git@ip:port/home/git/repo.git #ssh，ip和port换成对应的ip和端口即可
```
```bash
# 添加
git add README.md #添加文件 
git add -A  #提交所有变化(修改，删除，添加)
git add -u  #提交被修改(modified)和被删除(deleted)文件，不包括新文件(new)
git add .  #提交新文件(new)和被修改(modified)文件，不包括被删除(deleted)文件....似乎有误，会提交所有文件

```

```bash
#修改
git commit --amend #修改上次提交信息
```

```bash
# 合并远程分支到本地
git pull origin master #pull先把远程分支取到现在机器上，再与本地分支合并
# 等价于 git fetch origin + git merge origin/master
```

```bash
# 本地分支push到远程
git push origin master
# git push <remote> <branch>
```

```bash
# 比较差异，需要先把远程仓库的内容取到本地分支
git fetch origin
# git diff <local branch> <remote>/<remote branch>
git diff --stat master origin/master
```
```bash
# 恢复
git reset
```
```bash
# 添加远程仓库
git remote add origin git@github.com:RoaringWind/BoringPic.git # origin只是一个别名（shortname），不是分支名也不是直接的仓库名，可以理解成绰号！！
```

```bash
# 合并分支
git checkout -b iss53 # 新建并切换到iss53 等价于 git branch iss53 + git checkout iss53
# 进行增删改操作后commit
git checkout master # Switched to branch 'master'
git merge iss53 # Merge made by the 'recursive' strategy. Merge iss53 into master.

```

```bash
# 删除文件夹
git rm -r --cached <foldName>
git commit -m "delete fold"
```

