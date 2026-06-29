package com.sales.ops.utils;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.StringPhoneUtil;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.enums.*;
import org.springframework.util.StringUtils;

import java.util.*;

public class DoBuilder {

    private DoTypeEnum doTypeEnum;
    private DoSourceEnum doSourceEnum;
    private String orderId;
    private Integer orderItem;
    private Integer num;
    private Integer assNum;
    private String doId;


    private List<invInfo> invs = new ArrayList<>();


    // 库存信息
    class invInfo {
        private InventoryTableTypeEnum invTableTypeEnum;
        private Long inventoryId;
        private String modelno;
        private int qty = 0;

        public invInfo(InventoryTableTypeEnum invTableTypeEnum, Long inventoryId, String modelno, int qty) {
            this.invTableTypeEnum = invTableTypeEnum;
            this.inventoryId = inventoryId;
            this.modelno = modelno;
            this.qty = qty;
        }
    }


    // 仓库地址信息
    private String warehouse;
    private String gatherWarehouse;
    private String customerNo;
    private String province;
    private String city;
    private String district;
    private String street;
    private String address;
    private String linkman;
    private String mobile;
    private String phone;
    private String postcode;


    // do状态
    private DoStatusEnum doStatusEnum;
    private DoWaitTypeEnum doWaitTypeEnum;
    private int outQty;


    private String creator;
    private String updater;


    public static DoBuilder createTKCK(String orderId, Integer orderItem) {
        DoBuilder doBuilder = new DoBuilder();
        doBuilder.doTypeEnum = DoTypeEnum.TKCK;
        doBuilder.doSourceEnum = DoSourceEnum.ALL;
        doBuilder.orderId = orderId;
        doBuilder.orderItem = orderItem;
        doBuilder.num = 0;
        doBuilder.assNum = 0;
        doBuilder.doId = WmOrderNoFactory.TKCK(orderId, orderItem, 0, 0);
        return doBuilder;
    }

    public DoBuilder addInventoryInfo(OpsInventory inv, int qty) {
        invInfo invInfo = new invInfo(InventoryTableTypeEnum.NORMAL, inv.getInventoryId(), inv.getModelno(), qty);
        this.invs.add(invInfo);
        return this;
    }

    public DoBuilder addInventoryInfo(Map<OpsInventory, Integer> invqtyMap) {
        for (Map.Entry<OpsInventory, Integer> entry : invqtyMap.entrySet()) {
            this.addInventoryInfo(entry.getKey(), entry.getValue());
        }
        return this;
    }





    public DoBuilder addInventoryInfo(OpsInventoryMove move, int qty) {
        invInfo invInfo = new invInfo(InventoryTableTypeEnum.TRANS, move.getInventoryId(), move.getModelno(), qty);
        this.invs.add(invInfo);
        return this;
    }

    public DoBuilder warehouseInfo(String fromWarehouse, String toWarehouse) {
        this.warehouse = fromWarehouse;
        this.gatherWarehouse = toWarehouse;
        return this;
    }

    public DoBuilder warehouseInfo(String warehouse) {
        this.warehouse = warehouse;
        this.gatherWarehouse = warehouse;
        return this;
    }
    public DoBuilder customerNo(String customerNo) {
        this.customerNo = customerNo;
        return this;
    }

    public DoBuilder addressInfo(OpsWarehouse warehouse) {
        this.province = warehouse.getProvince();
        this.city = warehouse.getCity();
        this.district = warehouse.getDistrict();
        this.street = warehouse.getAdress();
        this.address = warehouse.getAdress();
        this.linkman = warehouse.getLinkman();
        if (!StringUtils.isEmpty(warehouse.getLinkMobile()) && StringPhoneUtil.isMobil(warehouse.getLinkMobile())) {// 手机号
            this.mobile = warehouse.getLinkMobile();
        }
        // 是电话 且不是手机号 存电话
        if (!StringUtils.isEmpty(warehouse.getLinkPhone()) && StringPhoneUtil.isPhone(warehouse.getLinkPhone()) && !StringPhoneUtil.isMobil(warehouse.getLinkPhone())) {
            this.phone = warehouse.getLinkPhone();
        }
        this.postcode = warehouse.getPostCode() + "";
        return this;
    }


    public DoBuilder statusInfo(DoStatusEnum doStatusEnum, DoWaitTypeEnum doWaitTypeEnum, int outQty) {
        this.doStatusEnum = doStatusEnum;
        this.doWaitTypeEnum = doWaitTypeEnum;
        this.outQty = outQty;
        return this;
    }

    public DoBuilder statusInfo(DoStatusEnum doStatusEnum) {
        this.doStatusEnum = doStatusEnum;
        this.doWaitTypeEnum = DoWaitTypeEnum.OK;
        this.outQty = 0;
        return this;
    }


    public DoBuilder creator(String creator, String updater) {
        this.creator = creator;
        this.updater = updater;
        return this;
    }


    public OpsDoDto build() throws OpsException {
        if (doTypeEnum == null) {
            throw Exceptions.OpsException("指令类型不能为空");
        }
        if (doSourceEnum == null) {
            doSourceEnum = DoSourceEnum.ALL;
        }
        OpsDo opsDo = new OpsDo();
        OpsDoItem opsDoItem = new OpsDoItem();
        List<OpsDoItemInventory> opsDoItemInventoryList = new ArrayList<>();
        opsDo.setDoType(doTypeEnum.getType());
        opsDo.setDoSource(doSourceEnum.getType());
        opsDo.setOrderId(orderId);
        opsDo.setOrderItem(orderItem.toString());
        opsDo.setNum(num);
        opsDo.setAssNum(assNum);
        opsDo.setDoId(doId);
        opsDoItem.setDoId(doId);
        opsDoItem.setDoItem(1);

        for (invInfo inv : invs) {
            OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
            opsDoItemInventory.setDoId(doId);
            opsDoItemInventory.setDoItem(1);

            opsDoItemInventory.setInventoryTableType(inv.invTableTypeEnum.getType());
            opsDoItemInventory.setInventoryId(inv.inventoryId);
            opsDoItemInventory.setUseQty(inv.qty);
            if (opsDoItem.getModelno() == null) {
                opsDoItem.setModelno(inv.modelno);
            }
            opsDoItem.setQty(Optional.ofNullable(opsDoItem.getQty()).orElse(0) + inv.qty);
            opsDoItemInventoryList.add(opsDoItemInventory);
        }

        opsDo.setWarehouseCode(warehouse);
        opsDo.setGatherWarehouseCode(gatherWarehouse);
        opsDo.setCustomerNo(customerNo);
        opsDo.setProvince(province);
        opsDo.setCity(city);
        opsDo.setDistrict(district);
        opsDo.setStreet(street);
        opsDo.setAddress(address);
        opsDo.setLinkman(linkman);
        opsDo.setMobile(mobile);
        opsDo.setPhone(phone);
        opsDo.setPostcode(postcode);
        opsDo.setDoStatus(doStatusEnum.getStatus());
        opsDo.setWaitType(doWaitTypeEnum.getType());
        opsDoItem.setOutQty(outQty);


        opsDo.setDelflag(0);
        opsDo.setVersion(0);
        opsDo.setCreTime(new Date());
        opsDo.setModifyTime(new Date());
        opsDo.setCreator(creator);
        opsDo.setModifier(updater);
        opsDoItem.setDelflag(0);
        opsDoItem.setVersion(0);
        opsDoItem.setCreTime(new Date());
        opsDoItem.setModifyTime(new Date());
        opsDoItem.setCreator(creator);
        opsDoItem.setModifier(updater);
        for (OpsDoItemInventory opsDoItemInventory : opsDoItemInventoryList) {
            opsDoItemInventory.setDelflag(0);
            opsDoItemInventory.setVersion(0L);
            opsDoItemInventory.setCreTime(new Date());
            opsDoItemInventory.setModifyTime(new Date());
            opsDoItemInventory.setCreator(creator);
            opsDoItemInventory.setModifier(updater);
        }
        return new OpsDoDto(opsDo, opsDoItem, opsDoItemInventoryList);
    }


}
