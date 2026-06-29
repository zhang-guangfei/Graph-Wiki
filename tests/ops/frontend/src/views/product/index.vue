<template>
  <div class="product-search-content">
    <div v-if="productSearch" class="search-title-content">
      <el-form ref="form" :inline="true" size="mini">
        <el-form-item>
          <el-autocomplete
            v-model.trim="condition.modelNo"
            :fetch-suggestions="querySearchAsync"
            :debounce="0"
            type="text"
            size="small"
            placeholder="请输入型号"
            class="search-input-class"
            prefix-icon="el-icon-search"
            round
            highlight-first-item
            select-when-unmatched
            clearable
            @select="handleSelect">
            <template slot-scope="{ item }">
              <div class="name">{{ item.modelno }}</div>
            </template>
          </el-autocomplete>
          <el-button v-permission="['335936']" :disabled="!condition.modelNo" type="primary" size="small" @click="productDetail">查询</el-button>
          <!-- <el-button :disabled="!condition.modelNo || checkListDisabled === true" type="primary" size="small" style="margin-left:0px" @click="addCheckList">加入待交易清单</el-button> -->
        </el-form-item>
      </el-form>
    </div>
    <div id="product-detail-content">
      <product-detail ref="productDetail" @change="getProductStatus"/>
    </div>
    <!-- <check-list ref="checkRef"/> -->
  </div>
</template>

<script>
import detail from './detail'
import { Loading } from 'element-ui'
// import checkList from './checkList'
import { findList } from '@/api/product'

export default {
  name: 'ProductSearch',
  inject: ['reload'],
  components: {
    // 'check-list': checkList,
    'product-detail': detail
  },
  data() {
    return {
      productSearch: true,
      inventoryVisible: true,
      priceVisible: true,
      baseInfoVisible: true,
      inventDetailVisible: false,
      shikomiTipDisabled: true,
      condition: {
        modelNo: '',
        oldModelNo: ''
      },
      product: {
        eosInfo: { isEos: false },
        canShikomi: true
      },
      productArray: [],
      shikomiNoList: [],
      consultVisible: false,
      checkListLoading: false,
      page: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 1
      },
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      checkListDisabled: true,
      agreementPriceList: [],
      agreePriceNodeList: [],
      ePriceList: [],
      noDataText: '无数据',
      noDataRequire: '请至少输入三个字符',
      noDataTextDefault: '无数据',
      userListloading: false,
      userId: this.$store.getters.info.userId,
      customerCodesOptions: [],
      modelList: [],
      inventoryList: [],
      checkListVisible: false,
      productDetailStatus: false,
      consultType: null
    }
  },
  watch: {
    'condition.modelNo': {
      handler(val, oldV) {
        if (val) {
          this.condition.modelNo = val.toUpperCase()
        }
      },
      deep: true
    }
  },
  created() {
    this.loadAllProduct()
    var lett = this
    document.onkeydown = function(e) {
      var key = window.event.keyCode
      if (key === 13) {
        lett.productDetail()
      }
    }
  },
  mounted() {
  },
  methods: {
    getProductStatus(status) {
      if (!status) {
        this.checkListDisabled = true
      } else {
        this.checkListDisabled = false
      }
    },
    productDetail() {
      var modelNo = this.condition.modelNo
      const loadingInstance = Loading.service({ fullscreen: false, text: '加载中', target: document.querySelector('#product-detail-content') })
      this.$refs.productDetail.condition.modelNo = modelNo
      console.log(this.$refs)
      this.$refs.productDetail.productDetail().then(() => {
        this.$nextTick(() => {
          loadingInstance.close()
        })
      })
      setTimeout(() => {
      }, 1500)
    },
    loadAllProduct() {
      var modelNo = this.condition.modelNo
      if (!modelNo) {
        modelNo = null
      }
      this.productArray = []
      findList(modelNo).then(data => {
        if (data && data.data) {
          this.productArray = data.data
        }
      })
    },
    productSearchChange() {
      this.productSearch = false
    },
    handleCommand(item) {
      this.product.shikomiNo = item
      this.$forceUpdate()
    },
    querySearchAsync(modelNo, cb) {
      if (!modelNo) {
        modelNo = null
      }
      findList(modelNo).then(data => {
        var productArray = []
        var results = []
        if (data && data.data) {
          productArray = data.data
          results = modelNo ? productArray.filter(this.createStateFilter(modelNo)) : productArray
        }
        this.productArray = productArray
        cb(results)
      })
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.modelno.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    handleSelect(item) {
      this.condition.modelNo = item.modelno
      this.productDetail()
    },
    addCheckList() {
      this.$refs.checkRef.checkListVisible = true
    }
  }
}
</script>

<style scoped lang="scss">
.product-search-content{
  height: 100%;
  .search-title-content {
    padding: 10px 15px 0px 15px;
    text-align: center;
    .search-input-class {
      width: 300px;
    }
    .el-form-item--mini.el-form-item {
      margin-bottom: 5px;
    }
  }
}
#product-detail-content {
  min-height: 500px;
  .bg-purple-light {
    background: #e5e9f2;
    padding: 0 10px;
  }
  .el-dropdown-link {
    color: #337AB7;
    cursor: pointer;
  }
  .collapse-title-class {
    color: #337AB7;
    cursor: pointer;
    font-weight: bold;
  }
  .el-input--mini .el-input__inner {
    padding: 0 5px !important;
  }
  .base-content {
    height: auto;
    padding: 10px;
    border: 1px solid #E4E7ED;
    border-radius: 4px;
  }
  .invent-col-class {
    margin-right: 20px;
    font-size: 14px;
  }
  .stock-content {
    height: 100%;
    min-height: 500px;
    border-radius: 4px;
    border: 1px solid #E4E7ED;
    padding: 10px;
    .el-col {
      padding: 10px 0;
      .el-tree {
        height: 250px;
        overflow-y: auto;
        font-size: 8px !important;
        overflow-x: auto;
        .el-tree-node .el-tree-node__children {
          overflow: inherit;
        }
      }
    }
  }
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  justify-content: space-between;
  font-size: 8px !important;
  padding-right: 8px;
}
.el-divider--horizontal {
  margin: 5px 0;
}
.col-class {
  height: 20px;
  padding: 0px;
  width: 240px;
}
.el-form-item__label {
  font-size: 12px;
  min-width: 80px;
  max-width: 100px;
  padding: 0px !important;
}
.el-button--mini.is-round {
  padding: 5px 5px;
}
.node-tree-class {
  height: 250px;
}

</style>
