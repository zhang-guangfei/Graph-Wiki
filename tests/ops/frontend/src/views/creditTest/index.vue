<template>
  <div id="iframeContent" class="iframe-content-class">
    <el-divider style="margin: 5px 0 !important"/>
    <iframe
      id="iframe3S"
      ref="senTable"
      :src="urlCss"
      sandbox="allow-scripts allow-forms allow-same-origin"
      frameborder="0"
      height="1000px"
      left="0"
      right="0"
      top="0"
      bottom="0"
      width="100%"
      scrolling="auto"
      style="width:100%;height:100%;" />
  </div>
</template>
<script>
import elementResizeDetectorMaker from 'element-resize-detector'

export default {
  name: 'CreditTest',
  components: {
  },
  data() {
    return {
      condition: {
        modelNo: ''
      },
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
      urlCss: '',
      aurlCss: '',
      userId: this.$store.getters.info.userId,
      type: this.$store.getters.info.type,
      modelList: [],
      checkListVisible: false
    }
  },
  mounted() {
    const _this = this
    const erd = elementResizeDetectorMaker()
    erd.listenTo(document.getElementById('iframeContent'), (element) => {
      _this.$nextTick(() => {
        console.log('offsetWidth' + element.offsetWidth)
        console.log('offsetHeight' + element.offsetHeight)
        _this.autoResizeIframe(element.offsetWidth)
      })
    })
  },
  created() {
    this.aurlCss = 'http://10.116.1.216:6699/'
    this.urlCss = '/credit/login_sso?' +
                  'usernumber=YWRtaW4mMjAyNA==' +
                  '&redirectTo=L2ludmVzdGlnYXRpb25PblNpdGUvYXBwbHlJbXBsYW50' +
                  '&redirectToType=1' +
                  '&token=eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjU3NTFkYjhjLWFmYTQtNDBjNC1hNWVjLTJhOGQ5OWQyOTQ1NiJ9.Nhx6QFudg5lGc9f1Kw6l19W5Ij-NBfyMTa3DCs1qQ0RPWggcIdY8dxncUHDsDzi_2dS9w1xvWfht8jyCdxeM4A'
  },
  methods: {
    autoResizeIframe(width) {
      /**
         * iframe-宽高自适应显示
         */
      const oIframe = document.getElementById('iframe3S')
      const deviceHeight = document.documentElement.clientHeight
      console.log('deviceHeight:' + deviceHeight)
      var height = Number(deviceHeight)
      console.log('height:' + height)
      oIframe.style.width = (width + 18) + 'px' // 数字是页面布局宽度差值
      oIframe.style.height = (height - 30) + 'px' // 数字是页面布局高度差
    },
    handleNodeClick() {
      console.log()
    },
    cancel() {
      this.checkListVisible = false
    },
    confirm() {
      this.checkListVisible = false
    },
    addCheckList() {
      this.$refs.checkRef.checkListVisible = true
    }
  }
}
</script>

<style scoped lang="scss">
  .iframe-content-class {
    overflow-y: hidden;
    overflow-x: hidden;
    .search-title-content {
      padding: 5px 15px 0px 15px;
      margin-top: 5px;
      text-align: right;
      height:auto;
    }
    .el-divider--horizontal {
      margin: 5px 0;
    }
  }
</style>
