<template>
  <div class="app-container">
    <div class="filter-container">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="模型管理" name="first">
          <el-form ref="form" :inline="true" size="small">
            <el-form-item>
              <el-input
                v-model="condition.name"
                type="text"
                size="small"
                placeholder="请输入流程名称"
                class="search-input-class"
                round
                clearable/>
            </el-form-item>
            <el-form-item>
              <el-button size="small" type="primary" @click="findList">查询</el-button>
              <el-button size="small" type="primary" @click="attachedfileDialogVisible = true">部署</el-button>
              <el-button size="small" type="primary" @click="flowCharting">绘制流程图</el-button>
            </el-form-item>
          </el-form>
          <vxe-table
            :data="tableData"
            :loading="loading"
            border
            resizable
            show-overflow
            size="mini"
            align="left">
            <!-- <vxe-table-column type="seq" width="60" title="序号"/> -->
            <vxe-table-column field="id" min-width="200" title="流程定义ID">
              <!-- <template v-slot="{ row }">
                <a @click="seeCharting(row)">{{ row.id }}</a>
              </template> -->
            </vxe-table-column>
            <vxe-table-column field="key" width="260" title="key"/>
            <vxe-table-column field="name" min-width="200" title="名称"/>
            <vxe-table-column field="version" width="100" title="版本号"/>
            <vxe-table-column width="230" title="操作" fixed="right" style="text-align: center;">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" title="图片" circle @click="imgDetailEvent(scope.row)"><svg-icon icon-class="image" /></el-button>
                <el-button type="primary" size="mini" title="xml" circle @click="xmlDetailEvent(scope.row)"><svg-icon icon-class="xml" /></el-button>
                <el-button type="primary" size="mini" icon="el-icon-view" title="查看" circle @click="seeCharting(scope.row)"/>
                <el-button type="primary" size="mini" icon="el-icon-edit" title="编辑" circle @click="edittCharting(scope.row)"/>
                <el-button type="primary" size="mini" icon="el-icon-delete" title="删除" circle @click="removeRowEvent(scope.row)"/>
              </template>
            </vxe-table-column>
          </vxe-table>
          <vxe-pager
            :loading="loading"
            :current-page="page.pageNumber"
            :page-size="page.pageSize"
            :total="page.totalResult"
            :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
            border
            size="mini"
            @page-change="handlePageChange"/>
          <el-dialog
            :visible.sync="attachedfileDialogVisible"
            title="上传流程模型"
            width="30%"
            center>
            <!-- <span>需要注意的是内容是默认不居中的</span> -->
            <el-upload ref="upload" :multiple="true" :loading="loading" :auto-upload="false" :on-change="handleChange" :file-list="fileList" :http-request="uploadFile" accept=".zip" action="String" >
              <el-button slot="trigger" icon="el-icon-plus" size="medium" type="primary">添加附件</el-button>
              <div slot="tip" class="el-upload__tip">
                仅支持上传.zip压缩文件格式
              </div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
              <el-button @click="attachedfileDialogVisible = false">取 消</el-button>
              <el-button :disabled="isButtonDisabled" type="primary" @click="uploadFile()">确 定</el-button>
            </span>
          </el-dialog>
          <div>
            <el-img-viewer v-if="showFlag" :on-close="closeViewer" :url-list="[url]"/>
          </div>
          <!-- highlight_s是css样式，xmlData是xml数据。 -->
          <el-dialog :visible.sync="xmlDetailVisible" title="xml详情" @close="xmlDetailVisible = false">
            <!-- <code>{{ xmlData }}</code> -->
            <p>{{ xmlData }}</p>
          </el-dialog>
        </el-tab-pane>
        <el-tab-pane label="实例管理" name="second">
          <el-form :model="form" :inline="true" size="small">
            <el-form-item>
              <el-input v-model="form.processDefinitionKeys" placeholder="请输入实例名称" size="small" class="search-input-class"/>
            </el-form-item>
            <el-form-item>
              <el-button id="query" type="primary" size="small" class="filter-item" style="margin-bottom:3px" @click="findProcessInstance()">查询</el-button>
            </el-form-item>
          </el-form>
          <vxe-grid
            ref="xGrid"
            :loading="loading"
            :pager-config="tablePage1"
            :data="processInstanceData"
            border
            size="mini"
            show-overflow
            resizable
            show-header-overflow
            highlight-hover-row
            @page-change="handlePageChangeForProcessInstance">
            <vxe-table-column field="id" width="220" title="Id"/>
            <vxe-table-column field="name" width="100" title="Name"/>
            <vxe-table-column field="startDate" width="200" title="StartDate"/>
            <vxe-table-column field="initiator" width="150" title="Initiator"/>
            <vxe-table-column field="businessKey" min-width="250" title="BusinessKey"/>
            <vxe-table-column field="status" width="120" title="Status"/>
            <vxe-table-column field="processDefinitionId" min-width="250" title="ProcessDefinitionId"/>
            <vxe-table-column field="processDefinitionKey" width="80" title="ProcessDefinitionKey"/>
            <vxe-table-column field="parentId" min-width="200" title="ParentId"/>
            <vxe-table-column field="processDefinitionVersion" width="100" title="版本号"/>
            <vxe-table-column width="150" title="操作" fixed="right" style="text-align: center;">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" icon="el-icon-video-pause" title="中断" circle @click="suspendEvent(scope.row)"/>
                <el-button type="primary" size="mini" icon="el-icon-video-play" title="激活" circle @click="resumeEvent(scope.row)"/>
                <el-button type="primary" size="mini" icon="el-icon-info" title="获取参数" circle @click="getVariablesEvent(scope.row)"/>
              </template>
            </vxe-table-column>
          </vxe-grid>
          <div>
            <el-dialog :visible.sync="dialogProcessInstanceDetailVisible" title="进程详情" width="850px" @close="dialogProcessInstanceDetailVisible = false">
              <div style="margin-top:20px">
                <vxe-grid
                  :data="processInstanceDetailData"
                  :loading="loading"
                  show-overflow="title"
                  border
                  size="mini"
                  round
                  highlight-hover-row>
                  <vxe-table-column field="name" width="100px" title="姓名"/>
                  <vxe-table-column field="processInstanceId" min-width="300px" title="流程实例ID"/>
                  <vxe-table-column field="taskVariable" min-width="150px" title="任务参数"/>
                  <vxe-table-column field="type" min-width="100px" title="类型"/>
                  <vxe-table-column field="value" min-width="150px" title=" 参数值"/>
                </vxe-grid>
              </div>
            </el-dialog>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import { fetchList, deleteProcessDefinition, uploadFiles, detail, xmlDetail, queryProcessInstance, suspend, resume, getVariables } from '@/api/activiti'
import ElImgViewer from 'element-ui/packages/image/src/image-viewer'
export default {
  components: { ElImgViewer },
  data() {
    return {
      // 流程实例数据分页参数
      tablePage1: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        pageSizes: [10, 20, 50, 100, 200, 500],
        layouts: [
          'Sizes',
          'PrevJump',
          'PrevPage',
          'Number',
          'NextPage',
          'NextJump',
          'FullJump',
          'Total'
        ],
        perfect: true
      },
      form: {},
      activeName: 'first',
      xmlDetailVisible: false,
      url: '',
      xmlData: '',
      showFlag: false,
      fileList: [],
      isButtonDisabled: true,
      attachedfileDialogVisible: false,
      condition: {
        name: ''
      },
      conditionForProcessInstance: {},
      page: {
        pageNumber: 1,
        pageSize: 50,
        totalResult: 1
      },
      id: '',
      loading: false,
      tableData: [],
      processInstanceData: [],
      // variableInstanceData: [],
      processInstanceDetailData: [],
      dialogProcessInstanceDetailVisible: false
    }
  },
  created() {
    this.findList()
  },
  methods: {
    findList() {
      this.loading = true
      this.condition.pageNumber = this.page.pageNumber
      this.condition.pageSize = this.page.pageSize
      fetchList(this.condition).then(data => {
        this.tableData = data.content
        this.tablePage.total = data.totalItems
        this.loading = false
      }).catch(error => {
        this.loading = false
        console.log(error)
      })
    },
    findProcessInstance() {
      // this.loading = true
      // this.conditionForProcessInstance.pageNumber = this.page.pageNumber
      // this.conditionForProcessInstance.pageSize = this.page.pageSize
      this.loading = true
      this.form.pageNumber = this.tablePage1.currentPage - 1
      this.form.pageSize = this.tablePage1.pageSize
      const conditions = this.form
      queryProcessInstance(conditions).then(data => {
        this.processInstanceData = data.content.content
        this.tablePage1.total = data.content.totalItems
        this.loading = false
      }).catch(error => {
        this.loading = false
        console.log(error)
      })
    },
    // tab页面切换事件
    handleClick(tab, event) {
      console.log(tab, event)
      if (tab.name === 'first') {
        this.findList()
      }
      if (tab.name === 'second') {
        this.findProcessInstance()
      }
    },
    handlePageChange({ currentPage, pageSize }) {
      this.form.pageSize = pageSize
      this.form.pageNumber = currentPage
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.findList()
    },
    // 流程实例分页功能
    handlePageChangeForProcessInstance({ currentPage, pageSize }) {
      this.tablePage1.currentPage = currentPage
      this.tablePage1.pageSize = pageSize
      this.findProcessInstance()
    },
    searchEvent() {
      this.form.pageNumber = 1
      this.tablePage.currentPage = 1
      this.findList()
    },
    // 上传文件判断
    handleChange(file, fileList) {
      this.fileList = fileList
      if (this.fileList !== null) {
        this.isButtonDisabled = false
      }
    },
    // 手动上传
    uploadFile() {
      const formData = new FormData()
      this.fileList.forEach(element => {
        if (element.status !== 'success') {
          formData.append('reportFile', element.raw, element.raw.name)
        }
      })
      // if (formData !== null) {
      //   console.log(formData)
      //   return
      // }
      uploadFiles(formData).then(data => {
        this.fileList.forEach(element => {
          element.status = 'success'
        })
        this.findList()
        this.attachedfileDialogVisible = false
        this.$notify({
          title: '成功',
          message: '发布成功！',
          type: 'success'
        })
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '发布失败！'
        })
      })
    },
    // 删除流程
    removeRowEvent(row) {
      this.$confirm('此操作将永久删除该流程, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        var params = {
          processDefinitionId: row.id,
          bool: false
        }
        deleteProcessDefinition(params).then((response) => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.loading = false
          this.findList()
        }).catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '删除失败'
          })
        })
      }).catch(() => {
      })
    },
    // 图片详情
    imgDetailEvent(row) {
      var params = {
        processDefinitionId: row.id,
        bool: true
      }
      detail(params).then((res) => {
        this.url = 'data:image/png;base64,' + btoa(new Uint8Array(res).reduce((res, byte) => res + String.fromCharCode(byte), ''))
        this.showFlag = true
      }).catch((e) => {
        this.loading = false
      })
    },
    // 关闭图片查看器
    closeViewer() {
      this.showFlag = false
    },
    // xml文件详情
    xmlDetailEvent(row) {
      var xmlParams = {
        processDefinitionId: row.id,
        bool: false
      }
      xmlDetail(xmlParams).then((res) => {
        this.xmlData = res
        this.xmlDetailVisible = true
      }).catch((e) => {
        this.loading = false
      })
    },
    // 中断
    suspendEvent(row) {
      this.$confirm('此操作将中断流程实例, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        var suspendParams = {
          processInstanceId: row.id
        }
        suspend(suspendParams).then((response) => {
          this.$notify({
            title: '成功',
            message: '中断成功',
            type: 'success',
            duration: 2000
          })
          this.loading = false
        }).catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '中断失败'
          })
        })
      })
    },
    // 激活
    resumeEvent(row) {
      this.$confirm('此操作将激活流程实例, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        var resumeParams = {
          processInstanceId: row.id
        }
        resume(resumeParams).then((response) => {
          this.$notify({
            title: '成功',
            message: '激活成功',
            type: 'success',
            duration: 2000
          })
          this.loading = false
        }).catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '激活失败'
          })
        })
      })
    },
    // 获取参数
    getVariablesEvent(row) {
      this.loading = true
      var getVariablesParams = {
        processInstanceId: row.id
      }
      getVariables(getVariablesParams).then(data => {
        this.dialogProcessInstanceDetailVisible = true
        this.processInstanceDetailData = data.content
        this.loading = false
      }).catch(error => {
        this.loading = false
        console.log(error)
      })
    },
    // 跳转到绘制流程图页面
    flowCharting() {
      this.$router.push({
        path: '/activiti/draw/modeler',
        query: {
          flag: 'add'
        }
      })
    },
    // 跳转到绘制流程图页面
    seeCharting(row) {
      var xmlParams = {
        processDefinitionId: row.id,
        bool: false
      }
      xmlDetail(xmlParams).then((res) => {
        const xmlData1 = res
        this.$router.push({
          path: '/activiti/draw/modeler',
          query: {
            bpmnXml: xmlData1,
            flag: 'watch'
          }
        })
      }).catch((e) => {
        console.log('失败')
      })
    },
    // 编辑流程图
    edittCharting(row) {
      var xmlParams = {
        processDefinitionId: row.id,
        bool: false
      }
      xmlDetail(xmlParams).then((res) => {
        const xmlData1 = res
        this.$router.push({
          path: '/activiti/draw/modeler',
          query: {
            bpmnXml: xmlData1,
            flag: 'edit'
          }
        })
      }).catch((e) => {
        console.log('失败')
      })
    }
  }
}
</script>

<style scoped lang="scss">
.product-consult-content{
  padding: 0px 15px;
}
.search-title-content {
  padding: 0 20px 0px 20px;
  text-align: left;
}
.search-input-class {
  width: 200px;
}
#product-detail {
  min-height: 500px;
}
.stock-content {
  min-height: 500px;
  border-radius: 4px;
  border: 1px solid #E4E7ED;
  padding: 10px;
}
.base-content {
  min-height: 500px;
  padding: 10px;
  border: 1px solid #E4E7ED;
  border-radius: 4px;
}
.el-divider--horizontal {
  margin: 5px 0;
}
.col-class {
  height: 20px;
}
.el-form-item__label {
  margin: 4px 0px;
}
.el-form-item {
  margin-bottom: 10px
}
.el-button--mini.is-round {
  padding: 5px 5px;
}

.el-button--medium.is-round {
  padding: 8px 8px;
}
.stock-content-body {
  margin-bottom: 100px;
}
</style>
