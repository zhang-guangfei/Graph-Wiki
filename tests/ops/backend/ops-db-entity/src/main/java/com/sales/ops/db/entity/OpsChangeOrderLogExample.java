package com.sales.ops.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpsChangeOrderLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpsChangeOrderLogExample() {
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
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeIsNull() {
            addCriterion("trasferimento_type is null");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeIsNotNull() {
            addCriterion("trasferimento_type is not null");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeEqualTo(Integer value) {
            addCriterion("trasferimento_type =", value, "trasferimentoType");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeNotEqualTo(Integer value) {
            addCriterion("trasferimento_type <>", value, "trasferimentoType");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeGreaterThan(Integer value) {
            addCriterion("trasferimento_type >", value, "trasferimentoType");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("trasferimento_type >=", value, "trasferimentoType");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeLessThan(Integer value) {
            addCriterion("trasferimento_type <", value, "trasferimentoType");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeLessThanOrEqualTo(Integer value) {
            addCriterion("trasferimento_type <=", value, "trasferimentoType");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeIn(List<Integer> values) {
            addCriterion("trasferimento_type in", values, "trasferimentoType");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeNotIn(List<Integer> values) {
            addCriterion("trasferimento_type not in", values, "trasferimentoType");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeBetween(Integer value1, Integer value2) {
            addCriterion("trasferimento_type between", value1, value2, "trasferimentoType");
            return (Criteria) this;
        }

        public Criteria andTrasferimentoTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("trasferimento_type not between", value1, value2, "trasferimentoType");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumIsNull() {
            addCriterion("dosource_enum is null");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumIsNotNull() {
            addCriterion("dosource_enum is not null");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumEqualTo(String value) {
            addCriterion("dosource_enum =", value, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumNotEqualTo(String value) {
            addCriterion("dosource_enum <>", value, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumGreaterThan(String value) {
            addCriterion("dosource_enum >", value, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumGreaterThanOrEqualTo(String value) {
            addCriterion("dosource_enum >=", value, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumLessThan(String value) {
            addCriterion("dosource_enum <", value, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumLessThanOrEqualTo(String value) {
            addCriterion("dosource_enum <=", value, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumLike(String value) {
            addCriterion("dosource_enum like", value, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumNotLike(String value) {
            addCriterion("dosource_enum not like", value, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumIn(List<String> values) {
            addCriterion("dosource_enum in", values, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumNotIn(List<String> values) {
            addCriterion("dosource_enum not in", values, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumBetween(String value1, String value2) {
            addCriterion("dosource_enum between", value1, value2, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andDosourceEnumNotBetween(String value1, String value2) {
            addCriterion("dosource_enum not between", value1, value2, "dosourceEnum");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdIsNull() {
            addCriterion("from_do_item_inventory_id is null");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdIsNotNull() {
            addCriterion("from_do_item_inventory_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdEqualTo(Long value) {
            addCriterion("from_do_item_inventory_id =", value, "fromDoItemInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdNotEqualTo(Long value) {
            addCriterion("from_do_item_inventory_id <>", value, "fromDoItemInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdGreaterThan(Long value) {
            addCriterion("from_do_item_inventory_id >", value, "fromDoItemInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_do_item_inventory_id >=", value, "fromDoItemInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdLessThan(Long value) {
            addCriterion("from_do_item_inventory_id <", value, "fromDoItemInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdLessThanOrEqualTo(Long value) {
            addCriterion("from_do_item_inventory_id <=", value, "fromDoItemInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdIn(List<Long> values) {
            addCriterion("from_do_item_inventory_id in", values, "fromDoItemInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdNotIn(List<Long> values) {
            addCriterion("from_do_item_inventory_id not in", values, "fromDoItemInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdBetween(Long value1, Long value2) {
            addCriterion("from_do_item_inventory_id between", value1, value2, "fromDoItemInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromDoItemInventoryIdNotBetween(Long value1, Long value2) {
            addCriterion("from_do_item_inventory_id not between", value1, value2, "fromDoItemInventoryId");
            return (Criteria) this;
        }

        public Criteria andDoidIsNull() {
            addCriterion("doId is null");
            return (Criteria) this;
        }

        public Criteria andDoidIsNotNull() {
            addCriterion("doId is not null");
            return (Criteria) this;
        }

        public Criteria andDoidEqualTo(String value) {
            addCriterion("doId =", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidNotEqualTo(String value) {
            addCriterion("doId <>", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidGreaterThan(String value) {
            addCriterion("doId >", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidGreaterThanOrEqualTo(String value) {
            addCriterion("doId >=", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidLessThan(String value) {
            addCriterion("doId <", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidLessThanOrEqualTo(String value) {
            addCriterion("doId <=", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidLike(String value) {
            addCriterion("doId like", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidNotLike(String value) {
            addCriterion("doId not like", value, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidIn(List<String> values) {
            addCriterion("doId in", values, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidNotIn(List<String> values) {
            addCriterion("doId not in", values, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidBetween(String value1, String value2) {
            addCriterion("doId between", value1, value2, "doid");
            return (Criteria) this;
        }

        public Criteria andDoidNotBetween(String value1, String value2) {
            addCriterion("doId not between", value1, value2, "doid");
            return (Criteria) this;
        }

        public Criteria andPcoidIsNull() {
            addCriterion("pcoId is null");
            return (Criteria) this;
        }

        public Criteria andPcoidIsNotNull() {
            addCriterion("pcoId is not null");
            return (Criteria) this;
        }

        public Criteria andPcoidEqualTo(String value) {
            addCriterion("pcoId =", value, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidNotEqualTo(String value) {
            addCriterion("pcoId <>", value, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidGreaterThan(String value) {
            addCriterion("pcoId >", value, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidGreaterThanOrEqualTo(String value) {
            addCriterion("pcoId >=", value, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidLessThan(String value) {
            addCriterion("pcoId <", value, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidLessThanOrEqualTo(String value) {
            addCriterion("pcoId <=", value, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidLike(String value) {
            addCriterion("pcoId like", value, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidNotLike(String value) {
            addCriterion("pcoId not like", value, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidIn(List<String> values) {
            addCriterion("pcoId in", values, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidNotIn(List<String> values) {
            addCriterion("pcoId not in", values, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidBetween(String value1, String value2) {
            addCriterion("pcoId between", value1, value2, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoidNotBetween(String value1, String value2) {
            addCriterion("pcoId not between", value1, value2, "pcoid");
            return (Criteria) this;
        }

        public Criteria andPcoitemIsNull() {
            addCriterion("pcoItem is null");
            return (Criteria) this;
        }

        public Criteria andPcoitemIsNotNull() {
            addCriterion("pcoItem is not null");
            return (Criteria) this;
        }

        public Criteria andPcoitemEqualTo(Integer value) {
            addCriterion("pcoItem =", value, "pcoitem");
            return (Criteria) this;
        }

        public Criteria andPcoitemNotEqualTo(Integer value) {
            addCriterion("pcoItem <>", value, "pcoitem");
            return (Criteria) this;
        }

        public Criteria andPcoitemGreaterThan(Integer value) {
            addCriterion("pcoItem >", value, "pcoitem");
            return (Criteria) this;
        }

        public Criteria andPcoitemGreaterThanOrEqualTo(Integer value) {
            addCriterion("pcoItem >=", value, "pcoitem");
            return (Criteria) this;
        }

        public Criteria andPcoitemLessThan(Integer value) {
            addCriterion("pcoItem <", value, "pcoitem");
            return (Criteria) this;
        }

        public Criteria andPcoitemLessThanOrEqualTo(Integer value) {
            addCriterion("pcoItem <=", value, "pcoitem");
            return (Criteria) this;
        }

        public Criteria andPcoitemIn(List<Integer> values) {
            addCriterion("pcoItem in", values, "pcoitem");
            return (Criteria) this;
        }

        public Criteria andPcoitemNotIn(List<Integer> values) {
            addCriterion("pcoItem not in", values, "pcoitem");
            return (Criteria) this;
        }

        public Criteria andPcoitemBetween(Integer value1, Integer value2) {
            addCriterion("pcoItem between", value1, value2, "pcoitem");
            return (Criteria) this;
        }

        public Criteria andPcoitemNotBetween(Integer value1, Integer value2) {
            addCriterion("pcoItem not between", value1, value2, "pcoitem");
            return (Criteria) this;
        }

        public Criteria andSortnumIsNull() {
            addCriterion("sortNum is null");
            return (Criteria) this;
        }

        public Criteria andSortnumIsNotNull() {
            addCriterion("sortNum is not null");
            return (Criteria) this;
        }

        public Criteria andSortnumEqualTo(Integer value) {
            addCriterion("sortNum =", value, "sortnum");
            return (Criteria) this;
        }

        public Criteria andSortnumNotEqualTo(Integer value) {
            addCriterion("sortNum <>", value, "sortnum");
            return (Criteria) this;
        }

        public Criteria andSortnumGreaterThan(Integer value) {
            addCriterion("sortNum >", value, "sortnum");
            return (Criteria) this;
        }

        public Criteria andSortnumGreaterThanOrEqualTo(Integer value) {
            addCriterion("sortNum >=", value, "sortnum");
            return (Criteria) this;
        }

        public Criteria andSortnumLessThan(Integer value) {
            addCriterion("sortNum <", value, "sortnum");
            return (Criteria) this;
        }

        public Criteria andSortnumLessThanOrEqualTo(Integer value) {
            addCriterion("sortNum <=", value, "sortnum");
            return (Criteria) this;
        }

        public Criteria andSortnumIn(List<Integer> values) {
            addCriterion("sortNum in", values, "sortnum");
            return (Criteria) this;
        }

        public Criteria andSortnumNotIn(List<Integer> values) {
            addCriterion("sortNum not in", values, "sortnum");
            return (Criteria) this;
        }

        public Criteria andSortnumBetween(Integer value1, Integer value2) {
            addCriterion("sortNum between", value1, value2, "sortnum");
            return (Criteria) this;
        }

        public Criteria andSortnumNotBetween(Integer value1, Integer value2) {
            addCriterion("sortNum not between", value1, value2, "sortnum");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdIsNull() {
            addCriterion("from_inventory_id is null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdIsNotNull() {
            addCriterion("from_inventory_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdEqualTo(Long value) {
            addCriterion("from_inventory_id =", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdNotEqualTo(Long value) {
            addCriterion("from_inventory_id <>", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdGreaterThan(Long value) {
            addCriterion("from_inventory_id >", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_inventory_id >=", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdLessThan(Long value) {
            addCriterion("from_inventory_id <", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdLessThanOrEqualTo(Long value) {
            addCriterion("from_inventory_id <=", value, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdIn(List<Long> values) {
            addCriterion("from_inventory_id in", values, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdNotIn(List<Long> values) {
            addCriterion("from_inventory_id not in", values, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdBetween(Long value1, Long value2) {
            addCriterion("from_inventory_id between", value1, value2, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFromInventoryIdNotBetween(Long value1, Long value2) {
            addCriterion("from_inventory_id not between", value1, value2, "fromInventoryId");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeIsNull() {
            addCriterion("fromInventory_table_type is null");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeIsNotNull() {
            addCriterion("fromInventory_table_type is not null");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeEqualTo(String value) {
            addCriterion("fromInventory_table_type =", value, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeNotEqualTo(String value) {
            addCriterion("fromInventory_table_type <>", value, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeGreaterThan(String value) {
            addCriterion("fromInventory_table_type >", value, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeGreaterThanOrEqualTo(String value) {
            addCriterion("fromInventory_table_type >=", value, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeLessThan(String value) {
            addCriterion("fromInventory_table_type <", value, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeLessThanOrEqualTo(String value) {
            addCriterion("fromInventory_table_type <=", value, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeLike(String value) {
            addCriterion("fromInventory_table_type like", value, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeNotLike(String value) {
            addCriterion("fromInventory_table_type not like", value, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeIn(List<String> values) {
            addCriterion("fromInventory_table_type in", values, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeNotIn(List<String> values) {
            addCriterion("fromInventory_table_type not in", values, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeBetween(String value1, String value2) {
            addCriterion("fromInventory_table_type between", value1, value2, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andFrominventoryTableTypeNotBetween(String value1, String value2) {
            addCriterion("fromInventory_table_type not between", value1, value2, "frominventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdIsNull() {
            addCriterion("toInventory_id is null");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdIsNotNull() {
            addCriterion("toInventory_id is not null");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdEqualTo(Long value) {
            addCriterion("toInventory_id =", value, "toinventoryId");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdNotEqualTo(Long value) {
            addCriterion("toInventory_id <>", value, "toinventoryId");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdGreaterThan(Long value) {
            addCriterion("toInventory_id >", value, "toinventoryId");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("toInventory_id >=", value, "toinventoryId");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdLessThan(Long value) {
            addCriterion("toInventory_id <", value, "toinventoryId");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdLessThanOrEqualTo(Long value) {
            addCriterion("toInventory_id <=", value, "toinventoryId");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdIn(List<Long> values) {
            addCriterion("toInventory_id in", values, "toinventoryId");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdNotIn(List<Long> values) {
            addCriterion("toInventory_id not in", values, "toinventoryId");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdBetween(Long value1, Long value2) {
            addCriterion("toInventory_id between", value1, value2, "toinventoryId");
            return (Criteria) this;
        }

        public Criteria andToinventoryIdNotBetween(Long value1, Long value2) {
            addCriterion("toInventory_id not between", value1, value2, "toinventoryId");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeIsNull() {
            addCriterion("toInventory_table_type is null");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeIsNotNull() {
            addCriterion("toInventory_table_type is not null");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeEqualTo(String value) {
            addCriterion("toInventory_table_type =", value, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeNotEqualTo(String value) {
            addCriterion("toInventory_table_type <>", value, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeGreaterThan(String value) {
            addCriterion("toInventory_table_type >", value, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeGreaterThanOrEqualTo(String value) {
            addCriterion("toInventory_table_type >=", value, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeLessThan(String value) {
            addCriterion("toInventory_table_type <", value, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeLessThanOrEqualTo(String value) {
            addCriterion("toInventory_table_type <=", value, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeLike(String value) {
            addCriterion("toInventory_table_type like", value, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeNotLike(String value) {
            addCriterion("toInventory_table_type not like", value, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeIn(List<String> values) {
            addCriterion("toInventory_table_type in", values, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeNotIn(List<String> values) {
            addCriterion("toInventory_table_type not in", values, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeBetween(String value1, String value2) {
            addCriterion("toInventory_table_type between", value1, value2, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToinventoryTableTypeNotBetween(String value1, String value2) {
            addCriterion("toInventory_table_type not between", value1, value2, "toinventoryTableType");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeIsNull() {
            addCriterion("to_warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeIsNotNull() {
            addCriterion("to_warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeEqualTo(String value) {
            addCriterion("to_warehouse_code =", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeNotEqualTo(String value) {
            addCriterion("to_warehouse_code <>", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeGreaterThan(String value) {
            addCriterion("to_warehouse_code >", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("to_warehouse_code >=", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeLessThan(String value) {
            addCriterion("to_warehouse_code <", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("to_warehouse_code <=", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeLike(String value) {
            addCriterion("to_warehouse_code like", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeNotLike(String value) {
            addCriterion("to_warehouse_code not like", value, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeIn(List<String> values) {
            addCriterion("to_warehouse_code in", values, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeNotIn(List<String> values) {
            addCriterion("to_warehouse_code not in", values, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeBetween(String value1, String value2) {
            addCriterion("to_warehouse_code between", value1, value2, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("to_warehouse_code not between", value1, value2, "toWarehouseCode");
            return (Criteria) this;
        }

        public Criteria andToQtyIsNull() {
            addCriterion("to_qty is null");
            return (Criteria) this;
        }

        public Criteria andToQtyIsNotNull() {
            addCriterion("to_qty is not null");
            return (Criteria) this;
        }

        public Criteria andToQtyEqualTo(Integer value) {
            addCriterion("to_qty =", value, "toQty");
            return (Criteria) this;
        }

        public Criteria andToQtyNotEqualTo(Integer value) {
            addCriterion("to_qty <>", value, "toQty");
            return (Criteria) this;
        }

        public Criteria andToQtyGreaterThan(Integer value) {
            addCriterion("to_qty >", value, "toQty");
            return (Criteria) this;
        }

        public Criteria andToQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("to_qty >=", value, "toQty");
            return (Criteria) this;
        }

        public Criteria andToQtyLessThan(Integer value) {
            addCriterion("to_qty <", value, "toQty");
            return (Criteria) this;
        }

        public Criteria andToQtyLessThanOrEqualTo(Integer value) {
            addCriterion("to_qty <=", value, "toQty");
            return (Criteria) this;
        }

        public Criteria andToQtyIn(List<Integer> values) {
            addCriterion("to_qty in", values, "toQty");
            return (Criteria) this;
        }

        public Criteria andToQtyNotIn(List<Integer> values) {
            addCriterion("to_qty not in", values, "toQty");
            return (Criteria) this;
        }

        public Criteria andToQtyBetween(Integer value1, Integer value2) {
            addCriterion("to_qty between", value1, value2, "toQty");
            return (Criteria) this;
        }

        public Criteria andToQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("to_qty not between", value1, value2, "toQty");
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

        public Criteria andDoDbidIsNull() {
            addCriterion("do_dbID is null");
            return (Criteria) this;
        }

        public Criteria andDoDbidIsNotNull() {
            addCriterion("do_dbID is not null");
            return (Criteria) this;
        }

        public Criteria andDoDbidEqualTo(String value) {
            addCriterion("do_dbID =", value, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidNotEqualTo(String value) {
            addCriterion("do_dbID <>", value, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidGreaterThan(String value) {
            addCriterion("do_dbID >", value, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidGreaterThanOrEqualTo(String value) {
            addCriterion("do_dbID >=", value, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidLessThan(String value) {
            addCriterion("do_dbID <", value, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidLessThanOrEqualTo(String value) {
            addCriterion("do_dbID <=", value, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidLike(String value) {
            addCriterion("do_dbID like", value, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidNotLike(String value) {
            addCriterion("do_dbID not like", value, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidIn(List<String> values) {
            addCriterion("do_dbID in", values, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidNotIn(List<String> values) {
            addCriterion("do_dbID not in", values, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidBetween(String value1, String value2) {
            addCriterion("do_dbID between", value1, value2, "doDbid");
            return (Criteria) this;
        }

        public Criteria andDoDbidNotBetween(String value1, String value2) {
            addCriterion("do_dbID not between", value1, value2, "doDbid");
            return (Criteria) this;
        }

        public Criteria andModelNoIsNull() {
            addCriterion("model_no is null");
            return (Criteria) this;
        }

        public Criteria andModelNoIsNotNull() {
            addCriterion("model_no is not null");
            return (Criteria) this;
        }

        public Criteria andModelNoEqualTo(String value) {
            addCriterion("model_no =", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotEqualTo(String value) {
            addCriterion("model_no <>", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoGreaterThan(String value) {
            addCriterion("model_no >", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoGreaterThanOrEqualTo(String value) {
            addCriterion("model_no >=", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLessThan(String value) {
            addCriterion("model_no <", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLessThanOrEqualTo(String value) {
            addCriterion("model_no <=", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoLike(String value) {
            addCriterion("model_no like", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotLike(String value) {
            addCriterion("model_no not like", value, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoIn(List<String> values) {
            addCriterion("model_no in", values, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotIn(List<String> values) {
            addCriterion("model_no not in", values, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoBetween(String value1, String value2) {
            addCriterion("model_no between", value1, value2, "modelNo");
            return (Criteria) this;
        }

        public Criteria andModelNoNotBetween(String value1, String value2) {
            addCriterion("model_no not between", value1, value2, "modelNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoIsNull() {
            addCriterion("split_no is null");
            return (Criteria) this;
        }

        public Criteria andSplitNoIsNotNull() {
            addCriterion("split_no is not null");
            return (Criteria) this;
        }

        public Criteria andSplitNoEqualTo(Integer value) {
            addCriterion("split_no =", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoNotEqualTo(Integer value) {
            addCriterion("split_no <>", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoGreaterThan(Integer value) {
            addCriterion("split_no >", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("split_no >=", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoLessThan(Integer value) {
            addCriterion("split_no <", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoLessThanOrEqualTo(Integer value) {
            addCriterion("split_no <=", value, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoIn(List<Integer> values) {
            addCriterion("split_no in", values, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoNotIn(List<Integer> values) {
            addCriterion("split_no not in", values, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoBetween(Integer value1, Integer value2) {
            addCriterion("split_no between", value1, value2, "splitNo");
            return (Criteria) this;
        }

        public Criteria andSplitNoNotBetween(Integer value1, Integer value2) {
            addCriterion("split_no not between", value1, value2, "splitNo");
            return (Criteria) this;
        }

        public Criteria andDbFlagIsNull() {
            addCriterion("db_flag is null");
            return (Criteria) this;
        }

        public Criteria andDbFlagIsNotNull() {
            addCriterion("db_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDbFlagEqualTo(Boolean value) {
            addCriterion("db_flag =", value, "dbFlag");
            return (Criteria) this;
        }

        public Criteria andDbFlagNotEqualTo(Boolean value) {
            addCriterion("db_flag <>", value, "dbFlag");
            return (Criteria) this;
        }

        public Criteria andDbFlagGreaterThan(Boolean value) {
            addCriterion("db_flag >", value, "dbFlag");
            return (Criteria) this;
        }

        public Criteria andDbFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("db_flag >=", value, "dbFlag");
            return (Criteria) this;
        }

        public Criteria andDbFlagLessThan(Boolean value) {
            addCriterion("db_flag <", value, "dbFlag");
            return (Criteria) this;
        }

        public Criteria andDbFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("db_flag <=", value, "dbFlag");
            return (Criteria) this;
        }

        public Criteria andDbFlagIn(List<Boolean> values) {
            addCriterion("db_flag in", values, "dbFlag");
            return (Criteria) this;
        }

        public Criteria andDbFlagNotIn(List<Boolean> values) {
            addCriterion("db_flag not in", values, "dbFlag");
            return (Criteria) this;
        }

        public Criteria andDbFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("db_flag between", value1, value2, "dbFlag");
            return (Criteria) this;
        }

        public Criteria andDbFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("db_flag not between", value1, value2, "dbFlag");
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