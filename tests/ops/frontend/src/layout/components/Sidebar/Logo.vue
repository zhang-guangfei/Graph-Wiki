<template>
  <div :class="{'collapse':collapse}" class="sidebar-logo-container">
    <transition name="sidebarLogoFade">
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo">
        <h1 v-else class="sidebar-title">{{ title }} </h1>
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo">
        <h1 class="sidebar-title">{{ title }} </h1>
      </router-link>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'SidebarLogo',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      title: 'SMC业务处理系统',
      logo: require('../../../assets/img/smclogo.png')
    }
  },
  created() {
    // var currentUrl = window.location.href
    // console.log('主页url ==> ', currentUrl)
    // if (currentUrl.includes('localhost') || currentUrl.includes('10.116.1.15')) {
    //   this.title = 'SMC业务处理系统'
    // }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 40px;
  line-height: 50px;
  background: #0076BD;
  // background: #cdcccf;
  // background: rgb(240, 239, 239);
  text-align: center;
  // overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;

    & .sidebar-logo {
      width: 30px;
      height: 30px;
      vertical-align: middle;
      margin-right: 5px;
      margin-bottom: 13px;
      // margin-left: 2px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      // margin-top: 5px;
      font-weight: 600;
      line-height: 50px;
      font-size: 16px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      vertical-align: middle;
      margin-bottom: 5px;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
