package com.sales.ops.api.service.impl.wms;

import com.sales.ops.api.conf.opsApiConfig;
import com.sales.ops.api.dto.wms.OpsDoConfirmDto;
import com.sales.ops.api.dto.wms.OpsDoItemConfirmDto;
import com.sales.ops.api.dto.wms.OpsRoConfirmDto;
import com.sales.ops.api.service.wms.OpsRecApiFromWmsService;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.ExpdetailBarcodeMapper;
import com.sales.ops.db.dao.OpsCoordinateMapper;
import com.sales.ops.db.dao.OpsExceptionHandleMapper;
import com.sales.ops.db.entity.ExpdetailBarcode;
import com.sales.ops.db.entity.ExpdetailBarcodeExample;
import com.sales.ops.db.entity.OpsCoordinate;
import com.sales.ops.db.entity.OpsExceptionHandle;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.DoConfirmErrorCodeEnum;
import com.sales.ops.feign.OpsWmFeignApi;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops接收wms数据
 * @date ：Created in 2021/10/27 16:54
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OpsRecApiFromWmsServiceImpl implements OpsRecApiFromWmsService {

    @Autowired
    private OpsWmFeignApi opsWmFeignApi;
    @Autowired
    private opsApiConfig opsApiConfig;

    @Autowired
    private OpsCoordinateMapper opsCoordinateMapper;

    @Autowired
    private ExpdetailBarcodeMapper expdetailBarcodeMapper;

    @Autowired
    private OpsExceptionHandleMapper opsExceptionHandleMapper;

    @Override
    public CommonResult doConfirm(WmDoConfirmDto wmDoConfirmDto) throws OpsException {

        //组装userDto
        UserDto userDto = new UserDto();
        userDto.setUserName("wmUser");
        userDto.setIp(opsApiConfig.getWmsApi());
        wmDoConfirmDto.setUserDto(userDto);

        //提交数据到OPS
        return opsWmFeignApi.wmDoConfirm(wmDoConfirmDto);//出库单回传接口
    }

    @Override
    public CommonResult roConfirm(WmRoConfirmDto wmRoConfirmDto) throws OpsException {
        UserDto userDto = new UserDto();
        userDto.setUserName("wmUser");
        userDto.setIp(opsApiConfig.getWmsApi());
        wmRoConfirmDto.setUserDto(userDto);
        //提交数据到OPS
//        return opsWmFeignApi.wmRoConfirm(wmRoConfirmDto);
//        return opsWmFeignApi.wmRoConfirmNew(wmRoConfirmDto);
        //begin 2022-12-01 bug 8695
        return opsWmFeignApi.wmRoConfirmHandle(wmRoConfirmDto);
        //end

    }

    @Override
    public CommonResult wmPCOConfirm(WmPCOConfirmDto param) throws OpsException {
        //组装userDto
        UserDto userDto = new UserDto();
        userDto.setUserName("wmUser");
        userDto.setIp(opsApiConfig.getWmsApi());
        param.setUserDto(userDto);
        //提交数据到OPS
        return opsWmFeignApi.wmPCOConfirm(param);
    }

    @Override
    public CommonResult wmDoStatus(WmDoStatusDto param) throws OpsException {
        //组装userDto
        UserDto userDto = new UserDto();
        userDto.setUserName("wmUser");
        userDto.setIp(opsApiConfig.getWmsApi());
        param.setUserDto(userDto);
        //提交数据到OPS
        return  opsWmFeignApi.wmDoStatus(param);
    }

    /**
     * 记录wms日志
     * @param opsCoordinate
     */
    @Override
    public void savaLog(OpsCoordinate opsCoordinate){
        // 保存系统日志
        opsCoordinateMapper.insertSelective(opsCoordinate);
    }


    /**
     * 出库箱码明细
     * @param param
     */
    @Override
    public void saveDoBarcode(WmDoBarcodeDto param){
        //存箱码明细 给固定流水号 分别存expdetial 和 expdetail_barcode 唯一流水号
        if(Objects.nonNull(param) && CollectionUtils.isNotEmpty(param.getBarCode())){
            for(WmDoBarcodeItemDto wmDoBarcodeItemDto : param.getBarCode()){
                //验证重复 bugid:9202 c14717 20221230
                ExpdetailBarcodeExample example = new ExpdetailBarcodeExample();
                ExpdetailBarcodeExample.Criteria criteria = example.createCriteria();
                criteria.andDoIdEqualTo(wmDoBarcodeItemDto.getDeliveryOrderCode());//doid
                criteria.andExpdetailIdEqualTo(wmDoBarcodeItemDto.getWmsOrderCode());////托码
                criteria.andBarcodeEqualTo(wmDoBarcodeItemDto.getBarcode());//箱码
                Long repeatVerification = expdetailBarcodeMapper.countByExample(example);
                if(repeatVerification.equals(0L)){//存表
                    //存表
                    ExpdetailBarcode expdetailBarcode = new ExpdetailBarcode();
                    expdetailBarcode.setCreateTime(new Date());
                    expdetailBarcode.setCreateUser("wms");
                    expdetailBarcode.setCustomerNo(param.getConsigneeId());
                    expdetailBarcode.setBarcode(wmDoBarcodeItemDto.getBarcode());//箱码
                    expdetailBarcode.setQuantity(wmDoBarcodeItemDto.getQty());
                    expdetailBarcode.setDoId(wmDoBarcodeItemDto.getDeliveryOrderCode());//doid
                    expdetailBarcode.setExpdetailId(wmDoBarcodeItemDto.getWmsOrderCode());//托码
                    //bugid 8462 c14717 20221230 冷干机序列号
                    expdetailBarcode.setColdDryerNo(wmDoBarcodeItemDto.getColdDryerNo());
                    expdetailBarcodeMapper.insertSelective(expdetailBarcode);
                }else{//重复存在记录异常表
                    OpsExceptionHandle opsExceptionHandle = new OpsExceptionHandle();
                    opsExceptionHandle.setBusinessType("Do");
                    opsExceptionHandle.setErrType(DoConfirmErrorCodeEnum.REPEAT_CALL.getCode());//重复调用
                    opsExceptionHandle.setApiName("5.14出库箱码明细回传接口"); //接口名字
                    opsExceptionHandle.setStatus(9);//状态（0：待处理，1：已处理，9：无需处理）
                    opsExceptionHandle.setOutputMsg(param.toString());//返回报文
                    opsExceptionHandle.setParameter1(wmDoBarcodeItemDto.getDeliveryOrderCode());
                    opsExceptionHandle.setParameter3(wmDoBarcodeItemDto.getQty() + "");
                    opsExceptionHandle.setCreateTime(new Date());
                    opsExceptionHandle.setCreateUser("saveDoBarcode");
                    opsExceptionHandleMapper.insertSelective(opsExceptionHandle);
                }
            }
        }
    }

}
