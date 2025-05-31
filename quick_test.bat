@echo off
echo Testing MotorPH Application Launch...
echo.

cd /d "c:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system"

echo Checking Java version:
java -version
echo.

echo Checking if Main class exists:
if exist "target\classes\com\motorph\Main.class" (
    echo ✅ Main.class found
) else (
    echo ❌ Main.class not found
    goto :end
)

echo.
echo Checking CSV files:
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
echo Attempting to run application...
java -cp "target\classes" com.motorph.Main
echo.
echo Application finished with exit code: %ERRORLEVEL%

:end
pause
