<template>
  <div>
    <el-dialog :visible.sync="initDia" :close-on-click-modal="false" title="消息管理">
      <el-table
        ref="msgData"
        :data="msgData"
        :row-class-name="tableRowClassName"
        max-height="450"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">

        <el-table-column
          type="selection"
          width="55"/>

        <el-table-column
          label="发送时间"
          width="120">
          <template slot-scope="scope">{{ scope.row.date }}</template>
        </el-table-column>

        <el-table-column
          prop="msgType"
          label="消息类型"
          width="120"/>

        <el-table-column
          prop="status"
          label="消息状态" />

        <el-table-column
          prop="msgContent"
          label="消息内容"
          show-overflow-tooltip />
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button size="small" type="text" @click="delRow(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取 消</el-button>
        <el-button type="primary" @click="closeDialog">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'MessageManagement',
  props: {
    dialogtablevisible: {
      type: Boolean,
      default: function() {
        return false
      }
    },
    msgData: {
      type: Array,
      default: function() {
        return []
      }
    }
  },
  data() {
    return {
      multipleSelection: []
    }
  },
  computed: {
    initDia: {
      get() {
        return this.dialogtablevisible
      },
      set(val) {
        console.log('计算属性中的val ->', val)
        this.$emit('changeDialogFlag', val)
      }
    }
  },
  methods: {
    closeDialog() {
      this.initDia = false
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.status === 'error') {
        return 'warning-row'
      } else if (row.status === 'success') {
        return 'success-row'
      }
      return ''
    },
    handleClick(row) {
      console.log(row)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    }
  }
}
</script>
<style lang="scss" scoped>
.item {
  margin-top: -20px;
  margin-right: 34px;
}
</style>
<style>
.el-table .warning-row {
    background: oldlace;
  }

.el-table .success-row {
  background: #f0f9eb;
}
</style>
