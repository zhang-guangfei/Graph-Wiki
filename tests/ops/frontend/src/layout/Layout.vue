<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <sidebar class="sidebar-container"/>
    <div :class="{hasTagsView:needTagsView}" class="main-container">
      <div :class="{'fixed-header':fixedHeader}">
        <navbar :num="num" @dialogShow="isShowDialog"/>
        <tags-view v-if="needTagsView" />
      </div>
      <app-main />
      <right-panel v-if="showSettings">
        <settings />
      </right-panel>
    </div>
    <message-management :msg-data="msgData" :dialogtablevisible="dialogtablevisible" @changeDialogFlag="changeDiLogFlag"/>
    <message-ws :message-visible="messageVisible" @closeMessage="closeMessageChild"/>
  </div>
</template>

<script>
// import SockJS from 'sockjs-client'
// import Stomp from 'stompjs'
import MessageWs from '@/components/MessageWs'
import RightPanel from '@/components/RightPanel'
import { AppMain, Navbar, Settings, Sidebar, TagsView } from './components'
import ResizeMixin from './mixin/ResizeHandler'
// import { getToken } from '@/utils/auth'
import { mapGetters, mapState } from 'vuex'
import MessageManagement from '@/components/MessageManagement'
export default {
  name: 'Layout',
  components: {
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView,
    MessageWs,
    MessageManagement
  },
  mixins: [ResizeMixin],
  data() {
    return {
      msgData: [
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'success',
          msgContent: '测试消息推送喔...'
        },
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'error',
          msgContent: '测试消息推送喔...'
        },
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'success',
          msgContent: '测试消息推送喔...'
        },
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'success',
          msgContent: '测试消息推送喔...'
        },
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'success',
          msgContent: '测试消息推送喔...'
        },
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'success',
          msgContent: '测试消息推送喔...'
        },
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'success',
          msgContent: '测试消息推送喔...'
        },
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'success',
          msgContent: '测试消息推送喔...'
        },
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'success',
          msgContent: '测试消息推送喔...'
        },
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'error',
          msgContent: '测试消息推送喔...'
        },
        {
          date: '2016-05-03',
          msgType: '测试',
          status: 'success',
          msgContent: '测试消息推送喔...'
        }
      ],
      dialogtablevisible: false,
      num: 0,
      stompClient: null,
      // cmes: '',
      mes: '',
      socket: null // websocket
      // timer: null // 定时器名称
    }
  },
  computed: {
    ...mapGetters(['msgDataList']),
    ...mapState({
      sidebar: state => state.app.sidebar,
      device: state => state.app.device,
      showSettings: state => state.settings.showSettings,
      needTagsView: state => state.settings.tagsView,
      fixedHeader: state => state.settings.fixedHeader,
      messageVisible: state => state.settings.messageVisible
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    }
  },
  mounted() {
    this.num = this.msgData.length
    this.initWebSocket()
    // this.timer = setInterval(() => {
    //   // 要执行的函数
    //   this.init()
    // }, 1000)
  },
  destroyed() {
    // 离开页面时关闭websocket连接
    if (this.stompClient !== null) {
      this.stompClient.disconnect()
    }
  },
  methods: {
    // init() {
    //   this.$store.dispatch('saveMsgs', this.msgData).then(() => {
    //     console.log('res ->', this.msgDataList)
    //     for (var i = 0; i < this.msgDataList.length; i++) {
    //       if (new Date() > this.msgDataList[i].expiration) {
    //         this.msgDataList.splice(i, 1)
    //       }
    //     }
    //   })
    // },
    initWebSocket() {
      // const _that = this
      // console.log(getToken())
      // // 连接 socket
      // this.socket = new SockJS(process.env.OPS_API_copy + '/websocket')
      // const stompClient = Stomp.over(this.socket)
      // console.log('stompClient', stompClient)
      // stompClient.connect({ 'token': getToken() }, function(frame) {
      //   console.log('frame ->', frame)
      //   stompClient.subscribe('/topic', function(message) {
      //     // 接收服务器返回的数据
      //     const resData = JSON.parse(message.body)
      //     console.log('websocket 接收到的数据', resData)
      //   })
      // })
      // this.stompClient = stompClient
    },
    send() {
      // 这是发送消息
      this.stompClient.send('/mes', {}, '客户端发来消息')
    },
    handleClickOutside() {
      this.$store.dispatch('CloseSideBar', { withoutAnimation: false })
    },
    closeMessageChild() {
      this.$store.dispatch('settings/changeSetting', {
        key: 'messageVisible',
        value: false
      })
    },
    isShowDialog(val) {
      console.log('navbar 传给 layout的 dialogtablevisible 值 ->', val)
      this.dialogtablevisible = val
    },
    changeDiLogFlag(val) {
      console.log('点击确定之后的 val', val)
      this.dialogtablevisible = val
      this.num = 0
    }
    // beforeDestroy() {
    //   clearInterval(this.timer)
    //   this.timer = null
    //   console.log(this.timer) // 输出为: null,但是任务依然在继续运行
    // }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";
  @import "~@/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;

    &.mobile.openSidebar {
      position: fixed;
      top: 0;
    }
  }
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px)
  }

  .mobile .fixed-header {
    width: 100%;
  }
</style>
