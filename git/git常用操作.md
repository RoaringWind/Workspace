```bash
# 克隆
git clone git@github.com:RoaringWind/PracticeCode.git #在当前文件夹下，创建以PracticeCode为名字的文件夹，递归克隆仓库
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
git pull origin master
```

```bash
# 本地分支push到远程
git push origin master
git push <remote> <branch>
```

```bash
# 比较差异，先把远程仓库的内容取到本地分支
git fetch origin
# git diff <local branch> <remote>/<remote branch>
git diff --stat master origin/master
```
```bash
# 恢复
git reset
```

