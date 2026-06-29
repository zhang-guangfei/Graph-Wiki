package com.smc.ops.delivery.service.barcode.impl;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.expdetail.DataCodeDto;
import com.sales.ops.dto.expdetail.ExpdetailDto;
import com.sales.ops.dto.expdetail.SampleOrderApplyDto;
import com.sales.ops.dto.expdetail.SampleOrderlogDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.ExpdetailSampleBalEnum;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.SendStatusEnum;
import com.sales.ops.enums.TradeCompanyIdEnum;
import com.smc.ops.delivery.mapper.*;
import com.smc.ops.delivery.mapper.samplebal.OpsSampleBalMapper;
import com.smc.ops.delivery.mapper.samplebal.SampleBalPropertyAssignDao;
import com.smc.ops.delivery.model.branch.ExpdetailPropertyDo;
import com.smc.ops.delivery.model.samplebal.OpsSamplebalDO;
import com.smc.ops.delivery.service.barcode.SampleBalExtractService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * 样品出库订单抽取，抽取到sampleBal中
 */
@Service
public class SampleBalExtractServiceImpl implements SampleBalExtractService {

    @Autowired
    private SampleBalExtractDao sampleBalExtractDao;

    @Autowired
    private OpsSampleBalMapper opsSampleBalMapper;

    @Autowired
    private SampleBalOrderDao sampleBalOrderDao;

    @Resource
    private DictdataDao dictdataDao;

    @Resource
    private OpsExpdetailDao opsExpdetailDao;

    @Resource
    private OpslogDao opslogDao;

    @Resource
    private SampleBalPropertyAssignDao sampleBalPropertyAssignDao;

    /**
     * 抽取样品出库数据
     * @param prodFlag 是否拆分
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public CommonResult<String> extractSampleBal(Boolean prodFlag) throws OpsException{
        List<ExpdetailDto> newList  = new ArrayList<>();
        // 是否为拆分型号抽取
        if(prodFlag) {
            // 查询拆分的 样品出库清单
            newList = sampleBalExtractDao.getProdFlagData(OrderTypeEnum.YANGPIN, ExpdetailSampleBalEnum.CHUSHI);
        }
        else {
            // 查询不拆分的 样品出库清单
            List<ExpdetailDto> unProdFlagList = sampleBalExtractDao.getUnProdFlagData(OrderTypeEnum.YANGPIN, ExpdetailSampleBalEnum.CHUSHI);
            // 根据订单号+型号合并
            newList = mergeByOrderNoWithModelNo(unProdFlagList);
        }
        // 操作数据生成
        return generateSampleBalData(newList);

    }


    // 根据型号订单号合并 数量加金额
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<ExpdetailDto> mergeByOrderNoWithModelNo(List<ExpdetailDto> list) throws OpsException {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        Map<String, ExpdetailDto> map = new HashMap<>();
        for (ExpdetailDto item : list) {
            String key = item.getOrderFno().trim() + item.getModelNo().trim();
            if (map.containsKey(key)) {
                ExpdetailDto expdetailVO = map.get(key);
                List<Long> ids = expdetailVO.getIdList();
                ids.add(item.getId());
                expdetailVO.setIdList(ids);
                expdetailVO.setQuantity(expdetailVO.getQuantity() + item.getQuantity());
                map.put(key, expdetailVO);
            } else {
                List<Long> id = new ArrayList<>();
                id.add(item.getId());
                item.setIdList(id);
                map.put(key, item);
            }
        }
        Collection<ExpdetailDto> values = map.values();
        return new ArrayList<ExpdetailDto>(values);
    }

    @Override
    public Map<String, String> getDictData(String classCode) throws OpsException{
        // 获取字典表的申请类型和结转类型对应关系
        Map<String, String> map = new HashMap<>();
        List<DataCodeDto> dataCodes = dictdataDao.getTbldatatype(classCode);
        if (CollectionUtils.isEmpty(dataCodes)) {
            return null;
        }
        for (DataCodeDto item : dataCodes) {
            map.put(item.getCode(), item.getExtNote1());
        }
        return map;
    }


    // 抽取，样品出库订单到 sampleBal表中
    public CommonResult<String> generateSampleBalData(List<ExpdetailDto> list) {
//        if (CollectionUtils.isEmpty(list)) {
//            return CommonResult.failure("暂无所需结算数据");
//        }
//        SampleOrderApplyDto sampleOrderApplyDto;
//        SampleOrderDetailDto sampleOrderDetailDto;
//        SampleBal sampleBal;
////        OrderLogVO orderLogVO;
//
//        // 获取字典表的申请类型和结转类型对应关系
//        Map<String, String> map = new HashMap<>();
//        List<DataCodeDto> dataCodes = dictdataDao.getTbldatatype(ExpdetailSampleBalEnum.DICT_CODE_SAMPLEORDER_APPTYPE);
//        if (CollectionUtils.isEmpty(dataCodes)) {
//            return CommonResult.failure("无法获取字典参数配置信息");
//        }
//        for (DataCodeDto item : dataCodes) {
//            map.put(item.getCode(), item.getExtNote1());
//        }
//
//        for (ExpdetailDto item : list) {
//            // 查找rcvdetail 接单数据做对比
//            Rcvdetail rcvdetail = findRcvDetail(item);
//            if (rcvdetail == null) {
//                continue;
//            }
//            SampleBalExample example = new SampleBalExample();
//            SampleBalExample.Criteria sampleBalCriteria = example.createCriteria();
//            sampleBalCriteria.andRordernoEqualTo(item.getOrderFno()).andOptcodeNotEqualTo(ExpdetailSampleBalEnum.QUXIAO);
//            List<SampleBal> samplebalList = sampleBalMapper.selectByExample(example);
//            // 判断是否为拆分型号订单，再校验数量
//            if (!rcvdetail.getProdFlag().equals("2")) {
//                int alreadyBalNum = 0;
//                if (CollectionUtils.isNotEmpty(samplebalList)) {
//                    alreadyBalNum = samplebalList.stream().filter(i -> i.getQuantity() != null).mapToInt(SampleBal::getQuantity).sum();
//                }
//                // 判断是否整型号 控制写入sample_bal里面的整型号数量不能超过rcvdetail的接单数量
//                if (StringUtils.isBlank(item.getModelNo()) || !item.getModelNo().trim().equals(rcvdetail.getModelNo().trim())
//                        || ((item.getQuantity() + alreadyBalNum) > rcvdetail.getQuantity())) {
//                    // 更新出库表异常状态
//                    upExpdetailOptCodeById(item.getIdList(), ExpdetailSampleBalEnum.YICHANG);
//                    //是否加入邮件提醒
//                    continue;
//                }
//            }
//
//            sampleBal = new SampleBal();
//            // 根据expdetail 订单号 项号 查样品申请主表
//            sampleOrderApplyDto = sampleBalOrderDao.getSampleApply(item.getOrderFno()).get(0);
//
//            // hl所 (费用承担部门)
//            if (StringUtils.isNotBlank(item.getHrUnitId()) && StringUtils.isNotBlank(item.getHlCode())) {
//                sampleBal.setDeptdesc(item.getHrUnitId());
//                sampleBal.setDeptno(item.getHlCode());
//            } else {
//                ResultVo<String> deptNoResult = getDeptNoByHRSalesDeptNos(sampleOrderApplyDto.getDeptNo());
//                if (!deptNoResult.isSuccess() || deptNoResult.getData() == null) {
//                    sampleBal.setDeptno(null);
//                    sampleBal.setRemark("部门数据错误");
//                } else {
//                    if (!sampleOrderApplyDto.getDeptNo().equals(deptNoResult.getData())) {
//                        sampleBal.setDeptdesc(sampleOrderApplyDto.getDeptNo());
//                        sampleBal.setDeptno(deptNoResult.getData());
//                    } else {
//                        sampleBal.setDeptno(sampleOrderApplyDto.getDeptNo());
//                        sampleBal.setDeptdesc(sampleOrderApplyDto.getDeptNo());
//                    }
//                }
//            }
//
//            // 判断索赔号,索赔金额,物流公司名称
//            if (!isAlreadyInto(samplebalList)) {
//                sampleBal.setClaimNo(sampleOrderApplyDto.getClaimNo());
//                sampleBal.setClaimAmount(sampleOrderApplyDto.getClaimAmount());
//                sampleBal.setExpressCompany(sampleOrderApplyDto.getExpressCompany());
//            }
//            sampleBal.setRcvdeptno(sampleBal.getDeptno());
//            sampleBal.setApplycode(sampleOrderApplyDto.getApplyNo());
//            sampleBal.setApptype(sampleOrderApplyDto.getApplyType());
//            sampleBal.setBaltype(sampleOrderApplyDto.getCostType());
//            if (StringUtils.isBlank(sampleBal.getBaltype())) {
//                if (StringUtils.isNotBlank(sampleOrderApplyDto.getApplyType())) {
//                    String applyType = sampleOrderApplyDto.getApplyType();
//                    String balType = map.get(applyType);
//                    if (StringUtils.isNotBlank(balType)) {
//                        sampleBal.setBaltype(balType);
//                    }
//                }
//            }
//            // 写入拆分类别
//            if (StringUtils.isNotBlank(rcvdetail.getProdFlag()) && "2".equals(rcvdetail.getProdFlag())) {
//                sampleBal.setProdflag(ExpdetailSampleBalEnum.CHAIFEN);
//            } else {
//                sampleBal.setProdflag(ExpdetailSampleBalEnum.BUCHAIFEN);
//            }
//
//            sampleBal.setOptcode("1");
//            sampleBal.setPrice(item.getPrice());
//            // 申请价格 默认为单价
//            sampleBal.setPriceApply(item.getPrice());
//            sampleBal.setCustomerno(item.getCustomerNo());
//            sampleBal.setRorderno(item.getOrderFno());
//            sampleBal.setModelno(item.getModelNo());
//            sampleBal.setQuantity(item.getQuantity());
//            sampleBal.setOptdate(new Date());
//            sampleBal.setOpttime(new Date());
//            // 工厂产品标识 prodCode
//            // ECode
//            sampleBal.setRemark("样品结转");
//            sampleBal.setExpdate(item.getShipDate());
//            sampleBal.setOrdtype(OrderTypeEnum.YANGPIN); // 样品订单
//            sampleBal.setCreatetime(new Date());
//            sampleBal.setStockcode(item.getStockCode());
//            // 写入sampleBal
//            sampleBalMapper.insertSelective(sampleBal);
//            // 更改expDetail的状态 1 -> 2
//            upExpdetailOptCodeById(item.getIdList(),ExpdetailSampleBalEnum.WANCHENG);

            // 是否需要写到MQ中，记录到日志表
//            orderLogVO = new OrderLogVO();
//            orderLogVO.setOrderNo(sampleBal.getRorderno());
//            orderLogVO.setOptType(11);
//            orderLogVO.setDescription("发货数据生成结转数据,数量"+item.getQuantity()+"型号"+item.getModelNo());
//            orderLogVO.setOptTime(new Date());
//            orderLogVO.setIp(IpUtil.getIpAddress());
//            orderLogVO.setOptUserName("system");
//            orderLogVO.setOptUserId("system");
//            orderLogFeignApi.sendOrderLogMsgToMQ(orderLogVO);
//        }
        return  CommonResult.success("样品数据结算成功! 共计: " + list.size());
    }


    /**
     * 根据订单号获取订单明细信息
     */
    @Override
    public Rcvdetail findRcvDetail(ExpdetailDto expdetailVO) throws OpsException{
        if (expdetailVO == null || StringUtils.isBlank(expdetailVO.getOrderFno())) {
            return null;
        }
//        RcvdetailExample example = new RcvdetailExample();
//        RcvdetailExample.Criteria rcvdetailCriteria = example.createCriteria();
//        rcvdetailCriteria.andRorderFnoEqualTo(expdetailVO.getOrderFno());
//        List<Rcvdetail> rcvdetails = rcvdetailMapper.selectByExample(example);
        List<Rcvdetail> rcvdetails = sampleBalExtractDao.getRcvdetails(expdetailVO.getOrderFno());
        if (CollectionUtils.isEmpty(rcvdetails)) {
            return null;
        }
        return rcvdetails.get(0);
    }

    @Override
    public List<SampleBal> getSampleBalList(String orderFno) throws OpsException{
//        SampleBalExample example = new SampleBalExample();
//        SampleBalExample.Criteria sampleBalCriteria = example.createCriteria();
//        sampleBalCriteria.andRordernoEqualTo(orderFno).andOptcodeNotEqualTo(ExpdetailSampleBalEnum.QUXIAO);
        return sampleBalExtractDao.getSampleBalList(orderFno);
    }

    /**
     * 修改发货表expdetail的状态
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void upExpdetailOptCodeById(List<Long> idList, Integer optCode) throws OpsException {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        sampleBalExtractDao.updateExpdetailOptcode(idList,optCode);
//        Expdetail expdetail = new Expdetail();
//        expdetail.setOptCode(optCode);
//        expdetail.setUpdateTime(new Date());
//
//        ExpdetailExample example = new ExpdetailExample();
//        ExpdetailExample.Criteria exampleCriteria = example.createCriteria();
//        exampleCriteria.andOptCodeEqualTo(optCode).andIdIn(idList);
//        expdetailMapper.updateByExampleSelective(expdetail, example);

    }

    @Override
    public void updateOptCodeProdFlag(String order_fno, Integer optCode) throws OpsException {
        if (StringUtils.isBlank(order_fno)) {
            throw Exceptions.OpsException("拆分型号单据为空，更新状态失败");
        }
        sampleBalExtractDao.updateExpdetailOptcodeProdflag(order_fno,optCode);
    }

    @Override
    public SampleOrderApplyDto getSampleApply(String orderFno) throws OpsException{
        return sampleBalOrderDao.getSampleApply(orderFno).get(0);
    }

    // 判断是否已经写入过索赔号/索赔金额/物流公司
    // true 写入过
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Boolean isAlreadyInto(List<SampleBal> list) throws OpsException{
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        for (SampleBal item : list) {
            if (StringUtils.isNotBlank(item.getClaimNo())) {
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public SampleBal InsertSampleBal(SampleOrderApplyDto sampleOrderApplyDto,ExpdetailDto item,List<SampleBal> samplebalList, Map<String, String> map,String prodFlag) throws OpsException{
        SampleBal sampleBal = new SampleBal();
        // hl所 (费用承担部门)
        if (StringUtils.isNotBlank(item.getHrUnitId()) && StringUtils.isNotBlank(item.getHlCode())) {
            sampleBal.setDeptdesc(item.getHrUnitId());
            sampleBal.setDeptno(item.getHlCode());
        } else {
            ResultVo<String> deptNoResult = getDeptNoByHRSalesDeptNos(sampleOrderApplyDto.getDeptNo());
            if (!deptNoResult.isSuccess() || deptNoResult.getData() == null) {
                sampleBal.setDeptno(null);
                sampleBal.setRemark("部门数据错误");
            } else {
                if (!sampleOrderApplyDto.getDeptNo().equals(deptNoResult.getData())) {
                    sampleBal.setDeptdesc(sampleOrderApplyDto.getDeptNo());
                    sampleBal.setDeptno(deptNoResult.getData());
                } else {
                    sampleBal.setDeptno(sampleOrderApplyDto.getDeptNo());
                    sampleBal.setDeptdesc(sampleOrderApplyDto.getDeptNo());
                }
            }
        }

        // 判断索赔号,索赔金额,物流公司名称
        if (!isAlreadyInto(samplebalList)) {
            sampleBal.setClaimNo(sampleOrderApplyDto.getClaimNo());
            sampleBal.setClaimAmount(sampleOrderApplyDto.getClaimAmount());
            sampleBal.setExpressCompany(sampleOrderApplyDto.getExpressCompany());
        }
        sampleBal.setRcvdeptno(sampleBal.getDeptno());
        sampleBal.setApplycode(sampleOrderApplyDto.getApplyNo());
        sampleBal.setApptype(sampleOrderApplyDto.getApplyType());
        sampleBal.setBaltype(sampleOrderApplyDto.getCostType());
        if (StringUtils.isBlank(sampleBal.getBaltype())) {
            if (StringUtils.isNotBlank(sampleOrderApplyDto.getApplyType())) {
                String applyType = sampleOrderApplyDto.getApplyType();
                String balType = map.get(applyType);
                if (StringUtils.isNotBlank(balType)) {
                    sampleBal.setBaltype(balType);
                }
            }
        }
        //写入拆分类别
        sampleBal.setProdflag(prodFlag);
        sampleBal.setOptcode("1");
        sampleBal.setPrice(item.getPrice());
        // 申请价格 默认为单价
        sampleBal.setPriceApply(item.getPrice());
        sampleBal.setCustomerno(item.getCustomerNo());
        sampleBal.setRorderno(item.getOrderFno());
        sampleBal.setModelno(item.getModelNo());
        sampleBal.setQuantity(item.getQuantity());
        sampleBal.setOptdate(new Date());
        sampleBal.setOpttime(new Date());
        // 工厂产品标识 prodCode
        // ECode
        sampleBal.setRemark("样品结转");
        sampleBal.setExpdate(item.getShipDate());
        sampleBal.setOrdtype(OrderTypeEnum.YANGPIN); // 样品订单
        sampleBal.setCreatetime(new Date());
        sampleBal.setStockcode(item.getStockCode());
        // bug19021 免费样品订单推送财务成本中间库时，需要增加对End_user字段的赋值
        sampleBal.setUserNo(item.getUserNo());
        // bug 16372 样品结转广州统合增加,id List用于记录抽取结转数据时关联的expdetail_id,支持多条记录以逗号分隔。
        if (StringUtils.isBlank(item.getIdString())) {
            // 使用 Stream API 将 List<Long> 转换为逗号分隔的字符串
            String idString = item.getIdList().stream().map(String::valueOf).collect(Collectors.joining(","));
            item.setIdString(idString);
        }
        sampleBal.setExpdetailId(item.getIdString());
        // 写入sampleBal
//        int insertCount = sampleBalExtractDao.insertSampleBal(sampleBal);
        // bug 18883 样品转成本库结转数据确认,采用公共方法写入，以便获取完整的写入数据
        OpsSamplebalDO sampleBalInsert = BeanCopyUtil.copy(sampleBal, OpsSamplebalDO.class);
        int insertCount = opsSampleBalMapper.insert(sampleBalInsert);
        // 写入完成后，查询刚刚写入的数据,新增Sample_bal_property_assign表，用于记录sample_bal表的资产构成明细
        if (insertCount > 0) {
            // 写入成功时，再写入分配明细
            sampleBalPropertyAssignAdd(sampleBalInsert);
        }
        return sampleBal;
    }

    @Override
    public int sendOrderLog(ExpdetailDto expdetailDto) throws OpsException {
        SampleOrderlogDto sampleOrderlogDto = new SampleOrderlogDto();
        sampleOrderlogDto.setOrderNo(expdetailDto.getOrderFno());
        sampleOrderlogDto.setOptType(11);
        sampleOrderlogDto.setDescription("发货数据生成结转数据,数量"+expdetailDto.getQuantity()+"型号"+expdetailDto.getModelNo());
        sampleOrderlogDto.setOptTime(new Date());
        sampleOrderlogDto.setCreateTime(new Date());
        sampleOrderlogDto.setOptUserName("system");
        sampleOrderlogDto.setOptUserId("system");
        return opslogDao.insertOrderlog(sampleOrderlogDto);
    }


    @Override
    public void sendErrorMail(String message) {
        //保存邮件到数据库
        OpsMail opsMail = new OpsMail();
        //初始状态是0
        opsMail.setStatus(SendStatusEnum.INIT.getType());
        //收件人
        opsMail.setMailTo(ExpdetailSampleBalEnum.MAILTO);
        //主题
        opsMail.setSubject("样品订单出库抽取sampleBal出错");
        StringBuffer con = new StringBuffer();
        con.append("<h4>"+message+"</h4>\r\n ");
        //邮件内容
        opsMail.setContext(con.toString());
        opsExpdetailDao.insertMailData(opsMail);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultVo<String> getDeptNoByHRSalesDeptNos(String hrSalesDeptNo) throws OpsException{
        // 查询销售部门代码是否是Holon,如果是则返回所属的营业所代码,否则其本身就是营业所代码
        if (StringUtils.isBlank(hrSalesDeptNo)) {
            return ResultVo.failure("入参不可为空");
        }
        List<String> hrLevel = new ArrayList<>();
        hrLevel.add("课内机构(HL)");
        hrLevel.add("驻在所HL");
//        HrOrganizationExample hrOrganizationExample = new HrOrganizationExample();
//        HrOrganizationExample.Criteria criteria = hrOrganizationExample.createCriteria();
//        criteria.andLevelIn(hrLevel).andCompanycodeEqualTo("200000").andIdEqualTo(hrSalesDeptNo);
//        List<HrOrganization> hrOrganizations = hrOrganizationMapper.selectByExample(hrOrganizationExample);
        List<HrOrganization> hrOrganizations = sampleBalExtractDao.getHrOrg(hrLevel,hrSalesDeptNo);
        if (CollectionUtils.isEmpty(hrOrganizations)) {
            return ResultVo.success(hrSalesDeptNo);
        }
        return ResultVo.success(hrOrganizations.get(0).getParentid());
    }

    /**
     * 新增Sample_bal_property_assign表，用于记录sample_bal表的资产构成明细
     * @param sampleBal
     * @return
     * @throws OpsException
     */
    @Override
    public int sampleBalPropertyAssignAdd(OpsSamplebalDO sampleBal) {
        int result = 0;
        // bug18883 样品转成本库结转数据确认,无需再重复查询，直接使用写入实体即可
        // 首先根据订单号等信息，去查询sample表明细
//        List<SampleBal> samplebalList = sampleBalExtractDao.getSampleBalBylist(sampleBal);
//        if (CollectionUtils.isEmpty(samplebalList)) {
//            return result;
//        }
//        sampleBal = samplebalList.get(0);
        int sampleBalQty = sampleBal.getQuantity();
        if (StringUtils.isNotBlank(sampleBal.getExpdetailId())) {
            List<SampleBalPropertyAssign> sampleBalPropertyAssignList = new ArrayList<>();
            SampleBalPropertyAssign sampleBalPropertyAssign;
            String expdetailId = sampleBal.getExpdetailId();
            // 传list<Long>,根据逗号进行区分
            List<Long> expdetailIdList = Arrays.stream(expdetailId.split(",")).map(Long::parseLong).collect(Collectors.toList());
            List<ExpdetailPropertyDo>  expdetailPropertyDos = sampleBalPropertyAssignDao.getExpdetailPropertyData(expdetailIdList);
            // 根据拆分类型进行区分操作
            if (ExpdetailSampleBalEnum.CHAIFEN.equals(sampleBal.getProdflag())) { // 拆分型号的处理,
                // 不管是否存在CNG资产都写入完整的拆分明细,查询OPS的拆分明细
                List<OpsOrderAssignResult> opsOrderAssignResults = sampleBalPropertyAssignDao.getOpsAssignResult(sampleBal.getRorderno());
                if (CollectionUtils.isNotEmpty(opsOrderAssignResults)) {
                    for (OpsOrderAssignResult assignResult: opsOrderAssignResults) {
                        String assignModelNo = assignResult.getModelno();
                        int assignQuantity = assignResult.getQuantity();
                        // 定义拆分比例
//                        String proportion = String.valueOf(assignQuantity * 1.0 / sampleBalQty);
                        String proportion = String.valueOf((int) Math.round((assignQuantity * 1.0 / sampleBalQty)));
                        if (CollectionUtils.isNotEmpty(expdetailPropertyDos)) {
                            // 内层循环去追加非CNG资产的明细信息
                            for (ExpdetailPropertyDo expdetailPropertyDo : expdetailPropertyDos) {
                                if (expdetailPropertyDo.getModelNo().equals(assignModelNo)) {
                                    // 写入sample_bal_property_assign表
                                    sampleBalPropertyAssign = new SampleBalPropertyAssign();
                                    sampleBalPropertyAssign.setSampleBalId(sampleBal.getId());
                                    sampleBalPropertyAssign.setModelNo(assignModelNo);
                                    sampleBalPropertyAssign.setQuantity(expdetailPropertyDo.getPropertyQuantity());
                                    sampleBalPropertyAssign.setCompanyId(expdetailPropertyDo.getCompanyId());
                                    sampleBalPropertyAssign.setCreateTime(new Date());
                                    sampleBalPropertyAssign.setCreateUser("SampleBal-Insert");
                                    sampleBalPropertyAssign.setVersion(1);
                                    sampleBalPropertyAssign.setProportion(proportion);
                                    sampleBalPropertyAssignList.add(sampleBalPropertyAssign);
                                    // 需要扣减非拆分的数量
                                    assignQuantity = assignQuantity - expdetailPropertyDo.getPropertyQuantity();
                                }
                            }
                        }
                        // 循环写入完成后，写入CN0资产的数据
                        if (assignQuantity > 0) {
                            sampleBalPropertyAssign = new SampleBalPropertyAssign();
                            sampleBalPropertyAssign.setSampleBalId(sampleBal.getId());
                            sampleBalPropertyAssign.setModelNo(assignModelNo);
                            sampleBalPropertyAssign.setQuantity(assignQuantity);
                            sampleBalPropertyAssign.setCompanyId(TradeCompanyIdEnum.CN0.getCode());
                            sampleBalPropertyAssign.setCreateTime(new Date());
                            sampleBalPropertyAssign.setCreateUser("SampleBal-Insert");
                            sampleBalPropertyAssign.setVersion(1);
                            sampleBalPropertyAssign.setProportion(proportion);
                            sampleBalPropertyAssignList.add(sampleBalPropertyAssign);
                        }
                    }
                }
            } else { // 非拆分型号的处理
                // 非拆分型号订单：根据id,联查expdetail分配表的数据，当分配表存在时，写入分配表的数据companyid数据，同时扣减原始数量，其余数量记录为CN0的明细
                if (CollectionUtils.isNotEmpty(expdetailPropertyDos)) {
                    // 循环进行写入
                    for (ExpdetailPropertyDo item : expdetailPropertyDos) {
                        // 写入sample_bal_property_assign表
                        sampleBalPropertyAssign = new SampleBalPropertyAssign();
                        sampleBalPropertyAssign.setSampleBalId(sampleBal.getId());
                        sampleBalPropertyAssign.setModelNo(item.getModelNo());
                        sampleBalPropertyAssign.setQuantity(item.getPropertyQuantity());
                        sampleBalPropertyAssign.setCompanyId(item.getCompanyId());
                        sampleBalPropertyAssign.setCreateTime(new Date());
                        sampleBalPropertyAssign.setCreateUser("SampleBal-Insert");
                        sampleBalPropertyAssign.setVersion(1);
                        sampleBalPropertyAssign.setProportion("1");
                        sampleBalPropertyAssignList.add(sampleBalPropertyAssign);
                        // 需要扣减非拆分的数量
                        sampleBalQty = sampleBalQty - item.getPropertyQuantity();
                    }
                }
                // 循环写入完成后，写入CN0资产的数据
                if (sampleBalQty > 0) {
                    sampleBalPropertyAssign = new SampleBalPropertyAssign();
                    sampleBalPropertyAssign.setSampleBalId(sampleBal.getId());
                    sampleBalPropertyAssign.setModelNo(sampleBal.getModelno());
                    sampleBalPropertyAssign.setQuantity(sampleBalQty);
                    sampleBalPropertyAssign.setCompanyId(TradeCompanyIdEnum.CN0.getCode());
                    sampleBalPropertyAssign.setCreateTime(new Date());
                    sampleBalPropertyAssign.setCreateUser("SampleBal-Insert");
                    sampleBalPropertyAssign.setVersion(1);
                    sampleBalPropertyAssign.setProportion("1");
                    sampleBalPropertyAssignList.add(sampleBalPropertyAssign);
                }
            }
            if (CollectionUtils.isNotEmpty(sampleBalPropertyAssignList)) {
                result = sampleBalPropertyAssignDao.insertSampleBalPropertyAssign(sampleBalPropertyAssignList);
            }
        }
        return result;
    }

}
