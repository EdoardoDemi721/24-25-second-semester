echo Pulling from git
git pull

echo Adding new files to repo
git add .

echo Committing changes
set /p message="Insert commit message: "
git commit -m "%message%"

echo Pushing changes
git push

pause