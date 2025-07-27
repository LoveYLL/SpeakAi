<template>
  <view class="video-container" :style="containerStyle">
    <video
      ref="videoRef"
      class="video-player"
      :src="src"
      muted
      autoplay
      loop
      preload="auto"
    ></video>
    <view class="gradient-mask" :style="maskStyle"></view>
  </view>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'

// Props 定义
const props = defineProps({
  src: {
    type: String,
    required: true
  },
  basicPx: {
    type: Number,
    default: 50
  },
  gradientOpacity: {
    type: Number,
    default: 0.4
  },
  gradientStart: {
    type: String,
    default: 'bottom'
  }
})

const videoRef = ref(null)
const containerStyle = computed(() => ({ 
  width: props.basicPx * 9 + 'px',
  height: props.basicPx * 16 + 'px'
}))
// 蒙版样式计算
const maskStyle = computed(() => ({
  background: `linear-gradient(to top, rgba(0, 0, 0, ${props.gradientOpacity}), rgba(0, 0, 0, ${props.gradientOpacity * 0.5}) 30%, transparent 70%)`
}))

// 视频事件处理
const handleVideoEnd = () => {
  console.log('视频播放结束')
}

const handleVideoStart = () => {
  console.log('视频开始播放')
}

const handleVideoError = (error) => {
  console.error('视频播放错误:', error)
}


</script>

<style scoped>
.video-container {
  position: relative;
  overflow: hidden;
}

.video-player {
  width: 105%;
  height: 100%;
  object-fit: contain; /* 改为contain保持完整比例 */
  background-color: transparent; /* 移除默认黑色背景 */
  width: 100%; /* 确保宽度填满 */
}

.gradient-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    to top, 
    rgba(0, 0, 0, calc(v-bind('props.gradientOpacity') * 0.8)), 
    transparent 70%
  );
  pointer-events: none;
}
</style>