<template>
  <el-cascader
    ref="deptCascader"
    :props="{ multiple: true }"
    :options="scopeOptions"
    :show-all-levels="false"
    collapse-tags
    placeholder="请选择营业所"
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
  name: 'InquiryDepartment',
  props: {},
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
    // 营业所选择框
    getScopeOptions() {
      getDataCodesTree('9005').then(res => {
        if (!res.success) {
          this.$message.error('获取部门异常.')
          return
        }
        this.tempDeptNo = res.content
        var self = this
        findAfterFiltrationDeptInfo().then(data => {
          var array = []
          arrayToTree(data).map((item) => {
            for (var i = 0; i < this.tempDeptNo.length; i++) {
              if (item.id === this.tempDeptNo[i].code) {
                var tree = Object.assign({}, item, { sort: i })
                array.push(tree)
              }
            }
          })
          array.sort(function(a, b) {
            return a.sort - b.sort
          })
          self.addTreeArray(array)
          self.scopeOptions = array
        }).catch(error => {
          console.log('error ==> ', error)
          error = '营业所检索条件，返回数据格式有误！'
          this.smcErrorMsg(error)
        })
      })
    },
    addTreeArray(arrayData) {
      if (arrayData.children !== undefined && arrayData.children.length > 0) {
        for (let i = 0; i < arrayData.length; i++) {
          arrayData[i].value = arrayData[i].code
          arrayData[i].label = arrayData[i].name
          this.addTreeArray(arrayData[i])
        }
      }
    },
    handleScopeChange(val) {
      var queryDept = []
      val.forEach(item => {
        console.log(item)
        queryDept.push(item[item.length - 1])
      })
      this.$emit('handleScopeChange', queryDept)
    }
  }
}
</script>
<style scoped>
element.style {
  height: 24px;
}
</style>
