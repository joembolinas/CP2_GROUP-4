@echo off
echo === MPHCR-02 Implementation Test ===
echo Compiling project...

cd /d "c:\Users\ADMIN\Desktop\developerFiles\CP2_GROUP-4\motorph_payroll_system"

mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo ✅ Compilation successful!
echo.
echo Running MPHCR-02 test...

mvn exec:java -Dexec.mainClass="com.motorph.test.MPHCR02Test"

echo.
echo Test completed. Press any key to continue...
pause
