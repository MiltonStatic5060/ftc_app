git add .
set /P message="Enter a commit (checkpoint saving) message:  "
git commit -m "%message%"
cmd /k git push
