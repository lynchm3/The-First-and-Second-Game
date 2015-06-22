//package com.marklynch.notes;
//
//public class temp {
//	Windows PowerShell
//	Copyright (C) 2013 Microsoft Corporation. All rights reserved.
//
//	C:\dev\workspaces\tactics [master +1 ~8 -0 !]> git commit -a
//	[master dc1d3e3] logging on screen, attack random target
//	 8 files changed, 308 insertions(+), 126 deletions(-)
//	C:\dev\workspaces\tactics [master +1 ~0 -0 !]> git status
//	On branch master
//	Your branch is ahead of 'origin/master' by 3 commits.
//	  (use "git push" to publish your local commits)
//
//	Untracked files:
//	  (use "git add <file>..." to include in what will be committed)
//
//	        tactics_project/src/com/marklynch/tactics/objects/level/ActivityLog.java
//
//	nothing added to commit but untracked files present (use "git add" to track)
//	C:\dev\workspaces\tactics [master +1 ~0 -0 !]> git add *
//	C:\dev\workspaces\tactics [master +1 ~0 -0]> git commit -a
//	[master 6bdca4e] missing file
//	 1 file changed, 14 insertions(+)
//	 create mode 100644 tactics_project/src/com/marklynch/tactics/objects/level/ActivityLog.java
//	C:\dev\workspaces\tactics [master]> git push
//	Counting objects: 75, done.
//	Delta compression using up to 8 threads.
//	Compressing objects: 100% (43/43), done.
//	Writing objects: 100% (56/56), 10.22 KiB | 0 bytes/s, done.
//	Total 56 (delta 24), reused 0 (delta 0)
//	To https://github.com/lynchm3/tactics.git
//	   2bb6ed9..6bdca4e  master -> master
//	C:\dev\workspaces\tactics [master]> git status
//	On branch master
//	Your branch is up-to-date with 'origin/master'.
//
//	Changes not staged for commit:
//	  (use "git add <file>..." to update what will be committed)
//	  (use "git checkout -- <file>..." to discard changes in working directory)
//
//	        modified:   tactics_project/src/com/marklynch/notes/Notes.java
//	        modified:   tactics_project/src/com/marklynch/notes/TODO.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/level/Level.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/unit/Actor.java
//
//	Untracked files:
//	  (use "git add <file>..." to include in what will be committed)
//
//	        tactics_project/res/images/a2r2.png
//	        tactics_project/res/images/a3r1.png
//
//	no changes added to commit (use "git add" and/or "git commit -a")
//	C:\dev\workspaces\tactics [master +2 ~4 -0 !]> git add *
//	C:\dev\workspaces\tactics [master +2 ~4 -0]> git commit -a
//	[master 6195597] Overlay actor's weapons on actor
//	 6 files changed, 54 insertions(+), 7 deletions(-)
//	 create mode 100644 tactics_project/res/images/a2r2.png
//	 create mode 100644 tactics_project/res/images/a3r1.png
//	C:\dev\workspaces\tactics [master]> git status
//	On branch master
//	Your branch is ahead of 'origin/master' by 1 commit.
//	  (use "git push" to publish your local commits)
//
//	Changes not staged for commit:
//	  (use "git add <file>..." to update what will be committed)
//	  (use "git checkout -- <file>..." to discard changes in working directory)
//
//	        modified:   tactics_project/src/com/marklynch/Game.java
//	        modified:   tactics_project/src/com/marklynch/notes/TODO.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/GameObject.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/level/Faction.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/level/Level.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/level/Square.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/unit/Actor.java
//
//	no changes added to commit (use "git add" and/or "git commit -a")
//	C:\dev\workspaces\tactics [master +0 ~7 -0]> git commit -a
//	[master 4dc9ed9] Death and showing health of actors and objects
//	 7 files changed, 92 insertions(+), 46 deletions(-)
//	C:\dev\workspaces\tactics [master]> git push
//	Counting objects: 57, done.
//	Delta compression using up to 8 threads.
//	Compressing objects: 100% (31/31), done.
//	Writing objects: 100% (37/37), 783.56 KiB | 0 bytes/s, done.
//	Total 37 (delta 19), reused 0 (delta 0)
//	To https://github.com/lynchm3/tactics.git
//	   6bdca4e..4dc9ed9  master -> master
//	C:\dev\workspaces\tactics [master]> git status
//	On branch master
//	Your branch is up-to-date with 'origin/master'.
//
//	Changes not staged for commit:
//	  (use "git add <file>..." to update what will be committed)
//	  (use "git checkout -- <file>..." to discard changes in working directory)
//
//	        modified:   tactics_project/src/com/marklynch/Game.java
//	        modified:   tactics_project/src/com/marklynch/notes/TODO.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/GameObject.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/level/Faction.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/level/Level.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/unit/Actor.java
//
//	no changes added to commit (use "git add" and/or "git commit -a")
//	C:\dev\workspaces\tactics [master +0 ~6 -0]> git commit -a
//	[master 0bcbdff] Log end turn on screen, AI to move towards target object, added a second enemy, fixed big where you att
//	ack dead enemies rather than moving to the square they died on
//	 6 files changed, 150 insertions(+), 110 deletions(-)
//	C:\dev\workspaces\tactics [master]> git push
//	Counting objects: 33, done.
//	Delta compression using up to 8 threads.
//	Compressing objects: 100% (14/14), done.
//	Writing objects: 100% (17/17), 3.05 KiB | 0 bytes/s, done.
//	Total 17 (delta 8), reused 0 (delta 0)
//	To https://github.com/lynchm3/tactics.git
//	   4dc9ed9..0bcbdff  master -> master
//	C:\dev\workspaces\tactics [master]> git status
//	On branch master
//	Your branch is up-to-date with 'origin/master'.
//
//	Changes not staged for commit:
//	  (use "git add <file>..." to update what will be committed)
//	  (use "git checkout -- <file>..." to discard changes in working directory)
//
//	        modified:   tactics_project/res/images/a2r2.png
//	        modified:   tactics_project/res/images/a3r1.png
//	        modified:   tactics_project/src/com/marklynch/Game.java
//	        modified:   tactics_project/src/com/marklynch/notes/Notes.java
//	        modified:   tactics_project/src/com/marklynch/notes/Reading.java
//	        modified:   tactics_project/src/com/marklynch/notes/TODO.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/GameObject.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/level/Faction.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/level/Level.java
//	        modified:   tactics_project/src/com/marklynch/tactics/objects/unit/Actor.java
//
//	no changes added to commit (use "git add" and/or "git commit -a")
//	C:\dev\workspaces\tactics [master +0 ~10 -0]> git commit -a
//	[master 7f65fa0] Recentred and zoomed starting camera position, Player attack gameobject, AI attack spcific actor/object
//	, Logging kills and destruction of objects
//	 10 files changed, 143 insertions(+), 50 deletions(-)
//	 rewrite tactics_project/res/images/a2r2.png (99%)
//	 rewrite tactics_project/res/images/a3r1.png (99%)
//	C:\dev\workspaces\tactics [master]> git push
//	Counting objects: 45, done.
//	Delta compression using up to 8 threads.
//	Compressing objects: 100% (20/20), done.
//	Writing objects: 100% (23/23), 11.82 KiB | 0 bytes/s, done.
//	Total 23 (delta 11), reused 0 (delta 0)
//	To https://github.com/lynchm3/tactics.git
//	   0bcbdff..7f65fa0  master -> master
//	C:\dev\workspaces\tactics [master]> git commit -a
//	[master 5aad48d] AI skip move phase if nowhere to move, AI skip attack phase of nothing to attack, stop AI moving around
//	 target when already at ideal distance
//	 2 files changed, 70 insertions(+), 20 deletions(-)
//	C:\dev\workspaces\tactics [master]> git commit -a
//	[master dbddb2f] Fixed - if dude dies on AI team during his turn it skips the AI guy
//	 3 files changed, 20 insertions(+), 14 deletions(-)
//	C:\dev\workspaces\tactics [master]> git push
//	Counting objects: 37, done.
//	Delta compression using up to 8 threads.
//	Compressing objects: 100% (19/19), done.
//	Writing objects: 100% (25/25), 2.79 KiB | 0 bytes/s, done.
//	Total 25 (delta 15), reused 0 (delta 0)
//	To https://github.com/lynchm3/tactics.git
//	   7f65fa0..dbddb2f  master -> master
//	C:\dev\workspaces\tactics [master]> git commit -a
//	[master 1e25b5f] Draw enemy weapons on the right hand side of their square, draw what you can attack the square with
//	 5 files changed, 53 insertions(+), 9 deletions(-)
//	C:\dev\workspaces\tactics [master]> git push
//	Counting objects: 33, done.
//	Delta compression using up to 8 threads.
//	Compressing objects: 100% (13/13), done.
//	Writing objects: 100% (17/17), 1.87 KiB | 0 bytes/s, done.
//	Total 17 (delta 9), reused 0 (delta 0)
//	To https://github.com/lynchm3/tactics.git
//	   dbddb2f..1e25b5f  master -> master
//	C:\dev\workspaces\tactics [master]> git commit -a
//	[master 804f913] Not showing what weapons can attack what square when AI in control
//	 1 file changed, 26 insertions(+), 23 deletions(-)
//	C:\dev\workspaces\tactics [master]> git push
//	Counting objects: 19, done.
//	Delta compression using up to 8 threads.
//	Compressing objects: 100% (7/7), done.
//	Writing objects: 100% (10/10), 925 bytes | 0 bytes/s, done.
//	Total 10 (delta 5), reused 0 (delta 0)
//	To https://github.com/lynchm3/tactics.git
//	   1e25b5f..804f913  master -> master
//	C:\dev\workspaces\tactics [master]> git pull
//	remote: Counting objects: 49, done.
//	remote: Compressing objects: 100% (29/29), done.
//	remote: Total 49 (delta 25), reused 34 (delta 10), pack-reused 0
//	Unpacking objects: 100% (49/49), done.
//	From https://github.com/lynchm3/tactics
//	   804f913..99bdc1f  master     -> origin/master
//	Updating 804f913..99bdc1f
//	error: Your local changes to the following files would be overwritten by merge:
//	        tactics_project/src/com/marklynch/notes/Notes.java
//	        tactics_project/src/com/marklynch/notes/TODO.java
//	Please, commit your changes or stash them before you can merge.
//	Aborting
//	C:\dev\workspaces\tactics [master +1 ~2 -0 !]> git status
//	On branch master
//	Your branch is behind 'origin/master' by 4 commits, and can be fast-forwarded.
//	  (use "git pull" to update your local branch)
//
//	Changes not staged for commit:
//	  (use "git add <file>..." to update what will be committed)
//	  (use "git checkout -- <file>..." to discard changes in working directory)
//
//	        modified:   tactics_project/src/com/marklynch/notes/Notes.java
//	        modified:   tactics_project/src/com/marklynch/notes/TODO.java
//
//	Untracked files:
//	  (use "git add <file>..." to include in what will be committed)
//
//	        tactics_project/src/com/marklynch/notes/DesignRules.java
//
//	no changes added to commit (use "git add" and/or "git commit -a")
//	C:\dev\workspaces\tactics [master +1 ~2 -0 !]> git diff
//	diff --git a/tactics_project/src/com/marklynch/notes/Notes.java b/tactics_project/src/com/marklynch/notes/Notes.java
//	index 124e5b3..7fc8ca4 100644
//	--- a/tactics_project/src/com/marklynch/notes/Notes.java
//	+++ b/tactics_project/src/com/marklynch/notes/Notes.java
//	@@ -876,6 +876,19 @@ public class Notes {

//
//	 }
//	diff --git a/tactics_project/src/com/marklynch/notes/TODO.java b/tactics_project/src/com/marklynch/notes/TODO.java
//	index 70999fb..388a54a 100644
//	--- a/tactics_project/src/com/marklynch/notes/TODO.java
//	+++ b/tactics_project/src/com/marklynch/notes/TODO.java
//	@@ -4,6 +4,10 @@ public class TODO {
//
//	        // TO DO
//

//
//	C:\dev\workspaces\tactics [master +1 ~2 -0 !]>
// }
