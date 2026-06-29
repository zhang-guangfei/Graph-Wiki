<template>
  <div class="chat-div-body">
    <el-card>
      <el-form :inline="true" label-position="left">
        <el-form-item style="margin-bottom:0px; margin-left:5px;" label="订单号: ">
          <el-tag>{{ chatOrderNo }}</el-tag>
        </el-form-item>
        <el-form-item style="margin-bottom:0px; margin-left:5px;" label="供应商ID:">
          <el-tag>{{ supplierId }}</el-tag>
        </el-form-item>
        <!-- <el-form-item style="margin-bottom:0px;" label="用户名:">
          <el-tag>{{ supplierUser.userName }}</el-tag>
        </el-form-item> -->
        <el-form-item style="margin-bottom:0px; margin-left:5px;" label="供应商:">
          <el-tag>{{ supplierName }}</el-tag>
        </el-form-item>
      </el-form>
      <hr size="5" color="#0066cc">
      <el-row :gutter="20">
        <el-col :span="18"><div>
          <el-table
            ref="chatContainer"
            :data="chatData"
            height="400"
            style="width: 100%">
            <el-table-column :label="chatPerson">
              <template slot-scope="scope">
                <div>
                  <span v-if="scope.row.otherMsg !== '' && scope.row.otherMsg !== null ">
                    <el-row :gutter="20">
                      <!-- <el-col :span="2"><svg-icon icon-class="icon-tx-w" /></el-col> -->
                      <div style="font-size: 10px; margin-left: 23px">{{ scope.row.fromUser }}</div>
                      <!-- <el-col :span="12"><div style="color: #C0C4CC; font-size: 10px">{{ scope.row.sendDate | parseTime('{m}-{d} {h}:{i}') }}</div></el-col> -->
                    </el-row>
                    <el-row :gutter="20">
                      <el-col :span="1" style="padding-left: 0px;"><svg-icon icon-class="icon-tx-w"/></el-col>
                      <el-col :span="21"><div class="chat-left-box">{{ scope.row.otherMsg }}</div></el-col>
                    </el-row>
                  </span>
                </div>
              </template>
            </el-table-column>
            <el-table-column width="10"/>
            <el-table-column>
              <template slot-scope="scope">
                <div>
                  <span v-if="scope.row.myMsg !== '' && scope.row.myMsg !== null ">
                    <el-row :gutter="20">
                      <!-- <el-col :span="2"><svg-icon icon-class="icon-tx-m" /></el-col> -->
                      <div style="float: right;font-size: 10px;margin-right: 30px">{{ scope.row.fromUser }}</div>
                      <!-- <el-col :span="12"><div style="color: #C0C4CC; font-size: 10px">{{ scope.row.sendDate | parseTime('{m}-{d} {h}:{i}') }}</div></el-col> -->
                    </el-row>
                    <el-row :gutter="20" style="margin-right: -30px;">
                      <el-col :span="21"><div class="chat-right-box" style="float: right" type="success">{{ scope.row.myMsg }}</div></el-col>
                      <el-col :span="1" style="padding-left: 0px;"><svg-icon icon-class="icon-tx-m"/></el-col>
                    </el-row>
                  </span>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <hr size="5" color="#0066cc">
          <div>
            <el-input
              :autosize="{ minRows: 2, maxRows: 4}"
              v-model="messageText"
              type="textarea"
              placeholder="请输入内容"
              @keyup.enter.native="sendChatMsg()"
            />
            <el-button class="send-btn-position" type="primary" @click="sendChatMsg()">发送</el-button>
          </div>
        </div></el-col>
        <el-col :span="6">
          <!-- <div>
            <el-card>
              <el-form label-position="left" class="chat-form-card">
                <el-form-item style="margin-bottom:0px;" label="订单号: ">
                  <el-tag>{{ chatOrderNo }}</el-tag>
                </el-form-item>
                <el-form-item style="margin-bottom:0px;" label="供应商ID:">
                  <el-tag>{{ supplierId }}</el-tag>
                </el-form-item>
                <el-form-item style="margin-bottom:0px;" label="供应商:">
                  <div class="chat-left-box">{{ supplierName }}</div>
                </el-form-item>
              </el-form>
          </el-card></div> -->
          <div style="height: 480px; overflow-y: scroll;">
            <div>
              <el-tag
                v-for="tag in dynamicTags"
                :key="tag"
                :disable-transitions="false"
                :type="tag === chatOrderNo ? 'primary' : 'info'"
                closable
                style="margin-top: 5px; display: list-item; width:145px; cursor: pointer"
                @click.native="changeOrderNo(tag)"
                @close="handleClose(tag)">
                {{ tag }}
              </el-tag>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import { getSupplerUserBySupplierId } from '@/api/user'
import { getChatRecord, getSocketURL } from '@/api/chat'
// import { getToken } from '@/utils/auth'
// import { getOrderDetails } from '@/api/orderReceive'
export default {
  name: 'Chat',
  props: {
    chatVisible: {
      type: Boolean,
      default: true
    },
    chatOrderNo: {
      type: String,
      default: ''
    },
    supplierId: {
      type: String,
      default: ''
    },
    supplierName: {
      type: String,
      default: ''
    },
    dynamicTags: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      // 选中的
      chatFund: {},
      chatData: [],
      supplierUser: {},
      // 聊天对象
      chatPerson: 'SMC',
      websock: null,
      messageText: '',
      userList: []
      // dynamicTags: []
    }
  },
  watch: {
    chatVisible() {
      if (this.chatVisible === true) {
        this.getChatRecords()
      }
    },
    chatOrderNo() {
      // this.dynamicTags.unshift(this.chatOrderNo)
      // this.dynamicTags = this.uniqArray(this.dynamicTags)
      this.getChatRecords()
    },
    chatData() {
      this.$nextTick(() => {
        this.$refs.chatContainer.bodyWrapper.scrollTop = this.$refs.chatContainer.bodyWrapper.scrollHeight
      })
    }
  },
  created() {
    this.initWebSocket()
    this.getSupplerUser()
    this.getChatRecords()
  },
  methods: {
    // 初始化weosocket
    initWebSocket() {
      const wsuri = getSocketURL() + this.$store.getters.info.username
      this.websock = new WebSocket(wsuri)
      this.websock.onmessage = this.websocketonmessage
      this.websock.onopen = this.websocketonopen
      this.websock.onerror = this.websocketonerror
      this.websock.onclose = this.websocketclose
    },
    getChatRecords() {
      var self = this
      // var params = { orderNo: this.chatFund.orderno }
      self.chatData = []
      var params = { orderNo: this.chatOrderNo }
      getChatRecord(params).then(res => {
        var array = res.data
        array.forEach(element => {
          if (element.fromUser === this.$store.getters.info.username) {
            var chatmsg = { myMsg: element.textMessage, otherMsg: '', sendDate: element.sendDate, fromUser: element.fromUser }
            self.chatData.push(chatmsg)
          } else {
            var chatmsg1 = { myMsg: '', otherMsg: element.textMessage, sendDate: element.sendDate, fromUser: element.fromUser }
            self.chatData.push(chatmsg1)
          }
        })
      })
        .catch(err => {
          console.log(err)// 错误信息
        })
    },
    // 连接建立失败重连
    websocketonerror() {
      this.initWebSocket()
    },
    // 点击订单号标签 向父组件通信
    changeOrderNo(value) {
      this.$emit('childByValue', value)
    },
    // 向父组件传递 未读消息数相关
    msgNumWithFather(value) {
      this.$emit('msgOrderNo', value)
    },
    // 关闭标签
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
      if (this.dynamicTags.length > 0) {
        this.changeOrderNo(this.dynamicTags[0])
      }
      if (this.dynamicTags.length === 0) {
        this.$emit('visibleStatus', false)
      }
    },
    // 数据接收
    websocketonmessage(e) {
      const redata = JSON.parse(e.data)
      // 有人上线
      if (redata.messageType === 1 && redata.username !== this.$store.getters.info.username) {
        var list = { username: redata.username }
        this.userList.push(list)
      // 有人下线
      } else if (redata.messageType === 2) {
        this.userList = []
        var users = redata.onlineUsers
        users.forEach(element => {
          var list = { username: element }
          this.userList.push(list)
        })
      // 告诉自己上线用户人数
      } else if (redata.messageType === 3) {
        this.userList = []
        var userdatas = redata.onlineUsers
        userdatas.forEach(element => {
          if (!(element === this.$store.getters.info.username)) {
            var list = { username: element }
            this.userList.push(list)
          }
        })
      } else if (redata.textMessage) {
        this.msgNumWithFather(redata.orderNo)
        var data = { myMsg: '', otherMsg: redata.textMessage, sendDate: new Date(), fromUser: redata.fromusername }
        this.chatPerson = redata.fromusername
        this.chatData.push(data)
      }
    },
    // 数据发送
    // websocketsend(Data) {
    //   var message = {
    //     message: 'summerTest666',
    //     username: 'summer',
    //     to: 'All'
    //   }
    //   this.websock.send(JSON.stringify(message))
    // },
    // 关闭
    websocketclose(e) {
      console.log('断开连接', e)
    },
    sendChatMsg() {
      if (this.messageText === '' || this.messageText === null) {
        this.$message({
          type: 'error',
          message: '消息内容不可以为空哦!'
        })
      } else {
        var message = {
          message: this.messageText,
          username: this.$store.getters.info.username,
          to: this.supplierUser.userName,
          orderNo: this.chatOrderNo
        }
        this.websock.send(JSON.stringify(message))
        var data2 = { myMsg: this.messageText, otherMsg: '', sendDate: new Date(), fromUser: this.$store.getters.info.username }
        this.chatData.push(data2)
        // 清空输入框
        this.messageText = ''
      }
    },
    // supplierId
    getSupplerUser() {
      var self = this
      getSupplerUserBySupplierId(self.supplierId).then(data => {
        self.supplierUser = data
      })
        .catch(err => {
          console.log(err)// 错误信息
        })
    }
  }
}
</script>

<style scoped>
/* .el-dialog.el-dialog__body {
  padding-top: 10px;
  padding-bottom: 30px;
} */
/* .send-btn-position {
  position: relative;
  left: 80vmin;
  top: 1vmin;
} */
.el-textarea {
  /* min-height: 100px; */
  width: 320px;
}
</style>
