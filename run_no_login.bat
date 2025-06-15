@echo off
echo === MotorPH Application - Login Disabled ===
echo.

cd /d "c:\Users\ADMIN\Desktop\developerFiles\CP2_GROUP-4\motorph_payroll_system"

echo Recompiling CredentialManager with login disabled...
javac -cp "src\main\java;target\classes;C:\Users\ADMIN\.m2\repository\org\apache\commons\commons-csv\1.9.0\commons-csv-1.9.0.jar" -d target\classes src\main\java\com\motorph\repository\CredentialManager.java

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo ✅ CredentialManager compiled successfully!
echo.
echo Starting MotorPH Application (Login bypassed)...
echo *** You can enter any username/password - authentication will always succeed ***
echo.

java -cp "target\classes;C:\Users\ADMIN\.m2\repository\org\apache\commons\commons-csv\1.9.0\commons-csv-1.9.0.jar;C:\Users\ADMIN\.m2\repository\com\opencsv\opencsv\5.7.1\opencsv-5.7.1.jar" com.motorph.Main

echo.
echo Application ended.
pause
