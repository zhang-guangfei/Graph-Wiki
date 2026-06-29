<template>
  <div class="order-edit-class base-index" element-loading-text="加载中">
    <el-dialog :visible.sync="show" title="限制订单提醒配置" top="10vh" width="700px" @close="close">
      <el-form
        ref="addFormRef"
        :model="addDialog.addForm"
        label-width="90px"
      >
        <el-divider content-position="left">修改页面</el-divider>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item
              label="缓冲期>="
              prop="bufferDays"
            >
              <el-input v-model="addDialog.addForm.bufferDays" type="number" onkeyup="value=value.replace(/[^\d]/g,'')" @input="change($event)"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!--操作按钮-->
      <span slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="9" :span="2">
            <el-button :loading="addLoading" @click="addButton('addFormRef')">保存</el-button>
          </el-col>
          <el-col :offset="1" :span="2">
            <el-button @click="close">关闭</el-button>
          </el-col>
        </el-row>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { selectBufferDays, updateBufferDays } from '@/api/order/limitPrompt'
export default {
  name: 'Dialog',
  props: {
    // 是否显示弹窗
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      show: false,
      assChildFlag: false,
      addDialog: {
        addForm: {
        }
      },
      addLoading: false,
      addData: []
    }
  },
  watch: {
    visible: {
      deep: true, // 深度监听
      handler(newObj, oldObj) {
        if (newObj) {
          this.show = newObj
          this.getConfigList()
        }
      }
    }
  },
  methods: {
    change() {
      this.$forceUpdate()
    },
    getConfigList() {
      selectBufferDays().then((res) => {
        this.addDialog.addForm.bufferDays = res.content
        this.$forceUpdate()
      }).catch((e) => {
      })
    },
    // 增添事件
    addButton() {
      this.addLoading = true
      updateBufferDays(this.addDialog.addForm.bufferDays).then(res => {
        this.addLoading = false
        if (res.success) {
          this.close()
          this.smcSuccessMsg(res.message)
        } else {
          this.smcErrorMsg(res.message)
        }
      }).catch(res => {
        this.addLoading = false
        this.smcErrorMsg(res.message)
      })
    },
    // 关闭窗口
    close() {
      this.show = false
      this.$emit('update:visible', false)
      this.addData = []
    }
  }
}
</script>

<style scoped lang="scss">
.el-input--mini .el-input__inner {
  height: 40px;
}
</style>
