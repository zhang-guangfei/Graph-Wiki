<template>
  <div class="record-wrapper">
    <div class="time">{{ timestamp | formatTimeToMsg }}</div>
    <div v-if="fromUser === currentUser" class="msg msg-right">
      <div class="img-wrapper">
        <img :src="from1" class="img" >
      </div>
      <div class="message-wrapper message-wrapper-right">
        <div class="message"><span>{{ messageText }}</span></div>
      </div>
    </div>

    <div v-else class="msg msg-left">
      <div class="img-wrapper">
        <img :src="from2" class="img" >
      </div>
      <div class="message-wrapper message-wrapper-left">
        <div class="message"><span>{{ messageText }}</span></div>
      </div>
    </div>
  </div>
</template>

<script>
import { formatTime } from '@/utils/index'
export default {
  name: '',
  filters: {
    formatTimeToMsg: function(value) {
      return formatTime(value)
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
.record-wrapper {
  margin: 4px;
  padding: 4px;
}
.time {
  text-align: center;
  margin: 3px 0px;
  font-size: 5px;
  color: rgb(117, 117, 115);
}
.msg {
  display: flex;
  flex-direction: row;

  .message-wrapper {
    max-width: 220px;

    margin: 0px 10px 0px 10px;

    .message {
      margin: 8px;
      word-wrap: break-word; //英文换行
    }
  }

  .message-wrapper-left {
    background-color: rgb(243, 232, 135);
    border-radius: 0px 12px 12px 12px;
  }

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
</style>
