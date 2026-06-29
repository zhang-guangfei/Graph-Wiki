const msgMange = {
  // 定义消息集合
  state: {
    msgList: []
    // msgList: [],
    //   expiration: new Date().getTime() + (1000 * 60) // 设置一分钟到期
  },
  // 在mutations中 写一个方法来赋值msgs (必须写在mutation中，因为这是改变store的唯一方法)
  mutations: {
    SET_MSGS: (state, obj) => {
      state.msgList.push(obj)
    }
  },
  // 让外部进行调用 对msgs进行修改
  actions: {
    saveMsgs({ commit }, msgs) {
      console.log('actions msg', msgs)
      for (var i = 0; i < msgs.length; i++) {
        var obj = {
          dataList: [],
          expiration: ''
        }
        obj.dataList = msgs[i]
        obj.expiration = new Date().getTime() + (1000 * 60)
        commit('SET_MSGS', obj)
      }
    }
  }

}
export default msgMange
