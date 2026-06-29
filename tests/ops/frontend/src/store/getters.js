const getters = {
  sidebar: state => state.app.sidebar,
  size: state => state.app.size,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  introduction: state => state.user.introduction,
  roles: state => state.user.roles,
  permission_routers: state => state.permission.routers,
  errorLogs: state => state.errorLog.logs,
  info: state => state.user.info,
  position: state => state.user.position,
  positions: state => state.user.positions,
  menus: state => state.user.menus,
  btns: state => state.user.btns,
  addRouters: state => state.permission.addRouters,
  refreshToken: state => state.user.refresh_token,
  msgDataList: state => state.msgManagement.msgList
}
export default getters
