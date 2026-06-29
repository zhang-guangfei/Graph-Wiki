package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.enums.SendStatusEnum;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.sampleorder.SampleOrderManageMapper;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DepartmentVO;
import com.smc.smccloud.model.OpsMailDO;
import com.smc.smccloud.model.constants.Constants;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.sampleorder.*;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.OpsCommonService;
import com.smc.smccloud.service.OrderLogFeignApi;
import com.smc.smccloud.service.SampleOrderManageService;
import com.smc.smccloud.utils.JasperHelper;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-08-01
 */
@Service
@Slf4j
public class SampleOrderManageServiceImpl extends ServiceImpl<SampleOrderManageMapper, SampleOrderManageDO> implements SampleOrderManageService {
    @Resource
    private SampleOrderManageMapper sampleOrderManageMapper;
    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private HttpServletResponse response;
    @Resource
    private RedisManager redisManager;
    @Resource
    private JavaMailSenderImpl mailSender;
    @Resource
    private OrderLogFeignApi orderLogFeignApi;
    @Resource
    private SampleOrderManageService sampleOrderManageService;

    @Value("${ops-file-upload-path.url}")
    private String filePath;

    @Override
    @DS("sharedb")
    public int insertSampleOrderManage(SampleOrderManageDO sampleOrderManageDO) {
         return  sampleOrderManageMapper.insert(sampleOrderManageDO);
    }

    @Override
    public ResultVo<PageInfo<SampleOrderManageVO>> listSampleOrderManage(SampleOrderManageQuery request, Page page) {
        if (request == null) {
            return ResultVo.failure("参数不可为空");
        }

        LambdaQueryWrapper<SampleOrderManageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(CollectionUtils.isNotEmpty(request.getDeptNos()),SampleOrderManageDO::getDeptNo,request.getDeptNos())
                .likeRight(StringUtils.isNotBlank(request.getOrderNo()),SampleOrderManageDO::getOrderNo,request.getOrderNo())
                .eq(StringUtils.isNotBlank(request.getModelNo()),SampleOrderManageDO::getModelNo,request.getModelNo())
                .ge(StringUtils.isNotBlank(request.getInDateStart()),SampleOrderManageDO::getOutTime,request.getInDateStart())
                .le(StringUtils.isNotBlank(request.getInDateEnd()),SampleOrderManageDO::getOutTime,request.getInDateEnd())
                .ge(StringUtils.isNotBlank(request.getCreDateStart()),SampleOrderManageDO::getCreateTime,request.getCreDateStart())
                .le(StringUtils.isNotBlank(request.getCreDateEnd()),SampleOrderManageDO::getCreateTime,request.getCreDateEnd())
                .ge(StringUtils.isNotBlank(request.getShipDateStart()),SampleOrderManageDO::getShipDate,request.getShipDateStart())
                .le(StringUtils.isNotBlank(request.getShipDateEnd()),SampleOrderManageDO::getShipDate,request.getShipDateEnd())
                .orderByDesc(SampleOrderManageDO::getUpdateTime);

        PageInfo<SampleOrderManageDO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize()).doSelectPageInfo(() -> {
            sampleOrderManageMapper.selectList(queryWrapper);
        });
        PageInfo<SampleOrderManageVO> pageInfoVO = BeanCopyUtil.pageDto2Vo(pageInfo, SampleOrderManageVO.class);
        pageInfoVO.setList(handCommonData(pageInfoVO.getList()));
        return ResultVo.success(pageInfoVO);
    }

    public List<SampleOrderManageVO> handCommonData(List<SampleOrderManageVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(com.smc.smccloud.model.constants.Constants.ZLZS_MANAGE_STATUS);
        Map<String,String> map = new HashMap<>();
        if (dataCodes.isSuccess() && CollectionUtils.isNotEmpty(dataCodes.getData())) {
            for (DataCodeVO item : dataCodes.getData()) {
                map.put(item.getCode(),item.getCodeName());
            }
        }
        for (SampleOrderManageVO item : list) {
            if (StringUtils.isNotBlank(item.getDeptNo())) {
                item.setDeptName("["+item.getDeptNo()+"]"+opsCommonService.getDeptNameByNo(item.getDeptNo()));
            }

            if (StringUtils.isNotBlank(item.getParentDeptNo())) {
                item.setParentDeptName("["+item.getParentDeptNo()+"]"+opsCommonService.getDeptNameByNo(item.getParentDeptNo()));
            }

            if (StringUtils.isNotBlank(item.getManager())) {
                item.setManageName("["+item.getManager()+"]"+opsCommonService.getEmpSaleNameByNo(item.getManager()));
            }
            if (item.getStatus() != null) {
                item.setStatusName(map.get(String.valueOf(item.getStatus())));
            }

            if (StringUtils.isNotBlank(item.getUpdateUser())) {
                item.setUpdateUserName("["+item.getUpdateUser()+"]"+opsCommonService.getEmpSaleNameByNo(item.getUpdateUser()));
            }

        }
        return list;
    }

    @Override
    public void exportSampleOrderManage(SampleOrderManageQuery request) {
        long startTimer = System.currentTimeMillis();
        LambdaQueryWrapper<SampleOrderManageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(CollectionUtils.isNotEmpty(request.getDeptNos()),SampleOrderManageDO::getDeptNo,request.getDeptNos())
                .eq(StringUtils.isNotBlank(request.getOrderNo()),SampleOrderManageDO::getOrderNo,request.getOrderNo())
                .eq(StringUtils.isNotBlank(request.getModelNo()),SampleOrderManageDO::getModelNo,request.getModelNo())
                .ge(StringUtils.isNotBlank(request.getInDateStart()),SampleOrderManageDO::getOutTime,request.getInDateStart())
                .le(StringUtils.isNotBlank(request.getInDateEnd()),SampleOrderManageDO::getOutTime,request.getInDateEnd())
                .ge(StringUtils.isNotBlank(request.getCreDateStart()),SampleOrderManageDO::getCreateTime,request.getCreDateStart())
                .le(StringUtils.isNotBlank(request.getCreDateEnd()),SampleOrderManageDO::getCreateTime,request.getCreDateEnd())
                .ge(StringUtils.isNotBlank(request.getShipDateStart()),SampleOrderManageDO::getShipDate,request.getShipDateStart())
                .le(StringUtils.isNotBlank(request.getShipDateEnd()),SampleOrderManageDO::getShipDate,request.getShipDateEnd());

        List<SampleOrderManageDO> sampleOrderManageDOS = sampleOrderManageMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(sampleOrderManageDOS)) {
            return;
        }
        String path = "template/ExportSampleOrderManage.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.openSheet(0);
        // 向模板中写入数据
        int row = 1;
        int cel;
        List<SampleOrderManageVO> sampleOrderManageVOS = handCommonData(BeanCopyUtil.copyList(sampleOrderManageDOS, SampleOrderManageVO.class));
        for (SampleOrderManageVO item : sampleOrderManageVOS) {
            cel = 0;
            excel.setCellValue(row, cel++, item.getOrderNo());
            excel.setCellValue(row, cel++, item.getModelNo());
            excel.setCellValue(row, cel++, item.getImpQty());
            excel.setCellValue(row, cel++, item.getShipDate());
            excel.setCellValue(row, cel++, item.getOutTime());
            excel.setCellValue(row, cel++, item.getStatusName());
            excel.setCellValue(row, cel++, item.getDeptName());
            excel.setCellValue(row, cel++, item.getParentDeptName());
            excel.setCellValue(row, cel++, item.getManageName());
            excel.setCellValue(row, cel++, item.getRemark());
            excel.setCellValue(row, cel++, item.getUpdateUserName());
            excel.setCellValue(row, cel++, item.getCreateTime());
            excel.setCellValue(row, cel, item.getUpdateTime());
            row++;
        }
        excel.writeToResponse(response, "ExportSampleBalData.xlsx");
        long endTimer = System.currentTimeMillis();
        log.info("展览展示品导出 " + sampleOrderManageDOS.size() + " 耗时: " + (endTimer - startTimer) / 1000 + " 秒");
    }

    @Override
    public void exportZlzsManageData(SampleOrderManageQuery request) {

        if(request == null || StringUtils.isBlank(request.getInDateStart()) || StringUtils.isBlank(request.getInDateEnd())) {
            return;
        }

        log.info(" 盘点导出参数 {}",JSONObject.toJSONString(request));

        List<SampleOrderManageVO> sampleOrderManageVOS = sampleOrderManageMapper.getSampleOrderManageNeZero(request.getInDateStart(),request.getInDateEnd());
        if (CollectionUtils.isEmpty(sampleOrderManageVOS)) {
            return;
        }
        log.info("查出盘点数据 {} 条 ",sampleOrderManageVOS.size());
        Map<String, SampleOrderManageVO> mergeByOrderAndModelAndDeptNoMap = new HashMap<>();
        Map<String,List<SampleOrderManageVO>> mapByDeptNo = new HashMap<>();
        // 根据订单号型号实物所在部门合并数量
        for (SampleOrderManageVO item : sampleOrderManageVOS) {
            String key = item.getOrderNo()+"-"+item.getModelNo()+"-"+item.getParentDeptNo();
           if (mergeByOrderAndModelAndDeptNoMap.containsKey(key)) {
               SampleOrderManageVO sampleOrderManageVO = mergeByOrderAndModelAndDeptNoMap.get(key);
               if (item.getImpQty() == null) {
                   item.setImpQty(0);
               }
               sampleOrderManageVO.setImpQty(sampleOrderManageVO.getImpQty()+item.getImpQty());
               sampleOrderManageVO.setOrderNo(sampleOrderManageVO.getOrderNo());
               if (StringUtils.isBlank(sampleOrderManageVO.getRemark())) {
                   sampleOrderManageVO.setRemark("");
               }
               if (StringUtils.isBlank(item.getRemark())) {
                   item.setRemark("");
               }
               if (!sampleOrderManageVO.getRemark().contains(item.getRemark())) {
                   sampleOrderManageVO.setRemark(sampleOrderManageVO.getRemark()+";"+item.getRemark());
               }
               mergeByOrderAndModelAndDeptNoMap.put(key,sampleOrderManageVO);
           } else {
               if (item.getImpQty() == null) {
                   item.setImpQty(0);
               }
               mergeByOrderAndModelAndDeptNoMap.put(key,item);
           }
        }
        List<SampleOrderManageVO> list = new ArrayList<>(mergeByOrderAndModelAndDeptNoMap.values());
        // 按照营业所分组
        for (SampleOrderManageVO item : list) {
            if (StringUtils.isBlank(item.getParentDeptNo())) {
                continue;
            }
            if (mapByDeptNo.containsKey(item.getParentDeptNo())) {
                List<SampleOrderManageVO> sampleOrderManageVOList = mapByDeptNo.get(item.getParentDeptNo());
                sampleOrderManageVOList.add(item);
                mapByDeptNo.put(item.getParentDeptNo(),sampleOrderManageVOList);
            } else {
                List<SampleOrderManageVO> sampleOrderManageVOList = new ArrayList<>();
                sampleOrderManageVOList.add(item);
                mapByDeptNo.put(item.getParentDeptNo(),sampleOrderManageVOList);
            }
        }
        // 存入所有的盘点结余数量
        mapByDeptNo.put("allData",list);
        String downloadName = "样品管理-展览展示管理.zip"; // 压缩包名称
        response.setContentType("application/zip; charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + downloadName);
        // 按照营业所导出excel
        String deptName;
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String deptNo : mapByDeptNo.keySet()) {
            List<SampleOrderManageVO> sampleOrderManageVOList = mapByDeptNo.get(deptNo);
            if (CollectionUtils.isEmpty(sampleOrderManageVOList)) {
                return;
            }
            sampleOrderManageVOList = sampleOrderManageVOList.stream().filter(SampleOrderManageVO -> SampleOrderManageVO.getImpQty() != 0).collect(Collectors.toList());
            if (deptNo.equals("allData")) {
                deptName = "";
            } else {
                deptName = opsCommonService.getDeptNameByNo(deptNo);
            }
            // 部门为空导出所有部门到一个excel
            Workbook workbook = exportAllZlzsSampleOrderManage(sampleOrderManageVOList, deptName, deptNo, request.getInDateEnd());
            if (workbook == null ) {
                continue;
            }
            try {
                ZipEntry entry = new ZipEntry(deptNo+deptName+"展览展示品盘点票.xlsx");
                zipOutputStream.putNextEntry(entry);
                ByteOutputStream byteOutputStream = new ByteOutputStream();
                workbook.write(byteOutputStream);
                byteOutputStream.writeTo(zipOutputStream);
                byteOutputStream.close();
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 存储导出时间
        redisManager.set(Constants.ZLZS_EXPORT_TIME,request.getInDateStart()+"="+request.getInDateEnd());

    }

    @Override
    public ResultVo<String> pushZlzsSampleOrderManageForPdf(SampleOrderManageQuery request) {
        if(request == null || StringUtils.isBlank(request.getInDateStart()) || StringUtils.isBlank(request.getInDateEnd())) {
            return ResultVo.failure("未获取盘点票导出的结转日期范围,请先进行盘点票导出操作");
        }
        List<SampleOrderManageVO> sampleOrderManageVOS = sampleOrderManageMapper.getSampleOrderManageNeZero(request.getInDateStart(),request.getInDateEnd());
        if (CollectionUtils.isEmpty(sampleOrderManageVOS)) {
            return ResultVo.failure("暂无所需发布的数据.");
        }

        Map<String, SampleOrderManageVO> mergeByOrderAndModelAndDeptNoMap = new HashMap<>();
        Map<String,List<SampleOrderManageVO>> mapByDeptNo = new HashMap<>();
        // 根据订单号型号实物所在部门合并数量
        for (SampleOrderManageVO item : sampleOrderManageVOS) {
            String key = item.getOrderNo()+"-"+item.getModelNo()+"-"+item.getParentDeptNo();
            if (mergeByOrderAndModelAndDeptNoMap.containsKey(key)) {
                SampleOrderManageVO sampleOrderManageVO = mergeByOrderAndModelAndDeptNoMap.get(key);
                if (item.getImpQty() == null) {
                    item.setImpQty(0);
                }
                sampleOrderManageVO.setImpQty(sampleOrderManageVO.getImpQty()+item.getImpQty());
                sampleOrderManageVO.setOrderNo(sampleOrderManageVO.getOrderNo());
                if (StringUtils.isBlank(item.getRemark())) {
                    item.setRemark(" ");
                }
                if (StringUtils.isBlank(sampleOrderManageVO.getRemark())) {
                    sampleOrderManageVO.setRemark(" ");
                }
                if (sampleOrderManageVO.getRemark() != null && !sampleOrderManageVO.getRemark().contains(item.getRemark())) {
                    sampleOrderManageVO.setRemark(sampleOrderManageVO.getRemark()+";"+item.getRemark());
                }
                mergeByOrderAndModelAndDeptNoMap.put(key,sampleOrderManageVO);
            } else {
                if (item.getImpQty() == null) {
                    item.setImpQty(0);
                }
                mergeByOrderAndModelAndDeptNoMap.put(key,item);
            }
        }
        List<SampleOrderManageVO> list = new ArrayList<>(mergeByOrderAndModelAndDeptNoMap.values());
        // 按照营业所分组
        for (SampleOrderManageVO item : list) {
            if (StringUtils.isBlank(item.getParentDeptNo())) {
                continue;
            }
            if (mapByDeptNo.containsKey(item.getParentDeptNo())) {
                List<SampleOrderManageVO> sampleOrderManageVOList = mapByDeptNo.get(item.getParentDeptNo());
                sampleOrderManageVOList.add(item);
                mapByDeptNo.put(item.getParentDeptNo(),sampleOrderManageVOList);
            } else {
                List<SampleOrderManageVO> sampleOrderManageVOList = new ArrayList<>();
                sampleOrderManageVOList.add(item);
                mapByDeptNo.put(item.getParentDeptNo(),sampleOrderManageVOList);
            }
        }
        // 存入所有的盘点结余数量
        mapByDeptNo.put("allData",list);
        String deptName;
        StringBuilder errMsg = new StringBuilder();
        for (String deptNo : mapByDeptNo.keySet()) {
            if (redisManager.hasKey(Constants.ZLZS_SEND_DEPTNOS+deptNo)) {
                continue;
            }
            List<SampleOrderManageVO> sampleOrderManageVOList = mapByDeptNo.get(deptNo);
            if (CollectionUtils.isEmpty(sampleOrderManageVOList)) {
                continue;
            }
            sampleOrderManageVOList = sampleOrderManageVOList.stream().filter(SampleOrderManageVO -> SampleOrderManageVO.getImpQty() != 0).collect(Collectors.toList());
            if (deptNo.equals("allData")) {
                deptName = "全部所";
            } else {
                deptName = opsCommonService.getDeptNameByNo(deptNo);
            }
            // 生成pdf
            ZlzsExportPdfVO zlzsExportPdfVO = new ZlzsExportPdfVO();
            List<ZlszPdfTableVo> zlzsExportPdfVOS = new ArrayList<>();
            for (SampleOrderManageVO item : sampleOrderManageVOList) {
                ZlszPdfTableVo vo = new ZlszPdfTableVo();
                vo.setModelNo(item.getModelNo());
                vo.setOrderNo(item.getOrderNo());
                vo.setQuantity(item.getImpQty());
                vo.setRemark(item.getRemark());
                zlzsExportPdfVOS.add(vo);
            }
            zlzsExportPdfVO.setTabledataWithZlzs(zlzsExportPdfVOS);
            Map<String, Object> map = new HashMap<>();
            map.put("deptName",deptName);
            map.put("endTime",request.getInDateEnd());
            map.put("nowDate","");

            List<ZlzsExportPdfVO> zlzsExportPdfVOList = new ArrayList<>();
            zlzsExportPdfVOList.add(zlzsExportPdfVO);

            InputStream inputStream = FileUtil.getTemplate("template/jasper/zlzsBalance.jasper");
            String title = "展览展示品盘点票-"+deptName;
            // String fileName = title + ".pdf";
            InputStream streamPdf = null;
            try {
                streamPdf = JasperHelper.savePdfToInputStrem(inputStream, map, zlzsExportPdfVOList);
            } catch (JRException e) {
                log.error(deptName+"预览打印展览展示品盘点票发生异常,",e);
                errMsg.append(deptName+"生成pdf文件失败.");
                continue;
            }
            ResultVo<String> resultVo = sendPdfEamilWithDept(deptNo, title, streamPdf);
            if (!resultVo.isSuccess()) {
                errMsg.append(resultVo.getMessage()+";");
            } else {
                // 将成功的存入redis
                redisManager.set(Constants.ZLZS_SEND_DEPTNOS+deptNo,deptNo,3600*24*7);
            }
        }
        if (StringUtils.isBlank(errMsg.toString())) {
            return ResultVo.success("发送成功,请注意查收");
        }
        return ResultVo.failure(errMsg.toString());
    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    public ResultVo<String> zlzsOrderWriteOff(SampleOrderManageVO dto) {

        if (StringUtils.isBlank(dto.getOrderNo())) {
            throw new BusinessException("订单号不可为空.");
            // return ResultVo.failure("订单号不可为空.");
        }
        if (StringUtils.isBlank(dto.getModelNo())) {
            throw new BusinessException(dto.getOrderNo()+"型号不可为空.");
            // return ResultVo.failure("型号不可为空");
        }
//        if (dto.getImpQty() <= 0) {
//            throw new BusinessException(dto.getOrderNo()+" "+dto.getModelNo()+"销账数量不可为0且请输入正数.");
//            // return ResultVo.failure("销账数量不可为0且请输入正数");
//        }
        // 销账数量小于等于剩余数量才可销账
        List<SampleOrderManageVO> sampleOrderManageVOList = sampleOrderManageMapper.getSampleOrderManageByOrderNo(dto.getOrderNo(), dto.getModelNo());
        if (CollectionUtils.isEmpty(sampleOrderManageVOList)) {
            throw new BusinessException(dto.getOrderNo()+" "+dto.getModelNo()+"没有可销账的数据");
            // return ResultVo.failure(dto.getOrderNo()+dto.getModelNo()+"没有可销账的数据");
        }
        SampleOrderManageVO sampleOrderManageVO = sampleOrderManageVOList.get(0);
        if (dto.getImpQty() > sampleOrderManageVO.getImpQty()) {
            throw new BusinessException(dto.getOrderNo()+" "+dto.getModelNo()+"销账数量小于等于剩余数量才可销账");
            // return ResultVo.failure("销账数量小于等于剩余数量才可销账");
        }
        LambdaQueryWrapper<SampleOrderManageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SampleOrderManageDO::getOrderNo,sampleOrderManageVO.getOrderNo())
                .eq(SampleOrderManageDO::getModelNo,sampleOrderManageVO.getModelNo());
        SampleOrderManageDO sampleOrderManageDO = sampleOrderManageMapper.selectList(queryWrapper).get(0);

        // 写入销账数据(负数)
        sampleOrderManageDO.setId(null);
        sampleOrderManageDO.setImpQty(dto.getImpQty()*-1);
        if (StringUtils.isNotBlank(dto.getManager())) {
            sampleOrderManageDO.setManager(dto.getManager());
        }
        if (StringUtils.isNotBlank(dto.getDeptNo())) {
            sampleOrderManageDO.setDeptNo(dto.getDeptNo());
        }
        sampleOrderManageDO.setOutTime(new Date());
        sampleOrderManageDO.setStatus(1);
        sampleOrderManageDO.setCreateTime(new Date());
        sampleOrderManageDO.setUpdateTime(new Date());
        sampleOrderManageDO.setUpdateUser(dto.getManageName());
        sampleOrderManageDO.setRemark(dto.getManageName()+"销账"+dto.getImpQty());

        int insert = 0;
        try {
            insert = sampleOrderManageMapper.insert(sampleOrderManageDO);
        } catch (Exception e) {
            throw new BusinessException("订单号"+dto.getOrderNo()+"型号"+dto.getModelNo()+"销账"+dto.getImpQty()+"个失败.");
        }
        // 记录销账操作日志
        insertOptLog(sampleOrderManageDO,"展览展示品销账"+dto.getImpQty(),dto.getManageName());
//        if (insert != 1) {
//           return ResultVo.failure("订单号"+dto.getOrderNo()+"型号"+dto.getModelNo()+"销账"+dto.getImpQty()+"个失败.");
//        }
        return ResultVo.success("订单号"+dto.getOrderNo()+"型号"+dto.getModelNo()+"销账"+dto.getImpQty()+"个成功.");
    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    @DS("sharedb")
    public ResultVo<String> batchImportWriteOffData(MultipartFile file, String loginUser) {

        if (file == null) {
            return ResultVo.failure("请上传文件");
        }

        if (StringUtils.isBlank(loginUser)) {
            return ResultVo.failure("操作人为空.请退出重新登录");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename) && !filename.matches("^.+\\.(?i)(xlsx)$")
                && !filename.matches("^.+\\.(?i)(xls)$")) {
            return ResultVo.failure("文件格式错误,请按照模板文件格式进行导入");
        }
        ExcelHelper excel = null;
        try {
            excel = new ExcelHelper(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (excel == null) {
            return ResultVo.failure("未读取到文件.");
        }
        Sheet sheet = excel.getSheet();
        int row = 0;
        Row rows;
        SampleOrderManageVO sampleOrderManageVO;
        ResultVo<String> resultVo;
        StringBuilder errMsg = new StringBuilder();
        int count = 0;
        while (true) {
            row++;
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            String rorderNo = excel.getCellString(rows.getCell(0));
            if (StringUtils.isBlank(rorderNo)) {
                throw new BusinessException("第"+row+"行的订单号为空.请仔细检查数据");
            }
            String modelNo = excel.getCellString(rows.getCell(1));
            if (StringUtils.isBlank(modelNo)) {
                throw new BusinessException("第"+row+"行的型号为空.请仔细检查数据");
            }
            String qtyStr = excel.getCellString(rows.getCell(2));
            if (StringUtils.isBlank(qtyStr)) {
                throw new BusinessException("第"+row+"行的数量为空.请仔细检查数据");
            }
            int qty = Integer.parseInt(qtyStr);
//            if (qty <= 0) {
//                throw new BusinessException("第"+row+"行的数量小于等于0.请仔细检查数据");
//            }
            String deptNo = excel.getCellString(rows.getCell(3));
            String manager = excel.getCellString(rows.getCell(4));

            sampleOrderManageVO = new SampleOrderManageVO();
            sampleOrderManageVO.setOrderNo(rorderNo);
            sampleOrderManageVO.setModelNo(modelNo);
            sampleOrderManageVO.setImpQty(qty);
            sampleOrderManageVO.setDeptNo(deptNo);
            sampleOrderManageVO.setManager(manager);
            sampleOrderManageVO.setManageName(loginUser); // 为了记录日志,借用manageName存储当前操作人
            try {
                resultVo = sampleOrderManageService.zlzsOrderWriteOff(sampleOrderManageVO);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
//            if (!resultVo.isSuccess()) {
//                errMsg.append(resultVo.getMessage()+";");
//            } else {
//
//            }
            count++;
        }
        if (StringUtils.isBlank(errMsg.toString())) {
            return ResultVo.success("批量销账完毕.共计"+count+"条");
        }
        return ResultVo.failure(errMsg.toString());

    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    public ResultVo<String> upSampleOrderManageDeptNo(SampleOrderManageVO sampleOrderManageVO) {

        if(sampleOrderManageVO == null) {
            throw new BusinessException("请选择所要修正的数据");
        }

        if (StringUtils.isBlank(sampleOrderManageVO.getOrderNo())) {
            throw new BusinessException("订单号不可为空");
        }

        if (StringUtils.isBlank(sampleOrderManageVO.getDeptNo())) {
            throw new BusinessException(sampleOrderManageVO.getOrderNo()+"需要变更的展示品实物所在部门不可为空");
        }
        int sumImpQty = sampleOrderManageMapper.getSumQtyByOrderNo(sampleOrderManageVO.getOrderNo(),sampleOrderManageVO.getBeforeUpDeptNo());
        if (sumImpQty == 0) {
            return ResultVo.failure("没有剩余数量,不可变更部门");
        }
        LambdaUpdateWrapper<SampleOrderManageDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(sampleOrderManageVO.getId() != null,SampleOrderManageDO::getId,sampleOrderManageVO.getId())
                .eq(StringUtils.isNotBlank(sampleOrderManageVO.getOrderNo()),SampleOrderManageDO::getOrderNo,sampleOrderManageVO.getOrderNo())
                .set(SampleOrderManageDO::getUpdateTime,new Date())
                .set(SampleOrderManageDO::getUpdateUser,sampleOrderManageVO.getManageName())
                .set(SampleOrderManageDO::getDeptNo,sampleOrderManageVO.getDeptNo());
        int update = 0;
        try {
            update = sampleOrderManageMapper.update(null, updateWrapper);
        } catch (Exception e) {
            throw new BusinessException(sampleOrderManageVO.getOrderNo()+"变更展示品实物所在部门为"+sampleOrderManageVO.getDeptNo()+"失败.");
        }

        // 记录日志
        SampleOrderManageDO sampleOrderManageDO = BeanCopyUtil.copy(sampleOrderManageVO, SampleOrderManageDO.class);
        insertOptLog(sampleOrderManageDO,"变更展示品实物营业所为"+sampleOrderManageVO.getDeptNo(),sampleOrderManageVO.getManageName());

        return ResultVo.success(sampleOrderManageVO.getOrderNo()+"变更展示品实物所在部门为"+sampleOrderManageVO.getDeptNo()+"成功.");
    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    @DS("sharedb")
    public ResultVo<String> batchUpSampleOrderManageDeptNo(MultipartFile file, String loginUser) {

        if (file == null) {
            return ResultVo.failure("请上传文件");
        }

        if (StringUtils.isBlank(loginUser)) {
            return ResultVo.failure("操作人为空.");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename) && !filename.matches("^.+\\.(?i)(xlsx)$")
                && !filename.matches("^.+\\.(?i)(xls)$")) {
            return ResultVo.failure("文件格式错误,请按照模板文件格式进行导入");
        }
        ExcelHelper excel = null;
        try {
            excel = new ExcelHelper(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (excel == null) {
            return ResultVo.failure("未读取到文件.");
        }
        Sheet sheet = excel.getSheet();
        int row = 0;
        Row rows;
        SampleOrderManageVO sampleOrderManageVO;
        ResultVo<String> resultVo;
        StringBuilder errMsg = new StringBuilder();
        int count = 0;
        while (true) {
            row++;
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            String orderNo = excel.getCellString(rows.getCell(0));
            if (StringUtils.isBlank(orderNo)) {
                throw new BusinessException("第"+row+"行的订单号为空.请仔细检查数据");
            }
            String beforeUpdeptNo = excel.getCellString(rows.getCell(1));
            if (StringUtils.isBlank(beforeUpdeptNo)) {
                throw new BusinessException("第"+row+"行的变更前展示品部门为空.请仔细检查数据");
            }
            String afterUpDeptNo = excel.getCellString(rows.getCell(2));
            if (StringUtils.isBlank(afterUpDeptNo)) {
                throw new BusinessException("第"+row+"行的变更后展示品部门为空.请仔细检查数据");
            }
            sampleOrderManageVO = new SampleOrderManageVO();
            sampleOrderManageVO.setManageName(loginUser);
            sampleOrderManageVO.setOrderNo(orderNo);
            sampleOrderManageVO.setBeforeUpDeptNo(beforeUpdeptNo);
            sampleOrderManageVO.setDeptNo(afterUpDeptNo);
            try {
                resultVo = sampleOrderManageService.upSampleOrderManageDeptNo(sampleOrderManageVO);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
//            if (!resultVo.isSuccess()) {
//                errMsg.append(resultVo.getMessage()+";");
//            } else {
//                count++;
//            }
            count++;
        }
        if (StringUtils.isBlank(errMsg.toString())) {
            return ResultVo.success("批量变更完毕. 共计变更"+count+"条");
        }
        return ResultVo.failure(errMsg.toString());
    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    @DS("sharedb")
    public ResultVo<String> batchImportSampleOrderManageData(MultipartFile file, String loginUser) {
        if (file == null) {
            return ResultVo.failure("请上传文件");
        }
        if (StringUtils.isBlank(loginUser)) {
            return ResultVo.failure("操作人为空.");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename) && !filename.matches("^.+\\.(?i)(xlsx)$")
                && !filename.matches("^.+\\.(?i)(xls)$")) {
            return ResultVo.failure("文件格式错误,请按照模板文件格式进行导入");
        }
        ExcelHelper excel = null;
        try {
            excel = new ExcelHelper(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (excel == null) {
            return ResultVo.failure("未读取到文件.");
        }
        Sheet sheet = excel.getSheet();
        int row = 0;
        Row rows;
        SampleOrderManageDO sampleOrderManageDO;
        int count = 0;
        while (true) {
            row++;
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            String orderNo = excel.getCellString(rows.getCell(0));
            if (StringUtils.isBlank(orderNo)) {
                throw new BusinessException("第"+row+"行的订单号为空.请仔细检查数据");
            }
            String modelNo = excel.getCellString(rows.getCell(1));
            if (StringUtils.isBlank(modelNo)) {
                throw new BusinessException("第"+row+"行的型号为空.请仔细检查数据");
            }
            String shipDate = excel.getCellString(rows.getCell(2));

            String impQty = excel.getCellString(rows.getCell(3));
            if (StringUtils.isBlank(impQty) || Integer.parseInt(impQty) == 0) {
                throw new BusinessException("第"+row+"行的结转数量不可为空或等于0.请仔细检查数据");
            }

            String deptNo = excel.getCellString(rows.getCell(4));
            if (StringUtils.isBlank(deptNo)) {
                throw new BusinessException("第"+row+"行的实物所在营业所不可为空.请仔细检查数据");
            }

            String manager = excel.getCellString(rows.getCell(5));

            String outTime = excel.getCellString(rows.getCell(6));
            if (StringUtils.isBlank(outTime)) {
                throw new BusinessException("第"+row+"行的结转时间不可为空.请仔细检查数据");
            }
            String remark = excel.getCellString(rows.getCell(7));

            sampleOrderManageDO = new SampleOrderManageDO();
            sampleOrderManageDO.setOrderNo(orderNo);
            sampleOrderManageDO.setModelNo(modelNo);
            if (StringUtils.isBlank(shipDate)){
                sampleOrderManageDO.setShipDate(null);
            } else {
                sampleOrderManageDO.setShipDate(DateUtil.stringToDate(shipDate));
            }
            sampleOrderManageDO.setImpQty(Integer.parseInt(impQty));
            sampleOrderManageDO.setDeptNo(deptNo);
            sampleOrderManageDO.setManager(manager);
            sampleOrderManageDO.setOutTime(DateUtil.stringToDate(outTime));
            sampleOrderManageDO.setRemark(remark);
            sampleOrderManageDO.setUpdateUser(loginUser);
            sampleOrderManageDO.setStatus(1);
            try {
                 sampleOrderManageService.importSampleOrderManageData(sampleOrderManageDO);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            count++;
        }
        return ResultVo.success("批量导入完毕.共计"+count+"条");
    }


    @Override
    @Transactional(rollbackFor = Exception.class )
    public ResultVo<String> importSampleOrderManageData(SampleOrderManageDO sampleOrderManageDO) {

        if (sampleOrderManageDO == null) {
            throw new RuntimeException("导入的数据不可为空.");
        }
        if (StringUtils.isBlank(sampleOrderManageDO.getOrderNo())) {
            throw new RuntimeException("订单号不可为空.");
        }

        if (StringUtils.isBlank(sampleOrderManageDO.getDeptNo())) {
            throw new RuntimeException("实物所在营业所不可为空.");
        }

        if (sampleOrderManageDO.getImpQty() == 0) {
            throw new RuntimeException(sampleOrderManageDO.getOrderNo()+"数量不可为0");
        }
        if (StringUtils.isBlank(sampleOrderManageDO.getModelNo())) {
            throw new RuntimeException(sampleOrderManageDO.getOrderNo()+"型号不可为空.");
        }
        if (StringUtils.isBlank(sampleOrderManageDO.getUpdateUser())) {
            throw new RuntimeException(sampleOrderManageDO.getOrderNo()+"操作人为空,请尝试重新登录.");
        }
        try {
            Date date = new Date();
            sampleOrderManageDO.setCreateTime(date);
            sampleOrderManageDO.setUpdateTime(date);
            sampleOrderManageMapper.insert(sampleOrderManageDO);
        } catch (Exception e) {
            throw new RuntimeException(sampleOrderManageDO.getOrderNo()+"导入失败."+e.getMessage());
        }
        return ResultVo.success(sampleOrderManageDO.getOrderNo()+"导入"+sampleOrderManageDO.getImpQty()+"成功.");
    }

    @Override
    public ResultVo<String> editSampleOrderManage(SampleOrderManageDO sampleOrderManageDO) {
        if (sampleOrderManageDO == null || sampleOrderManageDO.getId() == null) {
            return ResultVo.failure("请选择所需编辑的数据.");
        }
        if (sampleOrderManageDO.getImpQty() == 0) {
            return ResultVo.failure("结余数量不可等于0");
        }
        if (StringUtils.isBlank(sampleOrderManageDO.getUpdateUser())) {
            return ResultVo.failure("操作人为空.请尝试重新登录.");
        }
        int update = sampleOrderManageMapper.updateById(sampleOrderManageDO);
        if (update == 1) {
            return ResultVo.success("编辑成功");
        }
        return ResultVo.failure("编辑失败.");
    }

    @Override
    public void downLoadHistorySampleOrderManageExcel() {
        String path = "template/"+Constants.ZLZS_IMP_HISTORYDATA;
        ExcelUtil excel = new ExcelUtil(path);
        excel.writeToResponse(response, Constants.ZLZS_IMP_HISTORYDATA);
    }

    /**
     * 导出全部所的展览展示品结余数据
     */
    public Workbook exportAllZlzsSampleOrderManage(List<SampleOrderManageVO> list, String deptName,String deptNo,String inDateEnd) {

        if (org.apache.commons.collections4.CollectionUtils.isEmpty(list)) {
            return null;
        }
        String path = "template/样品管理-展览展示管理.xlsx";
        ExcelHelper excel = new ExcelHelper(path);
        excel.openSheet(0);
        // 向模板中写入数据
        int row = 4;
        int count = 0;
        int cel;
        String title = deptName + "展览展示品盘点票";
        String endTime = "数据截止日: "+ inDateEnd;
        String nowDate = "盘点时间: ";
        excel.setCellValue(0,0,title);
        excel.setCellValue(2,1,endTime);
        excel.setCellValue(2,6,nowDate);

        for (SampleOrderManageVO item : list) {
            cel = 0;
            count++;
            excel.setCellValue(row,cel++,count); // 序号
            excel.setCellValue(row,cel++,item.getModelNo()); // 型号
            excel.setCellValue(row,cel++,item.getOrderNo()); // 订单号
            excel.setCellValue(row,cel++,item.getImpQty()); // 账本数量
            excel.setCellValue(row,cel++,""); // 营业所实际数量
            excel.setCellValue(row,cel++,""); // 差异说明
            excel.setCellValue(row,cel,item.getRemark()); // 备注
            row++;
        }
        // sendEamilWithDept(deptNo, title, excel, true);
//        String downloadName = "样品管理-展览展示管理.zip";
//        response.setContentType("application/zip; charset=UTF-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + downloadName);
//        try {
//            Workbook workbook = excel.getWorkBook();
//            // ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
//            //多个文件从这里就可遍历了
//            // --start
//            ZipEntry entry = new ZipEntry(deptNo+"展览展示品盘点票.xlsx");
//            zipOutputStream.putNextEntry(entry);
//            ByteOutputStream byteOutputStream = new ByteOutputStream();
//            workbook.write(byteOutputStream);
//            byteOutputStream.writeTo(zipOutputStream);
//            byteOutputStream.close();
//            zipOutputStream.closeEntry();
//            // --end
//            // zipOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return excel.getWorkBook();
    }


    /**
     * 发送邮件展览展示盘点票给营业所
     * pdf格式
     */
    public ResultVo<String> sendPdfEamilWithDept(String deptNo,String title,InputStream inputStream){
        String backupEmail = "renyan@smc.com.cn;smccnorder@smc.com.cn";
        // 抄送邮箱
        String CsEmailAddr = "smccnorder@smc.com.cn;"+backupEmail;
        String toEamilAddr = "";
        String fileName = deptNo+title+".pdf";
        Map<String, InputStream> attachment = new LinkedHashMap<>(2);
        attachment.put(fileName, inputStream); // 附件
        if (StringUtils.isBlank(deptNo)) {
            toEamilAddr = backupEmail;
            CsEmailAddr = null;
        } else {
            if ("allData".equals(deptNo)) {
//                Map<String, InputStream> attachment = new LinkedHashMap<>(2);
//                attachment.put(fileName, inputStream); // 附件
                EmailUtil.send(mailSender,backupEmail , null,null,title,
                        "所有营业所和非营业所的盘点票,详情请查看附件,本邮件由系统自动发送，请勿直接回复本邮件",attachment);
                return ResultVo.success("发送成功");
            }

            // 根据部门编码获取部门信息
            ResultVo<DepartmentVO> departmentInfo = opsCommonService.getDepartmentInfo(deptNo);
            String subject = "";
            if (departmentInfo.isSuccess()) {
                DepartmentVO data = departmentInfo.getData();
                if (data == null || StringUtils.isBlank(data.getEmailAddr())) {
                    subject = "未能根据"+deptNo+"获取营业所邮箱地址,发送展览展示盘点票邮件失败";
                    EmailUtil.send(mailSender,backupEmail , null,null,subject,
                            "未能获取"+deptNo+"营业所邮箱地址,请进行邮箱地址维护,发送展览展示盘点票邮件失败,本邮件由系统自动发送，请勿直接回复本邮件",attachment);
                    return ResultVo.failure(subject);
                }
                toEamilAddr = data.getEmailAddr();
            } else {
                subject = "未能根据"+deptNo+"获取营业所邮箱地址,发送展览展示盘点票邮件失败";
                EmailUtil.send(mailSender,backupEmail , null,null,subject,
                        "未能获取"+deptNo+"营业所邮箱地址,请进行邮箱地址维护,发送展览展示盘点票邮件失败,本邮件由系统自动发送，请勿直接回复本邮件",attachment);
                return ResultVo.failure(subject);
            }
        }
//        Map<String, InputStream> attachment = new LinkedHashMap<>(2);
//        attachment.put(fileName, inputStream); // 附件

        if (StringUtils.isBlank(toEamilAddr)) {
            toEamilAddr = backupEmail;
        }

        String pathFile = filePath + File.separator + "sampleOrderManage"+ File.separator +DateUtil.getYearMonthDay(new Date());
        Boolean aBoolean = FileUtil.uploadFileWithStream(inputStream, pathFile, fileName);
        if (aBoolean) {
            OpsMailDO opsMailDO = new OpsMailDO();
            opsMailDO.setMailTo(toEamilAddr.replaceAll(";", ","));
            opsMailDO.setSubject(title);
            opsMailDO.setContext("详情请查看附件[" + title + "],本邮件由系统自动发送，请勿直接回复本邮件");
            opsMailDO.setSendDate(new Date());
            if (StringUtils.isNotBlank(CsEmailAddr)) {
                opsMailDO.setCc(CsEmailAddr.replaceAll(";",","));
            }
            opsMailDO.setBcc(null);
            opsMailDO.setStatus(SendStatusEnum.INIT.getType());
            opsMailDO.setFileUrls(pathFile+ File.separator+fileName);
            opsMailDO.setInsertTime(new Date());
            opsCommonService.insertOpsMail(opsMailDO);
        }
//        EmailUtil.send(mailSender,toEamilAddr , CsEmailAddr,null, title,
//                "详情请查看附件[" + title + "],本邮件由系统自动发送，请勿直接回复本邮件", attachment);

        return ResultVo.success(deptNo+"邮件发送完毕.");
    }


    // 记录操作日志
    public void insertOptLog(SampleOrderManageDO vo,String desc,String optUserNo) {

        LoginUserDTO loginAuthDto = new LoginUserDTO();
        if (StringUtils.isBlank(optUserNo)) {
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserName("未知");
            }
        } else {
            loginAuthDto.setUserName(optUserNo);
        }

        OrderLogVO orderLogVO = new OrderLogVO();
        orderLogVO.setOrderNo(vo.getOrderNo());
        orderLogVO.setOptType(9);
        orderLogVO.setOptTime(new Date());
        orderLogVO.setCreateTime(new Date());
        orderLogVO.setDescription(desc);
        orderLogVO.setOptUserName(loginAuthDto.getUserName());
        orderLogFeignApi.addOrderLog(orderLogVO);
    }

}
