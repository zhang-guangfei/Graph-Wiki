export type Status = "passed" | "warning" | "failed" | "ready" | "partial" | "empty" | "unknown" | string;

export interface EvidenceRef {
  id?: string;
  type: string;
  label: string;
  path: string;
  sourcePath?: string;
  section?: string;
  confidence?: number | null;
  confidenceLabel?: string;
  status?: Status;
}

export interface FlowStep {
  stepId: string;
  order: number;
  text: string;
  evidenceRefs: string[];
  ruleRefs: string[];
  status: Status;
  confidence: number;
}

export interface DomainFlow {
  flowId: string;
  title: string;
  summary: string;
  steps: FlowStep[];
  evidenceRefs: string[];
  status: Status;
  confidence: number;
}

export interface DomainRule {
  ruleId: string;
  statement: string;
  ruleType?: string;
  flowRefs: string[];
  evidenceRefs: string[];
  status: Status;
  confidence: number;
}

export interface FieldRuleMapping {
  api?: { method: string; url: string; functionName: string };
  dto?: { className: string; field: string; sourcePath?: string };
  entity?: { className: string; field: string; sourcePath?: string };
  database?: { table: string; column: string };
  frontendCallers?: string[];
}

export interface FieldRuleChainCompleteness {
  presentLayers: string[];
  missingRequiredLayers: string[];
  missingOptionalLayers: string[];
}

export interface FieldRule {
  fieldRuleId: string;
  fieldId: string;
  statement: string;
  chain: Array<{ layer: string; ref: string }>;
  mapping?: FieldRuleMapping;
  chainCompleteness?: FieldRuleChainCompleteness;
  evidenceRefs: string[];
  status: Status;
  confidence: number;
  partialReason?: string;
}

export interface DomainListItem {
  domainKey: string;
  displayName: string;
  summary: string;
  anchorCount: number;
  businessPointCount: number;
  apiCount: number;
  fieldFlowCount: number;
  dependencyCount: number;
  quality: Status;
  evidence: EvidenceRef[];
}

export interface BusinessPoint {
  pointId: string;
  domainKey: string;
  codeName: string;
  businessTitle: string;
  pointType: string;
  businessMeaning: string;
  entryFile: string;
  implementationFiles: string[];
  testSuggestions: string[];
  evidence: EvidenceRef[];
}

export interface DomainDetail {
  domainKey: string;
  displayName: string;
  businessMeaning: string;
  health: { status: Status; warnings: string[] };
  entryFiles: Array<{ role: string; name: string; path: string }>;
  businessPoints: {
    coreActions: BusinessPoint[];
    interactions: BusinessPoint[];
    helpers: BusinessPoint[];
  };
  flows: DomainFlow[];
  apis: ApiIndexItem[];
  fieldFlows: {
    status: Status;
    items: FieldFlowItem[];
    emptyState?: { reason: string; recommendedAction: string } | null;
  };
  dependencies: Array<Record<string, unknown>>;
  rules: { status: Status; wikiPage: string; items?: DomainRule[] };
  fieldRules: FieldRule[];
  spec: { status: Status; wikiPage: string };
  deepReadingPath?: { order: string[]; flowCount: number; ruleCount: number; evidenceCount: number };
  evidence: EvidenceRef[];
}

export interface ApiIndexItem {
  apiId: string;
  method: string;
  url: string;
  domainKey: string;
  businessUse: string;
  frontendCallers: string[];
  frontendCallerStatus: string;
  backend: {
    controller: string;
    controllerPath: string;
    method: string;
    serviceCall: string;
  };
  dto?: string;
  confidence: number;
  evidence: EvidenceRef[];
}

export interface FieldFlowItem {
  fieldId: string;
  domainKey: string;
  table: string;
  column: string;
  api: { method: string; url: string; functionName: string };
  dto: { className: string; field: string };
  entity: { className: string; field: string };
  frontendCallers: string[];
  confidence: number;
  confidenceLabel: string;
  evidence: EvidenceRef[];
}

export interface ImpactExample {
  type: string;
  question: string;
  answer: string;
  riskLevel: string;
  recommendedActions: string[];
  evidence: EvidenceRef[];
}

export interface WorkbenchData {
  schema: { version: string; source: string };
  overview: {
    projectName: string;
    generatedAt?: string;
    sourceRoot: string;
    quality: Record<string, Status>;
    scale: {
      totalFiles: number;
      codeFiles: number;
      graphNodes: number;
      graphEdges: number;
      apis: number;
      fields: number;
      domains: number;
    };
    domains: DomainListItem[];
    topQuestions: ImpactExample[];
    recommendedReading: Array<{ label: string; targetRoute: string }>;
    latestDreamCycle: DreamCycleSummary;
  };
  domains: DomainListItem[];
  domainDetails: Record<string, DomainDetail>;
  apis: ApiIndexItem[];
  fields: FieldFlowItem[];
  impact: {
    status: Status;
    queryExamples: ImpactExample[];
    coverage: Record<string, unknown>;
  };
  dreamCycle: DreamCycleSummary;
}

export interface DreamCycleSummary {
  status: Status;
  version: string;
  changes: {
    newFiles: number;
    modifiedFiles: number;
    deletedFiles: number;
    changedDomains: string[];
  };
  maintenance: {
    orphanWikiDomains: string[];
    archivedStaleDomains: string[];
    manualFilesProtected: number;
    duplicateBusinessPoints: unknown[];
  };
  reviewQueue: unknown[];
  message?: string;
  evidence: EvidenceRef[];
}
