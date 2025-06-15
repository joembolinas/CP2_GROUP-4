@echo off
echo === MotorPH Application - Fixed Version ===

cd /d "c:\Users\ADMIN\Desktop\developerFiles\CP2_GROUP-4\motorph_payroll_system"

echo Current directory: %CD%
echo.
echo Checking compiled classes...
dir target\classes\com\motorph\Main.class
dir target\classes\com\motorph\view\LogIn.class  
dir target\classes\com\motorph\repository\CredentialManager.class
echo.

echo Starting application...
echo Working directory: %CD%
echo Credentials file check:
dir data\employeeCredentials.csv
echo.
java -cp "target\classes;C:\Users\ADMIN\.m2\repository\org\apache\commons\commons-csv\1.9.0\commons-csv-1.9.0.jar;C:\Users\ADMIN\.m2\repository\com\opencsv\opencsv\5.7.1\opencsv-5.7.1.jar" com.motorph.Main

echo.
echo Application ended.
pause
