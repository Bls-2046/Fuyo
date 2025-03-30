<template>
  <div class="home-container">
    <div class="tips">
      <a href="https://api-docs.deepseek.com/zh-cn/news/news250325" target="_blank" style="text-decoration: none;">
        <button @click="openNewBrowserWindow">
          🎉 DeepSeek-V3 模型更新，各项能力全面进阶，在网页端、APP 和 API 全面上线，点击查看详情。
        </button>
      </a>
    </div>

    <div class="subhead">
      探索未至之境
    </div>

      <div class="cta">
        <button>EXPLORE NOW</button>
      </div>
  </div>
</template>

<script setup lang="ts">
const openNewBrowserWindow = () => {
  window.open(
      "https://api-docs.deepseek.com/zh-cn/news/news250325",
      "_blank",
      "noopener,noreferrer"
  );
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap');

.home-container {
  position: fixed;  /* 关键属性 */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: url("../assets/images/DeepSeek-Chat-Background.png");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  z-index: -1;
  overflow: hidden;
}

.tips {
  position: absolute;
  display: flex;
  justify-content: center;
  width: 100%;
  top:10%;
  color: rgb(147, 162, 183);
  font-size: 20px;
}

.tips button {
  font-size: 20px;
  font-weight: 550;
  text-transform: uppercase;
  color: rgb(147, 162, 183);
  background-color: transparent;
  border: 0 solid;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
  cursor: pointer;
}

.tips button:hover {
  color: rgb(86, 93, 93);
}

.subhead {
  position: absolute;
  display: flex;
  justify-content: center;
  width: 100%;
  top:46%;
  font-size: 45px;
  font-weight: 550;
  color: rgb(70, 84, 104);
}

.cta {
  position: absolute;
  bottom: 20%;
  display: flex;
  justify-content: center;
  width: 100%;
  z-index: 1;
}

.cta button {
  font-size: 25px;
  font-weight: 550;
  letter-spacing: 5px;
  text-transform: uppercase;
  color: rgb(255, 255, 255);
  border: 3px solid rgb(52, 135, 253);
  background-color: rgb(52, 135, 253);
  border-radius: 30px;
  height: 60px;
  width: 350px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  transition: all 0.2s;
  cursor: pointer;
}

.cta button:hover {
  font-size: 26px;
  width: 355px;
  height: 61px;
}
</style>