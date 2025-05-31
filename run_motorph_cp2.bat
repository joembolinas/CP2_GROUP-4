@echo off
REM Computer Programming 2 - MotorPH Payroll System
REM Simple build and run script for Java project

echo ==========================================
echo Computer Programming 2 - MotorPH Payroll
echo ==========================================

cd /d "c:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system"

echo 🔍 Checking Java version...
java -version

echo 🧹 Cleaning previous build...
mvn clean

echo 🔨 Compiling Java project...
mvn compile

if %ERRORLEVEL% neq 0 (
    echo ❌ Compilation failed!
    echo 💡 This usually means:
    echo   - Java version mismatch in pom.xml
    echo   - Missing dependencies
    echo   - Syntax errors in Java code
    pause
    exit /b 1
)

echo ✅ Compilation successful!
echo 🚀 Starting MotorPH Payroll System...

mvn exec:java

if %ERRORLEVEL% neq 0 (
    echo ❌ Application failed to start!
    echo 💡 Check the error messages above
    pause
    exit /b 1
)

echo ✅ Application completed successfully!
pause
