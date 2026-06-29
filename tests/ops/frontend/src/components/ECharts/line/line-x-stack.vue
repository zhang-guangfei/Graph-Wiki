<template>
  <div
    ref="lineXStack"
    :id="domId"/>
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: 'LineXStack',
  props: {
    store: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      chartsInfo: {},
      seriesData: [],
      isDone: false
    }
  },
  computed: {
    domId() {
      return 'line_x_stack_' + (Math.random() * 90000000000).toFixed(0)
    }
  },
  watch: {
    store: {
      handler(newVal, oldVal) {
        this.initChartsInfo()
      },
      deep: true
    }
  },
  created() {
    this.$nextTick(() => {
      this.checkApply()
      window.addEventListener('scroll', this.checkApply, true)
      window.addEventListener('resize', () => {
        if (this.isDone) {
          this.chartsInfo.resize()
        }
      })
    })
  },
  methods: {
    /**
     * @description 检测图表是否需要生成
     */
    checkApply() {
      if (!this.isDone && this.store.showCharts(this.$refs.lineXStack)) {
        this.isDone = true
        this.initChartsInfo()
      }
    },
    /**
     * @description 初始化图表DOM信息
     */
    initChartsInfo() {
      // this.chartsInfo = window.$eCharts.init(document.getElementById(this.domId))
      this.chartsInfo = echarts.init(document.getElementById(this.domId))
      this.dataFormat()
      this.initChartsDataInfo()
    },
    /**
     * @description 图表数据处理
     */
    dataFormat() {
      this.seriesData = []
      const flag = this.store.getYDataType === 'object'
      if (flag) {
        this.store.getYData.map(item => {
          this.seriesData.push({
            name: item[this.store.getDataLabel],
            type: 'line',
            areaStyle: {},
            stack: this.store.getStackLabel,
            data: item[this.store.getDataKey]
          })
        })
      }
    },
    /**
     * @description 初始化图表的数据信息
     */
    initChartsDataInfo() {
      const _this = this
      _this.chartsInfo.setOption({
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar'] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        title: _this.store.getTitleInfo,
        tooltip: _this.store.getToolTip,
        legend: _this.store.getLegendConfig,
        grid: _this.store.getGridConfig,
        color: _this.store.getColorList,
        xAxis: _this.store.getAxisBoundaryGap,
        yAxis: _this.store.getAxisValue,
        series: _this.seriesData,
        animationEasing: 'elasticOut',
        animationDelayUpdate: function(idx) {
          return idx * 5
        }
      }, true)
    }
  }
}
</script>

<style lang='scss'>

</style>
