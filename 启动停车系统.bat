@echo off
chcp 65001
title 停车系统一键启动器
color 0A

echo ==========================================
echo       正在启动车辆收费管理系统...
echo ==========================================

:: ----------------配置区域 (请修改这里)----------------

:: 1. Python 项目路径
set PY_DIR=D:\javaproject\park_project\fix_park

:: 2. 【关键修改】虚拟环境 Python 的绝对路径
set PY_VENV_EXE="D:\javaproject\park_project\fix_park\.venv\Scripts\python.exe"

:: 3. Java 项目路径
set JAVA_DIR=D:\javaproject\park_project\parking-backend\target

:: 4. Java Jar 包的文件名
set JAR_NAME=parking-backend-0.0.1-SNAPSHOT.jar

:: 5. Java 17 的绝对路径
set JAVA_EXE="D:\javajdk\bin\java.exe"

:: ----------------------------------------------------

echo.
echo [1/3] 正在启动 Python OCR 服务 (使用虚拟环境)...
start "Python OCR Service" cmd /k "cd /d %PY_DIR% && %PY_VENV_EXE% park.py"

echo.
echo [2/3] 正在启动 Java 后端服务...
echo 请等待 Spring Boot 图标出现...
start "Java Parking Backend" cmd /k "cd /d %JAVA_DIR% && %JAVA_EXE% -jar %JAR_NAME%"

:: 等待 15 秒，让 Java 服务有时间启动
echo.
echo 正在等待服务初始化 (15秒)...
timeout /t 15 /nobreak >nul

echo.
echo [3/3] 正在打开登录页面...
:: ▼▼▼▼▼▼▼▼▼▼▼▼ 修改了这里 ▼▼▼▼▼▼▼▼▼▼▼▼
start "Java Parking Front" cmd /k "cd /d D:\javaproject\park_project\front && start login.html"
:: ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

echo.
echo ==========================================
echo           系统启动成功！
echo      请不要关闭弹出的黑色命令窗口
echo ==========================================
pause