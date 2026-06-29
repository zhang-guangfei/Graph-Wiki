package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsInquiryCodeConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInquiryCodeConfigExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCodeTypeIsNull() {
            addCriterion("code_type is null");
            return (Criteria) this;
        }

        public Criteria andCodeTypeIsNotNull() {
            addCriterion("code_type is not null");
            return (Criteria) this;
        }

        public Criteria andCodeTypeEqualTo(String value) {
            addCriterion("code_type =", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotEqualTo(String value) {
            addCriterion("code_type <>", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeGreaterThan(String value) {
            addCriterion("code_type >", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("code_type >=", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeLessThan(String value) {
            addCriterion("code_type <", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeLessThanOrEqualTo(String value) {
            addCriterion("code_type <=", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeLike(String value) {
            addCriterion("code_type like", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotLike(String value) {
            addCriterion("code_type not like", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeIn(List<String> values) {
            addCriterion("code_type in", values, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotIn(List<String> values) {
            addCriterion("code_type not in", values, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeBetween(String value1, String value2) {
            addCriterion("code_type between", value1, value2, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotBetween(String value1, String value2) {
            addCriterion("code_type not between", value1, value2, "codeType");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeIsNull() {
            addCriterion("ops_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeIsNotNull() {
            addCriterion("ops_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeEqualTo(String value) {
            addCriterion("ops_reason_code =", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeNotEqualTo(String value) {
            addCriterion("ops_reason_code <>", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeGreaterThan(String value) {
            addCriterion("ops_reason_code >", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ops_reason_code >=", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeLessThan(String value) {
            addCriterion("ops_reason_code <", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("ops_reason_code <=", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeLike(String value) {
            addCriterion("ops_reason_code like", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeNotLike(String value) {
            addCriterion("ops_reason_code not like", value, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeIn(List<String> values) {
            addCriterion("ops_reason_code in", values, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeNotIn(List<String> values) {
            addCriterion("ops_reason_code not in", values, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeBetween(String value1, String value2) {
            addCriterion("ops_reason_code between", value1, value2, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonCodeNotBetween(String value1, String value2) {
            addCriterion("ops_reason_code not between", value1, value2, "opsReasonCode");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescIsNull() {
            addCriterion("ops_reason_desc is null");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescIsNotNull() {
            addCriterion("ops_reason_desc is not null");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescEqualTo(String value) {
            addCriterion("ops_reason_desc =", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescNotEqualTo(String value) {
            addCriterion("ops_reason_desc <>", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescGreaterThan(String value) {
            addCriterion("ops_reason_desc >", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescGreaterThanOrEqualTo(String value) {
            addCriterion("ops_reason_desc >=", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescLessThan(String value) {
            addCriterion("ops_reason_desc <", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescLessThanOrEqualTo(String value) {
            addCriterion("ops_reason_desc <=", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescLike(String value) {
            addCriterion("ops_reason_desc like", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescNotLike(String value) {
            addCriterion("ops_reason_desc not like", value, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescIn(List<String> values) {
            addCriterion("ops_reason_desc in", values, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescNotIn(List<String> values) {
            addCriterion("ops_reason_desc not in", values, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescBetween(String value1, String value2) {
            addCriterion("ops_reason_desc between", value1, value2, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andOpsReasonDescNotBetween(String value1, String value2) {
            addCriterion("ops_reason_desc not between", value1, value2, "opsReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeIsNull() {
            addCriterion("gz_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeIsNotNull() {
            addCriterion("gz_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeEqualTo(String value) {
            addCriterion("gz_reason_code =", value, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeNotEqualTo(String value) {
            addCriterion("gz_reason_code <>", value, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeGreaterThan(String value) {
            addCriterion("gz_reason_code >", value, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("gz_reason_code >=", value, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeLessThan(String value) {
            addCriterion("gz_reason_code <", value, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("gz_reason_code <=", value, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeLike(String value) {
            addCriterion("gz_reason_code like", value, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeNotLike(String value) {
            addCriterion("gz_reason_code not like", value, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeIn(List<String> values) {
            addCriterion("gz_reason_code in", values, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeNotIn(List<String> values) {
            addCriterion("gz_reason_code not in", values, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeBetween(String value1, String value2) {
            addCriterion("gz_reason_code between", value1, value2, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonCodeNotBetween(String value1, String value2) {
            addCriterion("gz_reason_code not between", value1, value2, "gzReasonCode");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescIsNull() {
            addCriterion("gz_reason_desc is null");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescIsNotNull() {
            addCriterion("gz_reason_desc is not null");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescEqualTo(String value) {
            addCriterion("gz_reason_desc =", value, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescNotEqualTo(String value) {
            addCriterion("gz_reason_desc <>", value, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescGreaterThan(String value) {
            addCriterion("gz_reason_desc >", value, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescGreaterThanOrEqualTo(String value) {
            addCriterion("gz_reason_desc >=", value, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescLessThan(String value) {
            addCriterion("gz_reason_desc <", value, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescLessThanOrEqualTo(String value) {
            addCriterion("gz_reason_desc <=", value, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescLike(String value) {
            addCriterion("gz_reason_desc like", value, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescNotLike(String value) {
            addCriterion("gz_reason_desc not like", value, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescIn(List<String> values) {
            addCriterion("gz_reason_desc in", values, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescNotIn(List<String> values) {
            addCriterion("gz_reason_desc not in", values, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescBetween(String value1, String value2) {
            addCriterion("gz_reason_desc between", value1, value2, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andGzReasonDescNotBetween(String value1, String value2) {
            addCriterion("gz_reason_desc not between", value1, value2, "gzReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeIsNull() {
            addCriterion("as400_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeIsNotNull() {
            addCriterion("as400_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeEqualTo(String value) {
            addCriterion("as400_reason_code =", value, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeNotEqualTo(String value) {
            addCriterion("as400_reason_code <>", value, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeGreaterThan(String value) {
            addCriterion("as400_reason_code >", value, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("as400_reason_code >=", value, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeLessThan(String value) {
            addCriterion("as400_reason_code <", value, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("as400_reason_code <=", value, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeLike(String value) {
            addCriterion("as400_reason_code like", value, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeNotLike(String value) {
            addCriterion("as400_reason_code not like", value, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeIn(List<String> values) {
            addCriterion("as400_reason_code in", values, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeNotIn(List<String> values) {
            addCriterion("as400_reason_code not in", values, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeBetween(String value1, String value2) {
            addCriterion("as400_reason_code between", value1, value2, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonCodeNotBetween(String value1, String value2) {
            addCriterion("as400_reason_code not between", value1, value2, "as400ReasonCode");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescIsNull() {
            addCriterion("as400_reason_desc is null");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescIsNotNull() {
            addCriterion("as400_reason_desc is not null");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescEqualTo(String value) {
            addCriterion("as400_reason_desc =", value, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescNotEqualTo(String value) {
            addCriterion("as400_reason_desc <>", value, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescGreaterThan(String value) {
            addCriterion("as400_reason_desc >", value, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescGreaterThanOrEqualTo(String value) {
            addCriterion("as400_reason_desc >=", value, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescLessThan(String value) {
            addCriterion("as400_reason_desc <", value, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescLessThanOrEqualTo(String value) {
            addCriterion("as400_reason_desc <=", value, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescLike(String value) {
            addCriterion("as400_reason_desc like", value, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescNotLike(String value) {
            addCriterion("as400_reason_desc not like", value, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescIn(List<String> values) {
            addCriterion("as400_reason_desc in", values, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescNotIn(List<String> values) {
            addCriterion("as400_reason_desc not in", values, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescBetween(String value1, String value2) {
            addCriterion("as400_reason_desc between", value1, value2, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andAs400ReasonDescNotBetween(String value1, String value2) {
            addCriterion("as400_reason_desc not between", value1, value2, "as400ReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeIsNull() {
            addCriterion("manu_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeIsNotNull() {
            addCriterion("manu_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeEqualTo(String value) {
            addCriterion("manu_reason_code =", value, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeNotEqualTo(String value) {
            addCriterion("manu_reason_code <>", value, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeGreaterThan(String value) {
            addCriterion("manu_reason_code >", value, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("manu_reason_code >=", value, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeLessThan(String value) {
            addCriterion("manu_reason_code <", value, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("manu_reason_code <=", value, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeLike(String value) {
            addCriterion("manu_reason_code like", value, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeNotLike(String value) {
            addCriterion("manu_reason_code not like", value, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeIn(List<String> values) {
            addCriterion("manu_reason_code in", values, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeNotIn(List<String> values) {
            addCriterion("manu_reason_code not in", values, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeBetween(String value1, String value2) {
            addCriterion("manu_reason_code between", value1, value2, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonCodeNotBetween(String value1, String value2) {
            addCriterion("manu_reason_code not between", value1, value2, "manuReasonCode");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescIsNull() {
            addCriterion("manu_reason_desc is null");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescIsNotNull() {
            addCriterion("manu_reason_desc is not null");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescEqualTo(String value) {
            addCriterion("manu_reason_desc =", value, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescNotEqualTo(String value) {
            addCriterion("manu_reason_desc <>", value, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescGreaterThan(String value) {
            addCriterion("manu_reason_desc >", value, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescGreaterThanOrEqualTo(String value) {
            addCriterion("manu_reason_desc >=", value, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescLessThan(String value) {
            addCriterion("manu_reason_desc <", value, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescLessThanOrEqualTo(String value) {
            addCriterion("manu_reason_desc <=", value, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescLike(String value) {
            addCriterion("manu_reason_desc like", value, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescNotLike(String value) {
            addCriterion("manu_reason_desc not like", value, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescIn(List<String> values) {
            addCriterion("manu_reason_desc in", values, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescNotIn(List<String> values) {
            addCriterion("manu_reason_desc not in", values, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescBetween(String value1, String value2) {
            addCriterion("manu_reason_desc between", value1, value2, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andManuReasonDescNotBetween(String value1, String value2) {
            addCriterion("manu_reason_desc not between", value1, value2, "manuReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeIsNull() {
            addCriterion("purchase_reason_code is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeIsNotNull() {
            addCriterion("purchase_reason_code is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeEqualTo(String value) {
            addCriterion("purchase_reason_code =", value, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeNotEqualTo(String value) {
            addCriterion("purchase_reason_code <>", value, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeGreaterThan(String value) {
            addCriterion("purchase_reason_code >", value, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_reason_code >=", value, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeLessThan(String value) {
            addCriterion("purchase_reason_code <", value, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeLessThanOrEqualTo(String value) {
            addCriterion("purchase_reason_code <=", value, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeLike(String value) {
            addCriterion("purchase_reason_code like", value, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeNotLike(String value) {
            addCriterion("purchase_reason_code not like", value, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeIn(List<String> values) {
            addCriterion("purchase_reason_code in", values, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeNotIn(List<String> values) {
            addCriterion("purchase_reason_code not in", values, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeBetween(String value1, String value2) {
            addCriterion("purchase_reason_code between", value1, value2, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonCodeNotBetween(String value1, String value2) {
            addCriterion("purchase_reason_code not between", value1, value2, "purchaseReasonCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescIsNull() {
            addCriterion("purchase_reason_desc is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescIsNotNull() {
            addCriterion("purchase_reason_desc is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescEqualTo(String value) {
            addCriterion("purchase_reason_desc =", value, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescNotEqualTo(String value) {
            addCriterion("purchase_reason_desc <>", value, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescGreaterThan(String value) {
            addCriterion("purchase_reason_desc >", value, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_reason_desc >=", value, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescLessThan(String value) {
            addCriterion("purchase_reason_desc <", value, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescLessThanOrEqualTo(String value) {
            addCriterion("purchase_reason_desc <=", value, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescLike(String value) {
            addCriterion("purchase_reason_desc like", value, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescNotLike(String value) {
            addCriterion("purchase_reason_desc not like", value, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescIn(List<String> values) {
            addCriterion("purchase_reason_desc in", values, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescNotIn(List<String> values) {
            addCriterion("purchase_reason_desc not in", values, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescBetween(String value1, String value2) {
            addCriterion("purchase_reason_desc between", value1, value2, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andPurchaseReasonDescNotBetween(String value1, String value2) {
            addCriterion("purchase_reason_desc not between", value1, value2, "purchaseReasonDesc");
            return (Criteria) this;
        }

        public Criteria andIdDeletedIsNull() {
            addCriterion("id_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIdDeletedIsNotNull() {
            addCriterion("id_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIdDeletedEqualTo(Boolean value) {
            addCriterion("id_deleted =", value, "idDeleted");
            return (Criteria) this;
        }

        public Criteria andIdDeletedNotEqualTo(Boolean value) {
            addCriterion("id_deleted <>", value, "idDeleted");
            return (Criteria) this;
        }

        public Criteria andIdDeletedGreaterThan(Boolean value) {
            addCriterion("id_deleted >", value, "idDeleted");
            return (Criteria) this;
        }

        public Criteria andIdDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("id_deleted >=", value, "idDeleted");
            return (Criteria) this;
        }

        public Criteria andIdDeletedLessThan(Boolean value) {
            addCriterion("id_deleted <", value, "idDeleted");
            return (Criteria) this;
        }

        public Criteria andIdDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("id_deleted <=", value, "idDeleted");
            return (Criteria) this;
        }

        public Criteria andIdDeletedIn(List<Boolean> values) {
            addCriterion("id_deleted in", values, "idDeleted");
            return (Criteria) this;
        }

        public Criteria andIdDeletedNotIn(List<Boolean> values) {
            addCriterion("id_deleted not in", values, "idDeleted");
            return (Criteria) this;
        }

        public Criteria andIdDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("id_deleted between", value1, value2, "idDeleted");
            return (Criteria) this;
        }

        public Criteria andIdDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("id_deleted not between", value1, value2, "idDeleted");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}