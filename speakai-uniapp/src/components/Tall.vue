<template>
  <view class="simple-chat">
    <!-- 消息列表区域 -->
    <view class="chat-content-wrapper">
      <view class="chat-messages">
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
              <text class="message-time">{{ message.time }}</text>
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
</template>

<script setup>
import { onLoad } from '@dcloudio/uni-app'
import { ref, computed, nextTick,defineProps, watch, toRef } from 'vue'
import { Postchat } from '../utils/request'
import Recorder from 'js-audio-recorder'
let recorder = new Recorder()
const props = defineProps({
  voice: ''
})
const request = ref({
  text: '',
  roleCode:''
})

const playAudio = (src) => {
  const au = uni.createInnerAudioContext()
  au.src = src
  au.play()
}
// 响应式数据
const messages = ref([
  {
    type: 'ai',
    content: '<p>你好！我是AI助手，有什么可以帮您的吗？</p>',
    time: '09:30'
  }
])
const isRecording = ref(false)
const inputMessage = ref('')
const isTyping = ref(false)
const blobWav = ref(null)
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

const getResp = (file) => {
  console.log(request.value.roleCode);
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

const taggleRecording = () => {
  if(!isRecording.value){
    console.log('开始录音');
    isRecording.value = true
    recorder.start()
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
    getResp(blobWav.value)    
    
  }
}


const getCurrentTime = () => {
  const now = new Date()
  return now.getHours().toString().padStart(2, '0') + ':' + 
         now.getMinutes().toString().padStart(2, '0')
}
// 初始化
scrollToBottom()
onLoad(() => {
  console.log(1);
  console.log(props.voice);
  request.value.roleCode = props.voice
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
  --user: #E8F5E8;
  --gray-50: #F9FAFB;
  --gray-100: #F3F4F6;
  --gray-200: #E5E7EB;
  --gray-300: #D1D5DB;
  --gray-400: #9CA3AF;
  --gray-500: #6B7280;
  --gray-600: #4B5563;
  --gray-700: #374151;
  --white: #FFFFFF;
}

/* 全局样式 */
.simple-chat {
  height: 100%;
  width: 100%; /* 改为100% */
  display: flex;
  border-radius: 30px;
  flex-direction: column;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  overflow: hidden; /* 添加溢出隐藏 */
}

/* 消息区域 */
.chat-content-wrapper {
  flex: 1;
  display: flex;
  justify-content: center;
  height: 85%;
  padding: 16px;
}

.chat-messages {
  width: 100%;
  max-width: 600px;
  height: 95%;
  padding: 24px;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 30px;
  box-shadow: 
    0 8px 32px rgba(0, 0, 0, 0.1),
    0 0 0 1px rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(10px);
  max-height: calc(100vh - 180px);
}

.message-item {
  margin-bottom: 20px;
}

.ai-message, .user-message {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  animation: fadeIn 0.3s ease-in-out;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.user-message {
  justify-content: flex-end;
}

.message-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  object-fit: cover;
  flex-shrink: 0;
  margin-top: 2px;
}

.message-content {
  max-width: 75%;
}

.ai-bubble, .user-bubble {
  padding: 14px 18px;
  border-radius: 18px;
  font-size: 15px;
  line-height: 1.5;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  word-wrap: break-word;
}

.ai-bubble {
  background: var(--chatbot);
  color: var(--dark);
  border-bottom-left-radius: 6px;
}

.user-bubble {
  background: var(--user);
  color: var(--dark);
  border-bottom-right-radius: 6px;
}

.message-time {
  font-size: 11px;
  color: var(--gray-400);
  margin-top: 4px;
  display: block;
}

/* 输入区域 */
.input-area {
  background: linear-gradient(to top, rgba(255, 255, 255, 0.95), transparent);
  padding: 20px 16px;
  display: flex;
  justify-content: center;
  backdrop-filter: blur(10px);
}

.input-box {
  position: relative;
  width: 100%;
  max-width: 600px;
  display: flex;
  align-items: center;
  gap: 12px;
  background: var(--white);
  border-radius: 24px;
  box-shadow: 
    0 4px 12px rgba(0, 0, 0, 0.1),
    0 0 0 1px rgba(0, 0, 0, 0.05);
  padding: 8px 8px 8px 20px;
  transition: all 0.3s ease;
}

.input-box:focus-within {
  box-shadow: 
    0 8px 20px rgba(0, 0, 0, 0.15),
    0 0 0 1px rgba(79, 70, 229, 0.2);
}

.message-input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 16px;
  color: var(--dark);
  background: transparent;
  padding: 8px 0;
}

.message-input::placeholder {
  color: var(--gray-400);
}

.send-btn {
  width: 44px;
  height: 44px;
  background: linear-gradient(135deg, var(--primary), #6366f1);
  color: var(--white);
  border: none;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  flex-shrink: 0;
}

.send-btn.recording {
  background: linear-gradient(135deg, #4338ca, #4f46e5);
  transform: translateY(-50%) scale(1.05);
  box-shadow: 0 4px 12px rgba(79, 70, 229, 0.4);
}


.send-icon {
  font-size: 18px;
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
  background: var(--chatbot);
  border-radius: 18px;
  padding: 14px 18px;
}

.typing-animation {
  display: flex;
  gap: 4px;
}

.dot {
  width: 6px;
  height: 6px;
  background: var(--gray-400);
  border-radius: 50%;
  animation: typing 1.4s infinite ease-in-out;
}

.dot:nth-child(1) { animation-delay: -0.32s; }
.dot:nth-child(2) { animation-delay: -0.16s; }

@keyframes typing {
  0%, 80%, 100% { transform: scale(0); }
  40% { transform: scale(1); }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .chat-messages {
    width: 100%;
    margin: 8px;
    padding: 16px;
    border-radius: 16px;
  }
  
  .input-area {
    padding: 16px;
  }
  
  .input-box {
    margin: 0 8px;
  }
}

/* 滚动条美化 */
.chat-messages::-webkit-scrollbar {
  width: 4px;
}

.chat-messages::-webkit-scrollbar-track {
  background: transparent;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: var(--gray-300);
  border-radius: 2px;
}
/* 添加父容器宽度为0时的隐藏效果 */
.simple-chat {
  transition: all 0.3s ease;
}

/* 当父容器宽度为0时，确保所有子元素都隐藏 */
.simple-chat[style*="width: 0"] {
  display: none;
}

/* 或者使用min-width确保完全隐藏 */
.simple-chat {
  min-width: 0;
  min-height: 0;
}
</style>