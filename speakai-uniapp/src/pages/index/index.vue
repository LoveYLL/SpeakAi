<template>
  <view class="data-center">
    <!-- 顶部导航 -->
    <view class="header">
      <view class="nav-bar">
        <view class="logo">
          <image src="/static/logo.png" mode="aspectFit" class="logo-img" />
          <text class="logo-text">SpeakAI</text>
        </view>
        <view class="search-bar">
          <input 
            type="text" 
            placeholder="搜索单词..." 
            class="search-input"
          />
          <text class="search-icon">🔍</text>
        </view>
        <view class="user-info">
          <image src="/static/avatar.png" mode="aspectFit" class="avatar" />
          <text class="username">李明</text>
        </view>
      </view>
    </view>

    <!-- 主要内容 -->
    <view class="main-content">
      <view class="page-title">
        <text class="title-text">学习数据中心</text>
        <text class="subtitle-text">查看你的口语练习进度和表现分析</text>
      </view>

      <!-- 数据概览卡片 -->
      <view class="stats-overview">
        <view class="stat-card" v-for="stat in statsData" :key="stat.title">
          <view class="stat-content">
            <view class="stat-info">
              <text class="stat-title">{{ stat.title }}</text>
              <text class="stat-value">{{ stat.value }}</text>
              <text class="stat-change" :class="stat.change > 0 ? 'up' : 'down'">
                {{ stat.change > 0 ? '+' : '' }}{{ stat.change }}% 较上周
              </text>
            </view>
            <view class="stat-icon" :class="stat.iconClass">
              <text>{{ stat.icon }}</text>
            </view>
          </view>
        </view>
      </view>

      <!-- 图表区域 -->
      <view class="charts-section">
        <view class="chart-card trend-chart">
          <view class="chart-header">
            <text class="chart-title">练习趋势</text>
            <view class="chart-tabs">
              <text class="tab active">周</text>
              <text class="tab">月</text>
              <text class="tab">年</text>
            </view>
          </view>
          <view class="chart-container">
            <qiun-data-charts type="line" :chartData="trendData" />
          </view>
        </view>
        
        <view class="chart-card error-chart">
          <view class="chart-header">
            <text class="chart-title">错误类型分布</text>
          </view>
          <view class="chart-container">
            <qiun-data-charts type="pie" :chartData="errorData" />
          </view>
        </view>
      </view>
      <view class="records-header">
          <text class="section-title">最近学习记录</text>
        </view>
      <!-- 学习记录 -->
      <view style="width: 100%;height: 165px;overflow-y: auto;scrollbar-width: none;">
        
        <view class="record-list">
          <view class="record-item" v-for="record in records" :key="record.id">
            <view class="record-icon" :class="record.iconClass">
              <text>{{ record.icon }}</text>
            </view>
            <view class="record-info">
              <text class="record-title">{{ record.title }}</text>
              <text class="record-desc">{{ record.description }}</text>
            </view>
            <view class="record-time">
              <text class="duration">{{ record.duration }}</text>
              <text class="time">{{ record.time }}</text>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'

// 响应式数据
const statsData = ref([
  { title: '总练习时长', value: '12.5小时', change: 12, icon: '🕒', iconClass: 'blue-icon' },
  { title: '练习次数', value: '48次', change: 8, icon: '📅', iconClass: 'green-icon' },
  { title: '掌握单词', value: '156个', change: 15, icon: '✅', iconClass: 'purple-icon' },
  { title: '发音准确率', value: '87%', change: -2, icon: '📊', iconClass: 'amber-icon' }
])

const trendData = ref({
  categories: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
  series: [
    { name: '练习时长', data: [30, 45, 60, 35, 50, 70, 40] },
    { name: '准确率', data: [85, 87, 90, 88, 89, 92, 87] }
  ]
})

const errorData = ref({
  series: [
    { name: '发音错误', data: 35 },
    { name: '语法错误', data: 25 },
    { name: '词汇错误', data: 20 },
    { name: '语调错误', data: 20 }
  ]
})

const records = ref([
  { id: 1, title: '日常对话练习', description: '讨论周末计划和活动安排', duration: '35分钟', time: '今天 14:30', icon: '💬', iconClass: 'blue-bg' },
  { id: 2, title: '单词发音练习', description: '商务英语核心词汇 - 第5单元', duration: '20分钟', time: '昨天 19:15', icon: '📚', iconClass: 'green-bg' },
  { id: 3, title: '发音评测', description: '美式发音 - 元音练习', duration: '25分钟', time: '昨天 10:45', icon: '🎤', iconClass: 'purple-bg' },
  { id: 4, title: '情景对话模拟', description: '餐厅点餐和服务场景', duration: '40分钟', time: '06月12日', icon: '🎥', iconClass: 'amber-bg' }
])

const keyWords = ref([
  { id: 1, text: 'accommodation', pronunciation: '/əˌkɒməˈdeɪʃn/', meaning: 'n. 住宿；膳宿；调节；和解', errorCount: 8, tag: '高频错误', tagClass: 'error-tag' },
  { id: 2, text: 'receive', pronunciation: '/rɪˈsiːv/', meaning: 'v. 收到；接待；接纳；接收', errorCount: 5, tag: '常错词', tagClass: 'warning-tag' },
  { id: 3, text: 'through', pronunciation: '/θruː/', meaning: 'prep. 通过；穿过；凭借', errorCount: 4, tag: '常错词', tagClass: 'warning-tag' }
])

// 播放发音
const playPronunciation = (word) => {
  // 实现发音播放逻辑
  console.log('播放发音:', word)
}
</script>

<style scoped>
/* 基础样式 */
.data-center {
  height: 94.7vh;
  background-color: #f9fafb;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', sans-serif;
}

/* 顶部导航 */
.header {
  background: #ffffff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 50;
}

.nav-bar {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
}

.logo-img {
  width: 40px;
  height: 40px;
  border-radius: 8px;
}

.logo-text {
  font-size: 20px;
  font-weight: 700;
  color: #1f2937;
}

.search-bar {
  position: relative;
  flex: 1;
  max-width: 400px;
  margin: 0 32px;
}

.search-input {
  width: 100%;
  height: 40px;
  padding: 0 40px 0 16px;
  border: 1px solid #e5e7eb;
  border-radius: 20px;
  font-size: 14px;
  outline: none;
  transition: all 0.2s;
}

.search-input:focus {
  border-color: #4f46e5;
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
}

.search-icon {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 16px;
  color: #9ca3af;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: 2px solid rgba(79, 70, 229, 0.2);
}

.username {
  font-size: 14px;
  font-weight: 500;
  color: #1f2937;
}

/* 主要内容 */
.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px 20px;
}

.page-title {
  margin-bottom: 24px;
}

.title-text {
  font-size: clamp(24px, 3vw, 40px);
  font-weight: 700;
  color: #1f2937;
  display: block;
}

.subtitle-text {
  font-size: 14px;
  color: #6b7280;
  margin-top: 4px;
}

/* 数据概览 */
.stats-overview {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 10px;
  margin-bottom: 10px;
}

.stat-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.stat-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.stat-content {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.stat-title {
  font-size: 14px;
  color: #6b7280;
  display: block;
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  color: #1f2937;
  margin-top: 4px;
  display: block;
}

.stat-change {
  font-size: 12px;
  margin-top: 4px;
  display: flex;
  align-items: center;
  gap: 2px;
}

.stat-change.up {
  color: #10b981;
}

.stat-change.down {
  color: #ef4444;
}

.stat-icon {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
}

.blue-icon {
  background: rgba(59, 130, 246, 0.1);
  color: #3b82f6;
}

.green-icon {
  background: rgba(16, 185, 129, 0.1);
  color: #10b981;
}

.purple-icon {
  background: rgba(139, 92, 246, 0.1);
  color: #8b5cf6;
}

.amber-icon {
  background: rgba(245, 158, 11, 0.1);
  color: #f59e0b;
}

/* 图表区域 */
.charts-section {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 24px;
  margin-bottom: 5px;
}

.chart-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.chart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.chart-title {
  font-size: 18px;
  font-weight: 600;
  color: #1f2937;
}

.chart-tabs {
  display: flex;
  gap: 8px;
}

.tab {
  padding: 4px 12px;
  font-size: 12px;
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.2s;
}

.tab.active {
  background: rgba(79, 70, 229, 0.1);
  color: #4f46e5;
  font-weight: 500;
}

.tab:not(.active):hover {
  background: #f3f4f6;
}

.chart-container {
  height: 300px;
}

/* 学习记录 */
.learning-records,
.key-words {
  margin-bottom: 5px;
}

.records-header,
.words-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #1f2937;
}

.view-all {
  font-size: 14px;
  color: #4f46e5;
  cursor: pointer;
}

.view-all:hover {
  text-decoration: underline;
}

.record-list {
  max-height: 800px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.record-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 12px;
  background: #ffffff;
  border-radius: 8px;
  transition: background 0.2s;
}

.record-item:hover {
  background: #f9fafb;
}

.record-icon {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
}

.blue-bg {
  background: rgba(59, 130, 246, 0.1);
  color: #3b82f6;
}

.green-bg {
  background: rgba(16, 185, 129, 0.1);
  color: #10b981;
}

.purple-bg {
  background: rgba(139, 92, 246, 0.1);
  color: #8b5cf6;
}

.amber-bg {
  background: rgba(245, 158, 11, 0.1);
  color: #f59e0b;
}

.record-info {
  flex: 1;
  min-width: 0;
}

.record-title {
  font-size: 14px;
  font-weight: 500;
  color: #1f2937;
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.record-desc {
  font-size: 12px;
  color: #6b7280;
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.record-time {
  text-align: right;
}

.duration {
  font-size: 14px;
  font-weight: 500;
  color: #1f2937;
  display: block;
}

.time {
  font-size: 12px;
  color: #9ca3af;
}


/* 响应式设计 */
@media (max-width: 768px) {
  .nav-bar {
    flex-direction: column;
    height: auto;
    padding: 12px 20px;
    gap: 12px;
  }
  
  .search-bar {
    margin: 0;
    max-width: 100%;
  }
  
  .charts-section {
    grid-template-columns: 1fr;
  }
  
  .stats-overview {
    grid-template-columns: 1fr;
  }
}
</style>