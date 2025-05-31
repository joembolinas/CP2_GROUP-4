@echo off
setlocal enabledelayedexpansion

echo ========================================
echo MotorPH Payroll System - Complete Test
echo ========================================
echo.

:: Set the correct directory
cd /d "c:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system"
echo Working directory: %CD%
echo.

:: Check Java version
echo Checking Java installation:
java -version
if %ERRORLEVEL% neq 0 (
    echo ❌ Java not found or not in PATH
    goto :end
)
echo.

:: Check required CSV files
echo Checking CSV files:
if exist "employeeDetails.csv" (
    echo ✅ employeeDetails.csv found
) else (
    echo ❌ employeeDetails.csv missing
    goto :end
)

if exist "attendanceRecord.csv" (
    echo ✅ attendanceRecord.csv found
) else (
    echo ❌ attendanceRecord.csv missing
    goto :end
)
echo.

:: Check compiled classes
echo Checking compiled classes:
if exist "target\classes\com\motorph\Main.class" (
    echo ✅ Main.class found
) else (
    echo ❌ Main.class missing - attempting to compile
    goto :compile
)

if exist "target\classes\com\motorph\repository\DataRepository.class" (
    echo ✅ DataRepository.class found
) else (
    echo ❌ DataRepository.class missing - attempting to compile
    goto :compile
)

goto :run_csv_test

:compile
echo.
echo Attempting to compile project...
if exist "pom.xml" (
    echo Using Maven to compile...
    mvn compile -q
    if %ERRORLEVEL% equ 0 (
        echo ✅ Maven compilation successful
        goto :run_csv_test
    ) else (
        echo ❌ Maven compilation failed - trying javac
    )
)

echo Using javac to compile...
cd src\main\java
javac -cp "..\..\..\target\classes" -d "..\..\..\target\classes" com\motorph\*.java com\motorph\*\*.java
cd ..\..\..\
echo.

:run_csv_test
echo ========================================
echo Running CSV Load Test
echo ========================================

:: Get the classpath
if exist "classpath.txt" (
    set /p FULL_CLASSPATH=<classpath.txt
    echo Using classpath from classpath.txt
) else (
    echo Classpath file not found - using basic classpath
    set FULL_CLASSPATH=target\classes
)

:: Test CSV loading first
echo Testing CSV data loading...
java -cp "!FULL_CLASSPATH!;target\classes" com.motorph.test.CSVLoadTest

if %ERRORLEVEL% neq 0 (
    echo ❌ CSV load test failed
    echo.
    echo Trying alternative approach...
    java -cp "target\classes" com.motorph.test.CSVLoadTest
)

echo.
echo ========================================
echo Running Main Application
echo ========================================
echo Starting MotorPH Application...
echo Command: java -cp "!FULL_CLASSPATH!;target\classes" com.motorph.Main
echo.

java -cp "!FULL_CLASSPATH!;target\classes" com.motorph.Main

echo.
echo ========================================
echo Application finished with exit code: %ERRORLEVEL%
echo ========================================

:end
echo.
echo Press any key to continue...
pause >nul
