const scopeOptions = [
  {
    value: '部',
    label: '部',
    children: [{
      value: '武汉',
      label: '武汉'
    }, {
      value: '北京',
      label: '北京'
    }, {
      value: '上海',
      label: '上海'
    }, {
      value: '江苏',
      label: '江苏'
    }, {
      value: '山东',
      label: '山东'
    }, {
      value: '浙江',
      label: '浙江'
    }, {
      value: '南京',
      label: '南京'
    }]
  }, {
    value: '所',
    label: '所',
    children: [{
      value: '合肥营业所',
      label: '合肥营业所'
    }]
  }, {
    value: 'holon',
    label: 'holon',
    children: [{
      value: 'HFFP',
      label: 'HFFP'
    }, {
      value: 'HFholon1',
      label: 'HFholon1'
    }, {
      value: 'HFholon2',
      label: 'HFholon2'
    }, {
      value: 'HFholon3',
      label: 'HFholon3'
    }, {
      value: 'HFholon4',
      label: 'HFholon4'
    }, {
      value: 'HFholon45',
      label: 'HFholon45'
    }, {
      value: 'HFholon456',
      label: 'HFholon456'
    }]
  }
]
const contentTypeOptions = [
  {
    value: '需求',
    label: '需求'
  }, {
    value: '供给',
    label: '供给'
  }, {
    value: 'MS-P产品',
    label: 'MS-P产品'
  }, {
    value: '竞争',
    label: '竞争'
  }, {
    value: '项目',
    label: '项目'
  }, {
    value: '故障品',
    label: '故障品'
  }, {
    value: '贩促',
    label: '贩促'
  }, {
    value: '代销',
    label: '代销'
  }, {
    value: '课题产品',
    label: '课题产品'
  }, {
    value: '新产品',
    label: '新产品'
  }, {
    value: '两通阀',
    label: '两通阀'
  }, {
    value: '真空元件',
    label: '真空元件'
  }, {
    value: '节能提案',
    label: '节能提案'
  }, {
    value: '冷干机',
    label: '冷干机'
  }, {
    value: '其他',
    label: '其他'
  }
]
const industryOptions = [
  {
    value: 'CP',
    label: 'CP'
  }, {
    value: 'MP',
    label: 'MP'
  }, {
    value: 'FP',
    label: 'FP'
  }, {
    value: 'DP',
    label: 'DP'
  }, {
    value: 'SP',
    label: 'SP'
  }, {
    value: 'BF',
    label: 'BF'
  }, {
    value: 'AP',
    label: 'AP'
  }, {
    value: 'HP',
    label: 'HP'
  }, {
    value: 'IP',
    label: 'IP'
  }, {
    value: 'PP',
    label: 'PP'
  }, {
    value: 'RP',
    label: 'RP'
  }, {
    value: 'KP',
    label: 'KP'
  }, {
    value: 'TP',
    label: 'TP'
  }, {
    value: 'IC',
    label: 'IC'
  }, {
    value: 'LSP',
    label: 'LSP'
  }, {
    value: '其他',
    label: '其他'
  }
]

const marketList = [
  {
    number: 123456198012,
    username: '蒋方舟',
    department: '陕西营业所',
    title: '陕西佳鑫实业有限公司客户情况汇报',
    date: '2019-05-09',
    contentType: '需求',
    industry: 'CP',
    status: '所长已确认'
  }, {
    number: 223456198012,
    username: '吕雅楠',
    department: '郑州营业所',
    title: '科慧科技经营恶化情况汇报',
    date: '2019-05-07',
    contentType: '需求',
    industry: 'CP',
    status: '所长已确认'
  }, {
    number: 323456198012,
    username: '何滔',
    department: '重庆营业所',
    title: '富升承接川维气源改造项目说明',
    date: '2019-05-07',
    contentType: '需求',
    industry: 'CP',
    status: '所长已确认'
  }, {
    number: 423456198012,
    username: '吕雅楠',
    department: '郑州营业所',
    title: '科慧科技经营恶化情况汇报',
    date: '2019-05-07',
    contentType: '需求',
    industry: 'CP',
    status: '所长已确认'
  }
]

const marketDetail = {
  number: 1234561980,
  username: '蒋方舟',
  department: '陕西营业所',
  title: '陕西佳鑫实业有限公司客户情况汇报',
  date: '2019-05-09',
  contentType: '节能提案',
  score: 0,
  agent: 'jdfh',
  industry: '其他',
  sendDepartment: ['HOLON', '区域长', '所长'],
  status: '所长已确认',
  subject: '关于精益猛讯电缸推进的市况报告',
  customer1: 'B4556Q',
  customer2: 'B4556J',
  customer3: 'B4556H',
  background: '廊坊开发区精益猛迅自动化技术有限公司是集自动化设备设计、制造、销售、服务为一体的专业公司,拥有齐全的技术方案,设计人才和各方面的技术工程实力,可以提供整体性的服务:包括系统分析和规划,机械设计,制造和安装,电控的设计和测试',
  currentSituation: '推进背景：廊坊精益气动需求约200W，电缸需求约100W，气动我司占有率约70%，电缸迟迟没能取得突破，目前客户使用上银模组、松下电机自行组装推进难点：客户设计机台为贴膜机且多为搭接使用，负载较大，对空间和精度要求很高，我司LEFS普通型电缸空间和定位精度可以满足，但是当负载加大时滑块的偏移量过大，影响精度，LEJ系列刚性可以满足但是体积过大，一直无法完成替换   切入点：1、了解到电缸搭接时对刚性要求较大，单独使用时并未有过高的要求，客户正在设计的玻璃上料机应用电缸40条，货期要求20天，客户承诺货期满足会选SMC，考虑近期电缸制造压力过大，经与与日本制造反复确认，一直未给明确答复，无法承诺客户，与客户协商提前告知项目信息，为我司争取备库时间2、同时为客户推荐不带电机的模组，客户希望订购长行程模组，根据自己所需进行裁切，自行解决机械连接部分，客户表示有过相关经验，不用担心无法使用的问题，与我司技术联络，不建议如此操作，将利害关系阐明，客户自行决定',
  countermeasure: '1.根据客户常用机型适当备库，缩短纳期2、确认无电机方案，如果可行，进行报价',
  schedule: '客户定期拜访。',
  matter: '',
  replier: '',
  replyDate: '2017-4-29',
  postil: [
    {
      id: 1,
      position: '所长',
      flag: 0,
      postilContent: '这个项目应该更清楚地说明，客户上下对此信心不足，希望强压样品。目前还是沟通客户自己采购，样品的作用还是使客户对不熟悉的产品加强信心，实现既定功能，而非成本分担的方式。',
      postilDate: '2019-4-15',
      replyScore: 2,
      postilName: '吴刚',
      reply: [{
        position: '员工',
        flag: 1,
        replyContent: '好的',
        replyDate: '2019-4-15',
        replyScore: 2,
        replyName: '某某'
      }]
    }, {
      id: 2,
      position: '区域长',
      flag: 0,
      postilContent: '何时供货生产样机？',
      postilDate: '2019-4-15',
      replyScore: 2,
      postilName: '朱昊琛',
      reply: [{
        position: '员工',
        flag: 1,
        replyContent: '还需要30天',
        replyDate: '2019-4-15',
        replyScore: 2,
        replyName: '某某'
      }]
    }
  ]
}

const usernameOptions = [
  {
    operatorId: 'B74718',
    operator: '任红洋'
  },
  {
    operatorId: 'A12345',
    operator: '吴洪旭'
  },
  {
    operatorId: 'B82559',
    operator: '张沈博'
  },
  {
    operatorId: 'A02345',
    operator: '周彤'
  },
  {
    operatorId: 'A92345',
    operator: '王忠杰'
  },
  {
    operatorId: 'A12366',
    operator: '业务处理中心'
  },
  {
    operatorId: 'A13345',
    operator: '蔡学晶'
  },
  {
    operatorId: 'A47345',
    operator: '王维'
  },
  {
    operatorId: 'A42345',
    operator: '李鹏博'
  },
  {
    operatorId: 'A14345',
    operator: '刘静云'
  },
  {
    operatorId: 'A17445',
    operator: '田建丰'
  },
  {
    operatorId: 'A17345',
    operator: '孙国丰'
  },
  {
    operatorId: 'A12355',
    operator: '刘好玉'
  },
  {
    operatorId: 'A12545',
    operator: '王超'
  },
  {
    operatorId: 'A15345',
    operator: '杨雅哲'
  },
  {
    operatorId: 'A5345',
    operator: '陈文锐'
  }
]

const customerCodesOptions = [
  { userName: '成都铭群科技有限公司',
    value: 'B3WR3'
  },
  { userName: '迈克医疗电子有限公司',
    value: 'B567D'
  },
  { userName: '成都华远电器设备有限公司',
    value: 'A6815'
  },
  { userName: '成都2',
    value: 'B3WR2'
  },
  { userName: '成都3',
    value: 'B3W23'
  },
  { userName: '成都4',
    value: 'B3WR4'
  },
  { userName: '成都5',
    value: 'B3WR5'
  },
  { userName: '成都6',
    value: 'B3WR6'
  }
]
const statusOptions = [
  {
    value: '未回复',
    label: '未回复'
  }, {
    value: '已回复',
    label: '已回复'
  }
]
const agentStatusOptions = [
  {
    value: 0,
    label: '未确认'
  }, {
    value: 1,
    label: '已确认'
  }
]

export default {
  getScopeOptions: config => {
    return scopeOptions
  },
  getContentTypeOptions: config => {
    return contentTypeOptions
  },
  getIndustryOptions: config => {
    return industryOptions
  },
  getAllMarketList: config => {
    return marketList
  },
  getUsernameOptions: config => {
    return usernameOptions
  },
  getMarketDetail: config => {
    return marketDetail
  },
  getStatusOptions: config => {
    return statusOptions
  },
  getAgentStatusOptions: config => {
    return agentStatusOptions
  },
  getCustomerCodesOptions: config => {
    return customerCodesOptions
  }
}
