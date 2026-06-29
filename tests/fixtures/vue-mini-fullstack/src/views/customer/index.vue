<script>
import { approveCustomer, searchCustomers } from '../../api/customer'

export default {
  methods: {
    approveCustomer() {
      return approveCustomer(this.form)
    },
    searchCustomerList() {
      return searchCustomers(this.query)
    },
    resetForm() {
      this.form = {}
    }
  }
}
</script>
