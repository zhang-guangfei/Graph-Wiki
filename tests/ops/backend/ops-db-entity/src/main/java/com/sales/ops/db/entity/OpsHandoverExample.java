package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsHandoverExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsHandoverExample() {
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

        public Criteria andInvoicenoIsNull() {
            addCriterion("invoiceNo is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIsNotNull() {
            addCriterion("invoiceNo is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenoEqualTo(String value) {
            addCriterion("invoiceNo =", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotEqualTo(String value) {
            addCriterion("invoiceNo <>", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThan(String value) {
            addCriterion("invoiceNo >", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoGreaterThanOrEqualTo(String value) {
            addCriterion("invoiceNo >=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThan(String value) {
            addCriterion("invoiceNo <", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLessThanOrEqualTo(String value) {
            addCriterion("invoiceNo <=", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoLike(String value) {
            addCriterion("invoiceNo like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotLike(String value) {
            addCriterion("invoiceNo not like", value, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoIn(List<String> values) {
            addCriterion("invoiceNo in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotIn(List<String> values) {
            addCriterion("invoiceNo not in", values, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoBetween(String value1, String value2) {
            addCriterion("invoiceNo between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andInvoicenoNotBetween(String value1, String value2) {
            addCriterion("invoiceNo not between", value1, value2, "invoiceno");
            return (Criteria) this;
        }

        public Criteria andHandDateIsNull() {
            addCriterion("hand_date is null");
            return (Criteria) this;
        }

        public Criteria andHandDateIsNotNull() {
            addCriterion("hand_date is not null");
            return (Criteria) this;
        }

        public Criteria andHandDateEqualTo(Date value) {
            addCriterion("hand_date =", value, "handDate");
            return (Criteria) this;
        }

        public Criteria andHandDateNotEqualTo(Date value) {
            addCriterion("hand_date <>", value, "handDate");
            return (Criteria) this;
        }

        public Criteria andHandDateGreaterThan(Date value) {
            addCriterion("hand_date >", value, "handDate");
            return (Criteria) this;
        }

        public Criteria andHandDateGreaterThanOrEqualTo(Date value) {
            addCriterion("hand_date >=", value, "handDate");
            return (Criteria) this;
        }

        public Criteria andHandDateLessThan(Date value) {
            addCriterion("hand_date <", value, "handDate");
            return (Criteria) this;
        }

        public Criteria andHandDateLessThanOrEqualTo(Date value) {
            addCriterion("hand_date <=", value, "handDate");
            return (Criteria) this;
        }

        public Criteria andHandDateIn(List<Date> values) {
            addCriterion("hand_date in", values, "handDate");
            return (Criteria) this;
        }

        public Criteria andHandDateNotIn(List<Date> values) {
            addCriterion("hand_date not in", values, "handDate");
            return (Criteria) this;
        }

        public Criteria andHandDateBetween(Date value1, Date value2) {
            addCriterion("hand_date between", value1, value2, "handDate");
            return (Criteria) this;
        }

        public Criteria andHandDateNotBetween(Date value1, Date value2) {
            addCriterion("hand_date not between", value1, value2, "handDate");
            return (Criteria) this;
        }

        public Criteria andDoIdIsNull() {
            addCriterion("do_id is null");
            return (Criteria) this;
        }

        public Criteria andDoIdIsNotNull() {
            addCriterion("do_id is not null");
            return (Criteria) this;
        }

        public Criteria andDoIdEqualTo(String value) {
            addCriterion("do_id =", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotEqualTo(String value) {
            addCriterion("do_id <>", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdGreaterThan(String value) {
            addCriterion("do_id >", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdGreaterThanOrEqualTo(String value) {
            addCriterion("do_id >=", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLessThan(String value) {
            addCriterion("do_id <", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLessThanOrEqualTo(String value) {
            addCriterion("do_id <=", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdLike(String value) {
            addCriterion("do_id like", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotLike(String value) {
            addCriterion("do_id not like", value, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdIn(List<String> values) {
            addCriterion("do_id in", values, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotIn(List<String> values) {
            addCriterion("do_id not in", values, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdBetween(String value1, String value2) {
            addCriterion("do_id between", value1, value2, "doId");
            return (Criteria) this;
        }

        public Criteria andDoIdNotBetween(String value1, String value2) {
            addCriterion("do_id not between", value1, value2, "doId");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNull() {
            addCriterion("modelNo is null");
            return (Criteria) this;
        }

        public Criteria andModelnoIsNotNull() {
            addCriterion("modelNo is not null");
            return (Criteria) this;
        }

        public Criteria andModelnoEqualTo(String value) {
            addCriterion("modelNo =", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotEqualTo(String value) {
            addCriterion("modelNo <>", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThan(String value) {
            addCriterion("modelNo >", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoGreaterThanOrEqualTo(String value) {
            addCriterion("modelNo >=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThan(String value) {
            addCriterion("modelNo <", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLessThanOrEqualTo(String value) {
            addCriterion("modelNo <=", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoLike(String value) {
            addCriterion("modelNo like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotLike(String value) {
            addCriterion("modelNo not like", value, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoIn(List<String> values) {
            addCriterion("modelNo in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotIn(List<String> values) {
            addCriterion("modelNo not in", values, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoBetween(String value1, String value2) {
            addCriterion("modelNo between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andModelnoNotBetween(String value1, String value2) {
            addCriterion("modelNo not between", value1, value2, "modelno");
            return (Criteria) this;
        }

        public Criteria andQtyIsNull() {
            addCriterion("qty is null");
            return (Criteria) this;
        }

        public Criteria andQtyIsNotNull() {
            addCriterion("qty is not null");
            return (Criteria) this;
        }

        public Criteria andQtyEqualTo(Integer value) {
            addCriterion("qty =", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotEqualTo(Integer value) {
            addCriterion("qty <>", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThan(Integer value) {
            addCriterion("qty >", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("qty >=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThan(Integer value) {
            addCriterion("qty <", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThanOrEqualTo(Integer value) {
            addCriterion("qty <=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyIn(List<Integer> values) {
            addCriterion("qty in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotIn(List<Integer> values) {
            addCriterion("qty not in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyBetween(Integer value1, Integer value2) {
            addCriterion("qty between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("qty not between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andCasenoIsNull() {
            addCriterion("caseno is null");
            return (Criteria) this;
        }

        public Criteria andCasenoIsNotNull() {
            addCriterion("caseno is not null");
            return (Criteria) this;
        }

        public Criteria andCasenoEqualTo(String value) {
            addCriterion("caseno =", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotEqualTo(String value) {
            addCriterion("caseno <>", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoGreaterThan(String value) {
            addCriterion("caseno >", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoGreaterThanOrEqualTo(String value) {
            addCriterion("caseno >=", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoLessThan(String value) {
            addCriterion("caseno <", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoLessThanOrEqualTo(String value) {
            addCriterion("caseno <=", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoLike(String value) {
            addCriterion("caseno like", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotLike(String value) {
            addCriterion("caseno not like", value, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoIn(List<String> values) {
            addCriterion("caseno in", values, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotIn(List<String> values) {
            addCriterion("caseno not in", values, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoBetween(String value1, String value2) {
            addCriterion("caseno between", value1, value2, "caseno");
            return (Criteria) this;
        }

        public Criteria andCasenoNotBetween(String value1, String value2) {
            addCriterion("caseno not between", value1, value2, "caseno");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNull() {
            addCriterion("barcode is null");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNotNull() {
            addCriterion("barcode is not null");
            return (Criteria) this;
        }

        public Criteria andBarcodeEqualTo(String value) {
            addCriterion("barcode =", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotEqualTo(String value) {
            addCriterion("barcode <>", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThan(String value) {
            addCriterion("barcode >", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThanOrEqualTo(String value) {
            addCriterion("barcode >=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThan(String value) {
            addCriterion("barcode <", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThanOrEqualTo(String value) {
            addCriterion("barcode <=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLike(String value) {
            addCriterion("barcode like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotLike(String value) {
            addCriterion("barcode not like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeIn(List<String> values) {
            addCriterion("barcode in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotIn(List<String> values) {
            addCriterion("barcode not in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeBetween(String value1, String value2) {
            addCriterion("barcode between", value1, value2, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotBetween(String value1, String value2) {
            addCriterion("barcode not between", value1, value2, "barcode");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNull() {
            addCriterion("cre_time is null");
            return (Criteria) this;
        }

        public Criteria andCreTimeIsNotNull() {
            addCriterion("cre_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreTimeEqualTo(Date value) {
            addCriterion("cre_time =", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotEqualTo(Date value) {
            addCriterion("cre_time <>", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThan(Date value) {
            addCriterion("cre_time >", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cre_time >=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThan(Date value) {
            addCriterion("cre_time <", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeLessThanOrEqualTo(Date value) {
            addCriterion("cre_time <=", value, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeIn(List<Date> values) {
            addCriterion("cre_time in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotIn(List<Date> values) {
            addCriterion("cre_time not in", values, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeBetween(Date value1, Date value2) {
            addCriterion("cre_time between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreTimeNotBetween(Date value1, Date value2) {
            addCriterion("cre_time not between", value1, value2, "creTime");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNull() {
            addCriterion("delflag is null");
            return (Criteria) this;
        }

        public Criteria andDelflagIsNotNull() {
            addCriterion("delflag is not null");
            return (Criteria) this;
        }

        public Criteria andDelflagEqualTo(Integer value) {
            addCriterion("delflag =", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotEqualTo(Integer value) {
            addCriterion("delflag <>", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThan(Integer value) {
            addCriterion("delflag >", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("delflag >=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThan(Integer value) {
            addCriterion("delflag <", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThanOrEqualTo(Integer value) {
            addCriterion("delflag <=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagIn(List<Integer> values) {
            addCriterion("delflag in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotIn(List<Integer> values) {
            addCriterion("delflag not in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagBetween(Integer value1, Integer value2) {
            addCriterion("delflag between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotBetween(Integer value1, Integer value2) {
            addCriterion("delflag not between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeIsNull() {
            addCriterion("fromWarehouseCode is null");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeIsNotNull() {
            addCriterion("fromWarehouseCode is not null");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeEqualTo(String value) {
            addCriterion("fromWarehouseCode =", value, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeNotEqualTo(String value) {
            addCriterion("fromWarehouseCode <>", value, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeGreaterThan(String value) {
            addCriterion("fromWarehouseCode >", value, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeGreaterThanOrEqualTo(String value) {
            addCriterion("fromWarehouseCode >=", value, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeLessThan(String value) {
            addCriterion("fromWarehouseCode <", value, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeLessThanOrEqualTo(String value) {
            addCriterion("fromWarehouseCode <=", value, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeLike(String value) {
            addCriterion("fromWarehouseCode like", value, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeNotLike(String value) {
            addCriterion("fromWarehouseCode not like", value, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeIn(List<String> values) {
            addCriterion("fromWarehouseCode in", values, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeNotIn(List<String> values) {
            addCriterion("fromWarehouseCode not in", values, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeBetween(String value1, String value2) {
            addCriterion("fromWarehouseCode between", value1, value2, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andFromwarehousecodeNotBetween(String value1, String value2) {
            addCriterion("fromWarehouseCode not between", value1, value2, "fromwarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeIsNull() {
            addCriterion("toWarehouseCode is null");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeIsNotNull() {
            addCriterion("toWarehouseCode is not null");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeEqualTo(String value) {
            addCriterion("toWarehouseCode =", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeNotEqualTo(String value) {
            addCriterion("toWarehouseCode <>", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeGreaterThan(String value) {
            addCriterion("toWarehouseCode >", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeGreaterThanOrEqualTo(String value) {
            addCriterion("toWarehouseCode >=", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeLessThan(String value) {
            addCriterion("toWarehouseCode <", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeLessThanOrEqualTo(String value) {
            addCriterion("toWarehouseCode <=", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeLike(String value) {
            addCriterion("toWarehouseCode like", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeNotLike(String value) {
            addCriterion("toWarehouseCode not like", value, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeIn(List<String> values) {
            addCriterion("toWarehouseCode in", values, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeNotIn(List<String> values) {
            addCriterion("toWarehouseCode not in", values, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeBetween(String value1, String value2) {
            addCriterion("toWarehouseCode between", value1, value2, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andTowarehousecodeNotBetween(String value1, String value2) {
            addCriterion("toWarehouseCode not between", value1, value2, "towarehousecode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeIsNull() {
            addCriterion("logisticsCode is null");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeIsNotNull() {
            addCriterion("logisticsCode is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeEqualTo(String value) {
            addCriterion("logisticsCode =", value, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeNotEqualTo(String value) {
            addCriterion("logisticsCode <>", value, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeGreaterThan(String value) {
            addCriterion("logisticsCode >", value, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeGreaterThanOrEqualTo(String value) {
            addCriterion("logisticsCode >=", value, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeLessThan(String value) {
            addCriterion("logisticsCode <", value, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeLessThanOrEqualTo(String value) {
            addCriterion("logisticsCode <=", value, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeLike(String value) {
            addCriterion("logisticsCode like", value, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeNotLike(String value) {
            addCriterion("logisticsCode not like", value, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeIn(List<String> values) {
            addCriterion("logisticsCode in", values, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeNotIn(List<String> values) {
            addCriterion("logisticsCode not in", values, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeBetween(String value1, String value2) {
            addCriterion("logisticsCode between", value1, value2, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andLogisticscodeNotBetween(String value1, String value2) {
            addCriterion("logisticsCode not between", value1, value2, "logisticscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeIsNull() {
            addCriterion("expressCode is null");
            return (Criteria) this;
        }

        public Criteria andExpresscodeIsNotNull() {
            addCriterion("expressCode is not null");
            return (Criteria) this;
        }

        public Criteria andExpresscodeEqualTo(String value) {
            addCriterion("expressCode =", value, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeNotEqualTo(String value) {
            addCriterion("expressCode <>", value, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeGreaterThan(String value) {
            addCriterion("expressCode >", value, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeGreaterThanOrEqualTo(String value) {
            addCriterion("expressCode >=", value, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeLessThan(String value) {
            addCriterion("expressCode <", value, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeLessThanOrEqualTo(String value) {
            addCriterion("expressCode <=", value, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeLike(String value) {
            addCriterion("expressCode like", value, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeNotLike(String value) {
            addCriterion("expressCode not like", value, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeIn(List<String> values) {
            addCriterion("expressCode in", values, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeNotIn(List<String> values) {
            addCriterion("expressCode not in", values, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeBetween(String value1, String value2) {
            addCriterion("expressCode between", value1, value2, "expresscode");
            return (Criteria) this;
        }

        public Criteria andExpresscodeNotBetween(String value1, String value2) {
            addCriterion("expressCode not between", value1, value2, "expresscode");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdIsNull() {
            addCriterion("invoice_id is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdIsNotNull() {
            addCriterion("invoice_id is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdEqualTo(Long value) {
            addCriterion("invoice_id =", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdNotEqualTo(Long value) {
            addCriterion("invoice_id <>", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdGreaterThan(Long value) {
            addCriterion("invoice_id >", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("invoice_id >=", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdLessThan(Long value) {
            addCriterion("invoice_id <", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdLessThanOrEqualTo(Long value) {
            addCriterion("invoice_id <=", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdIn(List<Long> values) {
            addCriterion("invoice_id in", values, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdNotIn(List<Long> values) {
            addCriterion("invoice_id not in", values, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdBetween(Long value1, Long value2) {
            addCriterion("invoice_id between", value1, value2, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdNotBetween(Long value1, Long value2) {
            addCriterion("invoice_id not between", value1, value2, "invoiceId");
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