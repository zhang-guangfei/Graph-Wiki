<template>
  <div class="app-container">
    <el-card>
      <el-form ref="form" :inline="true" :model="form" size="small" label-width="100px">
        <el-form-item label="" class="formitem" style="margin-left  : 8%;">
          <el-select v-model="form.ownerCode" placeholder="请选择" style="width:125px" size="small">
            <el-option v-for="item in ownerCodeOptions" :key="item.id" :value="item.id" :label="item.typeName" />
          </el-select>
        </el-form-item>
        <el-form-item class="formitem">
          <el-card class="formcard" >
            待接订单<br>
            <el-form-item label="">
              <el-link :underline="true">{{ form.preProce }}</el-link>
            </el-form-item>
          </el-card>
        </el-form-item>
        <el-form-item class="formitem">
          <el-card class="formcard">
            处理中订单<br>
            <el-form-item label="">
              <el-link>{{ form.preProce }}</el-link>
            </el-form-item>
          </el-card>
        </el-form-item>
        <el-form-item>
          <el-card class="formcard">
            暂不处理 <br>
            <el-form-item label="">
              <el-link>{{ form.preProce }}</el-link>
            </el-form-item>
          </el-card>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.updatetime" size="small" style="width:100px"/>
          <!-- <el-button size="small" type="primary" @click="findList">刷新</el-button> -->
        </el-form-item>
      </el-form>
      <el-form ref="form" :inline="true" :model="form" size="small" label-width="100px">
        <el-form-item>
          <el-checkbox v-model="checked">子订单</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="findList">刷新</el-button>
        </el-form-item>
        <el-form-item class="formitem">
          <el-button size="small" type="primary" @click="findList">接入订单</el-button>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="findList">自动接单处理</el-button>
        </el-form-item>
        <el-form-item class="formitem">
          <el-button size="small" type="primary" @click="findList">出在库</el-button>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="findList">拆分数量</el-button>
        </el-form-item>
        <el-form-item class="formitem">
          <el-button size="small" type="primary" @click="findList">拆分型号</el-button>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" style="margin-right: 1%" @click="findList">还原处理</el-button>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="findList">暂不处理</el-button>
        </el-form-item>
        <el-form-item label="刷选订单号">
          <el-input v-model="form.qryOrderNo" size="small" style="width:120px"/>
          <!-- <el-button size="small" type="primary" @click="findList">刷新</el-button> -->
        </el-form-item>
      </el-form>
    </el-card>
    <div style="width:100%;padding-top:0px;float:left;">
      <el-table
        ref="roleTable"
        :data="orderlist"
        :row-style="rowStyle"
        element-loading-text="Loading"
        size="small"
        border
        fit
        @selection-change="handleSelectionChange">
        <el-table-column
          text="选择"
          type="selection"
          width="40"
          align="center"/>
        <el-table-column label="订单号" min-width="210" align="center">
          <template slot-scope="scope">
            {{ scope.row.orderNo }}
          </template>
        </el-table-column>
        <el-table-column label="型号" min-width="210" align="center">
          <template slot-scope="scope">
            {{ scope.row.modelNo }}
          </template>
        </el-table-column>
        <el-table-column label="数量" min-width="210" align="center">
          <template slot-scope="scope">
            {{ scope.row.quanty }}
          </template>
        </el-table-column>
        <el-table-column label="客户交货期" min-width="210" align="center">
          <template slot-scope="scope">
            {{ scope.row.time }}
          </template>
        </el-table-column>
        <el-table-column label="BJ在库" min-width="210" align="center">
          <template slot-scope="scope">
            {{ scope.row.bjQty }}
          </template>
        </el-table-column>
        <el-table-column label="SH在库" min-width="210" align="center">
          <template slot-scope="scope">
            {{ scope.row.shQty }}
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
export default {
  name: 'OrderProcess',
  components: { Pagination },
  data() {
    return {
      ownerCodeOptions: [{
        id: 1,
        typeName: '北分'
      },
      {
        id: 2,
        typeName: '广分'
      }],
      form: {
        ownerCode: '北分',
        preProce: '1007',
        updatetime: '更新(09:35)',
        qryOrderNo: '',
        name: ''
      },
      orderlist: [{
        orderNo: 'HA00325',
        modelNo: 'KHNHG-HI6T8',
        quanty: 8,
        time: '2021-09-26',
        shQty: 78,
        bjQty: 12
      },
      {
        orderNo: 'HA00325',
        modelNo: 'KHNHG-HI6T8',
        quanty: 8,
        time: '2021-09-26',
        shQty: 78,
        bjQty: 12
      },
      {
        orderNo: 'HA00325',
        modelNo: 'KHNHG-HI6T8',
        quanty: 8,
        time: '2021-09-26',
        shQty: 78,
        bjQty: 12
      }]
    }
  },
  created() {
  },
  methods: {
  }
}
</script>
<style scoped>
.line{
  text-align: center;
  margin-left: 5%;
}
.formitem{
    margin-right: 6%;
}
.formcard{
width:140px;
height:120px;
text-align: center
}
</style>

