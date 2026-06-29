import html2Canvas from 'html2canvas'
import JsPDF from 'jspdf'
export default{
  install(Vue, options) {
    Vue.prototype.getPdf = function() {
      var title = this.htmlTitle
      html2Canvas(document.querySelector('#pdfDom'), {
        allowTaint: true
      }).then(function(canvas) {
        const contentWidth = canvas.width
        const contentHeight = canvas.height

        // <----- 横向A4 -----> <--------------------------------->
        // 一页pdf显示html页面生成的canvas高度
        const pageHeight = contentWidth / 841.89 * 592.28
        // 未生成pdf的html页面高度 592.28 * 841.89
        let leftHeight = contentHeight
        // 页面偏移
        let position = 0
        // a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
        const imgWidth = 841.89
        const imgHeight = 841.89 / contentWidth * contentHeight
        const pageData = canvas.toDataURL('image/jpeg', 1.0)
        // l:横向， p：纵向(改变需要改变)
        const PDF = new JsPDF('l', 'pt', 'A4')

        // <----- 纵向A4 -----> <--------------------------------->
        // const pageHeight = contentWidth / 592.28 * 841.89
        // // 未生成pdf的html页面高度 592.28 * 841.89
        // let leftHeight = contentHeight
        // // 页面偏移
        // let position = 0
        // // a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
        // const imgWidth = 592.28
        // const imgHeight = 592.28 / contentWidth * contentHeight
        // const pageData = canvas.toDataURL('image/jpeg', 1.0)
        // // l:横向， p：纵向(改变需要改变)
        // const PDF = new JsPDF('p', 'pt', 'A4')

        // 有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
        // 当内容未超过pdf一页显示的范围，无需分页
        if (leftHeight < pageHeight) {
          PDF.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight)
        } else {
          while (leftHeight > 0) {
            PDF.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
            leftHeight -= pageHeight
            position -= 592.28
            // 避免添加空白页
            if (leftHeight > 0) {
              PDF.addPage()
            }
          }
        }
        PDF.save(title + '.pdf')
      }
      )
    }
  }
}
