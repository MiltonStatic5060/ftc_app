@echo off
for /f "tokens=1-2 delims=: " %%a in ('time /t') do (set mytime=%%a:%%b)
for /f "tokens=2-4 delims=/ " %%a in ('date /t') do (set mydate=%%c-%%a-%%b)
echo %mydate%_%mytime%
git add *
gradlew.bat assembleDebug && copy /Y TeamCode\build\outputs\apk\TeamCode-debug.apk apk\TeamCode-debug.apk && echo The newest apk is in the apk folder. && pause
pause