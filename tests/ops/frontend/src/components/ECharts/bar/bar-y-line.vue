<template>
  <div
    ref="barYLine"
    :id="domId"/>
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: 'BarYLine',
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
      return 'bar_y_line_' + (Math.random() * 90000000000).toFixed(0)
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
      if (!this.isDone && this.store.showCharts(this.$refs.barYLine)) {
        this.isDone = true
        this.initChartsInfo()
      }
    },
    /**
     * @description 初始化图表信息
     */
    initChartsInfo() {
      // this.chartsInfo = window.$eCharts.init(document.getElementById(this.domId))
      this.chartsInfo = echarts.init(document.getElementById(this.domId))
      this.dataFormat()
    },
    /**
     * @description 图表数据重组
     */
    dataFormat() {
      this.seriesData = []
      this.store.getXData.map(item => {
        this.seriesData.push({
          name: this.store.getDataLabel ? item[this.store.getDataLabel] : item,
          type: 'bar',
          data: this.store.getDataKey ? item[this.store.getDataKey] : item,
          barMaxWidth: '20px',
          itemStyle: this.store.getYBarStyle
        })
      })
      this.seriesData.push(this.store.getYLineData)
      this.initChartsDataInfo()
    },
    /**
     * @description 图表绘制与数据显示
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
        legend: _this.store.getLineLegend,
        yAxis: _this.store.getAxisConfig,
        xAxis: _this.store.getLineAxisValue,
        grid: _this.store.getGridConfig,
        color: _this.store.getColorList,
        series: _this.seriesData,
        animationEasing: 'elasticOut',
        animationDelayUpdate: function(idx) {
          return idx * 5
        }
      })
    }
  }
}
</script>

<style lang="scss">

</style>
