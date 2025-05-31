@echo off
echo ==========================================
echo MotorPH System Verification
echo ==========================================

echo [1/5] Checking Java installation...
java -version 2>nul
if %ERRORLEVEL% neq 0 (
    echo ❌ Java not found or not in PATH
    goto :error
) else (
    echo ✅ Java is available
)

echo.
echo [2/5] Checking Maven installation...
mvn -version 2>nul
if %ERRORLEVEL% neq 0 (
    echo ❌ Maven not found or not in PATH
    goto :error
) else (
    echo ✅ Maven is available
)

echo.
echo [3/5] Checking project structure...
cd /d "c:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system"
if not exist "pom.xml" (
    echo ❌ pom.xml not found
    goto :error
) else (
    echo ✅ pom.xml found
)

if not exist "src\main\java\com\motorph\Main.java" (
    echo ❌ Main.java not found
    goto :error
) else (
    echo ✅ Main.java found
)

echo.
echo [4/5] Checking data files...
if not exist "employeeDetails.csv" (
    echo ❌ employeeDetails.csv not found
    goto :error
) else (
    echo ✅ employeeDetails.csv found
)

if not exist "attendanceRecord.csv" (
    echo ❌ attendanceRecord.csv not found
    goto :error
) else (
    echo ✅ attendanceRecord.csv found
)

echo.
echo [5/5] Testing compilation...
mvn clean compile -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Compilation failed
    goto :error
) else (
    echo ✅ Compilation successful
)

echo.
echo ==========================================
echo ✅ ALL CHECKS PASSED
echo ==========================================
echo.
echo Starting MotorPH Payroll System...
mvn exec:java -Dexec.mainClass="com.motorph.Main"
goto :end

:error
echo.
echo ==========================================
echo ❌ SYSTEM CHECK FAILED
echo ==========================================
echo Please fix the issues above before running the application.

:end
pause
