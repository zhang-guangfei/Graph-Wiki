<template>
  <div class="login-container" style="background:#3B3B3B">
    <!-- <div class="Logo" style="position: absolute;z-index:3">
      <img class="logo" src="@/assets/img/smc_logo.png" style="margin-left:4.5vw;margin-top:4vh;float:left">
      <p style="float:left;margin-top:4.3vw;font-size:2.5vh;font-weight: 400;font-weight: bold;">(中国)有限公司</p>
    </div> -->
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" :inline-message="true" class="login-form" auto-complete="on" label-position="left">
      <div>
        <!-- <img class="logo" src="@/assets/img/smc_logo1.png"> -->
        <h3 class="title">{{ showTitle }}</h3>
      </div>
      <el-form-item prop="username" style="border-radius: 5px;">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input v-model="loginForm.username" name="username" size="small" type="text" auto-complete="on" placeholder="username" />
      </el-form-item>
      <el-form-item prop="password" style="border-radius: 5px;margin-bottom:0px">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :type="pwdType"
          v-model="loginForm.password"
          name="password"
          size="small"
          auto-complete="on"
          placeholder="password"/>
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>
      <el-form-item style="padding-bottom:1vh;" align="center">
        <el-button :loading="loading" style="width:82%;margin-top:2.5vh;background:#178FFF;color:white" @click.native.prevent="handleLogin">
          登录
        </el-button>
      </el-form-item>
    </el-form>
    <img src="@/assets/img/background_prod.png" height="100%" width="100%">
    <!-- <div style="margin:30px auto;text-align:center;color:white" width="100%" height="10%">
      Copyright © 2019 SMC(中国)有限公司 All rights reserved.
    </div> -->
  </div>
</template>

<script>
// import Bg from '@/../static/images/404.png'
export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('请输入正确的用户名'))
      } else {
        callback()
      }
    }
    const validatePass = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('密码不能小于5位'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '',
        password: ''
      },
      commitUserForm: {
        username: '',
        password: ''
      },
      encryptionCount: 3,
      // bg: Bg,
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePass }]
      },
      loading: false,
      pwdType: 'password',
      redirect: undefined,
      showTitle: 'SMC业务处理系统'
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.keyupSubmit()
    var currentUrl = window.location.href
    console.log('登录页面url=>', currentUrl)
    if (currentUrl.includes('localhost') || currentUrl.includes('opstest.smcit.cn')) {
      this.showTitle = 'SMC业务处理系统[测试环境]'
    } else if (currentUrl.includes('opsuat.smcit.cn')) {
      this.showTitle = 'SMC业务处理系统[UAT环境]'
    }
  },
  methods: {
    numEncryption(pas, n) {
      for (var i = 1; i <= n; i++) {
        pas = window.btoa(pas)
      }
      return pas
    },
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    keyupSubmit() {
      document.onkeydown = e => {
        const key = window.event.keyCode
        if (key === 13) {
          this.handleLogin()
        }
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.commitUserForm.username = this.loginForm.username
          this.commitUserForm.password = this.numEncryption('1' + this.loginForm.password, this.encryptionCount)
          this.$store.dispatch('Login', this.commitUserForm).then(() => {
            this.loading = false
            this.$router.push({ path: this.redirect || '/' })
          }).catch((error) => {
            this.loading = false
            console.log(error)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
$bg:#2d3a4b;
$light_gray:#eee;

/* reset element-ui css */
.login-container {
  position: absolute;
  .el-input {
    // display: inline-block;
    height: 48px;
    width: 82%;
    input {
      // background: transparent;
      // border: 0px;
      // -webkit-appearance: none;
      // border-radius: 0px;
      padding: 12px 5px 12px 15px;
      // color: $light_gray;
      height: 42px;
      &:-webkit-autofill {
        -webkit-box-shadow: 0 0 0px 1000px white inset !important;
        // -webkit-text-fill-color:black !important;
      }
    }
  }
  // .el-form-item {
    // border: 1px solid rgba(255, 255, 255, 0.1);
    // background: rgba(0, 0, 0, 0.1);
    // background:#0074BE;
    // border-radius: 5px;
    // color: red($color: #000000);
  // }
}

</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;
.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  // background-color: #0074BE;
  // background:url(/assets/img/bg.jpg) repeat;
  .login-form {
    position: absolute;
    // border: 2px solid #0074BE;
    left: 0;
    right: 0;
    width: 20vw;
    // max-width: 100%;
    min-width: 360px;
    padding: 25px 35px 35px 25px;
    // padding-right: 20px;
    // padding-left: 20px;
    margin: 28vh auto;
    margin-left:25%;
    background: white;
    border-radius:5px;
  }
  .logo{
    width:13vw;
    height:8vh;
  }
  .tips {
    font-size: 30vh;
    margin-bottom: 10px;
    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
  .svg-container {
    padding: 6px 7px 6px 10px;
    vertical-align: middle;
    width: 30px;
    color: #0A409A;
    display: inline-block;
  }
  .title {
    font-size: 2.6vh;
    font-weight: 400;
    margin:0px;
    margin-bottom:3vh;
    margin-top:2vh;
    text-align: center;
    font-weight: bold;
  }
  .show-pwd {
    position: absolute;
    padding:6px 5px 6px 8px;
    font-size: 16px;
    color: #0A409A;
    cursor: pointer;
    user-select: none;
  }
}
</style>
