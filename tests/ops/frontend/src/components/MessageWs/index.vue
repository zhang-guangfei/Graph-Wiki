<template>
  <el-dialog
    v-el-drag-dialog
    :visible.sync="messageVisible"
    :before-close="onClose"
    :title="messageTitle"
    class="msg-dialog public-dialog"
    width="800px">
    <div>
      <el-row>
        <el-col :span="7">
          <el-scrollbar style="background: #EDEAE8">
            <div style="margin: 5px 0px 0px 0px; height: 18px">
              <el-popover
                placement="right"
                width="300"
                trigger="click">
                <div style="display: inline">
                  <el-input v-model="addUserId" size="mini" placeholder="请输入要添加的用户Id" style="width: 200px"/>
                  <el-button size="mini" @click="addUserClick()">添加</el-button>
                </div>
                <i slot="reference" class="el-icon-circle-plus" style="width: 15px; height: 15px; margin-left: 10px; cursor: pointer"/>
              </el-popover>
            </div>
            <div style="height: 2px; background: white"/>
            <div style="height: 550px">
              <contacter-item
                v-for="(item, index) in contacterItems"
                :key="index"
                :key-no="index"
                :contacter="item"
                :is-selected="item.isSelected"
                @changeSelected="clickItem"
              />
            </div>
          </el-scrollbar>
        </el-col>
        <el-col :span="17">
          <div class="message_div">
            <el-scrollbar ref="scroll_el" style="height: 437px">
              <chat-item
                v-for="(item, index) in chatItems"
                :key="index"
                :type="item.type"
                :message-text="item.messageText"
                :timestamp="item.timestamp"
                :from-user="item.fromUser"
              />
            </el-scrollbar>
          </div>
          <!-- <hr style="background-color: #337AB7"> -->
          <div style="margin: 5px 0px 0px 0px;">
            <el-popover
              placement="right"
              width="500"
              trigger="click">
              <!-- <div style="text-align: center; width: 100%">
                <el-tag size="small" v-text="messageRecordItems.length > 0 ? '消息记录' : '暂无数据'"/>
              </div> -->
              <el-divider>
                <el-tag size="small" class="el-icon-chat-line-round">
                  <span v-text="messageRecordItems.length > 0 ? ' 消息记录' : ' 暂无数据'"/>
                </el-tag>
              </el-divider>
              <el-scrollbar ref="scroll_el_record" style="height: 480px">
                <message-record-item
                  v-for="(item, index) in messageRecordItems"
                  :key="index"
                  :message-text="item.messageText"
                  :timestamp="item.timestamp"
                  :from-user="item.fromUser"
                />
              </el-scrollbar>
              <i slot="reference" class="el-icon-chat-dot-round" style="width: 15px; height: 15px; margin-left: 10px; cursor: pointer" @click="getMessageRecords()"/>
            </el-popover>
          </div>
          <div>
            <el-row :gutter="10">
              <el-col :span="20">
                <el-input
                  :autosize="{ minRows: 5, maxRows: 5}"
                  v-model="contentText"
                  type="textarea"
                  resize="none"
                  placeholder="请输入内容"
                  @keyup.enter.native="sendChatMsg()"
                />
              </el-col>
              <el-col :span="4">
                <div style="height: 100%; width: 100%">
                  <el-button float="right" class="send-btn-position" type="primary" size="small" @click="sendChatMsg()">发送</el-button>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>
    </div>
  </el-dialog>
</template>

<script>
// 可拖拽对话框
import elDragDialog from '@/directive/el-dragDialog'
// import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import ChatItem from './chatItem'
import ContacterItem from './contacterItem'
import MessageRecordItem from './messageRecordItem'
import { findMessageRecords, readMessage, findUserRelationByUserId } from '@/api/websocketMessage'
import { getToken } from '@/utils/auth'
// import { parseTime } from '@/utils/index'
import { stringToDate } from '@/utils/dateHandle'

export default {
  name: 'MessageWs',
  components: {
    'chat-item': ChatItem,
    'contacter-item': ContacterItem,
    'message-record-item': MessageRecordItem
  },
  directives: { elDragDialog },
  props: {
    messageVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      socket: null,
      stompClient: null,
      count: 0,
      userId: this.$store.getters.position.psnsmcId, // 当前用户ID
      username: this.$store.getters.position.psnName, // 当前用户昵称
      avatar: this.$store.getters.avatar, // 当前用户头像
      // input输入的值
      contentText: '',
      chatItems: [],
      contacterItems: [
        { userId: 'SMC', userName: 'SMC', nickName: 'SMC', isSelected: false, messageList: [], unreadNum: 0 },
        { userId: 'B82443', userName: '杨玉帝', nickName: '玉帝', isSelected: false, messageList: [], unreadNum: 0 },
        { userId: 'B82561', userName: '孙锐', nickName: 'Summer、Rain', isSelected: false, messageList: [], unreadNum: 0 },
        { userId: 'B82559', userName: '张沈博', nickName: '把我当成一棵树。', isSelected: false, messageList: [], unreadNum: 0 },
        { userId: 'B74718', userName: '任红洋', nickName: '任红洋', isSelected: false, messageList: [], unreadNum: 0 },
        { userId: 'C02038', userName: '马红祺', nickName: '℡安若惜ヽPisces ゛', isSelected: false, messageList: [], unreadNum: 0 },
        { userId: 'C02047', userName: '郭夏林', nickName: 'ᴇʀᴠɪɴ', isSelected: false, messageList: [], unreadNum: 0 }
      ],
      messageTitle: 'MessageWs',
      currentContacter: null,
      currentUserId: this.$store.getters.position.psnsmcId,
      addUserId: '',
      messageRecordItems: []
    }
  },
  watch: {
    // 自动滚动到底部
    chatItems() {
      this.$nextTick(() => {
        this.scrollBottm()
      })
    }
  },
  mounted() {
    // this.initWebSocket()
  },
  created() {
    // 初始化联系人列表
    // this.addUserList({ userId: this.userId })
    // this.initUserRelationByUserId()
  },
  destroyed() {
    // 离开页面时关闭websocket连接
    if (this.stompClient !== null) {
      this.stompClient.disconnect()
    }
  },
  methods: {
    onClose() {
      this.$emit('closeMessage')
    },
    clickItem(keyNo) {
      // 选中状态颜色改变
      this.contacterItems.forEach(element => {
        element.isSelected = false
      })
      const obj = Object.assign(this.contacterItems[keyNo])
      obj.isSelected = true
      this.contacterItems.splice(keyNo, 1, obj)
      // 切换Title
      if (obj.userName === null || obj.userName === '') {
        this.messageTitle = obj.nickName
      } else {
        this.messageTitle = obj.userName
      }
      this.currentContacter = obj
      this.chatItems = obj.messageList
      if (this.currentContacter.unreadNum > 0) {
        readMessage(this.userId, this.currentContacter.userId).then((data) => {
          obj.unreadNum = 0
        })
      }
      obj.unreadNum = 0
    },
    // 发送聊天信息
    sendChatMsg() {
      const _this = this
      if (!_this.contentText) {
        return
      }
      const params = {
        messageText: _this.contentText,
        fromUser: _this.$store.getters.info.userId,
        toUser: _this.currentContacter.userId
      }
      this.stompClient.send('/message', {}, JSON.stringify(params)) // 调用WebSocket send()发送信息的方法
      _this.contentText = ''
    },
    initWebSocket() {
      const _that = this
      // this.socket = new SockJS(process.env.WEBSOCKET_API + '/websocketApi')
      const stompClient = Stomp.over(this.socket)
      // console.log({ 'token': getToken() })
      // 关闭stomp的调试信息
      // stompClient.debug = null
      stompClient.connect({ 'token': getToken() }, function(frame) {
        stompClient.subscribe('/user/' + _that.currentUserId + '/queue/chat', function(message) {
          // 接收服务器返回的数据
          const resData = JSON.parse(message.body)
          const messageObj = { messageType: resData.messageType, messageText: resData.messageText, fromUser: resData.fromUser, timestamp: stringToDate(resData.messageTime) }
          _that.addUserList({ userId: resData.fromUser })
          _that.saveMessage(messageObj)
        })
        stompClient.subscribe('/topic', function(message) {
          // 接收服务器返回的数据
          const resData = JSON.parse(message.body)
          const messageObj = { messageType: resData.messageType, messageText: resData.messageText, fromUser: resData.fromUser, timestamp: stringToDate(resData.messageTime) }
          _that.addUserList({ userId: resData.fromUser })
          _that.saveMessage(messageObj)
        })
      })
      this.stompClient = stompClient
    },
    initUserRelationByUserId() {
      findUserRelationByUserId(this.userId).then((data) => {
        if (Array.isArray(data)) {
          this.contacterItems = data.map(item => { return { userId: item.relationUserId, userName: item.relationUserId, nickName: item.relationUserId, isSelected: false, messageList: [], unreadNum: 0 } })
          if (Array.isArray(this.contacterItems) && this.contacterItems.length > 0) {
            this.initMessageRecords()
            // 默认选中
            if (this.contacterItems.length > 0) {
              this.clickItem(0)
            }
          }
        }
      })
    },
    // 滚动条到底部
    scrollBottm() {
      if (this.$refs.scroll_el !== undefined) {
        this.$refs.scroll_el.wrap.scrollTop = this.$refs.scroll_el.wrap.children[0].offsetHeight
      }
    },
    saveMessage(messageObj) {
      // 自己的消息
      if (messageObj.fromUser === this.currentUserId) {
        this.chatItems.push(messageObj)
      // 别人发来的消息
      } else {
        this.contacterItems.forEach(element => {
          if (element.userId === messageObj.fromUser) {
            // 不是当前聊天人未读消息数加一
            element.messageList.push(messageObj)
            if (element.userId !== this.currentContacter.userId) {
              element.unreadNum = element.unreadNum + 1
            }
            return
          }
        })
        // 如果消息框没打开 添加提醒闪动
        if (!this.messageVisible) {
          this.$store.dispatch('settings/changeSetting', {
            key: 'messageRemind',
            value: true
          })
        }
      }
    },
    initMessageRecords() {
      const array = this.contacterItems
      for (let index = 0; index < array.length; index++) {
        const element = array[index]
        var params = { userA: this.userId, userB: element.userId }
        findMessageRecords(params).then((data) => {
          const records = data.content.messageWsList
          const unReadNum = data.content.unReadNum
          if (Number.isInteger(unReadNum) && unReadNum > 0) {
            element.unreadNum = unReadNum
            // 如果消息框没打开 添加提醒闪动
            if (!this.messageVisible) {
              this.$store.dispatch('settings/changeSetting', {
                key: 'messageRemind',
                value: true
              })
            }
          }
          if (Array.isArray(records)) {
            records.forEach(msg => {
              var messageObj = { messageType: msg.messageType, messageText: msg.messageText, fromUser: msg.fromUser, timestamp: stringToDate(msg.messageTime) }
              element.messageList.push(messageObj)
            })
          }
        }).catch((e) => {
          if (index === array.length - 1) {
            this.smcErrorMsg(e)
          }
        })
      }
    },
    addUserClick() {
      this.addUserList({ userId: this.addUserId })
      this.addUserId = ''
    },
    addUserList(userInfo) {
      if (!userInfo || userInfo.userId === '') {
        this.smcWarningMsg('请请输入用户')
        return
      }
      const res = this.contacterItems.find(v => v.userId === userInfo.userId)
      if (!res && userInfo.userId) {
        this.contacterItems.push({ userId: userInfo.userId, userName: userInfo.userName ? userInfo.userName : userInfo.userId, nickName: userInfo.nickName ? userInfo.nickName : userInfo.userId, isSelected: false, messageList: [], unreadNum: 0 })
        // addUserRelation({ userId: this.userId, relationUserId: userInfo.userId }).then((data) => {})
      }
    },
    getMessageRecords() {
      this.messageRecordItems = this.chatItems
      this.$refs.scroll_el_record.wrap.scrollTop = this.$refs.scroll_el_record.children[0].offsetHeight
    }
  }
}
</script>
<style>
.el-popover .el-scrollbar .el-scrollbar__wrap {
  overflow-x: hidden;
}
.message_div .el-scrollbar .el-scrollbar__wrap{
  overflow-x: hidden;
}
</style>
<style lang="scss" scoped>
.send-btn-position {
  margin: 0 5px;
}
.message_div {
  background: #F5F5F5;
}
</style>
