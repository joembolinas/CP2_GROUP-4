# MotorPH Payroll System - PowerShell Launcher
# Computer Programming 2 Project

Write-Host "===========================================" -ForegroundColor Blue
Write-Host "Computer Programming 2 - MotorPH Payroll" -ForegroundColor Blue
Write-Host "===========================================" -ForegroundColor Blue

# Change to project directory
Set-Location "c:\Users\ADMIN\CP2_GROUP-4\motorph_payroll_system"

Write-Host "🔍 Checking Java environment..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1
    Write-Host "Java version detected" -ForegroundColor Green
} catch {
    Write-Host "❌ Java not found! Please install Java" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "🧹 Cleaning previous build..." -ForegroundColor Yellow
try {
    mvn clean | Out-Null
    Write-Host "✅ Clean successful" -ForegroundColor Green
} catch {
    Write-Host "❌ Maven clean failed!" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "🔨 Compiling project..." -ForegroundColor Yellow
try {
    mvn compile
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Compilation successful!" -ForegroundColor Green
    } else {
        throw "Compilation failed"
    }
} catch {
    Write-Host "❌ Compilation failed!" -ForegroundColor Red
    Write-Host "💡 Common causes:" -ForegroundColor Yellow
    Write-Host "  - Java version mismatch in pom.xml" -ForegroundColor Yellow
    Write-Host "  - Missing dependencies" -ForegroundColor Yellow
    Write-Host "  - Syntax errors in Java code" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "🚀 Starting MotorPH Payroll System..." -ForegroundColor Yellow
try {
    mvn exec:java
    Write-Host "✅ Application completed successfully!" -ForegroundColor Green
} catch {
    Write-Host "❌ Application failed to start!" -ForegroundColor Red
    Write-Host "💡 Check the error messages above" -ForegroundColor Yellow
}

Read-Host "Press Enter to exit"
