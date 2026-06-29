package com.sales.ops.serviceimpl.inventory;

import com.sales.ops.common.conf.OpsCommonConfig;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsInventoryDao;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.CKTYPEEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.service.inventory.OpsInventoryBaseService;
import com.sales.ops.serviceimpl.annotation.InvLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：库存基础数据属性查询 增加缓存
 * @date ：Created in 2021/12/9 11:56
 */
@Slf4j
@Service
public class OpsInventoryBaseServiceImpl implements OpsInventoryBaseService {

    @Autowired
    private OpsInventoryMatchConfigMapper opsInventoryMatchConfigMapper;
    @Autowired
    private OpsInventoryRuleConfigMapper opsInventoryRuleConfigMapper;

    @Autowired
    private OpsOrderInventoryRuleConfigMapper opsOrderInventoryRuleConfigMapper;

    @Autowired
    private OpsInventoryRuleConfigItemMapper opsInventoryRuleConfigItemMapper;

    @Autowired
    private OPSRedisUtils opsRedisUtils;

    @Autowired
    private OpsInventoryDao opsInventoryDao;

    @Resource
    private OpsInventoryLogMapper inventoryLogMapper;

    @Autowired
    private OpsCommonConfig opsCommonConfig;
    @Autowired
    private OpsDoMapper opsDoMapper;

    @Resource
    private OpsInventoryMapper opsInventoryMapper;


    @InvLog(apiName = "库存数量变动日志")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void subQtyInventoryForPre(Long id, int qty, int preQty, String tableType, UserDto userDto)
            throws OpsException {
        int flag = 0;
        //重试次数读取配置文件3次
        // bugid:13647 20240304 c14717
        qty = -1 * qty;
        preQty = -1 * preQty;
        for(int i=0;i<opsCommonConfig.getCoInvRetryFrequency(); i++){
            //删除剩余在途库存
            try {
                if (InventoryTableTypeEnum.TRANS.getType().equals(tableType)) {
                    throw Exceptions.OpsException("在途库存只记录，不扣库存");
                } else if (InventoryTableTypeEnum.NORMAL.getType().equals(tableType)) {
                    //bugid:10716 c14717 20230610
                    OpsInventory inventory = opsInventoryMapper.selectByPrimaryKey(id);
                    int rows = 0;
                    if (inventory.getPrepareQuantity() + preQty < 0) {
                        int target = inventory.getPrepareQuantity() + preQty;
                        preQty = (preQty - target);// 预占数量为0  期望结果 inventory.getPrepareQuantity() + preQty = 0;
                    }
                    rows = opsInventoryDao.updateQtyInventoryWithPreAddOutTime(qty, preQty, inventory.getInventoryId(),
                            inventory.getVersion(), userDto.getUserName());
                    if (rows < 1){
                        throw Exceptions.OpsException("doconfirm失败库存补偿时库存更新异常");
                    }
                } else {
                    throw Exceptions.OpsException("库存更新异常tableType异常", tableType);
                }
                //处理成功
                flag = 1;
                break;
            } catch (OpsException e) {
                log.error("doconfirm失败库存补偿",e);
            }
        }
        //处理失败抛出异常
        if(flag == 0){
            throw Exceptions.OpsException("doconfirm失败库存补偿时库存更新异常");
        }
    }

    //查询匹配库存的维度
    public OpsInventoryMatchConfig getOpsInventoryMatchConfig(String inventoryMatchCode) {
        OpsInventoryMatchConfig  opsInventoryMatchConfig = opsInventoryMatchConfigMapper.selectByPrimaryKey(inventoryMatchCode);
       /* //引入redis
        if(StringUtils.isBlank(inventoryMatchCode)){
            return null;
        }
        Object obj  = opsRedisUtils.hGet("ops:opsInventoryMatchConfig:inventoryMatchCode",inventoryMatchCode);
        if(obj !=null){
            opsInventoryMatchConfig = JSON.parseObject(obj.toString(),OpsInventoryMatchConfig.class);
        }

        if(opsInventoryMatchConfig == null){
            opsInventoryMatchConfig = opsInventoryMatchConfigMapper.selectByPrimaryKey(inventoryMatchCode);
            if(opsInventoryMatchConfig != null){
                //opsInventoryMatchConfig
                opsRedisUtils.hPut("ops:opsInventoryMatchConfig:inventoryMatchCode",inventoryMatchCode,JSON.toJSONString(opsInventoryMatchConfig));
            }
        }*/
        return opsInventoryMatchConfig;
    }

    //根据ruleCode获取出库规则
    public OpsInventoryRuleConfig getOpsInventoryRuleConfigList(String ruleCode) {
        OpsInventoryRuleConfig opsInventoryRuleConfig = opsInventoryRuleConfigMapper.selectByPrimaryKey(ruleCode);
       /* //引入redis
        if(StringUtils.isBlank(ruleCode)){
            return null;
        }
        Object obj  = opsRedisUtils.hGet("ops:opsInventoryRuleConfig:ruleCode",ruleCode);
        if(obj !=null){
            opsInventoryRuleConfig = JSON.parseObject(obj.toString(),OpsInventoryRuleConfig.class);
        }
        if(opsInventoryRuleConfig == null){
            opsInventoryRuleConfig = opsInventoryRuleConfigMapper.selectByPrimaryKey(ruleCode);
            if(opsInventoryRuleConfig != null){
                opsRedisUtils.hPut("ops:opsInventoryRuleConfig:ruleCode",ruleCode,JSON.toJSONString(opsInventoryRuleConfig));
            }
        }*/
        return opsInventoryRuleConfig;
    }

    //根据ruleCode获取出库规则明细
    public List<OpsInventoryRuleConfigItem> getInventoryRuleConfigItemList(String ruleCode) {
        List<OpsInventoryRuleConfigItem> list = new ArrayList<>();
        OpsInventoryRuleConfigItemExample example = new OpsInventoryRuleConfigItemExample();
        example.createCriteria().andRuleCodeEqualTo(ruleCode).andDelflagEqualTo(0);
        example.setOrderByClause("rule_sort ASC");
        list = opsInventoryRuleConfigItemMapper.selectByExample(example);

       /* //引入redis
        if(StringUtils.isBlank(ruleCode)){
            return null;
        }
        Object obj  = opsRedisUtils.hGet("ops:opsInventoryRuleConfigItem:ruleCode",ruleCode);
        if(obj !=null){
            JSONArray opsInventoryRuleConfigItemJsonArray = JSONArray.parseArray(obj.toString());
            int opsInventoryRuleConfigItemJsonArraySize = opsInventoryRuleConfigItemJsonArray.size();
            for(int i=0;i<opsInventoryRuleConfigItemJsonArraySize;i++){
                list.add(JSON.parseObject(opsInventoryRuleConfigItemJsonArray.getString(i),OpsInventoryRuleConfigItem.class));
            }
        }
        //若redis内不存在则访问下游service
        if(list.isEmpty()){
            OpsInventoryRuleConfigItemExample example = new OpsInventoryRuleConfigItemExample();
            example.createCriteria().andRuleCodeEqualTo(ruleCode).andDelflagEqualTo(0);
            example.setOrderByClause("rule_sort ASC");
            list = opsInventoryRuleConfigItemMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(list)){
                JSONArray opsInventoryRuleConfigItems= JSONArray.parseArray(JSON.toJSONString(list));
                //设置product到redis内
                opsRedisUtils.hPut("ops:opsInventoryRuleConfigItem:ruleCode",ruleCode,opsInventoryRuleConfigItems.toJSONString());
            }
        }*/
        return list;
    }

    //获取单据类型和单据标记对应的出库规
    public List<OpsOrderInventoryRuleConfig> getOpsOrderInventoryRuleConfigList(String orderType, String tag, String propertyType,CKTYPEEnum cktypeEnum) {
        List<OpsOrderInventoryRuleConfig> list = new ArrayList<>();
        OpsOrderInventoryRuleConfigExample example = new OpsOrderInventoryRuleConfigExample();
        OpsOrderInventoryRuleConfigExample.Criteria criterion = example.createCriteria();
        criterion.andDelflagEqualTo(0).andOrderTypeEqualTo(orderType);
        if (!StringUtils.isBlank(tag)) {
            criterion.andOrderTagEqualTo(tag);
        }
        if (!StringUtils.isBlank(propertyType)) {
            criterion.andPropertyTypeEqualTo(propertyType);
        }
        if (CKTYPEEnum.ITEM_UNLIMIT.equals(cktypeEnum)) {
            criterion.andDlvEntireEqualTo(CKTYPEEnum.ITEM_UNLIMIT.getCode());//"2"
        } else if(CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.equals(cktypeEnum) //货齐单仓 随发单仓 通知发货 都按单仓发货
                || CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.equals(cktypeEnum)
                || CKTYPEEnum.NOTIFY_UNLIMIT.equals(cktypeEnum)){
            criterion.andDlvEntireEqualTo(CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode());//"1"
        }else {
            criterion.andDlvEntireEqualTo("0");// "0" 其他
        }
        //还有其他
        example.setOrderByClause(" sort ASC");
        list = opsOrderInventoryRuleConfigMapper.selectByExample(example);

        /*//引入redis
        if(StringUtils.isBlank(orderType)){
            return null;
        }
        StringBuffer str = new StringBuffer();
        str.append(orderType);
        if (!StringUtils.isBlank(tag)) {
            str.append("_").append(tag);
        }
        if (!StringUtils.isBlank(propertyType)) {
            str.append("_").append(propertyType);
        }
        Object obj  = opsRedisUtils.hGet("ops:opsOrderInventoryRuleConfig:orderType_tag_propertyType",str.toString());
        if(obj !=null){
            JSONArray opsOrderInventoryRuleConfigJsonArray = JSONArray.parseArray(obj.toString());
            int opsOrderInventoryRuleConfigJsonArraySize = opsOrderInventoryRuleConfigJsonArray.size();
            for(int i=0;i<opsOrderInventoryRuleConfigJsonArraySize;i++){
                list.add(JSON.parseObject(opsOrderInventoryRuleConfigJsonArray.getString(i),OpsOrderInventoryRuleConfig.class));
            }
        }
        //若redis内不存在则访问下游service
        if(list.isEmpty()){
            OpsOrderInventoryRuleConfigExample example = new OpsOrderInventoryRuleConfigExample();
            OpsOrderInventoryRuleConfigExample.Criteria criterion = example.createCriteria();
            criterion.andDelflagEqualTo(0).andOrderTypeEqualTo(orderType);
            if (!StringUtils.isBlank(tag)) {
                criterion.andOrderTagEqualTo(tag);
            }
            if (!StringUtils.isBlank(propertyType)) {
                criterion.andPropertyTypeEqualTo(propertyType);
            }
            example.setOrderByClause(" sort ASC");
            list = opsOrderInventoryRuleConfigMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(list)){
                JSONArray OpsOrderInventoryRuleConfigs= JSONArray.parseArray(JSON.toJSONString(list));
                //设置redis内
                opsRedisUtils.hPut("ops:opsOrderInventoryRuleConfig:orderType_tag_propertyType",str.toString(),OpsOrderInventoryRuleConfigs.toJSONString());
            }
        }*/
        return list;
    }

    //刷新以上的缓存数据;
    @Override
    public String refreshOpsInventoryRuleAndConfigData(String mi) {
        Long time = Long.parseLong(mi);
        //查询匹配库存的维度更新时间
        StringBuffer result = new StringBuffer();
        result.append("{ops:opsInventoryMatchConfig:inventoryMatchCode:");
        List<String> inventoryMatchCodes = opsInventoryDao.refreshOpsInventoryMatchConfigData(time);
        for(String inventoryMatchCode : inventoryMatchCodes){
            opsRedisUtils.hDelete("ops:opsInventoryMatchConfig:inventoryMatchCode",inventoryMatchCode);
            result.append(inventoryMatchCode);
        }


        //根据ruleCode获取出库规则 的更新时间;
        List<String> ruleCodes = opsInventoryDao.refreshOpsInventoryRuleConfigData(time);
        result.append("} {ops:opsInventoryRuleConfig:ruleCode:");
        for(String ruleCode : ruleCodes){
            opsRedisUtils.hDelete("ops:opsInventoryRuleConfig:ruleCode",ruleCode);
            result.append(ruleCode);
        }

        //根据ruleCode获取出库规则明细 的更新时间;
        List<String> itemRuleCodes = opsInventoryDao.refreshOpsInventoryRuleConfigItemData(time);
        result.append("} {ops:opsInventoryRuleConfigItem:ruleCode:");
        for(String itemRuleCode : itemRuleCodes){
            opsRedisUtils.hDelete("ops:opsInventoryRuleConfigItem:ruleCode",itemRuleCode);
            result.append(itemRuleCode);
        }
        //获取单据类型和单据标记对应的出库规 的更新时间;
        List<OpsOrderInventoryRuleConfig> opsOrderInventoryRuleConfigs = opsInventoryDao.refreshOpsOrderInventoryRuleConfigData(time);
        result.append("} {ops:opsOrderInventoryRuleConfig:orderType_tag_propertyType:");
        for(OpsOrderInventoryRuleConfig o : opsOrderInventoryRuleConfigs){
            StringBuffer str = new StringBuffer();
            str.append(o.getOrderType());
            if (!StringUtils.isBlank(o.getOrderTag())) {
                str.append("_").append(o.getOrderTag());
            }
            if (!StringUtils.isBlank(o.getPropertyType())) {
                str.append("_").append(o.getPropertyType());
            }
            opsRedisUtils.hDelete("ops:opsOrderInventoryRuleConfig:orderType_tag_propertyType",str.toString());
            result.append(str.toString());
        }

        return result.toString();
    }
}
