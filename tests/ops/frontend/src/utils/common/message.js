/**
 * 消息提示时间
 */
const timeLimit = 3600
export default {
  install(Vue, options) {
    Vue.prototype.smcSuccessMsg = function smcSuccessMsg(message) {
      this.$notify({
        title: '成功',
        message: (message === null || message === undefined || message === '') ? '操作成功！' : message,
        type: 'success',
        duration: timeLimit
      })
    }

    Vue.prototype.smcErrorMsg = function smcErrorMsg(message) {
      this.$notify({
        title: '遇到问题',
        message: (message === null || message === undefined || message === '') ? '遇到问题！' : message,
        type: 'error',
        duration: timeLimit
      })
    }

    Vue.prototype.smcWarningMsg = function smcWarningMsg(message) {
      this.$notify({
        title: '警告',
        message: (message === null || message === undefined || message === '') ? '操作成功！' : message,
        type: 'warning',
        duration: timeLimit
      })
    }

    Vue.prototype.smcInfoMsg = function smcInfoMsg(message) {
      this.$notify({
        title: '信息',
        message: (message === null || message === undefined || message === '') ? '操作成功！' : message,
        type: 'info',
        duration: timeLimit
      })
    }

    /**
     * HTML格式支持 消息
     * @param {*} message 消息
     * @param {*} type 类型 success error info warning
     */
    Vue.prototype.smcHtmlMsg = function smcHtmlMsg(message, type) {
      this.$notify({
        title: '提示',
        dangerouslyUseHTMLString: true,
        message: message,
        type: type || 'error',
        duration: timeLimit
      })
    }
  }
}

