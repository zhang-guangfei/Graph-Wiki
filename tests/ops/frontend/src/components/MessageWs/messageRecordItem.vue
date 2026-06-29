<template>
  <div class="record-wrapper-message-records">
    <div class="msg msg-left">
      <div class="img-wrapper">
        <img :src="fromUser === currentUser ? from1 : from2" class="img" >
      </div>
      <div class="message-wrapper message-wrapper-left">
        <div style="display: inline; width: 300px">
          <span style="margin-left: 5px; margin-right: 50px; width: 100px">{{ fromUser }}</span>
          <span class="time">{{ timestamp | formatTimeToMsg }}</span>
        </div>
        <div class="message"><span>{{ messageText }}</span></div>
        <el-divider/>
      </div>
    </div>
  </div>
</template>

<script>
// import { formatTime } from '@/utils/index'
import { parseTime } from '@/utils/index'
export default {
  name: 'MessageRecordItem',
  filters: {
    formatTimeToMsg: function(value) {
      // return formatTime(value)
      return parseTime(value, '{y}-{m}-{d} {h}:{i}:{s}')
    }
  },
  props: {
    fromUser: {
      type: String,
      default: ''
    },
    messageText: {
      type: String,
      default: ''
    },
    timestamp: {
      type: Date,
      default: new Date()
    }
  },
  data() {
    return {
      from1: require('@/assets/img/tx2.png'),
      from2: require('@/assets/img/tx3.png'),
      currentUser: this.$store.getters.info.userId
    }
  }
}
</script>
<style lang="scss" scoped>
.record-wrapper-message-records {
  margin: 4px;
  padding: 4px;
  .time {
    text-align: right;
    font-size: 5px;
    width: 200px;
    color: rgb(117, 117, 115);
  }
  .msg {
    display: flex;
    flex-direction: row;

    .message-wrapper {
      max-width: 420px;
      min-width: 400px;
      margin: 0px 10px 0px 10px;

      .message {
        margin: 8px;
        word-wrap: break-word; //英文换行
      }
    }

    // .message-wrapper-left {
    //   // background-color: rgb(243, 232, 135);
    //   // border-radius: 0px 12px 12px 12px;
    // }

    .message-wrapper-right {
      background-color: #27e9e9;
      border-radius: 12px 0px 12px 12px;
    }

    .img {
      flex: auto;
      height: 36px;
      width: 36px;
      border-radius: 8px;
    }
  }
  .msg-right {
    flex-direction: row-reverse;
  }
  .msg-left {
    flex-direction: row;
  }
}
</style>
