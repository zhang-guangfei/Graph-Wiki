package com.sales.ops.web.controller.inventory;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.sales.ops.common.conf.FileProp;
import com.sales.ops.common.easyexcel.EasyExcelUtil;
import com.sales.ops.common.easyexcel.handler.Custemhandler;
import com.sales.ops.db.dao.OpsCoordinateMapper;
import com.sales.ops.db.dao.OpsExceptionHandleMapper;
import com.sales.ops.db.entity.OpsCoordinate;
import com.sales.ops.db.entity.OpsExceptionHandle;
import com.sales.ops.db.extdao.OpsExceptionHandleDao;
import com.sales.ops.dto.ba.ChangeYYDto;
import com.sales.ops.dto.inventory.WmDoBarcodeDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.inventory.OpsExceptionHandleService;
import com.smc.smccloud.core.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sales.ops.dto.easyexcel.DoConfirmCompensateTenTimeDto;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：异常处理 及下预约
 * @date ：Created in 2022/11/21 10:36
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/excHandle")
public class OpsExcHandleController {

    @Autowired
    private OpsExceptionHandleService opsExceptionHandleService;

    @Autowired
    private OpsExceptionHandleMapper exceptionHandleMapper;

    @Autowired
    private OpsExceptionHandleDao opsExceptionHandleDao;

    @Autowired
    private OpsCoordinateMapper opsCoordinateMapper;

    @Autowired
    private FileProp fileProp;

    /**
     * bugid:10804
     * WMS 下预约调用ops接口
     * @param list
     * @return
     */
    @PostMapping("/changeYY")
    public CommonResult<List<ChangeYYDto>> changeYY(@RequestBody List<ChangeYYDto> list){
        CommonResult<List<ChangeYYDto>> returnData = null;
        //每次处理20条
        if(CollectionUtils.isNotEmpty(list)){
            int CAL_SIZE = 20;
            for (int i = 0; i < list.size(); i++) {
                if (i % CAL_SIZE == 0) {
                    List<ChangeYYDto> temp = new ArrayList<ChangeYYDto>();
                    if (i + CAL_SIZE < list.size()) {
                        temp = list.subList(i, i + CAL_SIZE);
                    } else {
                        temp = list.subList(i, list.size());
                    }
                    OpsCoordinate dbLog = new OpsCoordinate();
                    try {
                        opsExceptionHandleService.changeYY(temp);
                        dbLog.setIssuccess(true);
                    } catch (Exception e) {
                        dbLog.setIssuccess(false);
                        log.error("下预约",e);
                        //处理报错
                    }
                    //持久化日志
                    dbLog.setMessage(JSONArray.toJSONString(temp));
                    dbLog.setApiname("changeYY");
                    dbLog.setCreateTime(DateUtil.getNow());
                    opsCoordinateMapper.insertSelective(dbLog);
                }
            }
            returnData = CommonResult.success(list);
        }else {
            returnData = CommonResult.failure("无数据");
        }
        return returnData;
    }

    /**
     * 批量处理do异常数据
     * @param limit
     * @return
     */
    @GetMapping("/batchHandle")
    public CommonResult handleExceptionHandleToErrDoConfirm(@RequestParam Integer limit) {
        int sucessNums = 0;
        List<OpsExceptionHandle> excList = opsExceptionHandleDao.findExceptionHandleNew();
        List<DoConfirmCompensateTenTimeDto> excelDtoList = new ArrayList<>();
        for (OpsExceptionHandle obj : excList){
            try {
                //异常表status=1 已处理
                opsExceptionHandleService.handStatus(obj);
                //变更库存
                int flag = opsExceptionHandleService.handleExceptionHandleToErrDoConfirm(obj);
                if(flag == 1){
                    //处理成功
                    opsExceptionHandleService.handSucess(obj);
                    sucessNums = sucessNums + flag;
                }else {
                    //处理失败
                    opsExceptionHandleService.handFail(obj,excelDtoList);
                }
            } catch (Exception e) {
                //处理失败
                opsExceptionHandleService.handFail(obj,excelDtoList);
                log.error("doconfirm补偿",e);
            }
        }
        //bugid 9304 c14717 20230110
        if(CollectionUtils.isNotEmpty(excelDtoList)){
            //创建文件
            String fileName = fileProp.getBase() + "doconfirm/" + System.currentTimeMillis() + ".xlsx";
            //String fileName = "D:\\\\workSpaceC14717\\\\测试01.xlsx"; //本地地址
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            EasyExcel.write(fileName, DoConfirmCompensateTenTimeDto.class)
                    .registerWriteHandler(new Custemhandler())
                    .registerWriteHandler(EasyExcelUtil.getStyleStrategy())
                    .sheet("report")
                    .doWrite(excelDtoList);
            opsExceptionHandleService.saveMailToDb(fileName);
        }
        return CommonResult.success("处理了成功了 : "+sucessNums);
    }
}
