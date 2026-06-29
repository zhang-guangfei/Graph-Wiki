package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.enums.DoSourceEnum;


import java.util.*;

/**
 * @author C02483
 * @version 1.0
 * @description: 生成指令时
 * @date 2021/9/24 15:11
 */
public class InventoryDispatchDto {
    /**
     * 集约仓库
     */
    private String gatherhouse;
    /**
     * 普通出库，出在库<仓库号，库存列表>
     */
    private Map<String, List<InvQty>> invQtyMap = new HashMap<>();
    /**
     * 普通出库，出在途<仓库号，库存列表>
     */
    private Map<String, List<InvMoveQty>> invMoveQtyMap = new HashMap<>();

    private AssBom assBom;

    private Boolean specialBom;
    /**
     * 加工出库，出在库<加工bom,仓库号，库存列表>
     */
    private Map<ProductBom, Map<String, List<InvQty>>> BominvQtyMap = new HashMap<>();
    /**
     * 加工出库，出在途<加工bom,仓库号，库存列表>
     */
    private Map<ProductBom, Map<String, List<InvMoveQty>>> BominvMoveQtyMap = new HashMap<>();
    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 13:33
     * @description：加工的数量
     */
    private Map<ProductBom, Integer> productBomQtyMap = new HashMap<>();

    DoSourceEnum doSourceEnum;

    private Map<Object, Integer> inventoryMapSorted;
    /**
     * @author ：c02483
     * @date ：Created in 2021/11/9 19:35
     * @description：加工单缺货数量
     */
    /*  private Map<ProductBom, Map<String, Integer>> asslackmodelListMap=new HashMap<>();*/
    public InventoryDispatchDto(InventoryCkByOrderOutDto outdto) {
        this.gatherhouse = outdto.getWarehouseCode();
        setInvQtyMap(outdto);
        setInvMoveQtyMap(outdto);
        setBominvQtyMap(outdto);
        setBominvMoveQtyMap(outdto);
        setAssBom(outdto.getAssBom());
        setDoSourceEnum(outdto.getDoSourceEnum());
        setInventoryMapSorted(outdto.getInventoryMapSorted());
        setSpecialBom(outdto.getSpecialBom());
        /*  setProductBomQtyMap(outdto.getAssBom());*/

    }

    private void setInvQtyMap(InventoryCkByOrderOutDto outdto) {
        Iterator iterator = outdto.getInventoryMap().keySet().iterator();
        while (iterator.hasNext()) {
            OpsInventory inventory = (OpsInventory) iterator.next();
            InvQty invqty = new InvQty((Integer) outdto.getInventoryMap().get(inventory), inventory);
            if (invQtyMap.containsKey(inventory.getWarehouseCode())) {
                invQtyMap.get(inventory.getWarehouseCode()).add(invqty);
            } else {
                List<InvQty> list = new ArrayList<>();
                list.add(invqty);
                invQtyMap.put(inventory.getWarehouseCode(), list);
            }
        }
    }

    public void setInvMoveQtyMap(InventoryCkByOrderOutDto outdto) {
        Iterator iteratormove = outdto.getInventoryMoveMap().keySet().iterator();
        while (iteratormove.hasNext()) {
            OpsInventoryMove inventory = (OpsInventoryMove) iteratormove.next();
            InvMoveQty invqty = new InvMoveQty((Integer) outdto.getInventoryMoveMap().get(inventory), inventory);
            if (invMoveQtyMap.containsKey(inventory.getWarehouseCode())) {
                invMoveQtyMap.get(inventory.getWarehouseCode()).add(invqty);
            } else {
                List<InvMoveQty> invqtyList = new ArrayList<>();
                invqtyList.add(invqty);
                invMoveQtyMap.put(inventory.getWarehouseCode(), invqtyList);
            }
        }
    }

    public void setBominvQtyMap(InventoryCkByOrderOutDto outdto) {
        if (Objects.isNull(outdto.getAssBom())) {
            return;
        }
        AssBom assBom = outdto.getAssBom();
        for (AssBomDetail assBomDetail : assBom.getDetailList()) {
            if( assBomDetail.getMapqty() == null){
                continue;
            }
            Iterator iterator = assBomDetail.getMapqty().keySet().iterator();
            while (iterator.hasNext()) {
                OpsInventory inventory = (OpsInventory) iterator.next();
                int useqty = assBomDetail.getMapqty().get(inventory);
                if (BominvQtyMap.containsKey(assBom.getProductBom())) {
                    Map<String, List<InvQty>> invQtyMap = (Map<String, List<InvQty>>) BominvQtyMap.get(assBom.getProductBom());
                    if (invQtyMap.containsKey(inventory.getWarehouseCode())) {
                        InvQty invQty = new InvQty(useqty, inventory);
                        invQtyMap.get(inventory.getWarehouseCode()).add(invQty);
                    } else {
                        InvQty invQty = new InvQty(useqty, inventory);
                        List<InvQty> invQtyList = new ArrayList<>();
                        invQtyList.add(invQty);
                        invQtyMap.put(inventory.getWarehouseCode(), invQtyList);
                    }
                } else {
                    Map<String, List<InvQty>> invQtyMap = new HashMap<>();
                    InvQty invQty = new InvQty(useqty, inventory);
                    List<InvQty> invQtyList = new ArrayList<>();
                    invQtyList.add(invQty);
                    invQtyMap.put(inventory.getWarehouseCode(), invQtyList);
                    BominvQtyMap.put(assBom.getProductBom(), invQtyMap);
                }
            }
        }

    }

    public void setBominvMoveQtyMap(InventoryCkByOrderOutDto outdto) {
        if (Objects.isNull(outdto.getAssBom())) {
            return;
        }
        AssBom assBom = outdto.getAssBom();
        for (AssBomDetail assBomDetail : assBom.getDetailList()) {
            if (Objects.isNull(assBomDetail.getMapMoveqty())) {
                continue;
            }
            Iterator iterator = assBomDetail.getMapMoveqty().keySet().iterator();
            while (iterator.hasNext()) {
                OpsInventoryMove inventory = (OpsInventoryMove) iterator.next();
                int useqty = assBomDetail.getMapMoveqty().get(inventory);
                if (BominvMoveQtyMap.containsKey(assBom.getProductBom())) {
                    Map<String, List<InvMoveQty>> invmoveQtyMap = (Map<String, List<InvMoveQty>>) BominvMoveQtyMap.get(assBom.getProductBom());
                    if (invmoveQtyMap.containsKey(inventory.getWarehouseCode())) {
                        InvMoveQty invQty = new InvMoveQty(useqty, inventory);
                        invmoveQtyMap.get(inventory.getWarehouseCode()).add(invQty);
                    } else {
                        InvMoveQty invQty = new InvMoveQty(useqty, inventory);
                        List<InvMoveQty> invQtyList = new ArrayList<>();
                        invQtyList.add(invQty);
                        invmoveQtyMap.put(inventory.getWarehouseCode(), invQtyList);
                    }
                } else {
                    Map<String, List<InvMoveQty>> invmoveQtyMap = new HashMap<>();
                    InvMoveQty invQty = new InvMoveQty(useqty, inventory);
                    List<InvMoveQty> invQtyList = new ArrayList<>();
                    invQtyList.add(invQty);
                    invmoveQtyMap.put(inventory.getWarehouseCode(), invQtyList);
                    BominvMoveQtyMap.put(assBom.getProductBom(), invmoveQtyMap);
                }
            }
        }

    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/9/28 16:25
     * @description：在库占用
     */
    public class InvQty {
        private int useqty;
        private OpsInventory inventory;

        public InvQty(int useqty, OpsInventory inventory) {
            this.useqty = useqty;
            this.inventory = inventory;
        }

        public int getUseqty() {
            return useqty;
        }

        public void setUseqty(int useqty) {
            this.useqty = useqty;
        }

        public OpsInventory getInventory() {
            return inventory;
        }

        public void setInventory(OpsInventory inventory) {
            this.inventory = inventory;
        }
    }

    public DoSourceEnum getDoSourceEnum() {
        return doSourceEnum;
    }

    public void setDoSourceEnum(DoSourceEnum doSourceEnum) {
        this.doSourceEnum = doSourceEnum;
    }

    public void setInventoryMapSorted(Map<Object, Integer> inventoryMapSorted) {
        this.inventoryMapSorted = inventoryMapSorted;
    }

    public Map<Object, Integer> getInventoryMapSorted() {
        return inventoryMapSorted;
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/9/28 16:25
     * @description：在途占用
     */
    public class InvMoveQty {
        private int useqty;
        private OpsInventoryMove inventory;

        public InvMoveQty(int useqty, OpsInventoryMove inventory) {
            this.useqty = useqty;
            this.inventory = inventory;
        }

        public int getUseqty() {
            return useqty;
        }

        public void setUseqty(int useqty) {
            this.useqty = useqty;
        }

        public OpsInventoryMove getInventory() {
            return inventory;
        }

        public void setInventory(OpsInventoryMove inventory) {
            this.inventory = inventory;
        }
    }

    public String getGatherhouse() {
        return gatherhouse;
    }

    public Map<String, List<InvQty>> getInvQtyMap() {
        return invQtyMap;
    }

    public Map<String, List<InvMoveQty>> getInvMoveQtyMap() {
        return invMoveQtyMap;
    }

    public Map<ProductBom, Map<String, List<InvQty>>> getBominvQtyMap() {
        return BominvQtyMap;
    }

    public Map<ProductBom, Map<String, List<InvMoveQty>>> getBominvMoveQtyMap() {
        return BominvMoveQtyMap;
    }

    public AssBom getAssBom() {
        return assBom;
    }

    public AssBomDetail getAssBomDetail(String modelno) {
        for (AssBomDetail assBomDetail : assBom.getDetailList()) {
            if (Objects.equals(assBomDetail.getProductBomDetail().getModelno(), modelno)) {
                return assBomDetail;
            }
        }
        return null;

    }

    public void setAssBom(AssBom assBom) {
        this.assBom = assBom;
    }

    public Boolean getSpecialBom() {
        return specialBom;
    }

    public void setSpecialBom(Boolean specialBom) {
        this.specialBom = specialBom;
    }

    /*   public void setProductBomQtyMap(AssBom assBom) {
        this.productBomQtyMap.put(assBom.getProductBom(), assBom.getQty());
    }

    public Map<ProductBom, Integer> getProductBomQtyMap() {
        return productBomQtyMap;
    }
*/
   /* public void setAsslackmodelListMap( AssBom assBom) {
        Map<ProductBom, Map<String, Integer>> asslackmodelListMap;
        this.asslackmodelListMap = asslackmodelListMap;
    }

    public Map<ProductBom, Map<String, Integer>> getAsslackmodelListMap() {
        return asslackmodelListMap;
    }*/
}
