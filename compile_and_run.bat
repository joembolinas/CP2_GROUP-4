@echo off
echo === Complete MotorPH Compilation ===

cd /d "c:\Users\ADMIN\Desktop\developerFiles\CP2_GROUP-4\motorph_payroll_system"

echo Cleaning target directory...
rmdir /s /q target\classes 2>nul
mkdir target\classes 2>nul

echo Compiling all Java files...
javac -cp "src\main\java;C:\Users\ADMIN\.m2\repository\org\apache\commons\commons-csv\1.9.0\commons-csv-1.9.0.jar;C:\Users\ADMIN\.m2\repository\com\opencsv\opencsv\5.7.1\opencsv-5.7.1.jar;C:\Users\ADMIN\.m2\repository\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar;C:\Users\ADMIN\.m2\repository\commons-beanutils\commons-beanutils\1.9.4\commons-beanutils-1.9.4.jar;C:\Users\ADMIN\.m2\repository\commons-collections\commons-collections\3.2.2\commons-collections-3.2.2.jar" -d target\classes -sourcepath src\main\java src\main\java\com\motorph\*.java src\main\java\com\motorph\model\*.java src\main\java\com\motorph\repository\*.java src\main\java\com\motorph\service\*.java src\main\java\com\motorph\controller\*.java src\main\java\com\motorph\view\*.java src\main\java\com\motorph\view\dialog\*.java src\main\java\com\motorph\view\renderer\*.java src\main\java\com\motorph\util\*.java src\main\java\com\motorph\test\*.java

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo ✅ All classes compiled successfully!
echo.
echo Copying resources...
xcopy /s /y src\main\resources\*.* target\classes\ 2>nul

echo.
echo === Starting MotorPH Application ===
java -cp "target\classes;C:\Users\ADMIN\.m2\repository\org\apache\commons\commons-csv\1.9.0\commons-csv-1.9.0.jar;C:\Users\ADMIN\.m2\repository\com\opencsv\opencsv\5.7.1\opencsv-5.7.1.jar;C:\Users\ADMIN\.m2\repository\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar;C:\Users\ADMIN\.m2\repository\commons-beanutils\commons-beanutils\1.9.4\commons-beanutils-1.9.4.jar;C:\Users\ADMIN\.m2\repository\commons-collections\commons-collections\3.2.2\commons-collections-3.2.2.jar" com.motorph.Main

echo.
echo Application finished.
pause
