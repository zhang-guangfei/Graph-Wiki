package com.sales.ops.serviceimpl.event.v3.status.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.sales.ops.db.entity.OrderStatusItem;
import com.sales.ops.serviceimpl.event.v3.order.entity.JYCK;
import com.sales.ops.serviceimpl.event.v3.order.entity.Relation;
import com.sales.ops.serviceimpl.event.v3.status.enums.OrderStatusItemEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Data
public abstract class RelationStatus {


    protected OrderStatusItem orderStatusItem;
    RemarkBuilder remarkBuilder = RemarkBuilder.create();
    OrderStatusItemEnum statusItemEnum;


    public RelationStatus() {
        this.orderStatusItem = new OrderStatusItem();
    }

    public abstract Relation getRelation();

    public void createOrderStatusItem(JYCK jyck) {
        orderStatusItem.setOrderId(jyck.getOrderId());
        orderStatusItem.setOrderItem(jyck.getOrderItem());
        orderStatusItem.setSplitNo(jyck.getNum());
        orderStatusItem.setPcoItem(jyck.getAssNum());
        orderStatusItem.setQty(getRelation().getUseQty());
        orderStatusItem.setQtyOut(getRelation().getOutQty());
        orderStatusItem.setInventoryId(getRelation().getInvId());
        orderStatusItem.setInventoryTable(getRelation().getInvTable().getType());

        orderStatusItem.setCreTime(new Date());
        orderStatusItem.setCreator("OrderStatus生成器");
        orderStatusItem.setModifyTime(new Date());
        orderStatusItem.setModifier("OrderStatus生成器");

        // 需要计算并在后续子类中填充
        // orderStatusItem.setQtyIn();
        // orderStatusItem.setInventoryType();
        // orderStatusItem.setInvoiceNo();
        // orderStatusItem.setAssociateNo();

        // 在方法fillStatusDesc中填充
        // orderStatusItem.setStatus();
        // orderStatusItem.setStatusDesc();
        // orderStatusItem.setStatusInfo();

    }

    // 备注信息生成器
    public static class RemarkBuilder {
        public interface RemarkEnum {
            String getDesc();
        }

        String start = "【";
        String s = ":";
        String delimiter = ",";
        String end = "】";

        private HashMap<String, String> map;

        public RemarkBuilder() {
            this.map = new LinkedHashMap<>();
        }

        public static RemarkBuilder create() {
            return new RemarkBuilder();
        }

        public boolean containsKey(String key){
            return map.containsKey(key);
        }

        public RemarkBuilder set(String key, String value) {
            if (StringUtils.isNotBlank(value)) {
                this.map.put(key, value);
            }
            return this;
        }

        public RemarkBuilder set(RemarkEnum remarkCode, String value) {
            set(remarkCode.getDesc(), value);
            return this;
        }


        public RemarkBuilder set(RemarkEnum remarkCode, Date value, String pattern) {
            if (value != null) {
                set(remarkCode, new SimpleDateFormat(pattern).format(value));
            }
            return this;
        }

        public RemarkBuilder set(RemarkEnum remarkCode, Date value) {
            if (value == null) {
                return this;
            }
            // 带不带时分秒
            boolean sameTime = DateUtil.isSameTime(DateUtil.beginOfDay(value), value);
            if (sameTime) {
                setDate(remarkCode, value);
            } else {
                setDateTime(remarkCode, value);
            }
            return this;
        }

        public RemarkBuilder setDate(RemarkEnum remarkCode, Date value) {
            set(remarkCode, value, "yyyy-MM-dd");
            return this;
        }

        public RemarkBuilder setDateTime(RemarkEnum remarkCode, Date value) {
            set(remarkCode, value, "yyyy-MM-dd HH:mm:ss");
            return this;
        }

        public String buildAsText() {
            if (map.isEmpty()) {
                return null;
            }
            StringBuilder builder = new StringBuilder();
            builder.append(start);
            String remark = map.entrySet().stream().map(entry -> entry.getKey() + s + entry.getValue()).collect(Collectors.joining(delimiter));
            builder.append(remark);
            builder.append(end);
            return builder.toString();
        }

        public String buildAsJson() {
            if (map.isEmpty()) {
                return null;
            }
            return JSONUtil.toJsonStr(map);
        }

    }

    public abstract boolean afterSign();

}
