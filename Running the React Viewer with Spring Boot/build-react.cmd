cd viewer
call npm i
call npm run build
xcopy /E /Y build\* ..\src\main\resources\static\
cd ..
