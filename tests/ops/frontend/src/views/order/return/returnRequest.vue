<template>
  <div class="maindiv">
    <div class="divs top">
      <el-row class="btu">
        <el-button plain icon="el-icon-plus" @click="resetForm()">新增</el-button>
        <el-button plain style="margin-left: 35px" icon="el-icon-check">保存</el-button>
        <el-button plain style="margin-left: 35px" icon="el-icon-finished" @click="commitForm('returnDetail')">提交</el-button>
        <el-button plain style="margin-left: 35px" icon="el-icon-refresh-left">返回</el-button>
      </el-row>
      <p class="tit">退货申请</p>
      <p class="baseinfo">基本信息</p>
    </div>
    <div class="divs content">
      <hr class="h">
      <el-form ref="form_baseInfo" :model="form_baseInfo" label-width="100px">
        <el-form-item label="原因说明" prop="reason">
          <el-input v-model="form_baseInfo.reason" type="textarea" placeholder="请输入原因说明" @input="isSaveData"/>
        </el-form-item>
        <el-row align="middle" type="flex">
          <el-col :span="8">
            <el-form-item label="联系人" prop="username">
              <el-input v-model="form_baseInfo.username" placeholder="联系人" style="width:200px" @input="isSaveData" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="联系电话" prop="userphone">
              <el-input v-model="form_baseInfo.userphone" placeholder="联系电话" style="width:200px" @input="isSaveData" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="divs return_detail">
      <hr class="h2">
      <p class="returninfo">退货明细</p>
      <div class="tabbtu">
        <table style="margin-bottom: 10px;" border="2">
          <thead>
            <tr>
              <th style="line-height:20px"><el-button type="primary" icon="el-icon-circle-plus-outline" @click="addRow()" /></th>
              <th><el-button type="primary" icon="el-icon-remove-outline" @click="removeRow()"/></th>
              <th><el-button type="primary" icon="el-icon-chat-line-round" @click="BatchAddRow()" /></th>
              <th><el-button type="primary" icon="el-icon-edit" /></th>
              <th><el-button type="primary" icon="el-icon-edit" /></th>
            </tr>
          </thead>
        </table>
      </div>
      <el-form ref="returnDetail" :model="tableDataForm" label-width="100px">
        <el-table
          :data="tableDataForm.tableDataFormList"
          :row-class-name="tableRowClassName"
          :cell-style="{padding:'0px'}"
          border
          style="width: 100%"
          @selection-change="handleSelectionChange">

          <el-table-column
            type="selection"
            width="55"/>

          <el-table-column
            label="订单号"
            align="center"
            width="200">
            <template slot="header">
              <div>
                订单号
                <span style="color:red;font-size:16px;">*</span>
              </div>
            </template>
            <template slot-scope="scope">
              <el-form-item :prop="'tableDataFormList.'+scope.$index+'.orderNo'" :rules="rules.orderNo" class="in_input">
                <el-input v-model="scope.row.orderNo" placeholder="请输入订单号" @input="isSaveData"/>
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column
            label="型号"
            align="center"
            width="200">
            <template slot="header">
              <div>
                型号
                <span style="color:red;font-size:16px;">*</span>
              </div>
            </template>
            <template slot-scope="scope">
              <el-form-item :prop="'tableDataFormList.'+scope.$index+'.modelNo'" :rules="rules.modelNo" class="in_input">
                <el-input v-model="scope.row.modelNo" placeholder="请输入型号" @input="isSaveData"/>
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column
            label="总退货数量"
            align="center"
            width="200">
            <template slot-scope="scope">
              <el-input v-model="scope.row.totalReturnCount" placeholder="请输入总退货数量" @input="isSaveData"/>
            </template>
          </el-table-column>

          <el-table-column
            align="center"
            label="不良品数量"
            width="200">
            <template slot-scope="scope">
              <el-input v-model="scope.row.badCount" placeholder="请输入不良品数量" @input="isSaveData"/>
            </template>
          </el-table-column>

          <el-table-column
            align="center"
            label="退货原因">
            <template slot-scope="scope">
              <el-input id="tt" v-model="scope.row.returnReason" type="textarea" placeholder="请输入退货原因" @input="isSaveData" />
            </template>
          </el-table-column>

          <el-table-column
            align="center"
            label="退货责任"
            width="200" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.returnResponsibility" placeholder="请输入退货责任" @input="isSaveData"/>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <!--dialog-->
      <el-dialog :visible.sync="dialogTableVisible" :close-on-click-modal="false" title="退货明细添加" >
        <el-table
          :data="[{}]"
          style="width: 100%">
          <el-table-column
            label="订单号"
            width="130" />
          <el-table-column
            label="型号"
            width="130" />
          <el-table-column
            label="总退货数量"
            width="100" />
          <el-table-column
            label="不良品数量"
            width="100" />
        </el-table>
        <el-input v-model="dialogTestData" type="textarea" placeholder="请输入您要批量添加的数据..."/>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogTableVisible = false;submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReturnOrderApply',
  data() {
    return {
      // 退货基本信息
      form_baseInfo: {
        reason: '', // 原因说明
        username: '', // 联系人
        userphone: '' // 联系电话
      },
      // 退货明细
      tableDataForm: {
        tableDataFormList: [{
          orderNo: '', // 订单号
          modelNo: '', // 型号
          totalReturnCount: null, // 总退货数量
          badCount: null, // 不良品数量
          returnReason: '', // 退货原因
          returnResponsibility: '' // 退货责任
        }]
      },
      multipleSelection: [],
      dialogTableVisible: false, // dialog标记 默认不打开
      dialogTestData: '', // dialog 弹框文本域输入的文本值
      diaLogValArray: [],
      diaLogValArrayItem: [],
      rules: {
        orderNo: [{ required: true, message: '订单号不能为空', trigger: 'blur' }],
        modelNo: [{ required: true, message: '型号不能为空', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    // 暂存表单数据 当提交成功时 清空
    if (localStorage.getItem('form_baseInfo') == null || localStorage.getItem('tableDataForm') == null) {
      localStorage.setItem('form_baseInfo', JSON.stringify(this.form_baseInfo))
      localStorage.setItem('tableDataForm', JSON.stringify(this.tableDataForm))
    }
    this.form_baseInfo = JSON.parse(localStorage.getItem('form_baseInfo'))
    this.tableDataForm = JSON.parse(localStorage.getItem('tableDataForm'))
  },
  methods: {
    /** 当选项发送改变时触发该方法 */
    handleSelectionChange(val) {
      this.multipleSelection = val
      // console.log('val ->', val)
    },
    tableRowClassName(row, index) {
      // 给每条数据添加一个索引  在删除时可以对应唯一标识
      row.row.index = row.rowIndex
    },
    // 删除行
    removeRow() {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 遍历获得多选选中的index值
          this.multipleSelection.forEach((value, index) => {
            // 遍历多选框获取的数据
            console.log('val -->', value)
            console.log('val index -->', value.index)
            this.tableDataForm.tableDataFormList.forEach((v, i) => {
              // 遍历数据表，若索引值相同，则说明该数据被选中，其i则为索引值
              if (value.index === v.index) {
                console.log('v-->', v)
                console.log('v index -->', v.index)
                this.tableDataForm.tableDataFormList.splice(i, 1)
              }
            })
          })
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          console.log('删除成功')
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    /** 添加行 */
    addRow() {
      this.tableDataForm.tableDataFormList.push({
        orderNo: '',
        modelNo: '',
        totalReturnCount: null,
        badCount: null,
        returnReason: '',
        returnResponsibility: ''
      })
    },
    /** 批量添加 */
    BatchAddRow() {
      this.dialogTableVisible = true
    },
    /** 监听表单 */
    isSaveData() {
      localStorage.setItem('form_baseInfo', JSON.stringify(this.form_baseInfo))
      localStorage.setItem('tableDataForm', JSON.stringify(this.tableDataForm))
    },
    /** 重置表单 */
    resetForm() {
      this.form_baseInfo = {
        reason: '', // 原因说明
        username: '', // 联系人
        userphone: '' // 联系电话
      }
      this.tableDataForm = {
        tableDataFormList: [{
          orderNo: '', // 订单号
          modelNo: '', // 型号
          totalReturnCount: null, // 总退货数量
          badCount: null, // 不良品数量
          returnReason: '', // 退货原因
          returnResponsibility: '' // 退货责任
        }]
      }
      this.clearCache()
    },
    // 提交
    commitForm(returnDetail) {
      this.$refs[returnDetail].validate(valid => {
        if (valid) {
          alert('commit success! ')
          this.resetForm()
          this.clearCache()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    submit() {
      console.log(this.dialogTestData) // 得到文本域的字符串
      console.log('***** ', this.dialogTestData.replace(/\r\n/g, '~').replace(/\n/g, '~').split('~')) // 转换为数组
      this.diaLogValArray = this.dialogTestData.replace(/\r\n/g, '~').replace(/\n/g, '~').split('~')
      for (let i = 0; i < this.diaLogValArray.length; i++) { // 遍历数组
        this.diaLogValArrayItem.push(this.diaLogValArray[i].replace(/\r\t/g, '^').replace(/\t/g, '^').split('^'))
        // console.log('---  ', this.diaLogValArray[i].replace(/\r\t/g, '^').replace(/\t/g, '^').split('^'))
        this.tableDataForm.tableDataFormList.push({ // 追加进退货明细
          orderNo: this.diaLogValArrayItem[i][0],
          modelNo: this.diaLogValArrayItem[i][1],
          totalReturnCount: this.diaLogValArrayItem[i][2],
          badCount: this.diaLogValArrayItem[i][3],
          returnReason: '',
          returnResponsibility: ''
        })
      }
      // console.log('a-> ', this.diaLogValArrayItem)
    },
    /** 清空缓存 */
    clearCache() {
      const form_baseInfo = {
        reason: '', // 原因说明
        username: '', // 联系人
        userphone: '' // 联系电话
      }
      const tableDataForm = {
        tableDataFormList: [{
          orderNo: '', // 订单号
          modelNo: '', // 型号
          totalReturnCount: null, // 总退货数量
          badCount: null, // 不良品数量
          returnReason: '', // 退货原因
          returnResponsibility: '' // 退货责任
        }]
      }
      localStorage.setItem('form_baseInfo', JSON.stringify(form_baseInfo))
      localStorage.setItem('tableDataForm', JSON.stringify(tableDataForm))
    }
  }
}
</script>
<style scoped>
.tit{
    margin-top: -44px;
    padding-left: 655px;
    font-size: xx-large;
    font-family: cursive;
 }
.top{
   border: 1px solid rgb(0, 118, 189);
   height: 50px;
 }
.divs{
    width: 95%;
    border-radius: 12px;
    margin-left: 43px;
    margin-top: 15px;
 }
.btu{
    position: relative;
    margin-top: 5px;
    margin-left: 50px;
 }
.btu .el-button {
    padding: 6px;
    margin-top: 12px;
    border: 1px solid #0076bd;
}

.btu .el-button:hover{
    color: #fff;
    background-color: #0076BD;
  }

.el-button {
    padding: 8px;
    border: 1px solid #0076bd;
}

 .baseinfo{
   margin-top: -15px;
   padding-left: 12px;
   font-weight: 600;
 }
 .content{
   position: absolute;
   margin-top: 50px;
 }

 .h{
    position: relative;
    margin-top: 5px;
    border-bottom: 2px solid rgb(0, 118, 189);
 }
 .h2{
    position: relative;
    margin-top: 250px;
    border-bottom: 2px solid rgb(0, 118, 189);
 }
 .returninfo{
   position: absolute;
   font-weight: 600;
   padding-left: 12px;
   margin-top: -40px
 }

 .tabbtu{
   margin-left: 1240px;
   margin-top: 10px;
}

.in_input{
  margin-left: -105px;
  margin-top: 15px;
}

.el-textarea__inner{
    height: 75px;
    width: 96%;
}
</style>
