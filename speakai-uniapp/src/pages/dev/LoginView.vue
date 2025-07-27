<template>
  <view class="auth-container">
    <!-- 主要内容区域 -->
    <main class="main-content">
      <view class="auth-wrapper">
        <view class="brand-section">
          <!-- 左侧品牌介绍 -->
          <view class="brand-content">
            <view class="logo">
              <image src="/static/logo.png" mode="aspectFit" class="logo-img"></image>
            </view>
            <h1 class="brand-title">
              提升口语能力
              <text class="highlight">从这里开始</text>
            </h1>
            <p class="brand-description">
              SpeakAI助您突破语言障碍，通过智能练习和实时反馈，快速提升口语水平，自信流利地交流。
            </p>
          </view>
        </view>

        <view class="form-section">
          <!-- 右侧登录注册表单卡片 -->
          <view class="form-card">
            <!-- 表单切换标签 -->
            <view class="form-tabs">
              <text 
                :class="['tab', activeTab === 'login' ? 'active' : '']"
                @click="switchTab('login')"
              >登录</text>
              <text 
                :class="['tab', activeTab === 'register' ? 'active' : '']"
                @click="switchTab('register')"
              >注册</text>
            </view>

            <!-- 登录表单 -->
            <view v-if="activeTab === 'login'" class="form-content">
              <view class="mobile-header">
                <view class="logo-small">
                  <image src="/static/logo.png" mode="aspectFit"></image>
                </view>
                <h2 class="app-name">SpeakAI</h2>
                <p class="app-tagline">提升您的口语能力</p>
              </view>

              <form @submit.prevent="handleLogin">
                <view class="form-group">
                  <text class="label">账号</text>
                  <view class="input-wrapper">
                    <text class="input-icon">📧</text>
                    <input 
                      type="email" 
                      v-model="loginForm.username"
                      class="input"
                      placeholder="your@email.com"
                      required
                    />
                  </view>
                </view>

                <view class="form-group">
                  <view class="label-row">
                    <text class="label">密码</text>
                  </view>
                  <view class="input-wrapper">
                    <text class="input-icon">🔒</text>
                    <input 
                      :type="showLoginPassword ? 'text' : 'password'"
                      v-model="loginForm.password"
                      class="input"
                      placeholder="••••••••"
                      required
                    />
                    <text 
                      class="toggle-password"
                      @click="togglePassword('login')"
                    >{{ showLoginPassword ? '👁️' : '👁️‍🗨️' }}</text>
                  </view>
                </view>

                <button type="submit" class="submit-btn" @click="handleLogin">
                  <text>登录</text>
                  <text class="btn-icon">→</text>
                </button>
              </form>
            </view>

            <!-- 注册表单 -->
            <view v-if="activeTab === 'register'" class="form-content">
              <view class="mobile-header">
                <view class="logo-small">
                  <image src="/static/logo.png" mode="aspectFit"></image>
                </view>
                <h2 class="app-name">SpeakAI</h2>
                <p class="app-tagline">创建账户，开始您的口语之旅</p>
              </view>

              <form @submit.prevent="handleRegister">
                <view class="form-group">
                  <text class="label">账号</text>
                  <view class="input-wrapper">
                    <text class="input-icon">📧</text>
                    <input 
                      type="email" 
                      v-model="registerForm.username"
                      class="input"
                      placeholder="请输入账号....."
                      required
                    />
                  </view>
                </view>

                <view class="form-group">
                  <text class="label">密码</text>
                  <view class="input-wrapper">
                    <text class="input-icon">🔒</text>
                    <input 
                      :type="showRegisterPassword ? 'text' : 'password'"
                      v-model="registerForm.password"
                      class="input"
                      placeholder="••••••••"
                      required
                    />
                    <text 
                      class="toggle-password"
                      @click="togglePassword('register')"
                    >{{ showRegisterPassword ? '👁️' : '👁️‍🗨️' }}</text>
                  </view>
                </view>
                <button type="submit" class="submit-btn" @click="handleRegister">
                  <text>注册</text>
                  <text class="btn-icon">👤</text>
                </button>

                <view class="switch-form">
                  <text>已有账号?</text>
                  <text class="switch-link" @click="switchTab('login')">立即登录</text>
                </view>
              </form>
            </view>
          </view>
        </view>
      </view>
    </main>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { Post } from '../../utils/request'
// 响应式数据
const activeTab = ref('login')
const showLoginPassword = ref(false)
const showRegisterPassword = ref(false)

const loginForm = ref({
  username: '',
  password: ''
})

const registerForm = ref({
  username: '',
  password: ''
})

// 方法
const switchTab = (tab) => {
  activeTab.value = tab
}

const togglePassword = (type) => {
  if (type === 'login') {
    showLoginPassword.value = !showLoginPassword.value
  } else {
    showRegisterPassword.value = !showRegisterPassword.value
  }
}

const handleLogin = () => {
  console.log('登录表单提交:', loginForm.value)
  let username = loginForm.value.username.trim()
  let password = loginForm.value.password.trim()
  if (username && password) {
    Post('auth/login',loginForm.value,false).then(resp=>{ 
      if(resp.code === 200){
        uni.showToast({
          title: '登录成功',
          icon: 'success'
        })
        uni.switchTab({ url: '/pages/index/index' })
      }else{
        uni.showToast({
          title: resp.msg,
          icon: 'error',
          duration: 1000
        })
      }
    })
  }else{
    uni.showToast({
      title: '请填写完整信息',
      icon: 'error',
      duration: 1000
    })
  }

}

const handleRegister = () => {
  console.log('注册表单提交:', registerForm.value)
  // 这里添加注册逻辑
  let username = registerForm.value.username.trim()
  let password = registerForm.value.password.trim()
  if (username && password) {
    Post('auth/register',registerForm.value,false).then(resp=>{ 
      if(resp.code === 200){
        uni.showToast({
          title: '注册成功',
          icon: 'success'
        })
        switchTab('login')
      }else{
        uni.showToast({
          title: resp.msg,
          icon: 'error'
        })
      }
    })
  }else{
    uni.showToast({
      title: '请填写完整信息',
      icon: 'error'
    })
  }
}






</script>

<style scoped>
/* 基础样式 */
.auth-container {
  height: 100vh;
  background: linear-gradient(135deg, #f3f4f6 0%, #e5e7eb 100%);
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', sans-serif;
}

.main-content {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 95vh;
  padding: 20px;
}

.auth-wrapper {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 32px;
  align-items: center;
}

/* 品牌区域 */
.brand-section {
  display: flex;
  align-items: center;
  justify-content: center;
}

.brand-content {
  text-align: center;
}

.logo {
  width: 64px;
  height: 64px;
  margin: 0 auto 24px;
}

.logo-img {
  width: 100%;
  height: 100%;
  border-radius: 12px;
}

.brand-title {
  font-size: clamp(2rem, 5vw, 3.5rem);
  font-weight: 700;
  color: #1f2937;
  line-height: 1.2;
  margin-bottom: 16px;
}

.highlight {
  color: #4f46e5;
}

.brand-description {
  font-size: 18px;
  color: #6b7280;
  line-height: 1.6;
  margin-bottom: 32px;
  max-width: 400px;
  margin-left: auto;
  margin-right: auto;
}





/* 表单区域 */
.form-section {
  display: flex;
  align-items: center;
  justify-content: center;
}

.form-card {
  width: 100%;
  max-width: 400px;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.form-tabs {
  display: flex;
  border-bottom: 1px solid #e5e7eb;
}

.tab {
  flex: 1;
  padding: 16px 0;
  text-align: center;
  font-weight: 500;
  color: #6b7280;
  cursor: pointer;
  transition: all 0.2s;
}

.tab.active {
  color: #4f46e5;
  border-bottom: 2px solid #4f46e5;
}

.tab:hover:not(.active) {
  color: #374151;
}

.form-content {
  padding: 24px;
}

.mobile-header {
  display: none;
  text-align: center;
  margin-bottom: 24px;
}

.logo-small {
  width: 48px;
  height: 48px;
  margin: 0 auto 8px;
}

.app-name {
  font-size: 24px;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 4px;
}

.app-tagline {
  font-size: 14px;
  color: #6b7280;
}

.form-group {
  margin-bottom: 16px;
}

.label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: #374151;
  margin-bottom: 4px;
}

.label-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 4px;
}



.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.input-icon {
  position: absolute;
  left: 12px;
  font-size: 16px;
  color: #9ca3af;
  z-index: 1;
}

.input {
  width: 100%;
  padding: 12px 12px 12px 40px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 14px;
  transition: all 0.2s;
  outline: none;
}

.input:focus {
  border-color: #4f46e5;
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
}

.toggle-password {
  position: absolute;
  right: 12px;
  font-size: 16px;
  color: #9ca3af;
  cursor: pointer;
  z-index: 1;
}



.submit-btn {
  width: 100%;
  background: #4f46e5;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.submit-btn:hover {
  background: #4338ca;
  transform: translateY(-1px);
}

.submit-btn:active {
  transform: translateY(0);
}

.btn-icon {
  font-size: 14px;
}







.switch-form {
  text-align: center;
  margin-top: 24px;
  font-size: 14px;
  color: #6b7280;
}

.switch-link {
  color: #4f46e5;
  cursor: pointer;
  font-weight: 600;
}

.switch-link:hover {
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .auth-wrapper {
    grid-template-columns: 1fr;
    gap: 20px;
  }

  .brand-section {
    display: none;
  }

  .mobile-header {
    display: block;
  }

  .form-content {
    padding: 20px;
  }
}

@media (max-width: 480px) {
  .main-content {
    padding: 10px;
  }

  .form-card {
    border-radius: 0;
    box-shadow: none;
  }
}
</style>