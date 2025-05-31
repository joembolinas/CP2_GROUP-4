@echo off
echo =====================================
echo MotorPH System Status Check
echo =====================================

cd /d "c:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system"

echo [1/4] Checking Java version...
java -version

echo.
echo [2/4] Checking Maven version...  
mvn -version

echo.
echo [3/4] Verifying CSV files exist...
if exist "employeeDetails.csv" (
    echo ✅ employeeDetails.csv found
) else (
    echo ❌ employeeDetails.csv missing
)

if exist "attendanceRecord.csv" (
    echo ✅ attendanceRecord.csv found  
) else (
    echo ❌ attendanceRecord.csv missing
)

echo.
echo [4/4] Testing compilation...
mvn compile -q
if %ERRORLEVEL% equ 0 (
    echo ✅ Compilation successful
    echo.
    echo =====================================
    echo Starting MotorPH Application...
    echo =====================================
    mvn exec:java -Dexec.mainClass="com.motorph.Main"
) else (
    echo ❌ Compilation failed
)

pause
