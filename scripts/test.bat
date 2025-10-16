@echo off
:: ==============================================
:: CRJLib Test Suite
:: This batch file compiles and runs the CRJLib test program
:: 
:: Features:
:: - Creates necessary directories
:: - Compiles all source files
:: - Runs comprehensive tests
:: - Provides error feedback
:: 
:: Usage:
:: 1. Open command prompt in project directory
:: 2. Run: test.bat
:: 
:: Author: CJ Remillard
:: Version: 1.0
:: ==============================================

echo Compiling CRJLib test program...

:: Create bin directory if it doesn't exist
:: This directory will contain all compiled .class files
if not exist bin mkdir bin

:: Compile the library and test program
:: -d bin: Output directory for compiled classes
:: src/CRJLib/Std.java: Main library interface
:: src/CRJLib/assets/*.java: All asset classes
:: src/testing/Main.java: Test program
javac -d bin src/CRJLib/Std.java src/CRJLib/assets/*.java src/testing/*.java

:: Check if compilation was successful
:: errorlevel 1 indicates compilation failure
if errorlevel 1 (
    echo Compilation failed!
    echo Please check the error messages above.
    pause
    exit /b 1
)

echo.
echo Running CRJLib tests...
echo.

:: Run the test program
:: -cp bin: Set classpath to bin directory
::java -cp bin testing.Main
java -cp bin testing.WinTest

echo.
echo Test execution completed.
echo Press any key to exit...
pause 