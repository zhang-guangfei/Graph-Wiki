package com.sales.ops.serviceimpl.inventory;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.InventoryReportMapper;
import com.sales.ops.db.dao.OpsInventoryMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsInventoryDao;
import com.sales.ops.dto.inventory.InventoryDTO;
import com.sales.ops.dto.inventory.InventoryForTransDto;
import com.sales.ops.dto.inventory.InventoryReportExcel;
import com.sales.ops.dto.order.OpsWTInventoryDTO;
import com.sales.ops.dto.order.TransOrderQueryMoveParam;
import com.sales.ops.dto.order.TransOrderQueryMoveResult;
import com.sales.ops.dto.query.InventoryQO;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.inventory.OpsInventoryService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.OpsPcoService;
import com.sales.ops.serviceimpl.inventory.factory.PropertyFactory;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author C02483
 * @version 1.0
 * @description: 库存服务：出库算法、库存操作
 * @date 2021/9/10 15:52
 */
@Slf4j
@Service
public class OpsInventoryServiceImpl implements OpsInventoryService {

    @Resource
    private OpsInventoryMapper opsInventoryMapper;
    @Resource
    private OpsInventoryPropertyService opsInventoryPropertyService;
    @Autowired
    private OpsWarehouseService opsWarehouseService;
    @Resource
    private InventoryReportMapper inventoryReportMapper;
    @Resource
    private BaseInventoryService baseInventoryService;
    @Resource
    private BaseDoService baseDoService;
    @Resource
    private OpsPcoService opsPcoService;
    @Resource
    private OpsInventoryDao opsInventoryDao;
    @Autowired
    private BasePoService basePoService;


    @Override
    public Long getInventoryIdForTransOrder(String warehouse, String modelno, String inventoryTypeCode, String customerNo, String ppl, String projectCode, String groupCustomer, String salesInfo, UserDto userDto) throws OpsException {
        OpsInventoryProperty property = PropertyFactory.createCondition(inventoryTypeCode, customerNo, ppl, projectCode, groupCustomer, salesInfo);
        Long propertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(property, userDto);
        OpsInventory condition = new OpsInventory();
        condition.setWarehouseCode(warehouse);
        condition.setModelno(modelno);
        condition.setInventoryPropertyId(propertyId);
        List<OpsInventory> invList = baseInventoryService.findOpsInventory(condition);
        Long inventoryIdForIn = null;
        // 如果没有查询到库存，则创建一条0数量的库存
        if (invList.size() == 0) {
            condition.setInventoryStatus(InventoryStatusEnum.NORMAL.getCode());
            condition.setQuantity(0);
            condition.setPrepareQuantity(0);
            inventoryIdForIn = baseInventoryService.createInvReturnId(condition, UserDto.AUTO);
        } else {
            inventoryIdForIn = invList.get(0).getInventoryId();
        }
        return inventoryIdForIn;
    }

    /**
     * 根据transOrder提供的查询条件，查询可用的在库库存
     *
     * @param transDto
     * @return
     * @throws OpsException
     */
    @Override
    public Map<OpsInventory, Integer> findInventoryListByTransOrderInfo(InventoryForTransDto transDto) throws OpsException {
        Integer quantity = transDto.getSqty();
        Map<OpsInventory, Integer> invqtyMap = new HashMap<>();
        List<OpsInventory> invList;
        OpsInventory inv = new OpsInventory();
        inv.setWarehouseCode(transDto.getSwarehouseCode());
        inv.setModelno(transDto.getSmodelno());
        if (transDto.getsPropertyId() != null) {
            inv.setInventoryPropertyId(transDto.getsPropertyId());
            invList = baseInventoryService.findOpsInventory(inv);
        } else {
            OpsInventoryProperty property = new OpsInventoryProperty();
            property.setInventoryTypeCode(transDto.getsPropertyTypeCode());
            property.setCustomerNo(transDto.getScustomerNo());
            property.setPpl(transDto.getSppl());
            property.setProjectCode(transDto.getSprojectCode());
            property.setGroupCustomerNo(transDto.getSgroupCustomerNo());
            property.setSalesInfoNo(transDto.getSsalesInfoNo());
            invList = baseInventoryService.findOpsInventory(inv, property);
        }
        invList.sort(Comparator.comparing(OpsInventory::getQuantity));
        String modelno = "";
        for (OpsInventory opsInventory : invList) {
            modelno = opsInventory.getModelno();
            int hasqty = opsInventory.getQuantity() - opsInventory.getPrepareQuantity();
            if (hasqty > 0) {
                if (hasqty < quantity) {
                    invqtyMap.put(opsInventory, hasqty);
                    quantity -= hasqty;
                } else {
                    invqtyMap.put(opsInventory, quantity);
                    quantity = 0;
                    break;
                }
            }
        }
        if (quantity > 0) {
            throw Exceptions.OpsException("型号为" + modelno + "的可用数量不足");
        }
        return invqtyMap;
    }

    @Override
    public List<TransOrderQueryMoveResult> findAvailableMoveForTransOrder(TransOrderQueryMoveParam dto) throws OpsException {
        List<OpsInventoryMove> moves = baseInventoryService.findOpsInventoryMoveForTransOrder(dto.getModelno(), dto.getWarehouseCode(), dto.getStatusCode(), dto.getAssociateNoType(), dto.getSourceType());
        List<TransOrderQueryMoveResult> results = moves.stream().map(move -> {
            TransOrderQueryMoveResult result = new TransOrderQueryMoveResult();
            result.setInventoryId(move.getInventoryId());
            result.setWarehouseCode(move.getWarehouseCode());
            result.setInventoryStatus(move.getInventoryStatus());
            result.setModelno(move.getModelno());
            result.setQuantity(move.getQuantity());
            result.setPrepareQty(move.getPrepareQuantity());
            result.setAvailableQty(move.getQuantity() - move.getPrepareQuantity());
            result.setAssociateNo(move.getAssociateNo());
            result.setAssociateNoItem(move.getAssociateNoItem());
            result.setAssociateNoSplit(move.getAssociateNoSplitno());
            result.setInventoryPropertyId(move.getInventoryPropertyId());
            return result;
        }).collect(Collectors.toList());
        return results;
    }

    /**
     * 用于委托在库出库查询属性
     */
    @Override
    public List<OpsWTInventoryDTO> findInventoryIdAndInventoryTypeByDoId(String doId) throws OpsException {
        List<OpsDoItemInventory> doItemInventory = baseDoService.getDoItemInventoryByDoId(doId);
        if (doItemInventory.size() > 0) {
            List<OpsWTInventoryDTO> list = new ArrayList<OpsWTInventoryDTO>();
            for (OpsDoItemInventory itemInventory : doItemInventory) {
                InventoryTableTypeEnum tableTypeEnum = InventoryTableTypeEnum.getEnumByType(itemInventory.getInventoryTableType());
                InventoryDTO dto = getInventoryDto(tableTypeEnum, itemInventory.getInventoryId());
                OpsInventoryProperty property = opsInventoryPropertyService.findById(dto.getInventoryPropertyId());
                OpsWTInventoryDTO obj = new OpsWTInventoryDTO();
                obj.setInventoryId(itemInventory.getInventoryId());
                obj.setInventoryType(property.getInventoryTypeCode());
                list.add(obj);
            }
            return list;
        }
        return Collections.emptyList();
    }

    @Override
    public List<OpsWTInventoryDTO> findInventoryIdAndInventoryTypeByPcoId(String pcoId, Integer pcoItem) throws OpsException {
        List<OpsPcoItemInventory> pcoItemInventory = opsPcoService.findPcoItemInventoryByPcoIdAndItem(pcoId, pcoItem);
        if (pcoItemInventory.size() > 0) {
            List<OpsWTInventoryDTO> list = new ArrayList<OpsWTInventoryDTO>();
            for (OpsPcoItemInventory itemInventory : pcoItemInventory) {
                InventoryTableTypeEnum tableTypeEnum = InventoryTableTypeEnum.getEnumByType(itemInventory.getInventoryTableType());
                InventoryDTO dto = getInventoryDto(tableTypeEnum, itemInventory.getInventoryId());
                OpsInventoryProperty property = opsInventoryPropertyService.findById(dto.getInventoryPropertyId());
                OpsWTInventoryDTO obj = new OpsWTInventoryDTO();
                obj.setInventoryId(itemInventory.getInventoryId());
                obj.setInventoryType(property.getInventoryTypeCode());
                list.add(obj);
            }
            return list;
        }
        return Collections.emptyList();
    }

    /**
     * @description 查询在库或在途库存，通过inventoryId
     * @author C12961
     * @date 2022/4/3 14:26
     */
    @Override
    public InventoryDTO getInventoryDto(InventoryTableTypeEnum tableType, Long inventoryId) throws OpsException {
        if (InventoryTableTypeEnum.NORMAL.equals(tableType)) {
            OpsInventory inventory = baseInventoryService.getInventoryById(inventoryId);
            if (ObjectUtil.isNotNull(inventory)) {
                return new InventoryDTO(inventory);
            }
        }
        if (InventoryTableTypeEnum.TRANS.equals(tableType)) {
            OpsInventoryMove inventory = baseInventoryService.getInventoryMoveById(inventoryId);
            if (ObjectUtil.isNotNull(inventory)) {
                return new InventoryDTO(inventory);
            }
        }
        throw Exceptions.OpsException("没有查询到库存");
    }

    /**
     * 库存查询页面用,分页查询
     * 返回分页的数量统计信息，查询条件：仓库信息、批属性信息、型号、在库状态
     *
     * @auther C12961
     * @date 2021/10/15 10:57
     */
    @Override
    public PageInfo<InventoryReport> searchByPage(PageModel<InventoryQO> pageModel) throws OpsException {
        InventoryQO condition = pageModel.getCondition();
        InventoryReportExample example = new InventoryReportExample();
        InventoryReportExample.Criteria criteria = example.createCriteria();
        // 风险在库标识
        if(Objects.nonNull(condition.getRflag())){
            criteria.andRflagEqualTo(condition.getRflag());
        }
        if (StringUtils.isNotBlank(condition.getWarehouseType())) {
            criteria.andWarehouseTypeEqualTo(condition.getWarehouseType());
        }
        if (StringUtils.isNotBlank(condition.getWarehouseCode())) {
            criteria.andWarehouseCodeEqualTo(condition.getWarehouseCode());
        }
        if (StringUtils.isNotBlank(condition.getModelno())) {
            if (condition.getFuzzy() != null && condition.getFuzzy()) {
                criteria.andModelnoLike(StrUtil.trim(condition.getModelno() + "%"));
            } else {
                criteria.andModelnoEqualTo(StrUtil.trim(condition.getModelno()));
            }
        }
        if (StringUtils.isNotBlank(condition.getCustomerNo())) {
            criteria.andCustomerNoEqualTo(StrUtil.cleanBlank(condition.getCustomerNo()));
        }
        if (StringUtils.isNotBlank(condition.getGroupCustomerNo())) {
            criteria.andGroupCustomerNoEqualTo(StrUtil.cleanBlank(condition.getGroupCustomerNo()));
        }
        if (StringUtils.isNotBlank(condition.getPpl())) {
            criteria.andPplEqualTo(StrUtil.cleanBlank(condition.getPpl()));
        }
        if (StringUtils.isNotBlank(condition.getProjectCode())) {
            criteria.andProjectCodeEqualTo(StrUtil.cleanBlank(condition.getProjectCode()));
        }
        if (StringUtils.isNotBlank(condition.getInventoryTypeCode())) {
            criteria.andInventoryTypeCodeEqualTo(StrUtil.cleanBlank(condition.getInventoryTypeCode()));
        }
        if (StringUtils.isNotBlank(condition.getSalesInfoNo())) {
            criteria.andSalesInfoNoEqualTo(StrUtil.cleanBlank(condition.getSalesInfoNo()));
        }
        if (condition.getAvailable() != null && condition.getAvailable()) {
            criteria.andQtyAvailableGreaterThan(0);
        }
        //集合分页
        if (StrUtil.cleanBlank(pageModel.getOrderBy()) != null) {
            PageHelper.orderBy(pageModel.getOrderBy());
        }
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize());
        List<InventoryReport> reportList = inventoryReportMapper.selectByExample(example);
        PageInfo<InventoryReport> pageInfo = new PageInfo<>(reportList);
        return pageInfo;
    }


    /**
     * 导出库存查询结果
     * @param condition
     * @return
     * @throws OpsException
     */
    @Override
    public List<InventoryReportExcel> download(InventoryQO condition) throws OpsException {
        InventoryReportExample example = new InventoryReportExample();
        InventoryReportExample.Criteria criteria = example.createCriteria();
        // 风险在库标识
        if(Objects.nonNull(condition.getRflag())){
            criteria.andRflagEqualTo(condition.getRflag());
        }
        if (StringUtils.isNotBlank(condition.getWarehouseType())) {
            criteria.andWarehouseTypeEqualTo(condition.getWarehouseType());
        }
        if (StringUtils.isNotBlank(condition.getWarehouseCode())) {
            criteria.andWarehouseCodeEqualTo(condition.getWarehouseCode());
        }
        if (StringUtils.isNotBlank(condition.getModelno())) {
            if (condition.getFuzzy() != null && condition.getFuzzy()) {
                criteria.andModelnoLike(StrUtil.trim(condition.getModelno() + "%"));
            } else {
                criteria.andModelnoEqualTo(StrUtil.trim(condition.getModelno()));
            }
        }
        if (StringUtils.isNotBlank(condition.getCustomerNo())) {
            criteria.andCustomerNoEqualTo(StrUtil.cleanBlank(condition.getCustomerNo()));
        }
        if (StringUtils.isNotBlank(condition.getGroupCustomerNo())) {
            criteria.andGroupCustomerNoEqualTo(StrUtil.cleanBlank(condition.getGroupCustomerNo()));
        }
        if (StringUtils.isNotBlank(condition.getPpl())) {
            criteria.andPplEqualTo(StrUtil.cleanBlank(condition.getPpl()));
        }
        if (StringUtils.isNotBlank(condition.getProjectCode())) {
            criteria.andProjectCodeEqualTo(StrUtil.cleanBlank(condition.getProjectCode()));
        }
        if (StringUtils.isNotBlank(condition.getInventoryTypeCode())) {
            criteria.andInventoryTypeCodeEqualTo(StrUtil.cleanBlank(condition.getInventoryTypeCode()));
        }
        if (StringUtils.isNotBlank(condition.getSalesInfoNo())) {
            criteria.andSalesInfoNoEqualTo(StrUtil.cleanBlank(condition.getSalesInfoNo()));
        }
        if (condition.getAvailable() != null && condition.getAvailable()) {
            criteria.andQtyAvailableGreaterThan(0);
        }
        // 最多导出2万条数据
        PageHelper.startPage(1, 20000);
        List<InventoryReport> reportList = inventoryReportMapper.selectByExample(example);
        PageInfo<InventoryReport> pageInfo = new PageInfo<>(reportList);
        return BeanCopyUtil.copyList(pageInfo.getList(), InventoryReportExcel.class);
    }
}
