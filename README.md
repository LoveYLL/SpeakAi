          
# SpeakAi - AI语音学习平台

## 🎯 项目介绍

SpeakAi 是一个基于AI技术的语音学习平台，通过语音识别、自然语言处理和语音合成技术，帮助用户提升英语口语能力。系统提供实时发音评分、错词分析、场景对话等功能，让英语学习更加智能化和个性化。

## 🏗️ 系统架构

### 技术栈
- **前端**: Vue3 + Uniapp (跨平台小程序、H5、APP)
- **后端**: Spring Boot 3.x + MyBatis Plus
- **AI服务**: 
  - 语音识别：Whisper
  - 语音合成：Azure TTS
  - 大语言模型：Ollama (Llama3.2)
- **消息队列**: Apache Kafka
- **搜索引擎**: Elasticsearch
- **对象存储**: 阿里云OSS
- **数据库**: MySQL 8.0

### 核心功能模块

#### 1. 发音评估系统
- **音频上传**: 支持多种音频格式
- **语音识别**: 使用Whisper进行高精度语音转文字
- **发音评分**: 基于编辑距离算法计算发音准确度
- **错词分析**: 自动识别用户发音错误的单词

#### 2. 错词管理系统
- **实时收集**: 通过Kafka消息队列实时收集错词
- **智能存储**: 数据库存储 + Elasticsearch索引
- **搜索功能**: 支持错词关键词搜索和分页查询
- **定时同步**: 每30分钟自动同步数据到ES

#### 3. 场景对话系统
- **多场景支持**: 日常对话、商务英语、旅游英语等
- **角色扮演**: 支持不同角色语音选择
- **语音合成**: 使用Azure TTS生成自然语音
- **大模型对话**: 基于Ollama的智能对话引擎

#### 4. 用户认证系统
- **JWT认证**: 使用Sa-Token实现无状态认证
- **用户注册**: 支持用户名密码注册
- **密码加密**: MD5加密存储用户密码
- **跨域支持**: 完善的CORS配置

#### 5. 实时通信系统
- **WebSocket**: 支持实时消息推送
- **用户管理**: 基于用户名的连接管理
- **消息编码**: 支持复杂对象的JSON编码传输

## 🚀 快速开始

### 环境要求
- Java 17+
- Node.js 16+
- MySQL 8.0
- Redis (可选)
- Elasticsearch 8.x
- Apache Kafka
- Ollama + Llama3.2

### 后端启动

1. **克隆项目**
```bash
git clone https://github.com/yourusername/speakai.git
cd speakai/speakai-java
```

2. **配置数据库**
```sql
CREATE DATABASE speakai DEFAULT CHARACTER SET utf8mb4;
-- 执行数据库初始化脚本
```

3. **配置应用**
编辑 `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/speakai
    username: your_username
    password: your_password
  
aliyun:
  oss:
    endpoint: your_oss_endpoint
    access-key-id: your_access_key
    access-key-secret: your_access_secret
    bucket-name: your_bucket_name
```

4. **启动服务**
```bash
# 启动MySQL
# 启动Kafka
# 启动Elasticsearch
# 启动Ollama
ollama run llama3.2:3b

# 启动后端
mvn spring-boot:run
```

### 前端启动

1. **安装依赖**
```bash
cd speakai/speakai-uniapp
npm install
```

2. **开发环境**
```bash
# H5开发
npm run dev:h5

# 微信小程序开发
npm run dev:mp-weixin

# APP开发
npm run dev:app
```

3. **生产构建**
```bash
# H5构建
npm run build:h5

# 微信小程序构建
npm run build:mp-weixin
```

## 📋 API文档

### 认证接口
- **POST** `/api/auth/login` - 用户登录
- **POST** `/api/auth/register` - 用户注册

### 发音评估
- **POST** `/api/audio/score` - 音频评分
- **POST** `/api/whisper/text` - 语音转文字

### 错词管理
- **GET** `/api/es/search` - 搜索错词

### 场景对话
- **GET** `/api/reference/random` - 获取随机参考文本
- **POST** `/api/conversation/chat` - 场景对话
- **POST** `/api/tts/generate` - 文本转语音

### WebSocket
- **WS** `/websocket/{username}` - 实时通信

## 🗄️ 数据库设计

### 用户表 (user)
```sql
CREATE TABLE user (
  user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### 错词表 (pronunciation_diff_words)
```sql
CREATE TABLE pronunciation_diff_words (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  wrong_word VARCHAR(100) NOT NULL,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### 参考文本表 (reference_text)
```sql
CREATE TABLE reference_text (
  reference_text_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  content TEXT NOT NULL,
  level VARCHAR(20),
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### 角色语音表 (role_voice)
```sql
CREATE TABLE role_voice (
  role_code_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  role_code VARCHAR(50) UNIQUE NOT NULL,
  voice VARCHAR(100),
  description TEXT,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

## 🛠️ 开发规范

### 代码规范
- 使用Java 17新特性
- 遵循Spring Boot最佳实践
- 统一使用MyBatis Plus进行数据库操作
- 统一返回Result格式

### 分支管理
- `main` - 主分支
- `develop` - 开发分支
- `feature/*` - 功能分支
- `hotfix/*` - 紧急修复分支

### 提交规范
```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式调整
refactor: 代码重构
test: 测试相关
chore: 构建/工具相关
```

## 🎯 功能路线图

### 已完成功能
- ✅ 用户注册/登录
- ✅ 音频上传与评分
- ✅ 错词收集与存储
- ✅ 场景对话系统
- ✅ 语音合成功能
- ✅ Elasticsearch搜索
- ✅ WebSocket实时通信

### 计划功能
- 🔄 用户学习进度跟踪
- 🔄 个性化学习推荐
- 🔄 社交学习功能
- 🔄 移动端APP
- 🔄 多语言支持
- 🔄 AI语音评测升级

## 🤝 贡献指南

我们欢迎社区贡献！请按以下步骤参与：

1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系我们

- **邮箱**: your.email@example.com
- **微信**: your_wechat
- **QQ群**: 123456789

## 🙏 致谢

- [Whisper](https://github.com/openai/whisper) - 语音识别
- [Ollama](https://ollama.ai) - 大语言模型
- [Spring Boot](https://spring.io/projects/spring-boot) - 后端框架
- [Uniapp](https://uniapp.dcloud.io) - 跨平台框架
- [Vue.js](https://vuejs.org) - 前端框架

---

**⭐ 如果这个项目对你有帮助，请给个Star支持一下！**
        