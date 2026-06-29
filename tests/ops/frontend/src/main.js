import Vue from 'vue'
import 'xe-utils'
import VXETable from 'vxe-table'
import 'vxe-table/lib/style.css'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import * as echarts from 'echarts'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'
import 'babel-polyfill'
import '@/icons' // icon
import '@/permission' // permission control
import * as filters from './filters'
import permission from '@/directive/permission/index.js' // 权限判断指令
import postil from '@/directive/postil/index.js'
import './utils/directives.js'
import Print from 'vue-print-nb'
import htmlToPdf from '@/utils/htmlToPdf'
import VXETablePluginElement from 'vxe-table-plugin-element'
import 'vxe-table-plugin-element/dist/style.css'
import VXETablePluginExportXLSX from 'vxe-table-plugin-export-xlsx'
import ExcelJS from 'exceljs'
import '@/assets/icon/iconfont.css'
import message from '@/utils/common/message'
import dayjs from 'dayjs'
import { numberToCurrencyNo, numberToFixedTwo, numberToFixedFour } from '@/utils/numberToCurrency'
import './utils/vxeTableFormatters.js'
Vue.filter('numberToCurrency', numberToCurrencyNo)

Vue.filter('numberToFixedTwo', numberToFixedTwo)

Vue.filter('numberToFixedFour', numberToFixedFour)
// import 'lib-flexible'
Vue.prototype.dayjs = dayjs
Vue.use(message)

Vue.use(htmlToPdf)
Vue.use(VXETable)
// VXETable.setup({
//   version: 0,
//   zIndex: 3000, // 想多高就设置多高
//   table: {
//     autoResize: true
//   }
// })
VXETable.use(VXETablePluginElement)
VXETable.use(VXETablePluginExportXLSX, {
  ExcelJS
})
Vue.use(Print)
Vue.directive('permission', permission)
Vue.directive('postil', postil)

/* import htmlToPdf from '@/utils/htmlToPdf'

Vue.use(htmlToPdf) */

// 给 vue 实例挂载全局窗口对象，属性名称随意定义，例如：$XModal
Vue.prototype.$VXETable = VXETable
Vue.prototype.$XModal = VXETable.modal
Vue.prototype.$echarts = echarts
Vue.use(ElementUI)

Vue.config.productionTip = false
// register global utility filters.
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
