<template>
  <el-cascader
    ref="warehouseCascader"
    :props="{ multiple: true }"
    :options="scopeOptions"
    :show-all-levels="false"
    collapse-tags
    placeholder="请选择仓库"
    class="searchDeptNoClass"
    style="width: 140px;margin-left: 15px"
    size="mini"
    filterable
    clearable
    @change="handleScopeChange"
  />
</template>
<script>

import { listWarehouseNoWT } from '@/api/common/dict'
export default {
  name: 'Warehouse',
  props: {},
  data() {
    return {
      scopeOptions: [],
      warehouseData: [],
      warehouseMaster: [],
      warehouseSub: []
    }
  },
  mounted() {
    this.getScopeOptions()
  },
  methods: {
    // 仓库选择框
    getScopeOptions() {
      listWarehouseNoWT().then(res => {
        if (res.success) {
          this.warehouseData = res.content
          this.warehouseMaster = this.warehouseData.filter(function(f) {
            return f.warehouseName != null && f.warehouseName.indexOf('物流中心') >= 0
          })
          for (const item of this.warehouseMaster) {
            const subWarehouse = this.warehouseData.filter(function(f) {
              return f.parentCode != null && f.parentCode.indexOf(item.warehouseCode) >= 0
            })
            this.warehouseSub = []
            for (const subItem of subWarehouse) {
              this.warehouseSub.push({ value: subItem.warehouseCode, label: subItem.warehouseName })
            }
            if (this.warehouseSub != null && this.warehouseSub.length > 0) {
              this.scopeOptions.push({ value: item.warehouseCode, label: item.warehouseName, children: this.warehouseSub })
            } else {
              this.scopeOptions.push({ value: item.warehouseCode, label: item.warehouseName })
            }
          }
        } else {
          this.warehouseData = []
        }
      }).catch(error => {
        console.error(error)
        this.warehouseData = []
      })
    },
    handleScopeChange(val) {
      const selectData = []
      console.info(val)
      val.forEach(item => {
        selectData.push(item[1])
      })
      this.$emit('handleScopeChange', selectData)
    }
  }
}
</script>
<style scoped>
</style>
