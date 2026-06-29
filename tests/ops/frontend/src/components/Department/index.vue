<template>
  <el-cascader
    ref="deptCascader"
    :props="{ multiple: true }"
    :options="scopeOptions"
    :show-all-levels="false"
    :placeholder="showdesc"
    collapse-tags
    class="searchDeptNoClass"
    style="width: 140px;margin-left: 15px;height: 32px"
    size="mini"
    filterable
    clearable
    @change="handleScopeChange"
  />
</template>
<script>
import { findAfterFiltrationDeptInfo } from '@/api/unitsTree'
import { arrayToTree } from '@/utils/index'
import { getDataCodesTree } from '@/api/common/dict'
export default {
  name: 'Department',
  props: {
    showdesc: {
      type: String,
      default: '请选择营业所'
    }
  },
  data() {
    return {
      scopeOptions: [],
      tempDeptNo: []
    }
  },
  mounted() {
    this.getScopeOptions()
  },
  methods: {
    // 获取营业所选项数据
    // 功能: 从后端获取部门字典数据和过滤后的部门树形数据,构建级联选择器所需的选项
    // 流程:
    //  1. 获取部门字典(代码9005),确定哪些部门有权限显示
    //  2. 获取过滤后的部门树形数据
    //  3. 将扁平数据转为树形结构,并根据权限过滤
    //  4. 对部门进行排序
    //  5. 递归处理树形数据,转换为级联选择器需要的格式
    getScopeOptions() {
      // 第一步: 获取部门字典数据(代码9005),用于权限控制
      getDataCodesTree('9005').then(res => {
        if (!res.success) {
          this.$message.error('获取部门异常.')
          return
        }
        // 保存有权限的部门列表
        this.tempDeptNo = res.content
        var self = this

        // 第二步: 获取过滤后的部门树形数据
        findAfterFiltrationDeptInfo().then(data => {
          var array = []

          // 第三步: 将扁平数据转为树形结构,并根据权限过滤
          arrayToTree(data).map((item) => {
            // 遍历有权限的部门列表,只保留匹配的部门
            for (var i = 0; i < this.tempDeptNo.length; i++) {
              if (item.id === this.tempDeptNo[i].code) {
                // 复制部门数据,并添加排序字段
                var tree = Object.assign({}, item, { sort: i })

                // 如果标记为隐藏子级,则删除 children(不显示下级部门)
                if (this.tempDeptNo[i].extNote1 === 'hideChildren') {
                  delete tree.children // 删除子级
                }
                array.push(tree)
              }
            }
          })

          // 第四步: 按 sort 字段排序,保证部门显示顺序
          array.sort(function(a, b) {
            return a.sort - b.sort
          })
          // 第五步: 递归处理树形数据,转换字段名
          self.addTreeArray(array)
          // 赋值给级联选择器的 options
          self.scopeOptions = array
        }).catch(error => {
          console.log('error ==> ', error)
          error = '营业所检索条件,返回数据格式有误!'
          this.smcErrorMsg(error)
        })
      })
    },

    /**
     * 递归处理树形数据
     * 功能: 将后端返回的树形数据转换为 Element UI 级联选择器需要的格式
     * 转换规则:
     *   - code -> value (级联选择器识别的字段)
     *   - name -> label (级联选择器显示的字段)
     * 注意: 该方法会递归处理所有层级的子节点,支持任意深度的树形结构
     *
     * @param {Array} arrayData - 树形节点数组
     */
    addTreeArray(arrayData) {
      // 判断是否为有效的数组
      if (arrayData.children !== undefined && arrayData.children.length > 0) {
        for (let i = 0; i < arrayData.length; i++) {
          // 将 code 转换为 value(级联选择器需要的字段名)
          arrayData[i].value = arrayData[i].code
          // 将 name 转换为 label(级联选择器需要的字段名)
          arrayData[i].label = arrayData[i].name
          // 递归处理当前节点的子节点
          this.addTreeArray(arrayData[i])
        }
      }
    },

    /**
     * 处理级联选择器的值变化事件
     * 功能: 接收用户的选择结果,提取每条路径的最后一级(叶子节点),并通过事件传递给父组件
     * 参数说明:
     *   val - 二维数组,表示用户选择的多条路径
     *   例如: [['HD', 'SH', 'PD'], ['HD', 'WH']] 表示选择了"华东>上海>浦东"和"华东>武汉"
     * 处理逻辑:
     *   - 遍历每条选择路径
     *   - 提取路径的最后一个元素(最末级部门编号)
     *   - 通过 $emit 将结果数组传递给父组件
     *
     * @param {Array} val - 级联选择器返回的二维数组
     */
    handleScopeChange(val) {
      var queryDept = []
      // 遍历每条选择路径
      val.forEach(item => {
        console.log(item)
        // 提取路径的最后一个元素(叶子节点)
        // 例如: ['HD', 'SH', 'PD'] -> 'PD'
        queryDept.push(item[item.length - 1])
      })
      // 将处理后的部门编号数组传递给父组件
      this.$emit('handleScopeChange', queryDept)
    }
  }
}
</script>
<style scoped>
</style>
