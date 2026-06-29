<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '88px' : '248px'" class="aside">
      <div class="aside-shell">
        <div class="logo">
          <div class="logo-mark">
            <el-icon size="24"><Connection /></el-icon>
          </div>
          <div v-show="!isCollapse" class="logo-copy">
            <span class="logo-text">SVN Platform</span>
            <span class="logo-subtitle">Tech Workspace</span>
          </div>
        </div>

        <div v-show="!isCollapse" class="aside-kicker">
          <span class="aside-kicker__dot"></span>
          需求驱动工作台
        </div>

        <div class="menu-frame">
          <el-menu
            :default-active="activeMenu"
            :collapse="isCollapse"
            :collapse-transition="false"
            router
            class="nav-menu"
          >
            <el-menu-item index="/dashboard">
              <el-icon><HomeFilled /></el-icon>
              <template #title>首页</template>
            </el-menu-item>

            <el-menu-item index="/repo/list">
              <el-icon><FolderOpened /></el-icon>
              <template #title>仓库管理</template>
            </el-menu-item>

            <el-sub-menu index="/svn">
              <template #title>
                <el-icon><Operation /></el-icon>
                <span>SVN操作</span>
              </template>
              <el-menu-item index="/svn/status">
                <el-icon><Document /></el-icon>
                <template #title>提交代码</template>
              </el-menu-item>
              <el-menu-item index="/svn/log">
                <el-icon><Clock /></el-icon>
                <template #title>提交记录</template>
              </el-menu-item>
              <el-menu-item index="/svn/branch-tag">
                <el-icon><Share /></el-icon>
                <template #title>分支标签</template>
              </el-menu-item>
              <el-menu-item index="/svn/operation-history">
                <el-icon><Histogram /></el-icon>
                <template #title>操作历史</template>
              </el-menu-item>
            </el-sub-menu>

            <el-menu-item index="/svn/merge">
              <el-icon><Connection /></el-icon>
              <template #title>代码合并</template>
            </el-menu-item>

            <el-menu-item index="/svn/local-repo">
              <el-icon><Monitor /></el-icon>
              <template #title>本地仓库</template>
            </el-menu-item>

            <el-menu-item index="/requirement/list">
              <el-icon><Tickets /></el-icon>
              <template #title>需求管理</template>
            </el-menu-item>
          </el-menu>
        </div>
      </div>
    </el-aside>

    <el-container class="content-shell">
      <el-header class="header">
        <div class="header-left">
          <el-button class="collapse-btn" circle @click="toggleCollapse">
            <el-icon>
              <Fold v-if="!isCollapse" />
              <Expand v-else />
            </el-icon>
          </el-button>
          <div class="header-copy">
            <div class="page-kicker">Workspace</div>
            <div class="header-titles">
              <h1 class="header-title">{{ currentTitle }}</h1>
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item v-if="$route.meta.title">
                  {{ $route.meta.title }}
                </el-breadcrumb-item>
              </el-breadcrumb>
            </div>
          </div>
        </div>
        <div class="header-right">
          <div class="status-pill">
            <span class="status-pill__dot"></span>
            SVN Platform v1.0
          </div>
        </div>
      </el-header>

      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { getActiveMenu } from '@/utils/navigation'

const route = useRoute()
const isCollapse = ref(false)

const activeMenu = computed(() => getActiveMenu(route.fullPath))
const currentTitle = computed(() => route.meta.title || 'SVN 开发工作台')

function toggleCollapse() {
  isCollapse.value = !isCollapse.value
}
</script>

<style scoped>
.layout-container {
  height: 100%;
  min-width: 0;
  gap: 12px;
  padding: 12px;
  background: transparent;
  overflow: hidden;
}

.aside {
  transition: width 0.3s;
  overflow: hidden;
  border-radius: var(--app-radius-lg);
  min-height: 0;
}

.aside-shell {
  position: relative;
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 0;
  overflow: hidden;
  padding: 16px 14px 14px;
  border: 1px solid rgba(116, 149, 212, 0.22);
  border-radius: inherit;
  background:
    radial-gradient(circle at top left, rgba(96, 152, 255, 0.22) 0, transparent 28%),
    radial-gradient(circle at 100% 0, rgba(32, 203, 184, 0.14) 0, transparent 22%),
    linear-gradient(180deg, rgba(10, 23, 53, 0.94) 0%, rgba(14, 30, 64, 0.96) 52%, rgba(16, 32, 67, 0.98) 100%);
  box-shadow: 0 24px 52px rgba(11, 28, 63, 0.22);
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 14px;
}

.logo-mark {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 46px;
  height: 46px;
  border-radius: 16px;
  background: linear-gradient(135deg, rgba(51, 143, 255, 0.92), rgba(123, 97, 255, 0.88));
  color: #fff;
  box-shadow: 0 12px 28px rgba(63, 106, 242, 0.32);
}

.logo-copy {
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.logo-text {
  color: #f8fbff;
  font-size: 18px;
  font-weight: 700;
  letter-spacing: -0.02em;
}

.logo-subtitle {
  color: rgba(216, 227, 255, 0.74);
  font-size: 12px;
}

.aside-kicker {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  width: fit-content;
  margin-bottom: 18px;
  padding: 7px 12px;
  border-radius: 999px;
  background: rgba(120, 152, 223, 0.14);
  color: rgba(221, 233, 255, 0.86);
  font-size: 12px;
  font-weight: 600;
}

.aside-kicker__dot {
  width: 7px;
  height: 7px;
  border-radius: 999px;
  background: linear-gradient(135deg, #35d4bf 0%, #51a4ff 100%);
  box-shadow: 0 0 0 6px rgba(53, 212, 191, 0.12);
}

.menu-frame {
  flex: 1 1 auto;
  min-height: 0;
  padding: 10px 8px;
  border-radius: 22px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.07) 0%, rgba(255, 255, 255, 0.04) 100%);
  border: 1px solid rgba(131, 161, 225, 0.12);
  overflow: auto;
  overscroll-behavior: contain;
}

.nav-menu {
  border-right: none;
  background: transparent;
  min-width: 0;
}

.content-shell {
  min-width: 0;
  min-height: 0;
  overflow: hidden;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  min-height: 86px;
  gap: 12px;
  flex-wrap: wrap;
  border: 1px solid var(--app-border);
  border-radius: var(--app-radius-lg);
  background:
    radial-gradient(circle at top right, rgba(51, 143, 255, 0.12) 0, transparent 20%),
    linear-gradient(180deg, rgba(255, 255, 255, 0.96) 0%, rgba(245, 249, 255, 0.96) 100%);
  box-shadow: var(--app-shadow-soft);
  flex-shrink: 0;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 14px;
  min-width: 0;
  flex: 1;
}

.collapse-btn {
  flex-shrink: 0;
}

.header-copy {
  display: flex;
  align-items: center;
  gap: 14px;
  min-width: 0;
}

.header-titles {
  display: flex;
  flex-direction: column;
  gap: 6px;
  min-width: 0;
}

.header-title {
  margin: 0;
  color: var(--app-text);
  font-size: 24px;
  line-height: 1.1;
  font-weight: 700;
  letter-spacing: -0.03em;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 10px;
}

.status-pill {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 14px;
  border-radius: 999px;
  background: linear-gradient(135deg, rgba(51, 143, 255, 0.12), rgba(32, 203, 184, 0.12));
  color: var(--app-accent-strong);
  font-size: 13px;
  font-weight: 700;
}

.status-pill__dot {
  width: 8px;
  height: 8px;
  border-radius: 999px;
  background: linear-gradient(135deg, var(--app-accent) 0%, var(--app-accent-mint) 100%);
  box-shadow: 0 0 0 6px rgba(51, 143, 255, 0.1);
}

.main-content {
  display: flex;
  flex-direction: column;
  background: transparent;
  padding: 12px 0 0;
  overflow: hidden;
  min-width: 0;
  min-height: 0;
}

:deep(.el-breadcrumb__item:last-child .el-breadcrumb__inner),
:deep(.el-breadcrumb__inner) {
  color: var(--app-text-soft);
}

:deep(.nav-menu.el-menu--collapse .el-sub-menu__icon-arrow) {
  display: none;
}

:deep(.nav-menu .el-menu) {
  background: transparent;
}

:deep(.nav-menu .el-menu-item),
:deep(.nav-menu .el-sub-menu__title) {
  height: 46px;
  margin-bottom: 8px;
  border-radius: 14px;
  color: rgba(220, 231, 255, 0.82);
  background: transparent;
  transition: background-color 0.2s ease, color 0.2s ease, transform 0.2s ease;
}

:deep(.nav-menu .el-menu-item:hover),
:deep(.nav-menu .el-sub-menu__title:hover) {
  color: #ffffff;
  background: rgba(118, 153, 227, 0.14);
  transform: translateX(2px);
}

:deep(.nav-menu .el-menu-item.is-active) {
  color: #ffffff;
  background: linear-gradient(135deg, rgba(51, 143, 255, 0.9), rgba(123, 97, 255, 0.78));
  box-shadow: 0 10px 22px rgba(66, 115, 255, 0.26);
}

:deep(.nav-menu .el-sub-menu .el-menu-item) {
  height: 42px;
  margin-bottom: 6px;
  background: rgba(255, 255, 255, 0.03);
}

:deep(.nav-menu .el-menu-item [class^="el-icon"]),
:deep(.nav-menu .el-sub-menu__title [class^="el-icon"]) {
  font-size: 16px;
}

:deep(.nav-menu .el-sub-menu),
:deep(.nav-menu .el-menu-item) {
  min-width: 0;
}

@media (max-width: 1200px) {
  .layout-container {
    padding: 10px;
    gap: 10px;
  }

  .header {
    padding: 14px 16px;
  }

  .header-left,
  .header-copy {
    width: 100%;
  }
}

@media (max-width: 960px) {
  .header-copy {
    flex-wrap: wrap;
    gap: 10px;
  }

  .header-title {
    font-size: 20px;
  }
}
</style>
