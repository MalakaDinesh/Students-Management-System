@echo off
echo Compiling Student Information System...
if not exist bin mkdir bin
javac -d bin -encoding UTF-8 src\model\*.java src\data\*.java src\auth\*.java src\service\*.java src\gui\*.java src\*.java
if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo.
    echo To run the application, use: java -cp bin Main
) else (
    echo Compilation failed!
    pause
)

