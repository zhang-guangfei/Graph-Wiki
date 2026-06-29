<template>
  <form @submit.prevent="submitOrder">
    <input v-model="form.customerId" name="customerId" />
    <input v-model="form.skuId" name="skuId" />
    <input v-model.number="form.quantity" name="quantity" />
    <button type="submit">Create Order</button>
  </form>
</template>

<script>
import { createOrder, previewOrder } from '../../api/order'

export default {
  name: 'CreateOrder',
  data() {
    return { form: { customerId: '', skuId: '', quantity: 1 } }
  },
  methods: {
    async submitOrder() {
      await createOrder(this.form)
    },
    async previewStock() {
      return previewOrder(this.form)
    }
  }
}
</script>
