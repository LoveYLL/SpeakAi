<script setup>
import { onMounted, ref } from 'vue';
import Recorder from 'js-audio-recorder'
let recorder = new Recorder()
const j = ref(new Blob())

const ss = () => {
  j.value = recorder.getWAVBlob()
  
  console.log(j.value);
}

const string1 = ref('');
const string2 = ref(`噫吁嚱，危乎高哉！蜀道之难，难于上青天！蚕丛及鱼凫，开国何茫然！尔来四万八千岁，不与秦塞通人烟。西当太白有鸟道，可以横绝峨眉巅。地崩山摧壮士死，然后天梯石栈相钩连。上有六龙回日之高标，下有冲波逆折之回川。黄鹤之飞尚不得过，猿猱欲度愁攀援。青泥何盘盘，百步九折萦岩峦。扪参历井仰胁息，以手抚膺坐长叹。
问君西游何时还？畏途巉岩不可攀。但见悲鸟号古木，雄飞雌从绕林间。又闻子规啼夜月，愁空山。蜀道之难，难于上青天，使人听此凋朱颜！连峰去天不盈尺，枯松倒挂倚绝壁。飞湍瀑流争喧豗，砯崖转石万壑雷。其险也如此，嗟尔远道之人胡为乎来哉！
剑阁峥嵘而崔嵬，一夫当关，万夫莫开。所守或匪亲，化为狼与豺。朝避猛虎，夕避长蛇；磨牙吮血，杀人如麻。锦城虽云乐，不如早还家。蜀道之难，难于上青天，侧身西望长咨嗟！`);
const textTime= ref();

function print(){
  if (string1.value.length >= string2.value.length) {
  // 退出递归
    return;
  } else {
  // string1仍然比string2短，进入下一次等待和递归
     textTime.value = setTimeout(()=>{
      string1.value += string2.value.charAt(string1.value.length);
      print();
    }, 50);
  }
}
const stop = () => { 
  clearTimeout(textTime.value);
}
const reset = () => {
  clearTimeout(textTime.value);
  string1.value = '';
  string2.value = 'hello world';
  print()

}

</script>
 
<template>
  <main>
    <p id="pp" class="text">{{ string1 }}</p>
    <button @click="print"><p class="text">点击实现</p></button>
    <button @click="stop"><p class="text">点击停止</p></button>
    <button @click="reset"><p class="text">点击重置</p></button>
  </main>
  <!-- <view class="about">
    <button @click="recorder.start()">点击录音开始</button>
    <button @click="recorder.pause()">点击录音暂停</button>
    <button @click="recorder.resume()">点击录音恢复</button>
    <button @click="ss">点击录音停止</button>
    <button @click="recorder.play()">点击录音播放</button>
  </view> -->
</template>
 
<style scoped>
  .text {
    font-size: 30px;
  }
</style>
 