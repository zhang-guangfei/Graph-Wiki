export function getpickerOptionPie(arrayX, titleText, singleText) {
  var arrayY = []
  arrayX.forEach(element => {
    arrayY.push(element.name)
  })
  return {
    title: {
      text: titleText,
      subtext: titleText,
      x: 'center'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      x: 'left',
      data: arrayY
    },
    toolbox: {
      show: true,
      feature: {
        mark: { show: true },
        dataView: { show: true, readOnly: false },
        magicType: {
          show: true,
          type: ['pie', 'funnel'],
          option: {
            funnel: {
              x: '25%',
              width: '50%',
              funnelAlign: 'left',
              max: 1548
            }
          }
        },
        restore: { show: true },
        saveAsImage: { show: true }
      }
    },
    calculable: true,
    series: [
      {
        name: singleText,
        type: 'pie',
        radius: '55%',
        center: ['50%', '60%'],
        data: arrayX
      }
    ]
  }
}
