<template>
  <el-dialog :visible.sync="instructVisable" class="dialog-div" title="营业日报批示">
    <el-card class="card" style="margin-top:20px;">
      <el-row :gutter="20">
        <el-col :span="8">
          <div>
            <el-form label-position="left" class="demo-table-expand">
              <el-form-item style="margin-bottom:0px;" label="编号:">
                {{ list.b }}
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <el-col :span="8">
          <div>
            <el-form label-position="left">
              <el-form-item style="margin-bottom:0px;" label="姓名:">
                {{ list.c }}
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <el-col :span="8">
          <div>
            <el-form label-position="left">
              <el-form-item style="margin-bottom:0px;" label="时间:">
                {{ list.d }}
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
      <hr size="5" color="#7590A8">
      <el-row :gutter="20">
        <el-col :span="24">
          <div>
            <el-form label-position="left">
              <el-form-item style="margin-bottom:0px;" label="holon批示:">
                {{ instructList.a }}
              </el-form-item>
              <el-form-item style="margin-bottom:0px;" label="所长批示:">
                {{ instructList.b }}
              </el-form-item>
              <el-form-item style="margin-bottom:0px;" label="区域长批示:">
                {{ instructList.c }}
              </el-form-item>
              <el-form-item style="margin-bottom:0px;" label="市况需否:">
                <el-select v-model="value" multiple style="width:500px;" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"/>
                </el-select>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
      <hr size="5" color="#7590A8">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form label-position="left" >
            <el-form-item style="margin-bottom:0px;" label="您的批示:">
              <el-input
                :autosize="{ minRows: 2, maxRows: 4}"
                v-model="textarea"
                style="width:500px"
                type="textarea"
                placeholder="请输入您的批示"/>
            </el-form-item>
            <el-form-item style="margin-bottom:0px;">
              <el-checkbox v-model="checked">是否回复</el-checkbox>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-card>
    <div slot="footer" style="text-align: center;">
      <el-button size="small" @click="instructVisable = false">取消</el-button>
      <el-button size="small" type="primary" @click="updateSubmit()">提交</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name: 'Instruct',
  // eslint-disable-next-line space-before-blocks
  data(){
    return {
      list: {
        a: '郑州营业所',
        b: '4240270',
        c: '吕雅楠',
        d: '2019-05-05'
      },
      remarkList: {
        remark: '好好工作，天天向上'
      },
      options: [{
        value: '郑州捷锐砂轮机械设备有限公司',
        label: '郑州捷锐砂轮机械设备有限公司'
      }, {
        value: '郑州马寨精诚挂面机有限公司',
        label: '郑州马寨精诚挂面机有限公司'
      }, {
        value: '郑州郑飞科技有限责任公司',
        label: '郑州郑飞科技有限责任公司'
      }],
      value: '',
      textarea: '',
      checked: false,
      instructList: {
        a: '努力干活',
        b: '努力挣钱',
        c: '努力生活',
        d: '天天向上'
      }
    }
  }
}
</script>

