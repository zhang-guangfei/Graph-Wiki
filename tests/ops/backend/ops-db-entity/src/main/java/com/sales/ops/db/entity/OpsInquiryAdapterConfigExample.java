package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsInquiryAdapterConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsInquiryAdapterConfigExample() {
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

        public Criteria andSupplierClassIsNull() {
            addCriterion("supplier_class is null");
            return (Criteria) this;
        }

        public Criteria andSupplierClassIsNotNull() {
            addCriterion("supplier_class is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierClassEqualTo(String value) {
            addCriterion("supplier_class =", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassNotEqualTo(String value) {
            addCriterion("supplier_class <>", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassGreaterThan(String value) {
            addCriterion("supplier_class >", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_class >=", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassLessThan(String value) {
            addCriterion("supplier_class <", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassLessThanOrEqualTo(String value) {
            addCriterion("supplier_class <=", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassLike(String value) {
            addCriterion("supplier_class like", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassNotLike(String value) {
            addCriterion("supplier_class not like", value, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassIn(List<String> values) {
            addCriterion("supplier_class in", values, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassNotIn(List<String> values) {
            addCriterion("supplier_class not in", values, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassBetween(String value1, String value2) {
            addCriterion("supplier_class between", value1, value2, "supplierClass");
            return (Criteria) this;
        }

        public Criteria andSupplierClassNotBetween(String value1, String value2) {
            addCriterion("supplier_class not between", value1, value2, "supplierClass");
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

        public Criteria andConnectTypeIsNull() {
            addCriterion("connect_type is null");
            return (Criteria) this;
        }

        public Criteria andConnectTypeIsNotNull() {
            addCriterion("connect_type is not null");
            return (Criteria) this;
        }

        public Criteria andConnectTypeEqualTo(String value) {
            addCriterion("connect_type =", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeNotEqualTo(String value) {
            addCriterion("connect_type <>", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeGreaterThan(String value) {
            addCriterion("connect_type >", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeGreaterThanOrEqualTo(String value) {
            addCriterion("connect_type >=", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeLessThan(String value) {
            addCriterion("connect_type <", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeLessThanOrEqualTo(String value) {
            addCriterion("connect_type <=", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeLike(String value) {
            addCriterion("connect_type like", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeNotLike(String value) {
            addCriterion("connect_type not like", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeIn(List<String> values) {
            addCriterion("connect_type in", values, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeNotIn(List<String> values) {
            addCriterion("connect_type not in", values, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeBetween(String value1, String value2) {
            addCriterion("connect_type between", value1, value2, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeNotBetween(String value1, String value2) {
            addCriterion("connect_type not between", value1, value2, "connectType");
            return (Criteria) this;
        }

        public Criteria andSendClassNameIsNull() {
            addCriterion("send_class_name is null");
            return (Criteria) this;
        }

        public Criteria andSendClassNameIsNotNull() {
            addCriterion("send_class_name is not null");
            return (Criteria) this;
        }

        public Criteria andSendClassNameEqualTo(String value) {
            addCriterion("send_class_name =", value, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameNotEqualTo(String value) {
            addCriterion("send_class_name <>", value, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameGreaterThan(String value) {
            addCriterion("send_class_name >", value, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("send_class_name >=", value, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameLessThan(String value) {
            addCriterion("send_class_name <", value, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameLessThanOrEqualTo(String value) {
            addCriterion("send_class_name <=", value, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameLike(String value) {
            addCriterion("send_class_name like", value, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameNotLike(String value) {
            addCriterion("send_class_name not like", value, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameIn(List<String> values) {
            addCriterion("send_class_name in", values, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameNotIn(List<String> values) {
            addCriterion("send_class_name not in", values, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameBetween(String value1, String value2) {
            addCriterion("send_class_name between", value1, value2, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendClassNameNotBetween(String value1, String value2) {
            addCriterion("send_class_name not between", value1, value2, "sendClassName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameIsNull() {
            addCriterion("send_method_name is null");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameIsNotNull() {
            addCriterion("send_method_name is not null");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameEqualTo(String value) {
            addCriterion("send_method_name =", value, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameNotEqualTo(String value) {
            addCriterion("send_method_name <>", value, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameGreaterThan(String value) {
            addCriterion("send_method_name >", value, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameGreaterThanOrEqualTo(String value) {
            addCriterion("send_method_name >=", value, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameLessThan(String value) {
            addCriterion("send_method_name <", value, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameLessThanOrEqualTo(String value) {
            addCriterion("send_method_name <=", value, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameLike(String value) {
            addCriterion("send_method_name like", value, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameNotLike(String value) {
            addCriterion("send_method_name not like", value, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameIn(List<String> values) {
            addCriterion("send_method_name in", values, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameNotIn(List<String> values) {
            addCriterion("send_method_name not in", values, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameBetween(String value1, String value2) {
            addCriterion("send_method_name between", value1, value2, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andSendMethodNameNotBetween(String value1, String value2) {
            addCriterion("send_method_name not between", value1, value2, "sendMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameIsNull() {
            addCriterion("callback_class_name is null");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameIsNotNull() {
            addCriterion("callback_class_name is not null");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameEqualTo(String value) {
            addCriterion("callback_class_name =", value, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameNotEqualTo(String value) {
            addCriterion("callback_class_name <>", value, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameGreaterThan(String value) {
            addCriterion("callback_class_name >", value, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("callback_class_name >=", value, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameLessThan(String value) {
            addCriterion("callback_class_name <", value, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameLessThanOrEqualTo(String value) {
            addCriterion("callback_class_name <=", value, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameLike(String value) {
            addCriterion("callback_class_name like", value, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameNotLike(String value) {
            addCriterion("callback_class_name not like", value, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameIn(List<String> values) {
            addCriterion("callback_class_name in", values, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameNotIn(List<String> values) {
            addCriterion("callback_class_name not in", values, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameBetween(String value1, String value2) {
            addCriterion("callback_class_name between", value1, value2, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackClassNameNotBetween(String value1, String value2) {
            addCriterion("callback_class_name not between", value1, value2, "callbackClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameIsNull() {
            addCriterion("callback_method_name is null");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameIsNotNull() {
            addCriterion("callback_method_name is not null");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameEqualTo(String value) {
            addCriterion("callback_method_name =", value, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameNotEqualTo(String value) {
            addCriterion("callback_method_name <>", value, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameGreaterThan(String value) {
            addCriterion("callback_method_name >", value, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameGreaterThanOrEqualTo(String value) {
            addCriterion("callback_method_name >=", value, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameLessThan(String value) {
            addCriterion("callback_method_name <", value, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameLessThanOrEqualTo(String value) {
            addCriterion("callback_method_name <=", value, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameLike(String value) {
            addCriterion("callback_method_name like", value, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameNotLike(String value) {
            addCriterion("callback_method_name not like", value, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameIn(List<String> values) {
            addCriterion("callback_method_name in", values, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameNotIn(List<String> values) {
            addCriterion("callback_method_name not in", values, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameBetween(String value1, String value2) {
            addCriterion("callback_method_name between", value1, value2, "callbackMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackMethodNameNotBetween(String value1, String value2) {
            addCriterion("callback_method_name not between", value1, value2, "callbackMethodName");
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

        public Criteria andCallbackIncrementLastidIsNull() {
            addCriterion("callback_increment_lastid is null");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidIsNotNull() {
            addCriterion("callback_increment_lastid is not null");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidEqualTo(String value) {
            addCriterion("callback_increment_lastid =", value, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidNotEqualTo(String value) {
            addCriterion("callback_increment_lastid <>", value, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidGreaterThan(String value) {
            addCriterion("callback_increment_lastid >", value, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidGreaterThanOrEqualTo(String value) {
            addCriterion("callback_increment_lastid >=", value, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidLessThan(String value) {
            addCriterion("callback_increment_lastid <", value, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidLessThanOrEqualTo(String value) {
            addCriterion("callback_increment_lastid <=", value, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidLike(String value) {
            addCriterion("callback_increment_lastid like", value, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidNotLike(String value) {
            addCriterion("callback_increment_lastid not like", value, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidIn(List<String> values) {
            addCriterion("callback_increment_lastid in", values, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidNotIn(List<String> values) {
            addCriterion("callback_increment_lastid not in", values, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidBetween(String value1, String value2) {
            addCriterion("callback_increment_lastid between", value1, value2, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLastidNotBetween(String value1, String value2) {
            addCriterion("callback_increment_lastid not between", value1, value2, "callbackIncrementLastid");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeIsNull() {
            addCriterion("callback_increment_lasttime is null");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeIsNotNull() {
            addCriterion("callback_increment_lasttime is not null");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeEqualTo(Date value) {
            addCriterion("callback_increment_lasttime =", value, "callbackIncrementLasttime");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeNotEqualTo(Date value) {
            addCriterion("callback_increment_lasttime <>", value, "callbackIncrementLasttime");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeGreaterThan(Date value) {
            addCriterion("callback_increment_lasttime >", value, "callbackIncrementLasttime");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("callback_increment_lasttime >=", value, "callbackIncrementLasttime");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeLessThan(Date value) {
            addCriterion("callback_increment_lasttime <", value, "callbackIncrementLasttime");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeLessThanOrEqualTo(Date value) {
            addCriterion("callback_increment_lasttime <=", value, "callbackIncrementLasttime");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeIn(List<Date> values) {
            addCriterion("callback_increment_lasttime in", values, "callbackIncrementLasttime");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeNotIn(List<Date> values) {
            addCriterion("callback_increment_lasttime not in", values, "callbackIncrementLasttime");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeBetween(Date value1, Date value2) {
            addCriterion("callback_increment_lasttime between", value1, value2, "callbackIncrementLasttime");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementLasttimeNotBetween(Date value1, Date value2) {
            addCriterion("callback_increment_lasttime not between", value1, value2, "callbackIncrementLasttime");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameIsNull() {
            addCriterion("callback_increment_class_name is null");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameIsNotNull() {
            addCriterion("callback_increment_class_name is not null");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameEqualTo(String value) {
            addCriterion("callback_increment_class_name =", value, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameNotEqualTo(String value) {
            addCriterion("callback_increment_class_name <>", value, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameGreaterThan(String value) {
            addCriterion("callback_increment_class_name >", value, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("callback_increment_class_name >=", value, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameLessThan(String value) {
            addCriterion("callback_increment_class_name <", value, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameLessThanOrEqualTo(String value) {
            addCriterion("callback_increment_class_name <=", value, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameLike(String value) {
            addCriterion("callback_increment_class_name like", value, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameNotLike(String value) {
            addCriterion("callback_increment_class_name not like", value, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameIn(List<String> values) {
            addCriterion("callback_increment_class_name in", values, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameNotIn(List<String> values) {
            addCriterion("callback_increment_class_name not in", values, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameBetween(String value1, String value2) {
            addCriterion("callback_increment_class_name between", value1, value2, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementClassNameNotBetween(String value1, String value2) {
            addCriterion("callback_increment_class_name not between", value1, value2, "callbackIncrementClassName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameIsNull() {
            addCriterion("callback_increment_method_name is null");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameIsNotNull() {
            addCriterion("callback_increment_method_name is not null");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameEqualTo(String value) {
            addCriterion("callback_increment_method_name =", value, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameNotEqualTo(String value) {
            addCriterion("callback_increment_method_name <>", value, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameGreaterThan(String value) {
            addCriterion("callback_increment_method_name >", value, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameGreaterThanOrEqualTo(String value) {
            addCriterion("callback_increment_method_name >=", value, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameLessThan(String value) {
            addCriterion("callback_increment_method_name <", value, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameLessThanOrEqualTo(String value) {
            addCriterion("callback_increment_method_name <=", value, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameLike(String value) {
            addCriterion("callback_increment_method_name like", value, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameNotLike(String value) {
            addCriterion("callback_increment_method_name not like", value, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameIn(List<String> values) {
            addCriterion("callback_increment_method_name in", values, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameNotIn(List<String> values) {
            addCriterion("callback_increment_method_name not in", values, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameBetween(String value1, String value2) {
            addCriterion("callback_increment_method_name between", value1, value2, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andCallbackIncrementMethodNameNotBetween(String value1, String value2) {
            addCriterion("callback_increment_method_name not between", value1, value2, "callbackIncrementMethodName");
            return (Criteria) this;
        }

        public Criteria andWarningMailToIsNull() {
            addCriterion("warning_mail_to is null");
            return (Criteria) this;
        }

        public Criteria andWarningMailToIsNotNull() {
            addCriterion("warning_mail_to is not null");
            return (Criteria) this;
        }

        public Criteria andWarningMailToEqualTo(String value) {
            addCriterion("warning_mail_to =", value, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToNotEqualTo(String value) {
            addCriterion("warning_mail_to <>", value, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToGreaterThan(String value) {
            addCriterion("warning_mail_to >", value, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToGreaterThanOrEqualTo(String value) {
            addCriterion("warning_mail_to >=", value, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToLessThan(String value) {
            addCriterion("warning_mail_to <", value, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToLessThanOrEqualTo(String value) {
            addCriterion("warning_mail_to <=", value, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToLike(String value) {
            addCriterion("warning_mail_to like", value, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToNotLike(String value) {
            addCriterion("warning_mail_to not like", value, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToIn(List<String> values) {
            addCriterion("warning_mail_to in", values, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToNotIn(List<String> values) {
            addCriterion("warning_mail_to not in", values, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToBetween(String value1, String value2) {
            addCriterion("warning_mail_to between", value1, value2, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailToNotBetween(String value1, String value2) {
            addCriterion("warning_mail_to not between", value1, value2, "warningMailTo");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcIsNull() {
            addCriterion("warning_mail_cc is null");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcIsNotNull() {
            addCriterion("warning_mail_cc is not null");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcEqualTo(String value) {
            addCriterion("warning_mail_cc =", value, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcNotEqualTo(String value) {
            addCriterion("warning_mail_cc <>", value, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcGreaterThan(String value) {
            addCriterion("warning_mail_cc >", value, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcGreaterThanOrEqualTo(String value) {
            addCriterion("warning_mail_cc >=", value, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcLessThan(String value) {
            addCriterion("warning_mail_cc <", value, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcLessThanOrEqualTo(String value) {
            addCriterion("warning_mail_cc <=", value, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcLike(String value) {
            addCriterion("warning_mail_cc like", value, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcNotLike(String value) {
            addCriterion("warning_mail_cc not like", value, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcIn(List<String> values) {
            addCriterion("warning_mail_cc in", values, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcNotIn(List<String> values) {
            addCriterion("warning_mail_cc not in", values, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcBetween(String value1, String value2) {
            addCriterion("warning_mail_cc between", value1, value2, "warningMailCc");
            return (Criteria) this;
        }

        public Criteria andWarningMailCcNotBetween(String value1, String value2) {
            addCriterion("warning_mail_cc not between", value1, value2, "warningMailCc");
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