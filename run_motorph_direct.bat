@echo off
echo ========================================
echo MotorPH Direct Java Execution Test
echo ========================================
echo.

cd /d "c:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system"

echo Current directory: %CD%
echo.

echo Checking for required files:
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

if exist "target\classes\com\motorph\Main.class" (
    echo ✅ Main.class found
) else (
    echo ❌ Main.class missing
    goto :end
)

echo.
echo Checking classpath file:
if exist "classpath.txt" (
    echo ✅ classpath.txt found
    set /p CLASSPATH=<classpath.txt
    echo Using classpath: %CLASSPATH%
) else (
    echo ❌ classpath.txt missing - using basic classpath
    set CLASSPATH=target\classes
)

echo.
echo ========================================
echo Starting MotorPH Application
echo ========================================
echo Command: java -cp "%CLASSPATH%;target\classes" com.motorph.Main
echo.

java -cp "%CLASSPATH%;target\classes" com.motorph.Main

echo.
echo ========================================
echo Application finished with exit code: %ERRORLEVEL%
echo ========================================

:end
pause
