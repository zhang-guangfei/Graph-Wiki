<script>
import { createOrder, loadOrders } from '../../api/order'

export default {
  methods: {
    submitOrder() {
      return createOrder(this.form)
    },
    loadOrderList() {
      return loadOrders(this.query)
    },
    formatDate(value) {
      return value
    }
  }
}
</script>
