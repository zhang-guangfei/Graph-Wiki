<template>
  <div class="order-management-container">
    <!-- 查询条件区域 -->
    <div class="query-section">
      <div class="query-row">
        <div class="query-item">
          <label>准备订单号：</label>
          <vxe-input
            v-model="queryForm.prepareOrderNo"
            placeholder="请输入"
            size="small"
            clearable
          />
        </div>
        <div class="query-item">
          <label>型号：</label>
          <vxe-input
            v-model="queryForm.model"
            placeholder="请输入"
            size="small"
            clearable
          />
        </div>
        <div class="query-item">
          <label>供应商：</label>
          <vxe-select
            v-model="queryForm.supplier"
            placeholder="请选择"
            size="small"
            clearable
          >
            <vxe-option
              v-for="item in supplierOptions"
              :key="item.value"
              :value="item.value"
              :label="item.label"
            />
          </vxe-select>
        </div>
        <div class="query-item">
          <label>申请号：</label>
          <vxe-input
            v-model="queryForm.applyNo"
            placeholder="请输入"
            size="small"
            clearable
          />
        </div>
        <div class="query-item">
          <label>状态：</label>
          <vxe-select
            v-model="queryForm.status"
            placeholder="请选择"
            size="small"
            clearable
          >
            <vxe-option
              v-for="item in statusOptions"
              :key="item.value"
              :value="item.value"
              :label="item.label"
            />
          </vxe-select>
        </div>
      </div>

      <div class="action-row">
        <vxe-button type="primary" size="small" @click="handleQuery">
          查询
        </vxe-button>
        <vxe-button size="small" @click="handleExport">
          导出
        </vxe-button>
        <vxe-button type="primary" size="small" @click="handleCreate">
          新建
        </vxe-button>
      </div>
    </div>

    <!-- 主表格区域 -->
    <div class="main-table-section">
      <vxe-table
        ref="orderTable"
        :data="tableData"
        :row-config="{ height: 40 }"
        :header-row-class-name="'table-header-row'"
        :cell-class-name="getCellClassName"
        border
        show-overflow
        highlight-hover-row
        height="490"
      >
        <vxe-column type="seq" title="序号" width="60" align="center"/>
        <vxe-column field="prepareOrderNo" title="准备订单号" resizable width="140"/>
        <vxe-column field="model" title="型号" resizable width="100"/>
        <vxe-column field="quantity" title="数量" resizable width="80" align="center"/>
        <vxe-column field="supplier" title="供应商" resizable width="140"/>
        <vxe-column field="manufactureOrderNo" resizable title="制造接单号" width="140"/>
        <vxe-column field="applyNo" title="申请号" resizable width="120"/>
        <vxe-column field="applyQuantity" title="申请数量" resizable width="100" align="center"/>
        <vxe-column field="applyReason" title="申请理由" resizable width="120"/>
        <vxe-column field="status" title="状态" resizable width="100">
          <template #default="{ row }">
            <span :class="getStatusClass(row.status)">
              {{ row.status }}
            </span>
          </template>
        </vxe-column>
        <vxe-column field="processor" title="处理人" resizable width="120"/>
        <vxe-column field="processTime" title="处理时间" resizable width="140"/>
        <vxe-column field="processReply" title="处理回复" resizable width="180"/>
      </vxe-table>
    </div>

    <!-- 分页组件 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page-sizes="[20, 50, 100, 200, 500]"
      :page.sync="queryForm.pageNum"
      :limit.sync="queryForm.pageSize"
      @pagination="handleQuery"
    />

  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
export default {
  name: 'CancelPrepareOrder',
  components: { Pagination },
  data() {
    return {
      // 查询表单数据
      queryForm: {
        prepareOrderNo: '',
        model: '',
        supplier: '',
        applyNo: '',
        status: '',
        pageNum: 1,
        pageSize: 20
      },
      total: 1,
      // 供应商选项
      supplierOptions: [
        { value: 'SMC【中国工厂】', label: 'SMC【中国工厂】' },
        { value: 'SMC【北京工厂】', label: 'SMC【北京工厂】' },
        { value: 'SMC【上海工厂】', label: 'SMC【上海工厂】' }
      ],

      // 状态选项
      statusOptions: [
        { value: '待处理', label: '待处理' },
        { value: '已取消', label: '已取消' },
        { value: '已处理', label: '已处理' }
      ],

      // 分页配置
      page: {
        currentPage: 1,
        pageSize: 10,
        total: 35 // 模拟总数据量
      },

      // 表格数据
      tableData: []
    }
  },
  created() {
    // 初始化数据
    this.loadTableData()
  },
  methods: {
    // 加载表格数据
    loadTableData() {
      // 模拟数据
      const mockData = [
        {
          id: 1,
          prepareOrderNo: '770000001-1',
          model: 'ABC',
          quantity: 500,
          supplier: 'SMC【中国工厂】',
          manufactureOrderNo: 'YY770000001-1',
          applyNo: 'ZBSD10-1',
          applyQuantity: 400,
          applyReason: '客户取消',
          status: '待处理',
          processor: '',
          processTime: '',
          processReply: ''
        },
        {
          id: 2,
          prepareOrderNo: '770000001-2',
          model: 'ABC',
          quantity: 500,
          supplier: 'SMC【中国工厂】',
          manufactureOrderNo: 'YY770000001-2',
          applyNo: 'ZBSD10-2',
          applyQuantity: 200,
          applyReason: '客户取消',
          status: '已取消',
          processor: 'C56【张三】',
          processTime: '2025/7/10',
          processReply: '营业主动取消'
        },
        {
          id: 3,
          prepareOrderNo: '770000001-3',
          model: 'ABC',
          quantity: 500,
          supplier: 'SMC【中国工厂】',
          manufactureOrderNo: 'YY770000001-3',
          applyNo: 'ZBSD10-3',
          applyQuantity: 500,
          applyReason: '客户取消',
          status: '已取消',
          processor: 'C56【张三】',
          processTime: '2025/7/10',
          processReply: '不允许取消'
        }
      ]

      // 模拟更多数据
      const allData = [...mockData]
      for (let i = 4; i <= 35; i++) {
        const statusIndex = i % 3
        let status
        switch (statusIndex) {
          case 0:
            status = '待处理'
            break
          case 1:
            status = '已取消'
            break
          case 2:
            status = '已处理'
            break
        }

        allData.push({
          id: i,
          prepareOrderNo: `770000001-${i}`,
          model: `MODEL${i % 5}`,
          quantity: 100 + (i % 10) * 50,
          supplier: 'SMC【中国工厂】',
          manufactureOrderNo: `YY770000001-${i}`,
          applyNo: `ZBSD10-${i}`,
          applyQuantity: 50 + (i % 8) * 50,
          applyReason: i % 3 === 0 ? '客户取消' : (i % 3 === 1 ? '计划变更' : '库存调整'),
          status: status,
          processor: status === '待处理' ? '' : `C${50 + i % 10}【用户${i}】`,
          processTime: status === '待处理' ? '' : `2025/7/${10 + i % 20}`,
          processReply: status === '待处理' ? '' : (i % 2 === 0 ? '处理完成' : '已批准')
        })
      }

      // 应用查询条件
      let filteredData = allData
      if (this.queryForm.prepareOrderNo) {
        filteredData = filteredData.filter(item =>
          item.prepareOrderNo.includes(this.queryForm.prepareOrderNo)
        )
      }
      if (this.queryForm.model) {
        filteredData = filteredData.filter(item =>
          item.model.includes(this.queryForm.model)
        )
      }
      if (this.queryForm.supplier) {
        filteredData = filteredData.filter(item =>
          item.supplier === this.queryForm.supplier
        )
      }
      if (this.queryForm.applyNo) {
        filteredData = filteredData.filter(item =>
          item.applyNo.includes(this.queryForm.applyNo)
        )
      }
      if (this.queryForm.status) {
        filteredData = filteredData.filter(item =>
          item.status === this.queryForm.status
        )
      }

      // 更新分页总数
      this.page.total = filteredData.length

      // 分页处理
      const startIndex = (this.page.currentPage - 1) * this.page.pageSize
      const endIndex = startIndex + this.page.pageSize
      this.tableData = filteredData.slice(startIndex, endIndex)
    },

    // 查询
    handleQuery() {
      this.page.currentPage = 1
      this.loadTableData()
    },

    // 导出
    handleExport() {
      this.$XModal.alert({
        title: '提示',
        content: '导出功能待实现',
        status: 'info'
      })
    },

    // 新建
    handleCreate() {
      this.$XModal.alert({
        title: '提示',
        content: '新建功能待实现',
        status: 'info'
      })
    },

    // 编辑
    handleEdit(row) {
      this.$XModal.alert({
        title: '提示',
        content: `编辑订单：${row.prepareOrderNo}`,
        status: 'info'
      })
    },

    // 取消
    handleCancel(row) {
      this.$XModal.confirm({
        title: '确认',
        content: `确定要取消订单 ${row.prepareOrderNo} 吗？`,
        status: 'warning'
      }).then(() => {
        // 这里实际调用取消接口
        this.$XModal.message({ content: '取消成功', status: 'success' })
        this.loadTableData()
      })
    },

    // 处理
    handleProcess(row) {
      this.$XModal.alert({
        title: '提示',
        content: `处理订单：${row.prepareOrderNo}`,
        status: 'info'
      })
    },

    // 分页变化
    handlePageChange({ currentPage, pageSize }) {
      this.page.currentPage = currentPage
      this.page.pageSize = pageSize
      this.loadTableData()
    },

    // 获取状态样式
    getStatusClass(status) {
      const statusMap = {
        '待处理': 'status-pending',
        '已取消': 'status-cancelled',
        '已处理': 'status-processed'
      }
      return statusMap[status] || ''
    },

    // 获取单元格样式
    getCellClassName({ row, column }) {
      if (column.property === 'status') {
        return this.getStatusClass(row.status)
      }
      return ''
    }
  }
}
</script>

<style scoped>
.order-management-container {
  padding: 20px;
  background-color: #fff;
  font-family: 'Microsoft YaHei', Arial, sans-serif;
}

.query-section {
  background-color: #f8f9fa;
  padding: 16px;
  border-radius: 4px;
  margin-bottom: 16px;
  border: 1px solid #e4e7ed;
}

.query-row {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 12px;
}

.query-item {
  display: flex;
  align-items: center;
  margin-right: 20px;
  margin-bottom: 10px;
}

.query-item label {
  min-width: 80px;
  margin-right: 8px;
  text-align: right;
  font-size: 14px;
  color: #606266;
}

.action-row {
  display: flex;
  align-items: center;
}

.action-row .vxe-button {
  color: #fff;
  margin-right: 10px;
  background-color: #337ab7;
}

.main-table-section {
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  overflow: hidden;
}

.pagination-section {
  padding: 12px;
  background-color: #f8f9fa;
  border-top: 1px solid #e4e7ed;
  display: flex;
  justify-content: flex-end;
}

.action-buttons {
  display: flex;
  gap: 8px;
}

.action-buttons .vxe-button {
  padding: 4px 8px;
}

/* 状态样式 */
.status-pending {
  color: #e6a23c;
  background-color: #fdf6ec;
  padding: 2px 8px;
  border-radius: 3px;
  font-weight: 500;
}

.status-cancelled {
  color: #f56c6c;
  background-color: #fef0f0;
  padding: 2px 8px;
  border-radius: 3px;
  font-weight: 500;
}

.status-processed {
  color: #67c23a;
  background-color: #f0f9eb;
  padding: 2px 8px;
  border-radius: 3px;
  font-weight: 500;
}

/* 表格样式优化 */
:deep(.table-header-row) {
  background-color: #f5f7fa !important;
  font-weight: 600;
  color: #303133;
}

:deep(.vxe-table--render-wrapper .vxe-table--body) {
  font-size: 13px;
}

:deep(.vxe-table--render-wrapper .vxe-body--row) {
  height: 40px !important;
}

:deep(.vxe-table--render-wrapper .vxe-body--column) {
  padding: 8px 10px !important;
}

/* 响应式调整 */
@media (max-width: 1400px) {
  .query-item {
    margin-right: 15px;
  }

  .query-item label {
    min-width: 70px;
  }
}
</style>
