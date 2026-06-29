<template>
  <div>
    <div style="width:40%;float:left;margin-left:20px">
      <div class="custom-tree-container" style="width:100%">
        <p>岗位列表</p>
        <div class="block">
          <div style="margin-bottom:5px">
            <el-row>
              <el-col :span="24">
                <div class="grid-content bg-purple">
                  <el-input v-model="unitId" size="mini" style="width:30%" placeholder="请输入部门编码" clearable/>
                  <el-input v-model="positionName" size="mini" style="margin-left:5px;width:30%" placeholder="请输入岗位名称" clearable/>
                  <el-button type="primary" size="mini" style="margin-left:5px" plain @click="searchEvent">查询</el-button>
                  <el-button type="primary" size="mini" style="margin-left:5px" plain @click="bindEvent">绑定</el-button>
                </div>
              </el-col>
              <!-- <el-col :span="8">
                <div class="grid-content bg-purple-light">
                  <el-button type="primary" size="mini" plain @click="bindEvent">绑定</el-button>
                </div>
              </el-col> -->
            </el-row>
          </div>
          <vxe-grid
            ref="positionGrid"
            :loading="positionLoading"
            :data="positionTableData"
            :columns="positionTableColumn"
            align="center"
            size="mini"
            max-height="600"
            show-overflow
            show-header-overflow
            auto-resize
            keep-source
            resizable
            @checkbox-all="positionSelectAllEvent"
            @checkbox-change="positionSelectChangeEvent"/>
        </div>
      </div>
    </div>
    <div style="width:50%;float:left">
      <div class="custom-tree-container" style="margin-left:50px;width:100%">
        <p>审批流节点列表</p>
        <div class="block">
          <div style="margin-bottom:5px">
            <el-select v-model="businessType" size="mini" clearable placeholder="请选择业务类型" @change="selectChanged">
              <el-option
                v-for="item in businessTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </div>
          <vxe-grid
            ref="processNodeGrid"
            :loading="processNodeLoading"
            :data="processNodeTableData"
            :columns="processNodeTableColumn"
            :radio-config="{highlight: true}"
            align="center"
            size="mini"
            max-height="600"
            show-overflow
            show-header-overflow
            auto-resize
            keep-source
            resizable
            @radio-change="radioChangeEvent"/>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { findBussinessTypeOptions, findProcessNode, addProcessNodePosition, findByPositionName } from '@/api/nodePosition'
import { findPositionList } from '@/api/position'
export default {
  name: 'NodePositionIndex',
  data() {
    return {
      positionLoading: true,
      positionTableData: [],
      positionTableColumn: [
        { type: 'checkbox', width: 50 },
        { type: 'seq', width: 50 },
        { field: 'name', title: '岗位名称', minWidth: 200 }
      ],
      positionSelectList: [],
      processNodeLoading: false,
      processNodeTableData: [],
      processNodeTableColumn: [
        { type: 'radio', width: 50 },
        { type: 'seq', width: 50 },
        { field: 'processNodeCode', title: '审批流节点编码', minWidth: 100 },
        { field: 'processNode', title: '审批流节点', minWidth: 100 },
        { field: 'parentProcessNodeCode', title: '父节点编码', minWidth: 100 },
        { field: 'executionId', title: '审批节点Id', minWidth: 100 },
        { field: 'status', title: '状态', minWidth: 100 }
      ],
      processNodeSelect: null,
      unitId: '',
      positionName: '',
      businessType: '',
      businessTypeOptions: []
    }
  },
  created() {
    this.getBussinessTypeOptions()
    this.getPositionList()
  },
  methods: {
    // 获取业务类型数据
    getBussinessTypeOptions() {
      findBussinessTypeOptions().then((data) => {
        data.content.forEach(element => {
          this.businessTypeOptions.push({ value: element.businessType, label: element.businessName })
        })
      }).catch(error => {
        console.log(error)
      })
    },
    // 获取岗位列表
    getPositionList() {
      this.positionLoading = true
      this.positionTableData = []
      const params = { unitId: this.unitId, name: this.positionName }
      findPositionList(params).then((data) => {
        data.content.forEach(element => {
          this.positionTableData.push({ name: element })
        })
        this.processNodeSelect = null
        this.$refs.processNodeGrid.clearRadioRow()
        this.positionLoading = false
      }).catch(error => {
        console.log(error)
        this.positionLoading = false
      })
    },
    // 根据业务类型查询审批节点
    getProcessNode(params) {
      this.processNodeLoading = true
      findProcessNode(params).then((data) => {
        this.processNodeTableData = data.content
        this.processNodeLoading = false
      }).catch(error => {
        console.log(error)
        this.processNodeLoading = false
      })
    },
    // 部门编码输入框改变事件
    searchEvent() {
      this.getPositionList()
    },
    // 业务类型选择改变事件
    selectChanged(value) {
      const params = { businessType: value }
      this.getProcessNode(params)
    },
    // 岗位列表全选事件
    positionSelectAllEvent({ checked, records }) {
      this.positionSelectList = records
      if (this.positionSelectList.length === 1) {
        const params = { positionName: records[0].name, businessType: this.businessType }
        findByPositionName(params).then((data) => {
          this.processNodeTableData.forEach(element => {
            if (data.content === element.processNodeCode) {
              this.processNodeSelect = element
              this.$refs.processNodeGrid.setRadioRow(element)
              return
            }
          })
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.processNodeSelect = null
        this.$refs.processNodeGrid.clearRadioRow()
      }
    },
    // 岗位列表选择事件
    positionSelectChangeEvent({ checked, records }) {
      this.positionSelectList = records
      // 如果选择一条数据，则获取关联信息
      if (this.positionSelectList.length === 1) {
        const params = { positionName: records[0].name, businessType: this.businessType }
        findByPositionName(params).then((data) => {
          this.processNodeTableData.forEach(element => {
            if (data.content === element.processNodeCode) {
              this.processNodeSelect = element
              this.$refs.processNodeGrid.setRadioRow(element)
              return
            }
          })
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.processNodeSelect = null
        this.$refs.processNodeGrid.clearRadioRow()
      }
    },
    // 审批流节点选择改变
    radioChangeEvent({ row }) {
      this.processNodeSelect = row
    },
    // 绑定
    bindEvent() {
      const datas = []
      if (this.positionSelectList.length === 0) {
        this.$notify({
          title: '警告',
          message: '请选择岗位名称',
          type: 'warning'
        })
        return
      }
      console.log(this.processNodeSelect)
      if (this.processNodeSelect === null) {
        this.$notify({
          title: '警告',
          message: '请选择审批流节点',
          type: 'warning'
        })
        return
      }
      this.positionSelectList.forEach(positionElement => {
        datas.push({ processNodeCode: this.processNodeSelect.processNodeCode, positionName: positionElement.name })
      })
      addProcessNodePosition(datas).then((data) => {
        this.$notify({
          title: '成功',
          message: '绑定成功',
          type: 'success'
        })
      }).catch(error => {
        console.log(error)
        this.$notify.error({
          title: '失败',
          message: '绑定失败'
        })
      })
    }
  }
}
</script>

<style>
  body>.el-container{
    margin-bottom: 40px;
  }
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 12px;
    padding-right: 8px;
  }
</style>
