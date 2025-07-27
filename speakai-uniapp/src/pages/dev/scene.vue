<template>
  <view class="conversation-container">
    <!-- 顶部导航栏 -->
    <view class="header">
      <view class="header-left">
        <view class="logo">
          <svg fill="none" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
            <path clip-rule="evenodd"
              d="M39.475 21.6262C40.358 21.4363 40.6863 21.5589 40.7581 21.5934C40.7876 21.655 40.8547 21.857 40.8082 22.3336C40.7408 23.0255 40.4502 24.0046 39.8572 25.2301C38.6799 27.6631 36.5085 30.6631 33.5858 33.5858C30.6631 36.5085 27.6632 38.6799 25.2301 39.8572C24.0046 40.4502 23.0255 40.7407 22.3336 40.8082C21.8571 40.8547 21.6551 40.7875 21.5934 40.7581C21.5589 40.6863 21.4363 40.358 21.6262 39.475C21.8562 38.4054 22.4689 36.9657 23.5038 35.2817C24.7575 33.2417 26.5497 30.9744 28.7621 28.762C30.9744 26.5497 33.2417 24.7574 35.2817 23.5037C36.9657 22.4689 38.4054 21.8562 39.475 21.6262ZM4.41189 29.2403L18.7597 43.5881C19.8813 44.7097 21.4027 44.9179 22.7217 44.7893C24.0585 44.659 25.5148 44.1631 26.9723 43.4579C29.9052 42.0387 33.2618 39.5667 36.4142 36.4142C39.5667 33.2618 42.0387 29.9052 43.4579 26.9723C44.1631 25.5148 44.659 24.0585 44.7893 22.7217C44.9179 21.4027 44.7097 19.8813 43.5881 18.7597L29.2403 4.41187C27.8527 3.02428 25.8765 3.02573 24.2861 3.36776C22.6081 3.72863 20.7334 4.58419 18.8396 5.74801C16.4978 7.18716 13.9881 9.18353 11.5858 11.5858C9.18354 13.988 7.18717 16.4978 5.74802 18.8396C4.58421 20.7334 3.72865 22.6081 3.36778 24.2861C3.02574 25.8765 3.02429 27.8527 4.41189 29.2403Z"
              fill="currentColor" fill-rule="evenodd">
            </path>
          </svg>
        </view>
        <text class="brand-name">SpeakAi</text>
      </view>
      
      <view class="header-right">
        <view class="notification-btn">
          <svg fill="currentColor" height="20px" viewBox="0 0 256 256" width="20px" xmlns="http://www.w3.org/2000/svg">
            <path d="M221.8,175.94C216.25,166.38,208,139.33,208,104a80,80,0,1,0-160,0c0,35.34-8.26,62.38-13.81,71.94A16,16,0,0,0,48,200H88.81a40,40,0,0,0,78.38,0H208a16,16,0,0,0,13.8-24.06ZM128,216a24,24,0,0,1-22.62-16h45.24A24,24,0,0,1,128,216ZM48,184c7.7-13.24,16-43.92,16-80a64,64,0,1,1,128,0c0,36.05,8.28,66.73,16,80Z"></path>
          </svg>
        </view>
        <view class="user-avatar"></view>
      </view>
    </view>

    <!-- 主要内容区域 -->
    <view class="main-content">
      <view class="content-wrapper">
        <view class="page-title">
          <text class="title-text">Start a New Conversation</text>
          <text class="subtitle-text">Choose a topic and difficulty level to begin.</text>
        </view>

        <!-- 主题选择 -->
        <view class="section">
          <text class="section-title">Choose a Topic</text>
          <view class="topic-grid">
            <view 
              v-for="topic in topics" 
              :key="topic.value"
              class="topic-card"
              :class="{ active: currentTopic.name === topic.name }"
              @tap="selectTopic(topic)"
            >
              <view class="topic-icon">
                <svg class="icon-svg" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24">
                  <path :d="topic.icon" stroke-linecap="round" stroke-linejoin="round"></path>
                </svg>
              </view>
              <text class="topic-name">{{ topic.name }}</text>
            </view>
          </view>
        </view>

        <!-- 难度选择 -->
        <view class="section">
          <text class="section-title">Select Difficulty Level</text>
          <view class="difficulty-selector">
            <view 
              v-for="level in difficultyLevels" 
              :key="level.value"
              class="difficulty-option"
              :class="{ active: selectedDifficulty === level.value }"
              @tap="selectDifficulty(level.value)"
            >
              <text class="difficulty-text">{{ level.name }}</text>
            </view>
          </view>
        </view>

        <!-- 开始按钮 -->
        <view class="action-section">
          <button 
            class="start-button"
            @tap="startConversation"
          >
            Let's Talk!
          </button>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
const currentTopic = ref({
  name: '',
  topic: '',
})

// 主题数据
const topics = [
  { name: 'speakai', value: '自由对话', icon: 'M12 19l9 2-9-18-9 18 9-2zm0 0v-8' },
  { name: 'interview', value: '演讲', icon: 'M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z' },
  { name: 'travel', value: '旅游', icon: 'M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6' },
  { name: 'dailylife', value: '日常', icon: 'M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z' },
  { name: 'medicine', value: '医学', icon: 'M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2h1a2 2 0 002-2v-1a2 2 0 012-2H19.95m-16.9 0a2.992 2.992 0 00-2.723 3.664l1.523 7.618a2 2 0 001.98 1.716h12.94a2 2 0 001.98-1.716l1.523-7.618a2.992 2.992 0 00-2.723-3.664M12 6V3m0 3h-3m3 0h3' },
  { name: 'energency', value: '自救', icon: 'M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 002 2h3a2 2 0 002-2V7a2 2 0 00-2-2H5zM5 14a2 2 0 00-2 2v3a2 2 0 002 2h3a2 2 0 002-2v-3a2 2 0 00-2-2H5z' }
]

// 难度等级
const difficultyLevels = [
  { name: 'Beginner', value: 'beginner' },
  { name: 'Intermediate', value: 'intermediate' },
  { name: 'Advanced', value: 'advanced' }
]

// 响应式数据
const selectedTopic = ref('')
const selectedDifficulty = ref('')

// 方法
const selectTopic = (topic) => {
  currentTopic.value.topic = topic.value
  currentTopic.value.name = topic.name
}



const startConversation = () => {
  console.log(111);
  if (currentTopic.value.name) {
    uni.navigateTo({
    url: `/pages/dev/chat?topic=${currentTopic.value.topic}&name=${currentTopic.value.name}`,
  })
  }
}


</script>

<style scoped>
/* CSS变量定义 */
:root {
  --primary-color: #b2d1e5;
  --primary-accent-color: #89badd;
  --primary-hover-color: #a1c4db;
  --primary-text-color: #101518;
  --secondary-text-color: #4a5568;
  --background-color: #f0f4f8;
  --card-bg-color: #ffffff;
  --border-color: #e2e8f0;
  --selected-border-color: #63b3ed;
}

/* 主容器 */
.conversation-container {
  min-height: 94.7vh;
  background: linear-gradient(to bottom right, var(--background-color), #e6eaf0);
  font-family: "Plus Jakarta Sans", "Noto Sans", sans-serif;
}

/* 顶部导航栏 */
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 40px;
  border-bottom: 1px solid var(--border-color);
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(8px);
  position: sticky;
  top: 0;
  z-index: 10;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.logo {
  width: 32px;
  height: 32px;
  color: var(--primary-accent-color);
}

.brand-name {
  color: var(--primary-text-color);
  font-size: 20px;
  font-weight: 700;
  letter-spacing: -0.025em;
}


.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.notification-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #f3f4f6;
  color: var(--secondary-text-color);
  cursor: pointer;
  transition: background-color 0.15s ease-in-out;
}

.notification-btn:hover {
  background: #e5e7eb;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: url("https://lh3.googleusercontent.com/aida-public/AB6AXuA1wp1MUZ-2n30mrMYdWEBB3Nzfek5aH-_6AHfPrbbiguGbQh7T_E8xK_2bDUd10SbHBbSN3wu4G2pjTlFJUdZS6Zs81Pbp41S_Fclsmkbd8b5RdlinPvhDzvE4Fnre4Ga69iOx2MUX1jH2aZWW9oY-2c85fhfqTbyZ7QzMn2-v87cWD2IUuUFBviejAc5qp2dtNorH7oAd48h9Lnv3Mi8jPrvkmH3I7doGVRgr5qKEn5aD8EvFeQKjXw76glryMmx4o2RekAUCPUyt") center/cover;
}

/* 主要内容区域 */
.main-content {
  flex: 1;
  display: flex;
  justify-content: center;
  padding: 48px 16px;
}

.content-wrapper {
  width: 100%;
  max-width: 1280px;
  margin: 0 auto;
}

.page-title {
  text-align: center;
  margin-bottom: 48px;
}

.title-text {
  font-size: 32px;
  font-weight: 700;
  color: var(--primary-text-color);
  margin-bottom: 8px;
  display: block;
}

.subtitle-text {
  font-size: 16px;
  color: var(--secondary-text-color);
  display: block;
}

.section {
  margin-bottom: 40px;
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--primary-text-color);
  margin-bottom: 16px;
  display: block;
}

.topic-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}

.topic-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px;
  background: var(--card-bg-color);
  border: 1px solid var(--border-color);
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.15s ease-in-out;
}

.topic-card:hover {
  border-color: var(--primary-hover-color);
  transform: translateY(-2px);
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
}

.topic-card.active {
  border-color: var(--selected-border-color);
  background: rgba(99, 179, 237, 0.05);
}

.topic-icon {
  width: 48px;
  height: 48px;
  margin-bottom: 12px;
  color: var(--primary-accent-color);
}

.icon-svg {
  width: 100%;
  height: 100%;
}

.topic-name {
  font-size: 14px;
  font-weight: 500;
  color: var(--primary-text-color);
}

.difficulty-selector {
  display: flex;
  gap: 12px;
}

.difficulty-option {
  flex: 1;
  padding: 12px 24px;
  border: 1px solid var(--border-color);
  border-radius: 8px;
  text-align: center;
  cursor: pointer;
  transition: all 0.15s ease-in-out;
  background: var(--card-bg-color);
}

.difficulty-option:hover {
  border-color: var(--primary-hover-color);
  background: rgba(99, 179, 237, 0.05);
}

.difficulty-option.active {
  border-color: var(--selected-border-color);
  background: var(--primary-accent-color);
  color: white;
}

.difficulty-text {
  font-size: 14px;
  font-weight: 500;
}

.action-section {
  margin-top: 48px;
  text-align: center;
}

.start-button {
  padding: 16px 48px;
  background: blue;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  opacity: 0.5;
  transition: background-color 0.15s ease-in-out;
}

.start-button:hover{
  background: green;
  opacity: 0.7;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .header {
    padding: 12px 16px;
  }
  
  .header-nav {
    display: none;
  }
  
  .title-text {
    font-size: 24px;
  }
  
  .topic-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 12px;
  }
  
  .topic-card {
    padding: 16px;
  }
  
  .difficulty-selector {
    flex-direction: column;
  }
}

@media (max-width: 480px) {
  .topic-grid {
    grid-template-columns: 1fr;
  }
  
  .main-content {
    padding: 24px 16px;
  }
}
</style>