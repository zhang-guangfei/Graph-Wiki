package com.sales.ops.serviceimpl.purchase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.db.batchdao.ProductInspectionsGroupDao;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.CommonMqpper;
import com.sales.ops.db.extdao.InventoryRiskDao;
import com.sales.ops.db.extdao.RequestPurchaseDao;
import com.sales.ops.dto.prepareOrder.CanUsePrepareOrderParam;
import com.sales.ops.dto.prepareOrder.OpsPrepareOrderVO;
import com.sales.ops.dto.prepareOrder.PrepareOrderPreQtyUpdateDto;
import com.sales.ops.dto.product.ProductInspectionsGroupInfo;
import com.sales.ops.dto.product.ProductRecordRefModelToSales;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.dto.purchase.RequestPurchaseSupplierCondition;
import com.sales.ops.dto.purchase.RequestPurchaseSupplierInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.PurchaseTypeEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.OPSProductFeignApi;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.service.core.TransService;
import com.sales.ops.service.mdm.MdmService;
import com.sales.ops.service.purchase.PrepareOrderService;
import com.sales.ops.service.purchase.RequestPurchasePreService;
import com.sales.ops.serviceimpl.event.v3.PurchaseEventPublisher;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.shikomi.ShikomiPrepareDTO;
import com.smc.smccloud.model.shikomi.ShikomiVO;
import com.smc.smccloud.service.BinServiceFeignApi;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.ProductServiceFeignApi;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RequestPurchasePreServiceImpl implements RequestPurchasePreService {

    private final static Logger logger = LoggerFactory.getLogger(RequestPurchasePreServiceImpl.class);

    @Autowired
    private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

    @Autowired
    private OpsRequestpurchaseInterceptConfigMapper opsRequestpurchaseInterceptConfigMapper;

    @Autowired
    private ProductDeliveryMapper productDeliveryMapper;

    @Autowired
    private RequestPurchaseDao requestPurchaseDao;

    @Autowired
    private OPSProductFeignApi opsProductFeignApi;

    @Autowired
    private TblWorkdayYearMapper tblWorkdayYearMapper;

    @Autowired
    private ProductServiceFeignApi productServiceFeignApi;

    @Autowired
    private BinServiceFeignApi binServiceFeignApi;

    @Autowired
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    @Autowired
    private ProductInspectionsGroupDao productInspectionsGroupDao;

    @Autowired
    private PurchaseEventPublisher purchaseEventPublisher;

    @Autowired
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Autowired
    private TransService transService;
    @Autowired
    private RequestpurchaseViewMapper requestpurchaseViewMapper;

    @Autowired
    private MdmService mdmService;

    @Autowired
    private InventoryRiskDao inventoryRiskDao;

    @Resource
    private PrepareOrderService prepareOrderService;

    @Resource
    private CommonMqpper commonMqpper;

    private int CAL_SIZE = 2000;

    @Override
    public Integer updateRequestPurchaseBin() {
        // 获取待处理的补库请购单
        OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
        example.or().andStatecodeEqualTo(RequestPurchaseStatusEnum.DAICHULI).andOrdtypeEqualTo(OrderTypeEnum.BIN);
        example.setOrderByClause("orderno,itemno");
        // example.createCriteria().andOrdernoEqualTo("9900002452").andItemnoEqualTo(80);
        List<OpsRequestpurchase> list = opsRequestpurchaseMapper.selectByExample(example);

        if (list == null || list.size() == 0) {
            return 0;
        }
        // bug13496获取3C及冷干机清单
        List<ProductRecordRefModelToSales> refModel = productInspectionsGroupDao.selectAll();
        Map<String, ProductRecordRefModelToSales> refMap = new HashMap<String, ProductRecordRefModelToSales>();
        if (CollectionUtils.isNotEmpty(refModel)) {
            refModel.forEach(a -> {
                refMap.put(a.getRefModel(), a);
            });
        }

        Map<String, ProductInspectionsGroupInfo> modelMap = new HashMap<String, ProductInspectionsGroupInfo>();
        List<String> modelList = new ArrayList<String>();
        list.forEach(item -> {
            if (!modelMap.containsKey(item.getModelno())) {
                modelMap.put(item.getModelno(), null);
                modelList.add(item.getModelno());
            }
            // 增加带上3c-查询
            if (!item.getModelno().startsWith("3C-")) {
                if (!modelMap.containsKey("3C-" + item.getModelno())) {
                    modelMap.put("3C-" + item.getModelno(), null);
                    modelList.add("3C-" + item.getModelno());
                }
            }
        });

        List<ProductInspectionsGroupInfo> arr = new ArrayList<ProductInspectionsGroupInfo>();
        for (int i = 0; i < modelList.size(); i++) {
            if (i % CAL_SIZE == 0) {
                List<String> temp = new ArrayList<String>();
                if (i + CAL_SIZE < modelList.size()) {
                    temp = modelList.subList(i, i + CAL_SIZE);
                } else {
                    temp = modelList.subList(i, modelList.size());
                }
                List<ProductInspectionsGroupInfo> l = productInspectionsGroupDao.selectInfos(temp);
                if (l != null && l.size() > 0) {
                    arr.addAll(l);
                }
            }
        }

        modelMap.clear();
        arr.forEach(i -> {
            if (!modelMap.containsKey(i.getModel())) {
                modelMap.put(i.getModel(), i);
            }
        });

        // 设置净重
        Map<String, BigDecimal> weightMap = new HashMap<String, BigDecimal>();
        list.forEach(item -> {
            String modelNo = item.getModelno();
            // 获取净重
            if (weightMap.containsKey(modelNo)) {
                item.setNetweight(weightMap.get(modelNo));
            } else {
                CommonResult<ProductPhysics> physics = opsProductFeignApi.findProductPhysicsByModelNo(modelNo);
                if (physics.isSuccess() && physics.getData().getNetweight() != null) {
                    item.setNetweight(physics.getData().getNetweight());
                    weightMap.put(modelNo, physics.getData().getNetweight());
                }
            }
            // 从关务系统获取产品类别
            if (modelMap.containsKey(modelNo)) {
                if (modelMap.get(modelNo).getInspectionsGroupId() != null) {
                    item.setProducttype(Integer.parseInt(modelMap.get(modelNo).getInspectionsGroupId()));
                } else {
                    item.setProducttype(0);
                }
                item.setHscode(modelMap.get(modelNo).getTariffcode());
            }
            if (!item.getModelno().startsWith("3C-") && refMap.containsKey(modelNo)
                    && StringUtils.equals(refMap.get(modelNo).getCategory(), "3C认证品")) {
                if (modelMap.containsKey("3C-" + modelNo)) {
                    if (modelMap.get("3C-" + modelNo).getInspectionsGroupId() != null) {
                        item.setProducttype(Integer.parseInt(modelMap.get("3C-" + modelNo).getInspectionsGroupId()));
                    } else {
                        item.setProducttype(0);
                    }
                    item.setHscode(modelMap.get("3C-" + modelNo).getTariffcode());
                }
            }
            // 设置默认状态为处理中
            if (StringUtils.equals(RequestPurchaseStatusEnum.DAICHULI, item.getStatecode())) {
                item.setStatecode(RequestPurchaseStatusEnum.CHULIZHONG);
            }
        });

        // 需变更订单拦截状态清单
        List<OpsRequestpurchase> lanjie = null;
        // 分批处理
        int CAL_SIZE = 500;
        for (int i = 0; i < list.size(); i++) {
            if (i % CAL_SIZE == 0) {
                List<OpsRequestpurchase> temp = null;
                if (i + CAL_SIZE < list.size()) {
                    temp = list.subList(i, i + CAL_SIZE);
                } else {
                    temp = list.subList(i, list.size());
                }

                // 获取型号供应商
                temp = calSupplierInfo(temp);

                lanjie = new ArrayList<OpsRequestpurchase>();
                for (OpsRequestpurchase item : temp) {
                    // 判断是否有供应商
                    // if (StringUtils.equals(item.getStatecode(),
                    // RequestPurchaseStatusEnum.CHULIZHONG)) {
                    if (StringUtils.isBlank(item.getSupplierid())) {
                        item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                        if (StringUtils.isBlank(item.getInterceptmsg())) {
                            item.setInterceptmsg("无法获取该型号供应商信息");
                        } else if (!item.getInterceptmsg().contains("无法获取该型号供应商信息"))
                            item.setInterceptmsg(item.getInterceptmsg() + ";无法获取该型号供应商信息");
                    } else if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                        item.setInterceptmsg(item.getInterceptmsg().replace("无法获取该型号供应商信息;", ""));
                    }
                    // }

                    if (item.getSmccode() == null) {
                        if (StringUtils.equals("KGZ", item.getPurchasewarehouseid())) {
                            item.setSmccode("9501211");
                        } else {
                            item.setSmccode("9501200");
                        }
                    }

                    // 判断是否是3C-A产品，若是则拦截人工处理
                    // bug13496变更3C品的表
                    if (refMap.containsKey(item.getModelno())
                            && StringUtils.equals(refMap.get(item.getModelno()).getCategory(), "3C认证品")
                            && !item.getModelno().startsWith("3C-")) {
                        item.setInspectiontype("3C-A");
                        item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                        if (StringUtils.isBlank(item.getInterceptmsg())) {
                            item.setInterceptmsg("3C-A产品");
                        } else
                            item.setInterceptmsg(item.getInterceptmsg() + ";3C-A产品");
                    } else {
                        item.setInspectiontype(null);
                    }

                    if (refMap.containsKey(item.getModelno())
                            && StringUtils.equals(refMap.get(item.getModelno()).getCategory(), "中文说明书")) {
                        item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                        if (StringUtils.isBlank(item.getInterceptmsg())) {
                            item.setInterceptmsg("冷干机未加G");
                        } else
                            item.setInterceptmsg(item.getInterceptmsg() + ";冷干机未加G");
                    }

                    // SHIKOMI拦截，若无SHIKOMI则判断是否有可用
                    // 是否为SHIKOMI放行状态，若为SHIKOMI放行状态则不再判断SHIKOMI
                    if (!StringUtils.equals("2", item.getShikomirelease())) {
                        // SHIKOMI拦截判断，若有SHIKOMI则判断是否可用，若没有且非不使用SHIKOMI状态则找是否可借用
                        if (StringUtils.isNotBlank(item.getShikomianswerno())) {
                            // 判断是否可用，不可用则拦截;使用最终客户
                            ResultVo<ShikomiVO> shikomiApi = productServiceFeignApi.canUseShikomi(item.getModelno(),
                                    item.getShikomianswerno(),
                                    StringUtils.isBlank(item.getUserno()) ? item.getCustomerno() : item.getUserno());
                            if (shikomiApi.isSuccess()) {
                                if (item.getQuantity() <= shikomiApi.getData().getRemainQty()
                                        - shikomiApi.getData().getQtyOrdPre()) {
                                    // bug11918 马腾 增加供应商是否相同判断，若相同则不需重算
                                    if (!StringUtils.equals(shikomiApi.getData().getSupplierCode(),
                                            item.getSupplierid())) {
                                        // bug14982若供应商不一致则拦截
                                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                        item.setInterceptmsg("SHIKOMI对应供应商与型号产地不一致");
                                    }
                                    // bug12272 根据SHIKOMI的ROHS更新采购数据
                                    if (StringUtils.equals("1", shikomiApi.getData().getRohs())) {
                                        item.setProducttag("0");
                                    }
                                } else {
                                    item.setShikomiremainqty(shikomiApi.getData().getRemainQty());
                                    item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                    item.setInterceptmsg("SHIKOMI可用数量不满足");
                                }
                            } else {
                                item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                item.setInterceptmsg("SHIKOMI不可用");
                            }
                        } else if (!StringUtils.equals("1", item.getShikomirelease())) {
                            ResultVo<List<ShikomiVO>> shikomi = productServiceFeignApi.listCustomerShikomi(
                                    item.getModelno(),
                                    StringUtils.isBlank(item.getUserno()) ? item.getCustomerno() : item.getUserno());
                            if (shikomi.isSuccess()) {
                                if (shikomi.getData() != null && shikomi.getData().size() > 0) {
                                    // bug14982 增加供应商一致性判断
                                    if (!CollectionUtils.isEmpty(shikomi.getData())) {
                                        // 判断是否有可用，有则自动使用
                                        List<ShikomiVO> l = shikomi.getData().stream().filter(a -> 1 == a.getStatus())
                                                .collect(Collectors.toList());
                                        if (l != null && l.size() > 0) {
                                            // 根据优先级排序并赋值
                                            List<ShikomiVO> s = l.stream().filter(a -> "C".equals(a.getClassType()))
                                                    .sorted(Comparator.comparing(ShikomiVO::getApplyTime,
                                                            Comparator.nullsFirst(Date::compareTo)))
                                                    .collect(Collectors.toList());
                                            if (s != null && s.size() > 0) {
                                                int flag = 0;
                                                for (ShikomiVO vo : s) {
                                                    if (vo.getRemainQty() - vo.getQtyOrdPre() >= item.getQuantity()) {
                                                        item.setShikomianswerno(vo.getShikomiNo());
                                                        flag = 1;
                                                        // bug12272
                                                        // 根据SHIKOMI的ROHS更新采购数据
                                                        if (StringUtils.equals("1", vo.getRohs())) {
                                                            item.setProducttag("0");
                                                        }
                                                        // bug14982若供应商不一致则拦截提示
                                                        if (!StringUtils.equals(item.getSupplierid(),
                                                                vo.getSupplierCode())) {
                                                            item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                                            item.setInterceptmsg("有可用SHIKOMI，但是SHIKOMI对应供应商与型号产地不一致");
                                                        }
                                                        break;
                                                    }
                                                }
                                                if (flag == 0) {
                                                    item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                                    item.setInterceptmsg("残数不足");
                                                }
                                            } else {
                                                s = l.stream().filter(a -> "A".equals(a.getClassType()))
                                                        .sorted(Comparator.comparing(ShikomiVO::getApplyTime,
                                                                Comparator.nullsFirst(Date::compareTo)))
                                                        .collect(Collectors.toList());
                                                if (s != null && s.size() > 0) {
                                                    int flag = 0;
                                                    for (ShikomiVO vo : s) {
                                                        if (vo.getRemainQty() - vo.getQtyOrdPre() >= item
                                                                .getQuantity()) {
                                                            item.setShikomianswerno(vo.getShikomiNo());
                                                            flag = 1;
                                                            // bug12272
                                                            // 根据SHIKOMI的ROHS更新采购数据
                                                            if (StringUtils.equals("1", vo.getRohs())) {
                                                                item.setProducttag("0");
                                                            }
                                                            // bug14982若供应商不一致则拦截提示
                                                            if (!StringUtils.equals(item.getSupplierid(),
                                                                    vo.getSupplierCode())) {
                                                                item.setStatecode(
                                                                        RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                                                item.setInterceptmsg(
                                                                        "有可用SHIKOMI，但是SHIKOMI对应供应商与型号产地不一致");
                                                            }
                                                            break;
                                                        }
                                                    }
                                                    if (flag == 0) {
                                                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                                        item.setInterceptmsg("残数不足");
                                                    }
                                                }
                                            }
                                        } else {
                                            // 有可借用，则拦截
                                            l = shikomi.getData().stream().filter(a -> 2 == a.getStatus())
                                                    .collect(Collectors.toList());
                                            if (l != null && l.size() > 0) {
                                                item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                                item.setInterceptmsg("有可借用SHIKOMI");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // bug14982若放行且带着shikomi号，则判断是否供应商一致，不一致的话按照shikomi供应商变更请购信息
                        if (StringUtils.isNotBlank(item.getShikomianswerno())) {
                            // bug15202在放行时使用的SHIKOMI直接校验供应商即可，变更调用接口
                            ResultVo<ShikomiVO> shikomiApi = productServiceFeignApi
                                    .getSupplierByShikomi(item.getShikomianswerno());
                            if (shikomiApi.isSuccess()) {
                                if (item.getQuantity() <= shikomiApi.getData().getRemainQty()
                                        - shikomiApi.getData().getQtyOrdPre()) {
                                    // bug11918 马腾 增加供应商是否相同判断，若相同则不需重算
                                    if (!StringUtils.equals(shikomiApi.getData().getSupplierCode(),
                                            item.getSupplierid())) {
                                        // 设置供应商为SHIKOMI供应商
                                        item.setSupplierid(shikomiApi.getData().getSupplierCode());
                                        // bug11599 马腾 增加SMCCODE及采购仓的重新计算
                                        item.setSmccode(null);
                                        item.setPurchasewarehouseid(null);
                                        // bug15382 强制使用shikomi供应商，isedit设置为true
                                        item = calWarehouseAndSmccode(item, true);
                                    }
                                    // bug12272 根据SHIKOMI的ROHS更新采购数据
                                    if (StringUtils.equals("1", shikomiApi.getData().getRohs())) {
                                        item.setProducttag("0");
                                    }
                                } else {
                                    item.setShikomiremainqty(shikomiApi.getData().getRemainQty());
                                    item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                    item.setInterceptmsg("SHIKOMI可用数量不满足");
                                }
                            } else {
                                item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                item.setInterceptmsg("SHIKOMI不可用");
                            }
                        }
                    }

                    // bug13379将shikomi预占操作移出到公共操作中，不在shikomi放行时进行预占操作了
                    // 预占shikomi
                    if (StringUtils.isNotBlank(item.getShikomianswerno())
                            && !StringUtils.equals(RequestPurchaseStatusEnum.LANJIE, item.getStatecode())
                            && !StringUtils.equals(RequestPurchaseStatusEnum.SHIKOMILANJIE, item.getStatecode())) {
                        if (StringUtils.isBlank(item.getInformation()) || (StringUtils.isNotBlank(item.getInformation())
                                && !item.getInformation().contains("SHIKOMI已预占"))) {
                            ShikomiPrepareDTO shikomiPrepareDTO = new ShikomiPrepareDTO();
                            if (item.getSplititemno() == null) {
                                shikomiPrepareDTO.setOrderNo(item.getOrderno() + "-" + item.getItemno().toString());
                            } else {
                                shikomiPrepareDTO.setOrderNo(item.getOrderno() + "-" + item.getItemno().toString() + "-"
                                        + item.getSplititemno().toString());
                            }
                            shikomiPrepareDTO.setShikomiNo(item.getShikomianswerno());
                            shikomiPrepareDTO.setQuantity(item.getQuantity());
                            shikomiPrepareDTO.setSupplierCode(item.getSupplierid());
                            ResultVo<String> preresult = productServiceFeignApi.prepareShikomi(shikomiPrepareDTO);
                            if (!preresult.isSuccess()) {
                                item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                item.setInterceptmsg("SHIKOMI预占失败");
                            } else {
                                // 避免放行时反复预占，预占一次后information中记录
                                if (StringUtils.isBlank(item.getInformation())) {
                                    item.setInformation("SHIKOMI已预占;");
                                } else {
                                    item.setInformation(item.getInformation() + ";SHIKOMI已预占;");
                                }
                            }
                        }
                    }

                }
                // bug11599将此操作后移，在SHIKOMI变更供应商后需要重新计算运输方式
                // 计算指定出库日及运输方式
                temp = calDlvInfo(temp);

                // bug10666马腾 先判断可用SHIKOMI 在判断是否拦截
                // 判断拦截,设置状态为拦截
                temp = intercept(temp);
                // bug14330 AP订单合并后数量也不能超过固定值
                Integer maxnum = 200;
                ResultVo<List<DataCodeVO>> result = dictDataServiceFeignApi.getDataCodes("3011");
                if (result.isSuccess() && !CollectionUtils.isEmpty(result.getData())
                        && StringUtils.isNotBlank(result.getData().get(0).getExtNote1())) {
                    maxnum = Integer.parseInt(result.getData().get(0).getExtNote1());
                }
                String smccode = null;
                Date hope = null;
                String transtype = null;
                for (OpsRequestpurchase item : temp) {
                    // 阀汇流板specmark！=0，则统一smccode及出荷日
                    if (StringUtils.isBlank(item.getSpecmark()) || "0".equals(item.getSpecmark())) {
                        smccode = null;
                        hope = null;
                        transtype = null;
                    } else if ("1".equals(item.getSpecmark())) {
                        smccode = item.getSmccode();
                        hope = item.getHopeexportdate();
                        transtype = item.getTranstype();
                    } else if ("2".equals(item.getSpecmark())) {
                        if (smccode != null) {
                            item.setSmccode(smccode);
                        }
                        if (hope != null) {
                            item.setHopeexportdate(hope);
                        }
                        if (StringUtils.isNotBlank(transtype)) {
                            item.setTranstype(transtype);
                        }
                    }
                    // bug14330AP增加采购数量控制
                    if (StringUtils.equals("AP", item.getSupplierid())) {
                        if (item.getQuantity().compareTo(maxnum) > 0) {
                            item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                            if (StringUtils.isBlank(item.getInterceptmsg()))
                                item.setInterceptmsg("AP采购数量不可超过" + maxnum.toString());
                            else {
                                item.setInterceptmsg(item.getInterceptmsg() + ";AP采购数量不可超过" + maxnum.toString());
                            }
                        }
                    }

                    if (StringUtils.equals(RequestPurchaseStatusEnum.LANJIE, item.getStatecode())
                            || StringUtils.equals(RequestPurchaseStatusEnum.SHIKOMILANJIE, item.getStatecode())) {
                        lanjie.add(item);
                    } else {
                        // bug14200增加非拦截请购单的处理事件
                        purchaseEventPublisher.requestPurchaseOrderEvent(EventSourceEnum.REQUEST_PREPROCESS, item);
                    }
                    item.setUpdatetime(new Date());
                    item.setPoOrderNo(null);
                    item.setPoItemNo(null);
                    item.setPoSplitNo(null);
                    // 更新请购单数据及状态
                    opsRequestpurchaseMapper.updateByPrimaryKey(item);
                }

                logger.info("已处理500条BIN补库！");
                opsWmDispatchForOrderFeignApi.interceptForRequestPo(lanjie);
            }
        }

        return list.size();
    }

    @Override
    public Integer updateRequestPurchase() {
        // 获取待处理的非补库请购单
        OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
        example.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.DAICHULI)
                .andOrdtypeNotEqualTo(OrderTypeEnum.BIN);
        // example.createCriteria().andOrdernoEqualTo("12079954").andItemnoEqualTo(2);//
        // .andSplititemnoEqualTo(2);
        example.setOrderByClause("orderno,itemno");
        List<OpsRequestpurchase> list = opsRequestpurchaseMapper.selectByExample(example);

        if (list == null || list.size() == 0) {
            return 0;
        }
        // bug13496获取3C及冷干机清单
        List<ProductRecordRefModelToSales> refModel = productInspectionsGroupDao.selectAll();
        Map<String, ProductRecordRefModelToSales> refMap = new HashMap<String, ProductRecordRefModelToSales>();
        if (CollectionUtils.isNotEmpty(refModel)) {
            refModel.forEach(a -> {
                refMap.put(a.getRefModel(), a);
            });
        }

        Map<String, ProductInspectionsGroupInfo> modelMap = new HashMap<String, ProductInspectionsGroupInfo>();
        List<String> modelList = new ArrayList<String>();
        list.forEach(item -> {
            if (!modelMap.containsKey(item.getModelno())) {
                modelMap.put(item.getModelno(), null);
                modelList.add(item.getModelno());

            }
            // 增加带上3c-查询
            if (!item.getModelno().startsWith("3C-")) {
                if (!modelMap.containsKey("3C-" + item.getModelno())) {
                    modelMap.put("3C-" + item.getModelno(), null);
                    modelList.add("3C-" + item.getModelno());
                }
            }
        });

        List<ProductInspectionsGroupInfo> arr = new ArrayList<ProductInspectionsGroupInfo>();
        for (int i = 0; i < modelList.size(); i++) {
            if (i % CAL_SIZE == 0) {
                List<String> temp = new ArrayList<String>();
                if (i + CAL_SIZE < modelList.size()) {
                    temp = modelList.subList(i, i + CAL_SIZE);
                } else {
                    temp = modelList.subList(i, modelList.size());
                }
                List<ProductInspectionsGroupInfo> l = productInspectionsGroupDao.selectInfos(temp);
                if (l != null && l.size() > 0) {
                    arr.addAll(l);
                }
            }
        }

        modelMap.clear();
        arr.forEach(i -> {
            if (!modelMap.containsKey(i.getModel())) {
                modelMap.put(i.getModel(), i);
            }
        });

        // 设置净重
        Map<String, BigDecimal> weightMap = new HashMap<String, BigDecimal>();
        list.forEach(item -> {
            String modelNo = item.getModelno();
            // 获取净重
            if (weightMap.containsKey(modelNo)) {
                item.setNetweight(weightMap.get(modelNo));
            } else {
                CommonResult<ProductPhysics> physics = opsProductFeignApi.findProductPhysicsByModelNo(modelNo);
                if (physics.isSuccess() && physics.getData().getNetweight() != null) {
                    item.setNetweight(physics.getData().getNetweight());
                    weightMap.put(modelNo, physics.getData().getNetweight());
                }
            }
            // 从关务系统获取产品类别
            if (modelMap.containsKey(modelNo)) {
                if (modelMap.get(modelNo).getInspectionsGroupId() != null) {
                    item.setProducttype(Integer.parseInt(modelMap.get(modelNo).getInspectionsGroupId()));
                } else {
                    item.setProducttype(0);
                }
                item.setHscode(modelMap.get(modelNo).getTariffcode());
            }
            if (!item.getModelno().startsWith("3C-") && refMap.containsKey(modelNo)
                    && StringUtils.equals(refMap.get(modelNo).getCategory(), "3C认证品")) {
                if (modelMap.containsKey("3C-" + modelNo)) {
                    if (modelMap.get("3C-" + modelNo).getInspectionsGroupId() != null) {
                        item.setProducttype(Integer.parseInt(modelMap.get("3C-" + modelNo).getInspectionsGroupId()));
                    } else {
                        item.setProducttype(0);
                    }
                    item.setHscode(modelMap.get("3C-" + modelNo).getTariffcode());
                }
            }

            // 设置默认状态为处理中
            if (StringUtils.equals(RequestPurchaseStatusEnum.DAICHULI, item.getStatecode())) {
                item.setStatecode(RequestPurchaseStatusEnum.CHULIZHONG);
            }
        });

        // 需变更订单拦截状态清单
        List<OpsRequestpurchase> lanjie = new ArrayList<OpsRequestpurchase>();

        // 获取型号供应商
        list = calSupplierInfo(list);

        for (OpsRequestpurchase item : list) {
            // 判断是否有供应商
            // if (StringUtils.equals(item.getStatecode(),
            // RequestPurchaseStatusEnum.CHULIZHONG)) {
            if (StringUtils.isBlank(item.getSupplierid())) {
                item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                if (StringUtils.isBlank(item.getInterceptmsg())) {
                    item.setInterceptmsg("无法获取该型号供应商信息");
                } else if (!item.getInterceptmsg().contains("无法获取该型号供应商信息"))
                    item.setInterceptmsg(item.getInterceptmsg() + ";无法获取该型号供应商信息");
            } else if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                item.setInterceptmsg(item.getInterceptmsg().replace("无法获取该型号供应商信息;", ""));
            }
            // }

            if (item.getSmccode() == null) {
                if (StringUtils.equals("KGZ", item.getPurchasewarehouseid())) {
                    item.setSmccode("9501211");
                } else {
                    item.setSmccode("9501200");
                }
            }

            // bug13496变更3C品的表
            if (refMap.containsKey(item.getModelno())
                    && StringUtils.equals(refMap.get(item.getModelno()).getCategory(), "3C认证品")
                    && !item.getModelno().startsWith("3C-")) {
                item.setInspectiontype("3C-A");
                item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                if (StringUtils.isBlank(item.getInterceptmsg())) {
                    item.setInterceptmsg("3C-A产品");
                } else
                    item.setInterceptmsg(item.getInterceptmsg() + ";3C-A产品");
            } else {
                item.setInspectiontype(null);
            }

            if (refMap.containsKey(item.getModelno())
                    && StringUtils.equals(refMap.get(item.getModelno()).getCategory(), "中文说明书")) {
                item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                if (StringUtils.isBlank(item.getInterceptmsg())) {
                    item.setInterceptmsg("冷干机未加G");
                } else
                    item.setInterceptmsg(item.getInterceptmsg() + ";冷干机未加G");
            }
            // SHIKOMI拦截，若无SHIKOMI则判断是否有可用
            // 是否为SHIKOMI放行状态，若为SHIKOMI放行状态则不再判断SHIKOMI
            if (StringUtils.equals("2", item.getShikomirelease())) {
                // bug14982若放行且带着shikomi号，则判断是否供应商一致，不一致的话按照shikomi供应商变更请购信息
                if (StringUtils.isNotBlank(item.getShikomianswerno())) {
                    // 判断是否可用，不可用则拦截;使用最终客户
                    ResultVo<ShikomiVO> shikomiApi = productServiceFeignApi
                            .getSupplierByShikomi(item.getShikomianswerno());
                    if (shikomiApi.isSuccess()) {
                        if (item.getQuantity() <= shikomiApi.getData().getRemainQty()
                                - shikomiApi.getData().getQtyOrdPre()) {
                            // bug11918 马腾 增加供应商是否相同判断，若相同则不需重算
                            if (!StringUtils.equals(shikomiApi.getData().getSupplierCode(), item.getSupplierid())) {
                                // 设置供应商为SHIKOMI供应商
                                item.setSupplierid(shikomiApi.getData().getSupplierCode());
                                // bug11599 马腾 增加smccode及采购仓的重新计算
                                item.setSmccode(null);
                                item.setPurchasewarehouseid(null);
                                // bug15382 强制使用shikomi供应商，isedit设置为true
                                item = calWarehouseAndSmccode(item, true);
                            }
                            // bug12272 根据SHIKOMI的ROHS更新采购数据
                            if (StringUtils.equals("1", shikomiApi.getData().getRohs())) {
                                item.setProducttag("0");
                            }
                        } else {
                            item.setShikomiremainqty(shikomiApi.getData().getRemainQty());
                            item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                            item.setInterceptmsg("SHIKOMI可用数量不满足");
                        }
                    } else {
                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                        item.setInterceptmsg("SHIKOMI不可用");
                    }
                }
            }
            if (!StringUtils.equals("2", item.getShikomirelease())) {
                // SHIKOMI拦截判断，若有SHIKOMI则判断是否可用，若没有且非不使用SHIKOMI状态则找是否可借用
                if (StringUtils.isNotBlank(item.getShikomianswerno())) {
                    // 判断是否可用，不可用则拦截;使用最终客户
                    ResultVo<ShikomiVO> shikomiApi = productServiceFeignApi.canUseShikomi(item.getModelno(),
                            item.getShikomianswerno(),
                            StringUtils.isBlank(item.getUserno()) ? item.getCustomerno() : item.getUserno());
                    if (shikomiApi.isSuccess()) {
                        if (item.getQuantity() <= shikomiApi.getData().getRemainQty()
                                - shikomiApi.getData().getQtyOrdPre()) {
                            // bug11918 马腾 增加供应商是否相同判断，若相同则不需重算
                            if (!StringUtils.equals(shikomiApi.getData().getSupplierCode(), item.getSupplierid())) {
                                // bug14982若供应商不一致则拦截
                                item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                item.setInterceptmsg("SHIKOMI对应供应商与型号产地不一致");
                            }
                            // bug12272 根据SHIKOMI的ROHS更新采购数据
                            if (StringUtils.equals("1", shikomiApi.getData().getRohs())) {
                                item.setProducttag("0");
                            }
                        } else {
                            item.setShikomiremainqty(shikomiApi.getData().getRemainQty());
                            item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                            item.setInterceptmsg("SHIKOMI可用数量不满足");
                        }
                    } else {
                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                        item.setInterceptmsg("SHIKOMI不可用");
                    }
                } else if (!StringUtils.equals("1", item.getShikomirelease())) {
                    ResultVo<List<ShikomiVO>> shikomi = productServiceFeignApi.listCustomerShikomi(item.getModelno(),
                            StringUtils.isBlank(item.getUserno()) ? item.getCustomerno() : item.getUserno());
                    if (shikomi.isSuccess()) {
                        if (shikomi.getData() != null && shikomi.getData().size() > 0) {
                            // bug14982 增加供应商一致性判断
                            // String sup = item.getSupplierid();
                            // List<ShikomiVO> supplierShikomi =
                            // shikomi.getData().stream()
                            // .filter(a ->
                            // StringUtils.equals(a.getSupplierCode(), sup))
                            // .collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(shikomi.getData())) {
                                // 判断是否有可用，有则自动使用
                                List<ShikomiVO> l = shikomi.getData().stream().filter(a -> 1 == a.getStatus())
                                        .collect(Collectors.toList());
                                if (l != null && l.size() == 1) {
                                    if (l.get(0).getRemainQty() - l.get(0).getQtyOrdPre() >= item.getQuantity()) {
                                        item.setShikomianswerno(l.get(0).getShikomiNo());
                                        // bug12272 根据SHIKOMI的ROHS更新采购数据
                                        if (StringUtils.equals("1", l.get(0).getRohs())) {
                                            item.setProducttag("0");
                                        }
                                        // bug14982若供应商不一致则拦截提示
                                        if (!StringUtils.equals(item.getSupplierid(), l.get(0).getSupplierCode())) {
                                            item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                            item.setInterceptmsg("有可用SHIKOMI，但是SHIKOMI对应供应商与型号产地不一致");
                                        }
                                    } else {
                                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                        item.setInterceptmsg(
                                                l.get(0).getShikomiNo() + "残数不足:" + l.get(0).getRemainQty());
                                    }
                                } else if (l != null && l.size() > 1) {
                                    // 根据优先级排序并赋值
                                    int flag = 0;
                                    List<ShikomiVO> s = l.stream().filter(a -> "C".equals(a.getClassType()))
                                            .filter(a -> "CN".equals(a.getCompanyCode()))
                                            .sorted(Comparator.comparing(ShikomiVO::getApplyTime,
                                                    Comparator.nullsFirst(Date::compareTo)))
                                            .collect(Collectors.toList());
                                    if (s != null && s.size() > 0) {
                                        for (ShikomiVO vo : s) {
                                            if (vo.getRemainQty() - vo.getQtyOrdPre() >= item.getQuantity()) {
                                                item.setShikomianswerno(vo.getShikomiNo());
                                                flag = 1;
                                                // bug12272 根据SHIKOMI的ROHS更新采购数据
                                                if (StringUtils.equals("1", vo.getRohs())) {
                                                    item.setProducttag("0");
                                                }
                                                // bug14982若供应商不一致则拦截提示
                                                if (!StringUtils.equals(item.getSupplierid(), vo.getSupplierCode())) {
                                                    item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                                    item.setInterceptmsg("有可用SHIKOMI，但是SHIKOMI对应供应商与型号产地不一致");
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    if (flag == 0) {
                                        s = l.stream().filter(a -> "C".equals(a.getClassType()))
                                                .filter(a -> !"CN".equals(a.getCompanyCode()))
                                                .sorted(Comparator.comparing(ShikomiVO::getApplyTime,
                                                        Comparator.nullsFirst(Date::compareTo)))
                                                .collect(Collectors.toList());
                                        if (s != null && s.size() > 0) {
                                            for (ShikomiVO vo : s) {
                                                if (vo.getRemainQty() - vo.getQtyOrdPre() >= item.getQuantity()) {
                                                    item.setShikomianswerno(vo.getShikomiNo());
                                                    flag = 1;
                                                    // bug12272
                                                    // 根据SHIKOMI的ROHS更新采购数据
                                                    if (StringUtils.equals("1", vo.getRohs())) {
                                                        item.setProducttag("0");
                                                    }
                                                    // bug14982若供应商不一致则拦截提示
                                                    if (!StringUtils.equals(item.getSupplierid(),
                                                            vo.getSupplierCode())) {
                                                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                                        item.setInterceptmsg("有可用SHIKOMI，但是SHIKOMI对应供应商与型号产地不一致");
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (flag == 0) {
                                        s = l.stream().filter(a -> "A".equals(a.getClassType()))
                                                .sorted(Comparator.comparing(ShikomiVO::getApplyTime,
                                                        Comparator.nullsFirst(Date::compareTo)))
                                                .collect(Collectors.toList());
                                        if (s != null && s.size() > 0) {
                                            for (ShikomiVO vo : s) {
                                                if (vo.getRemainQty() - vo.getQtyOrdPre() >= item.getQuantity()) {
                                                    item.setShikomianswerno(vo.getShikomiNo());
                                                    flag = 1;
                                                    // bug12272
                                                    // 根据SHIKOMI的ROHS更新采购数据
                                                    if (StringUtils.equals("1", vo.getRohs())) {
                                                        item.setProducttag("0");
                                                    }
                                                    // bug14982若供应商不一致则拦截提示
                                                    if (!StringUtils.equals(item.getSupplierid(),
                                                            vo.getSupplierCode())) {
                                                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                                        item.setInterceptmsg("有可用SHIKOMI，但是SHIKOMI对应供应商与型号产地不一致");
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (flag == 0) {
                                        s = l.stream().filter(a -> "B".equals(a.getClassType()))
                                                .sorted(Comparator.comparing(ShikomiVO::getApplyTime,
                                                        Comparator.nullsFirst(Date::compareTo)))
                                                .collect(Collectors.toList());
                                        if (s != null && s.size() > 0) {
                                            for (ShikomiVO vo : s) {
                                                if (vo.getRemainQty() - vo.getQtyOrdPre() >= item.getQuantity()) {
                                                    item.setShikomianswerno(vo.getShikomiNo());
                                                    flag = 1;
                                                    // bug12272
                                                    // 根据SHIKOMI的ROHS更新采购数据
                                                    if (StringUtils.equals("1", vo.getRohs())) {
                                                        item.setProducttag("0");
                                                    }
                                                    // bug14982若供应商不一致则拦截提示
                                                    if (!StringUtils.equals(item.getSupplierid(),
                                                            vo.getSupplierCode())) {
                                                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                                        item.setInterceptmsg("有可用SHIKOMI，但是SHIKOMI对应供应商与型号产地不一致");
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (flag == 0) {
                                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                        item.setInterceptmsg("残数不足");
                                    }
                                } else {
                                    // 有可借用，则拦截
                                    l = shikomi.getData().stream().filter(a -> 2 == a.getStatus())
                                            .collect(Collectors.toList());
                                    if (l != null && l.size() > 0) {
                                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                                        item.setInterceptmsg("有可借用SHIKOMI");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // bug13379将shikomi预占操作移出到公共操作中，不在shikomi放行时进行预占操作了
            // 预占shikomi
            if (StringUtils.isNotBlank(item.getShikomianswerno())
                    && !StringUtils.equals(RequestPurchaseStatusEnum.LANJIE, item.getStatecode())
                    && !StringUtils.equals(RequestPurchaseStatusEnum.SHIKOMILANJIE, item.getStatecode())) {
                if (StringUtils.isBlank(item.getInformation()) || (StringUtils.isNotBlank(item.getInformation())
                        && !item.getInformation().contains("SHIKOMI已预占"))) {
                    ShikomiPrepareDTO shikomiPrepareDTO = new ShikomiPrepareDTO();
                    if (item.getSplititemno() == null) {
                        shikomiPrepareDTO.setOrderNo(item.getOrderno() + "-" + item.getItemno().toString());
                    } else {
                        shikomiPrepareDTO.setOrderNo(item.getOrderno() + "-" + item.getItemno().toString() + "-"
                                + item.getSplititemno().toString());
                    }
                    shikomiPrepareDTO.setShikomiNo(item.getShikomianswerno());
                    shikomiPrepareDTO.setQuantity(item.getQuantity());
                    shikomiPrepareDTO.setSupplierCode(item.getSupplierid());
                    ResultVo<String> preresult = productServiceFeignApi.prepareShikomi(shikomiPrepareDTO);
                    if (!preresult.isSuccess()) {
                        item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                        item.setInterceptmsg("SHIKOMI预占失败");
                    } else {
                        // 避免放行时反复预占，预占一次后information中记录
                        if (StringUtils.isBlank(item.getInformation())) {
                            item.setInformation("SHIKOMI已预占;");
                        } else {
                            item.setInformation(item.getInformation() + ";SHIKOMI已预占;");
                        }
                    }
                }
            }

        }
        // bug11599将此操作后移，在SHIKOMI变更供应商后需要重新计算运输方式
        // 计算指定出库日及运输方式
        list = calDlvInfo(list);

        // bug10666马腾 先判断可用SHIKOMI 在判断是否拦截
        // 判断拦截,设置状态为拦截
        list = intercept(list);
        // bug14330 AP订单合并后数量也不能超过固定值
        Integer maxnum = 200;
        ResultVo<List<DataCodeVO>> result = dictDataServiceFeignApi.getDataCodes("3011");
        if (result.isSuccess() && !CollectionUtils.isEmpty(result.getData())
                && StringUtils.isNotBlank(result.getData().get(0).getExtNote1())) {
            maxnum = Integer.parseInt(result.getData().get(0).getExtNote1());
        }
        String smccode = null;
        Date hope = null;
        // bug 14940统一运输方式
        String transtype = null;
        for (OpsRequestpurchase item : list) {
            // 阀汇流板specmark！=0，则统一smccode及出荷日
            if (StringUtils.isBlank(item.getSpecmark()) || "0".equals(item.getSpecmark())) {
                smccode = null;
                hope = null;
                transtype = null;
            } else if ("1".equals(item.getSpecmark())) {
                smccode = item.getSmccode();
                hope = item.getHopeexportdate();
                transtype = item.getTranstype();
            } else if ("2".equals(item.getSpecmark())) {
                if (StringUtils.isNotBlank(smccode)) {
                    item.setSmccode(smccode);
                }
                if (hope != null) {
                    item.setHopeexportdate(hope);
                }
                if (StringUtils.isNotBlank(transtype)) {
                    item.setTranstype(transtype);
                }
            }
            // bug14330AP增加采购数量控制
            if (StringUtils.equals("AP", item.getSupplierid())) {
                if (item.getQuantity().compareTo(maxnum) > 0) {
                    item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                    if (StringUtils.isBlank(item.getInterceptmsg()))
                        item.setInterceptmsg("AP采购数量不可超过" + maxnum.toString());
                    else {
                        item.setInterceptmsg(item.getInterceptmsg() + ";AP采购数量不可超过" + maxnum.toString());
                    }
                }
            }

            // bug10579 马腾 判断是否为订单还原单，若为订单还原则拦截
            if (StringUtils.isNotBlank(item.getInformation()) && item.getInformation().contains("revertOrder;")) {
                //BUGID:17646 C14717 查询请购视图，对比请购单号 2025-05-22
                if (revertOrderIntercept(item)) {
                    if (!StringUtils.equals(RequestPurchaseStatusEnum.SHIKOMILANJIE, item.getStatecode())) {
                        item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                    }
                    if (StringUtils.isBlank(item.getInterceptmsg())) {
                        item.setInterceptmsg("订单还原拦截");
                    } else {
                        item.setInterceptmsg(item.getInterceptmsg() + ";订单还原拦截");
                    }
                }
            }
            // TODO bug15266 一个拆分子项拦截时，其余拆分子项也拦截
            if ((StringUtils.equals(RequestPurchaseStatusEnum.LANJIE, item.getStatecode())
                    || StringUtils.equals(RequestPurchaseStatusEnum.SHIKOMILANJIE, item.getStatecode()))
                    && item.getSplititemno() != null) {
                List<OpsRequestpurchase> ltemp = list.stream()
                        .filter(p -> StringUtils.equals(p.getOrderno(), item.getOrderno())
                                && p.getItemno().compareTo(item.getItemno()) == 0)
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(ltemp)) {
                    ltemp.forEach(a -> {
                        if (a.getSplititemno() != null && a.getSplititemno() != item.getSplititemno()) {
                            a.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                            if (StringUtils.isNotBlank(a.getInterceptmsg())) {
                                a.setInterceptmsg(a.getInterceptmsg() + ";其他子项");
                            } else {
                                a.setInterceptmsg("其他子项");
                            }
                        }
                    });
                }
            }

            /**
             * 获取可用的准备订单清单
             */
            CanUsePrepareOrderParam param = new CanUsePrepareOrderParam();
            if (StringUtils.isNotBlank(item.getCustomerno())) {
                param.setEndUserNo(item.getCustomerno());
            }
            if (StringUtils.isNotBlank(item.getUserno())) {
                param.setEndUserNo(item.getUserno());
            }

            // 非客户订单
            if (item.getOrdtype().equals(OrderTypeEnum.PRE) || item.getOrdtype().equals(OrderTypeEnum.WT) || item.getOrdtype().equals(OrderTypeEnum.BIN)) {
                OpsRequestpurchase opsRequestpurchase = commonMqpper.queryOpsRequestpurchase(item.getOrderno(), item.getItemno());
                if (opsRequestpurchase != null) {
                    param.setPpl(opsRequestpurchase.getPpl());
                    param.setPj(opsRequestpurchase.getProjectcode());
                    param.setGroupCustomerNo(opsRequestpurchase.getGroupcustomerno());
                }
            } else {
                // 查询ppl pj groupCustomerNo信息
                Rcvdetail rcvdetail = commonMqpper.queryRcvDetail(item.getOrderno(), item.getItemno());
                if (rcvdetail != null) {
                    param.setPpl(rcvdetail.getPplNo());
                    param.setPj(rcvdetail.getProjectNo());
                    param.setGroupCustomerNo(rcvdetail.getGroupCustomerNo());
                }
            }
            param.setQuantity(item.getQuantity());
            param.setModelNo(item.getModelno());
            ResultVo<OpsPrepareOrderVO> prepareOrderResultVo = prepareOrderService.getAvailablePrepareOrderList(param);
            if (prepareOrderResultVo.isSuccess() && prepareOrderResultVo.getData() != null) {
                item.setPrepareorderno(prepareOrderResultVo.getData().getOrderFno());
                // 更新准备订单预占
                PrepareOrderPreQtyUpdateDto updateDto = new PrepareOrderPreQtyUpdateDto();
                updateDto.setOrderFno(prepareOrderResultVo.getData().getOrderFno());
                updateDto.setPreQty(item.getQuantity());
                updateDto.setOldPreQty(prepareOrderResultVo.getData().getPreQty());
                prepareOrderService.updatePreQty(updateDto);
            }
            if (!prepareOrderResultVo.isSuccess()) {
                item.setStatecode(RequestPurchaseStatusEnum.SHIKOMILANJIE);
                if (StringUtils.isBlank(item.getInterceptmsg())) {
                    item.setInterceptmsg(prepareOrderResultVo.getMessage());
                } else {
                    item.setInterceptmsg(item.getInterceptmsg() + ";" + prepareOrderResultVo.getMessage());
                }
            }

            item.setUpdatetime(new Date());
            item.setPoOrderNo(null);
            item.setPoItemNo(null);
            item.setPoSplitNo(null);
            // 更新请购单数据及状态
            opsRequestpurchaseMapper.updateByPrimaryKey(item);
        }
        // bug15266 一个拆分子项拦截时，其余拆分子项也拦截,将此操作从上面的循环中单独提出来
        for (OpsRequestpurchase item : list) {
            if (StringUtils.equals(RequestPurchaseStatusEnum.LANJIE, item.getStatecode())
                    || StringUtils.equals(RequestPurchaseStatusEnum.SHIKOMILANJIE, item.getStatecode())) {
                // bug20647 C14717 20260413 子项采购拦截，同订单子项应全部拦截
                OpsRequestpurchase checkRe = opsRequestpurchaseMapper.selectByPrimaryKey(item.getId());
                if (!StringUtils.equals(item.getStatecode(), checkRe.getStatecode())) {
                    opsRequestpurchaseMapper.updateByPrimaryKey(item);
                }
                lanjie.add(item);
            } else {
                // bug14200增加非拦截请购单的处理事件
                purchaseEventPublisher.requestPurchaseOrderEvent(EventSourceEnum.REQUEST_PREPROCESS, item);
            }
        }

        opsWmDispatchForOrderFeignApi.interceptForRequestPo(lanjie);
        return list.size();

    }

    /**
     * bugid:17646 c14717 2025-05-22
     * 订单还原订单 是否拦截
     * 查询请购视图，如果集合数量大于1 则表名存在重复单号，设置订单欢迎拦截
     *
     * @param item
     * @return
     */
    public boolean revertOrderIntercept(OpsRequestpurchase item) {
        RequestpurchaseViewExample reqex = new RequestpurchaseViewExample();
        if (Objects.nonNull(item.getSplititemno())) {
            reqex.createCriteria()
                    .andOrdernoEqualTo(item.getOrderno())
                    .andItemnoEqualTo(item.getItemno())
                    .andSplititemnoEqualTo(item.getSplititemno());
        } else {
            reqex.createCriteria()
                    .andOrdernoEqualTo(item.getOrderno())
                    .andItemnoEqualTo(item.getItemno());
        }
        List<RequestpurchaseView> relist = requestpurchaseViewMapper.selectByExample(reqex);
        return relist.size() > 1;
    }

    // 拦截判断
    @Override
    public List<OpsRequestpurchase> intercept(List<OpsRequestpurchase> list) {
        Map<String, Boolean> modelNoList = new HashMap<String, Boolean>();
        Map<String, List<ProductPrice>> priceMap = new HashMap<String, List<ProductPrice>>();
        Map<String, Boolean> errorMap = new HashMap<String, Boolean>();
        // Map<String, ProductEos> eosMap = new HashMap<String, ProductEos>();
        Map<String, Boolean> restrictModelNoList = new HashMap<String, Boolean>();
        Map<String, Map<String, Boolean>> restricWhiteMap = new HashMap<String, Map<String, Boolean>>();
        Map<String, List<String>> customerRestrictModelList = new HashMap<String, List<String>>();
        Map<String, Product> productMap = new HashMap<String, Product>();
        // bug15266 增加未登录型号是否拦截开关
        boolean modelIntercept = true;
        boolean minProductOn = false;
        ResultVo<List<DataCodeVO>> intertemp = dictDataServiceFeignApi.getDataCodes("3013");
        if (intertemp.isSuccess() && !CollectionUtils.isEmpty(intertemp.getData())) {
            for (DataCodeVO a : intertemp.getData()) {
                if (StringUtils.equals(a.getCode(), "modelIntercept") && StringUtils.isNotBlank(a.getExtNote1())) {
                    modelIntercept = Boolean.parseBoolean(a.getExtNote1());
                }
                if (StringUtils.equals(a.getCode(), "productOn") && StringUtils.isNotBlank(a.getExtNote1())) {
                    minProductOn = Boolean.parseBoolean(a.getExtNote1());
                }
            }
        }
        // bug15266增加未登录型号记录，完毕后将此批未登录型号发邮件
        Map<String, List<OpsRequestpurchase>> unKnownModel = new HashMap<String, List<OpsRequestpurchase>>();
        for (OpsRequestpurchase item : list) {

            if (StringUtils.isNotBlank(item.getModelno()) && !modelNoList.containsKey(item.getModelno())) {
                modelNoList.put(item.getModelno(), true);
                // 获取产品信息
                CommonResult<Product> producttemp = opsProductFeignApi.searchProduct(item.getModelno());
                if (producttemp.isSuccess()) {
                    if (producttemp.getData() != null && !producttemp.getData().getIsDeleted()) {
                        productMap.put(item.getModelno(), producttemp.getData());
                    }
                }

                // 获取多段价格
                CommonResult<List<ProductPrice>> pricetemp = opsProductFeignApi
                        .findProductPriceByModelNo(item.getModelno());
                if (pricetemp.isSuccess()) {
                    if (pricetemp.getData() != null) {
                        priceMap.put(item.getModelno(), pricetemp.getData());
                    }
                }

                // 获取错误型号
                CommonResult<ProductError> errortemp = opsProductFeignApi.findProductErrorByModelNo(item.getModelno());
                if (errortemp.isSuccess()) {
                    errorMap.put(item.getModelno(), true);
                }

                // 获取贩卖限制
                CommonResult<String> restemp = opsProductFeignApi
                        .findProductRestrictModelNoByModelNo(item.getModelno());
                if (restemp.isSuccess()) {
                    restrictModelNoList.put(item.getModelno(), true);
                }

            }
            //bug21053 将贩卖限制品白名单查询移到型号去重判断if之外,并使用最终用户enduser
            if (!customerRestrictModelList.containsKey(item.getEndUser())) {
                CommonResult<List<String>> customertemp = opsProductFeignApi
                        .findProductRestrictCustomerWhiteListByCustomerNo(item.getEndUser());
                if (customertemp.isSuccess()) {
                    customerRestrictModelList.put(item.getEndUser(), customertemp.getData());
                }
            }
        }

        // 获取贩卖限制
        if (!restrictModelNoList.isEmpty()) {
            // 获取白名单
            if (!customerRestrictModelList.isEmpty()) {
                customerRestrictModelList.keySet().forEach(i -> {
                    Map<String, Boolean> t = new HashMap<String, Boolean>();
                    customerRestrictModelList.get(i).forEach(j -> {
                        t.put(j, true);
                    });
                    restricWhiteMap.put(i, t);
                });
            }
        }

        // 获取特殊拦截清单、获取不允许客户采购产品
        OpsRequestpurchaseInterceptConfigExample exam = new OpsRequestpurchaseInterceptConfigExample();
        exam.createCriteria().andEnableEqualTo(true);
        // bug10329 马腾 增加enable标识查询
        List<OpsRequestpurchaseInterceptConfig> interceptConfig = opsRequestpurchaseInterceptConfigMapper
                .selectByExample(exam);
        // bug15403 业务拦截清单，同一型号多条数据时，拦截原因全部显示
        List<OpsRequestpurchaseInterceptConfig> modelFuzzyInter = new ArrayList<OpsRequestpurchaseInterceptConfig>();
        Map<String, List<OpsRequestpurchaseInterceptConfig>> modelCompleteInterMap = new HashMap<String, List<OpsRequestpurchaseInterceptConfig>>();
        Map<String, List<OpsRequestpurchaseInterceptConfig>> customerInterMap = new HashMap<String, List<OpsRequestpurchaseInterceptConfig>>();
        //bugid:17646 c14717 2025-05-22 自定义拦截 自定义拦截typeid=2
        Map<String, List<OpsRequestpurchaseInterceptConfig>> customizeInterMap = new HashMap<String, List<OpsRequestpurchaseInterceptConfig>>();
        if (interceptConfig != null && !interceptConfig.isEmpty()) {
            interceptConfig.forEach(a -> {
                if (StringUtils.equals("0", a.getTypeid())) {
                    if (a.getRulekey().contains("*") || a.getRulekey().contains("+")) {
                        modelFuzzyInter.add(a);
                    } else {
                        if (modelCompleteInterMap.containsKey(a.getRulekey())) {
                            modelCompleteInterMap.get(a.getRulekey()).add(a);
                        } else {
                            List<OpsRequestpurchaseInterceptConfig> temp = new ArrayList<OpsRequestpurchaseInterceptConfig>();
                            temp.add(a);
                            modelCompleteInterMap.put(a.getRulekey(), temp);
                        }
                    }
                } else if (StringUtils.equals("1", a.getTypeid())) {
                    if (customerInterMap.containsKey(a.getRulekey())) {
                        customerInterMap.get(a.getRulekey()).add(a);
                    } else {
                        List<OpsRequestpurchaseInterceptConfig> temp = new ArrayList<OpsRequestpurchaseInterceptConfig>();
                        temp.add(a);
                        customerInterMap.put(a.getRulekey(), temp);
                    }

                } else if (StringUtils.equals("2", a.getTypeid())) {//bugid:17646 c14717 2025-05-22 自定义拦截
                    if (customizeInterMap.containsKey(a.getRulekey())) {
                        customizeInterMap.get(a.getRulekey()).add(a);
                    } else {
                        List<OpsRequestpurchaseInterceptConfig> temp = new ArrayList<OpsRequestpurchaseInterceptConfig>();
                        temp.add(a);
                        customizeInterMap.put(a.getRulekey(), temp);
                    }
                }
            });
        }

        for (OpsRequestpurchase item : list) {
            // SHIKOMI拦截
            if (StringUtils.equals(RequestPurchaseStatusEnum.SHIKOMILANJIE, item.getStatecode())) {
                if (!productMap.containsKey(item.getModelno()) || !priceMap.containsKey(item.getModelno())) {
                    if (unKnownModel.containsKey(item.getModelno())) {
                        unKnownModel.get(item.getModelno()).add(item);
                    } else {
                        List<OpsRequestpurchase> t = new ArrayList<OpsRequestpurchase>();
                        t.add(item);
                        unKnownModel.put(item.getModelno(), t);
                    }
                }
                continue;
            }
            // BIN补库不判断拦截
            if (OrderTypeEnum.BIN.equals(item.getOrdtype())) {
                if (!productMap.containsKey(item.getModelno()) || !priceMap.containsKey(item.getModelno())) {
                    if (unKnownModel.containsKey(item.getModelno())) {
                        unKnownModel.get(item.getModelno()).add(item);
                    } else {
                        List<OpsRequestpurchase> t = new ArrayList<OpsRequestpurchase>();
                        t.add(item);
                        unKnownModel.put(item.getModelno(), t);
                    }
                }
                continue;
            }
            // 阀汇流板型号争取不发日本，需人工判断选择放行
            if (!StringUtils.equals(item.getSpecmark(), "0")) {
                item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                    item.setInterceptmsg(item.getInterceptmsg() + ";阀汇流板型号");
                } else
                    item.setInterceptmsg("阀汇流板型号");
            }
            // 最小包装单位、偶数订货
            Integer minQuantityProduct = null;
            if (productMap.containsKey(item.getModelno())) {
                // bug14954 更换最小起订量字段
                minQuantityProduct = productMap.get(item.getModelno()).getMinQuantity();
                // 超长超宽属性
                item.setNonstandardSizedProduct(productMap.get(item.getModelno()).getNonstandardSizedProduct());
                // 偶数订货
                if (StringUtils.isNotBlank(productMap.get(item.getModelno()).getIsEven())
                        && StringUtils.equals("1", productMap.get(item.getModelno()).getIsEven())) {
                    item.setIseven(true);
                }
                if (productMap.get(item.getModelno()).getMinPackUnit() != null
                        && productMap.get(item.getModelno()).getMinPackUnit() > 0) {
                    if (item.getQuantity() % productMap.get(item.getModelno()).getMinPackUnit() != 0) {
                        // 根据bin品判断是否拦截
                        ResultVo<Boolean> isBin = binServiceFeignApi.isBinModel(item.getModelno());
                        if (isBin.isSuccess() && !isBin.getData()) {
                            item.setMinpackunit(productMap.get(item.getModelno()).getMinPackUnit());
                            item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                            if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                                item.setInterceptmsg(item.getInterceptmsg() + ";不满足最小包装单位");
                            } else
                                item.setInterceptmsg("不满足最小包装单位");
                        } else if (isBin.isSuccess() && isBin.getData()) {
                            item.setMinpackunit(productMap.get(item.getModelno()).getMinPackUnit());
                        }
                    }
                }
                // bug11311MDM上线后变更为使用product中的is_eos进行判断是否收敛
                if (productMap.get(item.getModelno()).getIsEos() != null
                        && productMap.get(item.getModelno()).getIsEos()) {
                    if (StringUtils.isBlank(item.getShikomianswerno())) {
                        // bug10911 增加工厂供应商库存判断
                        if (item.getSupplierinventory() == null || item.getSupplierinventory() < item.getQuantity()) {
                            item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                            if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                                item.setInterceptmsg(item.getInterceptmsg() + ";已达接单停止日的收敛品");
                            } else {
                                item.setInterceptmsg("已达接单停止日的收敛品");
                            }
                        }
                    }
                }
            } else {
                // bug15266 增加判断是否拦截未登录型号
                if (modelIntercept) {
                    item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                    if (StringUtils.isNotBlank(item.getInterceptmsg())
                            && !item.getInterceptmsg().contains("获取型号产品信息失败")) {
                        item.setInterceptmsg(item.getInterceptmsg() + ";获取型号产品信息失败");
                    } else
                        item.setInterceptmsg("获取型号产品信息失败");
                }
                if (unKnownModel.containsKey(item.getModelno())) {
                    unKnownModel.get(item.getModelno()).add(item);
                } else {
                    List<OpsRequestpurchase> t = new ArrayList<OpsRequestpurchase>();
                    t.add(item);
                    unKnownModel.put(item.getModelno(), t);
                }
            }

            // 无shikomi时，判断多段价格，下一段价格低于此段则提示并拦截;判断未登录型号
            if (priceMap.containsKey(item.getModelno())) {
                // bug14477 重新计算时删除未登录型号拦截原因,避免重复计算
                if (StringUtils.isNotBlank(item.getInterceptmsg()) && item.getInterceptmsg().contains("未登录型号")) {
                    item.setInterceptmsg(item.getInterceptmsg().replace("未登录型号", ""));
                }
                // 升序排序
                List<ProductPrice> temp = priceMap.get(item.getModelno()).stream()
                        .sorted(Comparator.comparing(ProductPrice::getMinquantity)).collect(Collectors.toList());
                if (temp.size() > 1) {
                    item.setIslot(true);
                    // BigDecimal low = null;
                    BigDecimal eprice = null;
                    for (ProductPrice i : temp) {
                        if (item.getQuantity() >= i.getMinquantity()) {
                            // low = i.getLowestprice();
                            eprice = i.getEprice();
                            // bug10741 马腾 变更下两字段的赋值时刻
                            item.setImportfobpriceoriginal(i.getImportfobpriceoriginal());
                            item.setImportcurrencyid(i.getImportcurrencyid());
                        }
                        if (StringUtils.isBlank(item.getShikomianswerno())) {
                            if (item.getQuantity() < i.getMinquantity()) {
                                // bug15266采购不经济变更为差额为不小于2000
                                if (eprice != null && (i.getEprice().multiply(new BigDecimal(i.getMinquantity())))
                                        .add(new BigDecimal(2000))
                                        .compareTo(eprice.multiply(new BigDecimal(item.getQuantity()))) <= 0) {
                                    item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                                    if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                                        item.setInterceptmsg(item.getInterceptmsg() + ";采购不经济");
                                    } else
                                        item.setInterceptmsg("采购不经济");
                                    break;
                                }
                            }
                        }
                    }
                    // bugid:17864 eprice如果为空不更新
                    if (Objects.nonNull(eprice)) {
                        item.setStdprice(eprice);
                    }
                    // bug14954 切换最小起订量字段
                    int min = temp.stream().min(Comparator.comparing(ProductPrice::getMinquantity)).get()
                            .getMinquantity();
                    if (minProductOn) {
                        if (minQuantityProduct == null) {
                            // 若字段无值
                            min = 1;
                        } else {
                            min = minQuantityProduct;
                        }
                    }

                    if (StringUtils.isBlank(item.getShikomianswerno())) {
                        if (min > item.getQuantity()) {
                            item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                            if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                                item.setInterceptmsg(
                                        item.getInterceptmsg() + ";不满足最小订货数量" + min);
                            } else
                                item.setInterceptmsg("不满足最小订货数量" + min);
                        }
                    }
                } else {
                    int min = temp.get(0).getMinquantity();
                    if (temp.get(0) != null) {
                        item.setStdprice(temp.get(0).getEprice());
                        item.setImportfobpriceoriginal(temp.get(0).getImportfobpriceoriginal());
                        item.setImportcurrencyid(temp.get(0).getImportcurrencyid());
                        if (StringUtils.isBlank(item.getShikomianswerno())) {
                            if (minProductOn) {
                                if (minQuantityProduct == null) {
                                    // 若字段无值
                                    min = 1;
                                } else {
                                    min = minQuantityProduct;
                                }
                            }
                            if (min > item.getQuantity()) {
                                item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                                if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                                    item.setInterceptmsg(
                                            item.getInterceptmsg() + ";不满足最小订货数量" + min);
                                } else
                                    item.setInterceptmsg("不满足最小订货数量" + min);
                            }
                        }
                    }
                }
            } else {
                // bug15266 增加判断是否拦截未登录型号
                if (modelIntercept) {
                    item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                    if (StringUtils.isBlank(item.getInterceptmsg()))
                        item.setInterceptmsg("未登录型号");
                    else
                        item.setInterceptmsg(item.getInterceptmsg() + ";未登录型号");
                }
                if (unKnownModel.containsKey(item.getModelno())) {
                    unKnownModel.get(item.getModelno()).add(item);
                } else {
                    List<OpsRequestpurchase> t = new ArrayList<OpsRequestpurchase>();
                    t.add(item);
                    unKnownModel.put(item.getModelno(), t);
                }
            }
            if (errorMap.size() > 0) {
                if (errorMap.containsKey(item.getModelno())) {
                    item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                    if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                        item.setInterceptmsg(item.getInterceptmsg() + ";错误型号");
                    } else {
                        item.setInterceptmsg("错误型号");
                    }
                }
            }

            int res = 0;
            if (!restrictModelNoList.isEmpty()) {
                if (restrictModelNoList.containsKey(item.getModelno())) {
                    if (!restricWhiteMap.isEmpty()) {
                        String customerNo = item.getCustomerno();
                        if (StringUtils.isNotBlank(item.getUserno()))
                            customerNo = item.getUserno();
                        // 客户及用户代码均为空时不拦截贩卖限制品
                        if (StringUtils.isNotBlank(customerNo))
                            if (restricWhiteMap.containsKey(customerNo)) {
                                if (!restricWhiteMap.get(customerNo).containsKey(item.getModelno())) {
                                    res = 1;
                                }
                            } else {
                                res = 1;
                            }
                    } else {
                        res = 1;
                    }
                }
            }
            if (res == 1) {
                item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                    item.setInterceptmsg(item.getInterceptmsg() + ";贩卖限制品");
                } else {
                    item.setInterceptmsg("贩卖限制品");
                }
            }

            // bug15403 业务拦截清单，同一型号多条数据时，拦截原因全部显示
            if (CollectionUtils.isNotEmpty(modelFuzzyInter)) {
                modelFuzzyInter.forEach(a -> {
                    // 正则匹配
                    if (Pattern.matches(a.getRulekey(), item.getModelno())) {
                        setInterceptInfo(item, a);
                    }
                });
            }
            if (!modelCompleteInterMap.isEmpty()) {
                // 完全匹配
                if (modelCompleteInterMap.containsKey(item.getModelno())) {
                    modelCompleteInterMap.get(item.getModelno()).forEach(a -> {
                        setInterceptInfo(item, a);
                    });
                }
            }
            if (!customerInterMap.isEmpty()) {
                // 完全匹配
                if (customerInterMap.containsKey(item.getCustomerno())) {
                    customerInterMap.get(item.getCustomerno()).forEach(a -> {
                        setInterceptInfo(item, a);
                    });
                }
            }
            ////bugid:17646 c14717 2025-05-22 自定义拦截
            if (!customizeInterMap.isEmpty()) {
                if (customizeInterMap.containsKey(item.getModelno())) {
                    customizeInterMap.get(item.getModelno()).forEach(a -> {
                        //仅型号
                        if (StringUtils.isBlank(a.getRuleKey1()) && Objects.isNull(a.getRuleKey2())) {
                            setInterceptInfo(item, a);
                        }
                        // 型号+客户
                        if (StringUtils.isNotBlank(a.getRuleKey1())
                                && a.getRuleKey1().equals(item.getCustomerno())
                                && Objects.isNull(a.getRuleKey2())) {
                            setInterceptInfo(item, a);
                        }
                        // 型号+数量
                        if (StringUtils.isBlank(a.getRuleKey1())
                                && Objects.nonNull(a.getRuleKey2())
                                && item.getQuantity() >= a.getRuleKey2()) {
                            setInterceptInfo(item, a);
                        }
                        // 型号+客户+数量
                        if (StringUtils.isNotBlank(a.getRuleKey1())
                                && a.getRuleKey1().equals(item.getCustomerno())
                                && Objects.nonNull(a.getRuleKey2())
                                && item.getQuantity() >= a.getRuleKey2()) {
                            setInterceptInfo(item, a);
                        }
                    });
                }
            }

            /**
             * bug 18050 增加请购拦截判断，当客户代码为C1D72的国内集团采购订单，供应商为CN、CM、CT、YZ、TZ、GZ的则进行拦截，拦截原因“广州制造不允许订货中国境内工厂”
             */
            if (StringUtils.isNotBlank(item.getCustomerno()) && StringUtils.isNotBlank(item.getSupplierid()) && StringUtils.isNotBlank(item.getOrdtype())) {
                SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                // 从字典里获取中国内部工厂
                ResultVo<List<DataCodeVO>> dictList = dictDataServiceFeignApi.getDataCodes("6005");
                if (dictList != null && dictList.isSuccess()) {
                    List<DataCodeVO> data = dictList.getData();
                    List<String> valList = data.stream()
                            .map(DataCodeVO::getCode)
                            .collect(Collectors.toList());

                    if (StringUtils.equals(item.getCustomerno(), "C1D72") && valList.contains(item.getSupplierid()) && "11".equals(item.getOrdtype())) {
                        item.setInterceptmsg("广州制造不允许订货中国境内工厂");
                        item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                    }

                }
            }

            //bugid:19127 风险在库拦截
            Integer invRiskQty = inventoryRiskDao.findInvRiskByModelNOGetInvQty(item.getModelno());
            if (Objects.nonNull(invRiskQty) && invRiskQty > 0) {
                if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                    item.setInterceptmsg(item.getInterceptmsg() + ";存在风险在库");
                } else {
                    item.setInterceptmsg("存在风险在库");
                }
                item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
            }

        }
        // bugid:17646 c14717 20250731
        if (!unKnownModel.isEmpty()) {
            mdmService.sendMdmData(unKnownModel);
//			purchaseMailService.sendUnKnownModel(unKnownModel);
        }
        return list;
    }

    public void setInterceptInfo(OpsRequestpurchase item, OpsRequestpurchaseInterceptConfig config) {
        item.setStatecode(config.getDefaultaction());
        if (StringUtils.isNotBlank(item.getInterceptmsg())) {
            item.setInterceptmsg(item.getInterceptmsg() + ";" + config.getReason());
        } else {
            item.setInterceptmsg(config.getReason());
        }
        setPoJsonInfoInterceptConfigId(item, config.getId());
    }

    public void setPoJsonInfoInterceptConfigId(OpsRequestpurchase item, Integer interceptConfigId) {
        if (StringUtils.isNotBlank(item.getInfojson())) {
            RequestPurchaseInfo info = JSONObject.parseObject(item.getInfojson(), RequestPurchaseInfo.class);
            if (CollectionUtils.isEmpty(info.getInterceptConfigIdList())) {
                List<Integer> interceptConfigIdList = new ArrayList<>();
                interceptConfigIdList.add(interceptConfigId);
                info.setInterceptConfigIdList(interceptConfigIdList);
                item.setInfojson(JSON.toJSONString(info));
            } else {
                info.getInterceptConfigIdList().add(interceptConfigId);
                item.setInfojson(JSON.toJSONString(info));
            }
        } else {
            RequestPurchaseInfo info = new RequestPurchaseInfo();
            List<Integer> interceptConfigIdList = new ArrayList<>();
            interceptConfigIdList.add(interceptConfigId);
            info.setInterceptConfigIdList(interceptConfigIdList);
            item.setInfojson(JSON.toJSONString(info));
        }
    }

    public List<OpsRequestpurchase> calSupplierInfo(List<OpsRequestpurchase> list) {
        list.forEach(item -> {
            // if (StringUtils.equals(RequestPurchaseStatusEnum.CHULIZHONG,
            // item.getStatecode())) {
            String modelNo = item.getModelno();
            if ((PurchaseTypeEnum.SALE.getTypeCode().equals(item.getPurchasetype())
                    || PurchaseTypeEnum.PRE.getTypeCode().equals(item.getPurchasetype()))
                    && StringUtils.isBlank(item.getSupplierid())) {
                // 销售订单,多供应商规则
                // bug10617 马腾 使用请购仓替代部门来筛选采购仓
                List<RequestPurchaseSupplierInfo> infos = requestPurchaseDao.getByModelAndRequest(modelNo,
                        item.getRequestwarehouseid());
                if (infos != null && infos.size() > 0) {
                    // bug14263将供应商字段转为大写
                    infos.forEach(a -> {
                        a.setSupplyId(a.getSupplyId().trim().toUpperCase());
                    });
                    // 是否为GZ或TZ单供应商产品，若是则直接订到单供应商
                    int only = 0;
                    List<String> supplierlist = infos.stream().map(RequestPurchaseSupplierInfo::getSupplyId).distinct()
                            .collect(Collectors.toList());
                    if (supplierlist != null && supplierlist.size() == 1) {
                        if (StringUtils.equals("GZ", supplierlist.get(0))
                                || StringUtils.equals("TZ", supplierlist.get(0)))
                            only = 1;
                    }

                    List<String> warehouse = infos.stream().map(RequestPurchaseSupplierInfo::getWarehouseCode)
                            .distinct().collect(Collectors.toList());
                    int ok = 0;
                    for (String w : warehouse) {
                        if (ok == 1) {
                            break;
                        }
                        List<RequestPurchaseSupplierInfo> su = infos.stream()
                                .filter(a -> a.getWarehouseCode().equals(w))
                                .sorted(Comparator.comparing(RequestPurchaseSupplierInfo::getSupplierPriority))
                                .collect(Collectors.toList());
                        for (RequestPurchaseSupplierInfo i : su) {
                            // 根据产品类别判断此仓库是否可用
                            List<OpsPoDestinationConfig> smccodelist = requestPurchaseDao.getSmccode(i.getSupplyId(),
                                    i.getWarehouseCode(), item.getProducttype(), item.getCustomerno(),
                                    item.getPurchasetype(), item.getOrdtype(),
                                    item.getSplititemno() == null ? "0" : "1");
                            String smccode = calSMCCode(smccodelist, item);

                            if (only == 1) {
                                item.setSupplierid(i.getSupplyId());
                                item.setPurchasewarehouseid(i.getWarehouseCode());
                                item.setSmccode(smccode);
                                item.setSupplierpartno(i.getSupplierPartNo());
                                ok = 1;
                                break;
                            }
                            // 是否满足生产最大量限制
                            if (i.getEnableMaxProdQty()) {
                                if (i.getMaxProdQty() != null && i.getMaxProdQty() > 0) {
                                    if (item.getQuantity() > i.getMaxProdQty()) {
                                        continue;
                                    }
                                }
                            }

                            // 是否满足匹配条件
                            if (StringUtils.isNotBlank(i.getMatchPattern())) {
                                RequestPurchaseSupplierCondition condition = new RequestPurchaseSupplierCondition();
                                if (item.getHopedeliverydate() != null) {
                                    condition.setHopedeliverydate(item.getHopedeliverydate());
                                }
                                condition.setQuantity(item.getQuantity());
                                // 创建script引擎
                                ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                                Bindings bindings = new SimpleBindings();
                                Map<String, Object> params;
                                try {
                                    params = PropertyUtils.describe(condition);
                                    bindings.putAll(params);
                                    boolean ret = (Boolean) engine.eval(i.getMatchPattern(), bindings);
                                    if (ret) {
                                        // 判断交货期天数，工作日
                                        if (i.getDay() != null) {
                                            Integer day = subWorkday(item.getHopedeliverydate(), item.getSupplierid());
                                            if (day <= i.getDay()) {
                                                item.setSupplierid(i.getSupplyId());
                                                item.setPurchasewarehouseid(i.getWarehouseCode());
                                                item.setSmccode(smccode);
                                                item.setSupplierpartno(i.getSupplierPartNo());
                                                ok = 1;
                                                break;
                                            }
                                        } else {
                                            item.setSupplierid(i.getSupplyId());
                                            item.setPurchasewarehouseid(i.getWarehouseCode());
                                            item.setSmccode(smccode);
                                            item.setSupplierpartno(i.getSupplierPartNo());
                                            ok = 1;
                                            break;
                                        }

                                    }
                                } catch (Exception e) {
                                    logger.error("" + e);
                                }

                            } else {
                                item.setSupplierid(i.getSupplyId());
                                item.setPurchasewarehouseid(i.getWarehouseCode());
                                item.setSmccode(smccode);
                                item.setSupplierpartno(i.getSupplierPartNo());
                                ok = 1;
                                break;
                            }
                        }
                    }
                }
            } else if (StringUtils.isBlank(item.getSupplierid())) {
                // 主供应商
                ProductDeliveryExample example = new ProductDeliveryExample();
                example.createCriteria().andModelnoEqualTo(modelNo).andIsprimaryEqualTo(true)
                        .andIsDeletedEqualTo(false);
                List<ProductDelivery> delivery = productDeliveryMapper.selectByExample(example);
                if (delivery != null && delivery.size() == 1) {
                    item.setSupplierid(delivery.get(0).getSupplyid());
                    item.setSupplierpartno(delivery.get(0).getSupplierpartno());
                }
            }
            // }
            item = calWarehouseAndSmccode(item, false);
        });
        return list;
    }

    @Override
    public OpsRequestpurchase calWarehouseAndSmccode(OpsRequestpurchase item, boolean isEdit) {
        // 补充采购仓库
        if (StringUtils.isBlank(item.getPurchasewarehouseid())) {
            // 销售订单使用shikomi供应商作为订单供应商的情况或前端修改供应商
            // bug10617 马腾 使用请购仓替代部门来筛选采购仓
            List<RequestPurchaseSupplierInfo> infos = requestPurchaseDao.getBySupplierAndRequest(item.getSupplierid(),
                    item.getRequestwarehouseid());
            if (infos != null && infos.size() > 0) {
                for (RequestPurchaseSupplierInfo i : infos) {
                    // 根据产品类别判断此仓库是否可用
                    List<OpsPoDestinationConfig> smccodelist = requestPurchaseDao.getSmccode(i.getSupplyId(),
                            i.getWarehouseCode(), item.getProducttype(), item.getCustomerno(), item.getPurchasetype(),
                            item.getOrdtype(), item.getSplititemno() == null ? "0" : "1");
                    String smccode = calSMCCode(smccodelist, item);

                    // 是否满足匹配条件
                    if (StringUtils.isNotBlank(i.getMatchPattern()) && !isEdit) {
                        RequestPurchaseSupplierCondition condition = new RequestPurchaseSupplierCondition();
                        // condition.setHopedeliverydate(item.getHopedeliverydate());
                        if (item.getHopedeliverydate() != null) {
                            condition.setHopedeliverydate(item.getHopedeliverydate());
                        }
                        condition.setQuantity(item.getQuantity());
                        // 创建script引擎
                        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                        Bindings bindings = new SimpleBindings();
                        Map<String, Object> params;
                        try {
                            params = PropertyUtils.describe(condition);
                            bindings.putAll(params);
                            boolean ret = (Boolean) engine.eval(i.getMatchPattern(), bindings);
                            if (ret) {
                                // 判断交货期天数，工作日
                                if (i.getDay() != null) {
                                    Integer day = subWorkday(item.getHopedeliverydate(), item.getSupplierid());
                                    if (i.getDay() >= day) {
                                        item.setPurchasewarehouseid(i.getWarehouseCode());
                                        item.setSmccode(smccode);
                                        break;
                                    }
                                } else {
                                    item.setPurchasewarehouseid(i.getWarehouseCode());
                                    item.setSmccode(smccode);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            logger.error("", e);
                        }

                    } else {
                        item.setPurchasewarehouseid(i.getWarehouseCode());
                        item.setSmccode(smccode);
                        break;
                    }
                }
            } else {
                // 若转订到需限制采购仓的，且为计算得到采购仓，则直接设定采购仓
                if (StringUtils.equals("GZ", item.getSupplierid())) {
                    item.setPurchasewarehouseid("KGZ");
                } else if (StringUtils.equals("TZ", item.getSupplierid())) {
                    item.setPurchasewarehouseid("KSH");
                }
            }
        }
        if (item.getSmccode() == null && item.getSupplierid() != null && item.getPurchasewarehouseid() != null) {
            List<OpsPoDestinationConfig> smccodelist = requestPurchaseDao.getSmccode(item.getSupplierid(),
                    item.getPurchasewarehouseid(), item.getProducttype(), item.getCustomerno(), item.getPurchasetype(),
                    item.getOrdtype(), item.getSplititemno() == null ? "0" : "1");
            String smccode = calSMCCode(smccodelist, item);
            if (StringUtils.isNotBlank(smccode)) {
                item.setSmccode(smccode);
            } else {
                if (PurchaseTypeEnum.BIN.getTypeCode().equals(item.getPurchasetype())) {
                    Map<String, Boolean> r = notDefaultSMCcode();
                    if (r.containsKey(item.getSupplierid())) {
                        item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                        if (StringUtils.isNotBlank(item.getInterceptmsg())) {
                            item.setInterceptmsg(item.getInterceptmsg() + ";无对应smccode");
                        } else
                            item.setInterceptmsg("无对应smccode");
                    }
                }
            }
        }
        return item;
    }

    // 出荷日、运输方式
    @Override
    public List<OpsRequestpurchase> calDlvInfo(List<OpsRequestpurchase> list) {
        return transService.calDlvInfo(list, null);
    }


    // 计算参数日期与今天相差几个工作日
    // bug 10513 马腾 判断日本供应商时使用日本工作日字段
    public Integer subWorkday(Date day, String supplierId) {
        LocalDate today = new LocalDate(new Date());
        TblWorkdayYearExample ex = new TblWorkdayYearExample();
        // 增加了时间修改
        if ("JP".equals(supplierId)) {
            ex.createCriteria().andWorkdayDateGreaterThanOrEqualTo(today.toDate()).andOptflagJpEqualTo("0")
                    .andWorkdayDateLessThan(day);
        } else {
            ex.createCriteria().andWorkdayDateGreaterThanOrEqualTo(today.toDate()).andOptflagEqualTo("0")
                    .andWorkdayDateLessThan(day);
        }
        ex.setOrderByClause("Workday_date");
        List<TblWorkdayYear> temp = tblWorkdayYearMapper.selectByExample(ex);
        if (temp != null) {
            return temp.size();
        }
        return 100;
    }

    public String calSMCCode(List<OpsPoDestinationConfig> smccodelist, OpsRequestpurchase item) {
        String smccode = null;
        if (smccodelist != null && smccodelist.size() > 1) {
            List<OpsPoDestinationConfig> temp = smccodelist.stream()
                    .filter(a -> StringUtils.equals(a.getOrdertype(), item.getOrdtype())).collect(Collectors.toList());
            if (temp != null && temp.size() > 0) {
                if (temp.size() == 1) {
                    smccode = temp.get(0).getSmccode();
                } else {
                    temp = temp.stream().filter(a -> StringUtils.equals(a.getCustomerno(), item.getCustomerno()))
                            .collect(Collectors.toList());
                    if (temp != null && temp.size() > 0) {
                        if (temp.size() == 1) {
                            smccode = temp.get(0).getSmccode();
                        } else {
                            for (OpsPoDestinationConfig c : temp) {
                                if (c.getProducttype() == item.getProducttype()) {
                                    smccode = c.getSmccode();
                                }
                            }
                            if (smccode == null)
                                smccode = temp.get(0).getSmccode();
                        }
                    } else {
                        temp = smccodelist.stream().filter(a -> StringUtils.equals(a.getOrdertype(), item.getOrdtype())
                                && a.getProducttype() == item.getProducttype()).collect(Collectors.toList());
                        if (temp != null && temp.size() > 0) {
                            smccode = temp.get(0).getSmccode();
                        } else {
                            smccode = smccodelist.stream()
                                    .filter(a -> StringUtils.equals(a.getOrdertype(), item.getOrdtype()))
                                    .collect(Collectors.toList()).get(0).getSmccode();
                        }
                    }
                }
            } else {
                temp = smccodelist.stream().filter(a -> StringUtils.equals(a.getCustomerno(), item.getCustomerno()))
                        .collect(Collectors.toList());
                if (temp != null && temp.size() > 0) {
                    if (temp.size() == 1) {
                        smccode = temp.get(0).getSmccode();
                    } else {
                        for (OpsPoDestinationConfig c : temp) {
                            if (c.getProducttype() == item.getProducttype()) {
                                smccode = c.getSmccode();
                            }
                        }
                        if (smccode == null)
                            smccode = temp.get(0).getSmccode();
                    }
                } else {
                    temp = smccodelist.stream().filter(a -> a.getProducttype() == item.getProducttype())
                            .collect(Collectors.toList());
                    if (temp != null && temp.size() > 0) {
                        smccode = temp.get(0).getSmccode();
                    } else {
                        smccode = smccodelist.get(0).getSmccode();
                    }
                }
            }
        } else if (smccodelist != null && smccodelist.size() == 1) {
            smccode = smccodelist.get(0).getSmccode();
        }
        return smccode;
    }

    public Map<String, Boolean> notDefaultSMCcode() {
        Map<String, Boolean> r = new HashMap<String, Boolean>();
        r.put("JP", true);
        r.put("CN", true);
        r.put("CM", true);
        r.put("CT", true);
        r.put("YZ", true);
        r.put("TZ", true);
        r.put("GZ", true);
        return r;
    }

}
