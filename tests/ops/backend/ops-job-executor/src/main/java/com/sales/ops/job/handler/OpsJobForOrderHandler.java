package com.sales.ops.job.handler;

import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.Rcvmaster;
import com.sales.ops.dto.inventory.DispatchForOrderItemInputDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.ResultCode;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.job.util.CommonUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author C02483
 * @version 1.0
 * @description: 为订单处理创建
 * @date 2021/10/26 19:04
 */
@Component
public class OpsJobForOrderHandler {
    private static Logger logger = LoggerFactory.getLogger(OpsJobForOrderHandler.class);
    @Autowired
    private OpsWmDispatchForOrderFeignApi forOrderFeignApi;

    //处理偶数订单
    @XxlJob("CreateCustOrderByEvenNumber")
    public void CreateCustOrderByEvenNumber() throws Exception {

        try {
            String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)){
                limit = Integer.parseInt(param);
            }
            CommonResult<List<Rcvdetail>> commonResult = forOrderFeignApi.getcustdetailByEvenNumber(limit);
            if (commonResult.isSuccess()) {
                List<Rcvdetail> list = commonResult.getData();
                if (list.isEmpty()) {
                    XxlJobHelper.log("无可执行数据");
                    return;
                }
                for (Rcvdetail rcvdetail : list) {
                    long startTime = System.currentTimeMillis();
                    CommonResult<Rcvmaster> rcvmasterCommonResult = forOrderFeignApi.getcustOrder(rcvdetail.getRorderNo());
                    if (Objects.isNull(rcvmasterCommonResult.getData())) {
                        continue;
                    }
                    if (rcvmasterCommonResult.isSuccess()) {
                        DispatchForOrderItemInputDto inputDto = new DispatchForOrderItemInputDto(rcvmasterCommonResult.getData(), rcvdetail);
                        try {
                            CommonResult<String> cr =  forOrderFeignApi.dispatchForcustOrder(inputDto);
                            XxlJobHelper.log(cr.getMessage());
                        } catch (Exception e) {
                            XxlJobHelper.log(e.getMessage());
                        }
                    } else {
                        XxlJobHelper.log(rcvmasterCommonResult.getMessage());
                        logger.error(rcvmasterCommonResult.getMessage());
                    }
                    long cost = System.currentTimeMillis()-startTime;
                    XxlJobHelper.log("花费时间："+cost);
                }
            } else {
                XxlJobHelper.log(commonResult.getMessage());
            }

        } catch (Exception ex) {
            XxlJobHelper.log(ex.getMessage());
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    //处理奇数订单
    @XxlJob("CreateCustOrderByOddNumber")
    public void CreateCustOrderByOddNumber() throws Exception {

        try {
            String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)){
                limit = Integer.parseInt(param);
            }
            CommonResult<List<Rcvdetail>> commonResult = forOrderFeignApi.getcustdetailByOddNumber(limit);
            if (commonResult.isSuccess()) {
                List<Rcvdetail> list = commonResult.getData();
                if (list.isEmpty()) {
                    XxlJobHelper.log("无可执行数据");
                    return;
                }
                for (Rcvdetail rcvdetail : list) {
                    long startTime = System.currentTimeMillis();
                    CommonResult<Rcvmaster> rcvmasterCommonResult = forOrderFeignApi.getcustOrder(rcvdetail.getRorderNo());
                    if (Objects.isNull(rcvmasterCommonResult.getData())) {
                        continue;
                    }
                    if (rcvmasterCommonResult.isSuccess()) {
                        DispatchForOrderItemInputDto inputDto = new DispatchForOrderItemInputDto(rcvmasterCommonResult.getData(), rcvdetail);
                        try {
                            CommonResult<String> cr =  forOrderFeignApi.dispatchForcustOrder(inputDto);
                            XxlJobHelper.log(cr.getMessage());
                        } catch (Exception e) {
                            XxlJobHelper.log(e.getMessage());
                        }
                    } else {
                        XxlJobHelper.log(rcvmasterCommonResult.getMessage());
                        logger.error(rcvmasterCommonResult.getMessage());
                    }
                    long cost = System.currentTimeMillis()-startTime;
                    XxlJobHelper.log("花费时间："+cost);
                }
            } else {
                XxlJobHelper.log(commonResult.getMessage());
            }

        } catch (Exception ex) {
            XxlJobHelper.log(ex.getMessage());
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    //逐条处理订单
    @XxlJob("CreateCustOrder")
    public void CreateCustOrder() throws Exception {

        try {
            String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)){
                limit = Integer.parseInt(param);
            }
            CommonResult<List<Rcvdetail>> commonResult = forOrderFeignApi.getcustdetail(limit);
            if (commonResult.isSuccess()) {
                List<Rcvdetail> list = commonResult.getData();
                if (list.isEmpty()) {
                    XxlJobHelper.log("无可执行数据");
                    return;
                }
                for (Rcvdetail rcvdetail : list) {
                    long startTime = System.currentTimeMillis();
                    CommonResult<Rcvmaster> rcvmasterCommonResult = forOrderFeignApi.getcustOrder(rcvdetail.getRorderNo());
                    if (Objects.isNull(rcvmasterCommonResult.getData())) {
                        continue;
                    }
                    if (rcvmasterCommonResult.isSuccess()) {
                        DispatchForOrderItemInputDto inputDto = new DispatchForOrderItemInputDto(rcvmasterCommonResult.getData(), rcvdetail);
                        try {
                            CommonResult<String> cr =  forOrderFeignApi.dispatchForcustOrder(inputDto);
                            XxlJobHelper.log(cr.getMessage());
                        } catch (Exception e) {
                            XxlJobHelper.log(e.getMessage());
                        }
                    } else {
                        XxlJobHelper.log(rcvmasterCommonResult.getMessage());
                        logger.error(rcvmasterCommonResult.getMessage());
                    }
                    long cost = System.currentTimeMillis()-startTime;
                    XxlJobHelper.log("花费时间："+cost);
                }
            } else {
                XxlJobHelper.log(commonResult.getMessage());
            }

        } catch (Exception ex) {
            XxlJobHelper.log(ex.getMessage());
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    /**
     * bugid:10618 20230808 c14717  订单自动处理增加补偿程序
     * @throws Exception
     */
    @XxlJob("CreateCustOrderByMonth")
    public void CreateCustOrderByMonth() throws Exception {

        try {
            String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
            int limit = 100;
            int beforeMonth = 12;
            if (param != null && param.trim().length() != 0){
                String[] pa = param.split(",");
                if(StringUtils.isNotBlank(pa[0]) && CommonUtil.isInteger(pa[0])){
                    limit = Integer.parseInt(pa[0]);
                }
                if(StringUtils.isNotBlank(pa[1]) && CommonUtil.isInteger(pa[1])){
                    beforeMonth = Integer.parseInt(pa[1]);
                }
            }
            XxlJobHelper.log("limit:"+limit+"; beforeMonth:"+beforeMonth);
            CommonResult<List<Rcvdetail>> commonResult = forOrderFeignApi.getRcvdetailListByBeforeMoth(limit,beforeMonth);
            if (commonResult.isSuccess()) {
                List<Rcvdetail> list = commonResult.getData();
                if (list.isEmpty()) {
                    XxlJobHelper.log("无可执行数据");
                    return;
                }
                for (Rcvdetail rcvdetail : list) {
                    long startTime = System.currentTimeMillis();
                    CommonResult<Rcvmaster> rcvmasterCommonResult = forOrderFeignApi.getcustOrder(rcvdetail.getRorderNo());
                    if (Objects.isNull(rcvmasterCommonResult.getData())) {
                        continue;
                    }
                    if (rcvmasterCommonResult.isSuccess()) {
                        DispatchForOrderItemInputDto inputDto = new DispatchForOrderItemInputDto(rcvmasterCommonResult.getData(), rcvdetail);
                        try {
                            CommonResult<String> cr =  forOrderFeignApi.dispatchForcustOrder(inputDto);
                            XxlJobHelper.log(cr.getMessage());
                        } catch (Exception e) {
                            XxlJobHelper.log(e.getMessage());
                        }
                    } else {
                        XxlJobHelper.log(rcvmasterCommonResult.getMessage());
                        logger.error(rcvmasterCommonResult.getMessage());
                    }
                    long cost = System.currentTimeMillis()-startTime;
                    XxlJobHelper.log("花费时间："+cost);
                }
            } else {
                XxlJobHelper.log(commonResult.getMessage());
            }

        } catch (Exception ex) {
            XxlJobHelper.log(ex.getMessage());
            logger.error(ex.getMessage());
            throw ex;
        }
    }

}
