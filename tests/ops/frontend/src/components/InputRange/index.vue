<template>
  <div class="input-range-class" style="width: 220px">
    <el-form ref="rangeForm" :model="rangeForm" :inline="true" size="small">
      <el-form-item prop="min">
        <el-input
          v-model="rangeForm.min"
          :max="rangeForm.max"
          :clearable="false"
          type="number"
          size="mini"
          placeholder="最小值"
          round
          style="width: 102px !important"
          @change="getMin">
          <!-- <template v-if="unit === 0.01" slot="append" style="padding:0 5px">%</template>
          <template v-else-if="unit === 10000" slot="append" style="padding:0 5px">万</template> -->
        </el-input>
      </el-form-item>
      <span style="width:6px !important">
        -
      </span>
      <el-form-item prop="max">
        <el-input
          v-model="rangeForm.max"
          :min="rangeForm.min"
          :clearable="false"
          type="number"
          size="mini"
          placeholder="最大值"
          round
          style="width: 102px !important;margin-left: 2px"
          @change="getMax">
          <!-- <template v-if="unit === 0.01" slot="append" style="padding:0 5px">%</template>
          <template v-else-if="unit === 10000" slot="append" style="padding:0 5px">万</template> -->
        </el-input>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  name: 'PmInputRange',
  model: {
    prop: 'rangeForm',
    event: 'input'
  },
  props: {
    unit: {
      type: Number,
      default: 1
    },
    rangeForm: {
      type: Object,
      default: function() {
        return {
          min: Number,
          max: Number
        }
      }
    }
  },
  data() {
    return {
      min: null,
      max: null,
      data: {
        min: null,
        max: null
      }
    }
  },
  created() {
  },
  methods: {
    displayMin() {
      return this.min / this.unit
    },
    getMin(val) {
      this.min = null
      if (val) {
        this.min = Number(val) * Number(this.unit)
      }
      this.data = { min: this.min, max: this.max }
    },
    getMax(val) {
      this.max = null
      if (val) {
        this.max = Number(val) * Number(this.unit)
      }
      this.data = { min: this.min, max: this.max }
    },
    resetFields() {
      this.$refs.rangeForm.resetFields()
    }
  }
}
</script>
<style scoped lang="scss">
</style>
