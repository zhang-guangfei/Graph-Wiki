<template>
  <el-badge :value="num>0 ? num:''" :max="99" class="item">
    <i class="el-icon-chat-dot-round" @click="msgShow()"/>
  </el-badge>
</template>
<script>
export default {
  name: 'Elbadge',
  props: {
    num: {
      type: Number,
      default: function() {
        return ''
      }
    }
  },
  data() {
    return {
      dialogTableVisible: false
    }
  },
  methods: {
    msgShow() {
      this.dialogTableVisible = true
      this.$emit('showDialog', this.dialogTableVisible)
    }
  }
}
</script>
<style lang="scss" scoped>
.item {
  margin-top: -20px;
  margin-right: 34px;
}
.el-icon-chat-dot-round {
  font-size: 20px;
  color: #fff;
  position: absolute;
}
</style>
<style lang="scss">
.el-badge{
  .el-badge__content.is-fixed {
    right: -10px;
  }
}
</style>

