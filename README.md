# 智慧停车系统 (Smart Parking System)

本项目包含三个部分：
1. **Frontend**: 前端页面 (Vue/React)
2. **Backend**: 业务后端 (Spring Boot)
3. **OCR Service**: 车牌识别服务 (Python + PaddleOCR)

---

## 🚀 快速启动指南

### 1. 环境准备
确保你的电脑安装了以下环境：
- JDK 1.8+
- Node.js 16+
- Python 3.8+
- MySQL 数据库

---

### 📂 模块一：Python 车牌识别服务 (fix_park)

该服务负责接收图片并返回车牌号。

1. **进入目录**：
   ```bash
   cd fix_park

   # 建议先创建虚拟环境
python -m venv .venv
# Windows 激活环境
.venv\Scripts\activate
# 安装库
pip install -r requirements.txt

python app.py


📂 模块二：后端服务 (parking-backend)
Spring Boot 核心业务接口。
配置数据库：
修改 src/main/resources/application.yml 中的数据库账号密码。
启动：
使用 IntelliJ IDEA 打开该文件夹，运行主启动类即可。
服务将运行在端口 8080 (或其他配置端口)

📂 模块三：前端页面 (front)
用户操作界面。
进入目录：
code
Bash
cd front
安装依赖：
code
Bash
npm install
启动开发服务器：
code
Bash
npm run serve
# 或者
npm run dev


## 数据库配置
请在 MySQL Workbench (或 Navicat) 中导入项目根目录下的 `database/parking.sql` 文件。
注意事项
确保 Python 服务的 5000 端口未被占用。
首次运行 Python 服务会自动下载 OCR 模型，请保持网络通畅。