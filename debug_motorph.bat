@echo off
echo Checking Java and Maven setup...
echo.

echo Java Version:
java -version
echo.

echo Java Compiler Version:
javac -version
echo.

echo Checking if Maven is available:
where mvn
echo.

echo Maven Version (if available):
mvn -version
echo.

echo Checking project directory:
cd /d "c:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system"
echo Current directory: %CD%
echo.

echo Checking if compiled classes exist:
if exist "target\classes\com\motorph\Main.class" (
    echo ✅ Main.class found
) else (
    echo ❌ Main.class not found - need to compile
)

echo.
echo Attempting direct compilation with javac:
cd /d "c:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system\src\main\java"

echo Compiling Main.java...
javac -d "..\..\..\target\classes" com\motorph\Main.java 2>&1

echo.
echo Checking compilation result:
if exist "..\..\..\target\classes\com\motorph\Main.class" (
    echo ✅ Main.class compiled successfully
) else (
    echo ❌ Compilation failed
)

pause
