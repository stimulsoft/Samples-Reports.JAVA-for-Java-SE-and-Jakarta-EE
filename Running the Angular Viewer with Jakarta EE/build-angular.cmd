cd viewer
set NODE_OPTIONS=--openssl-legacy-provider
call npm i --force
IF %ERRORLEVEL% NEQ 0 ( 
set NODE_OPTIONS=
call npm i --force
)
call ng build --output-hashing none --output-path ..\src\main\webapp\ --delete-output-path=false
