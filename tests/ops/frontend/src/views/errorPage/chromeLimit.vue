<template>
  <div class="errPage-container">
    <el-card>
      <el-divider><el-tag style="font-size: 15px"><i class="el-icon-sunny"/> 为了更好得体验~</el-tag></el-divider>
      <el-row>
        <el-col :span="12" style="text-align: center">
          <p style="color: red;font-size: 20px; ">
            <a target="_blank" href="https://www.google.cn/chrome/">请您务必使用Chrome浏览器打开【点击下载】</a>
          </p>
        </el-col>
        <el-col :span="12">
          <img :src="smile" width="313" height="313" alt="Girl has dropped her ice cream.">
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
// import errGif from '@/assets/401_images/401.gif'
import smile from '@/assets/img/smile.png'

export default {
  name: 'PageChrome',
  data() {
    return {
      // errGif: errGif + '?' + +new Date(),
      smile: smile,
      ewizardClap: 'https://wpimg.wallstcn.com/007ef517-bafd-4066-aae4-6883632d9646',
      dialogVisible: false
    }
  },
  computed: {
  },
  methods: {
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .errPage-container {
    width: 1000px;
    max-width: 100%;
    margin: 100px auto;
    .pan-back-btn {
      background: #008489;
      color: #fff;
      border: none!important;
    }
    .pan-gif {
      margin: 0 auto;
      display: block;
    }
    .pan-img {
      display: block;
      margin: 0 auto;
      width: 100%;
    }
    .text-jumbo {
      font-size: 60px;
      font-weight: 700;
      color: #484848;
    }
    .list-unstyled {
      font-size: 14px;
      li {
        padding-bottom: 5px;
      }
      a {
        color: #008489;
        text-decoration: none;
        &:hover {
          text-decoration: underline;
        }
      }
    }
  }
</style>
