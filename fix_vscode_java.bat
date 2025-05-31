@echo off
echo ======================================
echo MotorPH Payroll System - VS Code Fix
echo ======================================

echo Checking Java installation...
java -version
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java JDK 17 or later
    pause
    exit /b 1
)

echo.
echo Checking project directory...
cd /d "C:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system"
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Cannot access project directory
    pause
    exit /b 1
)

echo.
echo Cleaning and recompiling...
if exist target\classes rmdir /s /q target\classes
mkdir target\classes 2>nul

echo.
echo Compiling Java sources...
javac -cp src\main\java -d target\classes src\main\java\com\motorph\*.java src\main\java\com\motorph\**\*.java
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo.
echo Running application...
java -cp target\classes com.motorph.Main

pause
