<script>
import { submitStockTransfer, queryStock } from '../../api/inventory'

export default {
  methods: {
    submitStockTransfer() {
      return submitStockTransfer(this.form)
    },
    queryStockBalance() {
      return queryStock(this.query)
    },
    calcTableHeight() {
      return 320
    }
  }
}
</script>
