import postil from './postil'

const install = function(Vue) {
  Vue.directive('postil', postil)
}

if (window.Vue) {
  window['postil'] = postil
  Vue.use(install); // eslint-disable-line
}

postil.install = install
export default postil
