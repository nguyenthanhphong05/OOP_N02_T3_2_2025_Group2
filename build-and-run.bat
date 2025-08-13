@echo off
echo ========================================
echo   Hotel Management System - Build Script
echo ========================================
echo.

echo Step 1: Building the application...
cd complete
call gradlew.bat clean build -x test
if %ERRORLEVEL% neq 0 (
    echo Build failed! Please check for errors.
    pause
    exit /b 1
)

echo.
echo Step 2: Starting the application...
echo Web Interface: http://localhost:8080
echo H2 Console: http://localhost:8080/h2-console
echo.
echo Press Ctrl+C to stop the application
echo ========================================
echo.

call gradlew.bat bootRun

pause
