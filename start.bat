@echo off
echo ========================================
echo   Hotel Management System - Group 2
echo ========================================
echo.
echo Starting Spring Boot application...
echo.
echo Web Interface: http://localhost:8080
echo H2 Console: http://localhost:8080/h2-console
echo.
echo Press Ctrl+C to stop the application
echo ========================================
echo.

cd complete
call gradlew.bat bootRun

pause
