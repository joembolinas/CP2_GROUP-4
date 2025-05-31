@echo off
echo Building and running MotorPH Payroll System...
echo.

REM Clean and compile the project
mvn clean compile

REM Copy dependencies
mvn dependency:copy-dependencies

REM Run the application
echo Starting application...
java -cp "target/classes;target/dependency/*" com.motorph.Main

pause
