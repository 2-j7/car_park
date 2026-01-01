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






//*************************************************

🚀 系统特色功能
1. 双模式车牌识别
AI拍照识别：调用Python PaddleOCR服务

手动输入：备用输入方式

2. 智能计费系统
基于停车时长计费

30天周期数据归档

月度报表导出功能

3. 模拟扫码支付
局域网内手机扫码支付

支付状态实时轮询

支付成功后自动抬杆

4. 数据统计可视化
实时在场车辆统计

收益分析图表

历史记录查询

5. 管理员登录
简单账号密码验证

业务逻辑与权限分离

📊 技术架构图
text
用户前端 (HTML/JS/CSS)
    ↓ HTTP请求
Spring Boot后端 (Java)
    ↓ REST API
    ├── 车辆管理业务逻辑
    ├── 支付状态管理
    └── 数据统计服务
    ↓ JDBC
MySQL数据库
    ↑
Python OCR服务
    ↓ AI识别
PaddleOCR模型
🔧 部署说明
端口配置：
前端页面：浏览器直接打开HTML文件

Java后端：8080端口

Python OCR服务：5000端口

网络要求：
局域网可访问（手机支付需要）

外网访问需配置端口转发

性能优化：
Python服务开启CPU加速（MKLDNN）

图像预处理减少OCR计算量

前端图表按需加载

数据库索引优化