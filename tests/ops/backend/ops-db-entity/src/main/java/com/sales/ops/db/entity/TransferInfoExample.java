package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransferInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TransferInfoExample() {
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

        public Criteria andInvoiceNoIsNull() {
            addCriterion("invoice_no is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIsNotNull() {
            addCriterion("invoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoEqualTo(String value) {
            addCriterion("invoice_no =", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotEqualTo(String value) {
            addCriterion("invoice_no <>", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoGreaterThan(String value) {
            addCriterion("invoice_no >", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_no >=", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLessThan(String value) {
            addCriterion("invoice_no <", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("invoice_no <=", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLike(String value) {
            addCriterion("invoice_no like", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotLike(String value) {
            addCriterion("invoice_no not like", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIn(List<String> values) {
            addCriterion("invoice_no in", values, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotIn(List<String> values) {
            addCriterion("invoice_no not in", values, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoBetween(String value1, String value2) {
            addCriterion("invoice_no between", value1, value2, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotBetween(String value1, String value2) {
            addCriterion("invoice_no not between", value1, value2, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusIsNull() {
            addCriterion("logistics_status is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusIsNotNull() {
            addCriterion("logistics_status is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusEqualTo(Integer value) {
            addCriterion("logistics_status =", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusNotEqualTo(Integer value) {
            addCriterion("logistics_status <>", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusGreaterThan(Integer value) {
            addCriterion("logistics_status >", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("logistics_status >=", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusLessThan(Integer value) {
            addCriterion("logistics_status <", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusLessThanOrEqualTo(Integer value) {
            addCriterion("logistics_status <=", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusIn(List<Integer> values) {
            addCriterion("logistics_status in", values, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusNotIn(List<Integer> values) {
            addCriterion("logistics_status not in", values, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusBetween(Integer value1, Integer value2) {
            addCriterion("logistics_status between", value1, value2, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("logistics_status not between", value1, value2, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseIsNull() {
            addCriterion("end_receive_warehouse is null");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseIsNotNull() {
            addCriterion("end_receive_warehouse is not null");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseEqualTo(String value) {
            addCriterion("end_receive_warehouse =", value, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseNotEqualTo(String value) {
            addCriterion("end_receive_warehouse <>", value, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseGreaterThan(String value) {
            addCriterion("end_receive_warehouse >", value, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseGreaterThanOrEqualTo(String value) {
            addCriterion("end_receive_warehouse >=", value, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseLessThan(String value) {
            addCriterion("end_receive_warehouse <", value, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseLessThanOrEqualTo(String value) {
            addCriterion("end_receive_warehouse <=", value, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseLike(String value) {
            addCriterion("end_receive_warehouse like", value, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseNotLike(String value) {
            addCriterion("end_receive_warehouse not like", value, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseIn(List<String> values) {
            addCriterion("end_receive_warehouse in", values, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseNotIn(List<String> values) {
            addCriterion("end_receive_warehouse not in", values, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseBetween(String value1, String value2) {
            addCriterion("end_receive_warehouse between", value1, value2, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andEndReceiveWarehouseNotBetween(String value1, String value2) {
            addCriterion("end_receive_warehouse not between", value1, value2, "endReceiveWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignDateIsNull() {
            addCriterion("sign_date is null");
            return (Criteria) this;
        }

        public Criteria andSignDateIsNotNull() {
            addCriterion("sign_date is not null");
            return (Criteria) this;
        }

        public Criteria andSignDateEqualTo(Date value) {
            addCriterion("sign_date =", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateNotEqualTo(Date value) {
            addCriterion("sign_date <>", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateGreaterThan(Date value) {
            addCriterion("sign_date >", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateGreaterThanOrEqualTo(Date value) {
            addCriterion("sign_date >=", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateLessThan(Date value) {
            addCriterion("sign_date <", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateLessThanOrEqualTo(Date value) {
            addCriterion("sign_date <=", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateIn(List<Date> values) {
            addCriterion("sign_date in", values, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateNotIn(List<Date> values) {
            addCriterion("sign_date not in", values, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateBetween(Date value1, Date value2) {
            addCriterion("sign_date between", value1, value2, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateNotBetween(Date value1, Date value2) {
            addCriterion("sign_date not between", value1, value2, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignUserIsNull() {
            addCriterion("sign_user is null");
            return (Criteria) this;
        }

        public Criteria andSignUserIsNotNull() {
            addCriterion("sign_user is not null");
            return (Criteria) this;
        }

        public Criteria andSignUserEqualTo(String value) {
            addCriterion("sign_user =", value, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserNotEqualTo(String value) {
            addCriterion("sign_user <>", value, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserGreaterThan(String value) {
            addCriterion("sign_user >", value, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserGreaterThanOrEqualTo(String value) {
            addCriterion("sign_user >=", value, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserLessThan(String value) {
            addCriterion("sign_user <", value, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserLessThanOrEqualTo(String value) {
            addCriterion("sign_user <=", value, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserLike(String value) {
            addCriterion("sign_user like", value, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserNotLike(String value) {
            addCriterion("sign_user not like", value, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserIn(List<String> values) {
            addCriterion("sign_user in", values, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserNotIn(List<String> values) {
            addCriterion("sign_user not in", values, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserBetween(String value1, String value2) {
            addCriterion("sign_user between", value1, value2, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignUserNotBetween(String value1, String value2) {
            addCriterion("sign_user not between", value1, value2, "signUser");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseIsNull() {
            addCriterion("sign_warehouse is null");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseIsNotNull() {
            addCriterion("sign_warehouse is not null");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseEqualTo(String value) {
            addCriterion("sign_warehouse =", value, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseNotEqualTo(String value) {
            addCriterion("sign_warehouse <>", value, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseGreaterThan(String value) {
            addCriterion("sign_warehouse >", value, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseGreaterThanOrEqualTo(String value) {
            addCriterion("sign_warehouse >=", value, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseLessThan(String value) {
            addCriterion("sign_warehouse <", value, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseLessThanOrEqualTo(String value) {
            addCriterion("sign_warehouse <=", value, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseLike(String value) {
            addCriterion("sign_warehouse like", value, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseNotLike(String value) {
            addCriterion("sign_warehouse not like", value, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseIn(List<String> values) {
            addCriterion("sign_warehouse in", values, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseNotIn(List<String> values) {
            addCriterion("sign_warehouse not in", values, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseBetween(String value1, String value2) {
            addCriterion("sign_warehouse between", value1, value2, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andSignWarehouseNotBetween(String value1, String value2) {
            addCriterion("sign_warehouse not between", value1, value2, "signWarehouse");
            return (Criteria) this;
        }

        public Criteria andShipDateIsNull() {
            addCriterion("ship_date is null");
            return (Criteria) this;
        }

        public Criteria andShipDateIsNotNull() {
            addCriterion("ship_date is not null");
            return (Criteria) this;
        }

        public Criteria andShipDateEqualTo(Date value) {
            addCriterion("ship_date =", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotEqualTo(Date value) {
            addCriterion("ship_date <>", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateGreaterThan(Date value) {
            addCriterion("ship_date >", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ship_date >=", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateLessThan(Date value) {
            addCriterion("ship_date <", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateLessThanOrEqualTo(Date value) {
            addCriterion("ship_date <=", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateIn(List<Date> values) {
            addCriterion("ship_date in", values, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotIn(List<Date> values) {
            addCriterion("ship_date not in", values, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateBetween(Date value1, Date value2) {
            addCriterion("ship_date between", value1, value2, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotBetween(Date value1, Date value2) {
            addCriterion("ship_date not between", value1, value2, "shipDate");
            return (Criteria) this;
        }

        public Criteria andCarriedIsNull() {
            addCriterion("carried is null");
            return (Criteria) this;
        }

        public Criteria andCarriedIsNotNull() {
            addCriterion("carried is not null");
            return (Criteria) this;
        }

        public Criteria andCarriedEqualTo(String value) {
            addCriterion("carried =", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedNotEqualTo(String value) {
            addCriterion("carried <>", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedGreaterThan(String value) {
            addCriterion("carried >", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedGreaterThanOrEqualTo(String value) {
            addCriterion("carried >=", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedLessThan(String value) {
            addCriterion("carried <", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedLessThanOrEqualTo(String value) {
            addCriterion("carried <=", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedLike(String value) {
            addCriterion("carried like", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedNotLike(String value) {
            addCriterion("carried not like", value, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedIn(List<String> values) {
            addCriterion("carried in", values, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedNotIn(List<String> values) {
            addCriterion("carried not in", values, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedBetween(String value1, String value2) {
            addCriterion("carried between", value1, value2, "carried");
            return (Criteria) this;
        }

        public Criteria andCarriedNotBetween(String value1, String value2) {
            addCriterion("carried not between", value1, value2, "carried");
            return (Criteria) this;
        }

        public Criteria andExpressCodeIsNull() {
            addCriterion("express_code is null");
            return (Criteria) this;
        }

        public Criteria andExpressCodeIsNotNull() {
            addCriterion("express_code is not null");
            return (Criteria) this;
        }

        public Criteria andExpressCodeEqualTo(String value) {
            addCriterion("express_code =", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeNotEqualTo(String value) {
            addCriterion("express_code <>", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeGreaterThan(String value) {
            addCriterion("express_code >", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeGreaterThanOrEqualTo(String value) {
            addCriterion("express_code >=", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeLessThan(String value) {
            addCriterion("express_code <", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeLessThanOrEqualTo(String value) {
            addCriterion("express_code <=", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeLike(String value) {
            addCriterion("express_code like", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeNotLike(String value) {
            addCriterion("express_code not like", value, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeIn(List<String> values) {
            addCriterion("express_code in", values, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeNotIn(List<String> values) {
            addCriterion("express_code not in", values, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeBetween(String value1, String value2) {
            addCriterion("express_code between", value1, value2, "expressCode");
            return (Criteria) this;
        }

        public Criteria andExpressCodeNotBetween(String value1, String value2) {
            addCriterion("express_code not between", value1, value2, "expressCode");
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

        public Criteria andUpdatorIsNull() {
            addCriterion("updator is null");
            return (Criteria) this;
        }

        public Criteria andUpdatorIsNotNull() {
            addCriterion("updator is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatorEqualTo(String value) {
            addCriterion("updator =", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotEqualTo(String value) {
            addCriterion("updator <>", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorGreaterThan(String value) {
            addCriterion("updator >", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorGreaterThanOrEqualTo(String value) {
            addCriterion("updator >=", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLessThan(String value) {
            addCriterion("updator <", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLessThanOrEqualTo(String value) {
            addCriterion("updator <=", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorLike(String value) {
            addCriterion("updator like", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotLike(String value) {
            addCriterion("updator not like", value, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorIn(List<String> values) {
            addCriterion("updator in", values, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotIn(List<String> values) {
            addCriterion("updator not in", values, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorBetween(String value1, String value2) {
            addCriterion("updator between", value1, value2, "updator");
            return (Criteria) this;
        }

        public Criteria andUpdatorNotBetween(String value1, String value2) {
            addCriterion("updator not between", value1, value2, "updator");
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