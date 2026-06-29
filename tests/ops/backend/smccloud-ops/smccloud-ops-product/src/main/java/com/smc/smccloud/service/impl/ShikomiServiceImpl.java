package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.enums.SendStatusEnum;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.*;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.HROrganizationVO;
import com.smc.smccloud.model.OpsMailDO;
import com.smc.smccloud.model.constants.Constants;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.enums.ShikomiInspectApplyTypeEnum;
import com.smc.smccloud.model.enums.ShikomiInspectConfirmTypeEnum;
import com.smc.smccloud.model.enums.ShikomiInspectStatusEnum;
import com.smc.smccloud.model.enums.ShikomiInspectTypesEnum;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.prestock.PreStockApplyDetailDTO;
import com.smc.smccloud.model.prestock.PreStockDetailDTO;
import com.smc.smccloud.model.prestock.PreStockDetailViewVO;
import com.smc.smccloud.model.prestock.ShikomiCallbackDTO;
import com.smc.smccloud.model.product.ProductInfoVO;
import com.smc.smccloud.model.purchase.OpsPurchaseOrderVO;
import com.smc.smccloud.model.shikomi.*;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.model.supplier.SupplierVo;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class ShikomiServiceImpl implements ShikomiService {

    @Resource
    private ShikomiTotalMapper shikomiTotalMapper;
    @Resource
    private ShikomiModelMapper shikomiModelMapper;

    @Resource
    private ShikomiCustomerMapper shikomiCustomerMapper;

    @Resource
    private RedisManager redisUtil;

    @Resource
    private ShikomiInspectionMapper shikomiInspectionMapper;

    @Resource
    private PreStockFeignApi preStockFeignApi;
    @Resource
    private CommonService commonService;
    @Resource
    private HttpServletResponse response;
    @Resource
    private ProductService productService;
    @Resource
    private CreateTokenForOtherServer createTokenForOtherServer;
    @Resource
    private BinServiceFeignApi binServiceFeignApi;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private JavaMailSenderImpl javaMailSender;
    @Resource
    private VShikomiTotalMapper vShikomiTotalMapper;
    @Resource
    private RedissonUtil redissonUtil;

    @Resource
    private ShikomiBudgetForSalesMapper shikomiBudgetForSalesMapper;

    @Value("${menhu.url}")
    private String menHuUrl;

    @Value("${sendmail.flag}")
    private String sendMailFlag;

    @Value("${ftp.server}")
    private String server;
    @Value("${ftp.user}")
    private String user;
    @Value("${ftp.password}")
    private String password;

    @Value("${ops-file-upload-path.url}")
    private String filePath;

    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private ShikomiBudgetMapper shikomiBudgetMapper;

    @Resource
    private TmpShimokiBaseMapper tmpShimokiBaseMapper;

    @Resource
    private OrderLogFeignApi orderLogFeignApi;

    @Override
    public ResultVo<ShikomiVO> canUseShikomi(String modelNo, String shikomiNo, String customerNo) {

        QueryWrapper<VShikomiTotalDO> query = new QueryWrapper<>();
//        query.eq("modelno", modelNo);
        query.eq("ShikomiNo", shikomiNo);
        query.eq("Status", 1);
        String s = JSON.toJSONString(modelNo).replace('\"', '\'');
        query.last("and (modelno= " + s + " or " + s + " like serialModel)");

//        VShikomiTotalDO vShikomiTotalDO = vShikomiTotalMapper.selectOne(query);
        List<VShikomiTotalDO> list = vShikomiTotalMapper.selectList(query);
        if (CollectionUtil.isEmpty(list)) {
            return ResultVo.failure("无效shikomi");
        }
        VShikomiTotalDO vShikomiTotalDO = list.get(0);
//        VShikomiTotalDO vShikomiTotalDO = new VShikomiTotalDO();
//        for (VShikomiTotalDO totalDO : list) {
//            if (totalDO.getModelNo().eq) {
//                vShikomiTotalDO = totalDO;
//            }
//        }
//        String modelType = this.getModelType(vShikomiTotalDO.getSerialModel());
        //3.复数型号
//        if (PublicUtil.isNotEmpty(vShikomiTotalDO.getSerialModel()) && "3".equals(vShikomiTotalDO.getModelType())) {
//            QueryWrapper<VShikomiTotalDO> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("modelno", vShikomiTotalDO.getSerialModel());
//            queryWrapper.eq("ShikomiNo", shikomiNo);
//            queryWrapper.eq("Status", 1);
//            queryWrapper.eq("modelType", 1);
//
//            vShikomiTotalDO = vShikomiTotalMapper.selectOne(queryWrapper);
//            if (vShikomiTotalDO == null) {
//
//                return ResultVo.failure("无效shikomi");
//            }
//        }

        ShikomiVO shikomiVO = BeanCopyUtil.copy(vShikomiTotalDO, ShikomiVO.class);

        return checkCanUse(shikomiVO, customerNo);
    }

    private ResultVo<ShikomiVO> checkCanUse(ShikomiVO shikomiVO, String customerNo) {
        //A中国特定客户,B海外公司特定客户,C所有客户
        ShikomiClassTypeEnum shikomiClassTypeEnum = ShikomiClassTypeEnum.getEnum(shikomiVO.getClassType());
        if (ShikomiClassTypeEnum.B.equals(shikomiClassTypeEnum)) {
            if (Constants.SUBSIDIARY_CODE.equalsIgnoreCase(shikomiVO.getSubsidiaryCode())) {
                shikomiVO.setAnswerText("国外管理,95012国内可用");
                return ResultVo.success(shikomiVO);
            } else {
                shikomiVO.setStatus(2);
                return ResultVo.failure(shikomiVO, "0", "B国外管理，国内不可以使用");
            }
        } else if (ShikomiClassTypeEnum.A.equals(shikomiClassTypeEnum)) {

            //1.中国公司共享 2客户专享可协商借用 3 客户专享 0.全球共享
            if (shikomiVO.getClassCode() == null
                    || ShikomiClassCodeEnum.China.getCode().equals(shikomiVO.getClassCode())
                    || ShikomiClassCodeEnum.Global.getCode().equals(shikomiVO.getClassCode())) {
                return ResultVo.success(shikomiVO);
            }
            QueryWrapper<ShikomiCustomerDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ShikomiNo", shikomiVO.getShikomiNo());
            queryWrapper.eq("CustomerNo", customerNo);
            ShikomiCustomerDO customerDO = shikomiCustomerMapper.selectOne(queryWrapper);

            if (customerDO != null) {
                return ResultVo.success(shikomiVO);
            }

            if (ShikomiClassCodeEnum.CustomerPrivate.getCode().equals(shikomiVO.getClassCode())) {
                if (shikomiVO.getCustomerNo().equalsIgnoreCase(customerNo)) {
                    return ResultVo.success(shikomiVO, "客户专享");
                }
            }

            if (ShikomiClassCodeEnum.CustomerShare.getCode().equals(shikomiVO.getClassCode())) {
                shikomiVO.setStatus(2);
                shikomiVO.setAnswerText("客户专享可协商借用");
                return ResultVo.failure(shikomiVO, "2", "客户专享可协商借用");
            }
        }
        if (ShikomiClassTypeEnum.C.equals(shikomiClassTypeEnum)) {
            return ResultVo.success(shikomiVO);
        }
        shikomiVO.setStatus(2);
        shikomiVO.setAnswerText("无法识别类型");
        return ResultVo.failure(shikomiVO, "0", "无法识别类型");
    }


    @Override
    public List<ShikomiVO> listCustomerShikomi(String modelNo, String customerNo) {

        QueryWrapper<VShikomiTotalDO> query = new QueryWrapper<>();
//        query.eq("modelno", modelNo);
        // query.eq("CustomerNo", customerNo);
        query.eq("Status", 1);
        String s = JSON.toJSONString(modelNo).replace('\"', '\'');
        query.last("and (modelno= " + s + " or " + s + " like serialModel)");

        List<VShikomiTotalDO> dolist = vShikomiTotalMapper.selectList(query);
        List<ShikomiVO> voList = new ArrayList<>();
        for (VShikomiTotalDO shikomiTotalDO : dolist) {
            ShikomiVO shikomiVO = new ShikomiVO();
            BeanUtils.copyProperties(shikomiTotalDO, shikomiVO);
            ResultVo<ShikomiVO> result = checkCanUse(shikomiVO, customerNo);
            if (!result.getCode().equalsIgnoreCase("0")) {
                ShikomiVO shikomiVOInfo = result.getData();
                shikomiVOInfo.setModelNo(modelNo);
                if (shikomiVOInfo.getApplyTime() == null) {
                    if (shikomiVOInfo.getRegistDate() != null) {
                        shikomiVOInfo.setApplyTime(shikomiVOInfo.getRegistDate());
                    }
                    if (shikomiVOInfo.getApplyTime() == null && shikomiVOInfo.getReviseDate() != null) {
                        shikomiVOInfo.setApplyTime(shikomiVOInfo.getReviseDate());
                    }
                    if (shikomiVOInfo.getApplyTime() == null) {
                        shikomiVOInfo.setApplyTime(shikomiVOInfo.getCreateTime());
                    }
                }
                voList.add(result.getData());
            }
        }

        return voList;
    }

    @Override
    public ResultVo<String> prepareShikomi(ShikomiPrepareDTO dto) {
        String lockKey = "ops:shikomi:prepareShikomi:" + dto.getSupplierCode() + ":" + dto.getShikomiNo();
        if (redissonUtil.isLock(lockKey)) {
            log.info("正在预约: {}", lockKey);
        }
        redissonUtil.lock(lockKey, 30);
        try {
            log.info("预约shikomi:" + dto.toString());
//        String date = DateUtil.dateToString(new Date());
            boolean exist = redisUtil.hHasKey("ops:prod:shikomi:" + dto.getSupplierCode(), dto.getOrderNo());

            if (exist) {
                return ResultVo.success("已预约成功");
            }
            ResultVo<SupplierVo> resultVo = commonServiceFeignApi.findSupplierById(dto.getSupplierCode());
            if (!resultVo.isSuccess()) {
                return ResultVo.failure("错误");
            }
            LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShikomiTotalDO::getSupplierCode, resultVo.getData().getId()).eq(ShikomiTotalDO::getShikomiNo, dto.getShikomiNo());

            //查询已预约的数量
            ShikomiTotalDO shikomiTotalDO = shikomiTotalMapper.selectOne(queryWrapper);

            if (shikomiTotalDO == null) {
                return ResultVo.failure("无此shikomi");
            }

            //防止空指针异常
            if (PublicUtil.isEmpty(shikomiTotalDO.getQtyOrdPre())) {
                shikomiTotalDO.setQtyOrdPre(0);
            }

            //判断剩余数量是否小于已预约的数量+想要预约的数量
            if (shikomiTotalDO.getRemainQty() < shikomiTotalDO.getQtyOrdPre() + dto.getQuantity()) {
                return ResultVo.failure("剩余数量不足，无法预约");
            }

            //更新预约数量
            shikomiTotalDO.setQtyOrdPre(shikomiTotalDO.getQtyOrdPre() + dto.getQuantity());
            shikomiTotalDO.setLastOrdDate(new Date()); // 记录下单时间
            int update = shikomiTotalMapper.updateById(shikomiTotalDO);

            if (update == 1) {
                redisUtil.hPut("ops:prod:shikomi:" + dto.getSupplierCode(), dto.getOrderNo(), dto.getShikomiNo() + "," + dto.getQuantity());
                return ResultVo.success("预约成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.failure("预约失败:" + e.getMessage());
        } finally {
            redissonUtil.unlock(lockKey);
        }
        return ResultVo.failure("预约失败");
    }

    @Override
    public ResultVo<String> cancelPrepareShikomi(ShikomiPrepareDTO dto) {
        log.info("shikomi取消预约:" + dto.toString());
        if (PublicUtil.isEmpty(dto.getShikomiNo())) {
            return ResultVo.failure("SHIKOMI号不能为空");
        }
        if (PublicUtil.isEmpty(dto.getSupplierCode())) {
            return ResultVo.failure("供应商不能为空");
        }
        String[] split1 = dto.getOrderNo().split("-");
        Integer splitItemNo = null;
        if (split1.length == 3) {
            splitItemNo = Integer.valueOf(split1[2]);
        }
        OpsPurchaseOrderVO purchaseOrder = shikomiTotalMapper.selectPurchaseOrder(split1[0], Integer.valueOf(split1[1]), splitItemNo);
        if (purchaseOrder == null) {
            return ResultVo.failure("不存在此订单");
        }
        ResultVo<SupplierVo> resultVo = commonServiceFeignApi.findSupplierById(dto.getSupplierCode());
        if (resultVo.isSuccess()) {
            dto.setSupplierCode(resultVo.getData().getCompanyId());
        }
        String orderNo = null;
        if (PublicUtil.isNotEmpty(purchaseOrder.getSplitItemNo())) {
            orderNo = purchaseOrder.getOrderNo() + "-" + purchaseOrder.getItemNo() + "-" + purchaseOrder.getSplitItemNo();
        } else {
            orderNo = purchaseOrder.getOrderNo() + "-" + purchaseOrder.getItemNo();
        }
//        String date = DateUtil.dateToString(new Date());
        boolean exist = redisUtil.hHasKey("ops:prod:shikomi:" + dto.getSupplierCode(), orderNo);

        if (!exist) {
            return ResultVo.failure("不存在此预约");
        }

        Object o = redisUtil.hGet("ops:prod:shikomi:" + dto.getSupplierCode(), orderNo);
        String[] split = o.toString().split(",");
        dto.setQuantity(Integer.valueOf(split[1]));

        LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShikomiTotalDO::getShikomiNo, dto.getShikomiNo());
        queryWrapper.eq(ShikomiTotalDO::getSupplierCode, dto.getSupplierCode());
        ShikomiTotalDO totalDO = shikomiTotalMapper.selectOne(queryWrapper);

        if (totalDO != null) {
            if (totalDO.getQtyOrdPre() >= dto.getQuantity()) {
                totalDO.setQtyOrdPre(totalDO.getQtyOrdPre() - dto.getQuantity());
            } else {
                totalDO.setRemainQty(totalDO.getRemainQty() + dto.getQuantity());
            }
            int i = shikomiTotalMapper.updateById(totalDO);
            if (i == 1) {
                redisUtil.hdel("ops:prod:shikomi:" + dto.getSupplierCode(), orderNo);
            } else {
                ResultVo.failure("取消预约时出错");
            }
        }

        return ResultVo.success("取消预约成功");
    }

    @Override
    public ResultVo<String> delShikomiCustomerById(Integer id) {
        if (PublicUtil.isEmpty(id)) {
            return ResultVo.failure("ID不能为空");
        }
        ShikomiCustomerDO customerDO = shikomiCustomerMapper.selectById(id);
        int delete = shikomiCustomerMapper.deleteById(id);
        if (delete == 1) {
            LambdaQueryWrapper<ShikomiCustomerDO> query = new LambdaQueryWrapper<>();
            query.eq(ShikomiCustomerDO::getShikomiNo, customerDO.getShikomiNo());
            List<ShikomiCustomerDO> list = shikomiCustomerMapper.selectList(query);
            if (CollectionUtil.isNotEmpty(list)) {
                List<String> doList = list.stream().map(ShikomiCustomerDO::getCustomerNo).collect(Collectors.toList());
                redisUtil.hPut("ops:shikomi:customer", customerDO.getShikomiNo(), JSON.toJSONString(doList));
            } else {
                redisUtil.hdel("ops:shikomi:customer", customerDO.getShikomiNo());
            }
        }
        return delete == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
    }

    @Override
    public ResultVo<String> delShikomiByNo(String shikomiNo,String modelNo, String supplierCode) {
        if (PublicUtil.isEmpty(shikomiNo) || PublicUtil.isEmpty(supplierCode) || PublicUtil.isEmpty(modelNo)) {
            return ResultVo.failure("shikomi号和供应商代码不能为空");
        }

        LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShikomiTotalDO::getShikomiNo, shikomiNo);
        queryWrapper.eq(ShikomiTotalDO::getModelNo, modelNo);
        queryWrapper.eq(ShikomiTotalDO::getSupplierCode, supplierCode);
        ShikomiTotalDO totalDO = shikomiTotalMapper.selectOne(queryWrapper);

        if (totalDO != null) {
            totalDO.setStatus(2);
            int i = shikomiTotalMapper.updateById(totalDO);
            return i == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
        }

        LambdaQueryWrapper<ShikomiModelDO> query = new LambdaQueryWrapper<>();
        query.eq(ShikomiModelDO::getModelno, modelNo);
        query.eq(ShikomiModelDO::getShikomino, shikomiNo);

        int delete = shikomiModelMapper.delete(query);
        return delete == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
//        LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ShikomiTotalDO::getShikomiNo, shikomiNo);
//        queryWrapper.eq(ShikomiTotalDO::getSupplierCode, supplierCode);
//
//        int delete = shikomiTotalMapper.delete(queryWrapper);
//        return delete == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
    }

    @Override
    public ResultVo<String> addRelationModel(ShikomiVO shikomiVO) {
        QueryWrapper<ShikomiModelDO> query = new QueryWrapper<>();
        query.eq("shikomino", shikomiVO.getShikomiNo());
        query.eq("modelno", shikomiVO.getModelNo());

        ShikomiModelDO modelDO = shikomiModelMapper.selectOne(query);
        if (modelDO != null) {
            ResultVo.failure("数据已存在");
        }

        ShikomiTotalDO totalDO = BeanCopyUtil.copy(shikomiVO, ShikomiTotalDO.class);
        totalDO.setModelType("3");
        totalDO.setMainModelFlag(0);
        ResultVo<String> shikomi = this.addAndUpdateShikomi(totalDO);

        return shikomi.isSuccess() ? ResultVo.success("添加成功！") : ResultVo.failure("添加失败");
    }

    @Override
    public ResultVo<List<ShikomiCustomerVO>> findCustomerDataByNo(String shikomiNo) {
        QueryWrapper<ShikomiCustomerDO> query = new QueryWrapper<>();
        query.eq("ShikomiNo", shikomiNo);

        List<ShikomiCustomerDO> list = shikomiCustomerMapper.selectList(query);
        List<ShikomiCustomerVO> voList = BeanCopyUtil.copyList(list, ShikomiCustomerVO.class);

        return ResultVo.success(voList);
    }

    @Override
    public ResultVo<String> inspectApply(ShikomiInspectionDTO info) {
        log.info("点检申请：" + info.toString());

        if (PublicUtil.isEmpty(info.getInspectApplyType())) {
            return ResultVo.failure("请输入点检申请类型");
        }
        QueryWrapper<ShikomiTotalDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ShikomiNo", info.getShikomiNo());
//        queryWrapper.eq("SupplierCode", info.getSupplierCode());

        List<ShikomiTotalDO> doList = shikomiTotalMapper.selectList(queryWrapper);
        if (doList == null) {
            return ResultVo.failure("shikomi不存在 " + info.getShikomiNo() + " 供应商：" + info.getSupplierCode());
        }
        ShikomiTotalDO totalDO = BeanCopyUtil.copy(info, ShikomiTotalDO.class);

        totalDO.setInspectAnswerTime(new Date());
        totalDO.setApplyNo(null);
        totalDO.setInspectApplyNo(info.getApplyNo());
        totalDO.setInspectStatus(2);//待处理
        if (info.getInspectApplyType() == 1) {
//            totalDO.setStatus(2);
            totalDO.setInspectQty(info.getInspectQty());
            totalDO.setInspectType(9);
        }
        if (info.getInspectApplyType() == 2) {
//            if (doList.get(0).getInspectApplyType() == null || doList.get(0).getInspectApplyType() != 1) {
//                return ResultVo.failure("当前点检状态未中止，不用申请继续");
//            }
            totalDO.setStatus(1);
            totalDO.setInspectType(7);
        }
        if (info.getInspectApplyType() == 3) {
            totalDO.setInspectQty(info.getInspectQty());
            totalDO.setInspectType(9);
        }
        int update = shikomiTotalMapper.update(totalDO, queryWrapper);
        if (update == 1) {
            ShikomiInspectionDO inspectionDO = new ShikomiInspectionDO();
            inspectionDO.setShikomiNo(totalDO.getShikomiNo());
            inspectionDO.setModelNo(totalDO.getModelNo());
            inspectionDO.setApplyNo(info.getApplyNo());
            inspectionDO.setApplyType(info.getInspectApplyType());
            inspectionDO.setApplyQty(info.getInspectQty());
            inspectionDO.setAnswerText(info.getInspectAnswerText());
            inspectionDO.setInspectType(totalDO.getInspectType());
            inspectionDO.setShikomiId(doList.get(0).getId().intValue());
            inspectionDO.setApplyDate(info.getInspectAnswerTime());
            inspectionDO.setApplicantNo(info.getInspectAnswerPsnNo());
            inspectionDO.setRemainQty(info.getRemainQty());
            inspectionDO.setCancelQty(info.getCancelQty());
            inspectionDO.setInspectStatus(2);
            inspectionDO.setCustomerQty(info.getCustomerQty());
            inspectionDO.setRetentionDurationDate(info.getRetentionDurationDate());
            inspectionDO.setExpirationHandle(info.getExpirationHandle());
            inspectionDO.setRepairQty(info.getRepairQty());
            shikomiInspectionMapper.insert(inspectionDO);
            return info.getInspectApplyType() == 1 ? ResultVo.success(info.getShikomiNo(), "终止申请成功") : ResultVo.success(info.getShikomiNo(), "申请继续成功");
        }
        return ResultVo.failure("申请失败");
    }

    @Override
    public ResultVo<String> inspectProcess(ShikomiInspectionDTO info) {
        LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShikomiTotalDO::getShikomiNo, info.getShikomiNo());
        queryWrapper.eq(ShikomiTotalDO::getModelNo, info.getModelNo());
        ShikomiTotalDO totalDO = shikomiTotalMapper.selectOne(queryWrapper);

        if (info.getHandleType() == 1) {
            if (totalDO.getInspectStatus() == 2) {
                totalDO.setInspectStatus(4);
                totalDO.setInspectAnswerText(info.getInspectAnswerText());
            } else {
                return ResultVo.failure("请选择状态为待处理的数据");
            }
        }
        if (info.getHandleType() == 2) {
            if (totalDO.getInspectStatus() == 4) {
                totalDO.setInspectStatus(6);
                totalDO.setInspectAnswerText(info.getInspectAnswerText());
            } else {
                return ResultVo.failure("请选择状态为已发给供应商的数据");
            }
        }
        if (info.getHandleType() == 3) {
            if (totalDO.getInspectStatus() == 4) {
                totalDO.setInspectAnswerText(info.getInspectAnswerText());
            } else {
                return ResultVo.failure("请选择状态为已发给供应商的数据");
            }
        }

        if (info.getHandleType() == 4) {
            totalDO.setInspectAnswerText(info.getInspectAnswerText());
            totalDO.setInspectStatus(9);

        }

        ShikomiInspectionCallbackDTO callbackDTO = new ShikomiInspectionCallbackDTO();
        callbackDTO.setApplyNo(info.getApplyNo());
        callbackDTO.setStatusCode(info.getHandleType());
        callbackDTO.setType(totalDO.getInspectApplyType());
        log.info(callbackDTO.toString());
        ResultVo<String> resultVo = this.callbackHandleType(callbackDTO);
        if (resultVo.isSuccess()) {
            shikomiTotalMapper.updateById(totalDO);
            ShikomiInspectionDO inspectionDO = new ShikomiInspectionDO();
            inspectionDO.setInspectStatus(totalDO.getInspectStatus());
            inspectionDO.setId(info.getId());
            shikomiInspectionMapper.updateById(inspectionDO);
            return ResultVo.success("修改状态成功");
        }
        log.error("shikomi 点检状态回调门户失败 {},{}", resultVo.getMessage(), callbackDTO.toString());
        return ResultVo.failure("修改失败");

    }

    @Override
    public void exportShikomi(ShikomiDataVO data) {
        LambdaQueryWrapper<VShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(data.getCustomerNo()), VShikomiTotalDO::getCustomerNo, data.getCustomerNo())
                .eq(PublicUtil.isNotEmpty(data.getClassType()), VShikomiTotalDO::getClassType, data.getClassType())
                .eq(PublicUtil.isNotEmpty(data.getClassCode()), VShikomiTotalDO::getClassCode, data.getClassCode())
                .eq(PublicUtil.isNotEmpty(data.getApplyNo()), VShikomiTotalDO::getApplyNo, data.getApplyNo())
                .eq(PublicUtil.isNotEmpty(data.getShikomiNo()), VShikomiTotalDO::getShikomiNo, data.getShikomiNo())
                .eq(PublicUtil.isNotEmpty(data.getSupplierCode()), VShikomiTotalDO::getSupplierCode, data.getSupplierCode())
                .in(data.getSupplierCodeList() != null && data.getSupplierCodeList().length > 0, VShikomiTotalDO::getSupplierCode, data.getSupplierCodeList())
//                .eq(PublicUtil.isNotEmpty(data.getStatus()), VShikomiTotalDO::getStatus, data.getStatus())
                .eq(PublicUtil.isNotEmpty(data.getDeptNo()), VShikomiTotalDO::getDeptNo, data.getDeptNo())
                .eq(PublicUtil.isNotEmpty(data.getInspectStatus()), VShikomiTotalDO::getInspectStatus, data.getInspectStatus())
                .eq(PublicUtil.isNotEmpty(data.getInspectType()), VShikomiTotalDO::getInspectType, data.getInspectType())
                .eq(PublicUtil.isNotEmpty(data.getApplicantNo()), VShikomiTotalDO::getApplicantNo, data.getApplicantNo())
                .eq(PublicUtil.isNotEmpty(data.getApplicantName()), VShikomiTotalDO::getApplicantName, data.getApplicantName())
                .eq(PublicUtil.isNotEmpty(data.getApplicantName()), VShikomiTotalDO::getApplicantName, data.getApplicantName())
                .eq(PublicUtil.isNotEmpty(data.getRohs()), VShikomiTotalDO::getRohs, data.getRohs())
                .eq(PublicUtil.isNotEmpty(data.getMainModelFlag()), VShikomiTotalDO::getMainModelFlag, data.getMainModelFlag())
                .eq(PublicUtil.isNotEmpty(data.getIndCode()), VShikomiTotalDO::getIndCode, data.getIndCode())
                .eq(PublicUtil.isNotEmpty(data.getMainCustomerNo()), VShikomiTotalDO::getCustomerNo, data.getMainCustomerNo())
                .between(PublicUtil.isNotEmpty(data.getQtyNoord1()) && PublicUtil.isNotEmpty(data.getQtyNoord2()), VShikomiTotalDO::getQtyNoord, data.getQtyNoord1(), data.getQtyNoord2());

        if (PublicUtil.isNotEmpty(data.getDateType())) {
            if (data.getDateType() == 1) {
                queryWrapper.between(PublicUtil.isNotEmpty(data.getStartTime()), VShikomiTotalDO::getUpdateTime, data.getStartTime(), data.getEndTime());
            }
            if (data.getDateType() == 2) {
                queryWrapper.between(PublicUtil.isNotEmpty(data.getStartTime()), VShikomiTotalDO::getApplyTime, data.getStartTime(), data.getEndTime());
            }
            if (data.getDateType() == 3) {
                queryWrapper.between(PublicUtil.isNotEmpty(data.getStartTime()), VShikomiTotalDO::getDlvDate, data.getStartTime(), data.getEndTime());
            }
            if (data.getDateType() == 4) {
                queryWrapper.between(PublicUtil.isNotEmpty(data.getStartTime()), VShikomiTotalDO::getInspectSendTime, data.getStartTime(), data.getEndTime());
            }
            if (data.getDateType() == 5) {
                queryWrapper.between(PublicUtil.isNotEmpty(data.getStartTime()), VShikomiTotalDO::getInspectAnswerTime, data.getStartTime(), data.getEndTime());
            }
        }

        if (PublicUtil.isEmpty(data.getStatus())) {
            queryWrapper.eq(VShikomiTotalDO::getStatus, 1);
        } else {
            queryWrapper.eq(VShikomiTotalDO::getStatus, data.getStatus());
        }
        if (PublicUtil.isNotEmpty(data.getDeptNos()) && data.getDeptNos().length > 0) {
            queryWrapper.in(VShikomiTotalDO::getDeptNo, data.getDeptNos());
        }

        if (PublicUtil.isNotEmpty(data.getModelNo()) && data.getModelNo().contains("%")) {
            queryWrapper.like(VShikomiTotalDO::getModelNo, data.getModelNo());
        } else {
            if (PublicUtil.isNotEmpty(data.getModelNo())) {
                String modelNo = data.getModelNo();
                String s = JSON.toJSONString(modelNo).replace('\"', '\'');
                queryWrapper.last("and (modelno= " + s + " or " + s + " like serialModel)");
            }
        }


        if (PublicUtil.isNotEmpty(data.getIsManageProduct())) {
            if (data.getIsManageProduct() == 1) {
                queryWrapper.eq(VShikomiTotalDO::getCompanyCode, "CN");
            }
            if (data.getIsManageProduct() == 0) {
                queryWrapper.ne(VShikomiTotalDO::getCompanyCode, "CN");
            }
        }

        if (PublicUtil.isNotEmpty(data.getChecked()) && data.getChecked()) {
            queryWrapper.ne(VShikomiTotalDO::getQtyWarning, 0);
            queryWrapper.last("and QtyWarning>=(qtyPO+qtyOnhand+RemainQty)");
        }
        List<VShikomiTotalDO> list = vShikomiTotalMapper.selectList(queryWrapper);

        String path = "templates/SHIKOMI.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.openSheet(0);

        // 向模板中写入数据
        int row = 1;
        int cel;
        for (VShikomiTotalDO totalDO : list) {
            cel = 0;
            excel.setCellValue(row, cel++, totalDO.getModelNo());
            excel.setCellValue(row, cel++, totalDO.getSupplierCode());
            excel.setCellValue(row, cel++, totalDO.getShikomiNo());
            excel.setCellValue(row, cel++, Optional.ofNullable(totalDO.getMainModelFlag()).orElse(0) == 1 ? "主型号" : "复数型号");
            excel.setCellValue(row, cel++, totalDO.getApplyNo());
            excel.setCellValue(row, cel++, totalDO.getApplyQty());
            excel.setCellValue(row, cel++, totalDO.getRemainQty());
            excel.setCellValue(row, cel++, totalDO.getApplyTime());
            excel.setCellValue(row, cel++, totalDO.getStatus() == 1 ? "有效" : "无效");
            excel.setCellValue(row, cel++, totalDO.getQtyOrdPre());
            excel.setCellValue(row, cel++, totalDO.getRegistDate());
            excel.setCellValue(row, cel++, totalDO.getAsseDays());
            excel.setCellValue(row, cel++, totalDO.getPartPrepareDays());
            excel.setCellValue(row, cel++, totalDO.getCustomerNo());
            excel.setCellValue(row, cel++, totalDO.getCustomerNo());
            excel.setCellValue(row, cel++, ShikomiClassTypeEnum.getName(totalDO.getClassType()));
            excel.setCellValue(row, cel++, ShikomiClassCodeEnum.getName(totalDO.getClassCode()));
            excel.setCellValue(row, cel++, totalDO.getIndCode());
            excel.setCellValue(row, cel++, totalDO.getSubsidiaryCode());
            excel.setCellValue(row, cel++, totalDO.getCompanyCode());
            excel.setCellValue(row, cel++, totalDO.getBranchCode());
            excel.setCellValue(row, cel++, getDeptName(totalDO.getDeptNo()));
            excel.setCellValue(row, cel++, totalDO.getQtyNoord());
            excel.setCellValue(row, cel++, totalDO.getQtyWarning());
            excel.setCellValue(row, cel++, totalDO.getIsWarning() != null && totalDO.getIsWarning() ? "是" : "否"); // 是否警告
            excel.setCellValue(row, cel++, totalDO.getEPrice());
            excel.setCellValue(row, cel++, totalDO.getPriceLot());
            excel.setCellValue(row, cel++, totalDO.getLotQty());
            excel.setCellValue(row, cel++, totalDO.getAnswerText());
            excel.setCellValue(row, cel++, totalDO.getRemark());
            excel.setCellValue(row, cel++, totalDO.getProjectNo());
            excel.setCellValue(row, cel++, totalDO.getPplNo());
            excel.setCellValue(row, cel++, totalDO.getDlvDate());
            excel.setCellValue(row, cel++, totalDO.getApplicantName());
            excel.setCellValue(row, cel++, totalDO.getApplicantEmail());
            excel.setCellValue(row, cel++, totalDO.getApproverName());
            excel.setCellValue(row, cel++, totalDO.getApproverEmail());
            excel.setCellValue(row, cel++, totalDO.getQtyOnhand());
            excel.setCellValue(row, cel++, totalDO.getQtyPO());
            excel.setCellValue(row, cel++, com.smc.smccloud.core.model.enums.ShikomiInspectStatusEnum.getName(totalDO.getInspectStatus()));
            excel.setCellValue(row, cel++, totalDO.getInspectSendTime());
            excel.setCellValue(row, cel++, totalDO.getInspectAnswerTime());
            excel.setCellValue(row, cel++, totalDO.getInspectAnswerText());
            excel.setCellValue(row, cel++, totalDO.getInspectDaily());
            excel.setCellValue(row, cel++, totalDO.getInspectQty());
            excel.setCellValue(row, cel++, ShikomiInspectTypeEnum.getName(totalDO.getInspectType()));
            excel.setCellValue(row, cel++, totalDO.getLastOrdDate());
            excel.setCellValue(row, cel++, totalDO.getUpdateTime());
            excel.setCellValue(row, cel, totalDO.getUpdateUser());
            row++;

        }

        excel.writeToResponse(response, "SHIKOMI.xlsx");
    }

    public String getDeptName(String deptNo) {
        if (StringUtils.isBlank(deptNo)) {
            return null;
        }
//        if (map.containsKey(deptNo)) {
//            return map.get(deptNo);
//        }
        Object obj = redisUtil.hGet(com.smc.smccloud.core.model.constants.Constants.REDIS_DEPARTMENT_NAME_ALL, deptNo);
        if (obj == null) {
            ResultVo<String> deptNameByNo = commonServiceFeignApi.getDeptNameByNo(deptNo);
            if (deptNameByNo.isSuccess() && deptNameByNo.getData() != null) {
//                map.put(deptNo, deptNameByNo.getData());
                return deptNameByNo.getData();
            }
            return null;
        }
//        map.put(deptNo, obj.toString());
        return obj.toString();
    }

    @Override
    public ResultVo<ShikomiVO> getShikomiDataByNo(String shikomiNo, String modelNo, String supplierNo) {
        LambdaQueryWrapper<VShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VShikomiTotalDO::getShikomiNo, shikomiNo);
//        queryWrapper.eq(VShikomiTotalDO::getModelNo, modelNo);
        queryWrapper.eq(StringUtils.isNoneBlank(supplierNo),VShikomiTotalDO::getSupplierCode, supplierNo);
        String s = JSON.toJSONString(modelNo).replace('\"', '\'');
        queryWrapper.last("and (modelno= " + s + " or " + s + " like serialModel) ORDER BY mainModelFlag desc");

        List<VShikomiTotalDO> list = vShikomiTotalMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return ResultVo.failure("未查询到shikomi信息");
        }
        ShikomiVO shikomiVO = BeanCopyUtil.copy(list.get(0), ShikomiVO.class);

        return ResultVo.success(shikomiVO);
    }

    @Override
    public ResultVo<List<SupplierVo>> getShikomiSupplier() {
        QueryWrapper<ShikomiTotalDO> query = new QueryWrapper<>();
        query.select("DISTINCT SupplierCode");
        List<ShikomiTotalDO> list = shikomiTotalMapper.selectList(query);
        List<SupplierVo> voList = new ArrayList<>();
        SupplierVo supplierVo;
        for (ShikomiTotalDO totalDO : list) {
            supplierVo = new SupplierVo();
            supplierVo.setId(totalDO.getSupplierCode());
            supplierVo.setName(commonServiceFeignApi.getSupplierName(totalDO.getSupplierCode()).getData());
            voList.add(supplierVo);
        }

        return ResultVo.success(voList);
    }

    @Override
    public ResultVo<String> saveShikomiCache() {
        redisUtil.del("ops:shikomi:nomodelno");
        redisUtil.del("ops:shikomi:customer");
        redisUtil.del("ops:shikomi:serialmodel");
        redisUtil.del("ops:emailOrder:cancelOrder");

        List<VShikomiTotalDO> totalDOList = vShikomiTotalMapper.getSerialModel2();
        for (VShikomiTotalDO totalDO : totalDOList) {
            String mo = totalDO.getModelNo().replaceAll("\\*", ".*");
            if (mo.contains("_")) {
                mo = mo.replaceAll("_", ".");
            }
            redisUtil.hPut("ops:shikomi:serialmodel", mo, totalDO.getModelNo());
        }

        QueryWrapper<ShikomiCustomerDO> queryWrapper = new QueryWrapper<>();
        List<ShikomiCustomerDO> customerDOList = shikomiCustomerMapper.selectList(queryWrapper);
        List<String> doList;
        for (ShikomiCustomerDO customerDO : customerDOList) {
            Object o = redisUtil.hGet("ops:shikomi:customer", customerDO.getShikomiNo());
            if (o != null) {
                doList = JSON.parseArray(o.toString(), String.class);
            } else {
                doList = new ArrayList<>();
            }
            doList.add(customerDO.getCustomerNo());
            redisUtil.hPut("ops:shikomi:customer", customerDO.getShikomiNo(), JSON.toJSONString(doList));
        }
        return ResultVo.success("成功");
    }

    @Override
    public ResultVo<List<ShikomiVO>> getCanUseShikomi(String modelNo) {
        if (PublicUtil.isEmpty(modelNo)) {
            return ResultVo.failure("请输入型号");
        }

        LambdaQueryWrapper<VShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VShikomiTotalDO::getStatus, 1);
        String s = JSON.toJSONString(modelNo).replace('\"', '\'');
        queryWrapper.last("and (modelno= " + s + " or " + s + " like serialModel)");

        List<VShikomiTotalDO> list = vShikomiTotalMapper.selectList(queryWrapper);
        if (PublicUtil.isEmpty(list)) {
            return ResultVo.failure("无效型号");
        }

        List<ShikomiVO> voList = new ArrayList<>();
        for (VShikomiTotalDO totalDO : list) {
//            String modelType = this.getModelType(totalDO.getSerialModel());
            // 复数型号
//            if (PublicUtil.isNotEmpty(totalDO.getSerialModel()) && "3".equals(totalDO.getModelType())) {
//                QueryWrapper<VShikomiTotalDO> query = new QueryWrapper<>();
//                query.eq("ModelNo", totalDO.getSerialModel());
//                query.eq("ShikomiNo", totalDO.getShikomiNo());
//                query.eq("Status", 1);
//                query.eq("modelType", 1);
//
//                totalDO = vShikomiTotalMapper.selectOne(query);
//                if (totalDO == null) {
//                    continue;
//                }
//            }
            ShikomiVO shikomiVO = BeanCopyUtil.copy(totalDO, ShikomiVO.class);
            ResultVo<ShikomiVO> resultVo = this.checkCanUse(shikomiVO, shikomiVO.getCustomerNo());
            voList.add(resultVo.getData());
        }
        return ResultVo.success(voList);
    }

    @Override
    public void calShikomiUpdateInfo() {
        //更新在库在途数量
        shikomiTotalMapper.calshikomiUpdateInfo();
    }

    @Override
    public Boolean checkShikomiStcokInsufficient() {

        QueryWrapper<ShikomiTotalDO> query = new QueryWrapper<>();
        query.eq("CompanyCode", "CN");
        query.eq("Status", 1);
        query.eq("isWarning", 1);
        List<ShikomiTotalDO> list = shikomiTotalMapper.selectList(query);
        ResultVo<DataTypeVO> resultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "SHIK1");
        List<ShikomiTotalDO> shikomiList = new ArrayList<>();
        for (ShikomiTotalDO totalDO : list) {
            // 防止空指针异常
            if (PublicUtil.isEmpty(totalDO.getQtyOnhand())) {
                totalDO.setQtyOnhand(0);
            }
            if (PublicUtil.isEmpty(totalDO.getQtyPO())) {
                totalDO.setQtyPO(0);
            }
            if (PublicUtil.isEmpty(totalDO.getQtyWarning())) {
                totalDO.setQtyWarning(0);
            }
//            if (totalDO.getQtyWarning() == 0) {
//                continue;
//            }
            if (PublicUtil.isEmpty(totalDO.getQtyOrdPre())) {
                totalDO.setQtyOrdPre(0);
            }
            this.getAllQty(totalDO);

            ShikomiWaringDTO dto = this.getQtyWaring(totalDO);
//            totalDO.setQtyWarning(dto.getQtyWarning());
            totalDO.setReserveMonth(dto.getReserveMonth());
            //（SHIKOMI残数+在库数量+在途数量-已预约数量）<=警告数
            if ((totalDO.getRemainQty() + totalDO.getQtyOnhand() + totalDO.getQtyPO() - totalDO.getQtyOrdPre()) <= totalDO.getQtyWarning()) {
                shikomiList.add(totalDO);

                if (PublicUtil.isNotEmpty(totalDO.getApplicantEmail())) {
                    List<ShikomiTotalDO> doList;
                    Object o = redisUtil.hGet("ops:shikomi:sendmail", totalDO.getApplicantEmail());
                    if (o != null) {
                        doList = JSON.parseArray(o.toString(), ShikomiTotalDO.class);
                    } else {
                        doList = new ArrayList<>();
                    }
                    doList.add(totalDO);
                    redisUtil.hPut("ops:shikomi:sendmail", totalDO.getApplicantEmail(), JSON.toJSONString(doList));
                }
                if (PublicUtil.isNotEmpty(totalDO.getApproverEmail())) {
                    List<ShikomiTotalDO> doList;
                    Object o = redisUtil.hGet("ops:shikomi:sendmail", totalDO.getApproverEmail());
                    if (o != null) {
                        doList = JSON.parseArray(o.toString(), ShikomiTotalDO.class);
                    } else {
                        doList = new ArrayList<>();
                    }
                    doList.add(totalDO);
                    redisUtil.hPut("ops:shikomi:sendmail", totalDO.getApproverEmail(), JSON.toJSONString(doList));
                }
            }
        }

        // 分发给申请人和责任人
        Object obj = redisUtil.getHashKeyValue("ops:shikomi:sendmail");
        log.info(obj.toString());
        if (obj != null) {
            Map<String, Object> map = JSON.parseObject(JSON.toJSONString(obj), HashMap.class);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println("key=" + entry.getKey() + ",value=" + entry.getValue());
                String email = entry.getKey();
                List<ShikomiTotalDO> doList = JSON.parseArray(entry.getValue().toString(), ShikomiTotalDO.class);

                this.sendShikomiEmail(doList, email,null, null);
            }
            redisUtil.del("ops:shikomi:sendmail");
        }

        // 发送总数据给设置的邮箱
        this.sendShikomiEmail(shikomiList, resultVo.getData().getExtNote1(), resultVo.getData().getExtNote2(), "webservice@smcgz.com.cn;"+resultVo.getData().getExtNote3());

        return true;
    }

    private ResultVo<String> sendShikomiEmail(List<ShikomiTotalDO> shikomiList, String to, String tocc, String mcc) {
        // 获取导出模板
        String path = "templates/SHIKOMI残数不足警告.xlsx";
        InputStream stream = FileUtil.getTemplate(path);
        ExcelHelper excel = new ExcelHelper(stream);
        Workbook wb = excel.getWorkBook();
        Font font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        excel.openSheet(0);
        String name = null;
        if (PublicUtil.isEmpty(shikomiList.get(0).getApplicantName())) {
            name = "";
        } else {
            name = shikomiList.get(0).getApplicantName();
        }
        // 向模板中写入数据
        String customer = null;
        int row = 4;
        int cel;
        excel.setCellValue(0, 0, "SHIKOMI残数不足警告_" + name + "_" + DateUtil.dateToString(new Date()));
        for (ShikomiTotalDO totalDO : shikomiList) {
//            ShikomiWaringDTO dto = this.getQtyWaring(totalDO);
            if (PublicUtil.isEmpty(totalDO.getQtyOrdPre())) {
                totalDO.setQtyOrdPre(0);
            }
            customer = "";
            cel = 0;
            excel.setCellValue(row, cel++, totalDO.getShikomiNo());
            excel.setCellValue(row, cel++, totalDO.getModelNo());
            ResultVo<String> supplierName = commonServiceFeignApi.getSupplierName(totalDO.getSupplierCode());
            excel.setCellValue(row, cel++, supplierName.getData());
            excel.setCellValue(row, cel++, totalDO.getCustomerNo());
            ResultVo<List<ShikomiCustomerVO>> data = findCustomerDataByNo(totalDO.getShikomiNo());
            List<ShikomiCustomerVO> list = data.getData();
            for (ShikomiCustomerVO customerVO : list) {
                customer += customerVO.getCustomerNo() + ",";
            }
            excel.setCellValue(row, cel++, customer);
            excel.setCellValue(row, cel++, ShikomiClassTypeEnum.getName(totalDO.getClassType()));
            excel.setCellValue(row, cel++, ShikomiClassCodeEnum.getName(totalDO.getClassCode()));
            excel.setCellValue(row, cel++, totalDO.getQtyNoord());
            excel.setCellValue(row, cel++, totalDO.getRemainQty());
            excel.setCellValue(row, cel++, totalDO.getQtyOrdPre());
//            excel.setCellValue(row, cel++, totalDO.getQtyOrdPre());
            excel.setCellValue(row, cel++, totalDO.getQtyOnhand());
            excel.setCellValue(row, cel++, totalDO.getQtyPO());
            excel.setCellValue(row, cel++, totalDO.getQtyWarning());
            excel.setCellValue(row, cel++, totalDO.getAvgOrdQty()); // 6个月订货
            excel.setCellValue(row, cel++, totalDO.getReserveMonth() + "个月"); // shikomi追加需提前
            if (PublicUtil.isEmpty(totalDO.getDeptNo())) {
                excel.setCellValue(row, cel++, totalDO.getIndCode());
            } else {
                excel.setCellValue(row, cel++, totalDO.getDeptNo());
            }
            excel.setCellValue(row, cel++, totalDO.getApplicantName());
            excel.setCellValue(row, cel, totalDO.getApproverName());

            row++;
        }
        InputStream inputStream = excel.convertTo();
        String fileName = "SHIKOMI残数不足警告_" + name + "_" + DateUtil.dateToString(new Date()) + ".xlsx";
        String subject = "申请人" + name + ",您的SHIKOMI残数已不足警告" + DateUtil.dateToString(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append("<span>申请人:</span></br>");
        sb.append("<span>您好,附件SHIKOMI残数已不足，请您确认下记事项：</span></br>");
        sb.append("<span>如后续客户有持续需求，请及时在直销系统-SHIKOMI管理中提出追加申请；</span></br>");
        sb.append("<span>如需调整警告数量，请联系在库企画课deliverysh@smc.com.cn。</span></br>");
        sb.append("本邮件由OPS系统自动发送，请勿直接回复本邮件");
        Map<String, InputStream> attachment = new LinkedHashMap<>(1);
        attachment.put(fileName, inputStream);
        EmailUtil.send(javaMailSender, to, tocc, mcc, subject, sb.toString(), attachment);
        return ResultVo.success();
    }


    @Override
    public ResultVo<String> shikomiWarningSendMH() {
        QueryWrapper<ShikomiTotalDO> query = new QueryWrapper<>();
        query.eq("CompanyCode", "CN");
        query.eq("Status", 1);
        query.eq("isWarning", 1);
        List<ShikomiTotalDO> list = shikomiTotalMapper.selectList(query);

        DealReturnOpsParamVO paramVO = new DealReturnOpsParamVO();
        paramVO.setApplyType(4);
        DealReturnOpsParam param = new DealReturnOpsParam();
        List<ShikomiWarningDTO> shikomiList = new ArrayList<>();
        ShikomiWarningDTO warningDTO;
        for (ShikomiTotalDO totalDO : list) {
            // 防止空指针异常
            if (PublicUtil.isEmpty(totalDO.getQtyOnhand())) {
                totalDO.setQtyOnhand(0);
            }
            if (PublicUtil.isEmpty(totalDO.getQtyPO())) {
                totalDO.setQtyPO(0);
            }
            if (PublicUtil.isEmpty(totalDO.getQtyWarning())) {
                totalDO.setQtyWarning(0);
            }
            if (PublicUtil.isEmpty(totalDO.getQtyOrdPre())) {
                totalDO.setQtyOrdPre(0);
            }
            if (PublicUtil.isEmpty(totalDO.getQtyWarning())) {
                totalDO.setQtyWarning(0);
            }
            this.getAllQty(totalDO);

            Integer qtyWarning = totalDO.getQtyWarning();
            String customer;
            ShikomiWaringDTO dto = this.getQtyWaring(totalDO);
            totalDO.setReserveMonth(dto.getReserveMonth());
            //（SHIKOMI残数+在库数量+在途数量-已预约数量）<警告数
            if ((totalDO.getRemainQty() + totalDO.getQtyOnhand() + totalDO.getQtyPO() - totalDO.getQtyOrdPre()) < qtyWarning) {
                customer = "";
                warningDTO = new ShikomiWarningDTO();
                warningDTO.setShikomiNo(totalDO.getShikomiNo());
                warningDTO.setModelNo(totalDO.getModelNo());
                warningDTO.setSupplierCode(totalDO.getSupplierCode());
                warningDTO.setCustomerNo(totalDO.getCustomerNo());
                ResultVo<List<ShikomiCustomerVO>> data = findCustomerDataByNo(totalDO.getShikomiNo());
                for (ShikomiCustomerVO customerVO : data.getData()) {
                    customer += customerVO.getCustomerNo() + ",";
                }
                warningDTO.setUserCustomerNo(customer);
                warningDTO.setClassType(totalDO.getClassType());
                warningDTO.setClassCode(totalDO.getClassCode());
                warningDTO.setQtyNood(totalDO.getQtyNoord());
                warningDTO.setRemainQty(totalDO.getRemainQty());
                warningDTO.setQtyOrdPre(totalDO.getQtyOrdPre());
                warningDTO.setQtyOnhand(totalDO.getQtyOnhand());
                warningDTO.setQtyPO(totalDO.getQtyPO());
                warningDTO.setQtyWarning(qtyWarning);
                warningDTO.setAvgOrdQty(totalDO.getAvgOrdQty());
                warningDTO.setPreDate(totalDO.getReserveMonth());
                if (StringUtils.isBlank(totalDO.getDeptNo())) {
                    warningDTO.setDeptNo(totalDO.getIndCode());
                } else {
                    warningDTO.setDeptNo(totalDO.getDeptNo());
                }
                warningDTO.setApplicantNo(totalDO.getApplicantNo());
                warningDTO.setApproverNo(totalDO.getApproverNo());
                shikomiList.add(warningDTO);
            }
        }

        String authorization = createTokenForOtherServer.getMHToken();
        HttpResponse response;
        String json = null;
        List<List<ShikomiWarningDTO>> partition = ListUtils.partition(shikomiList, 100);

        for (List<ShikomiWarningDTO> dtoList : partition) {
            param.setShikomiRemainQtyWarningVo(JSON.toJSON(dtoList));
            paramVO.setDealReturnOpsParam(param);
            json = JSONUtil.toJsonStr(paramVO);
            try {
                String dealShikomiUrl = menHuUrl + "/saleManageSystem/opsReturnInfo/dealReturnInfo";
                response = HttpUtil.createPost(dealShikomiUrl)
                        .header("Authorization", authorization)
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .charset(StandardCharsets.UTF_8)
                        .body(json)
                        .execute();
                String strResponse = response.body();
                JSONObject jsonObject = JSON.parseObject(strResponse);
                log.info("SHIKOMI残数警告jsonObject = " + jsonObject);
                if (jsonObject.getString("success").equals("true")) {
                    log.info(jsonObject.getString("content"));
                } else {
                    log.error(jsonObject.getString("message"));
                }
            } catch (Exception e) {
                log.error("shikomi残数警告接口 error=> {} ", e);
            }
        }

        return ResultVo.success("推送shikomi残数警告成功");
    }

    private void getAllQty(ShikomiTotalDO shikomiTotalDO) {
        LambdaQueryWrapper<VShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VShikomiTotalDO::getShikomiNo, shikomiTotalDO.getShikomiNo());
        queryWrapper.eq(VShikomiTotalDO::getStatus, 1);
        List<VShikomiTotalDO> totalDOList = vShikomiTotalMapper.selectList(queryWrapper);

        int avgeQty = 0;
        int purseQty = 0; // 采购中数量
        int onhand = 0;

        VShikomiTotalDO totalDO = totalDOList.get(0);
        List<String> list = totalDOList.stream().map(VShikomiTotalDO::getModelNo).collect(Collectors.toList());
        // 专享可借用时/客户专享 按照客户来
        if ("2".equals(totalDO.getClassCode()) || "3".equals(totalDO.getClassCode())) {
            ShikomiTotalDO total = vShikomiTotalMapper.getpurchaseQty3(list, totalDO.getCustomerNo());
            if (total != null) {
                onhand = Optional.ofNullable(total.getQtyOnhand()).orElse(0);
                purseQty = Optional.ofNullable(total.getQtyPO()).orElse(0);
            }
            ShikomiTotalDO totalDO1 = vShikomiTotalMapper.getpurchaseQty(list);
            if (totalDO1 != null) {
                onhand += Optional.ofNullable(totalDO1.getQtyOnhand()).orElse(0);
                purseQty += Optional.ofNullable(totalDO1.getQtyPO()).orElse(0);
            }
            List<Integer> orders = vShikomiTotalMapper.getOrdersByNo(list, totalDO.getCustomerNo());
            if (CollectionUtil.isNotEmpty(orders)) {
                avgeQty = orders.stream().mapToInt(Integer::intValue).sum();
            }
        } else {
            ShikomiTotalDO total = vShikomiTotalMapper.getpurchaseQty2(list);
            if (total != null) {
                onhand = total.getQtyOnhand();
                purseQty = total.getQtyPO();
            }
            List<Integer> orders = vShikomiTotalMapper.getOrdersByNo2(list);
            if (CollectionUtil.isNotEmpty(orders)) {
                avgeQty = orders.stream().mapToInt(Integer::intValue).sum();
            }
        }

//        for (VShikomiTotalDO totalDO : totalDOList) {
//
//            if ("2".equals(totalDO.getClassCode()) || "3".equals(totalDO.getClassCode())) {
//                ShikomiTotalDO total = vShikomiTotalMapper.getpurchaseQty1(totalDO.getModelNo(), totalDO.getCustomerNo());
//                if (total != null) {
//                    onhand += total.getQtyOnhand();
//                    purseQty += total.getQtyPO();
//                }
//                Integer orders = vShikomiTotalMapper.getOrdersByNo2(totalDO.getModelNo(), totalDO.getCustomerNo());
//                if (orders != null) {
//                    avgeQty += orders;
//                }
//            } else {
//                ShikomiTotalDO total = vShikomiTotalMapper.getpurchaseQty2(totalDO.getModelNo());
//                if (total != null) {
//                    onhand += total.getQtyOnhand();
//                    purseQty += total.getQtyPO();
//                }
//                Integer orders = vShikomiTotalMapper.getOrdersByNo(totalDO.getModelNo());
//                if (orders != null) {
//                    avgeQty += orders;
//                }
//            }
//        }
//        shikomiTotalDO.setRemainQty(remainQty);
        shikomiTotalDO.setAvgOrdQty(avgeQty);
        shikomiTotalDO.setQtyPO(purseQty);
//        shikomiTotalDO.setQtyOrdPre(ordPreQty);
        shikomiTotalDO.setQtyOnhand(onhand);

        this.updateShikomiInfo(shikomiTotalDO);
    }

    @Async
    public void updateShikomiInfo(ShikomiTotalDO totalDO) {
        LambdaUpdateWrapper<ShikomiTotalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ShikomiTotalDO::getId, totalDO.getId())
                .set(ShikomiTotalDO::getQtyOnhand, totalDO.getQtyOnhand())
                .set(ShikomiTotalDO::getAvgOrdQty, totalDO.getAvgOrdQty())
                .set(ShikomiTotalDO::getQtyPO, totalDO.getQtyPO());

        shikomiTotalMapper.update(null, updateWrapper);
    }

    @Override
    public ResultVo<String> updateShikomiWarnQty(ShikomiWarnCallBackDTO dto) {
        if (dto.getQtyWarning() <=0) {
            return ResultVo.failure("警告数不能小于或等于0");
        }
        LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShikomiTotalDO::getShikomiNo, dto.getShikomiNo());
        queryWrapper.eq(ShikomiTotalDO::getModelNo, dto.getModelNo());
        ShikomiTotalDO totalDO = shikomiTotalMapper.selectOne(queryWrapper);

        if (totalDO == null) {
            return ResultVo.failure("改shikomi不存在或者不为主型号");
        }
        if (totalDO.getRemainQty() < dto.getQtyWarning()) {
            return ResultVo.failure("警告数必须小于残数");
        }

        LambdaUpdateWrapper<ShikomiTotalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ShikomiTotalDO::getShikomiNo, dto.getShikomiNo());
        updateWrapper.eq(ShikomiTotalDO::getModelNo, dto.getModelNo());
        updateWrapper.set(ShikomiTotalDO::getQtyWarning, dto.getQtyWarning());

        int update = shikomiTotalMapper.update(null, updateWrapper);

        return update == 1 ? ResultVo.success("修改成功") : ResultVo.failure("修改shikomi警告数失败");
    }

    @Override
    public ResultVo<PageInfo<ShikomiBudgetVO>> findShikomiBudget(ShikomiBudgetRequest request) {
        if(request == null) {
            return ResultVo.failure("入参不可为空");
        }
        LambdaQueryWrapper<ShikomiBudgetDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .likeRight(StringUtils.isNotBlank(request.getBatchNo()),ShikomiBudgetDO::getBatchNo,request.getBatchNo())
                .in(CollectionUtil.isNotEmpty(request.getSupplierCode()),ShikomiBudgetDO::getSupplierCode,request.getSupplierCode())
                .likeRight(StringUtils.isNotBlank(request.getModelNo()),ShikomiBudgetDO::getModelNo,request.getModelNo())
                .likeRight(StringUtils.isNotBlank(request.getShikomiNo()),ShikomiBudgetDO::getShikomiNo,request.getShikomiNo())
                .eq(StringUtils.isNotBlank(request.getBelongCustomerNo()),ShikomiBudgetDO::getBelongCustomerNo,request.getBelongCustomerNo())
                .in(CollectionUtil.isNotEmpty(request.getDeptNo()),ShikomiBudgetDO::getDeptNo,request.getDeptNo())
                .likeRight(StringUtils.isNotBlank(request.getIndCode()),ShikomiBudgetDO::getIndCode,request.getIndCode())
                .in(CollectionUtil.isNotEmpty(request.getClassCode()),ShikomiBudgetDO::getClassCode,request.getClassCode())
                .likeRight(StringUtils.isNotBlank(request.getApplyNo()),ShikomiBudgetDO::getApplyNo,request.getApplyNo())
                .eq(StringUtils.isNotBlank(request.getApplicantNo()),ShikomiBudgetDO::getApplicationName,request.getApplicantNo())
                .in(CollectionUtil.isNotEmpty(request.getInspectStatus()),ShikomiBudgetDO::getInspectStatus,request.getInspectStatus())
                .apply(CollectionUtil.isNotEmpty(request.getInspectType()),"SUBSTRING(inspect_type, LEN(inspect_type) - 1, 1) IN (" +
                        request.getInspectType().stream()
                                .map(s -> "'" + s + "'")
                                .collect(Collectors.joining(",")) + ")")
                .in(CollectionUtil.isNotEmpty(request.getInspectConfirmType()),ShikomiBudgetDO::getInspectConfirmType,request.getInspectConfirmType());

        PageInfo<ShikomiBudgetDO> pageInfo = PageHelper.startPage(request.getPage().getPageNumber(), request.getPage().getPageSize()).doSelectPageInfo(() -> {
            shikomiBudgetMapper.selectList(lambdaQueryWrapper);
        });
        PageInfo<ShikomiBudgetVO> shikomiBudgetVOPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, ShikomiBudgetVO.class);
        shikomiBudgetVOPageInfo.setList(conventShikomiBudgetData(shikomiBudgetVOPageInfo.getList()));
        return ResultVo.success(shikomiBudgetVOPageInfo);
    }

    @Override
    public void exportShikomiBudget(ShikomiBudgetRequest request,  HttpServletResponse response) {
        if(request == null) {
            return;
        }
        LambdaQueryWrapper<ShikomiBudgetDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .likeRight(StringUtils.isNotBlank(request.getBatchNo()),ShikomiBudgetDO::getBatchNo,request.getBatchNo())
                .in(CollectionUtil.isNotEmpty(request.getSupplierCode()),ShikomiBudgetDO::getSupplierCode,request.getSupplierCode())
                .likeRight(StringUtils.isNotBlank(request.getModelNo()),ShikomiBudgetDO::getModelNo,request.getModelNo())
                .likeRight(StringUtils.isNotBlank(request.getShikomiNo()),ShikomiBudgetDO::getShikomiNo,request.getShikomiNo())
                .eq(StringUtils.isNotBlank(request.getBelongCustomerNo()),ShikomiBudgetDO::getBelongCustomerNo,request.getBelongCustomerNo())
                .in(CollectionUtil.isNotEmpty(request.getDeptNo()),ShikomiBudgetDO::getDeptNo,request.getDeptNo())
                .likeRight(StringUtils.isNotBlank(request.getIndCode()),ShikomiBudgetDO::getIndCode,request.getIndCode())
                .in(CollectionUtil.isNotEmpty(request.getClassCode()),ShikomiBudgetDO::getClassCode,request.getClassCode())
                .likeRight(StringUtils.isNotBlank(request.getApplyNo()),ShikomiBudgetDO::getApplyNo,request.getApplyNo())
                .eq(StringUtils.isNotBlank(request.getApplicantNo()),ShikomiBudgetDO::getApplicationName,request.getApplicantNo())
                .in(CollectionUtil.isNotEmpty(request.getInspectStatus()),ShikomiBudgetDO::getInspectStatus,request.getInspectStatus())
                .in(CollectionUtil.isNotEmpty(request.getInspectType()),ShikomiBudgetDO::getInspectType,request.getInspectType())
                .in(CollectionUtil.isNotEmpty(request.getInspectConfirmType()),ShikomiBudgetDO::getInspectConfirmType,request.getInspectConfirmType());

        List<ShikomiBudgetDO> shikomiBudgetDOS = shikomiBudgetMapper.selectList(lambdaQueryWrapper);

        List<ShikomiBudgetVO> shikomiBudgetVOS = conventShikomiBudgetData(BeanCopyUtil.copyList(shikomiBudgetDOS, ShikomiBudgetVO.class));

        try {
            String fileName = URLEncoder.encode("Shikomi决算", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");

            InputStream inputStream = new ClassPathResource(CommonConstants.common_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), ShikomiBudgetVO.class)
                    .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(shikomiBudgetVOS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultVo<String> shikomiInspectionCalcNew() {

        String batchNo = "JS"+DateUtil.getYearMonthCode(new Date());
        // 清空当前批次的计算结果
        LambdaQueryWrapper<ShikomiBudgetDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShikomiBudgetDO::getBatchNo,batchNo);
        shikomiBudgetMapper.delete(queryWrapper);

        // shikomi基础数据转换
        conventShikomiBaseData();

        // 查询SHIKOMI临时表
        List<TmpShikomiBaseDO> shikomiBaseData = getShikomiBaseData();
        if(CollectionUtil.isEmpty(shikomiBaseData)) {
            return ResultVo.success("暂无待决算的数据");
        }

        LambdaQueryWrapper<ShikomiTotalDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaUpdateWrapper<TmpShikomiBaseDO> updateWrapper = new LambdaUpdateWrapper<>();
        StringBuilder errorMsg = new StringBuilder();
        for (TmpShikomiBaseDO item : shikomiBaseData) {
            StringBuilder inspectType = new StringBuilder();
            lambdaQueryWrapper.clear();
            lambdaQueryWrapper.eq(ShikomiTotalDO::getShikomiNo,item.getShikomiNo()).eq(ShikomiTotalDO::getStatus,1);
            ShikomiTotalDO shikomiTotalDO = shikomiTotalMapper.selectOne(lambdaQueryWrapper);
            if(shikomiTotalDO == null) {
                errorMsg.append(item.getShikomiNo()+"未获取到shikomi信息;");
                continue;
            }
            /**
             * 1.1.1	若是1，写入shikomi_budget表点检类型记录为C追加申请中；
             * 1.1.2	若是2，写入shikomi_budget表点检类型记录为B清算中止中
             * 1.1.3	若是3，写入shikomi_budget表点检类型记录为A继续申请中。
             */
            if(ShikomiInspectApplyTypeEnum.sqxj.getCode().equals(item.getApplyType())) {
                inspectType.append(ShikomiInspectTypesEnum.zjsqz.getCode()).append(";");
            } else if(ShikomiInspectApplyTypeEnum.sqzz.getCode().equals(item.getApplyType())) {
                inspectType.append(ShikomiInspectTypesEnum.qszzz.getCode()).append(";");
            } else if (ShikomiInspectApplyTypeEnum.sqjx.getCode().equals(item.getApplyType())) {
                inspectType.append(ShikomiInspectTypesEnum.jxsqz.getCode()).append(";");
            }

            if(shikomiTotalDO.getPlanUseDate() == null ) {
                errorMsg.append(item.getShikomiNo()+"预计晚使用月份为空;");
                continue;
            }

            // 当前日期>预计晚使用月份 => 逾期项
            if(new Date().after(shikomiTotalDO.getPlanUseDate())) {
                inspectType.append(ShikomiInspectTypesEnum.yqx.getCode()).append(";");
            } else {

                /**
                 * 若当天时间<预计晚使用月份，则继续判断SHIKOMI未使用月数，
                 * A:SHIKOMI未使用月数<3，则记录G正常消耗项；
                 * B:若3<=SHIKOMI未使用月数<6，则记录为D短期滞留项；
                 * C:若SHIKOMI未使用月数>=6，则记录为E长期滞留项。
                 */

                if(shikomiTotalDO.getQtyNoord() == null) {
                    shikomiTotalDO.setQtyNoord(0);
                }
                if (shikomiTotalDO.getQtyNoord() < 3) {
                    inspectType.append(ShikomiInspectTypesEnum.zcxhx.getCode()).append(";");
                }

                if (shikomiTotalDO.getQtyNoord()  >= 3 && shikomiTotalDO.getQtyNoord() < 6) {
                    inspectType.append(ShikomiInspectTypesEnum.dqzlx.getCode()).append(";");
                }

                // 未订货月数>=6   长期滞留项
                if (shikomiTotalDO.getQtyNoord() >= 6) {
                    inspectType.append(ShikomiInspectTypesEnum.cqzlx.getCode()).append(";");
                }

                // 当天日期>计划月份 && (申请数量 - shikomi残数) <客户需求计划数量 => 逾期项
                if (item.getPlanUseMonth() != null) {
                    if (new Date().after(item.getPlanUseMonth()) && (shikomiTotalDO.getApplyQty() - shikomiTotalDO.getRemainQty() ) < item.getPlanUseMonthQty()) {
                        inspectType.append(ShikomiInspectTypesEnum.yqx.getCode()).append(";");
                    }
                }
            }

            // 计算SHIKOMI追加需要提前月数(预留月数)
            shikomiTotalDO.setReserveMonth(getReserveMonthByShikomiCal(shikomiTotalDO));
            // 计算在库/在途/近6个月平均数量并回更shikomi_total表
            getAllQtyByShikomiCal(shikomiTotalDO);
            // 计入计算结果表
            String inspectTypeStr = PublicUtil.getLastStrBySeparator(inspectType.toString());
            addShikomiBudget(shikomiTotalDO,item.getBatchNo(),inspectTypeStr);

            // 修改临时表数据为已处理
            updateWrapper.clear();
            updateWrapper.eq(TmpShikomiBaseDO::getId,item.getId()).set(TmpShikomiBaseDO::getHandStatus,1);
            tmpShimokiBaseMapper.update(null,updateWrapper);

        }
        // 决算数据推送门户中间表
        shikomiAdjustDataPush();
        return ResultVo.success("计算完成");
    }

    public void shikomiAdjustDataPush() {
        String batchNo = "JS"+DateUtil.getYearMonthCode(new Date());
        LambdaQueryWrapper<ShikomiBudgetDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShikomiBudgetDO::getBatchNo,batchNo);
        List<ShikomiBudgetDO> shikomiBudgetDOS = shikomiBudgetMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(shikomiBudgetDOS)) {
            log.error(batchNo+"暂无可决算数据推送");
            return;
        }

        LambdaQueryWrapper<ShikomiBudgetForSalesDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(ShikomiBudgetForSalesDO::getBatchNo,batchNo);
        shikomiBudgetForSalesMapper.delete(queryWrapper1);

        for(ShikomiBudgetDO item: shikomiBudgetDOS){
            ShikomiBudgetForSalesDO copy = BeanCopyUtil.copy(item, ShikomiBudgetForSalesDO.class);
            copy.setId(null);

            // 备库地点
            if(StringUtils.isNotBlank(copy.getSupplierCode())) {
                copy.setSupplierCodeName(commonService.getSupplierNameByCode(copy.getSupplierCode()));
            }

            // SHIKOMI客户范围 ShikomiClassCodeEnum
            if(StringUtils.isNotBlank(copy.getClassCode())) {
                copy.setClassCodeName(ShikomiClassCodeEnum.getName(copy.getClassCode()));
            }

            // shikomi区分
            if(StringUtils.isNotBlank(copy.getClassType())) {
                copy.setClassTypeName(ShikomiClassTypeEnum.getName(copy.getClassType()));
            }

            // 点检类型 只推送优先级最高的
            if (copy.getInspectType() != null) {
                String[] split = copy.getInspectType().split(";");
                List<String> list1 = Arrays.asList(split);
                List<String> filteredList = list1.stream()
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                Collections.reverse(filteredList);
                copy.setInspectType(filteredList.get(0));
            }
            shikomiBudgetForSalesMapper.insert(copy);
        }
    }

    @Override
    public void conventShikomiBaseData() {

        // 查询国内管理品 有效的数据 (shikomi_total)
        LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ShikomiTotalDO::getCompanyCode,"CN")
                .eq(ShikomiTotalDO::getStatus,1);  // 1 有效 2取消
        List<ShikomiTotalDO> shikomiTotalDOS = shikomiTotalMapper.selectList(queryWrapper);
        if(CollectionUtil.isEmpty(shikomiTotalDOS)) {
            return;
        }

        String batchNo = "JS"+DateUtil.getYearMonthCode(new Date());
        for(ShikomiTotalDO item: shikomiTotalDOS) {
            try {
                TmpShikomiBaseDO baseDO = new TmpShikomiBaseDO();
                baseDO.setBatchNo(batchNo);
                baseDO.setShikomiNo(item.getShikomiNo());
                baseDO.setModelNo(item.getModelNo());
                baseDO.setCreateUser(CommonConstants.COMMON_USER_OPS);
                baseDO.setCreateTime(new Date());
                baseDO.setHandStatus(0);
                int planUseQty = 0;
                int ltCurMonthQty = 0;
                if(StringUtils.isNotBlank(item.getApplyNo())) {
                    // 申请号以SHM开头
                    if(item.getApplyNo().startsWith("SHM")) {
                        List<PreStockDetailViewVO> preStockDetailViewByApplyNo = shikomiTotalMapper.getPreStockDetailViewByApplyNo(item.getApplyNo());
                        if (CollectionUtil.isEmpty(preStockDetailViewByApplyNo)) {
                            continue;
                        }
                        baseDO.setApplyDeptNo(preStockDetailViewByApplyNo.get(0).getDeptNo());
                        boolean existCurMonth = false;

                        for (PreStockDetailViewVO  viewVO : preStockDetailViewByApplyNo) {
                            planUseQty += viewVO.getQuantity();

                            if (isBeforeCurrentMonth(viewVO.getDlvDateJP())) {
                                ltCurMonthQty +=  viewVO.getQuantity();
                            }

                            if (isSameMonth(viewVO.getDlvDateJP())) {
                                // 存在当前月
                                existCurMonth = true;
                                baseDO.setPlanUseMonth(viewVO.getDlvDateJP());
                                baseDO.setPlanUseMonthQty(planUseQty);
                                break;
                            }
                        }
                        // 不存在当前月
                        if(!existCurMonth) {
                            String dateStr = DateUtil.getYearMonth(new Date())+"-01";
                            baseDO.setPlanUseMonthQty(ltCurMonthQty);
                            baseDO.setPlanUseMonth(DateUtil.stringToDate(dateStr));
                        }
                    } else {
                        baseDO.setPlanUseMonthQty(item.getApplyQty());
                        baseDO.setPlanUseMonth(item.getPlanUseDate());
                    }
                } else {
                    if(item.getApplyQty() == null) {
                        item.setApplyQty(0);
                    }
                    baseDO.setPlanUseMonthQty(item.getApplyQty());
                    baseDO.setPlanUseMonth(item.getPlanUseDate());
                }

                // 根据shikomi匹配,若已存在 则更新计划月份 客户需求计划  反之 新增 申请类型为0无申请
                LambdaQueryWrapper<TmpShikomiBaseDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(TmpShikomiBaseDO::getShikomiNo,item.getShikomiNo());
                List<TmpShikomiBaseDO> tmpShikomiBaseDOS = tmpShimokiBaseMapper.selectList(lambdaQueryWrapper);
                if(CollectionUtil.isEmpty(tmpShikomiBaseDOS)) {
                    baseDO.setApplyType(ShikomiInspectApplyTypeEnum.wsq.getCode());
                    tmpShimokiBaseMapper.insert(baseDO);
                } else {
                    LambdaUpdateWrapper<TmpShikomiBaseDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper
                            .eq(TmpShikomiBaseDO::getShikomiNo,item.getShikomiNo())
                            .set(TmpShikomiBaseDO::getPlanUseMonth,item.getPlanUseDate())
                            .set(TmpShikomiBaseDO::getPlanUseMonthQty,item.getApplyQty());
                    tmpShimokiBaseMapper.update(null,updateWrapper);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("异常数据: {}, 异常原因 {}",JSONUtil.toJsonStr(item),e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return;
    }

    @Override
    public ResultVo<String> upShikomiBudjet(ShikomiBudgetVO shikomiBudgetVO) {
        LambdaUpdateWrapper<ShikomiBudgetDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(ShikomiBudgetDO::getBatchNo,shikomiBudgetVO.getBatchNo())
                .eq(ShikomiBudgetDO::getShikomiNo,shikomiBudgetVO.getShikomiNo())
                .set(StringUtils.isNotBlank(shikomiBudgetVO.getInspectAnswerText()),ShikomiBudgetDO::getInspectAnswerText,shikomiBudgetVO.getInspectAnswerText())
                .set(shikomiBudgetVO.getInspectStatus() != null,ShikomiBudgetDO::getInspectStatus,shikomiBudgetVO.getInspectStatus())
                .set(shikomiBudgetVO.getInspectConfirmType() != null,ShikomiBudgetDO::getInspectConfirmType,shikomiBudgetVO.getInspectConfirmType())
                .set(shikomiBudgetVO.getLastUseDate() != null,ShikomiBudgetDO::getLastUseDate,shikomiBudgetVO.getLastUseDate())
                .set(ShikomiBudgetDO::getUpdateTime,new Date())
                .set(StringUtils.isNotBlank(shikomiBudgetVO.getRemark()),ShikomiBudgetDO::getRemark,shikomiBudgetVO.getRemark());
        shikomiBudgetMapper.update(null,updateWrapper);
        return ResultVo.success("更新成功");
    }

    @Override
    public ResultVo<List<ShikomiBudgetDO>> findAdjustBatchNo(String batchNo) {

        LambdaQueryWrapper<ShikomiBudgetDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ShikomiBudgetDO::getBatchNo);
        queryWrapper.likeRight(StringUtils.isNotBlank(batchNo),ShikomiBudgetDO::getBatchNo,batchNo);
        queryWrapper.groupBy(ShikomiBudgetDO::getBatchNo);
        List<ShikomiBudgetDO> shikomiBudgetDOS = shikomiBudgetMapper.selectList(queryWrapper);
        return ResultVo.success(shikomiBudgetDOS);
    }

    @Override
    public ResultVo<ShikomiVO> getSupplierByShikomi(String shikomiNo) {
        if (StringUtils.isBlank(shikomiNo)) {
            return ResultVo.failure("入参不可为空");
        }
        LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ShikomiTotalDO::getShikomiNo,shikomiNo)
                .eq(ShikomiTotalDO::getStatus,1);
        List<ShikomiTotalDO> list = shikomiTotalMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return ResultVo.failure(shikomiNo+"暂未查询到数据.");
        }
        ShikomiVO shikomiVO = new ShikomiVO();
        shikomiVO.setSupplierCode(list.get(0).getSupplierCode());
        shikomiVO.setRohs(list.get(0).getRohs());
        shikomiVO.setRemainQty(list.get(0).getRemainQty());
        shikomiVO.setQtyOrdPre(list.get(0).getQtyOrdPre()); // 订单预约数量
        return ResultVo.success(shikomiVO);
    }

    public static boolean isBeforeCurrentMonth(Date date) {
        // 获取当前日期
        Date currentDate = new Date();

        // 创建Calendar对象
        Calendar calendar = Calendar.getInstance();

        // 设置calendar为当前日期
        calendar.setTime(currentDate);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        // 设置calendar为给定日期
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        // 比较年份和月份
        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSameMonth(Date date) {
        // 获取当前日期
        Date currentDate = new Date();

        // 创建Calendar对象
        Calendar calendar = Calendar.getInstance();

        // 设置calendar为当前日期
        calendar.setTime(currentDate);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        // 设置calendar为给定日期
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        // 比较年份和月份
        return year == currentYear && month == currentMonth;
    }

    public void addShikomiBudget(ShikomiTotalDO totalDO,String batchNo,String inspectType) {
        ShikomiBudgetDO shikomiBudgetDO = new ShikomiBudgetDO();
        shikomiBudgetDO.setBatchNo(batchNo);
        shikomiBudgetDO.setDeptNo(totalDO.getDeptNo());
        shikomiBudgetDO.setSupplierCode(totalDO.getSupplierCode());
        shikomiBudgetDO.setModelNo(totalDO.getModelNo());
        shikomiBudgetDO.setShikomiNo(totalDO.getShikomiNo());
        shikomiBudgetDO.setApplyNo(totalDO.getApplyNo());
        shikomiBudgetDO.setApplyTime(totalDO.getApplyTime());
        shikomiBudgetDO.setApplyQty(totalDO.getApplyQty());
        shikomiBudgetDO.setRemainQty(totalDO.getRemainQty());
        shikomiBudgetDO.setQtyOrderPre(totalDO.getQtyOrdPre());
        shikomiBudgetDO.setClassType(totalDO.getClassType());
        shikomiBudgetDO.setClassCode(totalDO.getClassCode());
        LambdaQueryWrapper<ShikomiCustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ShikomiCustomerDO::getCustomerNo).eq(ShikomiCustomerDO::getShikomiNo,totalDO.getShikomiNo());
        List<ShikomiCustomerDO> shikomiCustomerDOS = shikomiCustomerMapper.selectList(queryWrapper);
        StringBuilder stringBuilder = new StringBuilder();
        if (CollectionUtil.isNotEmpty(shikomiCustomerDOS)) {
           for (ShikomiCustomerDO item : shikomiCustomerDOS) {
               stringBuilder.append(item.getCustomerNo()).append(",");
           }
           if (stringBuilder.toString().length() > 0) {
               shikomiBudgetDO.setAvliableCustomerNo(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
           }
        }
        shikomiBudgetDO.setBelongCustomerNo(totalDO.getCustomerNo());
        shikomiBudgetDO.setIndCode(totalDO.getIndCode());
        shikomiBudgetDO.setQtyNoord(totalDO.getQtyNoord());
        shikomiBudgetDO.setQtyOnhand(totalDO.getQtyOnhand());
        shikomiBudgetDO.setQtyPo(totalDO.getQtyPO());
        shikomiBudgetDO.setAvgOrdQty(totalDO.getAvgOrdQty());
        shikomiBudgetDO.setReserveMonth(totalDO.getReserveMonth());
        shikomiBudgetDO.setInspectStatus(ShikomiInspectStatusEnum.ddj.getCode());
        shikomiBudgetDO.setInspectType(inspectType);
        shikomiBudgetDO.setApplicantNo(totalDO.getApplicantNo());
        shikomiBudgetDO.setApplicationName(totalDO.getApplicantName());
        shikomiBudgetDO.setCreateTime(new Date());
        shikomiBudgetDO.setCreateUser(CommonConstants.COMMON_USER_OPS);
        shikomiBudgetDO.setUpdateTime(new Date());
        shikomiBudgetMapper.insert(shikomiBudgetDO);
    }

    /**
     * 计算在库/在途/近6个月平均数量
     */
    private void getAllQtyByShikomiCal(ShikomiTotalDO shikomiTotalDO) {
        LambdaQueryWrapper<VShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VShikomiTotalDO::getShikomiNo, shikomiTotalDO.getShikomiNo());
        queryWrapper.eq(VShikomiTotalDO::getStatus, 1);
        List<VShikomiTotalDO> totalDOList = vShikomiTotalMapper.selectList(queryWrapper);

        int avgeQty = 0;
        int purseQty = 0; // 采购中数量
        int onhand = 0;

        VShikomiTotalDO totalDO = totalDOList.get(0);
        List<String> list = totalDOList.stream().map(VShikomiTotalDO::getModelNo).collect(Collectors.toList());
        // 专享可借用时/客户专享 按照客户来
        if (ShikomiClassCodeEnum.CustomerShare.getCode().equals(totalDO.getClassCode()) || ShikomiClassCodeEnum.CustomerPrivate.getCode().equals(totalDO.getClassCode())) {
            ShikomiTotalDO total = vShikomiTotalMapper.getpurchaseQty3(list, totalDO.getCustomerNo());
            if (total != null) {
                onhand = Optional.ofNullable(total.getQtyOnhand()).orElse(0);
                purseQty = Optional.ofNullable(total.getQtyPO()).orElse(0);
            }
            ShikomiTotalDO totalDO1 = vShikomiTotalMapper.getpurchaseQty(list);
            if (totalDO1 != null) {
                onhand += Optional.ofNullable(totalDO1.getQtyOnhand()).orElse(0);
                purseQty += Optional.ofNullable(totalDO1.getQtyPO()).orElse(0);
            }
            List<Integer> orders = vShikomiTotalMapper.getOrdersByNo(list, totalDO.getCustomerNo());
            if (CollectionUtil.isNotEmpty(orders)) {
                avgeQty = orders.stream().mapToInt(Integer::intValue).sum();
            }
        } else {
            ShikomiTotalDO total = vShikomiTotalMapper.getpurchaseQty2(list);
            if (total != null) {
                onhand = total.getQtyOnhand();
                purseQty = total.getQtyPO();
            }
            List<Integer> orders = vShikomiTotalMapper.getOrdersByNo2(list);
            if (CollectionUtil.isNotEmpty(orders)) {
                avgeQty = orders.stream().mapToInt(Integer::intValue).sum();
            }
        }
//        shikomiTotalDO.setRemainQty(remainQty);
        shikomiTotalDO.setAvgOrdQty(avgeQty);
        shikomiTotalDO.setQtyPO(purseQty);
//        shikomiTotalDO.setQtyOrdPre(ordPreQty);
        shikomiTotalDO.setQtyOnhand(onhand);

        this.updateShikomiInfo(shikomiTotalDO);
    }

    /**
     * 计算预留月数
     * 如果部品准备工作日和组装工作日为0 则默认4
     * 否则预留月数等于 (准备工作日 + 组装工作日 + 20 ) / 20
     * @return  预留月数
     */
    public int getReserveMonthByShikomiCal(ShikomiTotalDO totalDO) {
        int reserveMonth = 0; //预留月数
        if (PublicUtil.isEmpty(totalDO.getPartPrepareDays())) {
            totalDO.setPartPrepareDays(0);
        }
        if (PublicUtil.isEmpty(totalDO.getAsseDays())) {
            totalDO.setAsseDays(0);
        }
        if (PublicUtil.isEmpty(totalDO.getAvgOrdQty())) {
            totalDO.setAvgOrdQty(0);
        }
        if (totalDO.getPartPrepareDays() <= 0 && totalDO.getAsseDays() <= 0) {
            reserveMonth = 4;
        } else {
            BigDecimal decimal = BigDecimalUtil.div(totalDO.getAsseDays() + totalDO.getPartPrepareDays() + 20, 20, 2);
            Double ceil = Math.ceil(decimal.doubleValue());
            reserveMonth = ceil.intValue();
        }
        return reserveMonth;
    }


    public List<TmpShikomiBaseDO> getShikomiBaseData() {
        return tmpShimokiBaseMapper.selectList(new LambdaQueryWrapper<TmpShikomiBaseDO>().eq(TmpShikomiBaseDO::getHandStatus,"0"));
    }


    public List<ShikomiBudgetVO> conventShikomiBudgetData(List<ShikomiBudgetVO> list) {
        if (CollectionUtil.isEmpty(list)) {
            return list;
        }
        for (ShikomiBudgetVO item : list) {

            // 营业所
            if(StringUtils.isNotBlank(item.getDeptNo())) {
                item.setDeptNo(commonService.getDeptNameByNo(item.getDeptNo()));
            }
            // 备库地点
            if(StringUtils.isNotBlank(item.getSupplierCode())) {
                item.setSupplierCode(commonService.getSupplierNameByCode(item.getSupplierCode()));
            }

            // SHIKOMI客户范围 ShikomiClassCodeEnum
            if(StringUtils.isNotBlank(item.getClassCode())) {
                item.setClassCode(ShikomiClassCodeEnum.getName(item.getClassCode()));
            }

//            // 有权限订货的客户
//            if(StringUtils.isNotBlank(item.getAvliableCustomerNo())) {
//                item.setAvliableCustomerNo("["+item.getAvliableCustomerNo()+"]"+commonService.getCustomerNameByNo(item.getAvliableCustomerNo()));
//            }

            // 归属客户
//            if(StringUtils.isNotBlank(item.getBelongCustomerNo())) {
//                item.setBelongCustomerNo("["+item.getBelongCustomerNo()+"]"+commonService.getCustomerNameByNo(item.getBelongCustomerNo()));
//            }

            // 点检状态
            if (item.getInspectStatus() != null) {
                item.setInspectStatusName(ShikomiInspectStatusEnum.getCodeNameByCode(item.getInspectStatus()));
            }
            // 点检类型
            if (item.getInspectType() != null) {
                String[] split = item.getInspectType().split(";");
                List<String> list1 = Arrays.asList(split);
                List<String> filteredList = list1.stream()
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                Collections.reverse(filteredList);
                item.setInspectTypeName(ShikomiInspectTypesEnum.getCodeNameByCode(Integer.parseInt(filteredList.get(0))));
            }
            // 营业所点检确认
            if (item.getInspectConfirmType() != null) {
                item.setInspectConfirmTypeName(ShikomiInspectConfirmTypeEnum.getCodeNameByCode(item.getInspectConfirmType()));
            }
        }
        return list;
    }

    @Override
    public ResultVo<String> checkStatus() {
        Object o = redisUtil.get("ops:shikomi:status");

        if (o == null || o.toString().equals("0")) {
            return ResultVo.success("待执行计算");
        }
        if (o.toString().equals("1")) {
            return ResultVo.success("正在计算");
        }
        if (o.toString().equals("2")) {
            return ResultVo.success("计算已完成，待发送");
        }
        return ResultVo.success("错误");
    }

    @Override
    public ResultVo<String> calcShikomiInspection(MultipartFile file) {
        Object o1 = redisUtil.get("ops:shikomi:status");

        if (o1 != null && o1.toString().equals("1")) {
            return ResultVo.failure("正在计算，请勿重复操作");
        }

        log.info("开始执行发送点检邮件计算");
        redisUtil.set("ops:shikomi:status", 1);
        QueryWrapper<ShikomiTotalDO> query = new QueryWrapper<>();
        query.eq("CompanyCode", "CN");
        query.eq("Status", 1);
        query.isNotNull("ApproverEmail");
        query.ne("ApproverEmail", "");
        List<ShikomiTotalDO> list = shikomiTotalMapper.selectList(query);
        List<ShikomiTotalDO> totalDOList = new ArrayList<>();
        Map<String, ShikomiTotalDO> tatalMap = getApproverShikomiInfoForFile(file);
        for (ShikomiTotalDO totalDO : list) {
            if (PublicUtil.isEmpty(totalDO.getDeptNo())) {
                continue;
            }
            List<ShikomiTotalDO> doList;
            /**
             * 1 清算中止中
             * 2 追加申请中
             * 3 继续申请中
             * 4 逾期项
             * 5 正常消耗项
             * 6 短期滞留项
             * 7 长期滞留项
             */
            // 当前日 > 预计晚使用月份  ==> 点检类型 逾期项
            if (PublicUtil.isNotEmpty(totalDO.getPlanUseDate()) && DateUtil.getDiffMonth(new Date(), totalDO.getPlanUseDate()) < 0) {
                totalDO.setInspectType(4);

            } else {
                if (PublicUtil.isEmpty(totalDO.getQtyNoord())) {
                    totalDO.setQtyNoord(0);
                }
                // 未订货月数 < 3 正常消耗项
                if (totalDO.getQtyNoord() < 3) {
                    totalDO.setInspectType(5);
                }
                // 未订货月数 >=3 <6  短期滞留项
                if (totalDO.getQtyNoord() >= 3 && totalDO.getQtyNoord() < 6) {
                    totalDO.setInspectType(6);
                }
                // 长期滞留项
                if (totalDO.getQtyNoord() >= 6) {
                    totalDO.setInspectType(7);
                }

            }

            if (tatalMap.containsKey(totalDO.getModelNo())) {
                ShikomiTotalDO shikomiTotalDO = tatalMap.get(totalDO.getModelNo());
                // 点检申请类型 ==  全部终止   点检类型为追加申请中
                if (shikomiTotalDO.getInspectApplyType() == 1 && totalDO.getDeptNo().equalsIgnoreCase(shikomiTotalDO.getDeptNo())) {
                    totalDO.setInspectType(2);
                } else if (shikomiTotalDO.getInspectApplyType() == 2 && totalDO.getShikomiNo().equalsIgnoreCase(shikomiTotalDO.getShikomiNo())) {
                    // 点检申请类型为 申请继续 点检类型为 清算中止中
                    totalDO.setInspectType(1);
                } else if (shikomiTotalDO.getInspectApplyType() == 3 && totalDO.getShikomiNo().equalsIgnoreCase(shikomiTotalDO.getShikomiNo())) {
                    // 点检申请类型为 部分中止 点检类型为 继续申请中
                    totalDO.setInspectType(3);
                }
            }

            Object o = redisUtil.hGet("ops:shikomi:inspectionEmail", totalDO.getApproverEmail());
            if (o != null) {
                doList = JSON.parseArray(o.toString(), ShikomiTotalDO.class);
            } else {
                doList = new ArrayList<>();
            }
            totalDO.setInspectSendTime(new Date());
            ShikomiWaringDTO dto = this.getQtyWaring(totalDO);
            this.getAllQty(totalDO);
            totalDO.setReserveMonth(dto.getReserveMonth());
            doList.add(totalDO);
            totalDOList.add(totalDO);

            redisUtil.hPut("ops:shikomi:inspectionEmail", totalDO.getApproverEmail(), JSON.toJSONString(doList));
        }
        redisUtil.set("ops:shikomi:status", 2);

        return ResultVo.success();
    }

    @Override
    public ResultVo<String> checkShikomiInspectionByDept() {

        Object o = redisUtil.get("ops:shikomi:status");
        if (o == null || !o.toString().equals("2")) {
            return ResultVo.failure("请先计算完成");
        }
        Object obj = redisUtil.getHashKeyValue("ops:shikomi:inspectionEmail");
        List<ShikomiTotalDO> totalDOList = new ArrayList<>();
        if (obj == null) {
            return ResultVo.failure("计算异常,请重新计算");
        }
        log.info(obj.toString());
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(obj), HashMap.class);

        ResultVo<DataTypeVO> shikomiDianjian = dictCommonService.getDataTypeCodesInfo("9004", "SHIKOMI_DIANJIAN");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            List<ShikomiTotalDO> doList = JSON.parseArray(entry.getValue().toString(), ShikomiTotalDO.class);
            totalDOList.addAll(doList);
            if (shikomiDianjian.isSuccess() && shikomiDianjian.getData() != null) {
                this.sendShikomiInspectionEmail(doList, null,shikomiDianjian.getData());
            } else {
                this.sendShikomiInspectionEmail(doList, null,new DataTypeVO());
            }
        }

        // 汇总
        if (shikomiDianjian.isSuccess() && shikomiDianjian.getData() != null) {
            this.sendShikomiInspectionEmail(totalDOList, shikomiDianjian.getData().getExtNote1(),shikomiDianjian.getData());
        } else {
            this.sendShikomiInspectionEmail(totalDOList, "deliverysh@smc.com.cn;wujiawen@smc.com.cn",null);
        }
        redisUtil.del("ops:shikomi:inspectionEmail");
        redisUtil.set("ops:shikomi:status", 0);
        return ResultVo.success("成功");
    }

    private ResultVo<String> sendShikomiInspectionEmail(List<ShikomiTotalDO> list, String email,DataTypeVO dataTypeVO) {
        ShikomiTotalDO shikomiDO = list.get(0);
        String to = "";
        String tocc = "deliverysh@smc.com.cn";
        if (PublicUtil.isNotEmpty(email)) {
            to = email;
        } else {
            to = shikomiDO.getApproverEmail();
            Set<String> set = list.stream().map(ShikomiTotalDO::getApplicantEmail).collect(Collectors.toSet());
            for (Object o : set.toArray()) {
                tocc = tocc + ";" + o;
            }
        }

        if (StringUtils.isBlank(to)) {
            return ResultVo.success();
        }
        // 获取部门名称
        String deptName = opsCommonService.getDeptNameByEmployeeId(shikomiDO.getApproverNo());
        // 获取导出模板
        String path = "templates/SHIKOMI清单.xlsx";
        InputStream stream = FileUtil.getTemplate(path);
        ExcelHelper excel = new ExcelHelper(stream);
        Workbook wb = excel.getWorkBook();
        Font font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        excel.openSheet(0);

        // 向模板中写入数据
        String customer = null;
        int row = 13;
        int cel;
        excel.setCellValue(1, 0, "SHIKOMI清单--" + deptName + "确认用表--发布日期" + DateUtil.dateToString(new Date()));
        excel.setCellStyle(1, 0, font, new Short[]{null, null, null, null});
        for (ShikomiTotalDO totalDO : list) {
//            ShikomiWaringDTO dto = this.getQtyWaring(totalDO);
            if (PublicUtil.isEmpty(totalDO.getQtyOrdPre())) {
                totalDO.setQtyOrdPre(0);
            }
            customer = "";
            cel = 0;
            excel.setCellValue(row, cel++, totalDO.getShikomiNo());
            excel.setCellValue(row, cel++, totalDO.getModelNo());
            excel.setCellValue(row, cel++, totalDO.getSupplierCode());
            excel.setCellValue(row, cel++, totalDO.getCustomerNo());
            ResultVo<List<ShikomiCustomerVO>> data = findCustomerDataByNo(totalDO.getShikomiNo());
            List<ShikomiCustomerVO> customerVOList = data.getData();
            for (ShikomiCustomerVO customerVO : customerVOList) {
                customer += customerVO.getCustomerNo() + ",";
            }
            excel.setCellValue(row, cel++, customer);
            excel.setCellValue(row, cel++, ShikomiClassTypeEnum.getName(totalDO.getClassType()));
            excel.setCellValue(row, cel++, ShikomiClassCodeEnum.getName(totalDO.getClassCode()));
            excel.setCellValue(row, cel++, totalDO.getRemainQty());
            excel.setCellValue(row, cel++, totalDO.getQtyOrdPre());
            excel.setCellValue(row, cel++, totalDO.getQtyNoord());
            excel.setCellValue(row, cel++, totalDO.getQtyOnhand());
            excel.setCellValue(row, cel++, totalDO.getQtyPO());
            excel.setCellValue(row, cel++, totalDO.getAvgOrdQty()); // 6个月订货
            excel.setCellValue(row, cel++, totalDO.getReserveMonth() + "个月"); // shikomi追加需提前
            if (PublicUtil.isNotEmpty(totalDO.getIndCode())) {
                excel.setCellValue(row, cel++, totalDO.getIndCode());
            } else {
                excel.setCellValue(row, cel++, totalDO.getDeptNo());
            }
            excel.setCellValue(row, cel++, totalDO.getApplicantName());
            excel.setCellValue(row, cel, ShikomiInspectionEnum.getName(String.valueOf(totalDO.getInspectType())));

            row++;
        }
        InputStream inputStream = excel.convertTo();
        String fileName = "SHIKOMI清单_" + deptName + "_日期" + DateUtil.dateToString(new Date()) + ".xlsx";
        String subject = "SHIKOMI清单--" + deptName + "--请一周内确认";
        StringBuilder sb = new StringBuilder();
        sb.append("<span>营业所/行业  负责人" + shikomiDO.getApproverName() + "，您好！</span></br>");
        sb.append("<span>附件SHIKOMI清单，请于一周内确认好后回复在库企画课deliverysh@smc.com.cn。</span></br>");
        sb.append("本邮件由OPS系统自动发送，请勿直接回复本邮件");
        Map<String, InputStream> attachment = new LinkedHashMap<>(1);
        attachment.put(fileName, inputStream);
        String pathFile = filePath + File.separator + "shikomidianjian";
        Boolean aBoolean = FileUtil.uploadFileWithStream(inputStream, pathFile, fileName);
        if (aBoolean) {
            if (sendMailFlag.equals("dev")) {
                OpsMailDO opsMailDO = new OpsMailDO();
                opsMailDO.setMailTo(dataTypeVO.getExtNote1().replaceAll(";", ","));
                opsMailDO.setSubject(subject);
                opsMailDO.setContext(sb.toString());
                opsMailDO.setSendDate(new Date());
                opsMailDO.setCc(dataTypeVO.getExtNote2().replaceAll(";",","));
                opsMailDO.setBcc(null);
                opsMailDO.setStatus(SendStatusEnum.INIT.getType());
                opsMailDO.setFileUrls(pathFile+File.separator+fileName);
                opsMailDO.setInsertTime(new Date());
                opsCommonService.insertOpsMail(opsMailDO);
               // EmailUtil.send(javaMailSender, dataTypeVO.getExtNote1(), dataTypeVO.getExtNote2(), null, subject, sb.toString(), attachment);
            } else {
                OpsMailDO opsMailDO = new OpsMailDO();
                opsMailDO.setMailTo(to.replaceAll(";", ","));
                opsMailDO.setSubject(subject);
                opsMailDO.setContext(sb.toString());
                opsMailDO.setSendDate(new Date());
                opsMailDO.setCc(tocc.replaceAll(";",","));
                opsMailDO.setBcc(dataTypeVO.getExtNote3().replaceAll(";", ","));
                opsMailDO.setStatus(SendStatusEnum.INIT.getType());
                opsMailDO.setFileUrls(pathFile+File.separator+fileName);
                opsMailDO.setInsertTime(new Date());
                opsCommonService.insertOpsMail(opsMailDO);
                // EmailUtil.send(javaMailSender, to, tocc, dataTypeVO.getExtNote3(), subject, sb.toString(), attachment);
            }
        }

        return ResultVo.success();
    }

    private String getIndEmail(String indCode) {
        String eamil = null;
        switch (indCode) {
            case "235200":
                eamil = "weiliya@smc.com.cn,yanglushuai@smc.com.cn;deliverysh@smc.com.cn";
                break;
            case "235100":
                eamil = "liuyang@smccom.cn,wangmengxiang@smc.com.cn;deliverysh@smc.com.cn";
                break;
            case "235600":
                eamil = "liyanqiu@smc.com.cn,fuzhonglin@smc.com.cn;deliverysh@smc.com.cn";
                break;
            case "235400":
                eamil = "fangqiutong@smc.com.cn,zhenshi@smc.com.cn;deliverysh@smc.com.cn";
                break;
            case "235500":
                eamil = "qiaoling@smc.com.cn,zhangqian@smc.com.cn;deliverysh@smc.com.cn";
                break;
        }

        return eamil;
    }

    Map<String, ShikomiTotalDO> getApproverShikomiInfoForFile(MultipartFile file) {
        ShikomiTotalDO totalDO = null;
        InputStream inputStream = null;
//        List<ShikomiTotalDO> list = new ArrayList<>();
        Map<String, ShikomiTotalDO> map = new HashMap<>();
        try {
            inputStream = file.getInputStream();
            ExcelHelper excel = new ExcelHelper(inputStream);
            Sheet sheet = excel.getSheet();

            Row rows;
            int lastRowNum = sheet.getLastRowNum();
            for (int row = 1; row <= lastRowNum; row++) {
                rows = sheet.getRow(row);
                if (rows == null) {
                    break;
                }
                totalDO = new ShikomiTotalDO();
                totalDO.setShikomiNo(excel.getCellString(rows.getCell(0)).trim());
                totalDO.setModelNo(excel.getCellString(rows.getCell(1)).trim());
                totalDO.setDeptNo(excel.getCellString(rows.getCell(2)).trim());
                totalDO.setInspectApplyType(excel.getCellInteger(rows.getCell(3)));
                map.put(totalDO.getModelNo(), totalDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


    private String authorization = "";

    @Override
    public ResultVo<String> callbackToSMCShikomi(ShikomiCallbackVO shikomiCallbackVO) {
        String authorization = createTokenForOtherServer.getMHToken();

        HttpResponse response;

        String json = null;
        if (shikomiCallbackVO != null) {
            json = JSONUtil.toJsonStr(shikomiCallbackVO);
        }
        try {
            String dealShikomiUrl = menHuUrl + "/saleManageSystem/opsReturnShikomi/dealShikomi";
            response = HttpUtil.createPost(dealShikomiUrl)
                    .header("Authorization", authorization)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .charset(StandardCharsets.UTF_8)
                    .body(json)
                    .execute();
            String strResponse = response.body();
            JSONObject jsonObject = JSON.parseObject(strResponse);
            System.out.println("jsonObject = " + jsonObject);
            if (jsonObject.getString("success").equals("true")) {
                return ResultVo.success(jsonObject.getString("content"));
            } else {
                return ResultVo.failure(jsonObject.getString("message"));
            }
        } catch (Exception e) {
            throw new BusinessException("shikomi回调门户接口 error=> {} ", e);
        }
    }


    private ResultVo<String> callbackHandleType(ShikomiInspectionCallbackDTO callbackDTO) {
        if (callbackDTO.getType() == 2) {
            return ResultVo.success("成功");
        }
        String auth = createTokenForOtherServer.getMHToken();
        HttpResponse response;

        String json = null;
        if (callbackDTO != null) {
            json = JSONUtil.toJsonStr(callbackDTO);
        }
        try {
            log.info("门户回调token:" + auth);
            String shikomiStatusUrl = menHuUrl + "/saleManageSystem/opsReturnShikomi/updateShikomiStopStatus";
            response = HttpUtil.createPost(shikomiStatusUrl)
                    .header("Authorization", auth)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .charset(StandardCharsets.UTF_8)
                    .body(json)
                    .execute();
            String strResponse = response.body();
            JSONObject jsonObject = JSON.parseObject(strResponse);
            System.out.println("jsonObject = " + jsonObject);
            if (jsonObject.getString("success").equals("true")) {
                return ResultVo.success(jsonObject.getString("content"));
            } else {
                return ResultVo.failure(jsonObject.getString("message"));
            }
        } catch (Exception e) {
            throw new BusinessException("shikomi点检状态回调门户接口 error=> {} ", e);
        }
    }

    @Override
    public ResultVo<String> importShikomiNoord(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ShikomiTotalDO totalDO = null;
        ExcelHelper excel = new ExcelHelper(inputStream);
        excel.openSheet(0);
        Sheet sheet = excel.getSheet();

        Row rows;
        int nood = 0;
        int lastRowNum = sheet.getLastRowNum();
        for (int row = 1; row <= lastRowNum; row++) {
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            totalDO = new ShikomiTotalDO();

            String shikomiNo = excel.getCellString(rows.getCell(0)).trim();
            String noord = excel.getCellString(rows.getCell(6));

            if (noord.contains("ヶ月受注なし")) {
                nood = Integer.valueOf(noord.replaceAll("[^0-9]", ""));
            } else {
                nood = 0;
            }
            totalDO.setQtyNoord(nood);
            LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShikomiTotalDO::getShikomiNo, shikomiNo);

            shikomiTotalMapper.update(totalDO, queryWrapper);
        }
        return ResultVo.success("成功");
    }

    @Override
    public ResultVo<String> syncCNShikomiNoordQty() {
        LambdaQueryWrapper<ShikomiTotalDO> query = new LambdaQueryWrapper<>();
        query.eq(ShikomiTotalDO::getCompanyCode, "CN");
        query.ne(ShikomiTotalDO::getSupplierCode, "JP");
        List<ShikomiTotalDO> list = shikomiTotalMapper.selectList(query);

        Date lastMonthFirstDate = DateUtil.getLastMonthFirstDate(new Date());
        Date monthEndTime = DateUtil.clearTimeBitAfterMinute(DateUtil.getEndTime(DateUtil.getLastMonthEndDate(new Date())));

        int noord = 0;
        ShikomiTotalDO shikomiTotalDO;
        for (ShikomiTotalDO totalDO : list) {
            shikomiTotalDO = new ShikomiTotalDO();
            if (PublicUtil.isEmpty(totalDO.getQtyNoord())) {
                totalDO.setQtyNoord(0);
            }
            Integer count = shikomiTotalMapper.getShikomiNoord(totalDO.getShikomiNo(), lastMonthFirstDate, monthEndTime);
            if (count >= 1) {
                noord = 0;
            } else {
                noord = totalDO.getQtyNoord() + 1;
            }
            shikomiTotalDO.setUpdateUser("syncCN");
            shikomiTotalDO.setId(totalDO.getId());
            shikomiTotalDO.setQtyNoord(noord);
            shikomiTotalMapper.updateById(shikomiTotalDO);
        }

        return ResultVo.success("计算完成");
    }

    @Override
    public ResultVo<String> calcShikomiQtyWarning() {
        LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShikomiTotalDO::getStatus, 1);
        // queryWrapper.eq(ShikomiTotalDO::getCompanyCode, "CN");
        queryWrapper.eq(ShikomiTotalDO::getClassType, "A");

        List<ShikomiTotalDO> totalDOList = shikomiTotalMapper.selectList(queryWrapper);
        for (ShikomiTotalDO totalDO : totalDOList) {
            this.getQtyWaring(totalDO);
        }
        return ResultVo.success("计算完成");
    }

    @Override
    public ResultVo<String> importCNShikomiData(MultipartFile file) {
        LoginUserDTO loginAuthDto = null;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
            loginAuthDto.setUserNo("cnimp");
        }

        log.info("importCNShikomiData=> 导入cn shikomi数据: "+loginAuthDto.getUserNo());
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ShikomiTotalDO totalDO = null;
//        ExcelHelper excel = new ExcelHelper(inputStream);
//        Sheet sheet = excel.getSheet();

        XLSExcelUtil excel = new XLSExcelUtil(inputStream);
        HSSFSheet sheet = excel.getSheet();

//        int row = 1;
        Row rows;
        int lastRowNum = sheet.getLastRowNum();
        for (int row = 1; row <= lastRowNum; row++) {
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            totalDO = new ShikomiTotalDO();

            String shikomiNo = excel.getCellString(rows.getCell(1)).trim();
            Integer remainQty = excel.getCellInteger(rows.getCell(10));
            totalDO.setRemainQty(remainQty);
            totalDO.setQtyOrdPre(0);
            totalDO.setUpdateUser("cnimp");
            LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
            List<String> supplierCodes = new ArrayList<>();
            supplierCodes.add("CN");
            supplierCodes.add("YZ");
            queryWrapper.in(ShikomiTotalDO::getSupplierCode, supplierCodes);
            queryWrapper.eq(ShikomiTotalDO::getShikomiNo, shikomiNo);

            shikomiTotalMapper.update(totalDO, queryWrapper);

        }
//        redisUtil.del("ops:prod:shikomi:CN");
        return ResultVo.success();
    }

    @Override
    public ResultVo<PageInfo<ShikomiInspectionVO>> listPageShikomiInspection(ShikomiInspectionRequest request) {
//        LambdaQueryWrapper<ShikomiInspectionDO> queryWrapper = new LambdaQueryWrapper();
//        queryWrapper.eq(PublicUtil.isNotEmpty(request.getModelNo()), ShikomiInspectionDO::getModelNo, request.getModelNo())
//                .eq(PublicUtil.isNotEmpty(request.getShikomiNo()), ShikomiInspectionDO::getShikomiNo, request.getShikomiNo())
//                .eq(PublicUtil.isNotEmpty(request.getApplyType()), ShikomiInspectionDO::getApplyType, request.getApplyType());

//        List<ShikomiInspectionDO> doList = shikomiInspectionMapper.listShikomiInspectionData(request);
        PageInfo<ShikomiInspectionVO> pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> shikomiInspectionMapper.listShikomiInspectionData(request));
        List<ShikomiInspectionVO> list = new ArrayList<>();
        for (ShikomiInspectionVO inspectionVO : pageInfo.getList()) {
            ResultVo<String> name = commonServiceFeignApi.getEmployeeNameByNo(inspectionVO.getApplicantNo());
            inspectionVO.setApplicantName(name.getData());
            String deptNo = shikomiInspectionMapper.getEmployeeDeptNo(inspectionVO.getApplicantNo());
            inspectionVO.setDeptNo(deptNo);
            ResultVo<HROrganizationVO> resultVo = commonServiceFeignApi.findHrOrganByDeptNo(deptNo);
            inspectionVO.setDeptName(resultVo.getData().getName());
            list.add(inspectionVO);
        }
        pageInfo.setList(list);

        return ResultVo.success(pageInfo);
    }

    @Override
    public void exportShikomiInspectionData(ShikomiInspectionRequest request) {
        LambdaQueryWrapper<ShikomiInspectionDO> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getModelNo()), ShikomiInspectionDO::getModelNo, request.getModelNo())
                .eq(PublicUtil.isNotEmpty(request.getShikomiNo()), ShikomiInspectionDO::getShikomiNo, request.getShikomiNo())
                .eq(PublicUtil.isNotEmpty(request.getApplyType()), ShikomiInspectionDO::getApplyType, request.getApplyType());

        List<ShikomiInspectionDO> list = shikomiInspectionMapper.selectList(queryWrapper);

        String path = "templates/SHIKOMI中止和继续.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.openSheet(0);

        int row = 1;
        int cel;
        for (ShikomiInspectionDO inspectionDO : list) {
            cel = 0;
            excel.setCellValue(row, cel++, inspectionDO.getShikomiNo());
            excel.setCellValue(row, cel++, inspectionDO.getModelNo());
            excel.setCellValue(row, cel++, inspectionDO.getApplyNo());
            excel.setCellValue(row, cel++, inspectionDO.getApplyType());
            excel.setCellValue(row, cel++, inspectionDO.getApplyQty());
            excel.setCellValue(row, cel++, inspectionDO.getApplicantNo());
            excel.setCellValue(row, cel++, inspectionDO.getApplyDate());
            excel.setCellValue(row, cel++, inspectionDO.getRemainQty());
            excel.setCellValue(row, cel++, inspectionDO.getCancelQty());
            excel.setCellValue(row, cel++, inspectionDO.getCustomerQty());
            excel.setCellValue(row, cel++, inspectionDO.getRepairQty());
            excel.setCellValue(row, cel++, inspectionDO.getExpirationHandle());
            excel.setCellValue(row, cel++, inspectionDO.getRetentionDurationDate());

            row++;
        }

        excel.writeToResponse(response, "SHIKOMI中止和继续.xlsx");
    }

    @Override
    public ResultVo<List<ShikomiRegistDateCallbackDTO>> listShikomiRegistData(List<String> applyNos) {

        ResultVo<List<ShikomiCallbackDTO>> stockData = preStockFeignApi.getShikomiStockData(applyNos);
        List<ShikomiCallbackDTO> list = stockData.getData();

        ShikomiRegistDateCallbackDTO callbackDTO;
        List<ShikomiRegistDateCallbackDTO> dtoList = new ArrayList<>();
        for (ShikomiCallbackDTO dto : list) {
            callbackDTO = new ShikomiRegistDateCallbackDTO();
            LambdaQueryWrapper<ShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShikomiTotalDO::getApplyNo, dto.getApplyNo().split("-")[0]);
            queryWrapper.eq(ShikomiTotalDO::getModelNo, dto.getModelNo());
//            ShikomiTotalDO totalDO = shikomiTotalMapper.selectOne(queryWrapper);
            List<ShikomiTotalDO> doList = shikomiTotalMapper.selectList(queryWrapper);
            if (CollectionUtil.isEmpty(doList)) {
                continue;
            }
            ShikomiTotalDO totalDO = doList.get(0);
            callbackDTO.setApplyNo(dto.getApplyNo());
            callbackDTO.setAsseDays(totalDO.getAsseDays());
            callbackDTO.setRegistDate(totalDO.getRegistDate());
            dtoList.add(callbackDTO);
        }

        return ResultVo.success(dtoList);
    }


    private ShikomiWaringDTO getQtyWaring(ShikomiTotalDO totalDO) {
        if (PublicUtil.isEmpty(totalDO.getPartPrepareDays())) {
            totalDO.setPartPrepareDays(0);
        }
        if (PublicUtil.isEmpty(totalDO.getAsseDays())) {
            totalDO.setAsseDays(0);
        }
        if (PublicUtil.isEmpty(totalDO.getAvgOrdQty())) {
            totalDO.setAvgOrdQty(0);
        }
        int reserveMonth = 0; //预留月数
        int qtyWarning = 0; //警告数量
        // 如果部品准备工作日和组装工作日为0 则默认4
        // 否则预留月数等于 (准备工作日 + 组装工作日 + 20 ) / 20
        if (totalDO.getPartPrepareDays() <= 0 && totalDO.getAsseDays() <= 0) {
            reserveMonth = 4;
        } else {
            BigDecimal decimal = BigDecimalUtil.div(totalDO.getAsseDays() + totalDO.getPartPrepareDays() + 20, 20, 2);
            Double ceil = Math.ceil(decimal.doubleValue());
            reserveMonth = ceil.intValue();
        }

        // 如果6个月平均订货数为0 警告数量设置为0。6个月平均订货数为小数，则向上圆整
        // 警告数量 = 6个月平均订货数 * 预留月数
        if (totalDO.getAvgOrdQty() <= 0) {
            qtyWarning = 0;
        } else {
            BigDecimal div = BigDecimalUtil.div(totalDO.getAvgOrdQty(), 6, 2);
            double avgOrdQty = Math.ceil(div.doubleValue());
            qtyWarning = BigDecimalUtil.mul(avgOrdQty, reserveMonth).intValue();
        }

        // 如果产品为接头,警告数量则向上圆整为10的倍数
        ResultVo<ProductInfoVO> info = productService.getProductInfoByModelNo(totalDO.getModelNo());
        if (info.getData() != null && "接头".equals(info.getData().getChineseName())) {
            BigDecimal decimal = BigDecimalUtil.div(totalDO.getQtyWarning(), 10, 2);
            double ceil = Math.ceil(decimal.doubleValue());
            qtyWarning = BigDecimalUtil.mul(ceil, 10).intValue();
        }
        // 21081 暂时恢复自动计算
        ShikomiTotalDO shikomiTotalDO = new ShikomiTotalDO();
        shikomiTotalDO.setQtyWarning(totalDO.getQtyWarning());
        shikomiTotalDO.setId(totalDO.getId());
        shikomiTotalMapper.updateById(shikomiTotalDO);

        ShikomiWaringDTO dto = new ShikomiWaringDTO();
        dto.setQtyWarning(qtyWarning);
        dto.setReserveMonth(reserveMonth);
        return dto;
    }

    @Override
    public ResultVo<String> sendInspectionReport() {
        //更新在库在途数量
//        shikomiTotalMapper.calshikomiUpdateInfo();

        QueryWrapper<ShikomiTotalDO> query = new QueryWrapper<>();
        query.eq("CompanyCode", "CN");
        query.eq("Status", 1);
        query.isNotNull("DeptNo");
        List<ShikomiTotalDO> list = shikomiTotalMapper.selectList(query);
        log.info("开始执行推送点检数据");
        for (ShikomiTotalDO totalDO : list) {
            //防止空指针异常
            if (PublicUtil.isEmpty(totalDO.getQtyNoord())) {
                totalDO.setQtyNoord(0);
            }
            if (PublicUtil.isEmpty(totalDO.getQtyOnhand())) {
                totalDO.setQtyOnhand(0);
            }
            if (PublicUtil.isEmpty(totalDO.getQtyPO())) {
                totalDO.setQtyPO(0);
            }
            if (PublicUtil.isEmpty(totalDO.getQtyWarning())) {
                totalDO.setQtyWarning(0);
            }
            if (PublicUtil.isEmpty(totalDO.getPartPrepareDays())) {
                totalDO.setPartPrepareDays(0);
            }
            if (PublicUtil.isEmpty(totalDO.getAsseDays())) {
                totalDO.setAsseDays(0);
            }
            if (PublicUtil.isEmpty(totalDO.getAvgOrdQty())) {
                totalDO.setAvgOrdQty(0);
            }

//            if (PublicUtil.isEmpty(totalDO.getApplyNo())) {
//                continue;
//            }
            //SHIKOMI未使用月数
            int month = 0;
//            if (Constants.SUPPLIER_CODE_JP.equalsIgnoreCase(totalDO.getSupplierCode())) {
//                if (totalDO.getQtyNoord() != null) {
//                    month = totalDO.getQtyNoord();
//                }
//            } else {
//                if (PublicUtil.isEmpty(totalDO.getApplyTime()) || PublicUtil.isEmpty(totalDO.getLastOrdDate())) {
//                    month = 0;
//                } else {
//                    month = DateUtil.getDiffMonth(totalDO.getApplyTime(), totalDO.getLastOrdDate());
//                }
//            }
            this.getAllQty(totalDO);
            ShikomiWaringDTO dto = this.getQtyWaring(totalDO);
            totalDO.setReserveMonth(dto.getReserveMonth());
//            totalDO.setQtyWarning(dto.getQtyWarning());

            month = totalDO.getQtyNoord();

            //SHIKOMI未使用月数<3,（SHIKOMI残数+在库数量+在途数量）>警告数
            if (month < 3 && (totalDO.getRemainQty() + totalDO.getQtyOnhand() + totalDO.getQtyPO()) > totalDO.getQtyWarning()) {

                totalDO.setInspectStatus(1);
                totalDO.setInspectSendTime(new Date());
                shikomiTotalMapper.updateById(totalDO);
            }

            //短滞留：3<=SHIKOMI未使用月数<6
            if (month >= 3 && month < 6) {
                totalDO.setInspectType(4);
                totalDO.setInspectStatus(1);
                totalDO.setInspectSendTime(new Date());
                shikomiTotalMapper.updateById(totalDO);
            }

            //长期滞留项：SHIKOMI未使用月数>=6
            if (month >= 6) {
                totalDO.setInspectType(5);
                totalDO.setInspectStatus(1);
                totalDO.setInspectSendTime(new Date());
                shikomiTotalMapper.updateById(totalDO);
            }
        }
//        this.checkShikomiStcokInsufficient();
//        this.checkShikomiInspectionByDept(file, totalDOList);

        return ResultVo.success("推送完成！");
    }

    @Override
    public ResultVo<String> answerInspection(ShikomiInspectionAnsewrDTO dto) {
        QueryWrapper<ShikomiTotalDO> query = new QueryWrapper<>();
        query.eq("ShikomiNo", dto.getShikomiNo());
        query.eq("ModelNo", dto.getModelNo());

        ShikomiTotalDO totalDO = shikomiTotalMapper.selectOne(query);

        if (totalDO.getInspectStatus() != 1) {
            return ResultVo.failure("状态正常");
        }

        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();

        ShikomiInspectionDO copy = BeanCopyUtil.copy(totalDO, ShikomiInspectionDO.class);
        copy.setShikomiId(Math.toIntExact(totalDO.getId()));
        copy.setCreateUser(userDTO.getUserNo());
        shikomiInspectionMapper.insert(copy);

        //修改shikomi_total
        totalDO.setInspectAnswerTime(new Date());
        totalDO.setInspectAnswerPsnName(userDTO.getUserName());
        totalDO.setInspectAnswerPsnNo(userDTO.getUserNo());
        shikomiTotalMapper.updateById(totalDO);

        return ResultVo.success("成功");
    }

    @Override
    public ResultVo<List<ShikomiInspectionVO>> findInspectionInfoById(String id) {

        QueryWrapper<ShikomiInspectionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ShikomiId", id);

        List<ShikomiInspectionDO> list = shikomiInspectionMapper.selectList(queryWrapper);
        List<ShikomiInspectionVO> copyList = BeanCopyUtil.copyList(list, ShikomiInspectionVO.class);

        return ResultVo.success(copyList);
    }


    @Override
    public PageInfo<ShikomiVO> listShikomi(ShikomiDataVO data) {

        LambdaQueryWrapper<VShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(data.getCustomerNo()), VShikomiTotalDO::getCustomerNo, data.getCustomerNo())
                .eq(PublicUtil.isNotEmpty(data.getClassType()), VShikomiTotalDO::getClassType, data.getClassType())
                .eq(PublicUtil.isNotEmpty(data.getClassCode()), VShikomiTotalDO::getClassCode, data.getClassCode())
                .eq(PublicUtil.isNotEmpty(data.getApplyNo()), VShikomiTotalDO::getApplyNo, data.getApplyNo())
                .eq(PublicUtil.isNotEmpty(data.getShikomiNo()), VShikomiTotalDO::getShikomiNo, data.getShikomiNo())
                .eq(PublicUtil.isNotEmpty(data.getSupplierCode()), VShikomiTotalDO::getSupplierCode, data.getSupplierCode())
                .in(data.getSupplierCodeList() != null && data.getSupplierCodeList().length > 0, VShikomiTotalDO::getSupplierCode, data.getSupplierCodeList())
//                .eq(PublicUtil.isNotEmpty(data.getStatus()), VShikomiTotalDO::getStatus, data.getStatus())
                .eq(PublicUtil.isNotEmpty(data.getDeptNo()), VShikomiTotalDO::getDeptNo, data.getDeptNo())
                .eq(PublicUtil.isNotEmpty(data.getInspectStatus()), VShikomiTotalDO::getInspectStatus, data.getInspectStatus())
                .eq(PublicUtil.isNotEmpty(data.getInspectType()), VShikomiTotalDO::getInspectType, data.getInspectType())
                .eq(PublicUtil.isNotEmpty(data.getApplicantNo()), VShikomiTotalDO::getApplicantNo, data.getApplicantNo())
                .eq(PublicUtil.isNotEmpty(data.getApplicantName()), VShikomiTotalDO::getApplicantName, data.getApplicantName())
                .eq(PublicUtil.isNotEmpty(data.getRohs()), VShikomiTotalDO::getRohs, data.getRohs())
                .eq(PublicUtil.isNotEmpty(data.getMainModelFlag()), VShikomiTotalDO::getMainModelFlag, data.getMainModelFlag())
                .eq(PublicUtil.isNotEmpty(data.getIndCode()), VShikomiTotalDO::getIndCode, data.getIndCode())
                .eq(PublicUtil.isNotEmpty(data.getMainCustomerNo()), VShikomiTotalDO::getCustomerNo, data.getMainCustomerNo())
                .between(PublicUtil.isNotEmpty(data.getQtyNoord1()) && PublicUtil.isNotEmpty(data.getQtyNoord2()), VShikomiTotalDO::getQtyNoord, data.getQtyNoord1(), data.getQtyNoord2())
                .orderByDesc(PublicUtil.isEmpty(data.getModelNo()) && (PublicUtil.isEmpty(data.getChecked()) || !data.getChecked()), VShikomiTotalDO::getMainModelFlag);

        if (PublicUtil.isNotEmpty(data.getDateType())) {
            if (data.getDateType() == 1) {
                queryWrapper.between(PublicUtil.isNotEmpty(data.getStartTime()), VShikomiTotalDO::getUpdateTime, data.getStartTime(), data.getEndTime());
            }
            if (data.getDateType() == 2) {
                queryWrapper.between(PublicUtil.isNotEmpty(data.getStartTime()), VShikomiTotalDO::getApplyTime, data.getStartTime(), data.getEndTime());
            }
            if (data.getDateType() == 3) {
                queryWrapper.between(PublicUtil.isNotEmpty(data.getStartTime()), VShikomiTotalDO::getDlvDate, data.getStartTime(), data.getEndTime());
            }
            if (data.getDateType() == 4) {
                queryWrapper.between(PublicUtil.isNotEmpty(data.getStartTime()), VShikomiTotalDO::getInspectSendTime, data.getStartTime(), data.getEndTime());
            }
            if (data.getDateType() == 5) {
                queryWrapper.between(PublicUtil.isNotEmpty(data.getStartTime()), VShikomiTotalDO::getInspectAnswerTime, data.getStartTime(), data.getEndTime());
            }
        }

        if (PublicUtil.isEmpty(data.getStatus())) {
            queryWrapper.eq(VShikomiTotalDO::getStatus, 1);
        } else {
            queryWrapper.eq(VShikomiTotalDO::getStatus, data.getStatus());
        }

        if (PublicUtil.isNotEmpty(data.getModelNo()) && data.getModelNo().contains("%")) {
            queryWrapper.like(VShikomiTotalDO::getModelNo, data.getModelNo());
        } else {
            if (PublicUtil.isNotEmpty(data.getModelNo())) {
                String modelNo = data.getModelNo();
                String s = JSON.toJSONString(modelNo).replace('\"', '\'');
                queryWrapper.last("and (modelno= " + s + " or " + s + " like serialModel) ORDER BY mainModelFlag desc");
            }
        }

        if (PublicUtil.isNotEmpty(data.getIsManageProduct())) {
            if (data.getIsManageProduct() == 1) {
                queryWrapper.eq(VShikomiTotalDO::getCompanyCode, "CN");
            }
            if (data.getIsManageProduct() == 0) {
                queryWrapper.ne(VShikomiTotalDO::getCompanyCode, "CN");
            }
        }

        if (PublicUtil.isNotEmpty(data.getModelNos()) && data.getModelNos().length > 0) {
            queryWrapper.in(VShikomiTotalDO::getModelNo, data.getModelNos());
        }
        if (PublicUtil.isNotEmpty(data.getDeptNos()) && data.getDeptNos().length > 0) {
            queryWrapper.in(VShikomiTotalDO::getDeptNo, data.getDeptNos());
        }
        if (data.getSubsidiaryType() == null) {
            data.setSubsidiaryType(0);
        }
        if (data.getSubsidiaryType() == 1) {
            queryWrapper.eq(VShikomiTotalDO::getCompanyCode, Constants.COUNTRY_CN);
        }
        if (data.getSubsidiaryType() == 2) {
            queryWrapper.isNotNull(VShikomiTotalDO::getSubsidiaryCode);
            queryWrapper.ne(VShikomiTotalDO::getCompanyCode, Constants.COUNTRY_CN);
        }

        if (PublicUtil.isNotEmpty(data.getChecked()) && data.getChecked()) {
            queryWrapper.ne(VShikomiTotalDO::getQtyWarning, 0);
            queryWrapper.eq(VShikomiTotalDO::getIsWarning, true);
            queryWrapper.last("and QtyWarning > (qtyPO+qtyOnhand+RemainQty-QtyOrdPre) ORDER BY mainModelFlag desc");
        }

        PageInfo<VShikomiTotalDO> pageInfo = PageHelper.startPage(data.getPageNumber(), data.getPageSize())
                .doSelectPageInfo(() -> vShikomiTotalMapper.selectList(queryWrapper));
        PageInfo<ShikomiVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, ShikomiVO.class);

        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        List<ShikomiVO> pageInfoList = voPageInfo.getList();

//        if (pageInfoList.size() == 1 && PublicUtil.isNotEmpty(pageInfoList.get(0).getSerialModel()) && "3".equals(pageInfoList.get(0).getModelType())) {
//            QueryWrapper<ShikomiTotalDO> query = new QueryWrapper<>();
//            query.eq("ShikomiNo", pageInfoList.get(0).getShikomiNo());
//            query.eq("ModelNo", pageInfoList.get(0).getSerialModel());
//            List<ShikomiTotalDO> list = shikomiTotalMapper.selectList(query);
//            pageInfoList.addAll(BeanCopyUtil.copyList(list, ShikomiVO.class));
//        }

        ResultVo<String> resultVo;
        Map<String, String> customerNameMap = new HashMap<>();
        Map<String, String> deptNameMap = new HashMap<>();
        Map<String, String> supplierNameMap = new HashMap<>();

        String customerName;
        String deptName;
        String supplierName;

        for (ShikomiVO vo : pageInfoList) {
//            vo.setTotalCustomer(vo.getCustomerNo());
            List<String> customerList = new ArrayList<>();
            if (PublicUtil.isNotEmpty(vo.getCustomerNo())) {
                if (customerNameMap.containsKey(vo.getCustomerNo())) {
                    customerName = customerNameMap.get(vo.getCustomerNo());
                } else {
                    Object o = redisUtil.hGet("ops:allCustomerInfo", vo.getCustomerNo());
                    if (o != null) {
                        CustomerVO customerVO = JSONObject.parseObject(o.toString(), CustomerVO.class);
                        customerName = customerVO.getName();
                        customerNameMap.put(vo.getCustomerNo(), customerName);
                    } else {
                        customerName = "";
                    }
                }
                vo.setCustomerName(customerName);
            }

//            QueryWrapper<ShikomiCustomerDO> query = new QueryWrapper<>();
//            query.eq("ShikomiNo", vo.getShikomiNo());
//            List<ShikomiCustomerDO> list = shikomiCustomerMapper.selectList(query);
//            for (ShikomiCustomerDO customerDO : list) {
//                customerList.add(customerDO.getCustomerNo());
//            }
            Object o = redisUtil.hGet("ops:shikomi:customer", vo.getShikomiNo());
            if (o != null) {
                customerList = JSON.parseArray(o.toString(), String.class);
            }

            if (PublicUtil.isEmpty(customerList) && PublicUtil.isNotEmpty(vo.getCustomerNo())) {
                customerList.add(vo.getCustomerNo());
            }
            vo.setCustomerNos(customerList);
            if (PublicUtil.isNotEmpty(vo.getDeptNo())) {
                if (deptNameMap.containsKey(vo.getDeptNo())) {
                    deptName = deptNameMap.get(vo.getDeptNo());
                } else {
                    deptName = commonService.getDeptNameByNo(vo.getDeptNo());
//                    resultVo = commonServiceFeignApi.getDeptNameByNo(vo.getDeptNo());
//                    deptName = resultVo.getData();
                    deptNameMap.put(vo.getDeptNo(), deptName);
                }
                vo.setDeptName(deptName);
            }
            if (PublicUtil.isNotEmpty(vo.getSupplierCode())) {
                if (supplierNameMap.containsKey(vo.getSupplierCode())) {
                    supplierName = supplierNameMap.get(vo.getSupplierCode());
                } else {
                    supplierName = commonService.getSupplierNameByCode(vo.getSupplierCode());
//                    resultVo = commonServiceFeignApi.getSupplierName(vo.getSupplierCode());
//                    supplierName = resultVo.getData();
                    supplierNameMap.put(vo.getSupplierCode(), supplierName);
                }
                vo.setSupplierName(supplierName);
            }
            vo.setIsCNManage("CN".equals(vo.getCompanyCode()) ? true : false);
            vo.setClassTypeName(ShikomiClassTypeEnum.getName(vo.getClassType()));
            vo.setClassCodeName(ShikomiClassCodeEnum.getName(vo.getClassCode()));
        }
//        voPageInfo.setList(pageInfoList);
        return voPageInfo;

    }

    private String getModelType(String serialModel) {

        String modelType;
        if (PublicUtil.isNotEmpty(serialModel)) {
            if (serialModel.contains("%")) {
                modelType = "2";
            } else {
                modelType = "3";
            }
        } else {
            modelType = "1";
        }

        return modelType;
    }

    @Override
    public ResultVo<String> saveShikomidata(ShikomiTotalDO info) {
        if (PublicUtil.isEmpty(info.getShikomiNo()) || PublicUtil.isEmpty(info.getModelNo())) {
            return ResultVo.failure("型号和shikomi号不能为空");
        }
        if (PublicUtil.isEmpty(info.getSupplierCode())) {
            return ResultVo.failure("备库地点不能为空");
        }
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();

        QueryWrapper<ShikomiTotalDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ShikomiNo", info.getShikomiNo());
        queryWrapper.eq("SupplierCode", info.getSupplierCode());
        ShikomiTotalDO totalDO = shikomiTotalMapper.selectOne(queryWrapper);

        if (PublicUtil.isEmpty(info.getId())) {
            if (totalDO != null) {
                return ResultVo.failure("已存在,请修改后重试");
            }
            info.setCreateTime(new Date());
            info.setCreateUser(userDTO.getUserNo());
            info.setModelNo(info.getMainModelNo());
            if (info.getModelNo().contains("*")) {
                info.setSerialModel(info.getModelNo().replace("*", "%"));
            }
            if (PublicUtil.isEmpty(info.getIsWarning())) {
                info.setIsWarning(true);
            }
            int insert = shikomiTotalMapper.insert(info);
            info.setMainModelFlag(1);
            this.addAndUpdateShikomi(info);
            if (insert == 1) {
                saveShikomiCache();
            }
            return insert == 1 ? ResultVo.success("保存成功") : ResultVo.failure("0", "保存失败");
        } else {
//            ShikomiTotalDO totalDO = shikomiTotalMapper.selectById(info.getId());
            LambdaUpdateWrapper<ShikomiTotalDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(ShikomiTotalDO::getApplyNo, info.getApplyNo());
            updateWrapper.set(ShikomiTotalDO::getApplyTime, info.getApplyTime());
            updateWrapper.set(ShikomiTotalDO::getSupplierCode, info.getSupplierCode());
            updateWrapper.set(ShikomiTotalDO::getStatus, info.getStatus());
            updateWrapper.set(ShikomiTotalDO::getClassCode, info.getClassCode());
            updateWrapper.set(ShikomiTotalDO::getCustomerNo, info.getCustomerNo());
            updateWrapper.set(ShikomiTotalDO::getClassType, info.getClassType());
            updateWrapper.set(ShikomiTotalDO::getDeptNo, info.getDeptNo());
            updateWrapper.set(ShikomiTotalDO::getIndCode, info.getIndCode());
            updateWrapper.set(ShikomiTotalDO::getCompanyCode, info.getCompanyCode());
            updateWrapper.set(ShikomiTotalDO::getIsWarning, info.getIsWarning());
            updateWrapper.set(ShikomiTotalDO::getAsseDays, info.getAsseDays());
            updateWrapper.set(ShikomiTotalDO::getQtyNoord, info.getQtyNoord());
            updateWrapper.set(ShikomiTotalDO::getQtyWarning, info.getQtyWarning());
            updateWrapper.set(ShikomiTotalDO::getPartPrepareDays, info.getPartPrepareDays());
            updateWrapper.set(ShikomiTotalDO::getPlanUseDate, info.getPlanUseDate());
            updateWrapper.set(ShikomiTotalDO::getRegistDate, info.getRegistDate());
            updateWrapper.set(ShikomiTotalDO::getApplyQty, info.getApplyQty());
            updateWrapper.set(ShikomiTotalDO::getRemainQty, info.getRemainQty());
            updateWrapper.set(ShikomiTotalDO::getQtyOrdPre, info.getQtyOrdPre());
            updateWrapper.set(ShikomiTotalDO::getLotQty, info.getLotQty());
            updateWrapper.set(ShikomiTotalDO::getPriceLot, info.getPriceLot());
            updateWrapper.set(ShikomiTotalDO::getProjectNo, info.getProjectNo());
            updateWrapper.set(ShikomiTotalDO::getPplNo, info.getPplNo());
            updateWrapper.set(ShikomiTotalDO::getRohs, info.getRohs());
            updateWrapper.set(ShikomiTotalDO::getApplicantNo, info.getApplicantNo());
            updateWrapper.set(ShikomiTotalDO::getApplicantName, info.getApplicantName());
            updateWrapper.set(ShikomiTotalDO::getApplicantEmail, info.getApplicantEmail());
            updateWrapper.set(ShikomiTotalDO::getApproverNo, info.getApproverNo());
            updateWrapper.set(ShikomiTotalDO::getApproverName, info.getApproverName());
            updateWrapper.set(ShikomiTotalDO::getApproverEmail, info.getApproverEmail());
            updateWrapper.set(ShikomiTotalDO::getRemark, info.getRemark());
            updateWrapper.set(ShikomiTotalDO::getUpdateUser, userDTO.getUserNo());
//            updateWrapper.set(ShikomiTotalDO::getModelNo,)
            updateWrapper.eq(ShikomiTotalDO::getId, info.getId());

            int i = shikomiTotalMapper.update(null, updateWrapper);
            if(i == 1) {
                saveShikomiCache();
            }
            return i == 1 ? ResultVo.success("修改成功") : ResultVo.failure("0", "修改失败");
        }

    }

    @Override
    public ResultVo<String> saveCustomerdata(ShikomiCustomerVO customerVO) {
        QueryWrapper<ShikomiCustomerDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CustomerNo", customerVO.getCustomerNo());
        queryWrapper.eq("ShikomiNo", customerVO.getShikomiNo());
        ShikomiCustomerDO customer = shikomiCustomerMapper.selectOne(queryWrapper);

        if (customer != null) {
            return ResultVo.failure("已存在，请不要重复添加");
        }

        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        ShikomiCustomerDO customerDO = BeanCopyUtil.copy(customerVO, ShikomiCustomerDO.class);
        //设置创建人
        customerDO.setCreateUser(userDTO.getUserNo());
        int insert = shikomiCustomerMapper.insert(customerDO);

        List<String> doList;
        Object o = redisUtil.hGet("ops:shikomi:customer", customerDO.getShikomiNo());
        if (o != null) {
            doList = JSON.parseArray(o.toString(), String.class);
        } else {
            doList = new ArrayList<>();
        }
        doList.add(customerDO.getCustomerNo());
        redisUtil.hPut("ops:shikomi:customer", customerDO.getShikomiNo(), JSON.toJSONString(doList));
        if (insert == 1) {
            saveShikomiCache();
        }

        return insert == 1 ? ResultVo.success("保存成功") : ResultVo.failure("保存失败");
    }

    private ResultVo<String> addAndUpdateShikomi(ShikomiTotalDO totalDO) {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();

        QueryWrapper<ShikomiModelDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shikomino", totalDO.getShikomiNo());
        queryWrapper.eq("modelno", totalDO.getModelNo());
        ShikomiModelDO shikomiModelDO = shikomiModelMapper.selectOne(queryWrapper);

        if (shikomiModelDO == null) {
            ShikomiModelDO modelDO = new ShikomiModelDO();
            queryWrapper.clear();
            queryWrapper.eq("shikomino", totalDO.getShikomiNo());
            int modelCount = shikomiModelMapper.selectCount(queryWrapper);
            if (modelCount >= 1) {
                if (PublicUtil.isNotEmpty(modelDO.getSerialModel())) {
                    if (modelDO.getSerialModel().contains("%")) {
                        modelDO.setModelType("2");
                    } else {
                        modelDO.setModelType("3");
                    }
                } else {
                    modelDO.setModelType("3");
                }
            } else {
                modelDO.setModelType("1");
            }
            modelDO.setMainModelFlag(totalDO.getMainModelFlag());
            modelDO.setShikomino(totalDO.getShikomiNo());
            modelDO.setModelno(totalDO.getModelNo());
            modelDO.setSerialModel(totalDO.getSerialModel());
            modelDO.setCreateTime(DateUtil.getNow());
            modelDO.setCreateUser(userDTO.getUserNo());
            if (modelDO.getModelno().contains("*")) {
                modelDO.setSerialModel(modelDO.getModelno().replace("*", "%"));
            }
            shikomiModelMapper.insert(modelDO);
        }

        queryWrapper.clear();
        queryWrapper.eq("shikomino", totalDO.getShikomiNo());
        int count = shikomiModelMapper.selectCount(queryWrapper);

        this.getAllQty(totalDO);
        ShikomiWaringDTO dto = this.getQtyWaring(totalDO);

        QueryWrapper<ShikomiTotalDO> query = new QueryWrapper<>();
        query.eq("ShikomiNo", totalDO.getShikomiNo());
        ShikomiTotalDO shikomiTotalDO = new ShikomiTotalDO();
        shikomiTotalDO.setModelCount(count);
        shikomiTotalDO.setQtyWarning(dto.getQtyWarning());

        shikomiTotalMapper.update(shikomiTotalDO, query);

        return ResultVo.success();
    }


    @Override
    public ResultVo<String> importCNReplyFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (!filename.matches("^.+\\.(?i)(xlsx)$")) {
            return ResultVo.failure("文件格式错误");
        }
        ExcelHelper excel = null;
//        Map<String, List<ShikomiTotalDO>> listMap = new HashMap<>();
        List<ShikomiTotalDO> listShikomiTotal = new ArrayList<>();
        ShikomiCallbackVO callbackVO = null;
        int count = 0;
        try {
            ResultVo<PreStockApplyDetailDTO> resultApplyInfo;
            PreStockApplyDetailDTO applyDto;
            ResultVo<List<PreStockDetailDTO>> resultPrestockDetail;
            PreStockDetailDTO detailDTO;
            QueryWrapper<ShikomiTotalDO> query;
//            ShikomiTotalDO item = null;
            Map<String, String> map = new HashMap<>();
            excel = new ExcelHelper(file.getInputStream());
            ShikomiTotalDO totalDO;
            Sheet sheet = excel.getSheet();

            Row rows;
            int lastRowNum = sheet.getLastRowNum();
            for (int row = 1; row <= lastRowNum; row++) {
                rows = sheet.getRow(row);
                if (rows == null) {
                    break;
                }
                totalDO = new ShikomiTotalDO();
                totalDO.setShikomiNo(excel.getCellString(rows.getCell(1)));
                totalDO.setDeptNo(excel.getCellString(rows.getCell(3)));
                String applyTime = excel.getCellString(rows.getCell(4));
                if (PublicUtil.isNotEmpty(applyTime)) {
                    totalDO.setApplyTime(DateUtil.stringToDate(applyTime));
                }
                totalDO.setModelNo(excel.getCellString(rows.getCell(5)).trim());
//            String answerTime = excel.getCellString(rows.getCell(8));
                String registDate = excel.getCellString(rows.getCell(9));
                if (PublicUtil.isNotEmpty(registDate)) {
                    totalDO.setRegistDate(DateUtil.stringToDate(registDate));
                }
                totalDO.setAsseDays(excel.getCellInteger(rows.getCell(10)));
//            totalDO.setCustomerNo();
                totalDO.setApplyNo(excel.getCellString(rows.getCell(12)));
                totalDO.setSupplierCode(excel.getCellString(rows.getCell(13)));
//                totalDO.setSupplierCode("CN");
                totalDO.setClassType("A");
                totalDO.setQtyOrdPre(0);

                if (totalDO.getModelNo().contains("*")) {
                    totalDO.setSerialModel(totalDO.getModelNo().replace("*", "%"));
                }
                if (totalDO.getApplyNo().startsWith("SHM")) {
                    totalDO.setCompanyCode("CN");
                }
                //根据申请号查prestockapply表
                resultApplyInfo = preStockFeignApi.findPreStockApplyByNo(totalDO.getApplyNo());
                if (!resultApplyInfo.isSuccess() || resultApplyInfo.getData() == null) {
                    log.error("shikomi cn reply 查询申请不存在" + resultApplyInfo.getMessage());
                    continue;
                }

                applyDto = resultApplyInfo.getData();
                totalDO.setClassCode(applyDto.getShikomiClass());
                totalDO.setApplicantNo(applyDto.getApplyPsnNo());
                totalDO.setApplicantName(applyDto.getApplyPsn());
                totalDO.setApplicantEmail(applyDto.getApplyPsnMail());
                totalDO.setApplyPsnName(applyDto.getApplyPsn());
                totalDO.setApproverNo(applyDto.getApproverNo());
                totalDO.setApproverEmail(applyDto.getApproverMail());
                totalDO.setCustomerNo(applyDto.getCustomerNo());
                totalDO.setApproverName(applyDto.getApproverName());
                totalDO.setDeptNo(applyDto.getDeptNo());
                totalDO.setIndCode(applyDto.getIndCode());
                totalDO.setApplyTime(applyDto.getApplyTime());
//                totalDO.setClassCode(applyDto.getShikomiClass());

                //根据申请号查prestockdetail表
                resultPrestockDetail = preStockFeignApi.findPreStockDetailByNo(applyDto.getId(), totalDO.getModelNo());
                if (!resultPrestockDetail.isSuccess()) {
                    log.error("shikomi cn reply 查询申请明细不存在:" + resultApplyInfo.getMessage());
                    continue;
                }
                if (PublicUtil.isEmpty(resultPrestockDetail.getData())) {
                    log.error("shikomi cn reply 不存在该型号:" + applyDto.getId() + ":" + totalDO.getModelNo() + resultApplyInfo.getMessage());
                    continue;
                }
                detailDTO = resultPrestockDetail.getData().stream().max(Comparator.comparing(PreStockDetailDTO::getDlvDateJp)).get();
//                detailDTO = resultPrestockDetail.getData().get(0);
                totalDO.setEPrice(detailDTO.getEprice());
                totalDO.setPriceLot(detailDTO.getEprice());
                totalDO.setApplyQty(detailDTO.getQuantity());
                totalDO.setRemainQty(totalDO.getApplyQty());
                totalDO.setLotQty(totalDO.getApplyQty());
                totalDO.setPlanUseDate(detailDTO.getDlvDateJp());
                totalDO.setAnswerText(detailDTO.getItemNo().toString());
                totalDO.setRohs(detailDTO.getRohs10() ? "1" : "0");

                // 保存到shikomiTotal
                query = new QueryWrapper<>();
                query.eq("ShikomiNo", totalDO.getShikomiNo());
                query.eq("SupplierCode", totalDO.getSupplierCode());
                ShikomiTotalDO ShikomiTotalDO = shikomiTotalMapper.selectOne(query);

                if (ShikomiTotalDO == null) {
                    totalDO.setStatus(1);
                    totalDO.setMainModelFlag(1);
                    totalDO.setQtyNoord(0);
                    totalDO.setIsWarning(true);
                    totalDO.setCreateUser("cnreply");
                    shikomiTotalMapper.insert(totalDO);
                    this.addAndUpdateShikomi(totalDO);
                } else {
                    totalDO.setUpdateUser("cnreply");
                    totalDO.setRemainQty(ShikomiTotalDO.getRemainQty());
                    if (ShikomiTotalDO.getApplyQty() == null) {
                        ShikomiTotalDO.setApplyQty(0);
                    }
                    if (ShikomiTotalDO.getQtyNoord() == null) {
                        ShikomiTotalDO.setQtyNoord(0);
                    }
                    totalDO.setApplyQty(totalDO.getApplyQty() + ShikomiTotalDO.getApplyQty());
                    shikomiTotalMapper.update(totalDO, query);
                }
                listShikomiTotal.add(totalDO);
                map.put(totalDO.getShikomiNo(), totalDO.getModelNo());

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            log.error(e.getMessage());
            return ResultVo.failure(e.getMessage());
        }
        List<ShikomiCallbackDetail> detailItem;

        //回调门户
        for (ShikomiTotalDO totalDO : listShikomiTotal) {
            detailItem = new ArrayList<>();
            callbackVO = new ShikomiCallbackVO();
            callbackVO.setStatus(1);
            ShikomiCallbackMaster master = new ShikomiCallbackMaster();
            master.setApplyNo(totalDO.getApplyNo());
            master.setShikomiNo(totalDO.getShikomiNo());
            master.setApplyType(1);

            ShikomiCallbackDetail detail = new ShikomiCallbackDetail();
            detail.setShikomiNo(totalDO.getShikomiNo());
            detail.setModelNo(totalDO.getModelNo());
            detail.setItemNo(String.valueOf(count++));
            detail.setApprovalDate(new Date());
            detail.setRegistDate(totalDO.getRegistDate());
            detail.setAsseDays(totalDO.getAsseDays());
            detailItem.add(detail);
            master.setDetailItem(detailItem);
            callbackVO.setShikomi(master);
            ResultVo<String> vo = this.callbackToSMCShikomi(callbackVO);
            if (vo.isSuccess() == false) {
                log.error("shikomi jp reply 回调门户失败 {},{}", vo.getMessage(), callbackVO.toString());
            }
        }

        return ResultVo.success("导入成功");
    }


    @Override
    // @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> importJPReplyFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (!filename.matches("^.+\\.(?i)(txt)$")) {
            return ResultVo.failure("文件格式错误");
        }
        String encoding = "GBK";
        String lineTxt = null;
        ShikomiTotalDO item = null;
        ShikomiCallbackVO callbackVO = null;
        List<ShikomiTotalDO> listShikomiTotal = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        ShikomiCustomerVO customerVO;
//        Map<String, List<ShikomiTotalDO>> listMap = new HashMap<>();
        int count = 0;

        try (InputStreamReader read = new InputStreamReader(file.getInputStream(), encoding);
             BufferedReader bufferedReader = new BufferedReader(read)) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            ResultVo<PreStockApplyDetailDTO> resultApplyInfo;
            PreStockApplyDetailDTO applyDto;
            ResultVo<List<PreStockDetailDTO>> resultPrestockDetail;
            PreStockDetailDTO detailDTO;
            QueryWrapper<ShikomiTotalDO> query;

            bufferedReader.readLine();//跳过表头

            while ((lineTxt = bufferedReader.readLine()) != null) {
                if (lineTxt.length() < 10) {
                    continue;
                }
                item = new ShikomiTotalDO();
                item.setStatus(1);
                item.setSubsidiaryCode(lineTxt.substring(1, 6).trim());
                String shikomiNo = lineTxt.substring(20, 37).trim();
                int i = shikomiNo.length() - 4;
                if (shikomiNo.substring(i, i + 1).equals("-")) {
                    shikomiNo = shikomiNo.substring(0, i);
                }
                item.setShikomiNo(shikomiNo);
                item.setModelNo(lineTxt.substring(37, 67).trim());
                item.setSupplierCode("JP");
                String remainQty = lineTxt.substring(68, 77).trim();
                if (PublicUtil.isNotEmpty(remainQty)) {
                    item.setRemainQty(Integer.valueOf(remainQty));
                }
                String applyQty = lineTxt.substring(68, 77).trim();
                if (PublicUtil.isNotEmpty(applyQty)) {
                    item.setApplyQty(Integer.valueOf(applyQty));
                }
                String registDate = lineTxt.substring(130, 145).trim();
                if (PublicUtil.isNotEmpty(registDate)) {
                    Date parse = dateFormat.parse(registDate);
                    item.setRegistDate(parse);
                } else {
                    item.setRegistDate(null);
                }
                String partPrepareDays = lineTxt.substring(146, 166).trim();
                if (PublicUtil.isNotEmpty(partPrepareDays)) {
                    item.setPartPrepareDays(Integer.valueOf(partPrepareDays));
                }
                String asseDays = lineTxt.substring(168, 185).trim();
                if (PublicUtil.isNotEmpty(asseDays)) {
                    item.setAsseDays(Integer.valueOf(asseDays));
                }
                int length = lineTxt.length();
                item.setApplyNo(lineTxt.substring(187, length).trim());
                if (item.getApplyNo().startsWith("SHM")) {
                    item.setCompanyCode("CN");
                }
                if (item.getModelNo().contains("*")) {
                    item.setSerialModel(item.getModelNo().replace("*", "%"));
                }
                //根据申请号查prestockapply表
                resultApplyInfo = preStockFeignApi.findPreStockApplyByNo(item.getApplyNo());
                if (!resultApplyInfo.isSuccess() || resultApplyInfo.getData() == null) {
                    log.error("shikomi jp reply 查询申请不存在" + resultApplyInfo.getMessage());
                    continue;
                }

                applyDto = resultApplyInfo.getData();
                item.setClassCode(applyDto.getShikomiClass());
                item.setApplicantNo(applyDto.getApplyPsnNo());
                item.setApplicantName(applyDto.getApplyPsn());
                item.setApplicantEmail(applyDto.getApplyPsnMail());
                item.setApplyPsnName(applyDto.getApplyPsn());
                item.setApproverNo(applyDto.getApproverNo());
                item.setApproverEmail(applyDto.getApproverMail());
                item.setCustomerNo(applyDto.getCustomerNo());
                item.setApproverName(applyDto.getApproverName());
                item.setDeptNo(applyDto.getDeptNo());
                item.setIndCode(applyDto.getIndCode());
                item.setApplyTime(applyDto.getApplyTime());
//                item.setClassCode(applyDto.getShikomiClass());
                if (CollectionUtil.isNotEmpty(applyDto.getShareCustomerNos())) {
                    for (String customerNo : applyDto.getShareCustomerNos()) {
                        customerVO = new ShikomiCustomerVO();
                        customerVO.setShikomiNo(item.getShikomiNo());
                        customerVO.setCustomerNo(customerNo);
                        this.saveCustomerdata(customerVO);
                    }
                }

                //根据申请号查prestockdetail表
                resultPrestockDetail = preStockFeignApi.findPreStockDetailByNo(applyDto.getId(), item.getModelNo());
                if (!resultPrestockDetail.isSuccess()) {
                    log.error("shikomi jp reply 查询申请明细不存在:" + resultApplyInfo.getMessage());
                    continue;
                }
                if (PublicUtil.isEmpty(resultPrestockDetail.getData())) {
                    log.error("shikomi jp reply 不存在该型号:" + item.getModelNo());
                    continue;
                }
                detailDTO = resultPrestockDetail.getData().stream().max(Comparator.comparing(PreStockDetailDTO::getDlvDateJp)).get();
//                detailDTO = resultPrestockDetail.getData().get(0);
                item.setEPrice(detailDTO.getEprice());
                item.setPriceLot(detailDTO.getEprice());
                item.setPlanUseDate(detailDTO.getDlvDateJp());
                item.setAnswerText(detailDTO.getItemNo().toString());
                item.setRohs(detailDTO.getRohs10() ? "1" : "0");

                // 保存到shikomiTotal
                query = new QueryWrapper<>();
                query.eq("ShikomiNo", item.getShikomiNo());
                query.eq("SupplierCode", item.getSupplierCode());
                ShikomiTotalDO ShikomiTotalDO = shikomiTotalMapper.selectOne(query);

//                if (listMap.containsKey(item.getApplyNo())) {
//                    List<ShikomiTotalDO> list = listMap.get(item.getApplyNo());
//                    list.add(item);
//                    listMap.put(item.getApplyNo(), list);
//                } else {
//                    listShikomiTotal = new ArrayList<>();
//                    listShikomiTotal.add(item);
//                    listMap.put(item.getApplyNo(), listShikomiTotal);
//                    LambdaQueryWrapper<VShikomiTotalDO> queryWrapper = new LambdaQueryWrapper<>();
//                    queryWrapper.eq(VShikomiTotalDO::getApplyNo, item.getApplyNo());
//                    List<VShikomiTotalDO> doList = vShikomiTotalMapper.selectList(queryWrapper);
//                    if (PublicUtil.isNotEmpty(doList)) {
//                        return ResultVo.failure("申请号: " + item.getApplyNo() + " 已存在，请不要重复导入");
//                    }
//                }

                if (ShikomiTotalDO == null) {
                    item.setStatus(1);
                    item.setMainModelFlag(1);
                    item.setQtyNoord(0);
                    item.setIsWarning(true);
                    item.setCreateUser("jpreply");
                    shikomiTotalMapper.insert(item);
                    this.addAndUpdateShikomi(item);
                } else {
                    item.setUpdateUser("jpreply");
                    item.setRemainQty(ShikomiTotalDO.getRemainQty());
                    if (ShikomiTotalDO.getApplyQty() == null) {
                        ShikomiTotalDO.setApplyQty(0);
                    }
                    if (ShikomiTotalDO.getQtyNoord() == null) {
                        ShikomiTotalDO.setQtyNoord(0);
                    }
                    item.setApplyQty(item.getApplyQty() + ShikomiTotalDO.getApplyQty());
                    shikomiTotalMapper.update(item, query);
                }
                listShikomiTotal.add(item);
                map.put(item.getShikomiNo(), item.getModelNo());
                count++;
            }
        } catch (Exception e) {
            log.error("line = {}", lineTxt);
            log.error("item = {}", item);
            log.error("上传文件时出错: {}", e);
            return ResultVo.failure("上传文件时出错");
        }
        List<ShikomiCallbackDetail> detailItem;

        //回调门户

//        for (Map.Entry<String, List<ShikomiTotalDO>> entry : listMap.entrySet()) {
//            count = 1;
//            List<ShikomiTotalDO> list = entry.getValue();
//            ShikomiCallbackMaster master = new ShikomiCallbackMaster();
//            ShikomiCallbackVO callbackVO = new ShikomiCallbackVO();
//            detailItem = new ArrayList<>();
//            ShikomiCallbackDetail detail;
//            for (ShikomiTotalDO totalDO : list) {
//                callbackVO.setStatus(1);
//                master.setApplyNo(totalDO.getApplyNo());
//                master.setShikomiNo(totalDO.getShikomiNo());
//                master.setApplyType(1);
//
//                detail = new ShikomiCallbackDetail();
//                detail.setShikomiNo(totalDO.getShikomiNo());
//                detail.setModelNo(totalDO.getModelNo());
//                detail.setItemNo(String.valueOf(count++));
//                detail.setApprovalDate(new Date());
//                detail.setRegistDate(totalDO.getRegistDate());
//                detail.setAsseDays(totalDO.getAsseDays());
//                detailItem.add(detail);
//            }
//            master.setDetailItem(detailItem);
//            callbackVO.setShikomi(master);
//            ResultVo<String> vo = this.callbackToSMCShikomi(callbackVO);
//            if (vo.isSuccess() == false) {
//                log.error("shikomi jp reply 回调门户失败 {},{}", vo.getMessage(), callbackVO.toString());
//            }
//        }

        for (ShikomiTotalDO totalDO : listShikomiTotal) {
            detailItem = new ArrayList<>();
            callbackVO = new ShikomiCallbackVO();
            callbackVO.setStatus(1);
            ShikomiCallbackMaster master = new ShikomiCallbackMaster();
            master.setApplyNo(totalDO.getApplyNo());
            master.setShikomiNo(totalDO.getShikomiNo());
            master.setApplyType(1);

            ShikomiCallbackDetail detail = new ShikomiCallbackDetail();
            detail.setShikomiNo(totalDO.getShikomiNo());
            detail.setModelNo(totalDO.getModelNo());
            detail.setItemNo(String.valueOf(count++));
            detail.setApprovalDate(new Date());
            detail.setRegistDate(totalDO.getRegistDate());
            detail.setAsseDays(totalDO.getAsseDays());
            detailItem.add(detail);
            master.setDetailItem(detailItem);
            callbackVO.setShikomi(master);
            ResultVo<String> vo = this.callbackToSMCShikomi(callbackVO);
            if (vo.isSuccess() == false) {
                log.error("shikomi jp reply 回调门户失败 {},{}", vo.getMessage(), callbackVO.toString());
            }
        }

        if (map.isEmpty()) {
            return ResultVo.failure("未成功导入，请检查数据是否有误");
        } else {
            return ResultVo.success("成功导入" + map.toString());
        }
    }


    @Override
    public ResultVo<String> downloadFile() {

        InputStream inputStream = null;
        BufferedInputStream bis = null;
        try {
            String s = FtpFileUtil.dowmloadFile("DBSKM.ZIP", server, user, password, filePath);
            inputStream = new FileInputStream(s);
            bis = new BufferedInputStream(inputStream);
            //进行文件解压
            return unzip(bis);
        } catch (Exception e) {
            log.error("下载失败: {}", e.getMessage(), e);
            return ResultVo.failure(e.getMessage());
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                    bis = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


    public ResultVo<String> unzip(BufferedInputStream bis) {
        try {
            // 读入流
            ZipInputStream zipInputStream = new ZipInputStream(bis);
            // 遍历每一个文件
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            String unzipFilePath = null;
            ResultVo<String> stringResultVo = null;
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) { // 是否为文件
                    // 文件
                    unzipFilePath = zipEntry.getName();
                    File file = new File(String.valueOf(zipEntry));
                    //如果是csv文件，则上传数据
                    if (unzipFilePath.endsWith(".CSV") || unzipFilePath.endsWith(".csv")) {
                        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), zipInputStream);
                        stringResultVo = saveToShikomiTotal2(multipartFile);
                        zipInputStream.close();
                        return stringResultVo;
                    }
                }
                zipEntry = zipInputStream.getNextEntry();
            }
            return stringResultVo;
        } catch (Exception e) {
            log.error("解压DBSKM文件出错 {} " ,e.getMessage(), e);
            return ResultVo.failure("解压DBSKM文件出错"+e.getMessage());
        }
    }


    /**
     * 上传csv文件
     *
     * @param file
     * @throws Exception
     */
    public void saveToShikomiTotal(MultipartFile file) {
        ShikomiTotalDO item = null;
        ShikomiModelDO modelDO = null;
        try {
            DataInputStream in = new DataInputStream(file.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));
            Map<String, ShikomiTotalDO> map = new HashMap<>();
            Map<String, ShikomiModelDO> modelMap = new HashMap<>();

            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {

                i = 0;
                item = new ShikomiTotalDO();
                if (line.contains("NO79,NOTSUDUKI")) {
                    line = line.replace("NO79,NOTSUDUKI", "NO79.NOTSUDUKI");
                }

                String[] csv = line.replaceAll("\"", "").split(",");//去掉存入数据库中的""

                item.setSupplierCode(Constants.SUPPLIER_CODE_JP);
                item.setModelNo(csv[i++].trim());
                if (item.getModelNo().contains("NO79.NOTSUDUKI")) {
                    item.setModelNo(item.getModelNo().replace("NO79.NOTSUDUKI", "NO79,NOTSUDUKI"));
                }
                String shikomiNo = csv[i++].trim();
                int j = shikomiNo.length() - 4;
                if (shikomiNo.substring(j, j + 1).equals("-")) {
                    shikomiNo = shikomiNo.substring(0, j);
                }
                item.setShikomiNo(shikomiNo);
                item.setSubsidiaryCode(csv[i++].trim());
                item.setClassType(csv[i++].trim());
                item.setCompanyCode(csv[i++].trim());
                item.setBranchCode(csv[i++].trim());
                item.setLotQty(Integer.valueOf(csv[i++].trim()));
                item.setRemainQty(Integer.valueOf(csv[i++].trim()));
                item.setMaxQty(Integer.valueOf(csv[i++].trim()));
                item.setQtyOrdPre(0);
                item.setStatus(1);

                String date = csv[i++].trim();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (date.length() == 6) {
                    String str = "20" + date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4, 6);
                    item.setRegistDate(sdf.parse(str));
                }

                date = csv[i++].trim();
                if (date.length() == 6) {
                    String str = "20" + date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4, 6);
                    item.setReviseDate(sdf.parse(str));
                }

                i = csv.length - 4;
                item.setPartPrepareDays(Integer.valueOf(csv[i++].trim()));
                item.setAsseDays(Integer.valueOf(csv[i++].trim()));

                if (csv[i].contains("MONTHS NO ORDER")) {

                    item.setQtyNoord(Integer.valueOf(csv[i].replaceAll("[^0-9]", "")));
                } else {
                    item.setRemark(csv[i]);
                }
                //BUG10644 2023/05/05 WuJiaWen
                String rohs = csv[i + 1].trim();
                if ("H".equals(rohs)) {
                    item.setRohs("1");
                }

                if (item.getClassType().equals("C")) {
                    item.setClassCode("0");
                } else if ("A".equalsIgnoreCase(item.getClassType())) {
                    if (!Constants.COUNTRY_CN.equalsIgnoreCase(item.getCompanyCode())) {
                        item.setClassType("B");//国外管理不可以下单
                        item.setClassCode("4");//国内不可以用

                        if (Constants.SUBSIDIARY_CODE.equalsIgnoreCase(item.getSubsidiaryCode())) {
                            item.setClassCode("1");//国内共享
                        }
                    } else {
                        item.setClassCode("1");//国内共享
                    }
                }

                if (item.getModelNo().contains("*")) {

                    item.setModelType("2");//系列
                    String replace = item.getModelNo().replace("*", "%");
                    item.setSerialModel(replace);
                } else {
                    item.setModelType("1");//完整型号
                }

                //选择SubsidiaryCode为95012的数据
                String key = item.getShikomiNo() + "-" + item.getSupplierCode();
                if (map.containsKey(key)) {
                    if (item.getSubsidiaryCode().equals(Constants.SUBSIDIARY_CODE)) {
                        map.put(key, item);
                    }
                } else {
                    map.put(key, item);
                }

                modelDO = new ShikomiModelDO();
                String modelKey = item.getShikomiNo() + "-" + item.getModelNo();
                if (!modelMap.containsKey(modelKey)) {
                    modelDO.setShikomino(item.getShikomiNo());
                    modelDO.setModelno(item.getModelNo());
                    modelDO.setSerialModel(item.getSerialModel());
                    modelMap.put(modelKey, modelDO);
                }
            }

            LambdaQueryWrapper<ShikomiTotalDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(ShikomiTotalDO::getUpdateUser, "JP");
            queryWrapper2.or().eq(ShikomiTotalDO::getCreateUser, "JP");
            List<ShikomiTotalDO> list = shikomiTotalMapper.selectList(queryWrapper2);
            ShikomiTotalDO totalDO1;
            for (ShikomiTotalDO totalDO : list) {
                if (!map.containsKey(totalDO.getShikomiNo() + "-" + totalDO.getSupplierCode())) {
                    queryWrapper2.clear();
                    totalDO1 = new ShikomiTotalDO();
                    totalDO1.setStatus(2);
                    totalDO1.setId(totalDO.getId());
                    shikomiTotalMapper.updateById(totalDO1);
                }

            }

            log.info("保存进shikomi_model中");
            List<ShikomiModelDO> doList = new ArrayList<>();
            List<ShikomiModelDO> updateList = new ArrayList<>();
            int count = 0;
            int size = 0;
            for (Map.Entry<String, ShikomiModelDO> entry : modelMap.entrySet()) {

                doList.add(entry.getValue());
                count ++;
                size++;

                //每次查500条
                if (count == 500 || size == modelMap.size()) {
                    // 查询出需要更新的数据
                    List<ShikomiModelDO> modelData = shikomiModelMapper.listShikomiModelData(doList);
                    Map<String, String> stringMap = modelData.stream().collect(Collectors.toMap(k -> k.getShikomino() + "-" + k.getModelno(), ShikomiModelDO::getShikomino, (key1, key2) -> key1));

                    for (ShikomiModelDO shikomiModelDO : doList) {
                        // 存在则放入更新List中，待会批量更新
                        if (stringMap.containsKey(shikomiModelDO.getShikomino() + "-" + shikomiModelDO.getModelno())) {
                            updateList.add(shikomiModelDO);
                            continue;
                        }

                        // 不存在则新增
                        shikomiModelDO.setCreateUser(Constants.SHIKOMI_USER_JP);
                        shikomiModelDO.setCreateTime(new Date());
                        try {
                            shikomiModelMapper.insert(shikomiModelDO);
                        } catch (Exception e) {
                            //防止因乱码数据重复插入而导致的主键冲突
                            QueryWrapper<ShikomiModelDO> queryWrapper = new QueryWrapper<>();
                            queryWrapper.eq("shikomino", shikomiModelDO.getShikomino());
                            queryWrapper.eq("modelno", shikomiModelDO.getModelno());
                            shikomiModelMapper.update(shikomiModelDO, queryWrapper);
                        }
                    }

                    // 批量更新
                    shikomiModelMapper.updateShikomiModelByList(updateList);

                    count = 0;
                    doList = new ArrayList<>();
                    updateList = new ArrayList<>();
                }

            }
            modelMap.clear();


            log.info("保存进shikomi_total中");
            List<ShikomiTotalDO> totalList = new ArrayList<>();
            List<ShikomiTotalDO> totalUpDateList = new ArrayList<>();
            count = 0;
            size = 0;
            // 保存进shikomi_total
            for (Map.Entry<String, ShikomiTotalDO> entry : map.entrySet()) {

                totalList.add(entry.getValue());
                count ++;
                size ++;

                if (count == 80 || size == map.size()) {
                    List<ShikomiTotalDO> totalDOList = shikomiTotalMapper.selectShikomiTotalByList(totalList);
                    Map<String, ShikomiTotalDO> totalDOMap = totalDOList.stream().collect(Collectors.toMap(k -> k.getShikomiNo() + "-" + k.getSupplierCode(), v -> v, (key1, key2) -> key1));
                    for (ShikomiTotalDO totalDO : totalList) {

                        if (totalDOMap.containsKey(totalDO.getShikomiNo() + "-" + totalDO.getSupplierCode())) {
                            ShikomiTotalDO shikomiTotalDO = totalDOMap.get(totalDO.getShikomiNo() + "-" + totalDO.getSupplierCode());
                            if (PublicUtil.isNotEmpty(shikomiTotalDO.getRegistDate())) {
                                totalDO.setRegistDate(shikomiTotalDO.getRegistDate());
                            }
                            totalDO.setModelNo(shikomiTotalDO.getModelNo());
                            //国内的用原数据库里面设置
                            if (Constants.COUNTRY_CN.equalsIgnoreCase(totalDO.getCompanyCode())) {
                                if ("1".equalsIgnoreCase(shikomiTotalDO.getClassCode())
                                        || "2".equalsIgnoreCase(shikomiTotalDO.getClassCode())
                                        || "3".equalsIgnoreCase(shikomiTotalDO.getClassCode())) {
                                    totalDO.setClassCode(shikomiTotalDO.getClassCode());
                                }
                            }
                            totalUpDateList.add(totalDO);
                            continue;
                        }

                        totalDO.setCreateUser(Constants.SHIKOMI_USER_JP);
                        totalDO.setCreateTime(new Date());
                        totalDO.setIsWarning(true);
                        shikomiTotalMapper.insert(totalDO);
                    }

                    // 批量更新
                    shikomiTotalMapper.updateShikomiTotalByList(totalUpDateList);

                    count = 0;
                    totalList = new ArrayList<>();
                    totalUpDateList = new ArrayList<>();
                }
            }

            shikomiModelMapper.updateMainFlag();

            log.info("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            log.error(e.getMessage());
        }
    }


    /**
     * 解析文件并插入数据库
     */
    public ResultVo<String> saveToShikomiTotal2(MultipartFile file) {
        ShikomiTotalDO item = null;
        ShikomiModelDO modelDO = null;
        try {
            DataInputStream in = new DataInputStream(file.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));
            Map<String, ShikomiTotalDO> map = new HashMap<>();
            Map<String, ShikomiModelDO> modelMap = new HashMap<>();

            String line;
            int i = 0;
            int num = 0;
            while ((line = br.readLine()) != null) {
                num++;
                i = 0;
                item = new ShikomiTotalDO();
                if (line.contains("NO79,NOTSUDUKI")) {
                    line = line.replace("NO79,NOTSUDUKI", "NO79.NOTSUDUKI");
                }

                String[] csv = line.replaceAll("\"", "").split(",");//去掉存入数据库中的""

                item.setSupplierCode(Constants.SUPPLIER_CODE_JP);
                item.setModelNo(csv[i++].trim());
                if (item.getModelNo().contains("NO79.NOTSUDUKI")) {
                    item.setModelNo(item.getModelNo().replace("NO79.NOTSUDUKI", "NO79,NOTSUDUKI"));
                }
                String shikomiNo = csv[i++].trim();
                int j = shikomiNo.length() - 4;
                if (shikomiNo.substring(j, j + 1).equals("-")) {
                    shikomiNo = shikomiNo.substring(0, j);
                }
                item.setShikomiNo(shikomiNo);
                item.setSubsidiaryCode(csv[i++].trim());
                item.setClassType(csv[i++].trim());
                item.setCompanyCode(csv[i++].trim());
                item.setBranchCode(csv[i++].trim());
                item.setLotQty(Integer.valueOf(csv[i++].trim()));
                item.setRemainQty(Integer.valueOf(csv[i++].trim()));
                item.setMaxQty(Integer.valueOf(csv[i++].trim()));
                item.setQtyOrdPre(0);
                item.setStatus(1);

                String date = csv[i++].trim();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (date.length() == 6) {
                    String str = "20" + date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4, 6);
                    item.setRegistDate(sdf.parse(str));
                }

                date = csv[i++].trim();
                if (date.length() == 6) {
                    String str = "20" + date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4, 6);
                    item.setReviseDate(sdf.parse(str));
                }

                i = csv.length - 4;
                item.setPartPrepareDays(Integer.valueOf(csv[i++].trim()));
                item.setAsseDays(Integer.valueOf(csv[i++].trim()));

                if (csv[i].contains("MONTHS NO ORDER")) {

                    item.setQtyNoord(Integer.valueOf(csv[i].replaceAll("[^0-9]", "")));
                } else {
                    item.setRemark(csv[i]);
                }
                //BUG10644 2023/05/05 WuJiaWen
                String rohs = csv[i + 1].trim();
                if ("H".equals(rohs)) {
                    item.setRohs("1");
                }

                if (item.getClassType().equals("C")) {
                    item.setClassCode("0");
                } else if ("A".equalsIgnoreCase(item.getClassType())) {
                    if (!Constants.COUNTRY_CN.equalsIgnoreCase(item.getCompanyCode())) {
                        item.setClassType("B");//国外管理不可以下单
                        item.setClassCode("4");//国内不可以用

                        if (Constants.SUBSIDIARY_CODE.equalsIgnoreCase(item.getSubsidiaryCode())) {
                            item.setClassCode("1");//国内共享
                        }
                    } else {
                        item.setClassCode("1");//国内共享
                    }
                }

                if (item.getModelNo().contains("*")) {

                    item.setModelType("2");//系列
                    String replace = item.getModelNo().replace("*", "%");
                    item.setSerialModel(replace);
                } else {
                    item.setModelType("1");//完整型号
                }

                //选择SubsidiaryCode为95012的数据
                String key = item.getShikomiNo() + "-" + item.getSupplierCode();
                if (map.containsKey(key)) {
                    if (item.getSubsidiaryCode().equals(Constants.SUBSIDIARY_CODE)) {
                        map.put(key, item);
                    }
                } else {
                    map.put(key, item);
                }

                modelDO = new ShikomiModelDO();
                String modelKey = item.getShikomiNo() + "-" + item.getModelNo();
                if (!modelMap.containsKey(modelKey)) {
                    modelDO.setShikomino(item.getShikomiNo());
                    modelDO.setModelno(item.getModelNo());
                    modelDO.setSerialModel(item.getSerialModel());
                    modelMap.put(modelKey, modelDO);
                }
            }

            LambdaQueryWrapper<ShikomiTotalDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(ShikomiTotalDO::getUpdateUser, "JP");
            queryWrapper2.or().eq(ShikomiTotalDO::getCreateUser, "JP");
            List<ShikomiTotalDO> list = shikomiTotalMapper.selectList(queryWrapper2);
            ShikomiTotalDO totalDO1;
            for (ShikomiTotalDO totalDO : list) {
                if (!map.containsKey(totalDO.getShikomiNo() + "-" + totalDO.getSupplierCode())) {
                    queryWrapper2.clear();
                    totalDO1 = new ShikomiTotalDO();
                    totalDO1.setStatus(2);
                    totalDO1.setId(totalDO.getId());
                    shikomiTotalMapper.updateById(totalDO1);
                }

            }
            List<ShikomiModelDO> doList = new ArrayList<>();
            List<ShikomiModelDO> updateList = new ArrayList<>();
            int count = 0;
            int size = 0;
            for (Map.Entry<String, ShikomiModelDO> entry : modelMap.entrySet()) {

                doList.add(entry.getValue());
                count ++;
                size++;

                //每次查500条
                if (count == 500 || size == modelMap.size()) {
                    // 查询出需要更新的数据
                    List<ShikomiModelDO> modelData = shikomiModelMapper.listShikomiModelData(doList);
                    Map<String, String> stringMap = modelData.stream().collect(Collectors.toMap(k -> k.getShikomino() + "-" + k.getModelno(), ShikomiModelDO::getShikomino, (key1, key2) -> key1));

                    for (ShikomiModelDO shikomiModelDO : doList) {
                        // 存在则放入更新List中，待会批量更新
                        if (stringMap.containsKey(shikomiModelDO.getShikomino() + "-" + shikomiModelDO.getModelno())) {
                            updateList.add(shikomiModelDO);
                            continue;
                        }
                        // 不存在则新增
                        shikomiModelDO.setCreateUser(Constants.SHIKOMI_USER_JP);
                        shikomiModelDO.setCreateTime(new Date());
                        try {
                            shikomiModelMapper.insert(shikomiModelDO);
                        } catch (Exception e) {
                            try {
                                LambdaUpdateWrapper<ShikomiModelDO> updateWrapper = new LambdaUpdateWrapper<>();
                                updateWrapper.eq(ShikomiModelDO::getShikomino, shikomiModelDO.getShikomino());
                                updateWrapper.eq(ShikomiModelDO::getModelno, shikomiModelDO.getModelno());
                                updateWrapper.set(ShikomiModelDO::getSerialModel, shikomiModelDO.getSerialModel());
                                updateWrapper.set(ShikomiModelDO::getUpdateUser, Constants.SHIKOMI_USER_JP);
                                updateWrapper.set(ShikomiModelDO::getUpdateTime, new Date());
                                shikomiModelMapper.update(null, updateWrapper);
                            } catch (Exception exception) {
                                // 计入日志
                                OrderLogVO log = new OrderLogVO();
                                log.setOrderNo(shikomiModelDO.getShikomino());
                                log.setDescription(shikomiModelDO.getModelno()+"保存进shikomi_model中失败");
                                log.setOptTime(new Date());
                                log.setCreateTime(new Date());
                                orderLogFeignApi.addOrderLog(log);
                            }
                        }
                    }
                    // 批量更新
                    shikomiModelMapper.updateShikomiModelByList(updateList);
                    count = 0;
                    doList = new ArrayList<>();
                    updateList = new ArrayList<>();
                }

            }
            modelMap.clear();
            List<ShikomiTotalDO> totalList = new ArrayList<>();
            List<ShikomiTotalDO> totalUpDateList = new ArrayList<>();
            count = 0;
            size = 0;
            // 保存进shikomi_total
            for (Map.Entry<String, ShikomiTotalDO> entry : map.entrySet()) {
                totalList.add(entry.getValue());
                count ++;
                size ++;
                if (count == 80 || size == map.size()) {
                    List<ShikomiTotalDO> totalDOList = shikomiTotalMapper.selectShikomiTotalByList(totalList);
                    Map<String, ShikomiTotalDO> totalDOMap = totalDOList.stream().collect(Collectors.toMap(k -> k.getShikomiNo() + "-" + k.getSupplierCode(), v -> v, (key1, key2) -> key1));
                    for (ShikomiTotalDO totalDO : totalList) {

                        if (totalDOMap.containsKey(totalDO.getShikomiNo() + "-" + totalDO.getSupplierCode())) {
                            ShikomiTotalDO shikomiTotalDO = totalDOMap.get(totalDO.getShikomiNo() + "-" + totalDO.getSupplierCode());
                            if (PublicUtil.isNotEmpty(shikomiTotalDO.getRegistDate())) {
                                totalDO.setRegistDate(shikomiTotalDO.getRegistDate());
                            }
                            totalDO.setModelNo(shikomiTotalDO.getModelNo());
                            //国内的用原数据库里面设置
                            if (Constants.COUNTRY_CN.equalsIgnoreCase(totalDO.getCompanyCode())) {
                                if ("1".equalsIgnoreCase(shikomiTotalDO.getClassCode())
                                        || "2".equalsIgnoreCase(shikomiTotalDO.getClassCode())
                                        || "3".equalsIgnoreCase(shikomiTotalDO.getClassCode())) {
                                    totalDO.setClassCode(shikomiTotalDO.getClassCode());
                                }
                            }
                            totalUpDateList.add(totalDO);
                            continue;
                        }

                        totalDO.setCreateUser(Constants.SHIKOMI_USER_JP);
                        totalDO.setCreateTime(new Date());
                        totalDO.setIsWarning(true);
                        shikomiTotalMapper.insert(totalDO);
                    }
                    // 批量更新
                    shikomiTotalMapper.updateShikomiTotalByList(totalUpDateList);
                    count = 0;
                    totalList = new ArrayList<>();
                    totalUpDateList = new ArrayList<>();
                }
            }
            shikomiModelMapper.updateMainFlag();
            return ResultVo.success("解析DBSKM成功, 共计"+num+"条");
        } catch (Exception e) {
            log.error("解析DBSKM失败 {} ", e.getMessage(), e);
           return ResultVo.failure("解析DBSKM失败"+e.getMessage());
        }
    }

    public void insertLog() {

    }

}