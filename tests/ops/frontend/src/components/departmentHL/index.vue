<template>
  <el-cascader
    ref="cascaderHandle"
    :options="deptNoOptions"
    :show-all-levels="false"
    :props="{ value: 'id', label: 'name', multiple: true }"
    :placeholder="placeholder"
    :filterable="filterable"
    :collapse-tags="collapseTags"
    :clearable="clearable"
    :style="customStyle"
    :size="size"
    class="menu-department"
    @change="handleChange"
  />
</template>

<script>
// import { getDictDataByPid } from '@/api/common/dict'
import { findDeptsToTree } from '@/api/organ'
import { getDataCodesTree } from '@/api/common/dict'

export default {
  name: 'DepartmentHL',
  props: {
    // 占位符文本
    placeholder: {
      type: String,
      default: '选择营业所'
    },
    // 是否可搜索
    filterable: {
      type: Boolean,
      default: true
    },
    // 是否折叠标签
    collapseTags: {
      type: Boolean,
      default: true
    },
    // 是否可清空
    clearable: {
      type: Boolean,
      default: true
    },
    // 自定义样式
    customStyle: {
      type: String,
      default: 'width: 170px'
    },
    // 尺寸
    size: {
      type: String,
      default: 'mini'
    }
  },
  data() {
    return {
      deptNoOptions: [],
      cpnysData: [],
      tempDeptNo: []
    }
  },
  mounted() {
    this.findDeptNos()
  },
  methods: {
    /**
     * 获取营业所树形数据
     * 功能: 从后端获取营业所数据并根据公司代码进行过滤
     * 流程:
     *   1. 获取公司字典数据(代码9005)
     *   2. 获取营业所树形数据
     *   3. 根据公司数据过滤营业所
     */
    findDeptNos() {
      // 第一步: 获取公司字典数据
      getDataCodesTree('9005').then(res => {
        if (!res.success) {
          this.$message.error('获取部门异常.')
          return
        }
        // 保存有权限的部门列表
        this.tempDeptNo = res.content
      })

      // 第二步: 获取营业所树形数据
      findDeptsToTree().then(res => {
        this.deptNoOptions = res.content
        if (!res.success) {
          return
        }

        // 第三步: 标记匹配的营业所
        for (var j = 0; j < this.deptNoOptions.length; j++) {
          for (var k = 0; k < this.tempDeptNo.length; k++) {
            if (this.deptNoOptions[j].id === this.tempDeptNo[k].code) {
              this.deptNoOptions[j].flag = true
              this.deptNoOptions[j].sort = j
              if (this.tempDeptNo[k].extNote1 === 'hideChildren') {
                this.deptNoOptions[j].children = undefined
                this.deptNoOptions[j].sort = 999 // 设置一个较大的值，确保这些节点排在最后
              }
            }
          }
        }

        // 第四步: 过滤掉不匹配的营业所
        for (var j2 = 0; j2 < this.deptNoOptions.length; j2++) {
          if (!this.deptNoOptions[j2].flag) {
            this.deptNoOptions.splice(j2, 1)
            j2--
          }
        }
        // 排序，从小到大
        this.deptNoOptions.sort(function(a, b) {
          return a.sort - b.sort
        })
        // 第五步: 如果数据为空,重新获取
        if (res.content.length === 0) {
          this.findDeptNos()
          return
        }
      })
    },

    /**
     * 处理级联选择器的值变化事件
     * 功能: 接收用户的选择结果,提取所有层级的节点值,并通过事件传递给父组件
     * 参数说明:
     *   value - 二维数组,表示用户选择的多条路径
     *   例如: [[ 'HD', 'SH', 'PD'], ['HD', 'WH']] 表示选择了"华东>上海>浦东"和"华东>武汉"
     * 处理逻辑:
     *   - 遍历每条选择路径
     *   - 提取路径中的所有节点值(去重)
     *   - 通过 $emit 将结果数组传递给父组件
     *
     * @param {Array} value - 级联选择器返回的二维数组
     */
    handleChange(value) {
      const deptNos = []
      // 遍历每条选择路径
      for (let i = 0; i < value.length; i++) {
        // 遍历路径中的每个节点
        for (let j = 0; j < value[i].length; j++) {
          // 去重处理
          if (deptNos.indexOf(value[i][j]) === -1) {
            deptNos.push(value[i][j])
          }
        }
      }
      // 将处理后的部门编号数组传递给父组件
      this.$emit('change', deptNos)
    }
  }
}
</script>

<style scoped>
.menu-department /deep/ .el-cascader__search-input {
  min-width: 1px !important;
  height: 0px !important;
  margin-left: 0px;
  margin-top: 0px;
}
</style>
