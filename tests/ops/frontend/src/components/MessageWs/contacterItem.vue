<template>
  <div :class="isSelected ? 'contact-wrapper-selected' : 'contact-wrapper'" style="cursor:default" @click="clickContactItem">
    <div class="contact">
      <div class="img-wrapper">
        <el-badge :hidden="contacter.unreadNum === 0" :value="contacter.unreadNum" :max="10" class="item">
          <img :src="(keyNo % 2 == 0) ? from1 : from2" class="img" >
        </el-badge>
      </div>
      <div class="contact-user-wrapper">
        <div class="contact-name">
          <span >{{ (contacter.userName === null || contacter.userName === '') ? contacter.nickName : contacter.userName }}</span>
        </div>
        <div class="contact-msg">{{ contacter.messageList.length > 0 ? contacter.messageList[contacter.messageList.length - 1].messageText : '' }}</div>
      </div>
      <span style="font-size: 10px; color: #999999; margin-left: 10px; width: 80px; text-align: right">{{ contacter.messageList.length > 0 ? contacter.messageList[contacter.messageList.length - 1].timestamp : null | formatTimeMToItem }}</span>
    </div>
  </div>
</template>
<script>
import { formatTime, parseTime, formatTimeM } from '@/utils/index'
// import { stringToDate } from '@/utils/dateHandle'
export default {
  name: 'ContacterItem',
  filters: {
    formatTimeToMsg: function(value) {
      return formatTime(value)
    },
    parseTime,
    formatTimeMToItem: function(value) {
      return formatTimeM(value)
    }
  },
  props: {
    keyNo: {
      type: Number,
      default: -1
    },
    isSelected: {
      type: Boolean,
      default: false
    },
    contacter: {
      type: Object,
      default: () => {
        return {}
      }
    }
    // ,
    // timestamp: {
    //   type: Date,
    //   default: new Date()
    // }
  },
  data() {
    return {
      from1: require('@/assets/img/tx2.png'),
      from2: require('@/assets/img/tx3.png')
    }
  },
  methods: {
    clickContactItem() {
      this.$emit('changeSelected', this.keyNo)
    }
  }
}
</script>
<style lang="scss" scoped>
.contact-wrapper {
  margin: 4px;
  padding: 4px;
}
.contact-wrapper-selected {
  margin: 4px;
  padding: 4px;
  background: #C8C6C6;
}
.contact-wrapper:hover {
  background: #DFDDDB;
}
.contact-wrapper:focus {
  background: red;
}
.contact-name {
  width: 100px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap; margin-top: 5px;
}
.contact-msg {
  width: 100px; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;
  font-size: 10px; color: #999999;
}
.time {
  text-align: center;
  margin: 3px 0px;
  font-size: 5px;
  color: rgb(117, 117, 115);
}
.contact {
  display: flex;
  flex-direction: row;
  .img-wrapper {
    width: 50px;
    height: 50px;
  }
  .img {
    height: 36px;
    width: 36px;
    border-radius: 8px;
  }
  .contact-user-wrapper {
    flex-direction: column;
    max-width: 200px;
  }
}
.item {
  margin-top: 0px;
  margin-right: 40px;
}
</style>
