<template>
  <view class="wrong-words-container">
    <!-- 顶部导航 -->
    <view class="header">
      <view class="header-content">
        <view class="logo-section">
          <svg class="logo-icon" fill="none" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
            <path clip-rule="evenodd" d="M39.475 21.6262C40.358 21.4363 40.6863 21.5589 40.7581 21.5934C40.7876 21.655 40.8547 21.857 40.8082 22.3336C40.7408 23.0255 40.4502 24.0046 39.8572 25.2301C38.6799 27.6631 36.5085 30.6631 33.5858 33.5858C30.6631 36.5085 27.6632 38.6799 25.2301 39.8572C24.0046 40.4502 23.0255 40.7407 22.3336 40.8082C21.8571 40.8547 21.6551 40.7875 21.5934 40.7581C21.5589 40.6863 21.4363 40.358 21.6262 39.475C21.8562 38.4054 22.4689 36.9657 23.5038 35.2817C24.7575 33.2417 26.5497 30.9744 28.7621 28.762C30.9744 26.5497 33.2417 24.7574 35.2817 23.5037C36.9657 22.4689 38.4054 21.8562 39.475 21.6262ZM4.41189 29.2403L18.7597 43.5881C19.8813 44.7097 21.4027 44.9179 22.7217 44.7893C24.0585 44.659 25.5148 44.1631 26.9723 43.4579C29.9052 42.0387 33.2618 39.5667 36.4142 36.4142C39.5667 33.2618 42.0387 29.9052 43.4579 26.9723C44.1631 25.5148 44.659 24.0585 44.7893 22.7217C44.9179 21.4027 44.7097 19.8813 43.5881 18.7597L29.2403 4.41187C27.8527 3.02428 25.8765 3.02573 24.2861 3.36776C22.6081 3.72863 20.7334 4.58419 18.8396 5.74801C16.4978 7.18716 13.9881 9.18353 11.5858 11.5858C9.18354 13.988 7.18717 16.4978 5.74802 18.8396C4.58421 20.7334 3.72865 22.6081 3.36778 24.2861C3.02574 25.8765 3.02429 27.8527 4.41189 29.2403Z" fill="currentColor" fill-rule="evenodd"></path>
          </svg>
          <text class="brand-name">SpeakAl</text>
        </view>
      </view>
    </view>

    <!-- 主要内容区域 -->
    <view class="main-content">
      <view class="content-wrapper">
        <!-- 页面标题 -->
        <view class="page-title">
          <text class="title-text">错词本</text>
        </view>

        <!-- 搜索框 -->
        <!-- 搜索区域 -->
        <view class="search-section">
          <view class="search-container">
            <input 
              type="text" 
              placeholder="搜索单词..." 
              class="search-input"
              v-model="searchKeyword"
              @input="onSearch"
            />
          </view>
        </view>

        <!-- 单词卡片网格 -->
        <!-- 在单词卡片网格下方添加分页容器 -->
        <view class="words-grid">
          <view 
            v-for="(word, index) in filteredWords" 
            :key="index"
            :class="['word-card', { 'mastered': word.mastered }]"
          >
            <text class="word-text">{{ word.text }}</text>
            <view class="card-actions">
              <button 
                class="pronunciation-btn"
                @tap="playPronunciation(word.text)"
              >
                正确发音
              </button>
              <button 
                class="master-btn"
                @tap="markAsMastered(word.text)"
                :disabled="word.mastered"
              >
                {{ word.mastered ? '已掌握' : '标记为掌握' }}
              </button>
            </view>
          </view>
        </view>
        <Pagination
          :current="pagination.current"
          :total="pagination.total"
          :page-size="pagination.pageSize"
          @change="getWords"
          show-icon
          show-info
        />
      </view>
    </view>
  </view>
</template>

<script setup>
import Recorder from 'js-audio-recorder'
import Pagination from '../../components/Pagination.vue'
import Player from 'js-audio-recorder/src/player/player'
import { base64toBlob } from '../../utils/trans'
import { ref, computed, onMounted } from 'vue'
import { Get, Post } from '../../utils/request'

// 响应式数据
const searchKeyword = ref('')
// 确保分页数据正确初始化
const pagination = ref({
  current: 1,
  total: 100,
  pageSize: 6, // 调整为每页6个单词
  pageCount: computed(() => Math.ceil(pagination.value.total / pagination.value.pageSize))
})

const getWords = (page = 1, pageSize = 6) => {
  let query = { page, pageSize }
  Get('es/search', {
    params: query
  }).then(res => {
    console.log(res.data);
    words.value = res.data.map(item => ({
      text: item.wrongWord,
      mastered: false
    }))
    
    // 更新分页信息
    pagination.value.current = page
    pagination.value.total = res.data.total || res.data.length
  }).catch(err => {
    console.error('获取单词失败:', err)
    uni.showToast({
      title: '获取单词失败',
      icon: 'none'
    })
  })
}
const words = ref([
  { text: 'through', mastered: false },
  { text: 'receive', mastered: true },
  { text: 'accommodation', mastered: true },
  { text: 'schedule', mastered: true },
  { text: 'pronunciation', mastered: false },
  { text: 'comfortable', mastered: false }
])

// 计算属性：根据搜索关键词过滤单词
const filteredWords = computed(() => {
  if (!searchKeyword.value) return words.value
  return words.value.filter(word => 
    word.text.toLowerCase().includes(searchKeyword.value.toLowerCase())
  )
})

// 方法：播放发音
const playPronunciation = (word) => {
  Post('tts/generate', { text: word },true).then(res => { 
    console.log(res.data);
    let bolb = base64toBlob(res.data.audioBase64, 'audio/mpeg')
    const url = URL.createObjectURL(bolb);
    let audio = uni.createInnerAudioContext()
    audio.src = url
    audio.play()
    audio.onended = () => {
    URL.revokeObjectURL(url);
    };
  })
  // 这里可以集成实际的语音播放功能
}

// 方法：标记为掌握
const markAsMastered = (wordText) => {
  const word = words.value.find(w => w.text === wordText)
  if (word) {
    word.mastered = true
    uni.showToast({
      title: `${wordText} 已标记为掌握`,
      icon: 'success'
    })
  }
}
// 搜索方法
const onSearch = () => {
  // 搜索逻辑已在filteredWords中处理
}

onMounted(() => {
  getWords()
})
</script>

<style scoped>
/* CSS变量定义 */
:root {
  --primary-color: #3f8cbf;
  --border-color: #e2e8f0;
  --background-color: #f9fafb;
  --text-color: #111827;
  --secondary-text: #6b7280;
  --card-bg: #ffffff;
  --shadow-color: rgba(0, 0, 0, 0.1);
}

/* 主容器 */
.wrong-words-container {
  min-height: 94.7vh;
  background-color: var(--background-color);
  font-family: "Plus Jakarta Sans", "Noto Sans", sans-serif;
}

/* 顶部导航 */
.header {
  border-bottom: 1px solid var(--border-color);
  background-color: var(--card-bg);
  position: sticky;
  top: 0;
  z-index: 10;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 40px;
  white-space: nowrap;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 16px;
  color: var(--text-color);
}

.logo-icon {
  width: 32px;
  height: 32px;
  color: var(--primary-color);
}

.brand-name {
  font-size: 20px;
  font-weight: 700;
  line-height: 1.25;
  letter-spacing: -0.025em;
}

/* 主要内容区域 */
.main-content {
  flex: 1;
  padding: 24px 16px;
}

.content-wrapper {
  max-width: 1024px;
  margin: 0 auto;
}

.page-title {
  text-align: center;
  margin-bottom: 24px;
}

.title-text {
  font-size: 30px;
  font-weight: 700;
  color: var(--primary-color);
}

/* 搜索区域 - 精美升级 */
.search-section {
  display: flex;
  justify-content: center;
  margin-bottom: 32px;
  position: relative;
}

.search-container {
  position: relative;
  width: 100%;
  max-width: 480px;
}

.search-input {
  width: 100%;
  padding: 16px 50px 16px 20px;
  font-size: 16px;
  border: 2px solid transparent;
  border-radius: 50px;
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
  box-shadow: 
    0 4px 20px rgba(63, 140, 191, 0.1),
    0 1px 3px rgba(0, 0, 0, 0.05),
    inset 0 1px 0 rgba(255, 255, 255, 0.8);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  outline: none;
  color: var(--text-color);
  font-family: inherit;
}

.search-input::placeholder {
  color: #94a3b8;
  font-weight: 400;
}

.search-input:focus {
  border-color: var(--primary-color);
  box-shadow: 
    0 8px 30px rgba(63, 140, 191, 0.15),
    0 2px 8px rgba(0, 0, 0, 0.08),
    inset 0 1px 0 rgba(255, 255, 255, 0.9);
  transform: translateY(-1px);
}

.search-input:hover {
  box-shadow: 
    0 6px 25px rgba(63, 140, 191, 0.12),
    0 2px 6px rgba(0, 0, 0, 0.06);
}

.search-container::before {
  content: "🔍";
  position: absolute;
  right: -50px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 18px;
  opacity: 0.6;
  transition: opacity 0.3s ease;
  pointer-events: none;
}

.search-container:focus-within::before {
  opacity: 1;
}

.search-container::after {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  border-radius: 50px;
  background: linear-gradient(135deg, rgba(63, 140, 191, 0.05) 0%, rgba(63, 140, 191, 0.02) 100%);
  opacity: 0;
  transition: opacity 0.3s ease;
  pointer-events: none;
}

.search-container:focus-within::after {
  opacity: 1;
}

/* 单词卡片网格 */
.words-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 24px;
}

/* 单词卡片 - 精美升级 */
.word-card {
  position: relative;
  border: none;
  border-radius: 16px;
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
  padding: 24px;
  box-shadow: 
    0 4px 6px -1px rgba(0, 0, 0, 0.1),
    0 2px 4px -1px rgba(0, 0, 0, 0.06),
    0 0 0 1px rgba(63, 140, 191, 0.1);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
}

.word-card::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, var(--primary-color), #60a5fa);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.word-card:hover {
  transform: translateY(-4px);
  box-shadow: 
    0 10px 25px -3px rgba(63, 140, 191, 0.2),
    0 4px 6px -2px rgba(0, 0, 0, 0.05),
    0 0 0 1px rgba(63, 140, 191, 0.2);
}

.word-card:hover::before {
  opacity: 1;
}

.word-text {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-color);
  margin-bottom: 20px;
  text-align: center;
  letter-spacing: -0.025em;
  position: relative;
}

.word-text::after {
  content: "";
  position: absolute;
  bottom: -8px;
  left: 50%;
  transform: translateX(-50%);
  width: 40px;
  height: 2px;
  background: linear-gradient(90deg, var(--primary-color), #60a5fa);
  border-radius: 1px;
}

.card-actions {
  display: flex;
  justify-content: center;
  gap: 12px;
  width: 100%;
  margin-top: 8px;
}

.pronunciation-btn {
  padding: 10px 20px;
  border: 1.5px solid var(--primary-color);
  color: var(--primary-color);
  border-radius: 50px;
  font-size: 14px;
  font-weight: 600;
  background-color: transparent;
  cursor: pointer;
  transition: all 0.2s ease;
  position: relative;
  overflow: hidden;
}

.pronunciation-btn:hover {
  background-color: var(--primary-color);
  color: white;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(63, 140, 191, 0.3);
}

.pronunciation-btn:active {
  transform: translateY(0);
  box-shadow: 0 2px 8px rgba(63, 140, 191, 0.2);
}

.master-btn {
  padding: 10px 20px;
  background: linear-gradient(135deg, var(--primary-color), #60a5fa);
  color: white;
  border: none;
  border-radius: 50px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  position: relative;
  overflow: hidden;
}

.master-btn:hover {
  background: linear-gradient(135deg, #2563eb, #3b82f6);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(37, 99, 235, 0.3);
}

.master-btn:active {
  transform: translateY(0);
  box-shadow: 0 2px 8px rgba(37, 99, 235, 0.2);
}

/* 已掌握状态 */
.word-card.mastered {
  background: linear-gradient(135deg, #f0fdf4 0%, #dcfce7 100%);
  box-shadow: 
    0 4px 6px -1px rgba(34, 197, 94, 0.1),
    0 2px 4px -1px rgba(34, 197, 94, 0.06),
    0 0 0 1px rgba(34, 197, 94, 0.1);
}

.word-card.mastered .word-text::after {
  background: linear-gradient(90deg, #22c55e, #16a34a);
}

.word-card.mastered .master-btn {
  background: linear-gradient(135deg, #22c55e, #16a34a);
  cursor: default;
  opacity: 0.7;
}

.word-card.mastered .master-btn:hover {
  transform: none;
  box-shadow: none;
}


/* 响应式设计 */
@media (min-width: 768px) {
  .words-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 1024px) {
  .words-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 767px) {
  .header-content {
    padding: 16px 20px;
  }
  
  .main-content {
    padding: 16px;
  }
}
</style>