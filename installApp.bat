@echo off
for /f "tokens=1-2 delims=: " %%a in ('time /t') do (set mytime=%%a:%%b)
for /f "tokens=2-4 delims=/ " %%a in ('date /t') do (set mydate=%%c-%%a-%%b)
echo %mydate%_%mytime%
..\..\android-sdk\platform-tools\adb.exe devices && \
..\..\android-sdk\platform-tools\adb.exe install -r apk\TeamCode-debug.apk && \
git add * && \
git commit -m "%mydate%_%mytime% App Installation Successful" && \
git push -u origin robot-team
pause