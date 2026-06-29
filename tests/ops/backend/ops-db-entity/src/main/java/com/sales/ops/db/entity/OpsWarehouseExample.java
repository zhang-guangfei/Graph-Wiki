package com.sales.ops.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsWarehouseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsWarehouseExample() {
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

        public Criteria andWarehouseCodeIsNull() {
            addCriterion("warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNotNull() {
            addCriterion("warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeEqualTo(String value) {
            addCriterion("warehouse_code =", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotEqualTo(String value) {
            addCriterion("warehouse_code <>", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThan(String value) {
            addCriterion("warehouse_code >", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_code >=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThan(String value) {
            addCriterion("warehouse_code <", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("warehouse_code <=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLike(String value) {
            addCriterion("warehouse_code like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotLike(String value) {
            addCriterion("warehouse_code not like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIn(List<String> values) {
            addCriterion("warehouse_code in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotIn(List<String> values) {
            addCriterion("warehouse_code not in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeBetween(String value1, String value2) {
            addCriterion("warehouse_code between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("warehouse_code not between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeIsNull() {
            addCriterion("warehouse_type is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeIsNotNull() {
            addCriterion("warehouse_type is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeEqualTo(String value) {
            addCriterion("warehouse_type =", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeNotEqualTo(String value) {
            addCriterion("warehouse_type <>", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeGreaterThan(String value) {
            addCriterion("warehouse_type >", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_type >=", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeLessThan(String value) {
            addCriterion("warehouse_type <", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeLessThanOrEqualTo(String value) {
            addCriterion("warehouse_type <=", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeLike(String value) {
            addCriterion("warehouse_type like", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeNotLike(String value) {
            addCriterion("warehouse_type not like", value, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeIn(List<String> values) {
            addCriterion("warehouse_type in", values, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeNotIn(List<String> values) {
            addCriterion("warehouse_type not in", values, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeBetween(String value1, String value2) {
            addCriterion("warehouse_type between", value1, value2, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseTypeNotBetween(String value1, String value2) {
            addCriterion("warehouse_type not between", value1, value2, "warehouseType");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIsNull() {
            addCriterion("warehouse_name is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIsNotNull() {
            addCriterion("warehouse_name is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameEqualTo(String value) {
            addCriterion("warehouse_name =", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotEqualTo(String value) {
            addCriterion("warehouse_name <>", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameGreaterThan(String value) {
            addCriterion("warehouse_name >", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_name >=", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLessThan(String value) {
            addCriterion("warehouse_name <", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLessThanOrEqualTo(String value) {
            addCriterion("warehouse_name <=", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLike(String value) {
            addCriterion("warehouse_name like", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotLike(String value) {
            addCriterion("warehouse_name not like", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIn(List<String> values) {
            addCriterion("warehouse_name in", values, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotIn(List<String> values) {
            addCriterion("warehouse_name not in", values, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameBetween(String value1, String value2) {
            addCriterion("warehouse_name between", value1, value2, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotBetween(String value1, String value2) {
            addCriterion("warehouse_name not between", value1, value2, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andDistrictIsNull() {
            addCriterion("district is null");
            return (Criteria) this;
        }

        public Criteria andDistrictIsNotNull() {
            addCriterion("district is not null");
            return (Criteria) this;
        }

        public Criteria andDistrictEqualTo(String value) {
            addCriterion("district =", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotEqualTo(String value) {
            addCriterion("district <>", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThan(String value) {
            addCriterion("district >", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThanOrEqualTo(String value) {
            addCriterion("district >=", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLessThan(String value) {
            addCriterion("district <", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLessThanOrEqualTo(String value) {
            addCriterion("district <=", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLike(String value) {
            addCriterion("district like", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotLike(String value) {
            addCriterion("district not like", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictIn(List<String> values) {
            addCriterion("district in", values, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotIn(List<String> values) {
            addCriterion("district not in", values, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictBetween(String value1, String value2) {
            addCriterion("district between", value1, value2, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotBetween(String value1, String value2) {
            addCriterion("district not between", value1, value2, "district");
            return (Criteria) this;
        }

        public Criteria andAdressIsNull() {
            addCriterion("adress is null");
            return (Criteria) this;
        }

        public Criteria andAdressIsNotNull() {
            addCriterion("adress is not null");
            return (Criteria) this;
        }

        public Criteria andAdressEqualTo(String value) {
            addCriterion("adress =", value, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressNotEqualTo(String value) {
            addCriterion("adress <>", value, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressGreaterThan(String value) {
            addCriterion("adress >", value, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressGreaterThanOrEqualTo(String value) {
            addCriterion("adress >=", value, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressLessThan(String value) {
            addCriterion("adress <", value, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressLessThanOrEqualTo(String value) {
            addCriterion("adress <=", value, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressLike(String value) {
            addCriterion("adress like", value, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressNotLike(String value) {
            addCriterion("adress not like", value, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressIn(List<String> values) {
            addCriterion("adress in", values, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressNotIn(List<String> values) {
            addCriterion("adress not in", values, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressBetween(String value1, String value2) {
            addCriterion("adress between", value1, value2, "adress");
            return (Criteria) this;
        }

        public Criteria andAdressNotBetween(String value1, String value2) {
            addCriterion("adress not between", value1, value2, "adress");
            return (Criteria) this;
        }

        public Criteria andPostCodeIsNull() {
            addCriterion("post_code is null");
            return (Criteria) this;
        }

        public Criteria andPostCodeIsNotNull() {
            addCriterion("post_code is not null");
            return (Criteria) this;
        }

        public Criteria andPostCodeEqualTo(String value) {
            addCriterion("post_code =", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotEqualTo(String value) {
            addCriterion("post_code <>", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeGreaterThan(String value) {
            addCriterion("post_code >", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeGreaterThanOrEqualTo(String value) {
            addCriterion("post_code >=", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLessThan(String value) {
            addCriterion("post_code <", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLessThanOrEqualTo(String value) {
            addCriterion("post_code <=", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLike(String value) {
            addCriterion("post_code like", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotLike(String value) {
            addCriterion("post_code not like", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeIn(List<String> values) {
            addCriterion("post_code in", values, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotIn(List<String> values) {
            addCriterion("post_code not in", values, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeBetween(String value1, String value2) {
            addCriterion("post_code between", value1, value2, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotBetween(String value1, String value2) {
            addCriterion("post_code not between", value1, value2, "postCode");
            return (Criteria) this;
        }

        public Criteria andLinkmanIsNull() {
            addCriterion("linkman is null");
            return (Criteria) this;
        }

        public Criteria andLinkmanIsNotNull() {
            addCriterion("linkman is not null");
            return (Criteria) this;
        }

        public Criteria andLinkmanEqualTo(String value) {
            addCriterion("linkman =", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotEqualTo(String value) {
            addCriterion("linkman <>", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanGreaterThan(String value) {
            addCriterion("linkman >", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanGreaterThanOrEqualTo(String value) {
            addCriterion("linkman >=", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLessThan(String value) {
            addCriterion("linkman <", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLessThanOrEqualTo(String value) {
            addCriterion("linkman <=", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLike(String value) {
            addCriterion("linkman like", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotLike(String value) {
            addCriterion("linkman not like", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanIn(List<String> values) {
            addCriterion("linkman in", values, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotIn(List<String> values) {
            addCriterion("linkman not in", values, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanBetween(String value1, String value2) {
            addCriterion("linkman between", value1, value2, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotBetween(String value1, String value2) {
            addCriterion("linkman not between", value1, value2, "linkman");
            return (Criteria) this;
        }

        public Criteria andMailIsNull() {
            addCriterion("mail is null");
            return (Criteria) this;
        }

        public Criteria andMailIsNotNull() {
            addCriterion("mail is not null");
            return (Criteria) this;
        }

        public Criteria andMailEqualTo(String value) {
            addCriterion("mail =", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotEqualTo(String value) {
            addCriterion("mail <>", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailGreaterThan(String value) {
            addCriterion("mail >", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailGreaterThanOrEqualTo(String value) {
            addCriterion("mail >=", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLessThan(String value) {
            addCriterion("mail <", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLessThanOrEqualTo(String value) {
            addCriterion("mail <=", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLike(String value) {
            addCriterion("mail like", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotLike(String value) {
            addCriterion("mail not like", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailIn(List<String> values) {
            addCriterion("mail in", values, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotIn(List<String> values) {
            addCriterion("mail not in", values, "mail");
            return (Criteria) this;
        }

        public Criteria andMailBetween(String value1, String value2) {
            addCriterion("mail between", value1, value2, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotBetween(String value1, String value2) {
            addCriterion("mail not between", value1, value2, "mail");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNull() {
            addCriterion("customer_no is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNotNull() {
            addCriterion("customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoEqualTo(String value) {
            addCriterion("customer_no =", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotEqualTo(String value) {
            addCriterion("customer_no <>", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThan(String value) {
            addCriterion("customer_no >", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("customer_no >=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThan(String value) {
            addCriterion("customer_no <", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("customer_no <=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLike(String value) {
            addCriterion("customer_no like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotLike(String value) {
            addCriterion("customer_no not like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIn(List<String> values) {
            addCriterion("customer_no in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotIn(List<String> values) {
            addCriterion("customer_no not in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoBetween(String value1, String value2) {
            addCriterion("customer_no between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotBetween(String value1, String value2) {
            addCriterion("customer_no not between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanIsNull() {
            addCriterion("customer_linkman is null");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanIsNotNull() {
            addCriterion("customer_linkman is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanEqualTo(String value) {
            addCriterion("customer_linkman =", value, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanNotEqualTo(String value) {
            addCriterion("customer_linkman <>", value, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanGreaterThan(String value) {
            addCriterion("customer_linkman >", value, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanGreaterThanOrEqualTo(String value) {
            addCriterion("customer_linkman >=", value, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanLessThan(String value) {
            addCriterion("customer_linkman <", value, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanLessThanOrEqualTo(String value) {
            addCriterion("customer_linkman <=", value, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanLike(String value) {
            addCriterion("customer_linkman like", value, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanNotLike(String value) {
            addCriterion("customer_linkman not like", value, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanIn(List<String> values) {
            addCriterion("customer_linkman in", values, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanNotIn(List<String> values) {
            addCriterion("customer_linkman not in", values, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanBetween(String value1, String value2) {
            addCriterion("customer_linkman between", value1, value2, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerLinkmanNotBetween(String value1, String value2) {
            addCriterion("customer_linkman not between", value1, value2, "customerLinkman");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneIsNull() {
            addCriterion("customer_phone is null");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneIsNotNull() {
            addCriterion("customer_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneEqualTo(String value) {
            addCriterion("customer_phone =", value, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNotEqualTo(String value) {
            addCriterion("customer_phone <>", value, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneGreaterThan(String value) {
            addCriterion("customer_phone >", value, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("customer_phone >=", value, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneLessThan(String value) {
            addCriterion("customer_phone <", value, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneLessThanOrEqualTo(String value) {
            addCriterion("customer_phone <=", value, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneLike(String value) {
            addCriterion("customer_phone like", value, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNotLike(String value) {
            addCriterion("customer_phone not like", value, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneIn(List<String> values) {
            addCriterion("customer_phone in", values, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNotIn(List<String> values) {
            addCriterion("customer_phone not in", values, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneBetween(String value1, String value2) {
            addCriterion("customer_phone between", value1, value2, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNotBetween(String value1, String value2) {
            addCriterion("customer_phone not between", value1, value2, "customerPhone");
            return (Criteria) this;
        }

        public Criteria andCustomerMailIsNull() {
            addCriterion("customer_mail is null");
            return (Criteria) this;
        }

        public Criteria andCustomerMailIsNotNull() {
            addCriterion("customer_mail is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerMailEqualTo(String value) {
            addCriterion("customer_mail =", value, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailNotEqualTo(String value) {
            addCriterion("customer_mail <>", value, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailGreaterThan(String value) {
            addCriterion("customer_mail >", value, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailGreaterThanOrEqualTo(String value) {
            addCriterion("customer_mail >=", value, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailLessThan(String value) {
            addCriterion("customer_mail <", value, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailLessThanOrEqualTo(String value) {
            addCriterion("customer_mail <=", value, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailLike(String value) {
            addCriterion("customer_mail like", value, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailNotLike(String value) {
            addCriterion("customer_mail not like", value, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailIn(List<String> values) {
            addCriterion("customer_mail in", values, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailNotIn(List<String> values) {
            addCriterion("customer_mail not in", values, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailBetween(String value1, String value2) {
            addCriterion("customer_mail between", value1, value2, "customerMail");
            return (Criteria) this;
        }

        public Criteria andCustomerMailNotBetween(String value1, String value2) {
            addCriterion("customer_mail not between", value1, value2, "customerMail");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanIsNull() {
            addCriterion("smc_linkman is null");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanIsNotNull() {
            addCriterion("smc_linkman is not null");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanEqualTo(String value) {
            addCriterion("smc_linkman =", value, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanNotEqualTo(String value) {
            addCriterion("smc_linkman <>", value, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanGreaterThan(String value) {
            addCriterion("smc_linkman >", value, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanGreaterThanOrEqualTo(String value) {
            addCriterion("smc_linkman >=", value, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanLessThan(String value) {
            addCriterion("smc_linkman <", value, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanLessThanOrEqualTo(String value) {
            addCriterion("smc_linkman <=", value, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanLike(String value) {
            addCriterion("smc_linkman like", value, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanNotLike(String value) {
            addCriterion("smc_linkman not like", value, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanIn(List<String> values) {
            addCriterion("smc_linkman in", values, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanNotIn(List<String> values) {
            addCriterion("smc_linkman not in", values, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanBetween(String value1, String value2) {
            addCriterion("smc_linkman between", value1, value2, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcLinkmanNotBetween(String value1, String value2) {
            addCriterion("smc_linkman not between", value1, value2, "smcLinkman");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneIsNull() {
            addCriterion("smc_phone is null");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneIsNotNull() {
            addCriterion("smc_phone is not null");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneEqualTo(String value) {
            addCriterion("smc_phone =", value, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneNotEqualTo(String value) {
            addCriterion("smc_phone <>", value, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneGreaterThan(String value) {
            addCriterion("smc_phone >", value, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("smc_phone >=", value, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneLessThan(String value) {
            addCriterion("smc_phone <", value, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneLessThanOrEqualTo(String value) {
            addCriterion("smc_phone <=", value, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneLike(String value) {
            addCriterion("smc_phone like", value, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneNotLike(String value) {
            addCriterion("smc_phone not like", value, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneIn(List<String> values) {
            addCriterion("smc_phone in", values, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneNotIn(List<String> values) {
            addCriterion("smc_phone not in", values, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneBetween(String value1, String value2) {
            addCriterion("smc_phone between", value1, value2, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcPhoneNotBetween(String value1, String value2) {
            addCriterion("smc_phone not between", value1, value2, "smcPhone");
            return (Criteria) this;
        }

        public Criteria andSmcMailIsNull() {
            addCriterion("smc_mail is null");
            return (Criteria) this;
        }

        public Criteria andSmcMailIsNotNull() {
            addCriterion("smc_mail is not null");
            return (Criteria) this;
        }

        public Criteria andSmcMailEqualTo(String value) {
            addCriterion("smc_mail =", value, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailNotEqualTo(String value) {
            addCriterion("smc_mail <>", value, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailGreaterThan(String value) {
            addCriterion("smc_mail >", value, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailGreaterThanOrEqualTo(String value) {
            addCriterion("smc_mail >=", value, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailLessThan(String value) {
            addCriterion("smc_mail <", value, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailLessThanOrEqualTo(String value) {
            addCriterion("smc_mail <=", value, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailLike(String value) {
            addCriterion("smc_mail like", value, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailNotLike(String value) {
            addCriterion("smc_mail not like", value, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailIn(List<String> values) {
            addCriterion("smc_mail in", values, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailNotIn(List<String> values) {
            addCriterion("smc_mail not in", values, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailBetween(String value1, String value2) {
            addCriterion("smc_mail between", value1, value2, "smcMail");
            return (Criteria) this;
        }

        public Criteria andSmcMailNotBetween(String value1, String value2) {
            addCriterion("smc_mail not between", value1, value2, "smcMail");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneIsNull() {
            addCriterion("link_phone is null");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneIsNotNull() {
            addCriterion("link_phone is not null");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneEqualTo(String value) {
            addCriterion("link_phone =", value, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneNotEqualTo(String value) {
            addCriterion("link_phone <>", value, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneGreaterThan(String value) {
            addCriterion("link_phone >", value, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("link_phone >=", value, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneLessThan(String value) {
            addCriterion("link_phone <", value, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneLessThanOrEqualTo(String value) {
            addCriterion("link_phone <=", value, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneLike(String value) {
            addCriterion("link_phone like", value, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneNotLike(String value) {
            addCriterion("link_phone not like", value, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneIn(List<String> values) {
            addCriterion("link_phone in", values, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneNotIn(List<String> values) {
            addCriterion("link_phone not in", values, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneBetween(String value1, String value2) {
            addCriterion("link_phone between", value1, value2, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkPhoneNotBetween(String value1, String value2) {
            addCriterion("link_phone not between", value1, value2, "linkPhone");
            return (Criteria) this;
        }

        public Criteria andLinkMobileIsNull() {
            addCriterion("link_mobile is null");
            return (Criteria) this;
        }

        public Criteria andLinkMobileIsNotNull() {
            addCriterion("link_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andLinkMobileEqualTo(String value) {
            addCriterion("link_mobile =", value, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileNotEqualTo(String value) {
            addCriterion("link_mobile <>", value, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileGreaterThan(String value) {
            addCriterion("link_mobile >", value, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileGreaterThanOrEqualTo(String value) {
            addCriterion("link_mobile >=", value, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileLessThan(String value) {
            addCriterion("link_mobile <", value, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileLessThanOrEqualTo(String value) {
            addCriterion("link_mobile <=", value, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileLike(String value) {
            addCriterion("link_mobile like", value, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileNotLike(String value) {
            addCriterion("link_mobile not like", value, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileIn(List<String> values) {
            addCriterion("link_mobile in", values, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileNotIn(List<String> values) {
            addCriterion("link_mobile not in", values, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileBetween(String value1, String value2) {
            addCriterion("link_mobile between", value1, value2, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andLinkMobileNotBetween(String value1, String value2) {
            addCriterion("link_mobile not between", value1, value2, "linkMobile");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanIsNull() {
            addCriterion("rcv_linkman is null");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanIsNotNull() {
            addCriterion("rcv_linkman is not null");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanEqualTo(String value) {
            addCriterion("rcv_linkman =", value, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanNotEqualTo(String value) {
            addCriterion("rcv_linkman <>", value, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanGreaterThan(String value) {
            addCriterion("rcv_linkman >", value, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanGreaterThanOrEqualTo(String value) {
            addCriterion("rcv_linkman >=", value, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanLessThan(String value) {
            addCriterion("rcv_linkman <", value, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanLessThanOrEqualTo(String value) {
            addCriterion("rcv_linkman <=", value, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanLike(String value) {
            addCriterion("rcv_linkman like", value, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanNotLike(String value) {
            addCriterion("rcv_linkman not like", value, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanIn(List<String> values) {
            addCriterion("rcv_linkman in", values, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanNotIn(List<String> values) {
            addCriterion("rcv_linkman not in", values, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanBetween(String value1, String value2) {
            addCriterion("rcv_linkman between", value1, value2, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkmanNotBetween(String value1, String value2) {
            addCriterion("rcv_linkman not between", value1, value2, "rcvLinkman");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelIsNull() {
            addCriterion("rcv_link_tel is null");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelIsNotNull() {
            addCriterion("rcv_link_tel is not null");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelEqualTo(String value) {
            addCriterion("rcv_link_tel =", value, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelNotEqualTo(String value) {
            addCriterion("rcv_link_tel <>", value, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelGreaterThan(String value) {
            addCriterion("rcv_link_tel >", value, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelGreaterThanOrEqualTo(String value) {
            addCriterion("rcv_link_tel >=", value, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelLessThan(String value) {
            addCriterion("rcv_link_tel <", value, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelLessThanOrEqualTo(String value) {
            addCriterion("rcv_link_tel <=", value, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelLike(String value) {
            addCriterion("rcv_link_tel like", value, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelNotLike(String value) {
            addCriterion("rcv_link_tel not like", value, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelIn(List<String> values) {
            addCriterion("rcv_link_tel in", values, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelNotIn(List<String> values) {
            addCriterion("rcv_link_tel not in", values, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelBetween(String value1, String value2) {
            addCriterion("rcv_link_tel between", value1, value2, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkTelNotBetween(String value1, String value2) {
            addCriterion("rcv_link_tel not between", value1, value2, "rcvLinkTel");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailIsNull() {
            addCriterion("rcv_link_email is null");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailIsNotNull() {
            addCriterion("rcv_link_email is not null");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailEqualTo(String value) {
            addCriterion("rcv_link_email =", value, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailNotEqualTo(String value) {
            addCriterion("rcv_link_email <>", value, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailGreaterThan(String value) {
            addCriterion("rcv_link_email >", value, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailGreaterThanOrEqualTo(String value) {
            addCriterion("rcv_link_email >=", value, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailLessThan(String value) {
            addCriterion("rcv_link_email <", value, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailLessThanOrEqualTo(String value) {
            addCriterion("rcv_link_email <=", value, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailLike(String value) {
            addCriterion("rcv_link_email like", value, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailNotLike(String value) {
            addCriterion("rcv_link_email not like", value, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailIn(List<String> values) {
            addCriterion("rcv_link_email in", values, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailNotIn(List<String> values) {
            addCriterion("rcv_link_email not in", values, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailBetween(String value1, String value2) {
            addCriterion("rcv_link_email between", value1, value2, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andRcvLinkEmailNotBetween(String value1, String value2) {
            addCriterion("rcv_link_email not between", value1, value2, "rcvLinkEmail");
            return (Criteria) this;
        }

        public Criteria andDisableFlagIsNull() {
            addCriterion("disable_flag is null");
            return (Criteria) this;
        }

        public Criteria andDisableFlagIsNotNull() {
            addCriterion("disable_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDisableFlagEqualTo(Boolean value) {
            addCriterion("disable_flag =", value, "disableFlag");
            return (Criteria) this;
        }

        public Criteria andDisableFlagNotEqualTo(Boolean value) {
            addCriterion("disable_flag <>", value, "disableFlag");
            return (Criteria) this;
        }

        public Criteria andDisableFlagGreaterThan(Boolean value) {
            addCriterion("disable_flag >", value, "disableFlag");
            return (Criteria) this;
        }

        public Criteria andDisableFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("disable_flag >=", value, "disableFlag");
            return (Criteria) this;
        }

        public Criteria andDisableFlagLessThan(Boolean value) {
            addCriterion("disable_flag <", value, "disableFlag");
            return (Criteria) this;
        }

        public Criteria andDisableFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("disable_flag <=", value, "disableFlag");
            return (Criteria) this;
        }

        public Criteria andDisableFlagIn(List<Boolean> values) {
            addCriterion("disable_flag in", values, "disableFlag");
            return (Criteria) this;
        }

        public Criteria andDisableFlagNotIn(List<Boolean> values) {
            addCriterion("disable_flag not in", values, "disableFlag");
            return (Criteria) this;
        }

        public Criteria andDisableFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("disable_flag between", value1, value2, "disableFlag");
            return (Criteria) this;
        }

        public Criteria andDisableFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("disable_flag not between", value1, value2, "disableFlag");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagIsNull() {
            addCriterion("assembly_flag is null");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagIsNotNull() {
            addCriterion("assembly_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagEqualTo(Boolean value) {
            addCriterion("assembly_flag =", value, "assemblyFlag");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagNotEqualTo(Boolean value) {
            addCriterion("assembly_flag <>", value, "assemblyFlag");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagGreaterThan(Boolean value) {
            addCriterion("assembly_flag >", value, "assemblyFlag");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("assembly_flag >=", value, "assemblyFlag");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagLessThan(Boolean value) {
            addCriterion("assembly_flag <", value, "assemblyFlag");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("assembly_flag <=", value, "assemblyFlag");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagIn(List<Boolean> values) {
            addCriterion("assembly_flag in", values, "assemblyFlag");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagNotIn(List<Boolean> values) {
            addCriterion("assembly_flag not in", values, "assemblyFlag");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("assembly_flag between", value1, value2, "assemblyFlag");
            return (Criteria) this;
        }

        public Criteria andAssemblyFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("assembly_flag not between", value1, value2, "assemblyFlag");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagIsNull() {
            addCriterion("centralize_flag is null");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagIsNotNull() {
            addCriterion("centralize_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagEqualTo(Boolean value) {
            addCriterion("centralize_flag =", value, "centralizeFlag");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagNotEqualTo(Boolean value) {
            addCriterion("centralize_flag <>", value, "centralizeFlag");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagGreaterThan(Boolean value) {
            addCriterion("centralize_flag >", value, "centralizeFlag");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("centralize_flag >=", value, "centralizeFlag");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagLessThan(Boolean value) {
            addCriterion("centralize_flag <", value, "centralizeFlag");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("centralize_flag <=", value, "centralizeFlag");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagIn(List<Boolean> values) {
            addCriterion("centralize_flag in", values, "centralizeFlag");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagNotIn(List<Boolean> values) {
            addCriterion("centralize_flag not in", values, "centralizeFlag");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("centralize_flag between", value1, value2, "centralizeFlag");
            return (Criteria) this;
        }

        public Criteria andCentralizeFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("centralize_flag not between", value1, value2, "centralizeFlag");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagIsNull() {
            addCriterion("autodispatch_flag is null");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagIsNotNull() {
            addCriterion("autodispatch_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagEqualTo(Boolean value) {
            addCriterion("autodispatch_flag =", value, "autodispatchFlag");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagNotEqualTo(Boolean value) {
            addCriterion("autodispatch_flag <>", value, "autodispatchFlag");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagGreaterThan(Boolean value) {
            addCriterion("autodispatch_flag >", value, "autodispatchFlag");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("autodispatch_flag >=", value, "autodispatchFlag");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagLessThan(Boolean value) {
            addCriterion("autodispatch_flag <", value, "autodispatchFlag");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("autodispatch_flag <=", value, "autodispatchFlag");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagIn(List<Boolean> values) {
            addCriterion("autodispatch_flag in", values, "autodispatchFlag");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagNotIn(List<Boolean> values) {
            addCriterion("autodispatch_flag not in", values, "autodispatchFlag");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("autodispatch_flag between", value1, value2, "autodispatchFlag");
            return (Criteria) this;
        }

        public Criteria andAutodispatchFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("autodispatch_flag not between", value1, value2, "autodispatchFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagIsNull() {
            addCriterion("purchase_flag is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagIsNotNull() {
            addCriterion("purchase_flag is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagEqualTo(Boolean value) {
            addCriterion("purchase_flag =", value, "purchaseFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagNotEqualTo(Boolean value) {
            addCriterion("purchase_flag <>", value, "purchaseFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagGreaterThan(Boolean value) {
            addCriterion("purchase_flag >", value, "purchaseFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("purchase_flag >=", value, "purchaseFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagLessThan(Boolean value) {
            addCriterion("purchase_flag <", value, "purchaseFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("purchase_flag <=", value, "purchaseFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagIn(List<Boolean> values) {
            addCriterion("purchase_flag in", values, "purchaseFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagNotIn(List<Boolean> values) {
            addCriterion("purchase_flag not in", values, "purchaseFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("purchase_flag between", value1, value2, "purchaseFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("purchase_flag not between", value1, value2, "purchaseFlag");
            return (Criteria) this;
        }

        public Criteria andParentCodeIsNull() {
            addCriterion("parent_code is null");
            return (Criteria) this;
        }

        public Criteria andParentCodeIsNotNull() {
            addCriterion("parent_code is not null");
            return (Criteria) this;
        }

        public Criteria andParentCodeEqualTo(String value) {
            addCriterion("parent_code =", value, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeNotEqualTo(String value) {
            addCriterion("parent_code <>", value, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeGreaterThan(String value) {
            addCriterion("parent_code >", value, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeGreaterThanOrEqualTo(String value) {
            addCriterion("parent_code >=", value, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeLessThan(String value) {
            addCriterion("parent_code <", value, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeLessThanOrEqualTo(String value) {
            addCriterion("parent_code <=", value, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeLike(String value) {
            addCriterion("parent_code like", value, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeNotLike(String value) {
            addCriterion("parent_code not like", value, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeIn(List<String> values) {
            addCriterion("parent_code in", values, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeNotIn(List<String> values) {
            addCriterion("parent_code not in", values, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeBetween(String value1, String value2) {
            addCriterion("parent_code between", value1, value2, "parentCode");
            return (Criteria) this;
        }

        public Criteria andParentCodeNotBetween(String value1, String value2) {
            addCriterion("parent_code not between", value1, value2, "parentCode");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNull() {
            addCriterion("dept_no is null");
            return (Criteria) this;
        }

        public Criteria andDeptNoIsNotNull() {
            addCriterion("dept_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNoEqualTo(String value) {
            addCriterion("dept_no =", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotEqualTo(String value) {
            addCriterion("dept_no <>", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThan(String value) {
            addCriterion("dept_no >", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoGreaterThanOrEqualTo(String value) {
            addCriterion("dept_no >=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThan(String value) {
            addCriterion("dept_no <", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLessThanOrEqualTo(String value) {
            addCriterion("dept_no <=", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoLike(String value) {
            addCriterion("dept_no like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotLike(String value) {
            addCriterion("dept_no not like", value, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoIn(List<String> values) {
            addCriterion("dept_no in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotIn(List<String> values) {
            addCriterion("dept_no not in", values, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoBetween(String value1, String value2) {
            addCriterion("dept_no between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andDeptNoNotBetween(String value1, String value2) {
            addCriterion("dept_no not between", value1, value2, "deptNo");
            return (Criteria) this;
        }

        public Criteria andMaxEamountIsNull() {
            addCriterion("max_eamount is null");
            return (Criteria) this;
        }

        public Criteria andMaxEamountIsNotNull() {
            addCriterion("max_eamount is not null");
            return (Criteria) this;
        }

        public Criteria andMaxEamountEqualTo(BigDecimal value) {
            addCriterion("max_eamount =", value, "maxEamount");
            return (Criteria) this;
        }

        public Criteria andMaxEamountNotEqualTo(BigDecimal value) {
            addCriterion("max_eamount <>", value, "maxEamount");
            return (Criteria) this;
        }

        public Criteria andMaxEamountGreaterThan(BigDecimal value) {
            addCriterion("max_eamount >", value, "maxEamount");
            return (Criteria) this;
        }

        public Criteria andMaxEamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_eamount >=", value, "maxEamount");
            return (Criteria) this;
        }

        public Criteria andMaxEamountLessThan(BigDecimal value) {
            addCriterion("max_eamount <", value, "maxEamount");
            return (Criteria) this;
        }

        public Criteria andMaxEamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_eamount <=", value, "maxEamount");
            return (Criteria) this;
        }

        public Criteria andMaxEamountIn(List<BigDecimal> values) {
            addCriterion("max_eamount in", values, "maxEamount");
            return (Criteria) this;
        }

        public Criteria andMaxEamountNotIn(List<BigDecimal> values) {
            addCriterion("max_eamount not in", values, "maxEamount");
            return (Criteria) this;
        }

        public Criteria andMaxEamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_eamount between", value1, value2, "maxEamount");
            return (Criteria) this;
        }

        public Criteria andMaxEamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_eamount not between", value1, value2, "maxEamount");
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

        public Criteria andDelflagEqualTo(Boolean value) {
            addCriterion("delflag =", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotEqualTo(Boolean value) {
            addCriterion("delflag <>", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThan(Boolean value) {
            addCriterion("delflag >", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("delflag >=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThan(Boolean value) {
            addCriterion("delflag <", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagLessThanOrEqualTo(Boolean value) {
            addCriterion("delflag <=", value, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagIn(List<Boolean> values) {
            addCriterion("delflag in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotIn(List<Boolean> values) {
            addCriterion("delflag not in", values, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagBetween(Boolean value1, Boolean value2) {
            addCriterion("delflag between", value1, value2, "delflag");
            return (Criteria) this;
        }

        public Criteria andDelflagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("delflag not between", value1, value2, "delflag");
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

        public Criteria andEnglishAddressIsNull() {
            addCriterion("english_address is null");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressIsNotNull() {
            addCriterion("english_address is not null");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressEqualTo(String value) {
            addCriterion("english_address =", value, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressNotEqualTo(String value) {
            addCriterion("english_address <>", value, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressGreaterThan(String value) {
            addCriterion("english_address >", value, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressGreaterThanOrEqualTo(String value) {
            addCriterion("english_address >=", value, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressLessThan(String value) {
            addCriterion("english_address <", value, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressLessThanOrEqualTo(String value) {
            addCriterion("english_address <=", value, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressLike(String value) {
            addCriterion("english_address like", value, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressNotLike(String value) {
            addCriterion("english_address not like", value, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressIn(List<String> values) {
            addCriterion("english_address in", values, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressNotIn(List<String> values) {
            addCriterion("english_address not in", values, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressBetween(String value1, String value2) {
            addCriterion("english_address between", value1, value2, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishAddressNotBetween(String value1, String value2) {
            addCriterion("english_address not between", value1, value2, "englishAddress");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanIsNull() {
            addCriterion("english_linkman is null");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanIsNotNull() {
            addCriterion("english_linkman is not null");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanEqualTo(String value) {
            addCriterion("english_linkman =", value, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanNotEqualTo(String value) {
            addCriterion("english_linkman <>", value, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanGreaterThan(String value) {
            addCriterion("english_linkman >", value, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanGreaterThanOrEqualTo(String value) {
            addCriterion("english_linkman >=", value, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanLessThan(String value) {
            addCriterion("english_linkman <", value, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanLessThanOrEqualTo(String value) {
            addCriterion("english_linkman <=", value, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanLike(String value) {
            addCriterion("english_linkman like", value, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanNotLike(String value) {
            addCriterion("english_linkman not like", value, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanIn(List<String> values) {
            addCriterion("english_linkman in", values, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanNotIn(List<String> values) {
            addCriterion("english_linkman not in", values, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanBetween(String value1, String value2) {
            addCriterion("english_linkman between", value1, value2, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andEnglishLinkmanNotBetween(String value1, String value2) {
            addCriterion("english_linkman not between", value1, value2, "englishLinkman");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagIsNull() {
            addCriterion("returnable_flag is null");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagIsNotNull() {
            addCriterion("returnable_flag is not null");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagEqualTo(Boolean value) {
            addCriterion("returnable_flag =", value, "returnableFlag");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagNotEqualTo(Boolean value) {
            addCriterion("returnable_flag <>", value, "returnableFlag");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagGreaterThan(Boolean value) {
            addCriterion("returnable_flag >", value, "returnableFlag");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("returnable_flag >=", value, "returnableFlag");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagLessThan(Boolean value) {
            addCriterion("returnable_flag <", value, "returnableFlag");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("returnable_flag <=", value, "returnableFlag");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagIn(List<Boolean> values) {
            addCriterion("returnable_flag in", values, "returnableFlag");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagNotIn(List<Boolean> values) {
            addCriterion("returnable_flag not in", values, "returnableFlag");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("returnable_flag between", value1, value2, "returnableFlag");
            return (Criteria) this;
        }

        public Criteria andReturnableFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("returnable_flag not between", value1, value2, "returnableFlag");
            return (Criteria) this;
        }

        public Criteria andTransferFlagIsNull() {
            addCriterion("transfer_flag is null");
            return (Criteria) this;
        }

        public Criteria andTransferFlagIsNotNull() {
            addCriterion("transfer_flag is not null");
            return (Criteria) this;
        }

        public Criteria andTransferFlagEqualTo(Boolean value) {
            addCriterion("transfer_flag =", value, "transferFlag");
            return (Criteria) this;
        }

        public Criteria andTransferFlagNotEqualTo(Boolean value) {
            addCriterion("transfer_flag <>", value, "transferFlag");
            return (Criteria) this;
        }

        public Criteria andTransferFlagGreaterThan(Boolean value) {
            addCriterion("transfer_flag >", value, "transferFlag");
            return (Criteria) this;
        }

        public Criteria andTransferFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("transfer_flag >=", value, "transferFlag");
            return (Criteria) this;
        }

        public Criteria andTransferFlagLessThan(Boolean value) {
            addCriterion("transfer_flag <", value, "transferFlag");
            return (Criteria) this;
        }

        public Criteria andTransferFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("transfer_flag <=", value, "transferFlag");
            return (Criteria) this;
        }

        public Criteria andTransferFlagIn(List<Boolean> values) {
            addCriterion("transfer_flag in", values, "transferFlag");
            return (Criteria) this;
        }

        public Criteria andTransferFlagNotIn(List<Boolean> values) {
            addCriterion("transfer_flag not in", values, "transferFlag");
            return (Criteria) this;
        }

        public Criteria andTransferFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("transfer_flag between", value1, value2, "transferFlag");
            return (Criteria) this;
        }

        public Criteria andTransferFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("transfer_flag not between", value1, value2, "transferFlag");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagIsNull() {
            addCriterion("prestock_flag is null");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagIsNotNull() {
            addCriterion("prestock_flag is not null");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagEqualTo(Boolean value) {
            addCriterion("prestock_flag =", value, "prestockFlag");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagNotEqualTo(Boolean value) {
            addCriterion("prestock_flag <>", value, "prestockFlag");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagGreaterThan(Boolean value) {
            addCriterion("prestock_flag >", value, "prestockFlag");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("prestock_flag >=", value, "prestockFlag");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagLessThan(Boolean value) {
            addCriterion("prestock_flag <", value, "prestockFlag");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("prestock_flag <=", value, "prestockFlag");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagIn(List<Boolean> values) {
            addCriterion("prestock_flag in", values, "prestockFlag");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagNotIn(List<Boolean> values) {
            addCriterion("prestock_flag not in", values, "prestockFlag");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("prestock_flag between", value1, value2, "prestockFlag");
            return (Criteria) this;
        }

        public Criteria andPrestockFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("prestock_flag not between", value1, value2, "prestockFlag");
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