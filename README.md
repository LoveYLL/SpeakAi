


          
# SpeakAI 智能语音助手

## 🎯 项目简介

SpeakAI 是一个基于人工智能的语音助手应用，集成了语音识别、自然语言处理和语音合成技术，支持多端运行（微信小程序、H5、APP）。项目采用前后端分离架构，提供完整的语音交互解决方案。

## 🏗️ 系统架构

### 技术栈
- **前端**: uni-app (Vue3 + TypeScript + Vite)
- **后端**: Java Spring Boot
- **语音处理**: Python (TTS, ASR, NLP)
- **数据库**: MySQL + Redis
- **部署**: Vercel (前端) + 云服务器 (后端)

### 项目结构
```
speakai-uniapp/
├── speakai-uniapp/          # uni-app前端项目
│   ├── src/
│   │   ├── pages/          # 页面组件
│   │   ├── components/     # 公共组件
│   │   ├── utils/          # 工具函数
│   │   └── static/         # 静态资源
├── speakai-java/           # Java后端服务
│   ├── src/main/java/      # 主代码
│   └── src/test/java/      # 测试代码
├── Pythonprojects/         # Python语音处理
│   ├── src/
│   │   ├── TTS.py         # 文本转语音
│   │   ├── app.py         # Flask API服务
│   │   └── testai.py      # AI测试脚本
└── README.md
```

## 🚀 快速开始

### 环境要求
- Node.js ≥ 16.0.0
- Java ≥ 8
- Python ≥ 3.8
- MySQL ≥ 5.7
- Redis ≥ 3.0

### 前端启动
```bash
# 进入前端目录
cd speakai-uniapp

# 安装依赖
npm install

# 开发环境运行
npm run dev:h5        # H5端
npm run dev:mp-weixin # 微信小程序

# 构建生产版本
npm run build:h5
npm run build:mp-weixin
```

### 后端启动
```bash
# 进入后端目录
cd speakai-java

# 安装依赖并运行
mvn clean install
mvn spring-boot:run
```

### Python语音服务启动
```bash
# 进入Python目录
cd Pythonprojects

# 创建虚拟环境
python -m venv .venv
source .venv/bin/activate  # Linux/Mac
.\.venv\Scripts\activate  # Windows

# 安装依赖
pip install -r requirements.txt

# 启动Flask服务
python src/app.py
```

## 📖 功能特性

### 核心功能
- **语音识别**: 支持中文语音识别，准确率高达95%
- **智能对话**: 基于大语言模型的自然语言理解
- **语音合成**: 支持多种音色和语速调节
- **多端适配**: 微信小程序、H5、APP三端统一
- **用户管理**: 完整的用户注册、登录、权限管理

### 特色功能
- **离线缓存**: 支持离线语音识别和缓存
- **实时翻译**: 语音实时翻译多种语言
- **个性化设置**: 自定义唤醒词和音色偏好
- **场景模式**: 支持会议、学习、生活等多种场景

## 🔧 配置说明

### 环境变量配置
在 `speakai-uniapp/.env` 文件中配置：
```bash
# API基础地址
VITE_API_BASE_URL=http://localhost:8080/api

# 语音识别服务地址
VITE_ASR_SERVICE_URL=http://localhost:5000

# 微信配置
VITE_WECHAT_APP_ID=your_wechat_app_id
```

### 数据库配置
在 `speakai-java/src/main/resources/application.yml` 中配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/speakai?useSSL=false&serverTimezone=UTC
    username: your_username
    password: your_password
  
  redis:
    host: localhost
    port: 6379
    password: your_redis_password
```

## 📱 使用指南

### 微信小程序使用
1. 使用微信开发者工具导入 `speakai-uniapp` 项目
2. 配置小程序AppID
3. 点击编译运行

### H5使用
1. 访问 `http://localhost:3000`
2. 允许浏览器使用麦克风权限
3. 点击开始语音对话

### APP使用
1. 运行 `npm run build:app`
2. 使用HBuilderX云打包或本地打包
3. 安装到手机使用

## 🧪 开发规范

### 代码规范
- 前端遵循 [Vue.js 风格指南](https://cn.vuejs.org/v2/style-guide/)
- Java代码遵循 [阿里巴巴Java开发手册](https://github.com/alibaba/p3c)
- Python代码遵循 [PEP 8](https://pep8.org/)

### 分支管理
- `main`: 主分支，稳定版本
- `develop`: 开发分支，集成测试
- `feature/*`: 功能开发分支
- `hotfix/*`: 紧急修复分支

## 📊 性能指标

| 指标 | 数值 |
|------|------|
| 语音识别准确率 | ≥95% |
| 语音合成自然度 | ≥4.5/5.0 |
| 接口响应时间 | ≤500ms |
| 并发用户数 | ≥1000 |

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 👥 联系方式

- **项目主页**: [https://github.com/your-username/speakai](https://github.com/your-username/speakai)
- **问题反馈**: [Issues](https://github.com/your-username/speakai/issues)
- **邮箱**: your-email@example.com

## 🙏 致谢

- 感谢 [uni-app](https://uniapp.dcloud.io/) 提供的跨端开发框架
- 感谢 [科大讯飞](https://www.xfyun.cn/) 提供的语音识别技术支持
- 感谢所有贡献者和使用者的支持

---

**⭐ 如果这个项目对您有帮助，请给个Star支持一下！**
        