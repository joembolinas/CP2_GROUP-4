@echo off
echo ========================================
echo    MotorPH Payroll System - Launcher
echo ========================================
echo.

echo Building project...
mvn clean compile

echo.
echo Copying dependencies...
mvn dependency:copy-dependencies

echo.
echo Starting MotorPH Payroll System...
echo (Close the GUI window to return to this prompt)
java -cp "target/classes;target/dependency/*" com.motorph.Main

echo.
echo Application closed.
pause
