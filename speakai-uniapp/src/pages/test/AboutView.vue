<script setup>
import { onMounted } from 'vue'
import { animate } from 'motion'
onMounted(() => {
  setTimeout(() => {
    animate('.box',{
      rotate: 360
    },{
      duration: 5
    }) 
  },2000)  
})
</script>


<template>
  <div class="box">

  </div>
</template>


<style scoped>
.box {
  width: 300px;
  margin: 100px 100px;
  height: 300px;
  border: 1px solid red;
}
</style>