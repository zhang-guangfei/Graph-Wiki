<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import type { ApiIndexItem, DomainDetail, DomainListItem, ImpactExample, WorkbenchData } from "./types";

type ViewKey = "overview" | "domains" | "apis" | "impact" | "maintenance";

const data = ref<WorkbenchData | null>(null);
const loading = ref(true);
const error = ref("");
const activeView = ref<ViewKey>("overview");
const activeDomainKey = ref("");
const apiDomainFilter = ref("all");
const query = ref("");

const navItems: Array<{ key: ViewKey; label: string; hint: string }> = [
  { key: "overview", label: "总览", hint: "质量、规模、业务版图" },
  { key: "domains", label: "业务域", hint: "领域、代码入口、业务点" },
  { key: "apis", label: "API", hint: "接口、调用者、后端证据" },
  { key: "impact", label: "影响分析", hint: "字段、接口、规则、依赖" },
  { key: "maintenance", label: "维护", hint: "Dream Cycle 与审阅队列" },
];

onMounted(async () => {
  try {
    const response = await fetch("./workbench-data.json", { cache: "no-store" });
    if (!response.ok) throw new Error(`workbench-data.json 加载失败：${response.status}`);
    data.value = await response.json();
    activeDomainKey.value = data.value?.domains[0]?.domainKey ?? "";
  } catch (err) {
    error.value = err instanceof Error ? err.message : "未知加载错误";
  } finally {
    loading.value = false;
  }
});

const overview = computed(() => data.value?.overview);
const domains = computed(() => data.value?.domains ?? []);
const activeDomain = computed<DomainDetail | null>(() => {
  if (!data.value || !activeDomainKey.value) return null;
  return data.value.domainDetails[activeDomainKey.value] ?? null;
});
const filteredApis = computed(() => {
  const apis = data.value?.apis ?? [];
  const text = query.value.trim().toLowerCase();
  return apis.filter((api) => {
    const byDomain = apiDomainFilter.value === "all" || api.domainKey === apiDomainFilter.value;
    const bySearch = !text || [api.url, api.businessUse, api.backend.controller, api.dto ?? ""].some((item) => item.toLowerCase().includes(text));
    return byDomain && bySearch;
  });
});
const searchResults = computed(() => {
  const text = query.value.trim().toLowerCase();
  if (!text || !data.value) return [];
  const domainHits = data.value.domains
    .filter((domain) => [domain.domainKey, domain.displayName, domain.summary].some((item) => item.toLowerCase().includes(text)))
    .map((domain) => ({ type: "业务域", title: domain.displayName, detail: domain.summary, domainKey: domain.domainKey }));
  const apiHits = data.value.apis
    .filter((api) => [api.url, api.businessUse, api.backend.controller].some((item) => item.toLowerCase().includes(text)))
    .slice(0, 6)
    .map((api) => ({ type: "API", title: api.url, detail: api.businessUse, domainKey: api.domainKey }));
  return [...domainHits, ...apiHits].slice(0, 8);
});

function setView(view: ViewKey) {
  activeView.value = view;
}

function setDomain(domainKey: string) {
  activeDomainKey.value = domainKey;
  activeView.value = "domains";
}

function statusLabel(status: string | undefined) {
  if (!status) return "unknown";
  const labels: Record<string, string> = {
    passed: "通过",
    ready: "就绪",
    partial: "部分",
    warning: "警告",
    failed: "失败",
    empty: "空",
    placeholder: "待补充",
  };
  return labels[status] ?? status;
}

function statusTone(status: string | undefined) {
  if (status === "passed" || status === "ready") return "good";
  if (status === "warning" || status === "partial" || status === "placeholder") return "warn";
  if (status === "failed") return "bad";
  return "quiet";
}

function riskTone(risk: string) {
  if (risk === "high") return "bad";
  if (risk === "medium") return "warn";
  return "good";
}
</script>

<template>
  <main class="shell">
    <aside class="sidebar" aria-label="Graph-Wiki navigation">
      <div class="brand">
        <span class="brand-mark">GW</span>
        <div>
          <strong>Graph-Wiki</strong>
          <small>Workbench v0</small>
        </div>
      </div>

      <nav class="nav-list">
        <button
          v-for="item in navItems"
          :key="item.key"
          class="nav-button"
          :class="{ active: activeView === item.key }"
          type="button"
          @click="setView(item.key)"
        >
          <span>{{ item.label }}</span>
          <small>{{ item.hint }}</small>
        </button>
      </nav>

      <section class="side-section" v-if="overview">
        <p class="eyebrow">当前项目</p>
        <h2>{{ overview.projectName }}</h2>
        <div class="status-row">
          <span class="status" :class="statusTone(overview.quality.build)">{{ statusLabel(overview.quality.build) }}</span>
          <span class="muted">{{ overview.scale.codeFiles }} 个代码文件</span>
        </div>
      </section>
    </aside>

    <section class="workspace">
      <header class="topbar">
        <div>
          <p class="eyebrow">代码业务知识库</p>
          <h1>{{ overview?.projectName ?? "Graph-Wiki Workbench" }}</h1>
        </div>
        <label class="search">
          <span>搜索</span>
          <input v-model="query" type="search" placeholder="输入 API、字段、业务域或代码文件" />
        </label>
      </header>

      <section v-if="loading" class="state-panel">
        <h2>正在加载工作台数据</h2>
        <p>读取 workbench-data.json，页面会在数据就绪后自动渲染。</p>
      </section>

      <section v-else-if="error" class="state-panel error">
        <h2>数据加载失败</h2>
        <p>{{ error }}</p>
      </section>

      <template v-else-if="data && overview">
        <section v-if="searchResults.length" class="search-results" aria-label="search results">
          <button
            v-for="result in searchResults"
            :key="result.type + result.title"
            type="button"
            @click="result.domainKey ? setDomain(result.domainKey) : undefined"
          >
            <span>{{ result.type }}</span>
            <strong>{{ result.title }}</strong>
            <small>{{ result.detail }}</small>
          </button>
        </section>

        <section v-show="activeView === 'overview'" class="view stack">
          <div class="summary-band">
            <div>
              <p class="eyebrow">项目总览</p>
              <h2>{{ overview.projectName }} 的业务知识图谱已编译完成</h2>
              <p>
                当前工作台把代码域、API、字段链路、影响分析和维护状态聚合成一个可浏览入口。
                前端只读取稳定数据包，Wiki 每次重新编译不会改变页面代码。
              </p>
            </div>
            <dl class="quality-strip">
              <div v-for="(value, key) in overview.quality" :key="key">
                <dt>{{ key }}</dt>
                <dd><span class="status" :class="statusTone(value)">{{ statusLabel(value) }}</span></dd>
              </div>
            </dl>
          </div>

          <div class="metric-grid">
            <div class="metric"><span>业务域</span><strong>{{ overview.scale.domains }}</strong></div>
            <div class="metric"><span>API</span><strong>{{ overview.scale.apis }}</strong></div>
            <div class="metric"><span>字段链路</span><strong>{{ overview.scale.fields }}</strong></div>
            <div class="metric"><span>代码文件</span><strong>{{ overview.scale.codeFiles }}</strong></div>
            <div class="metric"><span>图节点</span><strong>{{ overview.scale.graphNodes }}</strong></div>
            <div class="metric"><span>图关系</span><strong>{{ overview.scale.graphEdges }}</strong></div>
          </div>

          <div class="domain-grid">
            <button v-for="domain in domains" :key="domain.domainKey" class="domain-tile" type="button" @click="setDomain(domain.domainKey)">
              <span>{{ domain.domainKey }}</span>
              <strong>{{ domain.displayName }}</strong>
              <p>{{ domain.summary }}</p>
              <small>{{ domain.apiCount }} API · {{ domain.businessPointCount }} 业务点 · {{ domain.fieldFlowCount }} 字段链路</small>
            </button>
          </div>
        </section>

        <section v-show="activeView === 'domains'" class="view domain-layout">
          <div class="domain-rail" aria-label="domain list">
            <button
              v-for="domain in domains"
              :key="domain.domainKey"
              type="button"
              :class="{ active: activeDomainKey === domain.domainKey }"
              @click="setDomain(domain.domainKey)"
            >
              <strong>{{ domain.displayName }}</strong>
              <span>{{ domain.apiCount }} API / {{ domain.businessPointCount }} 点</span>
            </button>
          </div>

          <article v-if="activeDomain" class="domain-detail">
            <header class="section-header">
              <div>
                <p class="eyebrow">业务域</p>
                <h2>{{ activeDomain.displayName }}</h2>
                <p>{{ activeDomain.businessMeaning }}</p>
              </div>
              <div class="status-stack">
                <span class="status" :class="statusTone(activeDomain.health.status)">{{ statusLabel(activeDomain.health.status) }}</span>
                <span class="status" :class="statusTone(activeDomain.rules.status)">rules {{ statusLabel(activeDomain.rules.status) }}</span>
                <span class="status" :class="statusTone(activeDomain.spec.status)">spec {{ statusLabel(activeDomain.spec.status) }}</span>
              </div>
            </header>

            <div class="split">
              <section>
                <h3>关键代码入口</h3>
                <ul class="file-list">
                  <li v-for="file in activeDomain.entryFiles" :key="file.role + file.path">
                    <span>{{ file.role }}</span>
                    <strong>{{ file.name }}</strong>
                    <small>{{ file.path }}</small>
                  </li>
                </ul>
              </section>
              <section>
                <h3>业务动作</h3>
                <div class="point-groups">
                  <details open>
                    <summary>核心动作 {{ activeDomain.businessPoints.coreActions.length }}</summary>
                    <div v-for="point in activeDomain.businessPoints.coreActions" :key="point.pointId" class="point-row">
                      <strong>{{ point.businessTitle }}</strong>
                      <small>{{ point.codeName }} · {{ point.entryFile }}</small>
                    </div>
                  </details>
                  <details>
                    <summary>交互动作 {{ activeDomain.businessPoints.interactions.length }}</summary>
                    <div v-for="point in activeDomain.businessPoints.interactions" :key="point.pointId" class="point-row">
                      <strong>{{ point.businessTitle }}</strong>
                      <small>{{ point.codeName }} · {{ point.entryFile }}</small>
                    </div>
                  </details>
                  <details>
                    <summary>辅助方法 {{ activeDomain.businessPoints.helpers.length }}</summary>
                    <div v-for="point in activeDomain.businessPoints.helpers" :key="point.pointId" class="point-row muted-row">
                      <strong>{{ point.businessTitle }}</strong>
                      <small>{{ point.codeName }}</small>
                    </div>
                  </details>
                </div>
              </section>
            </div>
          </article>
        </section>

        <section v-show="activeView === 'apis'" class="view stack">
          <header class="section-header">
            <div>
              <p class="eyebrow">API 索引</p>
              <h2>接口归属、调用入口和后端证据</h2>
            </div>
            <select v-model="apiDomainFilter" class="select">
              <option value="all">全部业务域</option>
              <option v-for="domain in domains" :key="domain.domainKey" :value="domain.domainKey">{{ domain.displayName }}</option>
            </select>
          </header>
          <div class="table-shell">
            <table class="api-table">
              <thead>
                <tr>
                  <th>方法</th>
                  <th>URL</th>
                  <th>业务用途</th>
                  <th>业务域</th>
                  <th>前端调用者</th>
                  <th>后端入口</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="api in filteredApis" :key="api.apiId">
                  <td><span class="method">{{ api.method }}</span></td>
                  <td><code>{{ api.url }}</code></td>
                  <td>{{ api.businessUse }}</td>
                  <td>{{ api.domainKey }}</td>
                  <td>{{ api.frontendCallers.join(", ") || api.frontendCallerStatus }}</td>
                  <td>{{ api.backend.controller }}.{{ api.backend.method }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>

        <section v-show="activeView === 'impact'" class="view stack">
          <header class="section-header">
            <div>
              <p class="eyebrow">影响分析</p>
              <h2>从问题进入证据链和行动建议</h2>
            </div>
            <span class="status" :class="statusTone(data.impact.status)">{{ statusLabel(data.impact.status) }}</span>
          </header>
          <div class="impact-grid">
            <article v-for="item in data.impact.queryExamples" :key="item.question" class="impact-card">
              <div class="card-top">
                <span class="status" :class="riskTone(item.riskLevel)">风险 {{ item.riskLevel }}</span>
                <small>{{ item.type }}</small>
              </div>
              <h3>{{ item.question }}</h3>
              <p>{{ item.answer }}</p>
              <h4>建议动作</h4>
              <ul>
                <li v-for="action in item.recommendedActions" :key="action">{{ action }}</li>
              </ul>
            </article>
          </div>
        </section>

        <section v-show="activeView === 'maintenance'" class="view stack">
          <header class="section-header">
            <div>
              <p class="eyebrow">Dream Cycle</p>
              <h2>知识库维护摘要</h2>
              <p>{{ data.dreamCycle.message }}</p>
            </div>
            <span class="status" :class="statusTone(data.dreamCycle.status)">{{ statusLabel(data.dreamCycle.status) }}</span>
          </header>
          <div class="metric-grid">
            <div class="metric"><span>新增文件</span><strong>{{ data.dreamCycle.changes.newFiles }}</strong></div>
            <div class="metric"><span>修改文件</span><strong>{{ data.dreamCycle.changes.modifiedFiles }}</strong></div>
            <div class="metric"><span>删除文件</span><strong>{{ data.dreamCycle.changes.deletedFiles }}</strong></div>
            <div class="metric"><span>人工文件保护</span><strong>{{ data.dreamCycle.maintenance.manualFilesProtected }}</strong></div>
          </div>
          <section class="state-panel">
            <h3>{{ data.dreamCycle.reviewQueue.length ? "需要人工审核" : "无需人工处理" }}</h3>
            <p>
              孤立页面 {{ data.dreamCycle.maintenance.orphanWikiDomains.length }} 个，
              归档旧域 {{ data.dreamCycle.maintenance.archivedStaleDomains.length }} 个，
              重复业务点 {{ data.dreamCycle.maintenance.duplicateBusinessPoints.length }} 个。
            </p>
          </section>
        </section>
      </template>
    </section>
  </main>
</template>
