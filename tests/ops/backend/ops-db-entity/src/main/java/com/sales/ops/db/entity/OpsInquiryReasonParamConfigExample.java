package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsInquiryReasonParamConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInquiryReasonParamConfigExample() {
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

        public Criteria andSupplierIdIsNull() {
            addCriterion("supplier_id is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("supplier_id is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(String value) {
            addCriterion("supplier_id =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(String value) {
            addCriterion("supplier_id <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(String value) {
            addCriterion("supplier_id >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_id >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(String value) {
            addCriterion("supplier_id <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(String value) {
            addCriterion("supplier_id <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLike(String value) {
            addCriterion("supplier_id like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotLike(String value) {
            addCriterion("supplier_id not like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<String> values) {
            addCriterion("supplier_id in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<String> values) {
            addCriterion("supplier_id not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(String value1, String value2) {
            addCriterion("supplier_id between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(String value1, String value2) {
            addCriterion("supplier_id not between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNull() {
            addCriterion("supplier_name is null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNotNull() {
            addCriterion("supplier_name is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameEqualTo(String value) {
            addCriterion("supplier_name =", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotEqualTo(String value) {
            addCriterion("supplier_name <>", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThan(String value) {
            addCriterion("supplier_name >", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_name >=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThan(String value) {
            addCriterion("supplier_name <", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThanOrEqualTo(String value) {
            addCriterion("supplier_name <=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLike(String value) {
            addCriterion("supplier_name like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotLike(String value) {
            addCriterion("supplier_name not like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIn(List<String> values) {
            addCriterion("supplier_name in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotIn(List<String> values) {
            addCriterion("supplier_name not in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameBetween(String value1, String value2) {
            addCriterion("supplier_name between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotBetween(String value1, String value2) {
            addCriterion("supplier_name not between", value1, value2, "supplierName");
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

        public Criteria andParamNameIsNull() {
            addCriterion("param_name is null");
            return (Criteria) this;
        }

        public Criteria andParamNameIsNotNull() {
            addCriterion("param_name is not null");
            return (Criteria) this;
        }

        public Criteria andParamNameEqualTo(String value) {
            addCriterion("param_name =", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameNotEqualTo(String value) {
            addCriterion("param_name <>", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameGreaterThan(String value) {
            addCriterion("param_name >", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameGreaterThanOrEqualTo(String value) {
            addCriterion("param_name >=", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameLessThan(String value) {
            addCriterion("param_name <", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameLessThanOrEqualTo(String value) {
            addCriterion("param_name <=", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameLike(String value) {
            addCriterion("param_name like", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameNotLike(String value) {
            addCriterion("param_name not like", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameIn(List<String> values) {
            addCriterion("param_name in", values, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameNotIn(List<String> values) {
            addCriterion("param_name not in", values, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameBetween(String value1, String value2) {
            addCriterion("param_name between", value1, value2, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameNotBetween(String value1, String value2) {
            addCriterion("param_name not between", value1, value2, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamTypeIsNull() {
            addCriterion("param_type is null");
            return (Criteria) this;
        }

        public Criteria andParamTypeIsNotNull() {
            addCriterion("param_type is not null");
            return (Criteria) this;
        }

        public Criteria andParamTypeEqualTo(String value) {
            addCriterion("param_type =", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotEqualTo(String value) {
            addCriterion("param_type <>", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeGreaterThan(String value) {
            addCriterion("param_type >", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeGreaterThanOrEqualTo(String value) {
            addCriterion("param_type >=", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeLessThan(String value) {
            addCriterion("param_type <", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeLessThanOrEqualTo(String value) {
            addCriterion("param_type <=", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeLike(String value) {
            addCriterion("param_type like", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotLike(String value) {
            addCriterion("param_type not like", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeIn(List<String> values) {
            addCriterion("param_type in", values, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotIn(List<String> values) {
            addCriterion("param_type not in", values, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeBetween(String value1, String value2) {
            addCriterion("param_type between", value1, value2, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotBetween(String value1, String value2) {
            addCriterion("param_type not between", value1, value2, "paramType");
            return (Criteria) this;
        }

        public Criteria andDataLengthIsNull() {
            addCriterion("data_length is null");
            return (Criteria) this;
        }

        public Criteria andDataLengthIsNotNull() {
            addCriterion("data_length is not null");
            return (Criteria) this;
        }

        public Criteria andDataLengthEqualTo(String value) {
            addCriterion("data_length =", value, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthNotEqualTo(String value) {
            addCriterion("data_length <>", value, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthGreaterThan(String value) {
            addCriterion("data_length >", value, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthGreaterThanOrEqualTo(String value) {
            addCriterion("data_length >=", value, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthLessThan(String value) {
            addCriterion("data_length <", value, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthLessThanOrEqualTo(String value) {
            addCriterion("data_length <=", value, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthLike(String value) {
            addCriterion("data_length like", value, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthNotLike(String value) {
            addCriterion("data_length not like", value, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthIn(List<String> values) {
            addCriterion("data_length in", values, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthNotIn(List<String> values) {
            addCriterion("data_length not in", values, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthBetween(String value1, String value2) {
            addCriterion("data_length between", value1, value2, "dataLength");
            return (Criteria) this;
        }

        public Criteria andDataLengthNotBetween(String value1, String value2) {
            addCriterion("data_length not between", value1, value2, "dataLength");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthIsNull() {
            addCriterion("is_fixed_length is null");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthIsNotNull() {
            addCriterion("is_fixed_length is not null");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthEqualTo(Boolean value) {
            addCriterion("is_fixed_length =", value, "isFixedLength");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthNotEqualTo(Boolean value) {
            addCriterion("is_fixed_length <>", value, "isFixedLength");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthGreaterThan(Boolean value) {
            addCriterion("is_fixed_length >", value, "isFixedLength");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_fixed_length >=", value, "isFixedLength");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthLessThan(Boolean value) {
            addCriterion("is_fixed_length <", value, "isFixedLength");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthLessThanOrEqualTo(Boolean value) {
            addCriterion("is_fixed_length <=", value, "isFixedLength");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthIn(List<Boolean> values) {
            addCriterion("is_fixed_length in", values, "isFixedLength");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthNotIn(List<Boolean> values) {
            addCriterion("is_fixed_length not in", values, "isFixedLength");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthBetween(Boolean value1, Boolean value2) {
            addCriterion("is_fixed_length between", value1, value2, "isFixedLength");
            return (Criteria) this;
        }

        public Criteria andIsFixedLengthNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_fixed_length not between", value1, value2, "isFixedLength");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoIsNull() {
            addCriterion("other_type_info is null");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoIsNotNull() {
            addCriterion("other_type_info is not null");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoEqualTo(String value) {
            addCriterion("other_type_info =", value, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoNotEqualTo(String value) {
            addCriterion("other_type_info <>", value, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoGreaterThan(String value) {
            addCriterion("other_type_info >", value, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoGreaterThanOrEqualTo(String value) {
            addCriterion("other_type_info >=", value, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoLessThan(String value) {
            addCriterion("other_type_info <", value, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoLessThanOrEqualTo(String value) {
            addCriterion("other_type_info <=", value, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoLike(String value) {
            addCriterion("other_type_info like", value, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoNotLike(String value) {
            addCriterion("other_type_info not like", value, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoIn(List<String> values) {
            addCriterion("other_type_info in", values, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoNotIn(List<String> values) {
            addCriterion("other_type_info not in", values, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoBetween(String value1, String value2) {
            addCriterion("other_type_info between", value1, value2, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andOtherTypeInfoNotBetween(String value1, String value2) {
            addCriterion("other_type_info not between", value1, value2, "otherTypeInfo");
            return (Criteria) this;
        }

        public Criteria andParamRemarkIsNull() {
            addCriterion("param_remark is null");
            return (Criteria) this;
        }

        public Criteria andParamRemarkIsNotNull() {
            addCriterion("param_remark is not null");
            return (Criteria) this;
        }

        public Criteria andParamRemarkEqualTo(String value) {
            addCriterion("param_remark =", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkNotEqualTo(String value) {
            addCriterion("param_remark <>", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkGreaterThan(String value) {
            addCriterion("param_remark >", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("param_remark >=", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkLessThan(String value) {
            addCriterion("param_remark <", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkLessThanOrEqualTo(String value) {
            addCriterion("param_remark <=", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkLike(String value) {
            addCriterion("param_remark like", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkNotLike(String value) {
            addCriterion("param_remark not like", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkIn(List<String> values) {
            addCriterion("param_remark in", values, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkNotIn(List<String> values) {
            addCriterion("param_remark not in", values, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkBetween(String value1, String value2) {
            addCriterion("param_remark between", value1, value2, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkNotBetween(String value1, String value2) {
            addCriterion("param_remark not between", value1, value2, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
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