<template>
  <view class="about">
    <button @click="ws.open()">连接</button>
    <button @click="ws.close()">关闭连接</button>
    <p>{{ backMsg }}</p>
    <p>{{ msg }}</p>
    <input v-model="msg" placeholder="请输入发送的话"/>
    <button @click="send">发送</button>
  </view>
</template>

<script setup>
import { useWebSocket } from '@vueuse/core'
import { ref } from 'vue'
const backMsg = ref('')
const msg = ref('')
const ws = useWebSocket(`ws://udef3672.natappfree.cc/websocket/256`,{
    immediate: false,
    autoReconnect: false,
    heartbeat: false,
    autoClose: false,
    onConnected(){
        console.log('连接成功');
    },
    onDisconnected(ws,event){
        console.log('connect fail',event.code);
        console.log('connect fail',event.reason);
    },
    onMessage(data){
        backMsg.value = data 
    }
})

const send = () => {
   ws.send(msg.value)
} 

</script>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
