<template>
  <div class="order-edit-class base-index" element-loading-text="加载中">
    <el-dialog :visible.sync="show" title="通知发货" top="10vh" width="700px" @close="close">
      <el-form
        ref="addFormRef"
        :rules="rules"
        :model="addDialog.addForm"
        label-width="90px"
      >
        <el-divider content-position="left">新增页面 -（需校验订单）</el-divider>
        <el-row :gutter="20">
          <el-col :span="11" style="margin-top: -1px;">
            <el-form-item
              label="订单号: "
              prop="orderFno"
            >
              <el-input :disabled="defaultOrderFno!==null" v-model="addDialog.addForm.orderFno" @input="change($event)" @blur="verify()" @keyup.enter.native="verify()"/>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="11">
            <el-form-item>
              <el-button :loading="verifyLoading" type="primary" @click="verify()">校验订单</el-button>
            </el-form-item>
          </el-col> -->
          <el-col v-if="assChildFlag" :span="11" style="margin-top: -1px;">
            <el-form-item
              label="订单型号: "
              prop="adress">
              <el-select v-model="modelNoGroup" size="mini" placeholder="请选择" style="height: 40px;" @change="modelNoChange">
                <el-option
                  v-for="item in modelNoList"
                  :key="item.modelNo"
                  :label="item.modelNo"
                  :value="item"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col v-show="!assChildFlag" :span="11">
            <el-form-item
              label="订单型号: "
              prop="modelNo"
            >
              <label>{{ addDialog.addForm.modelNo }}</label>
              <el-input v-show="false" v-model="addDialog.addForm.modelNo" readonly="readonly" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="订单数量:"
              prop="orderQty"
            >
              {{ addDialog.addForm.orderQty }}
              <el-input v-show="false" v-model="addDialog.addForm.orderQty" readonly="readonly" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="就绪数量:"
              prop="readyQty"
            >
              {{ addDialog.addForm.readyQty }}
              <el-input v-show="false" v-model="addDialog.addForm.readyQty" readonly="readonly" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="已计划数: "
              prop="matchPlan"
            >
              {{ addDialog.addForm.matchPlanQty }}
              <el-input v-show="false" v-model="addDialog.addForm.matchPlanQty" readonly="readonly" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="可计划数:"
              prop="tishi"
            >
              {{ addDialog.addForm.tishi }}
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="计划数量:"
              prop="planQty"
            >
              <el-input v-model="addDialog.addForm.planQty" :readonly="addDialog.addForm.readonly" :disabled="addDialog.addForm.readonly" type="number" onkeyup="value=value.replace(/[^\d]/g,'')" @input="change($event)"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="客户货期:"
              prop="hopeDate"
            >
              <el-date-picker v-model="addDialog.addForm.hopeDate" :readonly="addDialog.addForm.readonly" :disabled="addDialog.addForm.readonly" :picker-options="disabledDate" placeholder="客户货期" value-format="yyyy-MM-dd" format="yyyy-MM-dd" type="date" clearable @input="change($event)"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!--操作按钮-->
      <span slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="9" :span="2">
            <el-button :loading="addLoading" size="mini" type="primary" plain @click="addButton('addFormRef')">保存</el-button>
          </el-col>
          <el-col :offset="1" :span="2">
            <el-button size="mini" type="primary" plain @click="close">关闭</el-button>
          </el-col>
        </el-row>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getCreateVerify, save } from '@/api/notifyShipmentPlan'
export default {
  name: 'Dialog',
  props: {
    // 是否显示弹窗
    visible: {
      type: Boolean,
      default: false
    },
    defaultOrderFno: {
      type: String,
      required: false,
      default: null
    }
  },
  data() {
    const hopeDateValid = ({ row, cellValue }) => {
      return new Promise((resolve, reject) => {
        const hopedate = this.addDialog.addForm.hopeDate
        if (hopedate == null) {
          reject(new Error('期望货期不为空'))
        }
        const currentDate = new Date()
        if (new Date(hopedate) < currentDate) {
          reject(new Error('期望货期必须大于当前日期'))
        }
        resolve()
      })
    }
    const planQtyValid = ({ row, cellValue }) => {
      return new Promise((resolve, reject) => {
        if (this.addDialog.addForm.matchPlanQty == null) {
          reject(new Error('请先校验正确订单'))
        }
        if (Number(this.addDialog.addForm.planQty) < 1) {
          reject(new Error('计划数量大于0'))
        }
        if (this.veriftyObj.orderAssChildQty - this.veriftyObj.planQty < Number(this.addDialog.addForm.planQty)) {
          reject(new Error('计划数量大于可订单剩余数量'))
        }
        resolve()
      })
    }
    return {
      show: false,
      assChildFlag: false,
      addDialog: {
        addForm: {
          planQty: 0,
          tishi: '',
          readonly: false,
          creator: ''
        }
      },
      modelNoGroup: {},
      modelNoList: [],
      veriftyObj: {},
      addLoading: false,
      verifyLoading: false,
      // 校验规则
      rules: {
        orderFno: [
          { required: true, message: '此项不能为空', trigger: 'blur' }
        ],
        planQty: [
          { validator: planQtyValid, trigger: 'blur' }
        ],
        hopeDate: [
          { validator: hopeDateValid, trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    visible: {
      deep: true, // 深度监听
      handler(newObj, oldObj) {
        if (newObj) {
          this.show = newObj
          if (this.defaultOrderFno !== null) {
            this.addDialog.addForm.orderFno = this.defaultOrderFno
            this.verify()
          }
        }
      }
    }
  },
  methods: {
    change() {
      this.$forceUpdate()
    },
    // 选型号
    disabledDate(time) {
      return time.getTime() < Date.now() - 8.64e7
    },
    modelNoChange(val) {
      this.addDialog.addForm.modelNo = val.modelNo
      this.modelNoGroup = val.modelNo
      this.addDialog.addForm.orderQty = val.orderAssChildQty
      this.addDialog.addForm.readyQty = val.readyQty
      this.addDialog.addForm.matchPlanQty = val.planQty
      this.veriftyObj = val
      this.getTishi()
    },
    // 添加按钮事件
    addButton(formNameRef) {
      // 表单校验
      this.$refs[formNameRef].validate((valid) => {
        if (valid) {
          this.addEvent()
        } else {
          this.smcErrorMsg('表单校验失败，请输入正确的格式')
        }
      })
    },
    verify() {
      if (this.addDialog.addForm.orderFno == null || this.addDialog.addForm.orderFno === '') {
        return false
      }

      if (this.addDialog.addForm.orderFno.length < 10) {
        return false
      }
      this.modelNoList = []
      this.modelNoGroup = {}
      this.assChildFlag = false
      // this.addDialog.addForm.planQty = 0
      this.addDialog.addForm.modelNo = null
      this.addDialog.addForm.orderQty = null
      this.addDialog.addForm.readyQty = null
      this.addDialog.addForm.matchPlanQty = null
      this.verifyLoading = true
      getCreateVerify(this.addDialog.addForm.orderFno).then(res => {
        this.verifyLoading = false
        if (res.success) {
          this.modelNoList = res.data.items
          this.veriftyObj = res.data.items[0]
          this.modelNoGroup = res.data.items[0].modelNo
          this.addDialog.addForm.modelNo = res.data.items[0].modelNo
          this.addDialog.addForm.orderQty = res.data.items[0].orderAssChildQty
          this.addDialog.addForm.readyQty = res.data.items[0].readyQty
          this.addDialog.addForm.matchPlanQty = res.data.items[0].planQty
          if (res.data.items.length > 1) {
            for (var i = 0; i < res.data.items.length; i++) {
              if (res.data.items[i].orderAssChildQty !== res.data.items[i].planQty) {
                this.veriftyObj = res.data.items[i]
                this.modelNoGroup = res.data.items[i].modelNo
                this.addDialog.addForm.modelNo = res.data.items[i].modelNo
                this.addDialog.addForm.orderQty = res.data.items[i].orderAssChildQty
                this.addDialog.addForm.readyQty = res.data.items[i].readyQty
                this.addDialog.addForm.matchPlanQty = res.data.items[i].planQty
                break
              }
            }
            this.assChildFlag = true
          }
          this.getTishi()
          this.smcSuccessMsg('验证成功')
        } else {
          var orderId = this.addDialog.addForm.orderFno
          this.resetData()
          this.addDialog.addForm.orderFno = orderId
          this.smcErrorMsg(res.message)
        }
      }).catch(res => {
        this.verifyLoading = false
        console.log(res)
      })
    },
    getTishi() {
      if (this.addDialog.addForm.matchPlanQty !== this.addDialog.addForm.orderQty) {
        var lastQty = this.addDialog.addForm.orderQty - this.addDialog.addForm.matchPlanQty
        this.addDialog.addForm.tishi = lastQty
        this.addDialog.addForm.readonly = false
      } else {
        this.addDialog.addForm.tishi = 0
        this.addDialog.addForm.readonly = true
      }
    },
    // 增添事件
    addEvent() {
      this.addLoading = true
      this.addDialog.addForm.creator = this.$store.getters.name
      save(this.addDialog.addForm).then(res => {
        this.addLoading = false
        if (res.success) {
          this.close()
          this.smcSuccessMsg(res.message)
          this.$emit('handleSuccess', res.message)
        } else {
          this.smcErrorMsg(res.message)
          this.$emit('handleSuccess', res.message)
        }
      }).catch(res => {
        this.addLoading = false
        this.smcErrorMsg(res.message)
      })
    },
    // 关闭窗口
    close() {
      this.show = false
      this.resetData()
      this.$emit('update:visible', false)
    },
    resetData() {
      this.modelNoList = []
      this.modelNoGroup = null
      this.veriftyObj = {}
      this.assChildFlag = false
      this.addDialog.addForm.tishi = ''
      this.addDialog.addForm.matchPlanQty = null
      this.addDialog.addForm.readonly = false
      this.$refs['addFormRef'].resetFields()
      this.$forceUpdate()
    }
  }
}
</script>

<style scoped lang="scss">
.el-input--mini .el-input__inner {
  height: 40px;
}
</style>
