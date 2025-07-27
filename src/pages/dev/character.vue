<template>
  <view class="character-page">
    <!-- 顶部导航栏 -->
    <view class="navbar">
      <image src="/static/logo.png" class="logo"></image>
      <view class="title">选择你的英语导师</view>
      <view class="menu-btn"></view>
    </view>

    <!-- 主内容区 -->
    <view class="main-content">
      <!-- 角色列表 -->
      <view class="character-list">
        <view 
          class="character-card" 
          v-for="(character, index) in characters" 
          :key="index"
          @click="selectCharacter(character)"
        >
          <view class="avatar-container">
            <image
              :src="character.avatarUrl"
              class="avatar"
            />
          </view>
          <view class="character-info">
            <text class="name">{{character.name}}</text>
            <text class="tag">{{character.tag}}</text>
            <text class="description">{{character.description}}</text>
            <button class="start-btn" @click.stop="startChat">开始聊天</button>
          </view>
        </view>
      </view>
      <!-- 右侧视频区域 -->
      <view class="video-container">
        <view v-if="selectedCharacter" class="videoF" >
          <view class="videol" >
            <VideoWithMask
            class="videol-item"
            :src="selectedCharacter.videoUrl"
            :basicPx="50"
            :gradientOpacity="1.0"
          />
          </view>
          <view class="tall">
            <Tall :voice="voice" v-if="isCreate" />
          </view>
        </view>
        <view v-else class="video-placeholder">
          <text class="placeholder-text">选择一位导师开始对话</text>
        </view>
      </view>
    </view>

    
  </view>
</template>

<script setup>
import { ref } from 'vue';
import Tall from '../../components/Tall.vue'
import VideoWithMask from '@/components/VideoWithMask.vue';
const selRole = ref({
  text:'',
  roleCode:''
})
const voice = ref('')
const isCreate = ref(false)
// 角色数据
const characters = [
  {
    id: 1,
    name: '英语纠错官Aria',
    tag: '英语(美)',
    modelName: 'Aria',
    description: '会对你说出的英语使用中文进行纠错及润色，然后再用简单的英语维持对话，方便初学者进行口语练习',
    avatarUrl: '/src/static/vidaoORimg/东南亚女.png',
    videoUrl: '/src/static/vidaoORimg/东南亚女不说话.mp4'
  },
  {
    id: 2,
    name: 'TalkAI助教Emma',
    tag: '全能学习助手',
    modelName: 'Emma',
    description: '超级全能的语言学习伙伴，提供语法讲解、词汇拓展和文化背景介绍',
    avatarUrl: '/src/static/vidaoORimg/中国女.png',
    videoUrl: '/src/static/vidaoORimg/中国女不说话.mp4'
  },
  {
    id: 3,
    name: '英语零基础外教Libby',
    tag: '中英混说',
    modelName: 'Libby',
    description: '艾拉是一位温柔耐心的英语口语导师，专门帮助零基础的中文母语者建立英语表达自信',
    avatarUrl: '/src/static/vidaoORimg/经典女.png',
    videoUrl: '/src/static/vidaoORimg/经典女不说话.mp4'
  },
  {
    id: 4,
    name: '商务英语专家Elliot',
    tag: '商务英语',
    modelName: 'Elliot',
    description: '专注于商务场景对话，帮助你掌握谈判技巧、邮件写作和商务Presentation能力',
    avatarUrl: '/src/static/vidaoORimg/经典男.png',
    videoUrl: '/src/static/vidaoORimg/经典男不说话.mp4'
  },
  {
    id: 5,
    name: '旅游英语达人Ashley',
    tag: '旅游场景',
    moduleName: 'Ashley',
    description: '模拟各种旅游场景对话，从机场入境到酒店入住，让你的海外旅行畅通无阻',
    avatarUrl: '/src/static/vidaoORimg/北美女.png',
    videoUrl: '/src/static/vidaoORimg/北美女不说话.mp4'
  },
  {
    id: 6,
    name: '学术英语教授John',
    tag: '学术写作',
    modelName:'John',
    description: '专注于论文写作和学术演讲，帮助你提升学术英语水平和发表成功率',
    avatarUrl: '/src/static/vidaoORimg/北美男.png',
    videoUrl: '/src/static/vidaoORimg/北美男不说话.mp4'
  },
  {
    id: 7,
    name: '日常口语伙伴Natasha',
    tag: '生活场景',
    modelName:'Natasha',
    description: '模拟各种日常生活场景对话，让你轻松应对购物、餐饮、就医等日常交流',
    avatarUrl: '/src/static/vidaoORimg/国漫女.png',
    videoUrl: '/src/static/vidaoORimg/国漫女不说话.mp4'
  },
  {
    id: 8,
    name: '发音教练James',
    tag: '发音矫正',
    modelName:'James',
    description: '专注于英语发音矫正和语调训练，帮助你说出地道流利的英语',
    avatarUrl: '/src/static/vidaoORimg/国漫男.png',
    videoUrl: '/src/static/vidaoORimg/国漫男不说话.mp4'
  }
];

// 选中的角色
const selectedCharacter = ref(null);
const startChat = () => {
  isCreate.value = true;
  const videol = document.querySelector('.videol');
  const tall = document.querySelector('.tall');
  if(videol){
    videol.classList.add('shrink');
  }
  if(tall){
    tall.classList.add('shrink');
  }
}

// 选择角色
const selectCharacter = (character) => {
  selectedCharacter.value = character;
  selRole.value.text = `you are ${character.modelName},now please practice spoken englist with me.`
  selRole.value.roleCode = character.modelName;
  voice.value = ''
  voice.value = character.modelName;
  console.log('54646');
  const videol = document.querySelector('.videol');
  const tall = document.querySelector('.tall'); 
    if (videol) {
      videol.classList.remove('shrink');
    }
    if (tall) {
      tall.classList.remove('shrink');
    }
  isCreate.value = false;  
};


</script>

<style scoped lang="scss">
/* 页面基础样式 */
.character-page {
  height: 94.7vh;
  overflow: hidden;
  background: #f9fafb;
  display: flex;
  flex-direction: column;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', sans-serif;
}

/* 顶部导航栏 */
.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  background: #ffffff;
  border-bottom: 1px solid #e5e7eb;
  height: 16px;
}

.logo {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  background: linear-gradient(135deg, #4f46e5, #7c3aed);
}

.title {
  font-size: 20px;
  font-weight: 700;
  color: #1f2937;
}

.menu-btn {
  width: 28px;
  height: 28px;
  background: rgba(79, 70, 229, 0.1);
  border-radius: 14px;
}

/* 角色列表 */
.main-content {
  display: flex;
  height: 95%;
  flex: 1;
  padding: 20px;
}

.character-list {
  width: 45%;
  height: 96%;
  margin: 0 auto;
  padding-right: 20px;
  overflow-y: auto;
  scrollbar-width: none;
}

.video-container {
  width: 60%;
  height: 95%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.videoF{
  width: 99.5%; 
  height: 100%;
  position: relative;
  .tall{
  height: 99%;
  width: 0;
  position: absolute;
  top: 0;
  right: 2%;
  transition: width 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94)
  }
  .tall.shrink{
    width: 50%;
  }
}

.videol {
  height: 100%;
  width: 70%;
  position: relative;
  overflow: hidden; /* 确保动画不会溢出 */
  transition: width 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94); /* 添加width过渡动画 */
  .videol-item {
    position: absolute;
    right: 0;
  }
  .tall{
  border: 1px solid green;
  height: 100%;
  width: 50px;
  position: absolute;
  }
}


/* 收缩状态 - width从70%到45% */
.videol.shrink {
  width: 45%;
}


.video-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.placeholder-text {
  color: #6b7280;
  font-size: 18px;
  text-align: center;
  padding: 20px;
}

.avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.character-card {
  background: #ffffff;
  border-radius: 12px;
  margin-bottom: 20px;
  margin-left: 10%;
  width: 80%;
  padding: 20px;
  display: flex;
  align-items: flex-start;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border: 1px solid #e5e7eb;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.character-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.avatar-container {
  --bas-px: 10px;
  position: relative;
  width: calc(var(--bas-px) * 9);
  height: calc(var(--bas-px) * 16);
  margin-right: 16px;
  flex-shrink: 0;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.character-info {
  flex: 1;
  min-width: 0;
}

.name {
  font-size: 18px;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 6px;
  display: block;
  line-height: 1.2;
}

.tag {
  font-size: 13px;
  color: #4f46e5;
  background: rgba(79, 70, 229, 0.1);
  padding: 4px 12px;
  border-radius: 16px;
  margin-bottom: 10px;
  display: inline-block;
  font-weight: 600;
  border: 1px solid rgba(79, 70, 229, 0.2);
}

.description {
  font-size: 15px;
  color: #6b7280;
  line-height: 1.5;
  margin-bottom: 16px;
  display: block;
  text-align: justify;
}

.start-btn {
  background: #4f46e5;
  color: white;
  border: none;
  padding: 10px 24px;
  border-radius: 25px;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 1px 3px rgba(79, 70, 229, 0.3);
}

.start-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(79, 70, 229, 0.4);
}

.start-btn:active {
  transform: translateY(0);
  box-shadow: 0 1px 3px rgba(79, 70, 229, 0.3);
}

/* 响应式设计 */
@media (max-width: 375px) {
  .character-card {
    padding: 16px;
  }
  
  .avatar-container {
    width: 80px;
    height: 80px;
    margin-right: 12px;
  }
  
  .name {
    font-size: 16px;
  }
  
  .description {
    font-size: 14px;
  }
}

@media (min-width: 768px) {
  .character-list {
    max-width: 600px;
    margin: 0 auto;
  }
}

body {
  margin: 0;
  padding: 0;
}
</style>
