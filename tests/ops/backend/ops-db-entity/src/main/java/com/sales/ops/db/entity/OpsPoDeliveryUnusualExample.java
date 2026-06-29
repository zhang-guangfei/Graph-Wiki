package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPoDeliveryUnusualExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPoDeliveryUnusualExample() {
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

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andItemNoIsNull() {
            addCriterion("item_no is null");
            return (Criteria) this;
        }

        public Criteria andItemNoIsNotNull() {
            addCriterion("item_no is not null");
            return (Criteria) this;
        }

        public Criteria andItemNoEqualTo(Integer value) {
            addCriterion("item_no =", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoNotEqualTo(Integer value) {
            addCriterion("item_no <>", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoGreaterThan(Integer value) {
            addCriterion("item_no >", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_no >=", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoLessThan(Integer value) {
            addCriterion("item_no <", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoLessThanOrEqualTo(Integer value) {
            addCriterion("item_no <=", value, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoIn(List<Integer> values) {
            addCriterion("item_no in", values, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoNotIn(List<Integer> values) {
            addCriterion("item_no not in", values, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoBetween(Integer value1, Integer value2) {
            addCriterion("item_no between", value1, value2, "itemNo");
            return (Criteria) this;
        }

        public Criteria andItemNoNotBetween(Integer value1, Integer value2) {
            addCriterion("item_no not between", value1, value2, "itemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoIsNull() {
            addCriterion("split_item_no is null");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoIsNotNull() {
            addCriterion("split_item_no is not null");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoEqualTo(Integer value) {
            addCriterion("split_item_no =", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoNotEqualTo(Integer value) {
            addCriterion("split_item_no <>", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoGreaterThan(Integer value) {
            addCriterion("split_item_no >", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("split_item_no >=", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoLessThan(Integer value) {
            addCriterion("split_item_no <", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoLessThanOrEqualTo(Integer value) {
            addCriterion("split_item_no <=", value, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoIn(List<Integer> values) {
            addCriterion("split_item_no in", values, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoNotIn(List<Integer> values) {
            addCriterion("split_item_no not in", values, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoBetween(Integer value1, Integer value2) {
            addCriterion("split_item_no between", value1, value2, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andSplitItemNoNotBetween(Integer value1, Integer value2) {
            addCriterion("split_item_no not between", value1, value2, "splitItemNo");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("modelno is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelno is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelno =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelno <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelno >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelno >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelno <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelno <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelno like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelno not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelno in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelno not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelno between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelno not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyIsNull() {
            addCriterion("unusual_classify is null");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyIsNotNull() {
            addCriterion("unusual_classify is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyEqualTo(String value) {
            addCriterion("unusual_classify =", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyNotEqualTo(String value) {
            addCriterion("unusual_classify <>", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyGreaterThan(String value) {
            addCriterion("unusual_classify >", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_classify >=", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyLessThan(String value) {
            addCriterion("unusual_classify <", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyLessThanOrEqualTo(String value) {
            addCriterion("unusual_classify <=", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyLike(String value) {
            addCriterion("unusual_classify like", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyNotLike(String value) {
            addCriterion("unusual_classify not like", value, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyIn(List<String> values) {
            addCriterion("unusual_classify in", values, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyNotIn(List<String> values) {
            addCriterion("unusual_classify not in", values, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyBetween(String value1, String value2) {
            addCriterion("unusual_classify between", value1, value2, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualClassifyNotBetween(String value1, String value2) {
            addCriterion("unusual_classify not between", value1, value2, "unusualClassify");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeIsNull() {
            addCriterion("unusual_type is null");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeIsNotNull() {
            addCriterion("unusual_type is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeEqualTo(String value) {
            addCriterion("unusual_type =", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotEqualTo(String value) {
            addCriterion("unusual_type <>", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeGreaterThan(String value) {
            addCriterion("unusual_type >", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_type >=", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeLessThan(String value) {
            addCriterion("unusual_type <", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeLessThanOrEqualTo(String value) {
            addCriterion("unusual_type <=", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeLike(String value) {
            addCriterion("unusual_type like", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotLike(String value) {
            addCriterion("unusual_type not like", value, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeIn(List<String> values) {
            addCriterion("unusual_type in", values, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotIn(List<String> values) {
            addCriterion("unusual_type not in", values, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeBetween(String value1, String value2) {
            addCriterion("unusual_type between", value1, value2, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualTypeNotBetween(String value1, String value2) {
            addCriterion("unusual_type not between", value1, value2, "unusualType");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeIsNull() {
            addCriterion("unusual_identification_code is null");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeIsNotNull() {
            addCriterion("unusual_identification_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeEqualTo(String value) {
            addCriterion("unusual_identification_code =", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeNotEqualTo(String value) {
            addCriterion("unusual_identification_code <>", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeGreaterThan(String value) {
            addCriterion("unusual_identification_code >", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_identification_code >=", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeLessThan(String value) {
            addCriterion("unusual_identification_code <", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeLessThanOrEqualTo(String value) {
            addCriterion("unusual_identification_code <=", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeLike(String value) {
            addCriterion("unusual_identification_code like", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeNotLike(String value) {
            addCriterion("unusual_identification_code not like", value, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeIn(List<String> values) {
            addCriterion("unusual_identification_code in", values, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeNotIn(List<String> values) {
            addCriterion("unusual_identification_code not in", values, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeBetween(String value1, String value2) {
            addCriterion("unusual_identification_code between", value1, value2, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualIdentificationCodeNotBetween(String value1, String value2) {
            addCriterion("unusual_identification_code not between", value1, value2, "unusualIdentificationCode");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngIsNull() {
            addCriterion("unusual_desc_eng is null");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngIsNotNull() {
            addCriterion("unusual_desc_eng is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngEqualTo(String value) {
            addCriterion("unusual_desc_eng =", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngNotEqualTo(String value) {
            addCriterion("unusual_desc_eng <>", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngGreaterThan(String value) {
            addCriterion("unusual_desc_eng >", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngGreaterThanOrEqualTo(String value) {
            addCriterion("unusual_desc_eng >=", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngLessThan(String value) {
            addCriterion("unusual_desc_eng <", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngLessThanOrEqualTo(String value) {
            addCriterion("unusual_desc_eng <=", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngLike(String value) {
            addCriterion("unusual_desc_eng like", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngNotLike(String value) {
            addCriterion("unusual_desc_eng not like", value, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngIn(List<String> values) {
            addCriterion("unusual_desc_eng in", values, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngNotIn(List<String> values) {
            addCriterion("unusual_desc_eng not in", values, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngBetween(String value1, String value2) {
            addCriterion("unusual_desc_eng between", value1, value2, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andUnusualDescEngNotBetween(String value1, String value2) {
            addCriterion("unusual_desc_eng not between", value1, value2, "unusualDescEng");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIsNull() {
            addCriterion("handle_time is null");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIsNotNull() {
            addCriterion("handle_time is not null");
            return (Criteria) this;
        }

        public Criteria andHandleTimeEqualTo(Date value) {
            addCriterion("handle_time =", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotEqualTo(Date value) {
            addCriterion("handle_time <>", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeGreaterThan(Date value) {
            addCriterion("handle_time >", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("handle_time >=", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLessThan(Date value) {
            addCriterion("handle_time <", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLessThanOrEqualTo(Date value) {
            addCriterion("handle_time <=", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIn(List<Date> values) {
            addCriterion("handle_time in", values, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotIn(List<Date> values) {
            addCriterion("handle_time not in", values, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeBetween(Date value1, Date value2) {
            addCriterion("handle_time between", value1, value2, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotBetween(Date value1, Date value2) {
            addCriterion("handle_time not between", value1, value2, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNull() {
            addCriterion("handle_status is null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNotNull() {
            addCriterion("handle_status is not null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusEqualTo(Integer value) {
            addCriterion("handle_status =", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotEqualTo(Integer value) {
            addCriterion("handle_status <>", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThan(Integer value) {
            addCriterion("handle_status >", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_status >=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThan(Integer value) {
            addCriterion("handle_status <", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThanOrEqualTo(Integer value) {
            addCriterion("handle_status <=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIn(List<Integer> values) {
            addCriterion("handle_status in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotIn(List<Integer> values) {
            addCriterion("handle_status not in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusBetween(Integer value1, Integer value2) {
            addCriterion("handle_status between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_status not between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleResultIsNull() {
            addCriterion("handle_result is null");
            return (Criteria) this;
        }

        public Criteria andHandleResultIsNotNull() {
            addCriterion("handle_result is not null");
            return (Criteria) this;
        }

        public Criteria andHandleResultEqualTo(String value) {
            addCriterion("handle_result =", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultNotEqualTo(String value) {
            addCriterion("handle_result <>", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultGreaterThan(String value) {
            addCriterion("handle_result >", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultGreaterThanOrEqualTo(String value) {
            addCriterion("handle_result >=", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultLessThan(String value) {
            addCriterion("handle_result <", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultLessThanOrEqualTo(String value) {
            addCriterion("handle_result <=", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultLike(String value) {
            addCriterion("handle_result like", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultNotLike(String value) {
            addCriterion("handle_result not like", value, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultIn(List<String> values) {
            addCriterion("handle_result in", values, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultNotIn(List<String> values) {
            addCriterion("handle_result not in", values, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultBetween(String value1, String value2) {
            addCriterion("handle_result between", value1, value2, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleResultNotBetween(String value1, String value2) {
            addCriterion("handle_result not between", value1, value2, "handleResult");
            return (Criteria) this;
        }

        public Criteria andHandleUserIsNull() {
            addCriterion("handle_user is null");
            return (Criteria) this;
        }

        public Criteria andHandleUserIsNotNull() {
            addCriterion("handle_user is not null");
            return (Criteria) this;
        }

        public Criteria andHandleUserEqualTo(String value) {
            addCriterion("handle_user =", value, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserNotEqualTo(String value) {
            addCriterion("handle_user <>", value, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserGreaterThan(String value) {
            addCriterion("handle_user >", value, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserGreaterThanOrEqualTo(String value) {
            addCriterion("handle_user >=", value, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserLessThan(String value) {
            addCriterion("handle_user <", value, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserLessThanOrEqualTo(String value) {
            addCriterion("handle_user <=", value, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserLike(String value) {
            addCriterion("handle_user like", value, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserNotLike(String value) {
            addCriterion("handle_user not like", value, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserIn(List<String> values) {
            addCriterion("handle_user in", values, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserNotIn(List<String> values) {
            addCriterion("handle_user not in", values, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserBetween(String value1, String value2) {
            addCriterion("handle_user between", value1, value2, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserNotBetween(String value1, String value2) {
            addCriterion("handle_user not between", value1, value2, "handleUser");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameIsNull() {
            addCriterion("handle_user_name is null");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameIsNotNull() {
            addCriterion("handle_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameEqualTo(String value) {
            addCriterion("handle_user_name =", value, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameNotEqualTo(String value) {
            addCriterion("handle_user_name <>", value, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameGreaterThan(String value) {
            addCriterion("handle_user_name >", value, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("handle_user_name >=", value, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameLessThan(String value) {
            addCriterion("handle_user_name <", value, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameLessThanOrEqualTo(String value) {
            addCriterion("handle_user_name <=", value, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameLike(String value) {
            addCriterion("handle_user_name like", value, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameNotLike(String value) {
            addCriterion("handle_user_name not like", value, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameIn(List<String> values) {
            addCriterion("handle_user_name in", values, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameNotIn(List<String> values) {
            addCriterion("handle_user_name not in", values, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameBetween(String value1, String value2) {
            addCriterion("handle_user_name between", value1, value2, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andHandleUserNameNotBetween(String value1, String value2) {
            addCriterion("handle_user_name not between", value1, value2, "handleUserName");
            return (Criteria) this;
        }

        public Criteria andRecordDateIsNull() {
            addCriterion("record_date is null");
            return (Criteria) this;
        }

        public Criteria andRecordDateIsNotNull() {
            addCriterion("record_date is not null");
            return (Criteria) this;
        }

        public Criteria andRecordDateEqualTo(Date value) {
            addCriterion("record_date =", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateNotEqualTo(Date value) {
            addCriterion("record_date <>", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateGreaterThan(Date value) {
            addCriterion("record_date >", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateGreaterThanOrEqualTo(Date value) {
            addCriterion("record_date >=", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateLessThan(Date value) {
            addCriterion("record_date <", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateLessThanOrEqualTo(Date value) {
            addCriterion("record_date <=", value, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateIn(List<Date> values) {
            addCriterion("record_date in", values, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateNotIn(List<Date> values) {
            addCriterion("record_date not in", values, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateBetween(Date value1, Date value2) {
            addCriterion("record_date between", value1, value2, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordDateNotBetween(Date value1, Date value2) {
            addCriterion("record_date not between", value1, value2, "recordDate");
            return (Criteria) this;
        }

        public Criteria andRecordCountIsNull() {
            addCriterion("record_count is null");
            return (Criteria) this;
        }

        public Criteria andRecordCountIsNotNull() {
            addCriterion("record_count is not null");
            return (Criteria) this;
        }

        public Criteria andRecordCountEqualTo(Integer value) {
            addCriterion("record_count =", value, "recordCount");
            return (Criteria) this;
        }

        public Criteria andRecordCountNotEqualTo(Integer value) {
            addCriterion("record_count <>", value, "recordCount");
            return (Criteria) this;
        }

        public Criteria andRecordCountGreaterThan(Integer value) {
            addCriterion("record_count >", value, "recordCount");
            return (Criteria) this;
        }

        public Criteria andRecordCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("record_count >=", value, "recordCount");
            return (Criteria) this;
        }

        public Criteria andRecordCountLessThan(Integer value) {
            addCriterion("record_count <", value, "recordCount");
            return (Criteria) this;
        }

        public Criteria andRecordCountLessThanOrEqualTo(Integer value) {
            addCriterion("record_count <=", value, "recordCount");
            return (Criteria) this;
        }

        public Criteria andRecordCountIn(List<Integer> values) {
            addCriterion("record_count in", values, "recordCount");
            return (Criteria) this;
        }

        public Criteria andRecordCountNotIn(List<Integer> values) {
            addCriterion("record_count not in", values, "recordCount");
            return (Criteria) this;
        }

        public Criteria andRecordCountBetween(Integer value1, Integer value2) {
            addCriterion("record_count between", value1, value2, "recordCount");
            return (Criteria) this;
        }

        public Criteria andRecordCountNotBetween(Integer value1, Integer value2) {
            addCriterion("record_count not between", value1, value2, "recordCount");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Boolean value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Boolean value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Boolean value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Boolean value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Boolean> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Boolean> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
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

        public Criteria andOrderFnoIsNull() {
            addCriterion("order_fno is null");
            return (Criteria) this;
        }

        public Criteria andOrderFnoIsNotNull() {
            addCriterion("order_fno is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFnoEqualTo(String value) {
            addCriterion("order_fno =", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotEqualTo(String value) {
            addCriterion("order_fno <>", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoGreaterThan(String value) {
            addCriterion("order_fno >", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoGreaterThanOrEqualTo(String value) {
            addCriterion("order_fno >=", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLessThan(String value) {
            addCriterion("order_fno <", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLessThanOrEqualTo(String value) {
            addCriterion("order_fno <=", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoLike(String value) {
            addCriterion("order_fno like", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotLike(String value) {
            addCriterion("order_fno not like", value, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoIn(List<String> values) {
            addCriterion("order_fno in", values, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotIn(List<String> values) {
            addCriterion("order_fno not in", values, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoBetween(String value1, String value2) {
            addCriterion("order_fno between", value1, value2, "orderFno");
            return (Criteria) this;
        }

        public Criteria andOrderFnoNotBetween(String value1, String value2) {
            addCriterion("order_fno not between", value1, value2, "orderFno");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIsNull() {
            addCriterion("reply_time is null");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIsNotNull() {
            addCriterion("reply_time is not null");
            return (Criteria) this;
        }

        public Criteria andReplyTimeEqualTo(Date value) {
            addCriterion("reply_time =", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotEqualTo(Date value) {
            addCriterion("reply_time <>", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeGreaterThan(Date value) {
            addCriterion("reply_time >", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("reply_time >=", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeLessThan(Date value) {
            addCriterion("reply_time <", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("reply_time <=", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIn(List<Date> values) {
            addCriterion("reply_time in", values, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotIn(List<Date> values) {
            addCriterion("reply_time not in", values, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeBetween(Date value1, Date value2) {
            addCriterion("reply_time between", value1, value2, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("reply_time not between", value1, value2, "replyTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIsNull() {
            addCriterion("complete_time is null");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIsNotNull() {
            addCriterion("complete_time is not null");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeEqualTo(Date value) {
            addCriterion("complete_time =", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotEqualTo(Date value) {
            addCriterion("complete_time <>", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeGreaterThan(Date value) {
            addCriterion("complete_time >", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("complete_time >=", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeLessThan(Date value) {
            addCriterion("complete_time <", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("complete_time <=", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIn(List<Date> values) {
            addCriterion("complete_time in", values, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotIn(List<Date> values) {
            addCriterion("complete_time not in", values, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeBetween(Date value1, Date value2) {
            addCriterion("complete_time between", value1, value2, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("complete_time not between", value1, value2, "completeTime");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNull() {
            addCriterion("supplier_code is null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNotNull() {
            addCriterion("supplier_code is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeEqualTo(String value) {
            addCriterion("supplier_code =", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotEqualTo(String value) {
            addCriterion("supplier_code <>", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThan(String value) {
            addCriterion("supplier_code >", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_code >=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThan(String value) {
            addCriterion("supplier_code <", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThanOrEqualTo(String value) {
            addCriterion("supplier_code <=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLike(String value) {
            addCriterion("supplier_code like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotLike(String value) {
            addCriterion("supplier_code not like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIn(List<String> values) {
            addCriterion("supplier_code in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotIn(List<String> values) {
            addCriterion("supplier_code not in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeBetween(String value1, String value2) {
            addCriterion("supplier_code between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotBetween(String value1, String value2) {
            addCriterion("supplier_code not between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andReplyDateIsNull() {
            addCriterion("reply_date is null");
            return (Criteria) this;
        }

        public Criteria andReplyDateIsNotNull() {
            addCriterion("reply_date is not null");
            return (Criteria) this;
        }

        public Criteria andReplyDateEqualTo(Date value) {
            addCriterion("reply_date =", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateNotEqualTo(Date value) {
            addCriterion("reply_date <>", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateGreaterThan(Date value) {
            addCriterion("reply_date >", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateGreaterThanOrEqualTo(Date value) {
            addCriterion("reply_date >=", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateLessThan(Date value) {
            addCriterion("reply_date <", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateLessThanOrEqualTo(Date value) {
            addCriterion("reply_date <=", value, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateIn(List<Date> values) {
            addCriterion("reply_date in", values, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateNotIn(List<Date> values) {
            addCriterion("reply_date not in", values, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateBetween(Date value1, Date value2) {
            addCriterion("reply_date between", value1, value2, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyDateNotBetween(Date value1, Date value2) {
            addCriterion("reply_date not between", value1, value2, "replyDate");
            return (Criteria) this;
        }

        public Criteria andReplyContentIsNull() {
            addCriterion("reply_content is null");
            return (Criteria) this;
        }

        public Criteria andReplyContentIsNotNull() {
            addCriterion("reply_content is not null");
            return (Criteria) this;
        }

        public Criteria andReplyContentEqualTo(String value) {
            addCriterion("reply_content =", value, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentNotEqualTo(String value) {
            addCriterion("reply_content <>", value, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentGreaterThan(String value) {
            addCriterion("reply_content >", value, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentGreaterThanOrEqualTo(String value) {
            addCriterion("reply_content >=", value, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentLessThan(String value) {
            addCriterion("reply_content <", value, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentLessThanOrEqualTo(String value) {
            addCriterion("reply_content <=", value, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentLike(String value) {
            addCriterion("reply_content like", value, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentNotLike(String value) {
            addCriterion("reply_content not like", value, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentIn(List<String> values) {
            addCriterion("reply_content in", values, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentNotIn(List<String> values) {
            addCriterion("reply_content not in", values, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentBetween(String value1, String value2) {
            addCriterion("reply_content between", value1, value2, "replyContent");
            return (Criteria) this;
        }

        public Criteria andReplyContentNotBetween(String value1, String value2) {
            addCriterion("reply_content not between", value1, value2, "replyContent");
            return (Criteria) this;
        }

        public Criteria andInquiryDateIsNull() {
            addCriterion("inquiry_date is null");
            return (Criteria) this;
        }

        public Criteria andInquiryDateIsNotNull() {
            addCriterion("inquiry_date is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryDateEqualTo(Date value) {
            addCriterion("inquiry_date =", value, "inquiryDate");
            return (Criteria) this;
        }

        public Criteria andInquiryDateNotEqualTo(Date value) {
            addCriterion("inquiry_date <>", value, "inquiryDate");
            return (Criteria) this;
        }

        public Criteria andInquiryDateGreaterThan(Date value) {
            addCriterion("inquiry_date >", value, "inquiryDate");
            return (Criteria) this;
        }

        public Criteria andInquiryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("inquiry_date >=", value, "inquiryDate");
            return (Criteria) this;
        }

        public Criteria andInquiryDateLessThan(Date value) {
            addCriterion("inquiry_date <", value, "inquiryDate");
            return (Criteria) this;
        }

        public Criteria andInquiryDateLessThanOrEqualTo(Date value) {
            addCriterion("inquiry_date <=", value, "inquiryDate");
            return (Criteria) this;
        }

        public Criteria andInquiryDateIn(List<Date> values) {
            addCriterion("inquiry_date in", values, "inquiryDate");
            return (Criteria) this;
        }

        public Criteria andInquiryDateNotIn(List<Date> values) {
            addCriterion("inquiry_date not in", values, "inquiryDate");
            return (Criteria) this;
        }

        public Criteria andInquiryDateBetween(Date value1, Date value2) {
            addCriterion("inquiry_date between", value1, value2, "inquiryDate");
            return (Criteria) this;
        }

        public Criteria andInquiryDateNotBetween(Date value1, Date value2) {
            addCriterion("inquiry_date not between", value1, value2, "inquiryDate");
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