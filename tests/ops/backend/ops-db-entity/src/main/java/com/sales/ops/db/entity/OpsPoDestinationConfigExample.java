package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsPoDestinationConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsPoDestinationConfigExample() {
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

        public Criteria andOrdertypeIsNull() {
            addCriterion("orderType is null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNotNull() {
            addCriterion("orderType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeEqualTo(String value) {
            addCriterion("orderType =", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotEqualTo(String value) {
            addCriterion("orderType <>", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThan(String value) {
            addCriterion("orderType >", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThanOrEqualTo(String value) {
            addCriterion("orderType >=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThan(String value) {
            addCriterion("orderType <", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThanOrEqualTo(String value) {
            addCriterion("orderType <=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLike(String value) {
            addCriterion("orderType like", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotLike(String value) {
            addCriterion("orderType not like", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIn(List<String> values) {
            addCriterion("orderType in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotIn(List<String> values) {
            addCriterion("orderType not in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeBetween(String value1, String value2) {
            addCriterion("orderType between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotBetween(String value1, String value2) {
            addCriterion("orderType not between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andProducttypeIsNull() {
            addCriterion("productType is null");
            return (Criteria) this;
        }

        public Criteria andProducttypeIsNotNull() {
            addCriterion("productType is not null");
            return (Criteria) this;
        }

        public Criteria andProducttypeEqualTo(Integer value) {
            addCriterion("productType =", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotEqualTo(Integer value) {
            addCriterion("productType <>", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeGreaterThan(Integer value) {
            addCriterion("productType >", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("productType >=", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeLessThan(Integer value) {
            addCriterion("productType <", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeLessThanOrEqualTo(Integer value) {
            addCriterion("productType <=", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeIn(List<Integer> values) {
            addCriterion("productType in", values, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotIn(List<Integer> values) {
            addCriterion("productType not in", values, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeBetween(Integer value1, Integer value2) {
            addCriterion("productType between", value1, value2, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotBetween(Integer value1, Integer value2) {
            addCriterion("productType not between", value1, value2, "producttype");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNull() {
            addCriterion("customerNo is null");
            return (Criteria) this;
        }

        public Criteria andCustomernoIsNotNull() {
            addCriterion("customerNo is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernoEqualTo(String value) {
            addCriterion("customerNo =", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotEqualTo(String value) {
            addCriterion("customerNo <>", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThan(String value) {
            addCriterion("customerNo >", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoGreaterThanOrEqualTo(String value) {
            addCriterion("customerNo >=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThan(String value) {
            addCriterion("customerNo <", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLessThanOrEqualTo(String value) {
            addCriterion("customerNo <=", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoLike(String value) {
            addCriterion("customerNo like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotLike(String value) {
            addCriterion("customerNo not like", value, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoIn(List<String> values) {
            addCriterion("customerNo in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotIn(List<String> values) {
            addCriterion("customerNo not in", values, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoBetween(String value1, String value2) {
            addCriterion("customerNo between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andCustomernoNotBetween(String value1, String value2) {
            addCriterion("customerNo not between", value1, value2, "customerno");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNull() {
            addCriterion("smccode is null");
            return (Criteria) this;
        }

        public Criteria andSmccodeIsNotNull() {
            addCriterion("smccode is not null");
            return (Criteria) this;
        }

        public Criteria andSmccodeEqualTo(String value) {
            addCriterion("smccode =", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotEqualTo(String value) {
            addCriterion("smccode <>", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThan(String value) {
            addCriterion("smccode >", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeGreaterThanOrEqualTo(String value) {
            addCriterion("smccode >=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThan(String value) {
            addCriterion("smccode <", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLessThanOrEqualTo(String value) {
            addCriterion("smccode <=", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeLike(String value) {
            addCriterion("smccode like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotLike(String value) {
            addCriterion("smccode not like", value, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeIn(List<String> values) {
            addCriterion("smccode in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotIn(List<String> values) {
            addCriterion("smccode not in", values, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeBetween(String value1, String value2) {
            addCriterion("smccode between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andSmccodeNotBetween(String value1, String value2) {
            addCriterion("smccode not between", value1, value2, "smccode");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNull() {
            addCriterion("supplierid is null");
            return (Criteria) this;
        }

        public Criteria andSupplieridIsNotNull() {
            addCriterion("supplierid is not null");
            return (Criteria) this;
        }

        public Criteria andSupplieridEqualTo(String value) {
            addCriterion("supplierid =", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotEqualTo(String value) {
            addCriterion("supplierid <>", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThan(String value) {
            addCriterion("supplierid >", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridGreaterThanOrEqualTo(String value) {
            addCriterion("supplierid >=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThan(String value) {
            addCriterion("supplierid <", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLessThanOrEqualTo(String value) {
            addCriterion("supplierid <=", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridLike(String value) {
            addCriterion("supplierid like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotLike(String value) {
            addCriterion("supplierid not like", value, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridIn(List<String> values) {
            addCriterion("supplierid in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotIn(List<String> values) {
            addCriterion("supplierid not in", values, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridBetween(String value1, String value2) {
            addCriterion("supplierid between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andSupplieridNotBetween(String value1, String value2) {
            addCriterion("supplierid not between", value1, value2, "supplierid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidIsNull() {
            addCriterion("warehouseid is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseidIsNotNull() {
            addCriterion("warehouseid is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseidEqualTo(String value) {
            addCriterion("warehouseid =", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidNotEqualTo(String value) {
            addCriterion("warehouseid <>", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidGreaterThan(String value) {
            addCriterion("warehouseid >", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidGreaterThanOrEqualTo(String value) {
            addCriterion("warehouseid >=", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidLessThan(String value) {
            addCriterion("warehouseid <", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidLessThanOrEqualTo(String value) {
            addCriterion("warehouseid <=", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidLike(String value) {
            addCriterion("warehouseid like", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidNotLike(String value) {
            addCriterion("warehouseid not like", value, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidIn(List<String> values) {
            addCriterion("warehouseid in", values, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidNotIn(List<String> values) {
            addCriterion("warehouseid not in", values, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidBetween(String value1, String value2) {
            addCriterion("warehouseid between", value1, value2, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andWarehouseidNotBetween(String value1, String value2) {
            addCriterion("warehouseid not between", value1, value2, "warehouseid");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterIsNull() {
            addCriterion("manufactureAccepter is null");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterIsNotNull() {
            addCriterion("manufactureAccepter is not null");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterEqualTo(String value) {
            addCriterion("manufactureAccepter =", value, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterNotEqualTo(String value) {
            addCriterion("manufactureAccepter <>", value, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterGreaterThan(String value) {
            addCriterion("manufactureAccepter >", value, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterGreaterThanOrEqualTo(String value) {
            addCriterion("manufactureAccepter >=", value, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterLessThan(String value) {
            addCriterion("manufactureAccepter <", value, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterLessThanOrEqualTo(String value) {
            addCriterion("manufactureAccepter <=", value, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterLike(String value) {
            addCriterion("manufactureAccepter like", value, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterNotLike(String value) {
            addCriterion("manufactureAccepter not like", value, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterIn(List<String> values) {
            addCriterion("manufactureAccepter in", values, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterNotIn(List<String> values) {
            addCriterion("manufactureAccepter not in", values, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterBetween(String value1, String value2) {
            addCriterion("manufactureAccepter between", value1, value2, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andManufactureaccepterNotBetween(String value1, String value2) {
            addCriterion("manufactureAccepter not between", value1, value2, "manufactureaccepter");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseIsNull() {
            addCriterion("hopeReceiveWarehouse is null");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseIsNotNull() {
            addCriterion("hopeReceiveWarehouse is not null");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseEqualTo(String value) {
            addCriterion("hopeReceiveWarehouse =", value, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseNotEqualTo(String value) {
            addCriterion("hopeReceiveWarehouse <>", value, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseGreaterThan(String value) {
            addCriterion("hopeReceiveWarehouse >", value, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseGreaterThanOrEqualTo(String value) {
            addCriterion("hopeReceiveWarehouse >=", value, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseLessThan(String value) {
            addCriterion("hopeReceiveWarehouse <", value, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseLessThanOrEqualTo(String value) {
            addCriterion("hopeReceiveWarehouse <=", value, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseLike(String value) {
            addCriterion("hopeReceiveWarehouse like", value, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseNotLike(String value) {
            addCriterion("hopeReceiveWarehouse not like", value, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseIn(List<String> values) {
            addCriterion("hopeReceiveWarehouse in", values, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseNotIn(List<String> values) {
            addCriterion("hopeReceiveWarehouse not in", values, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseBetween(String value1, String value2) {
            addCriterion("hopeReceiveWarehouse between", value1, value2, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andHopereceivewarehouseNotBetween(String value1, String value2) {
            addCriterion("hopeReceiveWarehouse not between", value1, value2, "hopereceivewarehouse");
            return (Criteria) this;
        }

        public Criteria andWmtagIsNull() {
            addCriterion("wmTag is null");
            return (Criteria) this;
        }

        public Criteria andWmtagIsNotNull() {
            addCriterion("wmTag is not null");
            return (Criteria) this;
        }

        public Criteria andWmtagEqualTo(String value) {
            addCriterion("wmTag =", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagNotEqualTo(String value) {
            addCriterion("wmTag <>", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagGreaterThan(String value) {
            addCriterion("wmTag >", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagGreaterThanOrEqualTo(String value) {
            addCriterion("wmTag >=", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagLessThan(String value) {
            addCriterion("wmTag <", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagLessThanOrEqualTo(String value) {
            addCriterion("wmTag <=", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagLike(String value) {
            addCriterion("wmTag like", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagNotLike(String value) {
            addCriterion("wmTag not like", value, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagIn(List<String> values) {
            addCriterion("wmTag in", values, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagNotIn(List<String> values) {
            addCriterion("wmTag not in", values, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagBetween(String value1, String value2) {
            addCriterion("wmTag between", value1, value2, "wmtag");
            return (Criteria) this;
        }

        public Criteria andWmtagNotBetween(String value1, String value2) {
            addCriterion("wmTag not between", value1, value2, "wmtag");
            return (Criteria) this;
        }

        public Criteria andSubCodeIsNull() {
            addCriterion("sub_code is null");
            return (Criteria) this;
        }

        public Criteria andSubCodeIsNotNull() {
            addCriterion("sub_code is not null");
            return (Criteria) this;
        }

        public Criteria andSubCodeEqualTo(String value) {
            addCriterion("sub_code =", value, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeNotEqualTo(String value) {
            addCriterion("sub_code <>", value, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeGreaterThan(String value) {
            addCriterion("sub_code >", value, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sub_code >=", value, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeLessThan(String value) {
            addCriterion("sub_code <", value, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeLessThanOrEqualTo(String value) {
            addCriterion("sub_code <=", value, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeLike(String value) {
            addCriterion("sub_code like", value, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeNotLike(String value) {
            addCriterion("sub_code not like", value, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeIn(List<String> values) {
            addCriterion("sub_code in", values, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeNotIn(List<String> values) {
            addCriterion("sub_code not in", values, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeBetween(String value1, String value2) {
            addCriterion("sub_code between", value1, value2, "subCode");
            return (Criteria) this;
        }

        public Criteria andSubCodeNotBetween(String value1, String value2) {
            addCriterion("sub_code not between", value1, value2, "subCode");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNull() {
            addCriterion("trans_type is null");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNotNull() {
            addCriterion("trans_type is not null");
            return (Criteria) this;
        }

        public Criteria andTransTypeEqualTo(String value) {
            addCriterion("trans_type =", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotEqualTo(String value) {
            addCriterion("trans_type <>", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThan(String value) {
            addCriterion("trans_type >", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_type >=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThan(String value) {
            addCriterion("trans_type <", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThanOrEqualTo(String value) {
            addCriterion("trans_type <=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLike(String value) {
            addCriterion("trans_type like", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotLike(String value) {
            addCriterion("trans_type not like", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeIn(List<String> values) {
            addCriterion("trans_type in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotIn(List<String> values) {
            addCriterion("trans_type not in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeBetween(String value1, String value2) {
            addCriterion("trans_type between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotBetween(String value1, String value2) {
            addCriterion("trans_type not between", value1, value2, "transType");
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