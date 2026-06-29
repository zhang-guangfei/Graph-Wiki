<template>
  <div ref="content" class="containers" >
    <div ref="canvas" class="canvas"/>
    <div v-show="rightVisible" id="js-properties-panel" class="panel"/>
    <div v-show="showButtonVisible" class="toolbar" align="center">
      <el-button size="small" type="primary" plain @click="handleUndo">后退</el-button>
      <el-button size="small" type="primary" plain @click="handleRedo">前进</el-button>
      <el-button size="small" type="primary" plain @click="importBPMNFileDialogVisible = true" >导入BPMN</el-button>
      <el-button size="small" type="primary" plain @click="deployEvent">部署</el-button>
      <el-button size="small" type="primary" plain @click="exportBpmn">导出BPMN</el-button>
      <el-button size="small" type="primary" plain @click="exportImg">导出图片</el-button>
    </div>
    <!-- <div>
      <el-container style="height: 900px">
        <el-aside width="80%" style="border: 1px solid #DCDFE6" >
          <div ref="canvas" style="width: 100%;height: 100%">
            <div v-show="showButtonVisible" class="toolbar" align="center">
              <el-button size="small" type="primary" plain @click="handleUndo">后退</el-button>
              <el-button size="small" type="primary" plain @click="handleRedo">前进</el-button>
              <el-button size="small" type="primary" plain @click="importBPMNFileDialogVisible = true" >导入BPMN</el-button>
              <el-button size="small" type="primary" plain @click="deployEvent">部署</el-button>
              <el-button size="small" type="primary" plain @click="exportBpmn">导出BPMN</el-button>
              <el-button size="small" type="primary" plain @click="exportImg">导出图片</el-button>
            </div>
          </div>
        </el-aside>
        <el-main style="border: 1px solid #DCDFE6;background-color:#FAFAFA">
          <el-form v-show="rightVisible" label-width="auto" size="mini" label-position="top">
            <component :is= "propsComponent" :element= "element" :key= "key"/>
          </el-form>
        </el-main>
      </el-container>
    </div> -->
    <el-dialog
      :visible.sync="importBPMNFileDialogVisible"
      title="上传BPMN文件"
      width="30%"
      center>
      <!-- <span>需要注意的是内容是默认不居中的</span> -->
      <el-upload ref="upload" :multiple="true" :loading="loading" :auto-upload="false" :on-change="handleChange" :file-list="fileList" :http-request="uploadFile" accept=".bpmn" action="String" >
        <el-button slot="trigger" icon="el-icon-plus" size="medium" type="primary">添加附件</el-button>
        <div slot="tip" class="el-upload__tip">
          仅支持上传.bpmn文件格式
        </div>
      </el-upload>
      <span slot="footer" class="dialog-footer">
        <el-button @click="importBPMNFileDialogVisible = false">取 消</el-button>
        <el-button :disabled="isButtonDisabled" type="primary" @click="uploadFile()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import BpmnModeler from 'bpmn-js/lib/Modeler'

// import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda'
import propertiesPanelModule from 'bpmn-js-properties-panel'
// import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda'
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/bpmn'
// import customControlsModule from '@/api/bpmnjs/customControls'
import customTranslate from '@/api/bpmnjs/customTranslate/customTranslate'
import bpmnHelper from '@/api/bpmnjs/helper/bpmnHelper'
import BpmData from '@/api/bpmnjs/bpmnData'
import CommonProps from '@/components/Activiti/commonProps'
import ProcessProps from '@/components/Activiti/processProps'
import StartEventProps from '@/components/Activiti/startEventProps'
import EndEventProps from '@/components/Activiti/endEventProps'
import IntermediateThrowEventProps from '@/components/Activiti/intermediateThrowEventProps'
import ExclusiveGatewayProps from '@/components/Activiti/exclusiveGatewayProps'
import ParallelGatewayProps from '@/components/Activiti/parallelGatewayProps'
import InclusiveGatewayProps from '@/components/Activiti/inclusiveGatewayProps'
import UserTaskProps from '@/components/Activiti/userTaskProps'
import SequenceFlowProps from '@/components/Activiti/sequenceFlowProps'
import CallActivityProps from '@/components/Activiti/callActivityProps'

// 整合activiti
import activitiModdleDescriptor from '@/api/bpmnjs/activiti.json'
// 图片转换
import canvg from 'canvg'
// 部署,获取xml数据
import { uploadFilesDirect } from '@/api/activiti'

export default {
  name: 'ActivitiDrawModeler',
  components: {
    CommonProps,
    ProcessProps,
    StartEventProps,
    EndEventProps,
    IntermediateThrowEventProps,
    ExclusiveGatewayProps,
    ParallelGatewayProps,
    InclusiveGatewayProps,
    UserTaskProps,
    SequenceFlowProps,
    CallActivityProps
  },
  // props:["params"],
  props: {
    params: {
      type: Function,
      default: function() {
        return Function
      }
    }
  },
  provide: function() {
    return {
      bpmnModeler: this.getBpmnModeler,
      params: this.params
    }
  },
  data() {
    return {
      propsComponent: 'CommonProps',
      bpmnModeler: null,
      canvas: null,
      container: null,
      element: null,
      key: '1',
      bpmData: new BpmData(),
      fileList: [],
      importBPMNFileDialogVisible: false,
      loading: false,
      isButtonDisabled: true,
      showButtonVisible: true,
      rightVisible: true,
      defaultData: {
        'bpmn:StartEvent': '交易开始',
        'bpmn:EndEvent': '交易完成',
        'bpmn:IntermediateThrowEvent': '交易终止'
      }
    }
  },
  mounted() {
    this.initBpmnModeler()
  },
  methods: {
    // 初始化BpmnModeler
    initBpmnModeler() {
      this.container = this.$refs.content
      const canvas = this.$refs.canvas
      var customTranslateModule = {
        translate: ['value', customTranslate]
      }
      this.bpmnModeler = new BpmnModeler({
        container: canvas,
        propertiesPanel: {
          parent: '#js-properties-panel'
        },
        additionalModules: [
          // 右边的工具栏
          propertiesPanelModule,
          // 左边工具栏以及节点
          propertiesProviderModule,
          // customControlsModule,
          // 汉化
          customTranslateModule,
          BpmnModeler, {
            // paletteProvider: ['value', ''], // 禁用左面板
            // labelEditingProvider: ['value', ''], // 禁用编辑
            // contextPadProvider: ['value', ''], // 禁用点击出现的contextPad
            // bendpoints: ['value', {}], // 禁止流程线变换waypoints
            zoomScroll: ['value', ''] // 禁止画布滚动
            // moveCanvas: ['value', ''] // 禁止拖拽
          }
        ],
        moddleExtensions: {
          // 模块拓展，拓展activiti的描述
          activiti: activitiModdleDescriptor
          // camunda: camundaModdleDescriptor
        }
      })
      this.importBpmnXml()
    },
    // 导入xml文档
    importBpmnXml() {
      // 清理画布
      this.bpmnModeler.clear()
      const that = this
      let bpmnXml = bpmnHelper.getBpmnTempate()
      // 获取xml数据
      if (that.$route.query.bpmnXml) {
        bpmnXml = that.$route.query.bpmnXml
      }
      that.bpmnModeler.importXML(bpmnXml, function(err) {
        if (err) {
          console.log('bpmn文档导入失败')
        } else {
          // 绑定监听事件
          that.successEvent()
        }
      })
    },
    getBpmnModeler() {
      return this.bpmnModeler
    },
    successEvent() {
      // 初始化element
      const elementRegistry = this.bpmnModeler.get('elementRegistry')
      const shape = elementRegistry.get('Process_1')
      this.element = shape
      // 若是查看功能，则去除监听，调整工具栏为不可见
      if (this.$route.query.flag === 'watch') {
        this.addModelerListener()
        this.addEventBusListener()
        this.adjustPaletteWatch()
        this.showButtonVisible = false
        this.rightVisible = false
        return
      }
      this.showButtonVisible = true
      this.rightVisible = true
      this.addModelerListener()
      this.addEventBusListener()
      // 调控左侧工具栏
      this.adjustPalette()
    },
    addModelerListener() {
      // 监听 modeler
      const bpmnjs = this.bpmnModeler
      const that = this
      const events = ['shape.added', 'shape.move.end', 'shape.removed']
      events.forEach(event => {
        that.bpmnModeler.on(event, e => {
          var elementRegistry = bpmnjs.get('elementRegistry')
          var shape = e.element ? elementRegistry.get(e.element.id) : e.shape
          if (event === 'shape.added') {
            // console.log('新增了shape')
            // 展示新增图形的属性
            that.key = e.element.id.replace('_label', '')
            that.propsComponent = bpmnHelper.getComponentByEleType(shape.type)
            that.element = e.element
          } else if (event === 'shape.move.end') {
            // console.log('移动了shape')
            // 展示新增图形的属性
            that.key = shape.id
            that.propsComponent = bpmnHelper.getComponentByEleType(shape.type)
            that.element = e.shape
          } else if (event === 'shape.removed') {
            // console.log('删除了shape')
            // 展示默认的属性
            that.propsComponent = 'CommonProps'
          }
        })
      })
    },
    addEventBusListener() {
      // 监听 element
      const that = this
      const eventBus = this.bpmnModeler.get('eventBus')
      const eventTypes = ['element.click', 'element.changed', 'selection.changed']
      eventTypes.forEach(eventType => {
        eventBus.on(eventType, function(e) {
          if (eventType === 'element.changed') {
            that.elementChanged(e)
          } else if (eventType === 'element.click') {
            // console.log('点击了element')
            if (!e || e.element.type === 'bpmn:Process') {
              that.key = '1'
              that.propsComponent = 'CommonProps'
              that.element = e.element
            } else {
              // 展示新增图形的属性
              that.key = e.element.id
              that.propsComponent = bpmnHelper.getComponentByEleType(e.element.type)
              that.element = e.element
            }
          }
        })
      })
    },
    // 调整左侧属性面板
    adjustPalette() {
      try {
        // 获取 bpmn 设计器实例
        const canvas = this.$refs.canvas
        const djsPalette = canvas.children[0].children[1].children[4]
        const djsPalStyle = {
          width: '130px',
          padding: '5px',
          background: 'white',
          left: '20px',
          borderRadius: 0
        }
        for (var key in djsPalStyle) {
          djsPalette.style[key] = djsPalStyle[key]
        }
        const palette = djsPalette.children[0]
        const allGroups = palette.children
        allGroups[0].style['display'] = 'none'
        // 修改控件样式
        for (var gKey in allGroups) {
          const group = allGroups[gKey]
          for (var cKey in group.children) {
            const control = group.children[cKey]
            const controlStyle = {
              display: 'flex',
              justifyContent: 'flex-start',
              alignItems: 'center',
              width: '100%',
              padding: '5px'
            }
            if (
              control.className &&
              control.dataset &&
              control.className.indexOf('entry') !== -1
            ) {
              const controlProps = this.bpmData.getControl(
                control.dataset.action
              )
              control.innerHTML = `<div style='font-size: 14px;font-weight:500;margin-left:15px;'>${
                controlProps['title']
              }</div>`
              for (var csKey in controlStyle) {
                control.style[csKey] = controlStyle[csKey]
              }
            }
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    // 调整左侧属性面板(不能编辑)
    adjustPaletteWatch() {
      try {
        // 获取 bpmn 设计器实例
        const canvas = this.$refs.canvas
        const djsPalette = canvas.children[0].children[1].children[4]
        // const djsPalette = canvas.children[1].children[1].children[4]
        djsPalette.style['display'] = 'none'
      } catch (e) {
        console.log(e)
      }
    },
    // 前进
    handleRedo() {
      this.bpmnModeler.get('commandStack').redo()
    },
    // 后退
    handleUndo() {
      this.bpmnModeler.get('commandStack').undo()
    },
    // 上传文件判断
    handleChange(file, fileList) {
      this.fileList = fileList
      if (this.fileList !== null) {
        this.isButtonDisabled = false
      }
    },
    // 将上传的bpmn展示在前端
    uploadFile() {
      this.importBPMNFileDialogVisible = false
      const reader = new FileReader()
      let data = ''
      // 读取bpmn文件
      reader.readAsText(this.fileList[0].raw)
      reader.onload = (event) => {
        data = event.target.result
        this.bpmnModeler.importXML(data, (err) => {
          if (err) {
            console.error('读取文件失败', err)
          }
        })
      }
    },
    // 直接部署
    deployEvent() {
      const formData = new FormData()
      // 获取保存的bpmn，发送给后台
      this.bpmnModeler.saveXML({ format: true }, function(err, xml) {
        if (err) {
          return console.error('无法保存BPMN', err)
        }
        formData.append('reportFile', new Blob([xml], { type: 'text/xml' }), 'activiti.bpmn')
      })
      uploadFilesDirect(formData).then(data => {
        // 跳转到流程管理列表
        this.$router.push({
          path: '/activiti/index'
        })
        this.$notify({
          title: '成功',
          message: '发布成功！',
          type: 'success'
        })
      }).catch(response => {
        this.$notify.error({
          title: '错误',
          message: '发布失败！'
        })
      })
    },
    // 导出bpmn文件
    exportBpmn() {
      this.bpmnModeler.saveXML({ format: true }, function(err, xml) {
        if (err) {
          return console.error('无法保存BPMN', err)
        }
        // 如果浏览器支持msSaveOrOpenBlob方法（也就是使用IE浏览器的时候）
        if (window.navigator.msSaveOrOpenBlob) {
          var blob = new Blob([xml], { type: 'text/plain' })
          window.navigator.msSaveOrOpenBlob(blob, 'activiti.bpmn')
        } else {
          var eleLink = document.createElement('a')
          eleLink.download = 'activiti.bpmn'
          eleLink.style.display = 'none'
          const blob = new Blob([xml]) // 字符内容转变成blob地址
          eleLink.href = URL.createObjectURL(blob)
          document.body.appendChild(eleLink) // 触发点击
          eleLink.click()
          document.body.removeChild(eleLink) // 然后移除
        }
      })
    },
    // 导出图片
    exportImg() {
      if (window.navigator.msSaveOrOpenBlob) {
        console.log('IE浏览器无法下载，建议使用谷歌浏览器')
        return
      }
      // 获取SVG数据（图片）
      this.bpmnModeler.saveSVG({ format: true }, (err, data) => {
        if (err) {
          console.log('保存失败')
        }
        var svgXml = data
        var canvas = document.createElement('canvas') // 准备空画布
        canvas.width = '1000px'
        canvas.height = screen.availHeight
        canvas.fillStyle = '#FFFFFF'
        canvg(canvas, svgXml)
        var imagedata = canvas.toDataURL('image/png')
        // 如果浏览器支持msSaveOrOpenBlob方法（也就是使用IE浏览器的时候）
        if (window.navigator.msSaveOrOpenBlob) {
          var bstr = atob(imagedata.split(',')[1])
          var n = bstr.length
          var u8arr = new Uint8Array(n)
          while (n--) {
            u8arr[n] = bstr.charCodeAt(n)
          }
          var blob = new Blob([u8arr])
          window.navigator.msSaveOrOpenBlob(blob, 'activiti.png')
        } else {
          var a = document.createElement('a')
          a.href = imagedata // 将画布内的信息导出为png图片数据
          a.download = 'activiti' // 设定下载名称
          a.click() // 点击触发下载
        }
      })
    },
    isInvalid(param) { // 判断是否是无效的值
      return param === null || param === undefined || param === ''
    },
    isSequenceFlow(type) { // 判断是否是线
      return type === 'bpmn:SequenceFlow'
    },
    getShape(id) {
      var elementRegistry = this.bpmnModeler.get('elementRegistry')
      return elementRegistry.get(id)
    },
    elementChanged(e) {
      const id = e.element.id.replace('_label', '')
      var shape = this.getShape(id)
      // var shape = this.getShape(e.element.id)
      this.element = shape
      const that = this
      if (!shape) {
        // 若是shape为null则表示删除, 无论是shape还是connect删除都调用此处
        // console.log('无效的shape')
        // 上面已经用 shape.removed 检测了shape的删除, 要是删除shape的话这里还会被再触发一次
        // console.log('删除了shape和connect')
        return
      }
      if (!this.isInvalid(shape.type)) {
        if (this.isSequenceFlow(shape.type)) {
          // console.log('改变了线')
        } else {
          that.setDefaultProperties()
        }
      }
    },
    setDefaultProperties() {
      const that = this
      const { element } = that
      if (element) {
        // 这里可以拿到当前点击的节点的所有属性
        const { type, businessObject } = element
        const { name } = businessObject
        if (that.verifyIsEvent(type)) {
          const eventType = businessObject.eventDefinitions ? businessObject.eventDefinitions[0]['$type'] : ''
          console.log(eventType)
        } else if (this.verifyIsTask(type)) {
          const taskType = type
          console.log(taskType)
        }
        that.element['name'] = name || that.defaultData[element.type]
      }
    },
    verifyIsEvent(type) {
      return type.includes('Event')
    },
    verifyIsTask(type) {
      return type.includes('Task')
    }
  }
}
</script>
<style lang="scss">
  /*左边工具栏以及编辑节点的样式*/
  @import 'bpmn-js/dist/assets/diagram-js.css';
  @import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css';
  @import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css';
  @import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css';
  /*右边工具栏样式*/
  @import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css';
  .containers{
    position: absolute;
    background-color: #ffffff;
    width: 100%;
    height: 700px;
  }
  .canvas{
    width: 100%;
    height: 700px;
  }
  .panel{
    position: absolute;
    right: 0;
    top: 0;
    width: 300px;
  }
  .bjs-powered-by {
    display:none !important;
  }
</style>
