<template>
  <view class="chat-container">
    <!-- 顶部导航栏 -->
    <view class="header">
      <view class="header-content">
        <view class="logo-section">
          <view class="logo-icon" @click="uni.switchTab({url:'/pages/dev/scene'})">
            <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path d="M15 19l-7-7 7-7" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"></path>
            </svg>
          </view>
          <view class="logo-icon">
            <text class="logo-icon-text">🎤</text>
          </view>
          <view class="logo-text">
            <text class="logo-title">SpeakAI</text>
          </view>
        </view>
        <view class="user-section">
          <button class="notification-btn">
            <text class="notification-icon">🔔</text>
            <view class="notification-dot"></view>
          </button>
          <view class="user-info">
            <image class="user-avatar" src="https://design.gemcoder.com/staticResource/echoAiSystemImages/07fb0458edddcdf97995bbb0583259c8.png" />
            <text class="user-name">张明</text>
          </view>
        </view>
      </view>
    </view>

    <!-- 主要内容区域 -->
    <view class="main-content">
      <!-- 会话头部 -->
      <view class="session-header">
        <view class="session-info">
          <image class="ai-avatar" src="https://design.gemcoder.com/staticResource/echoAiSystemImages/6c551c902692fa1ea92f548ed99b0a48.png" />
          <view class="ai-info">
            <text class="ai-name">{{ title }}</text>
          </view>
        </view>
        <view class="session-actions">
          <button class="action-btn"><text>📞</text></button>
          <button class="action-btn"><text>📹</text></button>
        </view>
      </view>

      <!-- 对话消息区域 -->
      <!-- 对话消息区域 -->
      <view class="chat-content-wrapper">
        <view class="chat-messages" >
          <!-- 日期显示 -->
          <view class="date-divider">
            <text class="date-text">{{ currentDate }}</text>
          </view>
      
          <!-- 消息列表 -->
          <view v-for="(message, index) in messages" :key="index" class="message-item">
            <!-- AI消息 -->
            <view v-if="message.type === 'ai'" class="ai-message">
              <image class="message-avatar" src="https://design.gemcoder.com/staticResource/echoAiSystemImages/7888ba3d404c3cf7a8aa427428b4cac9.png" />
              <view class="message-content">
                <view class="ai-bubble">
                  <text>{{ message.content }}</text>
                  <text style="margin-left: 10px;">
                    <svg t="1753522522154"  viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1499" width="24" height="24" @click="playAudio(message.audioUrl)">
                      <path d="M544 131.296v761.408L320 736H128V288h192l224-156.704zM480 256l-128 96H192v320h160l128 96V256zM693.056 212.096l27.424 16.48C824.96 291.296 896 393.6 896 512c0 118.4-71.072 220.672-175.52 283.424l-27.424 16.48-32.96-54.848 27.424-16.48C777.408 686.56 832 602.976 832 512c0-90.944-54.592-174.56-144.48-228.576l-27.424-16.48 32.96-54.848z" fill="#dbdbdb" p-id="1500">
                        </path><path d="M666.368 356.128l-27.872-15.712-31.424 55.744 27.872 15.712C679.872 437.152 704 474.432 704 512s-24.128 74.816-69.056 100.128l-27.84 15.68 31.36 55.776 27.904-15.68C725.12 634.752 768 579.072 768 512s-42.88-122.784-101.632-155.872z" fill="#dbdbdb" p-id="1501"></path>
                    </svg>
                  </text>
                </view>
                <text class="message-time">{{ message.time }}</text>
              </view>
            </view>
      
            <!-- 用户消息 -->
            <view v-else class="user-message">
              <view class="message-content">
                <view class="user-bubble">
                  <text>{{ message.content }}</text>
                </view>
                <view class="message-status">
                  <text class="message-time">{{ message.time }}</text>
                  <text class="status-icon">✓</text>
                </view>
              </view>
              <image class="message-avatar" src="https://design.gemcoder.com/staticResource/echoAiSystemImages/07fb0458edddcdf97995bbb0583259c8.png" />
            </view>
          </view>
      
          <!-- 正在输入指示器 -->
          <view v-if="isTyping" class="typing-indicator">
            <image class="message-avatar" src="https://design.gemcoder.com/staticResource/echoAiSystemImages/7888ba3d404c3cf7a8aa427428b4cac9.png" />
            <view class="message-content">
              <view class="ai-bubble typing-bubble">
                <view class="typing-animation">
                  <view class="dot"></view>
                  <view class="dot"></view>
                  <view class="dot"></view>
                </view>
              </view>
              <text class="message-time">正在输入...</text>
            </view>
          </view>
        </view>
      </view>
      
      <!-- 输入区域 -->
      <view class="input-area">
        <view class="input-box">
          <input
            class="message-input"
            v-model="inputMessage"
            placeholder="输入你的消息...按Enter发送"
            @keydown.enter="sendMessage"
          />
          <button :class="['send-btn',{ 'recording': isRecording }]" 
            @click="taggleRecording"
          >
           <svg class="record-icon" fill="currentColor" height="24px" viewBox="0 0 256 256" width="24px" xmlns="http://www.w3.org/2000/svg">
              <path d="M128,176a48.05,48.05,0,0,0,48-48V64a48,48,0,0,0-96,0v64A48.05,48.05,0,0,0,128,176ZM96,64a32,32,0,0,1,64,0v64a32,32,0,0,1-64,0Zm40,143.6V232a8,8,0,0,1-16,0V207.6A80.11,80.11,0,0,1,48,128a8,8,0,0,1,16,0,64,64,0,0,0,128,0,8,8,0,0,1,16,0A80.11,80.11,0,0,1,136,207.6Z"></path>
            </svg>
          </button>
        </view>
      </view>
    </view>
  </view>
</template>
<script setup>
import { ref, computed, nextTick } from 'vue'
import { Post, Postchat } from '../../utils/request'
import Recorder from 'js-audio-recorder'
import { onLoad } from '@dcloudio/uni-app'

let recorder = new Recorder()
const blobWav = ref(new Blob())
const request = ref({
  text:'',
  workspace:'',
})
const title = ref('')
const ss = () => {
  blobWav.value = recorder.getWAVBlob()
  recorder.stopStream()
  console.log(blobWav.value);
}
// 响应式数据
const messages = ref([

])

const inputMessage = ref('')
const isTyping = ref(false)
const scrollTop = ref(0)
const isRecording = ref(false)
const currentDate = computed(() => {
const now = new Date()
return now.toLocaleDateString('zh-CN', {
  year: 'numeric',
  month: 'long',
  day: 'numeric',
  weekday: 'long'
  })
})

const taggleRecording = () => {
  if(!isRecording.value){
    console.log('开始录音');
    recorder.start()
    isRecording.value = true
    uni.showToast({
      title: '开始录音...',
      icon: 'none',
      duration: 2000
    })
  }else{
    console.log('停止录音');
    isRecording.value = false
    blobWav.value = recorder.getWAVBlob()
    recorder.stopStream()
    request.value.text = ''
    getResp(blobWav.value)
  }
}



const playAudio = (src) => {
  const au = uni.createInnerAudioContext()
  au.src = src
  au.play()
}


// 方法定义
const sendMessage = () => {
  const message = inputMessage.value.trim()
  if (!message) return
  request.value.text = message
  // 添加用户消息
  messages.value.push({
    type: 'user',
    content: message,
    time: getCurrentTime()
  })
  inputMessage.value = ''
  scrollToBottom()
  getResp()
}

const scrollToBottom = () => {
  nextTick(() => {
    const container = document.querySelector('.chat-messages')
    if (container) {
      container.scrollTop = container.scrollHeight
    }
  })
}

const getCurrentTime = () => {
  const now = new Date()
  return now.getHours().toString().padStart(2, '0') + ':' + 
         now.getMinutes().toString().padStart(2, '0')
}
 
const getResp = (file) => {
  Postchat('conversation/chat',request.value,file).then(resp => { 
    console.log(resp.data);
    messages.value.push({
    type: 'ai',
    content: resp.data.text,
    audioUrl: resp.data.audioUrl,
    time: getCurrentTime()
    })
    scrollToBottom()
  })
}



onLoad((option) => {
  scrollToBottom()
  title.value = option.topic
  console.log(option.name);
  switch (option.name) {
    case 'speakai':
      request.value.text = 'What is the weather like?';
      break;
    case 'dnergency':
      request.value.text = 'there is an emergency';
      break;
    case 'interview':
      request.value.text = 'there is an interview';
      break;
    case 'travel':
      request.value.text = 'i will travel to paris';
      break;
    case 'dailylife':
      request.value.text = 'in the dailylife, could i do something';
      break;
    case 'medicine':
      request.value.text = 'i need medical help';
      break;        
  }
  request.value.workspace = option.name
  getResp()
})
</script>

<style scoped>
/* CSS变量定义 */
:root {
  --primary: #4F46E5;
  --secondary: #10B981;
  --accent: #3B82F6;
  --neutral: #F3F4F6;
  --dark: #1F2937;
  --chatbot: #E0F2FE;
  --user: #E8F5E8; /* 浅绿色背景 */
  --gray-50: #F9FAFB;
  --gray-100: #F3F4F6;
  --gray-200: #E5E7EB;
  --gray-300: #D1D5DB;
  --gray-400: #9CA3AF;
  --gray-500: #6B7280;
  --gray-600: #4B5563;
  --gray-700: #374151;
  --gray-800: #1F2937;
  --white: #FFFFFF;
}

/* 全局样式 */
.chat-container {
  min-height: 94.7vh;
  display: flex;
  flex-direction: column;
  background-color: var(--gray-50);
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

/* 顶部导航栏 */
.header {
  background: var(--white);
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
  position: sticky;
  top: 0;
  z-index: 30;
}

.header-content {
  margin: 0 auto;
  padding: 0 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px;
}

.logo-section {
  display: flex;
  align-items: center;
}

.logo-icon {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  background: var(--primary);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
}

.logo-icon-text {
  color: var(--white);
  font-size: 18px;
}

.logo-title {
  font-size: 20px;
  font-weight: bold;
  color: var(--dark);
}

.user-section {
  display: flex;
  align-items: center;
  gap: 16px;
}

.notification-btn {
  position: relative;
  padding: 8px;
  border-radius: 50%;
  background: transparent;
  border: none;
  cursor: pointer;
}

.notification-btn:hover {
  background-color: var(--gray-100);
}

.notification-icon {
  font-size: 16px;
  color: var(--gray-600);
}

.notification-dot {
  position: absolute;
  top: 4px;
  right: 4px;
  width: 8px;
  height: 8px;
  background: #EF4444;
  border-radius: 50%;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid var(--primary);
  border-color: rgba(79, 70, 229, 0.2);
}

.user-name {
  font-size: 14px;
  font-weight: 500;
  color: var(--dark);
}

/* 主要内容区域 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

/* 会话头部 */
.session-header {
  background: var(--white);
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1);
  padding: 16px;
  height: 2vh;
  border-bottom: 1px solid var(--gray-200);
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.session-info {
  display: flex;
  align-items: center;
}

.ai-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 12px;
  border: 2px solid var(--primary);
  border-color: rgba(79, 70, 229, 0.2);
}

.ai-name {
  font-weight: 600;
  font-size: 18px;
  color: var(--dark);
}

.session-actions {
  display: flex;
  gap: 8px;
}

.action-btn {
  padding: 8px;
  border-radius: 50%;
  background: transparent;
  border: none;
  cursor: pointer;
  font-size: 16px;
  color: var(--gray-600);
}

.action-btn:hover {
  background-color: var(--gray-100);
}

.date-divider {
  display: flex;
  justify-content: center;
  margin: 32px 0;
}

.date-text {
  background: var(--gray-100);
  color: var(--gray-500);
  font-size: 12px;
  padding: 8px 16px;
  border-radius: 16px;
}

.message-item {
  margin-bottom: 24px;
}

.ai-message, .user-message {
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.user-message {
  justify-content: flex-end;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  flex-shrink: 0;
  margin-top: 4px;
}

.message-content {
  max-width: 85%;
}

.ai-bubble, .user-bubble {
  padding: 16px;
  border-radius: 16px;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1);
  word-wrap: break-word;
}

.ai-bubble {
  background: var(--chatbot);
  border-bottom-left-radius: 4px;
}

.user-bubble {
  background: var(--user); /* 浅绿色 */
  border-bottom-right-radius: 4px;
}

.message-time {
  font-size: 12px;
  color: var(--gray-400);
  margin-top: 4px;
  margin-left: 8px;
}

.user-message .message-time {
  text-align: right;
  margin-right: 8px;
}

.message-status {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 8px;
}

.status-icon {
  color: var(--accent);
  font-size: 12px;
}

/* 正在输入指示器 */
.typing-indicator {
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.typing-bubble {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 40px;
}

.typing-animation {
  display: flex;
  gap: 4px;
}

.dot {
  width: 8px;
  height: 8px;
  background: var(--gray-400);
  border-radius: 50%;
  animation: typing 1.4s infinite ease-in-out;
}

.dot:nth-child(1) {
  animation-delay: -0.32s;
}

.dot:nth-child(2) {
  animation-delay: -0.16s;
}

@keyframes typing {
  0%, 80%, 100% {
    transform: scale(0);
  }
  40% {
    transform: scale(1);
  }
}

/* 消息内容居中容器 */
.chat-content-wrapper {
  display: flex;
  justify-content: center;
  flex: 1;
  overflow: hidden;
}

/* 消息列表宽度70%并居中 */
.chat-messages {
  width: 70%;
  height: 64vh;
  padding: 24px;
  overflow-y: auto;
  background: var(--white);
  border-radius: 16px;
  box-shadow: 
    0 4px 6px -1px rgba(0, 0, 0, 0.1),
    0 2px 4px -1px rgba(0, 0, 0, 0.06),
    0 0 0 1px rgba(0, 0, 0, 0.05);
  margin: 16px 0;
  max-height: calc(100vh - 200px);
  backdrop-filter: blur(10px);
}

/* 简化输入区域样式 */
.input-area {
  background: linear-gradient(to bottom, transparent, rgba(255, 255, 255, 0.8));
  padding: 20px 0;
  z-index: 20;
  height: 11vh;
  width: 74%;
  margin-left: 13%;
  display: flex;
  justify-content: center;
  backdrop-filter: blur(10px);
}

.input-box {
  position: relative;
  width: 70%;
  height: 95%;
  max-width: 800px;
  display: flex;
  align-items: center; /* 改为flex-end防止溢出 */
  gap: 12px;;
  background: var(--accent);
  border-radius: 24px;
  box-shadow: 
    0 10px 15px -3px rgba(0, 0, 0, 0.1),
    0 4px 6px -2px rgba(0, 0, 0, 0.05),
    0 0 0 1px rgba(0, 0, 0, 0.05);
  padding: 4px;
  transition: all 0.3s ease;
  max-height: 200px; /* 添加最大高度限制 */
  overflow: hidden; /* 防止内容溢出 */
}

.message-input {

  height: 80%;
  width: 90%;
  border-radius: 30%;
}


/* 添加媒体查询修复移动端 */
@media (max-width: 768px) {
  .input-box {
    width: 95%;
    max-height: 150px;
  }
  
  .message-input {
    max-height: 100px;
    font-size: 16px; /* 防止iOS缩放 */
  }
}
.message-input:focus {
  background: rgba(79, 70, 229, 0.02);
}

.message-input::placeholder {
  color: var(--gray-400);
  font-size: 15px;
}

.send-btn {
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, var(--primary), #6366f1);
  color: var(--white);
  border: none;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(79, 70, 229, 0.3);
}

.send-btn.recording {
  background: linear-gradient(135deg, #4338ca, #4f46e5);
  transform: translateY(-50%) scale(1.05);
  box-shadow: 0 4px 12px rgba(79, 70, 229, 0.4);
}


.send-icon {
  font-size: 16px;
}
/* 响应式设计 */
@media (max-width: 768px) {
  .header-content {
    padding: 0 12px;
  }
  
  .user-name {
    display: none;
  }
  
  .session-actions {
    gap: 4px;
  }
  
  .action-btn {
    padding: 6px;
    font-size: 14px;
  }
  
  .chat-messages,
  .input-box {
    width: 95%;
  }
}
/* 滚动条美化 */
.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-track {
  background: transparent;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: var(--gray-300);
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb:hover {
  background: var(--gray-400);
}
</style>