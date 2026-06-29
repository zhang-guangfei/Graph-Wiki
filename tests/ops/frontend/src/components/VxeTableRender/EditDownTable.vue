<template>
  <div class="edit-down-table">
    <vxe-pulldown ref="xDown" class="edit-down-pulldown" transfer>
      <template>
        <vxe-input v-model="row[column.property]" class="edit-down-input" suffix-icon="fa fa-caret-down" size="mini" @keyup="keyupEvent" @click="clickEvent" @suffix-click="suffixClick"/>
      </template>
      <template v-slot:dropdown>
        <div class="edit-down-wrapper">
          <!-- <vxe-grid
            :loading="loading"
            :pager-config="tablePage"
            :data="tableData"
            :columns="tableColumn"
            highlight-hover-row
            auto-resize
            height="auto"
            @cell-click="selectEvent"
            @page-change="pageChangeEvent"/> -->
          <vxe-grid
            :loading="loading"
            :data="tableData"
            :columns="tableColumn"
            height="auto"
            border
            resizable
            auto-resize
            highlight-current-row
            highlight-hover-row
            show-overflow
            footer-align="center"
            stripe
            size="mini"
            @cell-click="selectEvent"/>
        </div>
      </template>
    </vxe-pulldown>
  </div>
</template>

<script>
import { findCompetitorModelMapping } from '@/api/saleManageSystem/product'
export default {
  name: 'EditDownTable',
  props: {
    // eslint-disable-next-line vue/require-default-prop
    params: Object
  },
  data() {
    return {
      row: null,
      column: null,
      loading: false,
      tableData: [],
      tableColumn: [
        { type: 'seq', width: 45 },
        { field: 'competitorId', title: '竞争对手ID', width: 100 },
        { field: 'competitor', title: '竞争对手', width: 100 },
        { field: 'competitorModelNo', title: '竞争型号', width: 150 },
        { field: 'quantity', title: '竞争型号数量', width: 120 },
        { field: 'competitorRMBPrice', title: '竞争价格' },
        { field: 'methodId', title: '替换方式' }
      ],
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      const { row, column } = this.params
      this.row = row
      this.column = column
      const condition = { modelNo: row.modelNo }
      // if (column.property === 'competitor') {
      //   condition.competitorId = row[column.property]
      // }
      if (column.property === 'competitorModelNo') {
        condition.competitorModelNo = row[column.property]
        condition.competitorId = row.competitorId.toUpperCase()
      }
      findCompetitorModelMapping(condition).then((competitorData) => {
        if (competitorData && competitorData.content) {
          this.tableData = competitorData.content
        }
      }).catch((e) => {
      })
    },
    clickEvent() {
      this.$refs.xDown.showPanel()
    },
    keyupEvent() {
      const { row, column } = this
      const cellValue = row[column.property]
      this.loading = true
      if (cellValue) {
        const condition = { modelNo: row.modelNo }
        if (column.property === 'competitor') {
          condition.competitorId = row[column.property]
        }
        if (column.property === 'competitorModelNo') {
          condition.competitorModelNo = row[column.property]
        }
        findCompetitorModelMapping(condition).then((competitorData) => {
          this.loading = false
          if (competitorData && competitorData.content) {
            this.tableData = competitorData.content
          }
          if (competitorData.content === undefined) {
            this.tableData = []
          }
        }).catch((e) => {
          this.loading = false
        })
      } else {
        this.tableData = []
      }
    },
    suffixClick() {
      this.$refs.xDown.togglePanel()
    },
    selectEvent(params) {
      const { row, column } = this
      row[column.property] = params.row[column.property]
      row['competitorModelNo'] = params.row['competitorModelNo']
      row['competitorPrice'] = params.row['competitorRMBPrice']
      this.$refs.xDown.hidePanel()
    }
  }
}
</script>

<style lang="scss" scoped>
.edit-down-pulldown {
  width: 100%;
}
.edit-down-wrapper {
  width: 800px;
  height: 300px;
  background-color: #fff;
  border: 1px solid #dcdfe6;
  box-shadow: 0 0 6px 2px rgba(0, 0, 0, 0.1);
}
</style>
