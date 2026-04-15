@echo off
REM Build script for inventory-service
cd /d "D:\Real-Time Projects\E-Commerce Project\inventory-service"
echo Building inventory-service...
call mvnw.cmd clean install -DskipTests
if %errorlevel% equ 0 (
    echo Inventory-service built successfully
) else (
    echo Inventory-service build failed
)
pause
