@echo off
echo ======================================
echo MotorPH - VS Code File Path Debug Test
echo ======================================

echo Setting working directory to project root...
cd /d "C:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system"

echo.
echo Current directory: %CD%
echo.

echo Checking for CSV files...
if exist "employeeDetails.csv" (
    echo ✓ Found employeeDetails.csv
    echo   Size: 
    dir "employeeDetails.csv" | find "employeeDetails.csv"
) else (
    echo ✗ employeeDetails.csv NOT FOUND
)

if exist "attendanceRecord.csv" (
    echo ✓ Found attendanceRecord.csv
    echo   Size:
    dir "attendanceRecord.csv" | find "attendanceRecord.csv"
) else (
    echo ✗ attendanceRecord.csv NOT FOUND
)

echo.
echo Testing Java compilation and execution...
echo.

echo Compiling Main.java...
javac -cp "target\dependency\*" -d target\classes src\main\java\com\motorph\*.java src\main\java\com\motorph\**\*.java

if %ERRORLEVEL% EQU 0 (
    echo ✓ Compilation successful
    echo.
    echo Running application...
    java -cp "target\classes;target\dependency\*" com.motorph.Main
) else (
    echo ✗ Compilation failed
    echo.
    echo Trying with existing compiled classes...
    if exist target\classes\com\motorph\Main.class (
        echo Found existing Main.class, running...
        java -cp "target\classes;target\dependency\*" com.motorph.Main
    ) else (
        echo No compiled classes found
    )
)

echo.
echo ======================================
echo Debug test completed
echo ======================================
pause
