cd viewer
call npm i --no-save
call npm run build
xcopy /E /Y build\* ..\src\main\resources\static\
cd ..
