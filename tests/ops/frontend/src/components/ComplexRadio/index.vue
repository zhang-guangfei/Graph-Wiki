<template>
  <div class="complex-radio-body">
    <el-radio-group v-model="radioValue" @change="getValue">
      <el-radio v-for="item in options" :label="item.code" :key="item.code" style="width:100%;margin: 5px 5px">
        <!-- <span v-html="item.label"/> -->
        <!-- {{ item.value }} -->
        <span v-if="multiple === false">
          <span v-for="(v, index) in item.inputArray" :key="index">
            <!-- <span v-html="v"/> -->
            {{ v }}
            <span v-if="index >= item.inputArray.length - 1"/>
            <span v-else>
              <el-input-number
                v-if="type === 'number'"
                v-model="optionsValue[item.code]"
                :min="0"
                :max="100"
                :controls="false"
                :precision="2"
                :disabled="disabled"
                :style="inputClass"
                size="mini"
                style="width: 60px;height: 20px"
                @input="changeValue(item.code)"/>
              <el-input
                v-else
                v-model="optionsValue[item.code]"
                :type="type"
                :disabled="disabled"
                :style="inputClass"
                size="mini"
                @input="changeValue(item.code)"/>
            </span>
          </span>
        </span>
        <span v-else>
          <span v-for="(v, index) in item.indexInputArray" :key="index">
            <!-- {{ v.value }} -->
            <span v-html="v.label"/>
            <span v-if="index >= item.indexInputArray.length - 1"/>
            <span v-else>
              <el-input-number
                v-if="item.indexInputArray[index].type === 'number'"
                v-model="optionsValue[item.code][index]"
                :min="item.indexInputArray[index].min"
                :max="item.indexInputArray[index].max"
                :controls="false"
                :precision="item.indexInputArray[index].precision"
                :style="!item.indexInputArray[index].inputClass ? '': item.indexInputArray[index].inputClass"
                size="mini"
                @input="changeValue(item.code)"/>
              <el-input
                v-else
                v-model="optionsValue[item.code][index]"
                :disabled="item.indexInputArray[index].disabled"
                :style="!item.indexInputArray[index].inputClass ? '': item.indexInputArray[index].inputClass"
                :type="item.indexInputArray[index].type"
                size="mini"
                @input="changeValue(item.code)"/>
            </span>
          </span>
        </span>
      </el-radio>
    </el-radio-group>
  </div>
</template>

<script>
export default {
  name: 'ComplexRedio',
  model: {
    prop: 'value',
    event: 'input'
  },
  props: {
    value: {
      type: Object,
      default: function() {
        return {}
      }
    },
    separator: {
      type: String,
      default: null
    },
    multiple: {
      type: Boolean,
      default: false // 多种输入框类型的为true才能使用
    },
    type: {
      type: String,
      default: 'text'
    },
    multiType: {
      type: Array,
      default: function() {
        // 多种类型： [{ key: '&',type: 'number'}, { key: '#',type: 'text'}]
        return []
      }
    },
    readonly: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    },
    options: {
      type: Array,
      default: function() {
        return []
      }
    },
    optionKey: {
      type: String,
      default: 'key'
    },
    inputClass: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      radioValue: null,
      optionsValue: {}
    }
  },
  watch: {
    options: {
      handler(val) {
        if (val && val.length > 0) {
          this.initOptions()
        }
      }
    },
    multiType: {
      handler(val) {
        if (val) {
          this.initOptions()
        }
      },
      deep: true
    },
    value: {
      handler(val) {
        if (val) {
          this.radioValue = val.key
          this.optionsValue[val.key] = val.value
        }
      },
      deep: true
    }
  },
  created() {
    this.initOptions()
  },
  methods: {
    changeValue(code) {
      this.$forceUpdate()
      var a = this.optionsValue[this.radioValue]
      if (code === this.radioValue) {
        this.$emit('input', { key: this.radioValue, value: a })
        this.$emit('change', { key: this.radioValue, value: a })
      }
    },
    getValue(value) {
      var a = this.optionsValue[value]
      this.$emit('input', { key: this.radioValue, value: a })
      this.$emit('change', { key: this.radioValue, value: a })
    },
    findIndex(array, index) {
      if (!array || array.length === 0) {
        array = []
        return null
      }
      var indexObj = array.find(f => f.value === index)
      if (!indexObj) {
        return null
      }
      return this.multiType.find(f => f.key === indexObj.key)
    },
    initOptions() {
      if (!this.optionsValue || Object.keys(this.optionsValue).length === 0) {
        this.optionsValue = {}
      }
      this.options.map(m => {
        m.inputArray = m.value.split(this.separator)
        if (this.multiple === false) {
          if (!this.optionsValue.hasOwnProperty(m[this.optionKey])) {
            this.optionsValue[m[this.optionKey]] = null
          }
          return m
        }
        // m.inputArray = []
        var separator = ''
        if (this.multiType && this.multiType.length > 0) {
          this.multiType.forEach(f => {
            separator = separator + f.key
          })
          separator = '[' + separator + ']'
        }
        var regExp = new RegExp(separator, 'g')
        var array = m.value.split(new RegExp(regExp, 'g'))
        m.indexInputArray = [] // $符号的位置
        var b = { index: array.length - 1, label: array[array.length - 1] }
        m.indexInputArray.push(b)
        if (!this.optionsValue.hasOwnProperty(m[this.optionKey])) {
          this.optionsValue[m[this.optionKey]] = []
        }
        if (array.length > 1) {
          this.multiType.forEach(e => {
            var ind = m.value.indexOf(e.key)
            while (ind > -1) {
              var index = m.value.substring(0, ind).split(regExp).length - 1
              var a = { key: e.key, index: index, label: array[index] }
              a = Object.assign(a, e)
              m.indexInputArray.push(a)
              ind = m.value.indexOf(e.key, ind + 1)
            }
          })
          m.indexInputArray = m.indexInputArray.sort((n1, n2) => {
            return n1.index - n2.index
          })
          m.indexInputArray.forEach((f, index) => {
            if (index < m.indexInputArray.length - 1) {
              if (!this.optionsValue[m[this.optionKey]][index] ||
                (f.hasOwnProperty('disabled') && f.disabled === true)) {
                this.optionsValue[m[this.optionKey]][index] = ''
                if (f.hasOwnProperty('value')) {
                  this.optionsValue[m[this.optionKey]][index] = f.value
                }
              }
            }
          })
          // this.optionsValue
          // this.$forceUpdate()
        }
        return m
      })
      this.$forceUpdate()
    }
  }
}
</script>
