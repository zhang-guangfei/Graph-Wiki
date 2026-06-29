<template>
  <div/>
</template>
<script>
export default {
  name: 'SsoLogin',
  data() {
    return {}
  },
  created() {
    this.handleSsoLogin()
  },
  methods: {
    handleSsoLogin() {
      // 获取地址栏中的code C14717 bugid：14930  2024/8/14
      const usernumber = this.$route.query.usernumber
      const redirectTo = this.$route.query.redirectTo
      const token = this.$route.query.token
      if (usernumber === '' || usernumber === undefined || usernumber === null || redirectTo === '' || redirectTo === undefined || redirectTo === null || token === '' || token === undefined || token === null) {
        console.log('登录失败')
        this.$router.push('/').catch(() => { })
      } else {
        const decodedString = atob(redirectTo)
        const loginInfo = {
          'usernumber': usernumber,
          'redirectTo': redirectTo,
          'token': token
        }
        console.log('正在登录')
        this.$store.dispatch('SsoLogin', loginInfo).then(() => {
          this.$router.push({ path: decodedString || '/' })
        }).catch((err) => {
          console.log('登录', err)
          this.$router.push('/')
        })
      }
    }
  }
}
</script>
