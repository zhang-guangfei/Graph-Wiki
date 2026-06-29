<template>
  <div>
    <el-form-item v-show="false" label="编号" >
      <el-input v-model="id"/>
    </el-form-item>
    <el-form-item label="名称">
      <el-input v-model="name"/>
    </el-form-item>
    <el-form-item v-show="false" label="表单标识" >
      <el-input v-model="formKey"/>
    </el-form-item>
    <el-form-item v-show="false" label="文档" >
      <el-input v-model="documentation" type="textarea"/>
    </el-form-item>
  </div>
</template>
<script>
export default {
  // props: ['element'],
  props: {
    element: {
      type: Object,
      default: function() {
        return {}
      }
    }
  },
  inject: ['bpmnModeler'],
  data() {
    return {
      id: this.element.id || '',
      name: '',
      formKey: '',
      documentation: ''
    }
  },
  watch: {
    id: function(newVal, oldVal) {
      const bpmnModeler = this.bpmnModeler()
      const modeling = bpmnModeler.get('modeling')
      modeling.updateProperties(this.element, {
        id: newVal
      })
    },
    name: function(newVal, oldVal) {
      const bpmnModeler = this.bpmnModeler()
      const modeling = bpmnModeler.get('modeling')
      modeling.updateProperties(this.element, {
        name: newVal
      })
    },
    element: {
      handler(newVal, oldVal) {
        if (newVal !== oldVal) {
          if (newVal.type === 'bpmn:EndEvent') { // 防止修改其他子组件的属性
            this.name = newVal.name
          }
        }
      },
      immediate: true,
      deep: true
    }
  }
}
</script>
