<template>
  <div class="navbar">
    <!-- #B4CDCD -->
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar"/>
    <breadcrumb class="breadcrumb-container"/>
    <span v-if="showFlag === 1" class="test">SMC业务处理系统测试环境</span>
    <span v-if="showFlag === 2" class="test">SMC业务处理系统UAT环境</span>
    <div class="header-content-container" style="color:white;">
      <div style="font-size: 14px; font-weight:bold">
        <span>
          <elbadge :num="num" @showDialog="msgShow" />
        </span>
        <!-- <el-button :class="(messageRemind && !messageVisible) ? 'message_remind_animation message_remind_style' : 'message_remind_common message_remind_style'" size="mini" icon="el-icon-message" type="danger" @click="changeMessageStatus()"/> -->
        <!-- <svg-icon icon-class="message_remind" style="font-size: 18px"/> -->
        <span :class="(messageRemind && !messageVisible) ? 'message_remind_animation message_remind_style' : 'message_remind_common message_remind_style'" @click="changeMessageStatus()">
          <svg-icon icon-class="message_remind"/>
        </span>
        <el-divider direction="vertical"/> 欢迎！
        <span v-if="!info.employeePositions || info.employeePositions.length < 2" class="el-dropdown-link" style="color:white">
          【{{ position.unitName }}】-
          {{ position.positionName }}
        </span>
        <el-dropdown v-else style="cursor: pointer" @command="handleCommand">
          <span class="el-dropdown-link" style="color:white">
            【{{ position.unitName }}】 -
            {{ position.positionName }}<i class="el-icon-arrow-down el-icon--right"/>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-for="(position, index) in info.employeePositions" :key="index" :command="position">
              【{{ position.unitName }}】 - {{ position.positionName }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-divider direction="vertical"/>
        {{ name }}
        <el-divider direction="vertical"/>
        {{ position.psnName }}
      </div>
    </div>
    <div class="avatar-container">
      <div>
        <el-button type="danger" size="mini" @click="logout">退出</el-button>
      </div>
    </div>
    <Notice :key="key"/>
  </div>
</template>

<script>
import Notice from '@/views/notice/notice'
import { mapGetters } from 'vuex'
import { mapState } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import Elbadge from '@/components/Elbadge'
import { setPosition } from '@/utils/auth'
// import { queryCustomerTransferNotificationNumber } from '@/api/notification'
// import Utils from '@/api/notification'
// 和公告相关
import { getLatestNotice } from '@/api/notice'
export default {
  inject: ['reload'],
  components: {
    Breadcrumb,
    Hamburger,
    Notice: Notice,
    Elbadge
  },
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
      // 公告有关
      NOTICE_ID: 'notice_id',
      key: 0,
      showFlag: 0
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device',
      'name',
      'info',
      'position'
    ]),
    ...mapState({
      messageVisible: state => state.settings.messageVisible,
      messageRemind: state => state.settings.messageRemind
    })
  },
  created() {
    this.key = 0
    console.log(this.key)
    // 加载该页面，去查看最新公告，存入cookie，隔一段时间去查找新的公告，如果两者不同，把新的公告存入cookie，并显示dialog，内容为新公告
    this.selectLatestNotice()
    // this.noticeTimer = setInterval(this.selectLatestNotice, 1000 * 60 * 5)
    var currentUrl = window.location.href
    console.log('主页url ==> ', currentUrl)
    if (currentUrl.includes('localhost') || currentUrl.includes('opstest.smcit.cn')) {
      this.showFlag = 1
    } else if (currentUrl.includes('opsuat.smcit.cn')) {
      this.showFlag = 2
    } else {
      this.showFlag = 0
    }
  },
  beforeDestroy() {
    clearInterval(this.noticeTimer)
  },

  methods: {
    selectLatestNotice() {
      // 获取最新的公告列表
      // 公告列表显示的两个条件，第一，公告没有过时，第二，公告未读
      getLatestNotice().then(data => {
        this.notice = data
        if (localStorage.getItem(this.NOTICE_ID) != null) {
          if (data.id === localStorage.getItem(this.NOTICE_ID)) {
            console.log('暂时没有发布新公告')
          } else {
            // 启动定时器，看是否是最新公告，如果是，就启动计时器
            this.keyTimer = setInterval(this.handleClickUpdate, 1000 * 60 * 1)
            localStorage.setItem(this.NOTICE_ID, data.id)
          }
        } else {
          localStorage.setItem(this.NOTICE_ID, data.id)
        }
        if (this.notice.editStatus === 'outdated') {
          this.noOutDated = false
        }
      })
    },
    handleClickUpdate() {
      this.key += 1
    },
    handleCommand(item) {
      this.$store.commit('SET_POSITION', item)
      setPosition(item)
      this.$store.dispatch('GetInfo').then(res => {})
      // this.$router.push('/')
      this.reload()
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    logout() {
      this.$confirm('确定要退出登录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // this.$store.dispatch('LogOut').then(() => {
        //   location.reload() // 为了重新实例化vue-router对象 避免bug
        // })
        var date = new Date()
        // date.setTime(date.getTime() - 10000)
        // document.cookie = 'cookiename' + '=v; expire=' + date.toGMTString() + '; path=/'
        var keys = document.cookie.match(/[^ =;]+(?=\=)/g)
        console.log('需要删除的cookie名字：', keys)
        if (keys) {
          for (var i = keys.length; i--;) {
            document.cookie = keys[i] + '=0; expire=' + date.toGMTString() + '; path=/'
          }
        }
        location.reload()
        // this.$router.push({ path: '/' })
      }).catch(() => {
      })
    },
    changeMessageStatus() {
      // 消息对话框打开状态
      this.$store.dispatch('settings/changeSetting', {
        key: 'messageVisible',
        value: true
      })
      // 消息闪烁提醒状态
      this.$store.dispatch('settings/changeSetting', {
        key: 'messageRemind',
        value: false
      })
    },
    msgShow(val) {
      console.log('elbadge 传给 navbar的 dialogtablevisible 值->', val)
      this.$emit('dialogShow', val)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
// 工具栏高度设置
$toolbar-line-height: 40px;
.navbar {
  height: $toolbar-line-height;
  overflow: hidden;
  position: relative;
  background:#0076BD;
  background-image: linear-gradient(to right,#0076BD, #9da5ca);
  // background-image: linear-gradient(to right,#0C7BB3, #6CC6CB);
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: $toolbar-line-height;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;
    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }
  .hamburger-container span{
    background-color: #fff;
  }

  .breadcrumb-container {
    float: left;
    line-height: $toolbar-line-height;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }

  .header-content-container {
    height: $toolbar-line-height;
    display: inline-block;
    line-height: $toolbar-line-height;
    position: absolute;
    // color: #606266;
    // color: white;
    right: 100px;
  }
  .avatar-container {
    height: $toolbar-line-height;
    line-height: $toolbar-line-height;
    display: inline-block;
    position: absolute;
    right: 20px;
    color: #606266;
    font-size: 15px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 12px;
        font-size: 12px;
      }
    }
  }
}
.message_remind_style {
  // font-size: 18px; padding: 0px 0px; background: transparent; border-color: transparent; border-radius: 2.6px;
  cursor: pointer;
  font-size: 18px
}
.message_remind_common {
  animation: unset;
}
.message_remind_animation {
  animation: flash 1.2s infinite ease-in-out;
}
.message_remind_animation:hover {
  animation: unset;
}
@keyframes flash{
  0%{
		opacity:1;				/*透明度为1*/
  }
  49%{
    opacity:1;
  }
  50%{
		opacity:0;
  }
  99%{
		opacity:0;
  }
  100%{
		opacity:1;
  }
}
.test {
  font-size: 25px;
  color: red;
  font-weight: bold;
  margin-left: 5%;
}
</style>
