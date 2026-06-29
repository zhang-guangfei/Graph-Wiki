<template>
  <div>
    <el-form-item label="目标命名空间" >
      <el-input v-model="targetNamespace" readonly/>
    </el-form-item>
    <el-form-item v-show="isShow" label="标签版本" >
      <el-input v-model="versionTag" />
    </el-form-item>
    <el-form-item>
      <el-checkbox v-show="isShow" v-model="isExecutable">是否可执行</el-checkbox>
    </el-form-item>
    <el-form-item v-show="isShow" label="任务优先级" >
      <el-input v-model="taskPriority" />
    </el-form-item>
    <el-form-item v-show="isShow" label="工作优先级">
      <el-input v-model="jobPriority" />
    </el-form-item>
    <el-form-item v-show="isShow" label="候选开始组">
      <el-select v-model="candidateStarterGroups" multiple filterable placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"/>
      </el-select>
    </el-form-item>
    <el-form-item v-show="isShow" label="候选开始用户" >
      <el-input v-model="candidateStarterUsers"/>
    </el-form-item>
    <el-form-item v-show="isShow" label="历史生存时间" >
      <el-input v-model="historyTimeToLive"/>
    </el-form-item>
    <el-form-item v-show="isShow" label="监听器配置" >
      <el-input v-model="historyTimeToLive"/>
    </el-form-item>
  </div>
</template>
<script>
import bpmnHelper from '@/api/bpmnjs/helper/bpmnHelper'

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
  inject: ['bpmn'],
  data() {
    return {
      targetNamespace: '',
      isShow: false,
      versionTag: '',
      isExecutable: true,
      taskPriority: '',
      jobPriority: '',
      candidateStarterUsers: '',
      historyTimeToLive: '',
      options: [{
        value: '1',
        label: '黄金糕'
      }, {
        value: '2',
        label: '双皮奶'
      }],
      candidateStarterGroups: []
    }
  },
  mounted() {
    const modelInfo = this.$root.params.modelInfo
    const modelId = modelInfo.modelId
    const definition = this.element.parent
    if (definition) {
      const definitionBo = bpmnHelper.getBo(definition)
      definitionBo.set('targetNamespace', modelId)
    }
    this.targetNamespace = modelId
    this.$emit('updateProperties', { commonProperties: { id: modelId, name: modelInfo.name }})
  },
  methods: {
  }
}
</script>
