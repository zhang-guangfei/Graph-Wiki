package com.sales.ops.serviceimpl.wmOrder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.InventoryForTransInputDto;
import com.sales.ops.dto.order.OpsRoAddItemDto;
import com.sales.ops.dto.order.OpsWarehouseRoDto;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.sales.ops.dto.util.CreateInfoDto;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.log.*;
import com.sales.ops.service.order.state.OrderStateService;
import com.sales.ops.service.purchase.PurchaseCtcFinishService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.utils.WmOrderNoFactory;
import com.sales.ops.utils.WmTaskFactory;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.StockAssemblyFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author ：C12961
 * @version: 1.0$
 * @description：收货指令
 * @date ：Created in 2021/9/26 10:27
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OpsRoServiceImpl implements OpsRoService {
    @Resource
    private BaseInventoryService baseInventoryService;
    @Resource
    private OpsInventoryPropertyService opsInventoryPropertyService;
    @Resource
    private BaseDoService baseDoService;
    @Resource
    private OpsDoService opsDoService;
    @Resource
    private OpsPcoService opsPcoService;
    @Resource
    private BaseRoService baseRoService;
    @Resource
    private OpsRoMapper opsRoMapper;
    @Resource
    private OpsRoItemMapper opsRoItemMapper;
    @Resource
    private OpsRoItemInventoryMapper opsRoItemInventoryMapper;
    @Resource
    private OpsRoBarcodeMapper opsRoBarcodeMapper;
    @Resource
    private WmOrderTaskService wmOrderTaskService;
    @Autowired
    private OpsInventoryLogService opsInventoryLogService;
    @Autowired
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private OpsPurchaseorderMapper opsPurchaseorderMapper;
    @Resource
    private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

    @Autowired
    private StockAssemblyFeignApi stockAssemblyFeignApi;
    @Autowired
    private PurchaseCtcFinishService purchaseCtcFinishService;
    @Resource
    private OrderStateService OrderStateService;
    @Autowired
    private OpsRoBarcodeService opsRoBarcodeService;
    @Autowired
    private OpsRoPostService opsRoPostService;
    @Autowired
    private OpsImpdataService opsImpdataService;


    /**
     * 根据查询对象，分页查询符合条件的收货指令
     * @param pageModel 分页三参数，入库类型、仓库、状态、供应商--入库指令号、发票号、开始时间、结束时间
     * @return 返回一个符合查询条件的集合，并且封装分页
     */
    @Override
    public PageInfo<OpsRo> findRoByPage(PageModel<OpsWarehouseRoDto> pageModel) {
        OpsRoExample example = new OpsRoExample();
        OpsRoExample.Criteria criteria = example.createCriteria();
        OpsWarehouseRoDto condition = pageModel.getCondition();
        if (condition.getRoType() != null && !"".equals(condition.getRoType())) {// 入库类型
            criteria.andRoTypeEqualTo(condition.getRoType());
        }
        if (condition.getWarehouseCode() != null && !"".equals(condition.getWarehouseCode())) {// 仓库编码
            criteria.andWarehouseCodeEqualTo(condition.getWarehouseCode());
        }
        if (condition.getRoStatus() != null && !"".equals(condition.getRoStatus())) {// 状态 ？ 收货状态//todo
            criteria.andRoStatusEqualTo(condition.getRoStatus());
        }
        if (condition.getSupplierId() != null && !"".equals(condition.getSupplierId())) {// 供应商
            criteria.andSupplierIdEqualTo(condition.getSupplierId());
        }
        if (condition.getRoId() != null && !"".equals(condition.getRoId())) {// 入库指令号
            criteria.andRoIdLike("%" + condition.getRoId() + "%");
        }
        if (condition.getInvoiceNo() != null && !"".equals(condition.getInvoiceNo())) {// 发票号 --到货发票号
            criteria.andInvoiceNoLike("%" + condition.getInvoiceNo() + "%");
        }
        if (condition.getBeginTime() != null && !"".equals(condition.getBeginTime())) {
            criteria.andCreTimeGreaterThanOrEqualTo(condition.getBeginTime());
        }
        if (condition.getEndTime() != null && !"".equals(condition.getEndTime())) {
            criteria.andCreTimeLessThanOrEqualTo(condition.getEndTime());
        }
        criteria.andDelflagEqualTo(0);// 删除标志位
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize(), pageModel.getOrderBy());
        return new PageInfo(opsRoMapper.selectByExample(example));
    }




    /**
     * @description 修改原ro的数量，并且拆分一条新ro出来
     * @params DBDo： 原调拨入库单
     * subQty: 要拆分出来的ro数量
     * num: 新的ro的num号
     * @author C12961
     * @date 2023/5/24 16:53
     */
    @Override
    public String splitDBRo(OpsDo DBDo, Integer subQty, int num) throws OpsException {
        //查出RO
        List<OpsRo> opsRos = baseRoService.findDBRoByDBDo(DBDo);
        if (CollectionUtils.isNotEmpty(opsRos)) {
            OpsRo opsRo = opsRos.get(0);
            OpsRoItem opsRoItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
            //修改原调拨入的数量
            updateQtyForRoItem(opsRoItem, opsRoItem.getQty() - subQty, "调拨入分纳");
            //拆分出新的调拨入
            OpsRoDto dto = splitRoAndItemAndTaskFromRo(opsRos.get(0), opsRoItem, num, subQty, "FN", !DBDo.getWarehouseCode().equals(DBDo.getGatherWarehouseCode()));
            return dto.getOpsRo().getRoId();
        }
        return null;
    }

    /**
     * @description 修改roItem的数量
     * @author C12961
     * @date 2023/5/24 16:52
     */
    public void updateQtyForRoItem(OpsRoItem opsRoItem, Integer qty, String remark) throws OpsException {
        OpsRoItemExample roItemExample = new OpsRoItemExample();
        roItemExample.createCriteria().andIdEqualTo(opsRoItem.getId())
                .andVersionEqualTo(opsRoItem.getVersion()).andDelflagEqualTo(0);
        OpsRoItem updateOpsRoItem = new OpsRoItem();
        updateOpsRoItem.setQty(qty);// 当前发出的数量
        updateOpsRoItem.setVersion(opsRoItem.getVersion() + 1);
        updateOpsRoItem.setModifyTime(new Date());
        updateOpsRoItem.setModifier(UserDto.WMS.getUserName());
        updateOpsRoItem.setRemark(remark);
        int result = opsRoItemMapper.updateByExampleSelective(updateOpsRoItem, roItemExample);
        if (result != 1) {
            throw Exceptions.OpsException("修改roItem数量失败:" + opsRoItem.getId() + "修改原因" + remark);
        }
    }

    private OpsRoDto splitRoAndItemAndTaskFromRo(OpsRo opsOldRo, OpsRoItem opsRoItem, int num, Integer qty, String remark,boolean createTask) throws OpsException {
        String dbrkId = WmOrderNoFactory.DBRK_FN(opsOldRo.getOrderId(), Integer.valueOf(opsOldRo.getOrderItem()), num, 0);
        if (RoTypeEnum.TKRK.getType().equalsIgnoreCase(opsOldRo.getRoType())) {
            dbrkId = WmOrderNoFactory.TKRK_FN(opsOldRo.getOrderId(), Integer.valueOf(opsOldRo.getOrderItem()), num, 0);
        }
        OpsRo opsRo = new OpsRo();
        opsRo.setRoId(dbrkId);
        opsRo.setOrderId(opsOldRo.getOrderId());
        opsRo.setOrderItem(opsOldRo.getOrderItem());
        opsRo.setNum(num);
        opsRo.setAssNum(0);
        opsRo.setExtNum(0);
        opsRo.setRoSource(RoSourceEnum.EMPTY.getType());
        opsRo.setRoType(opsOldRo.getRoType());
        opsRo.setWarehouseCode(opsOldRo.getWarehouseCode());
        opsRo.setRoStatus(RoStatusEnum.INIT.getStatus());// 初始
        opsRo.setTransType("");// 运输方式
        opsRo.setCarried("");// 到货承运商
        opsRo.setExpressCode("");// 到货承运商
        opsRo.setCustomerNo(opsOldRo.getCustomerNo());
        opsRo.setInvoiceNo(null);// 到货发票号
        opsRo.setSupplierId("");// 到货供应商
        opsRo.setCreator(remark);
        OpsRoItem roItem = new OpsRoItem();
        roItem.setRoId(opsRo.getRoId());
        roItem.setRoItem(opsRoItem.getRoItem());
        roItem.setModelno(opsRoItem.getModelno());
        roItem.setQty(qty);
        roItem.setRecQty(0);
        roItem.setRemark(remark);
        //插入新调拨入库单
        insertRo(opsRo, roItem, null, new UserDto("FN"));
        wmOrderTaskService.addRoTask(opsRo.getRoId());

        return new OpsRoDto(opsRo, opsRoItem);
    }

    /**
     * @description 采购入库补库，预到货时创建
     * @author C12961
     * @date 2023/5/24 17:17
     */
    public OpsRo initRoForInvoice(String roId, String orderId, String orderItem, int num, RoTypeEnum roTypeEnum,
                                  String warehouseCode, String supplierId, String invoiceNo, Long invoiceId) {
        OpsRo ro = new OpsRo();
        ro.setRoId(roId);
        ro.setOrderId(orderId);
        ro.setOrderItem(orderItem);
        ro.setNum(num);
        ro.setRoType(roTypeEnum.getType());
        ro.setWarehouseCode(warehouseCode);
        ro.setSupplierId(supplierId);
        ro.setRoStatus(RoStatusEnum.WAIT.getStatus());
        ro.setInvoiceNo(invoiceNo);
        ro.setInvoiceId(invoiceId);
        ro.setDelflag(0);
        ro.setCreator(UserDto.ADMIN.getUserName());
        ro.setCreTime(new Date());
        return ro;
    }

    /**
     * @description 采购入库补库，预到货时创建
     * @author C12961
     * @date 2023/5/24 17:17
     */
    public OpsRoItem initRoItemForInvoice(String roId, int qty, String modelno, String greenCode, BigDecimal netWeight) {
        OpsRoItem roItem = new OpsRoItem();
        roItem.setRoId(roId);
        roItem.setRoItem(1);
        roItem.setModelno(modelno);
        roItem.setQty(Integer.valueOf(qty));
        roItem.setRecQty(0);
        roItem.setCreator(UserDto.ADMIN.getUserName());
        roItem.setCreTime(new Date());
        roItem.setGreenCode(greenCode);
        roItem.setNetweight(netWeight);//单个型号净重
        roItem.setQaStatus(QAStatusEnum.NORMAL.getType());//0良品  1不良品   2 未检品
        return roItem;
    }

    /**
     * @description 预到货时创建ro
     * @author C12961
     * @date 2023/5/24 17:17
     */
    public OpsRoItemInventory initRoItemInventoryForInvoice(String roId, Integer roItem, Long inventoryId,
                                                            Integer qty) {
        OpsRoItemInventory roItemInventory = new OpsRoItemInventory();
        roItemInventory.setRoId(roId);
        roItemInventory.setRoItem(roItem);
        roItemInventory.setInventoryId(inventoryId);
        roItemInventory.setQty(qty);
        roItemInventory.setVersion(0L);
        roItemInventory.setDelflag(0);
        roItemInventory.setCreator(UserDto.ADMIN.getUserName());
        roItemInventory.setCreTime(new Date());
        roItemInventory.setRecQty(0);
        return roItemInventory;
    }

    /**
     * @description：采购调拨入库，采购接单时，初始化采购调拨入库单
     * @author ：C12961
     * @date ：Created in 2021/10/2 14:07
     */
    @Override
    public OpsRo initOpsRo(String roId, OpsRequestpurchase opsRequestpurchase) {
        OpsRo opsRo = new OpsRo();
        opsRo.setRoId(roId);
        opsRo.setRoSource("");// todo 来源待定
        opsRo.setOrderId(opsRequestpurchase.getOrderno());
        opsRo.setOrderItem(opsRequestpurchase.getItemno() + "");
        if (null != opsRequestpurchase.getSplititemno()) {
            opsRo.setNum(opsRequestpurchase.getSplititemno());
        } else {
            opsRo.setNum(0);
        }
        opsRo.setAssNum(0);
        opsRo.setExtNum(0);
        opsRo.setRoType(RoTypeEnum.CGDBRK.getType());
        opsRo.setWarehouseCode(opsRequestpurchase.getRequestwarehouseid());
        opsRo.setRoStatus(0);// 初始
        opsRo.setTransType("");// 运输方式
        opsRo.setCarried("");// 到货承运商
        opsRo.setExpressCode("");// 到货承运商
        opsRo.setCustomerNo(opsRequestpurchase.getCustomerno());
        opsRo.setInvoiceNo("");// 到货发票号
        opsRo.setSupplierId("");// 到货供应商
        opsRo.setDelflag(0);
        opsRo.setCreator("补库调拨");
        opsRo.setCreTime(new Date());
        return opsRo;
    }


    /**
     * @description：采购调拨入库，采购接单时，初始化采购调拨入库单
     * @author ：
     * @date ：Created in 2021/10/2 14:07
     */
    @Override
    public OpsRoItem initOpsRoItem(String roid, OpsRequestpurchase opsRequestpurchase) {
        OpsRoItem roItem = new OpsRoItem();
        roItem.setRoId(roid);
        roItem.setRoItem(1);
        roItem.setQty(opsRequestpurchase.getQuantity());
        //bugid:10651 C14717 20230511 优化判断绿标
        if (org.apache.commons.lang.StringUtils.isNotBlank(opsRequestpurchase.getProducttag()) && "0".equals(opsRequestpurchase.getProducttag())) {
            roItem.setGreenCode("H");
        }
        roItem.setRecQty(0);
        roItem.setModelno(opsRequestpurchase.getModelno());
        roItem.setVersion(0);
        roItem.setDelflag(0);
        roItem.setCreator("补库调拨");
        roItem.setCreTime(new Date());
        return roItem;
    }


    /**
     * @author ：c02483
     * @date ：Created in 2021/10/2 13:44
     * @description：创建RO收货指令
     */
    @Override
    public OpsRo insertRo(OpsRo opsRo, OpsRoItem opsRoItem, List<OpsRoItemInventory> roItemInventoryList,
                          UserDto userDto) throws OpsException {
        opsRo.setVersion(0);
        opsRo.setCreator(userDto.getUserName());
        opsRo.setCreTime(new Date());
        opsRo.setModifier(userDto.getUserName());
        opsRo.setModifyTime(new Date());
        if (opsRo.getDelflag() == null) {
            opsRo.setDelflag(0);
        }
        opsRoMapper.insertSelective(opsRo);
        opsRoItem.setVersion(0);
        opsRoItem.setCreator(userDto.getUserName());
        opsRoItem.setCreTime(new Date());
        opsRoItem.setModifier(userDto.getUserName());
        opsRoItem.setModifyTime(new Date());
        if (opsRoItem.getDelflag() == null) {
            opsRoItem.setDelflag(0);
        }
        opsRoItemMapper.insertSelective(opsRoItem);
        if (!CollectionUtils.isEmpty(roItemInventoryList)) {
            for (OpsRoItemInventory roItemInventory : roItemInventoryList) {
                if (roItemInventory.getDelflag() == null) {
                    roItemInventory.setDelflag(0);
                }
                roItemInventory.setVersion(0L);
                roItemInventory.setCreTime(new Date());
                roItemInventory.setCreator(userDto.getUserName());
                roItemInventory.setModifyTime(new Date());
                roItemInventory.setModifier(userDto.getUserName());
                opsRoItemInventoryMapper.insertSelective(roItemInventory);
            }
        }
        return opsRo;
    }

    @Override
    public List<OpsRo> CreateRoForDispatch(List<CreRoByInventoryDto> roList, UserDto userDto) throws OpsException {
        List<OpsRo> list = new ArrayList<>();
        List<OpsWmOrderTask> taskList = new ArrayList<>();
        for (CreRoByInventoryDto dto : roList) {
            OpsRo ro = insertRo(dto.getOpsRo(), dto.getOpsRoItemList().get(0), dto.getOpsRoItemInventoryList(), userDto);
            OpsWmOrderTask opsWmOrderTask = WmTaskFactory.RoTask(ro.getRoId(), SendStatusEnum.SUSPENDED, new CreateInfoDto(userDto));
            taskList.add(opsWmOrderTask);
            list.add(ro);
        }
        //批量插入task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        return list;
    }

    /**
     * 打算将此方法废弃，因为roItemInventory中的inventoryId理应只写moveId,但是以前的不下发的ro都写了NormalId
     * @param opsRo
     * @param roItemInventory
     * @param userDto
     * @throws OpsException
     */
    @Override
    public void updateQtyForRo(OpsRo opsRo, OpsRoItemInventory roItemInventory, UserDto userDto) throws OpsException {
        baseInventoryService.addQtyInventory(roItemInventory.getInventoryId(), roItemInventory.getQty(),
                InventoryTableTypeEnum.NORMAL.getType(), userDto);
        opsInventoryLogService.insertOpsInventoryLogForRo(opsRo, roItemInventory, userDto);
    }

    @Override
    public void updatePreQtyForRo(OpsRo opsRo, OpsRoItemInventory roItemInventory, UserDto userDto)
            throws OpsException {
        // 无需判断风险在库
        baseInventoryService.addPreQtyInventory(roItemInventory.getInventoryId(), roItemInventory.getQty(),
                InventoryTableTypeEnum.NORMAL.getType(), userDto);
    }

    @Override
    public void updateRoItemQtyByDoItemId(Long opsRoItemId, Integer qty,Integer version) throws OpsException{
        OpsRoItem opsRoItem = new OpsRoItem();
        opsRoItem.setId(opsRoItemId);
        opsRoItem.setQty(qty);
        if(Objects.isNull(qty) || qty <= 0){
            throw Exceptions.OpsException("roItem数量更新异常:"+ qty);
        }
        opsRoItem.setVersion(version+1);
        opsRoItem.setModifyTime(DateUtil.getNow());
        opsRoItemMapper.updateByPrimaryKeySelective(opsRoItem);
    }

    @Override
    public List<OpsRo> createRoForAdjust(List<InventoryForAdjustDto> dtoList, UserDto userDto) throws OpsException {
        List<OpsRo> roList = new ArrayList<>();
        for (InventoryForAdjustDto adjustDto : dtoList) {
            OpsRo opsRo = new OpsRo();
            OpsRoItem opsRoItem = new OpsRoItem();
            OpsRoItemInventory opsRoItemInventory = new OpsRoItemInventory();
            opsRo.setOrderId(adjustDto.getOrderId());
            opsRo.setOrderItem(String.valueOf(roList.size() + 1));
            opsRo.setRoId(opsRo.getOrderId() + opsRo.getOrderItem());
            opsRo.setNum(0);
            opsRo.setRoType(RoTypeEnum.TZRK.getType());
            opsRo.setRoStatus(RoStatusEnum.FINISH.getStatus());
            opsRo.setWarehouseCode(adjustDto.getWarehouseCode());
            opsRo.setCustomerNo(adjustDto.getCustomerNo());
            opsRoItem.setRoId(opsRo.getRoId());
            opsRoItem.setRoItem(1);
            opsRoItem.setModelno(adjustDto.getModelno());
            opsRoItem.setQty(adjustDto.getQty());
            opsRoItem.setRecQty(adjustDto.getQty());
            // 查询库存
            OpsInventoryProperty property = new OpsInventoryProperty();
            // property.setInventoryTypeCode();
            /*property.setCustomerNo(adjustDto.getCustomerNo());
            property.setPpl(adjustDto.getPpl());
            property.setProjectCode(adjustDto.getProjectCode());
            property.setGroupCustomerNo(adjustDto.getGroupCustomerNo());*/
            property.setInventoryTypeCode("TY");
            if (!StringUtils.isEmpty(adjustDto.getCustomerNo())) {
                property.setCustomerNo(adjustDto.getCustomerNo());
                property.setInventoryTypeCode("GK-TY");
            }
            if (!StringUtils.isEmpty(adjustDto.getPpl())) {
                property.setPpl(adjustDto.getPpl());
                property.setInventoryTypeCode("GK-PPL");
            }
            if (!StringUtils.isEmpty(adjustDto.getProjectCode())) {
                property.setProjectCode(adjustDto.getProjectCode());
                property.setInventoryTypeCode("ZL-PJ");
            }
            if (!StringUtils.isEmpty(adjustDto.getGroupCustomerNo())) {
                property.setGroupCustomerNo(adjustDto.getGroupCustomerNo());
                property.setInventoryTypeCode("ZL-JT");
            }
            Long propertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(property, userDto);
            OpsInventory inv = new OpsInventory();
            inv.setWarehouseCode(adjustDto.getWarehouseCode());
            inv.setModelno(adjustDto.getModelno());
            inv.setInventoryPropertyId(propertyId);
            List<OpsInventory> invList = baseInventoryService.findOpsInventory(inv);
            Long invId = null;
            if (invList.size() == 0) {
                inv.setInventoryStatus(InventoryStatusEnum.NORMAL.getCode());
                inv.setQuantity(0);
                inv.setPrepareQuantity(0);
                if (adjustDto.getPrice() != null) {
                    inv.setPrice(BigDecimal.valueOf(adjustDto.getPrice()));
                }
                inv.setQaStatus(adjustDto.getQaStatus());
                invId = baseInventoryService.createInvReturnId(inv, UserDto.AUTO);
                // 此处不用写日志

            } else {
                invId = invList.get(0).getInventoryId();
            }
            opsRoItemInventory.setRoId(opsRo.getRoId());
            opsRoItemInventory.setRoItem(opsRoItem.getRoItem());
            opsRoItemInventory.setInventoryId(invId);
            opsRoItemInventory.setQty(adjustDto.getQty());
            opsRoItemInventory.setRecQty(adjustDto.getQty());
            opsRo.setDelflag(1);
            opsRoItem.setDelflag(1);
            opsRoItemInventory.setDelflag(1);
            // 插入数据
            insertRo(opsRo, opsRoItem, Collections.singletonList(opsRoItemInventory),
                    userDto);

            updateQtyForRo(opsRo, opsRoItemInventory, userDto);
            roList.add(opsRo);
        }
        return roList;
    }

    /**
     * WMS调账
     */
    @Override
    public List<OpsRo> createRoForWMSAdjust(List<InventoryForAdjustDto> dtoList, UserDto userDto) throws OpsException {
        List<OpsRo> roList = new ArrayList<>();

        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        for (InventoryForAdjustDto adjustDto : dtoList) {
            OpsRo opsRo = new OpsRo();

            OpsRoItem opsRoItem = new OpsRoItem();
            opsRo.setOrderId(adjustDto.getOrderId());
            opsRo.setOrderItem(String.valueOf(roList.size() + 1));
            opsRo.setNum(0);
            String roId = String.format(OrderIDFormatEnum.RO_FORMAT.getFormat(), opsRo.getOrderId(),
                    String.format("%03d", roList.size() + 1), String.format("%03d", 0), String.format("%03d", 0));
            opsRo.setRoId(roId);
            opsRo.setRoType(RoTypeEnum.PYRK.getType());// todo 更改状态
            opsRo.setRoStatus(RoStatusEnum.FINISH.getStatus());
            opsRo.setWarehouseCode(adjustDto.getWarehouseCode());
            opsRo.setCustomerNo(adjustDto.getCustomerNo());
            opsRoItem.setRoId(opsRo.getRoId());
            opsRoItem.setRoItem(1);
            opsRoItem.setModelno(adjustDto.getModelno());
            opsRoItem.setQty(adjustDto.getQty());
            opsRoItem.setRecQty(adjustDto.getQty());
            // 插入数据
            insertRo(opsRo, opsRoItem, null, userDto);
            OpsWmOrderTask opsWmOrderTask = new OpsWmOrderTask();
            opsWmOrderTask.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
            opsWmOrderTask.setCreator(userDto.getUserName());
            opsWmOrderTask.setCreTime(new Date());
            opsWmOrderTask.setWmOrderId(opsRo.getRoId());
            opsWmOrderTask.setWmOrderType(WmOrderTaskEnum.RO.getType());
            opsWmOrderTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
            taskList.add(opsWmOrderTask);
            //wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTask);
            roList.add(opsRo);
        }
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        return roList;
    }

    @Override
    public List<OpsRoDto> createRoForTrans(InventoryForTransInputDto inputDto) throws OpsException {
        List<OpsRoDto> roList = new ArrayList<>();
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        for (InventoryForTransDto transDto : inputDto.getDtoList()) {
            OpsRoDto opsRoDto = null;
            // 1.校验物流指令是否存在
            List<OpsRo> ros = baseRoService.findRoByOrderNo(transDto.getOrderId(), transDto.getItemNo().toString());
            if (!CollectionUtils.isEmpty(ros)) {
                throw Exceptions.OpsException("调拨入库指令已存在");
            }
            // 初始化ro
            OpsRo opsRo = new OpsRo();
            OpsRoItem opsRoItem = new OpsRoItem();
            opsRo.setOrderId(transDto.getOrderId());
            opsRo.setOrderItem(String.valueOf(transDto.getItemNo()));
            String dbrkId = WmOrderNoFactory.TKRK(transDto.getOrderId(), transDto.getItemNo(), 0, 0);
            // String dbrkId = String.format(OrderIDFormatEnum.DBR_FORMAT.getFormat(), transDto.getOrderId(),String.format("%04d", (transDto.getItemNo())), String.format("%03d", 0), String.format("%03d", 0));
            opsRo.setRoId(dbrkId);
            opsRo.setNum(0);
            opsRo.setAssNum(0);
            opsRo.setRoType(RoTypeEnum.TKRK.getType());
            opsRo.setRoStatus(RoStatusEnum.INIT.getStatus());
            opsRo.setWarehouseCode(transDto.getTwarehouseCode());
            opsRo.setCustomerNo(transDto.getTcustomerNo());
            opsRoItem.setRoId(opsRo.getRoId());
            opsRoItem.setRoItem(1);
            opsRoItem.setModelno(transDto.getTmodelno());
            opsRoItem.setQty(transDto.getTqty());
            opsRoItem.setRecQty(0);
            // 同仓调拨，直接修改库存数量，不下发物流指令，ro_item_inventory设为软删除
            if (transDto.getSwarehouseCode().equals(transDto.getTwarehouseCode())) {
                opsRo.setRoStatus(RoStatusEnum.FINISH.getStatus());
                opsRoItem.setRecQty(opsRoItem.getQty());
                // 查询库存
                Long propertyId = null;
                if (transDto.gettPropertyId() != null) {
                    propertyId = transDto.gettPropertyId();
                } else {
                    OpsInventoryProperty property = new OpsInventoryProperty();
                    property.setInventoryTypeCode(transDto.gettPropertyTypeCode());
                    property.setCustomerNo(transDto.getTcustomerNo());
                    property.setPpl(transDto.getTppl());
                    property.setProjectCode(transDto.getTprojectCode());
                    property.setGroupCustomerNo(transDto.getTgroupCustomerNo());
                    property.setSalesInfoNo(transDto.getSsalesInfoNo());
                    propertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(property,
                            inputDto.getUserDto());
                }
                OpsInventory inv = new OpsInventory();
                inv.setWarehouseCode(transDto.getTwarehouseCode());
                inv.setModelno(transDto.getTmodelno());
                inv.setInventoryPropertyId(propertyId);
                List<OpsInventory> invList = baseInventoryService.findOpsInventory(inv);
                Long invId = null;
                // 如果没有查询到库存，则创建一条库存
                if (invList.size() == 0) {
                    inv.setInventoryStatus(InventoryStatusEnum.NORMAL.getCode());
                    inv.setQuantity(0);
                    inv.setPrepareQuantity(0);
                    invId = baseInventoryService.createInvReturnId(inv, UserDto.AUTO);
                } else {
                    invId = invList.get(0).getInventoryId();
                }
                OpsRoItemInventory opsRoItemInventory = new OpsRoItemInventory();
                opsRoItemInventory.setRoId(opsRoItem.getRoId());
                opsRoItemInventory.setRoItem(opsRoItem.getRoItem());
                opsRoItemInventory.setInventoryId(invId);
                opsRoItemInventory.setQty(opsRoItem.getQty());
                opsRoItemInventory.setRecQty(opsRoItemInventory.getQty());
                opsRo.setDelflag(1);
                opsRoItem.setDelflag(1);
                opsRoItemInventory.setDelflag(1);
                insertRo(opsRo, opsRoItem, Collections.singletonList(opsRoItemInventory),
                        inputDto.getUserDto());
                updateQtyForRo(opsRo, opsRoItemInventory, inputDto.getUserDto());
                opsRoDto = new OpsRoDto(opsRo, opsRoItem,opsRoItemInventory);
            } else {//异仓调拨，不生成ro_item_inventory
                opsRo.setRoStatus(RoStatusEnum.INIT.getStatus());
                opsRoItem.setRecQty(0);
                insertRo(opsRo, opsRoItem, null, inputDto.getUserDto());
                // bug14051 2024-04-24 去掉task生成，在handconfirm中生成
                // OpsWmOrderTask opsWmOrderTask = new OpsWmOrderTask();
                // opsWmOrderTask.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
                // opsWmOrderTask.setCreator(inputDto.getUserDto().getUserName());
                // opsWmOrderTask.setCreTime(new Date());
                // opsWmOrderTask.setWmOrderId(opsRo.getRoId());
                // opsWmOrderTask.setWmOrderType(WmOrderTaskEnum.RO.getType());
                // opsWmOrderTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
                // taskList.add(opsWmOrderTask);
                opsRoDto = new OpsRoDto(opsRo, opsRoItem);
            }

            roList.add(opsRoDto);
        }
        //批量更新task
        // wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        return roList;
    }

    @Override
    public void createRoForTransMove(TransOrderDtoForMove dto, OpsInventoryMove move) throws OpsException {
        OpsInventoryProperty property = opsInventoryPropertyService.findById(move.getInventoryPropertyId());
        List<OpsRo> opsRoList = baseRoService.findRoByOrderNo(dto.getTransNo(), dto.getItemNo().toString());
        if (!CollectionUtils.isEmpty(opsRoList)) {
            throw Exceptions.OpsException("调拨入库指令已存在");
        }
        OpsRo opsRo = new OpsRo();
        OpsRoItem opsRoItem = new OpsRoItem();
        opsRo.setOrderId(dto.getTransNo());
        opsRo.setOrderItem(String.valueOf(dto.getItemNo()));
        opsRo.setNum(0);
        opsRo.setAssNum(0);
        String dbrkId = WmOrderNoFactory.TKRK(dto.getTransNo(), dto.getItemNo(), 0, 0);
        // String dbrkId = String.format(OrderIDFormatEnum.DBR_FORMAT.getFormat(), opsRo.getOrderId(),String.format("%04d", (Integer.valueOf(opsRo.getOrderItem()))), String.format("%03d", opsRo.getNum()), String.format("%03d", opsRo.getAssNum()));
        opsRo.setRoId(dbrkId);
        opsRo.setRoType(RoTypeEnum.TKRK.getType());
        opsRo.setRoStatus(RoStatusEnum.INIT.getStatus());
        opsRo.setWarehouseCode(dto.getToWarehouseCode());
        opsRo.setCustomerNo(property.getCustomerNo());
        opsRo.setRoStatus(RoStatusEnum.INIT.getStatus());
        opsRoItem.setRoId(opsRo.getRoId());
        opsRoItem.setRoItem(1);
        opsRoItem.setModelno(dto.getModelNo());
        opsRoItem.setQty(dto.getHopeQty());
        opsRoItem.setRecQty(0);
        opsRoItem.setRecQty(0);
        UserDto userDto = new UserDto("transOrder");
        // 不生成ro_item_inventory
        insertRo(opsRo, opsRoItem, null, userDto);
    }


    /**
     *  bugid ：12889 c14717 2024-01-12
     * 创建组换单ro bugid ：12889
     */
    @Override
    public void createROForPorduceV2(String orderId, String warehouseCode,
                                     UserDto userDto, Boolean onlyWms)throws OpsException {
        // 判断是否完全收货
        List<OpsDo> doListByOrderNo = baseDoService.findDoListByOrderNo(orderId);
        for(OpsDo opsDo: doListByOrderNo){
            if(!DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())){
                return;
            }
        }
        // 判断ro是否存在
        List<OpsRo> opsRoList = baseRoService.findRoByOrderNo(orderId);
        if(CollectionUtils.isNotEmpty(opsRoList)){
            return;
        }
        // 调用组换申请获取ro基础数据
        ResultVo<List<InventoryForAdjustDto>> resultVo = stockAssemblyFeignApi.getAssemblyInDataForWMS(orderId);
        if(resultVo.isSuccess() && CollectionUtils.isNotEmpty(resultVo.getData())){
            // 创建ro
            createRoForProduceChange(resultVo.getData(),warehouseCode,userDto,onlyWms);
        }
    }

    /**
     * 组换单
     */
    @Override
    public List<OpsRo> createRoForProduceChange(List<InventoryForAdjustDto> rkList, String warehouseCode,
                                                UserDto userDto, Boolean onlyWms) throws OpsException {
        List<OpsRo> roList = new ArrayList<>();
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        //bugid:12737 c14717 2024-01-11 增加invoniceId 2024-02-05 一个发票号对应一个id
        long currentTimestamp = System.currentTimeMillis();
        for (InventoryForAdjustDto adjustDto : rkList) {
            OpsRo opsRo = new OpsRo();
            OpsRoItem opsRoItem = new OpsRoItem();
            opsRo.setOrderId(adjustDto.getOrderId());
            opsRo.setOrderItem(String.valueOf(roList.size() + 1));
            opsRo.setInvoiceNo(adjustDto.getOrderId());
            opsRo.setInvoiceId(currentTimestamp);
            String roId = String.format(OrderIDFormatEnum.RO_FORMAT.getFormat(), opsRo.getOrderId(),
                    String.format("%03d", roList.size() + 1), String.format("%03d", 0), String.format("%03d", 0));
            opsRo.setRoId(roId);
            opsRo.setRoType(RoTypeEnum.ZHRK.getType());
            if (onlyWms) {
                opsRo.setRoType(RoTypeEnum.ZHRKOW.getType());
            }
            opsRo.setRoStatus(RoStatusEnum.INIT.getStatus());
            opsRo.setWarehouseCode(warehouseCode);
            opsRo.setCustomerNo(adjustDto.getCustomerNo());
            opsRoItem.setRoId(opsRo.getRoId());
            opsRoItem.setRoItem(1);
            opsRoItem.setModelno(adjustDto.getModelno());
            opsRoItem.setQty(adjustDto.getQty());
            opsRoItem.setRecQty(0);
            String data = commonServiceFeignApi.generatorBillNo("20").getData();
            // 插入数据
            OpsRoBarcode opsRoBarcode = new OpsRoBarcode();
            opsRoBarcode.setInvoiceid(currentTimestamp);

            opsRoBarcode.setInvoiceno(adjustDto.getOrderId());
            opsRoBarcode.setRoId(opsRo.getRoId());
            opsRoBarcode.setWarehouseCode(opsRo.getWarehouseCode());// 目的仓
            opsRoBarcode.setBarcode(data);
            opsRoBarcode.setPackageCode("");
            opsRoBarcode.setModelno(opsRoItem.getModelno());
            opsRoBarcode.setQty(opsRoItem.getQty());
            opsRoBarcode.setDelflag(0);
            opsRoBarcode.setCreTime(new Date());
            opsRoBarcode.setCreator(UserDto.WMS.getUserName());
            opsRoBarcode.setOrderno(opsRo.getOrderId());
            opsRoBarcode.setItemno(Integer.valueOf(opsRo.getOrderItem()));
            opsRoBarcode.setRoItem(opsRoItem.getRoItem());
            opsRoBarcodeMapper.insertSelective(opsRoBarcode);
            List<OpsRoItemInventory> roItemInventoryList = new ArrayList<>();
            if (!onlyWms) {
                OpsInventoryProperty property = new OpsInventoryProperty();
                // property.setInventoryTypeCode();
                property.setInventoryTypeCode("TY");
                if (!StringUtils.isEmpty(adjustDto.getCustomerNo())) {
                    property.setCustomerNo(adjustDto.getCustomerNo());
                    property.setInventoryTypeCode("GK-TY");
                }
                if (!StringUtils.isEmpty(adjustDto.getPpl())) {
                    property.setPpl(adjustDto.getPpl());
                    property.setInventoryTypeCode("GK-PPL");
                }
                if (!StringUtils.isEmpty(adjustDto.getProjectCode())) {
                    property.setProjectCode(adjustDto.getProjectCode());
                    property.setInventoryTypeCode("ZL-PJ");
                }
                if (!StringUtils.isEmpty(adjustDto.getGroupCustomerNo())) {
                    property.setGroupCustomerNo(adjustDto.getGroupCustomerNo());
                    property.setInventoryTypeCode("ZL-JT");
                }

                Long propertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(property, userDto);
                OpsInventoryMove inventory = new OpsInventoryMove();
                inventory.setInvoiceid(currentTimestamp);

                inventory.setInvoiceno(adjustDto.getOrderId());
                inventory.setOrderno(opsRo.getOrderId());
                inventory.setItemno(Integer.parseInt(opsRo.getOrderItem()));
                inventory.setAssociateNo(opsRo.getOrderId());
                inventory.setAssociateNoItem(Integer.parseInt(opsRo.getOrderItem()));
                inventory.setWarehouseCode(opsRo.getWarehouseCode());
                inventory.setInventoryStatus(InventoryStatusEnum.X.getCode());
                inventory.setSourceType(SourceTypeEnum.ZH.getType());
                inventory.setQuantity(opsRoItem.getQty());
                inventory.setPrepareQuantity(0);
                inventory.setModelno(opsRoItem.getModelno());
                inventory.setSignWarehouseCode(opsRo.getWarehouseCode());
                inventory.setInventoryPropertyId(propertyId);
                inventory.setQaStatus(adjustDto.getQaStatus());
                // 直接下发物流
                Long inventoryMoveId = baseInventoryService.createInvMoveReturnId(inventory, userDto);
                // 生成opsRoItemInventory
                OpsRoItemInventory opsRoItemInventory = initRoItemInventoryForInvoice(roId, 1, inventoryMoveId,
                        opsRoItem.getQty());
                roItemInventoryList.add(opsRoItemInventory);
            }
            insertRo(opsRo, opsRoItem, roItemInventoryList, userDto);
            OpsWmOrderTask opsWmOrderTaskBarcode = new OpsWmOrderTask();
            opsWmOrderTaskBarcode.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
            opsWmOrderTaskBarcode.setCreator(userDto.getUserName());
            opsWmOrderTaskBarcode.setCreTime(new Date());
            opsWmOrderTaskBarcode.setWmOrderId(opsRo.getRoId());
            opsWmOrderTaskBarcode.setWmOrderType(WmOrderTaskEnum.RO.getType());
            opsWmOrderTaskBarcode.setTaskType(WmOrderTaskEnum.BARCODE.getType());
            taskList.add(opsWmOrderTaskBarcode);
            OpsWmOrderTask opsWmOrderTask = new OpsWmOrderTask();
            opsWmOrderTask.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
            opsWmOrderTask.setCreator(userDto.getUserName());
            opsWmOrderTask.setCreTime(new Date());
            opsWmOrderTask.setWmOrderId(opsRo.getRoId());
            opsWmOrderTask.setWmOrderType(WmOrderTaskEnum.RO.getType());
            opsWmOrderTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
            taskList.add(opsWmOrderTask);
            roList.add(opsRo);
        }
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        return roList;
    }


    /**
     * @author ：c02483
     * @date ：Created in 2021/12/30 14:13
     * @description：营业情报，生成情报调账入
     */
    public OpsRo createRoAndFinishForSalesInfo(InventoryForAdjustDto dto, OpsDoDto doDto) throws OpsException {
        // 创建入库单-情报占用
        OpsRo opsRo = new OpsRo();
        opsRo.setRoId("QBR" + dto.getOrderId());
        opsRo.setOrderId(dto.getOrderId());
        opsRo.setOrderItem("1");
        opsRo.setNum(1);
        opsRo.setAssNum(0);
        opsRo.setRoType(RoTypeEnum.QBRK.getType());
        opsRo.setRoStatus(RoStatusEnum.FINISH.getStatus());
        opsRo.setWarehouseCode(dto.getWarehouseCode());
        opsRo.setCustomerNo(dto.getCustomerNo());
        opsRo.setInvoiceNo("");
        opsRo.setDelflag(1);
        OpsRoItem opsRoItem = new OpsRoItem();
        opsRoItem.setRoId(opsRo.getRoId());
        opsRoItem.setRoItem(1);
        opsRoItem.setModelno(dto.getModelno());
        opsRoItem.setQty(dto.getQty());
        opsRoItem.setRecQty(dto.getQty());
        opsRoItem.setDelflag(1);

        // 计算库存总数
        int qty = dto.getQty();
        // 查询或创建批属性
        OpsInventoryProperty tProperty = new OpsInventoryProperty();
        tProperty.setInventoryTypeCode(InventoryTypeEnum.QB.getType());
        tProperty.setSalesInfoNo(dto.getSalesInfoNo());
        tProperty.setExpDate(dto.getExpDate());
        List<OpsInventoryProperty> propertyList = opsInventoryPropertyService.matchByExample(tProperty);
        if (propertyList.size() > 0) {
            throw Exceptions.OpsException("该营业情报号已存在");
        }
        tProperty.setCustomerNo(dto.getCustomerNo());
        Long inventoryPropertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(tProperty,
                new UserDto("SalesInfo", null));
        // 创建库存
        OpsInventory inventory = new OpsInventory();
        inventory.setWarehouseCode(dto.getWarehouseCode());
        inventory.setInventoryStatus(InventoryStatusEnum.NORMAL.getCode());
        inventory.setQuantity(qty);
        inventory.setPrepareQuantity(0);
        inventory.setModelno(dto.getModelno());
        inventory.setQaStatus(QAStatusEnum.NORMAL.getType());
        inventory.setInventoryPropertyId(inventoryPropertyId);
        Long inventoryId = baseInventoryService.createInvReturnId(inventory, UserDto.ADMIN);
        // 库存日志
        opsInventoryLogService.insertOpsInventoryLogForRo(opsRo, dto.getModelno(), inventoryId, qty, UserDto.AUTO);
        OpsRoItemInventory opsRoItemInventory = new OpsRoItemInventory();
        opsRoItemInventory.setRoId(opsRoItem.getRoId());
        opsRoItemInventory.setRoItem(opsRoItem.getRoItem());
        opsRoItemInventory.setInventoryId(inventoryId);
        opsRoItemInventory.setQty(qty);
        opsRoItemInventory.setRecQty(qty);
        opsRoItemInventory.setVersion(0L);
        opsRoItemInventory.setDelflag(1);
        opsRoItemInventory.setCreTime(new Date());
        opsRoItemInventory.setCreator(UserDto.ADMIN.getUserName());
        insertRo(opsRo, opsRoItem, Collections.singletonList(opsRoItemInventory),
                UserDto.ADMIN);
        return opsRo;
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/12/30 14:13
     * @description：营业情报，生成取消情报
     */
    @Override
    public OpsRoDto createRoForCancelSalesInfo(InventoryForAdjustDto adjustDto, OpsDoDto opsDoDto)
            throws OpsException {
        // 收货指令
        OpsRo opsRo = new OpsRo();
        opsRo.setOrderId(adjustDto.getOrderId());
        opsRo.setOrderItem(adjustDto.getOrderItem().toString());
        opsRo.setRoId("QBQX" + adjustDto.getOrderId() + "-" + adjustDto.getOrderItem().toString());
        opsRo.setRoType(RoTypeEnum.QBQX.getType());
        opsRo.setRoSource(RoSourceEnum.SALESINFOUNDO.getType());
        opsRo.setRoStatus(RoStatusEnum.FINISH.getStatus());
        opsRo.setWarehouseCode(adjustDto.getWarehouseCode());
        opsRo.setRemark("情报占用到期取消");
        opsRo.setDelflag(1);//加删除标识，防止入库指令被执行
        // 收货指令行
        OpsRoItem opsRoItem = new OpsRoItem();
        opsRoItem.setRoId(opsRo.getRoId());
        opsRoItem.setRoItem(1);
        opsRoItem.setModelno(adjustDto.getModelno());
        opsRoItem.setFromInventoryId(adjustDto.getInventoryId());
        opsRoItem.setFromInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
        opsRoItem.setQty(adjustDto.getQty());
        opsRoItem.setRecQty(adjustDto.getQty());
        opsRoItem.setDelflag(1);//加删除标识，防止入库指令被执行
        OpsInventory inv = new OpsInventory();
        inv.setWarehouseCode(adjustDto.getWarehouseCode());
        inv.setModelno(adjustDto.getModelno());
        inv.setInventoryPropertyId(1L);
        List<OpsInventory> inventoryList = baseInventoryService.findOpsInventory(inv);
        Long invId = null;
        if (inventoryList.isEmpty()) {
            invId = baseInventoryService.createInvReturnId(inv, UserDto.AUTO);
        } else if (inventoryList.size() > 1) {
            invId = inventoryList.stream().max(Comparator.comparing(OpsInventory::getQuantity)).get().getInventoryId();
        } else {
            invId = inventoryList.get(0).getInventoryId();
        }
        // 收货指令-库存关联表
        OpsRoItemInventory opsRoItemInventory = new OpsRoItemInventory();
        opsRoItemInventory.setRoId(opsRoItem.getRoId());
        opsRoItemInventory.setRoItem(opsRoItem.getRoItem());
        opsRoItemInventory.setInventoryId(invId);
        opsRoItemInventory.setQty(adjustDto.getQty());
        opsRoItemInventory.setRecQty(adjustDto.getQty());
        opsRoItemInventory.setVersion(0L);
        opsRoItemInventory.setDelflag(1);
        opsRoItemInventory.setCreTime(new Date());
        opsRoItemInventory.setCreator(UserDto.AUTO.getUserName());
        // 插入指令表
        insertRo(opsRo, opsRoItem, Collections.singletonList(opsRoItemInventory), UserDto.AUTO);
        OpsRoDto opsRoDto = new OpsRoDto();
        opsRoDto.setOpsRo(opsRo);
        opsRoDto.setOpsRoItem(opsRoItem);
        opsRoDto.setRoItemInventoryList(Collections.singletonList(opsRoItemInventory));
        return opsRoDto;
    }

    @Override
    public void delOpsRoOrder(Long cancelId, OpsRo opsRo, String msg, UserDto user) throws OpsException {
        OpsRo updateRo = new OpsRo();
        updateRo.setId(opsRo.getId());
        updateRo.setDelflag(1);
        updateRo.setRoStatus(DoStatusEnum.CANCEL.getStatus());
        baseRoService.updateRoById(updateRo);

        //删除roItem
        OpsRoItem roItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
        baseRoService.deleteRoItem(roItem.getId(), user);

        opsDoService.insertOrderCancelItem(cancelId, opsRo.getRoId(), opsRo.getRoType(), "取消成功", user);
        List<OpsRoItemInventory> roItemInventories = baseRoService.findRoItemInventoryByRoId(opsRo.getRoId());
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(roItemInventories)) {
            for (OpsRoItemInventory opsRoItemInventory : roItemInventories) {
                // 释放库存 在途表 在库表
                OpsInventoryMove opsInventoryMove = baseInventoryService
                        .getInventoryMoveById(opsRoItemInventory.getInventoryId());
                if(Objects.nonNull(opsInventoryMove)){
                    baseInventoryService.deleteInventoryMove(opsInventoryMove.getInventoryId(), opsInventoryMove.getVersion(),
                            UserDto.WMS);
                }
            }
        }
        // 更新task
        wmOrderTaskService.delWmOrderTask(opsRo.getRoId(), msg);
    }

    /**
     * ops提交wmsRO和ROitem
     */
    @Override
    public OpsRoAddItemDto findRoToWms(String roId) {
        OpsRoAddItemDto opsRoAddItemDto = new OpsRoAddItemDto();
        // add ro to adapter
        OpsRoExample example = new OpsRoExample();
        OpsRoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0);
        criteria.andRoIdEqualTo(roId);
        criteria.andIsWmsEqualTo(0);// 初始值
        List<OpsRo> list = opsRoMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            opsRoAddItemDto.setRo(list.get(0));
        }

        // add roItem to adapter
        OpsRoItemExample exampleItem = new OpsRoItemExample();
        OpsRoItemExample.Criteria criteriaItem = exampleItem.createCriteria();
        criteriaItem.andRoIdEqualTo(roId);
        criteriaItem.andDelflagEqualTo(0);// 删除标
        List<OpsRoItem> listItem = opsRoItemMapper.selectByExample(exampleItem);
        if (!CollectionUtils.isEmpty(listItem)) {
            opsRoAddItemDto.setList(listItem);
        }

        if (CollectionUtils.isEmpty(listItem) || CollectionUtils.isEmpty(list)) {
            return null;
        }
        return opsRoAddItemDto;
    }

    /**
     * 处理虚拟组换单的逻辑
     *
     * @param roList
     * @param allQty
     * @throws OpsException
     */
    private void handleVirtualExchangeRo(List<OpsRo> roList, int allQty) throws OpsException {
        for (OpsRo opsRo : roList) {
            OpsRoItem roItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
            if (roItem.getQty() - roItem.getRecQty() > 0) {
                int qty = Math.min(allQty, roItem.getQty() - roItem.getRecQty());
                baseRoService.updateOpsRoItemRecQty(roItem.getRoId(), roItem.getRecQty() + qty, roItem.getVersion(), "组换入库");
                allQty -= qty;
                RoStatusEnum status = roItem.getRecQty() + qty >= roItem.getQty() ? RoStatusEnum.FINISH : RoStatusEnum.PART;
                baseRoService.updateOpsRoStatus(opsRo.getRoId(), status.getStatus(), opsRo.getVersion(), "组换入库");
            }
        }
    }

    /**
     * 收货扫描处理新方法
     * 【已废弃】
     * 2022-11-15 初始
     * 2023-2-28 用do.use_qty记录在途已收数
     */
    /*
    @Override
    public String wmRoConfirmHandle(WmRoConfirmDto wmRoConfirmDto) throws OpsException {
        if (Objects.isNull(wmRoConfirmDto)) {
            throw Exceptions.OpsException("参数解析失败--wmRoConfirmDto 或 UserDto");
        }
        List<OpsRo> roList = baseRoService.isExchangeOrder(wmRoConfirmDto.getInvoiceNo());
        if (CollectionUtils.isNotEmpty(roList)) {
            int barQty = Integer.valueOf(wmRoConfirmDto.getQty());
            handleVirtualExchangeRo(roList, barQty);
            stockAssemblyFeignApi.updateAssemblyStatus(roList.get(0).getOrderId(), true);
            return "成功";
        }
        String roId = wmRoConfirmDto.getReceiveOrderCode();//ROID入库指令
        String barCode = wmRoConfirmDto.getReceiveCode();// 箱码
        String warehouseCode = wmRoConfirmDto.getWarehouseCode();// 仓库号
        String wmsASNCode = wmRoConfirmDto.getWmsOrderCode();// 富勒入库单号（唯一码）
        String modelNo = wmRoConfirmDto.getModelNo();// 型号
        String scanType = wmRoConfirmDto.getScanType();//类型 1 箱码 2卡板号
        String inventoryType = wmRoConfirmDto.getInventoryType(); //品质 ZP =正品
        String invoiceNo = wmRoConfirmDto.getInvoiceNo();
        Date receiveTime = wmRoConfirmDto.getReceiveTime(); //物流扫描时间
        Long invoiceId = wmRoConfirmDto.getInvoiceId();
        String userName = wmRoConfirmDto.getUsername();
        UserDto userDto = new UserDto();
        userDto.setUserName(userName);
        if (StringUtils.isEmpty(roId)) {
            throw Exceptions.OpsException("ROID不可为空.", RoConfirmErrTypeFlagEnum.PARAM_ERROR.getFlag());
        }
        if (StringUtils.isEmpty(barCode)) {
            throw Exceptions.OpsException("箱码不可为空." + roId, RoConfirmErrTypeFlagEnum.PARAM_ERROR.getFlag());
        }
        if (StringUtils.isEmpty(wmRoConfirmDto.getQty())) {
            throw Exceptions.OpsException("数量不可为空." + roId, RoConfirmErrTypeFlagEnum.PARAM_ERROR.getFlag());
        }
        int qty = Integer.parseInt(wmRoConfirmDto.getQty());
        if (qty < 1) {
            throw Exceptions.OpsException("数量需大于0." + roId, RoConfirmErrTypeFlagEnum.PARAM_ERROR.getFlag());
        }
        //判断回传
        OpsRoPost opsRoPost = opsRoPostService.getRoPostByAsnId(roId, wmsASNCode);
        if (Objects.nonNull(opsRoPost)) {
            throw Exceptions.OpsException("已扫描,无重复扫描." + roId, RoConfirmErrTypeFlagEnum.RO_FINISH_ERROR.getFlag());
        }
        // 1.ops_do表变更状态为收货完成
        OpsRo opsRo = baseRoService.findRoByRoId(roId);
        if (Objects.isNull(opsRo)) {
            throw Exceptions.OpsException("无此收货单据." + roId, RoConfirmErrTypeFlagEnum.NO_RO_ERROR.getFlag());
        }
        if (!opsRo.getInvoiceNo().equalsIgnoreCase(invoiceNo)) {
            throw Exceptions.OpsException("发票号不一致." + roId + ",RO发票号：" + opsRo.getInvoiceNo() + "，请求发票号：" + invoiceNo, RoConfirmErrTypeFlagEnum.WAREHOUSE_ERROR.getFlag());
        }
        if (!opsRo.getWarehouseCode().equalsIgnoreCase(warehouseCode)) {
            throw Exceptions.OpsException("非本仓货物,不可收货扫描." + roId + ",应由：" + opsRo.getWarehouseCode() + "收货", RoConfirmErrTypeFlagEnum.WAREHOUSE_ERROR.getFlag());
        }
        //无签收后标记表示还没发票签收
        if (Objects.isNull(opsRo.getIsSign()) || !opsRo.getIsSign()) {
            throw Exceptions.OpsException("无法收货，仓库未签收发票.发票号：" + opsRo.getInvoiceNo(), RoConfirmErrTypeFlagEnum.NO_SIGN_ERROR.getFlag());
        }
        OpsRoItem opsRoItem = baseRoService.findRoItemByRoId(roId);
        if (Objects.isNull(opsRoItem)) {
            throw Exceptions.OpsException("无此收货单." + roId, RoConfirmErrTypeFlagEnum.NO_RO_ERROR.getFlag());
        }
        //超额收货判断
        if (opsRoItem.getRecQty() + qty > opsRoItem.getQty()) {
            throw Exceptions.OpsException("入库单." + roId + "，不可超额收货。应收：" + opsRoItem.getQty() + ",已收：" + opsRoItem.getRecQty() + ",本次：" + qty, RoConfirmErrTypeFlagEnum.EXCESS_ERROR.getFlag());
        }
        //型号判断
        if (!modelNo.equals(opsRoItem.getModelno())) {
            throw Exceptions.OpsException("型号错误.应收型号：" + opsRoItem.getModelno() + "，收货型号：" + modelNo + ".ROID：" + roId, RoConfirmErrTypeFlagEnum.PARAM_ERROR.getFlag());
        }
        //按箱码传值
        if ("1".equals(scanType)) {
            // 判断OpsRoBarcode
            OpsRoBarcode opsRoBarcode = opsRoBarcodeService.getRoBarcodeByBarcode(roId, barCode);
            // RO箱码barcode
            if (Objects.isNull(opsRoBarcode)) {
                throw Exceptions.OpsException("无此收货箱码:" + roId + ",箱码：" + barCode, RoConfirmErrTypeFlagEnum.NO_BARCODE_ERROR.getFlag());
            }
            if (!opsRoBarcode.getQty().equals(qty)) {
                throw Exceptions.OpsException("箱码数量与请求数量不一致。" + roId + ",箱码：" + barCode, RoConfirmErrTypeFlagEnum.BARCODE_QTY_UNEQUAL_ERROR.getFlag());
            }
        }
        invoiceNo = opsRo.getInvoiceNo();
        // 存入对账表
        opsRoPost = new OpsRoPost();
        opsRoPost.setMsgId(wmsASNCode);
        opsRoPost.setRoId(roId);
        opsRoPost.setWarehouseCode(warehouseCode);
        opsRoPost.setReceiveCode(barCode);
        opsRoPost.setQty(qty);
        opsRoPost.setInventoryType(inventoryType);
        opsRoPost.setScanType(scanType);
        opsRoPost.setCreator(userName);
        opsRoPost.setModelno(modelNo);
        opsRoPost.setInvoiceno(invoiceNo);
        opsRoPost.setInvoiceid(invoiceId);
        opsRoPost.setUsername(userName);
        opsRoPost.setReceivetime(receiveTime);
        opsRoPostService.addOpsRoPost(opsRoPost);
        int result = 0;
        // 2.变更ops_ro_item中的收货数量
        result = baseRoService.updateOpsRoItemRecQty(roId, opsRoItem.getRecQty() + qty, opsRoItem.getVersion(), userName);
        if (result < 1) {
            throw Exceptions.OpsException("更新RoItem收货数量错误。入库指令：" + roId, RoConfirmErrTypeFlagEnum.OTHER_ERROR.getFlag());
        }
        // 3.变更ops_ro 的收货状态为已收货
        result = baseRoService.updateOpsRoStatus(roId, (opsRoItem.getRecQty() + qty) >= opsRoItem.getQty() ? RoStatusEnum.FINISH.getStatus() : RoStatusEnum.PART.getStatus(), opsRo.getVersion(), userName);
        if (result < 1) {
            throw Exceptions.OpsException("更新Ro状态错误。入库指令：" + roId, RoConfirmErrTypeFlagEnum.OTHER_ERROR.getFlag());
        }


        //ROID关联在途
        List<OpsRoItemInventory> roItemInventories = baseRoService.findRoItemInventoryByRoId(opsRo.getRoId());
        if (CollectionUtils.isEmpty(roItemInventories)) {
            throw Exceptions.OpsException("无需分配库存ID。入库指令：" + roId, RoConfirmErrTypeFlagEnum.NO_RO_ERROR.getFlag());
        }
        //可分配数
        int allcBarcodeQty = qty;
        // RO与在途关联关系表会存在N个在途N个ROID预约 （原则上，一个OpsRoItemInventory 对应一个 InventoryMove）
        for (OpsRoItemInventory opsRoItemInventory : roItemInventories) {
            //被分配完跳出
            if (allcBarcodeQty < 1) {
                break;
            }
            OpsInventoryMove opsInventoryMove = baseInventoryService.getInventoryMoveById(opsRoItemInventory.getInventoryId());
            if (null == opsInventoryMove) {
                throw Exceptions.OpsException("无在途数据OpsInventoryMove,InventoryId:" + opsRoItemInventory.getInventoryId() + "。入库指令：" + roId, RoConfirmErrTypeFlagEnum.NO_MOVE_ERROR.getFlag());
            }
            if (opsInventoryMove.getPrepareQuantity() > opsInventoryMove.getQuantity()) {
                throw Exceptions.OpsException("在途数据OpsInventoryMove预约数不可大于在途数,InventoryId:" + opsRoItemInventory.getInventoryId() + "。预约数：" + opsInventoryMove.getPrepareQuantity() + "，在途数：" + opsInventoryMove.getQuantity() + "。入库指令：" + roId, RoConfirmErrTypeFlagEnum.PRE_QTY_EXCESS_ERROR.getFlag());
            }
            //在途数=0不分配
            if (opsInventoryMove.getQuantity() < 1) {
                continue;
            }
            // 在途数量、预约数量
            int moveQty = 0;
            int prepareQty = 0;
            //可分配数>=剩余数量
            if (allcBarcodeQty >= opsInventoryMove.getQuantity()) {
                moveQty = opsInventoryMove.getQuantity();
            } else {
                moveQty = allcBarcodeQty;
            }
            //可分配数够数分配在途ID 可以全额转移
            if (opsInventoryMove.getPrepareQuantity() > 0) {
                //qty>=预约数 取值
                if (allcBarcodeQty >= opsInventoryMove.getPrepareQuantity()) {
                    prepareQty = opsInventoryMove.getPrepareQuantity();
                } else {//数量qty<预占数 取qty
                    prepareQty = allcBarcodeQty;
                }
            }
            // 收齐把在途标记删除(分配数大于等于剩余在途数)
            if (allcBarcodeQty >= opsInventoryMove.getQuantity()) {
                //去除在途数据
                baseInventoryService.deleteInventoryMove(opsInventoryMove.getInventoryId(), opsInventoryMove.getVersion(), userDto);
            } else {
                //减去在途库存数
                baseInventoryService.subQtyInventoryForPre(opsInventoryMove.getInventoryId(), moveQty, prepareQty, InventoryTableTypeEnum.TRANS.getType(), userDto);
            }
            //增加在库表库存数
            Long inventoryId = 0L;
            OpsInventory opsInventory = new OpsInventory();
            opsInventory.setWarehouseCode(opsInventoryMove.getSignWarehouseCode());// 签收仓加库存数
            opsInventory.setModelno(opsInventoryMove.getModelno());
            opsInventory.setInventoryPropertyId(opsInventoryMove.getInventoryPropertyId());
            List<OpsInventory> opsInventoryList = baseInventoryService.findOpsInventory(opsInventory);
            if (CollectionUtils.isEmpty(opsInventoryList)) {
                // 新增行
                OpsInventory sourceInventory = new OpsInventory();
                sourceInventory.setWarehouseCode(opsInventoryMove.getSignWarehouseCode());// 签收仓加库存数
                sourceInventory.setInventoryStatus(InventoryStatusEnum.NORMAL.getCode());// 正常在库
                sourceInventory.setQuantity(moveQty);//当前
                sourceInventory.setPrepareQuantity(prepareQty);
                sourceInventory.setUnit(opsInventoryMove.getUnit());
                sourceInventory.setQaStatus(QAStatusEnum.NORMAL.getType());
                sourceInventory.setModelno(opsInventoryMove.getModelno());
                sourceInventory.setInventoryPropertyId(opsInventoryMove.getInventoryPropertyId());
                sourceInventory.setPrice(opsInventoryMove.getPrice());
                sourceInventory.setInTime(new Date());
                inventoryId = baseInventoryService.createInvReturnId(sourceInventory, userDto);
            } else {
                inventoryId = opsInventoryList.get(0).getInventoryId();
                //增加库存数
                baseInventoryService.UpdateQtyInventoryWithPreQty(inventoryId, moveQty, prepareQty, InventoryTableTypeEnum.NORMAL.getType(), userDto);
            }
            //剩余未分配数量（DO&PCO分配）
            int allcLeftQty = qty;
            // 记录日志
            opsInventoryLogService.insertOpsInventoryLogForRo(opsRo, modelNo, inventoryId, moveQty, userDto);
            // do关联关系表，where 在途ID & T行在途
            List<OpsDoItemInventory> opsDoItemInventories = baseDoService.getDoItemInventoryByInventoryId(opsInventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
            for (OpsDoItemInventory transDoItemInventory : opsDoItemInventories) {
                //可分配数量<1表示分配完毕
                if (allcLeftQty < 1) {
                    break;
                }
                OpsDo opsDo = baseDoService.findDoByDoId(transDoItemInventory.getDoId());
                if (null == opsDo) {
                    throw Exceptions.OpsException("无出库指令DOID:" + transDoItemInventory.getDoId() + "数据。", RoConfirmErrTypeFlagEnum.NO_DO_ERROR.getFlag());
                }
                OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(transDoItemInventory.getDoId());
                if (null == opsDoItem) {
                    throw Exceptions.OpsException("无出库指令opsDoItem数据， DOID:" + transDoItemInventory.getDoId(), RoConfirmErrTypeFlagEnum.NO_DO_ERROR.getFlag());
                }
                //do在途行可分配数量
                int allcTranQty = 0;
                //表示已经分配完，不需继续分配这个DOid
                if (transDoItemInventory.getUseQty() < 1) {
                    continue;
                }
                //T剩余分配数大于等于 可分配数，取分配数
                if (transDoItemInventory.getUseQty() >= allcLeftQty) {
                    //分配数=需出库数-已出库数
                    allcTranQty = allcLeftQty;
                } else {
                    //T剩余可分配小于当前可分配数，取 getUseQty
                    allcTranQty = transDoItemInventory.getUseQty();
                }
                //扣减在途数据 useqty 递减  outqty不变
                opsDoService.updateOpsDoItemInventory(transDoItemInventory.getId(), transDoItemInventory.getVersion(), transDoItemInventory.getUseQty() - allcTranQty
                        , transDoItemInventory.getInventoryId(), InventoryTableTypeEnum.TRANS, userDto);
                //找N行,where 来源转过来的在途ID
                OpsDoItemInventory srcOpsDoItemInventory = opsDoService.getOpsDoItemInventoryBySrcInventoryId(transDoItemInventory.getDoId(), transDoItemInventory.getInventoryId(), InventoryTableTypeEnum.NORMAL);
                if (Objects.isNull(srcOpsDoItemInventory)) {
                    OpsDoItemInventory doInventory = new OpsDoItemInventory();
                    doInventory.setDoId(opsDoItem.getDoId());
                    doInventory.setDoItem(opsDoItem.getDoItem());
                    doInventory.setInventoryId(inventoryId);
                    doInventory.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
                    doInventory.setSrcInventoryId(transDoItemInventory.getInventoryId());
                    doInventory.setSrcInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
                    doInventory.setUseQty(allcTranQty);//当前分配的数量
                    doInventory.setOutQty(0);
                    doInventory.setSortnum(1);
                    doInventory.setModifier(userName);
                    doInventory.setModifyTime(new Date());
                    opsDoService.insertDoItemInventory(doInventory);
                } else {
                    //存在N行更改DO在预占数
                    opsDoService.updateOpsDoItemInventory(srcOpsDoItemInventory.getId(), srcOpsDoItemInventory.getVersion()
                            , srcOpsDoItemInventory.getUseQty() + allcTranQty, srcOpsDoItemInventory.getInventoryId(), InventoryTableTypeEnum.NORMAL, userDto);
                }
                //当前在途行收齐改OK
                if (transDoItemInventory.getUseQty() - allcTranQty < 1) {
                    //分配完成，T改删除标记，
                    opsDoService.deleteDoItemInventoryByPrimaryKeySelective(transDoItemInventory.getId(), userDto);
                    //判断DOID有没有T，没就下发(货齐)
                    List<OpsDoItemInventory> opsDoItemInventoryList = baseDoService.getDoItemInventoryByDoId(transDoItemInventory.getDoId(), InventoryTableTypeEnum.TRANS);
                    if (CollectionUtils.isEmpty(opsDoItemInventoryList)) {
                        //do.wait改OK & task。4改3
                        // doWaitType改OK (这版暂不改 2023-02-24)
                        opsDoService.updateDoWaitTypeForDoId(opsDo.getDoId(), "OK", opsDo.getVersion(), userName);
                        Integer taskFlag;
                        try {
                            taskFlag = wmOrderTaskService.getTaskFlag(opsDo.getDoId());
                        } catch (OpsException e) {
                            //10274 记录异常信息到异常表，需要写异常类型 2023-04-04 C12961
                            throw Exceptions.OpsException("task重复:" + opsDo.getDoId(), RoConfirmErrTypeFlagEnum.TASK_DUPLICATION.getFlag());
                        }
                        if (taskFlag != null && taskFlag == 4) {
                            // 4->3
                            wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(opsDo.getDoId(), "入库扫描", 3);
                        } else if (taskFlag == null) {
                            throw Exceptions.OpsException("没有查询到task:" + opsDo.getDoId());
                        }
                    }
                }
                //写日志
                StatusParamDto statusParamDto = new StatusParamDto();
                statusParamDto.setInvoiceNo(opsRo.getInvoiceNo());
                //begin bug:9428 按实收货数量传值 b28029 2023-01-31
                statusParamDto.setQuantity(allcTranQty);//当前分配的数量
                //end bug:9428
                statusParamDto.setWarehouseCode(opsRo.getWarehouseCode());
                // todo 如果为采购入库，需提供关联单号
                if ("0".equals(opsInventoryMove.getSourceType())) {
                    statusParamDto.setPoNo(opsInventoryMove.getAssociateNo());
                    statusParamDto.setPoItem(opsInventoryMove.getAssociateNoItem());
                    statusParamDto.setPoSplitNo(opsInventoryMove.getAssociateNoSplitno());
                }
                orderEventService.customerOrderEventGenerator(CustomerOrderEventEnum.WM_INSTORAGE, opsDo.getOrderId(),
                        opsDo.getOrderItem(), opsDo.getNum(), statusParamDto);
                allcLeftQty -= allcTranQty;
            }
            // pco关联关系表，where 在途ID & T行在途
            List<OpsPcoItemInventory> pcoItemInventories = opsPcoService.getOpsPcoItemInventoryByInventoryId(opsInventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
            for (OpsPcoItemInventory pcoTransItemInventory : pcoItemInventories) {
                if (allcLeftQty < 1) {
                    break;
                }
                //当前行在途行useqty=0表示已被分配完或转移完毕
                if (pcoTransItemInventory.getUseQty() < 1) {
                    continue;
                }
                //pcoo在途行可分配数量
                int allcTranQty = 0;
                if (pcoTransItemInventory.getUseQty() >= allcLeftQty) {
                    //分配数=需出库数-已出库数
                    allcTranQty = allcLeftQty;
                } else {
                    //可分配数=剩余分配数
                    allcTranQty = pcoTransItemInventory.getUseQty();
                }
                //写日志
                OpsPco opsPco = opsPcoService.getPcoByPcoId(pcoTransItemInventory.getPcoId());
                if (null == opsPco) {
                    throw Exceptions.OpsException("无出库指令PCOID:" + pcoTransItemInventory.getPcoId() + "数据。", RoConfirmErrTypeFlagEnum.NO_DO_ERROR.getFlag());
                }
                OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoId(pcoTransItemInventory.getPcoId(), pcoTransItemInventory.getPcoItem());
                if (null == opsPcoItem) {
                    throw Exceptions.OpsException("无出OpsPcoItem数据。PCOID:" + pcoTransItemInventory.getPcoId(), RoConfirmErrTypeFlagEnum.NO_DO_ERROR.getFlag());
                }
                String pcoId = opsPco.getPcoId();
                int pcoItem = opsPcoItem.getPcoItem();
                //扣减在途数据 useqty 递减  outqty不变
                opsPcoService.updatetOpsPcoItemInventory(pcoTransItemInventory.getId(), pcoTransItemInventory.getVersion(), pcoTransItemInventory.getUseQty() - allcTranQty
                        , pcoTransItemInventory.getInventoryId(), InventoryTableTypeEnum.TRANS, UserDto.WMS);
                //找PCO关联关系在库数据，存在找原来来源的ID，不存在新写入PCO关联关系表
                OpsPcoItemInventory pcoItemInventory = opsPcoService.getOpsPcoItemInventoryBySrcInventoryId(pcoId, pcoItem
                        , pcoTransItemInventory.getInventoryId(), InventoryTableTypeEnum.NORMAL);
                //写入行
                if (Objects.isNull(pcoItemInventory)) {
                    OpsPcoItemInventory pcoInventory = new OpsPcoItemInventory();
                    pcoInventory.setPcoId(pcoId);
                    pcoInventory.setPcoItem(pcoItem);
                    pcoInventory.setPcoType(1);
                    pcoInventory.setInventoryId(inventoryId);
                    pcoInventory.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
                    pcoInventory.setSrcInventoryId(pcoTransItemInventory.getInventoryId());
                    pcoInventory.setSrcInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
                    pcoInventory.setUseQty(allcTranQty);//当前分配的数量
                    pcoInventory.setOutQty(0);
                    pcoInventory.setSortnum(1);
                    pcoInventory.setModifier(userName);
                    pcoInventory.setModifyTime(new Date());
                    opsPcoService.insertPcoItemInventory(pcoInventory);
                } else {
                    //更改N行，增加useqty
                    opsPcoService.updatetOpsPcoItemInventory(pcoItemInventory.getId(), pcoItemInventory.getVersion()
                            , pcoItemInventory.getUseQty() + allcTranQty, pcoItemInventory.getInventoryId(), InventoryTableTypeEnum.NORMAL, userDto);
                }
                //当前在途行收齐改OK
                if (pcoTransItemInventory.getUseQty() - allcTranQty < 1) {
                    //分配完成，T改删除标记，
                    opsPcoService.deletePcoItemInventoryByPrimaryKeySelective(pcoTransItemInventory.getId(), userDto);
                    List<OpsPcoItemInventory> opsPcoItemInventoryList = opsPcoService.findPcoItemInventoryByPcoIdAndItemAndTableType(pcoId, pcoItem
                            , InventoryTableTypeEnum.TRANS);
                    if (CollectionUtils.isEmpty(opsPcoItemInventoryList)) {
                        // doWaitType改OK
                        opsPcoService.updatePcoItemWaitTypeForItem(pcoId, pcoItem, "OK", userName);
                        Integer taskFlag;
                        try {
                            taskFlag = wmOrderTaskService.getTaskFlag(pcoId);
                        } catch (OpsException e) {
                            //10274 记录异常信息到异常表，需要写异常类型 2023-04-04 C12961
                            throw Exceptions.OpsException("task重复:" + pcoId, RoConfirmErrTypeFlagEnum.TASK_DUPLICATION.getFlag());
                        }
                        if (taskFlag != null && taskFlag == 4) {
                            // 4->3
                            wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(pcoId, "入库扫描", 3);
                        } else if (taskFlag == null) {
                            throw Exceptions.OpsException("没有查询到task:" + pcoId);
                        }
                    }
                }
                StatusParamDto statusParamDto = new StatusParamDto();
                statusParamDto.setInvoiceNo(opsRo.getInvoiceNo());
                //begin bug:9428 按实收货数量传值 b28029 2023-01-31
                statusParamDto.setQuantity(allcTranQty);//当前分配的数量
                //end bug:9428
                statusParamDto.setWarehouseCode(opsRo.getWarehouseCode());
                // todo 如果为采购入库，需提供关联单号
                if ("0".equals(opsInventoryMove.getSourceType())) {
                    statusParamDto.setPoNo(opsInventoryMove.getAssociateNo());
                    statusParamDto.setPoItem(opsInventoryMove.getAssociateNoItem());
                    statusParamDto.setPoSplitNo(opsInventoryMove.getAssociateNoSplitno());
                }
                //pco order_id,order_item,pcoItemInventory.PcoItem
                orderEventService.customerOrderEventGenerator(CustomerOrderEventEnum.WM_INSTORAGE, opsPco.getOrderId(),
                        String.valueOf(opsPco.getOrderItem()), opsPcoItem.getPcoItem(), statusParamDto);
                //剩余可分配数
                allcLeftQty -= allcTranQty;
            }
            //减去在途
            allcBarcodeQty -= moveQty;
            // 类型为采购，处理请购表
            if (SourceTypeEnum.PURCHASE.getType().equals(opsInventoryMove.getSourceType())) {
                //请购单号不可为空，为空为多到数量
                if (Objects.nonNull(opsInventoryMove.getOrderno())) {
                    // 请购表数据记录已收数
                    OpsRequestpurchaseExample opsRequestpurchaseExample = new OpsRequestpurchaseExample();
                    OpsRequestpurchaseExample.Criteria opsRequestpurchaseExampleCriteria = opsRequestpurchaseExample.createCriteria();
                    opsRequestpurchaseExampleCriteria.andOrdernoEqualTo(opsInventoryMove.getOrderno());
                    opsRequestpurchaseExampleCriteria.andItemnoEqualTo(opsInventoryMove.getItemno());
                    if (null != opsInventoryMove.getSplititemno() && opsInventoryMove.getSplititemno() > 0) {
                        opsRequestpurchaseExampleCriteria.andSplititemnoEqualTo(opsInventoryMove.getSplititemno());
                    }
                    opsRequestpurchaseExampleCriteria.andModelnoEqualTo(opsInventoryMove.getModelno());
                    List<OpsRequestpurchase> opsRequestpurchases = opsRequestpurchaseMapper.selectByExample(opsRequestpurchaseExample);
                    if (!CollectionUtils.isEmpty(opsRequestpurchases)) {
                        //请购表只有一行数据
                        OpsRequestpurchase opsRequestpurchase = opsRequestpurchases.get(0);
                        // 3.更变请购表入库数量字段ops_requestPurchase.qtyImport
                        OpsRequestpurchase updateRequestChase = new OpsRequestpurchase();
                        updateRequestChase.setQtyimport(opsRequestpurchase.getQtyimport() + moveQty);// 已收货数量 
                        updateRequestChase.setUpdatetime(new Date());//操作时间
                        OpsRequestpurchaseExample requestpurchaseExample = new OpsRequestpurchaseExample();
                        OpsRequestpurchaseExample.Criteria requestpurchaseExampleCriteria = requestpurchaseExample
                                .createCriteria();
                        requestpurchaseExampleCriteria.andOrdernoEqualTo(opsInventoryMove.getOrderno());// 订单号
                        requestpurchaseExampleCriteria.andItemnoEqualTo(opsInventoryMove.getItemno()); // 子项
                        if (null != opsInventoryMove.getSplititemno() && opsInventoryMove.getSplititemno() > 0) {
                            opsRequestpurchaseExampleCriteria.andSplititemnoEqualTo(opsInventoryMove.getSplititemno());
                        }
                        requestpurchaseExampleCriteria.andModelnoEqualTo(opsInventoryMove.getModelno()); // 2022-09-13 B91717 加入型号的筛选条件
                        int purqty = opsRequestpurchase.getQtyimport() + moveQty;
                        if (purqty >= opsRequestpurchase.getQuantity()) {
                            // 请购收齐入库标记
                            updateRequestChase.setStatecode(RequestPurchaseStatusEnum.YIWANCHENG);
                            updateRequestChase.setFinishtime(new Date());
                            OrderStateService.sendOrderStateForFinishRequestPurchase(opsRequestpurchase);
                        }
                        opsRequestpurchaseMapper.updateByExampleSelective(updateRequestChase, requestpurchaseExample);
                    }
                }
            }
        }
        // 4.采购表 ops_requestPurchase.qtyImport
        if (RoTypeEnum.CGRKBK.getType().equals(opsRo.getRoType())) {
            OpsPurchaseorderExample opsPurchaseorderExample = new OpsPurchaseorderExample();
            OpsPurchaseorderExample.Criteria opsPurchaseorderExampleCriteria = opsPurchaseorderExample
                    .createCriteria();
            opsPurchaseorderExampleCriteria.andOrdernoEqualTo(opsRo.getOrderId());
            opsPurchaseorderExampleCriteria.andItemnoEqualTo(Integer.valueOf(opsRo.getOrderItem()));
            opsPurchaseorderExampleCriteria.andModelnoEqualTo(opsRoItem.getModelno());
            List<OpsPurchaseorder> opsPurchaseorders = opsPurchaseorderMapper
                    .selectByExample(opsPurchaseorderExample);
            if (!CollectionUtils.isEmpty(opsPurchaseorders)) {
                OpsPurchaseorder opsPurchaseorder = opsPurchaseorders.get(0);
                OpsPurchaseorder updatePurchaseorder = new OpsPurchaseorder();
                updatePurchaseorder.setQtyreceive(opsPurchaseorder.getQtyreceive() + qty);// 已收货数量
                updatePurchaseorder.setUpdatetime(new Date());
                OpsPurchaseorderExample purchaseorderExample = new OpsPurchaseorderExample();
                OpsPurchaseorderExample.Criteria purchaseorderExampleCriteria = purchaseorderExample.createCriteria();
                purchaseorderExampleCriteria.andOrdernoEqualTo(opsRo.getOrderId());// 订单号
                purchaseorderExampleCriteria.andItemnoEqualTo(Integer.valueOf(opsRo.getOrderItem())); // 子项
                //AssociateNoSplitno 默认写入在途表null 将存储0（防止又改NULL存储） 大于0才去匹配拆分项
                if (Objects.nonNull(opsRo.getNum()) && opsRo.getNum() > 0) {
                    purchaseorderExampleCriteria.andSplititemnoEqualTo(opsRo.getNum()); //拆分项
                }
                int purqty = opsPurchaseorder.getQtyreceive() + qty;
                if (purqty >= opsPurchaseorder.getQuantity()) {
                    // 请购收齐入库标记
                    updatePurchaseorder.setStatecode(PurchaseOrderStatusEnum.YIWANCHENG);
                    updatePurchaseorder.setFinishdate(new Date());
                    // 2022-10-25 B91717,采购收齐入库同步CTC判断，如果供应商是制造公司，写入CTC中间表，由定时任务每天定时抽取 bug8426
                    List<String> suppilyList = new ArrayList<>();
                    suppilyList.add("CN");
                    suppilyList.add("CM");
                    suppilyList.add("YZ");
                    suppilyList.add("CT");
                    // suppilyList.add("TZ");
                    Set<String> stringSet = new HashSet<String>(suppilyList);
                    if (stringSet.contains(opsPurchaseorder.getSupplierid())) {
                        // 订单完成时，更新ctc的完成状态，调用feign接口
                        // 2022-10-25 B91717,采购完成同步CTC判断，如果供应商是制造公司，写入CTC中间表，由定时任务每天定时抽取更新状态 bug8426
                        purchaseCtcFinishService.insertMid(opsPurchaseorder.getOrderno(), opsPurchaseorder.getItemno(), opsPurchaseorder.getSplititemno());
                    }
                    OrderStateService.sendOrderStateForFinishPurchase(opsPurchaseorder);
                }
                opsPurchaseorderMapper.updateByExampleSelective(updatePurchaseorder, purchaseorderExample);
            }
        }
        //有剩余数未分配，数据异常不可收货
        if (allcBarcodeQty > 0) {
            throw Exceptions.OpsException("箱码：" + barCode + "，数量：" + qty + ",剩余数量：" + allcBarcodeQty + "无法分配。", RoConfirmErrTypeFlagEnum.OTHER_ERROR.getFlag());
        }
        // 写入主动调拨事件
        if (RoTypeEnum.TKRK.getType().equals(opsRo.getRoType())) {
            // 调库订单
            orderEventService.transferOrderEventGenerator(TransferOrderEventEnum.TRANS_FINISHED, opsRo.getOrderId(), opsRo.getOrderItem(), opsRo);
        }
        // 组换成功确认回传
        if (RoTypeEnum.ZHRK.getType().equals(opsRo.getRoType())) {
            stockAssemblyFeignApi.updateAssemblyStatus(opsRo.getOrderId(), true);
        }
        // 完全收货，更新ro-barcode扫描时间
        if (qty + opsRoItem.getRecQty() >= opsRoItem.getQty()) {
            opsRoBarcodeService.updateOpsRoBarcodeForRoConfirm(roId, userName);
        }
        // 操作opsLogWmsDispatchMapper表，记录日志;
        opsLogWmsDispatchService.insertOpsLogWmsDispatchForRoConfirm(roId, userName);
        String caseNo = "2".equals(scanType) ? StrUtil.removePrefix(barCode, invoiceNo) : null;
        opsImpdataService.updateImpdataForRoConfirm(invoiceNo, opsRo,scanType, barCode, caseNo);
        return "成功";
    }
    */

}

