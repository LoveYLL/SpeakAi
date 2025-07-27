<template>
  <view class="pronunciation-container">
    <!-- 顶部导航栏 -->
    <view class="header">
      <view class="header-content">
        <view class="logo-section">
          <view class="logo-icon">
            <text class="logo-icon-text">🎤</text>
          </view>
          <text class="logo-title">SpeakAI</text>
        </view>
        <view class="user-section">
          <image 
            class="user-avatar" 
            src="https://design.gemcoder.com/staticResource/echoAiSystemImages/909e276323bf512ef27e32e043a1617a.png"
            mode="aspectFill"
          />
        </view>
      </view>
    </view>

    <!-- 主要内容区 -->
    <view class="main-content">
      <!-- 页面标题 -->
      <view class="page-header">
        <text class="page-title">AI 智能跟读</text>
        <text class="page-subtitle">通过AI技术实时纠正发音，提升口语表达能力</text>
      </view>

      <!-- 跟读卡片 -->
      <view class="practice-card">
        <!-- 卡片头部 -->
        <view class="card-header">
          <view class="card-info">
            <text class="card-title">今日练习</text>
          </view>
          <!-- 进度条 -->
          <!-- <view class="progress-container">
            <view class="progress-bar" :style="{ width: progressPercent + '%' }"></view>
          </view> -->
        </view>
        <!-- 卡片内容区 -->
        <view class="card-content">
          <!-- 句子展示区 -->
          <view class="sentence-section">
            <view class="section-header">
              <view class="listen-hint" @click="load">
                <text class="hint-icon">🔊</text>
                <text class="hint-text">点击聆听示范</text>
              </view>
            </view>
            <view class="sentence-card">
              <text class="english-sentence">{{ currentSentenceData.english }}</text>
              <text class="chinese-sentence">{{ currentSentenceData.chinese }}</text>
            </view>
          </view>

          <!-- 录音区域 -->
          <view class="recording-section">
            <view v-if="recordingState === 'idle'" class="recording-idle">
              <view class="mic-container">
                <view class="mic-button" @tap="startRecording">
                  <text class="mic-icon">🎤</text>
                </view>
              </view>
              
              <text class="recording-title">点击麦克风开始跟读</text>
              <text class="recording-desc">请清晰朗读上方句子，AI将实时分析你的发音</text>
            </view>

            <view v-if="recordingState === 'recording'" class="recording-active">
              <view class="timer">
                <text class="timer-display">{{ formatTime(recordingTime) }}</text>
                <text class="timer-label">正在录音中...</text>
              </view>
            </view>
          </view>

          <!-- 反馈区域 -->
          <view v-if="showFeedback" class="feedback-section">
            <view class="feedback-header">
              <text class="feedback-title">发音反馈</text>
            </view>
            
            <view class="score-display" v-if="isBack">
              <view class="score-main">
                <text class="score-number">{{ pronunciationScore }}</text>
                <text class="score-total">/100</text>
              </view>
              <text class="score-label">{{ feedbackText }}</text>
            </view>
            <view v-else>
              <text class="score-label">正在分析中...</text>
            </view>
          </view>

          <!-- 操作按钮 -->
          <view class="action-buttons">
            <view class="center-actions">
              <button class="action-btn secondary" @tap="listenAgain">
                <text class="btn-icon">🔄</text>
                <text>再听一遍</text>
              </button>
              
              <button 
                class="action-btn primary" 
                @tap="toggleRecording"
                :class="{ recording: recordingState === 'recording' }"
              >
                <text class="btn-icon">🎤</text>
                <text>{{ recordingState === 'recording' ? '停止录音' : '开始录音' }}</text>
              </button>
            </view>
            
            <button class="action-btn secondary" @tap="nextSentence">
              <text>下一句</text>
              <text class="btn-icon">➡️</text>
            </button>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import Recorder from 'js-audio-recorder'
import client from '../../utils/oss'
import { Postfile,Post,Get } from '../../utils/request'
const recorder = new Recorder()

const j = document.createElement('audio')
const load = () => {
  j.play()
}

// 响应式数据
const recordingState = ref('idle') // idle, recording, processing
const recordingTime = ref(0)
const showFeedback = ref(false)
const pronunciationScore = ref(92)
const timer = ref(null)
const bolbVideo = ref(null)
const isBack = ref(false)
const feedbackText = ref('正在处理中........')
// 当前句子数据
const currentSentenceData = ref({
  english: 'The journey of a thousand miles begins with a single step.',
  chinese: '千里之行，始于足下。',
})


// 方法定义
const startRecording = () => {
  recordingState.value = 'recording'
  recordingTime.value = 0
  showFeedback.value = false
  // 模拟录音计时
  timer.value = setInterval(() => {
    recordingTime.value++
  }, 1000)
  recorder.start()
}

const stopRecording = () => {
  recordingState.value = 'idle'
  recordingTime.value = 0
  showFeedback.value = true
  clearInterval(timer.value);
  bolbVideo.value = recorder.getWAVBlob()
  recorder.stopStream()
  client.put('test.wav', bolbVideo.value).then(resp => {
    console.log(resp);
  })
  console.log(bolbVideo.value);
  Postfile('audio/score',bolbVideo.value,currentSentenceData.value).then(res => {
    console.log(res.data)
    if(res.code == 200){
      isBack.value = true
      pronunciationScore.value = res.data.score
      feedbackText.value = res.data.message
    }
  })
  
}

const toggleRecording = () => {
  if (recordingState.value === 'recording') {
    stopRecording()
  } else {
    startRecording()
  }
}

const formatTime = (seconds) => {
  const mins = Math.floor(seconds / 60).toString().padStart(2, '0')
  const secs = (seconds % 60).toString().padStart(2, '0')
  return `${mins}:${secs}`
}

const listenAgain = () => {
  // 重置状态
  showFeedback.value = false
  recordingState.value = 'idle'
  recordingTime.value = 0
  j.play()
}



const nextSentence = () => {
  getSentence()
}
const getSentence = () => {
  Get('reference/random').then(resp => {
    currentSentenceData.value.english = resp.data.text
    j.src = resp.data.audioUrl
    j.style.display = 'none'
  })
}

// 生命周期
onMounted(() => {
  getSentence()
})
</script>

<style scoped>
/* CSS变量定义 */
:root {
  --primary: #165DFF;
  --secondary: #36CFC9;
  --accent: #FF7D00;
  --dark: #1D2129;
  --light: #F2F3F5;
  --muted: #86909C;
  --white: #FFFFFF;
  --gray-50: #F9FAFB;
  --gray-100: #F3F4F6;
  --gray-200: #E5E7EB;
  --gray-300: #D1D5DB;
  --gray-400: #9CA3AF;
  --gray-500: #6B7280;
  --gray-600: #4B5563;
  --gray-700: #374151;
  --gray-800: #1F2937;
  --green-500: #10B981;
  --purple-500: #8B5CF6;
  --blue-100: #DBEAFE;
  --green-100: #D1FAE5;
  --purple-100: #EDE9FE;
  --red-500: #EF4444;
}

/* 全局样式 */
.pronunciation-container {
  height: 94.7vh;
  background-color: var(--gray-50);
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

/* 顶部导航栏 */
.header {
  background: var(--white);
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
  position: sticky;
  top: 0;
  z-index: 50;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 12px 16px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 8px;
}

.logo-icon {
  width: 32px;
  height: 32px;
  background: var(--primary);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-icon-text {
  color: var(--white);
  font-size: 16px;
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

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid var(--primary);
  border-color: rgba(22, 93, 255, 0.2);
}

/* 主要内容区 */
.main-content {
  flex: 1;
  max-width: 1200px;
  margin: 0 auto;
  padding: 32px 16px;
}

.page-header {
  text-align: center;
  margin-bottom: 20px;
}

.page-title {
  font-size: clamp(24px, 5vw, 40px);
  font-weight: bold;
  color: var(--dark);
  margin-bottom: 8px;
}

.page-subtitle {
  color: var(--muted);
  font-size: 16px;
  max-width: 600px;
  margin: 0 auto;
}

/* 跟读卡片 */
.practice-card {
  max-width: 900px;
  margin: 0 auto;
  background: var(--white);
  border-radius: 16px;
  box-shadow: 0 10px 30px -5px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  transition: all 0.3s ease;
}

.practice-card:hover {
  box-shadow: 0 15px 40px -10px rgba(0, 0, 0, 0.1);
}

/* 卡片头部 */
.card-header {
  background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
  color: var(--white);
  padding: 24px 32px;
}

.card-info {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 16px;
}

.card-title {
  font-size: clamp(18px, 3vw, 24px);
  font-weight: bold;
  margin-bottom: 4px;
}

.progress-container {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 9999px;
  height: 10px;
  overflow: hidden;
}

.progress-bar {
  background: var(--white);
  height: 100%;
  border-radius: 9999px;
  transition: width 0.3s ease;
}

/* 卡片内容区 */
.card-content {
  padding: 24px 32px;
}

/* 句子展示区 */
.sentence-section {
  margin-bottom: 40px;
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16px;
}

.listen-hint {
  display: flex;
  align-items: center;
  gap: 4px;
  background: rgba(22, 93, 255, 0.1);
  color: var(--primary);
  padding: 6px 12px;
  border-radius: 9999px;
  font-size: 12px;
  font-weight: 500;
}

.sentence-card {
  background: var(--light);
  border-radius: 12px;
  padding: 24px;
  transition: all 0.3s ease;
}

.sentence-card:hover {
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
}

.english-sentence {
  font-size: clamp(16px, 3vw, 20px);
  font-weight: 600;
  color: var(--dark);
  margin-bottom: 8px;
  line-height: 1.5;
}

.chinese-sentence {
  color: var(--muted);
  font-size: 16px;
  font-style: italic;
  margin-bottom: 16px;
}

/* 录音区域 */
.recording-section {
  text-align: center;
  margin-bottom: 40px;
}

.recording-idle {
  text-align: center;
}

.mic-container {
  position: relative;
  display: inline-block;
  margin-bottom: 24px;
}

.mic-button {
  width: 96px;
  height: 96px;
  background: var(--primary);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  animation: pulse 2s infinite;
}

@media (min-width: 768px) {
  .mic-button {
    width: 128px;
    height: 128px;
  }
}

.mic-icon {
  color: var(--white);
  font-size: 32px;
}

@media (min-width: 768px) {
  .mic-icon {
    font-size: 40px;
  }
}

.recording-title {
  font-size: clamp(16px, 3vw, 20px);
  font-weight: 500;
  color: var(--dark);
  margin-bottom: 4px;
}

.recording-desc {
  color: var(--muted);
  font-size: 14px;
}

.recording-active {
  text-align: center;
}

.timer {
  margin-bottom: 24px;
}

.timer-display {
  font-size: 32px;
  font-weight: bold;
  color: var(--primary);
  margin-bottom: 8px;
}

.timer-label {
  color: var(--muted);
}

@keyframes pulse {
  0% { box-shadow: 0 0 0 0 rgba(22, 93, 255, 0.4); }
  70% { box-shadow: 0 0 0 10px rgba(22, 93, 255, 0); }
  100% { box-shadow: 0 0 0 0 rgba(22, 93, 255, 0); }
}

/* 反馈区域 */
.feedback-section {
  background: var(--light);
  border-radius: 12px;
  padding: 10px;
}

.feedback-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16px;
}

.feedback-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--dark);
}

.score-display {
  display: flex;
  align-items: center;
  gap: 24px;
  margin-bottom: 24px;
}

.score-main {
  display: flex;
  align-items: center;
  gap: 4px;
}

.score-number {
  font-size: 32px;
  font-weight: bold;
  color: var(--primary);
}

.score-total {
  font-size: 20px;
  color: var(--muted);
}

.score-label {
  color: var(--muted);
}

/* 操作按钮 */
.action-buttons {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 16px;
  padding-top: 16px;
  border-top: 1px solid var(--gray-200);
}

.center-actions {
  display: flex;
  gap: 16px;
  margin-left: 30%;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
  border: none;
}

.action-btn.secondary {
  background: var(--white);
  color: var(--muted);
  border: 1px solid var(--gray-200);
}

.action-btn.secondary:hover {
  background: var(--gray-50);
}

.action-btn.primary {
  background: var(--primary);
  color: var(--white);
  box-shadow: 0 4px 14px 0 rgba(22, 93, 255, 0.4);
}

.action-btn.primary:hover {
  background: rgba(22, 93, 255, 0.9);
}

.action-btn.recording {
  background: var(--red-500);
  animation: pulse 2s infinite;
}

.btn-icon {
  font-size: 16px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .header-content {
    padding: 8px 16px;
  }
  
  .main-content {
    padding: 16px;
  }
  
  .card-content {
    padding: 16px;
  }
  
  .action-buttons {
    flex-direction: column;
  }
  
  .center-actions {
    justify-content: center;
  }
}
</style>