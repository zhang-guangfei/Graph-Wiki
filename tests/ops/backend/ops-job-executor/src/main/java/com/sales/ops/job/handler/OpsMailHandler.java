package com.sales.ops.job.handler;

import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.ResultCode;
import com.sales.ops.enums.SendStatusEnum;
import com.sales.ops.feign.OpsMailApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops邮件任务
 * @date ：Created in 2021/11/4 13:38
 */
@Component
public class OpsMailHandler {
    private static Logger logger = LoggerFactory.getLogger(OpsMailHandler.class);

    @Autowired
    private OpsMailApi opsMailApi;

    //定时发送邮件
    @XxlJob("jobSendMail")
    public void jobSendMail() throws Exception{
        XxlJobHelper.log("定时发送邮件----------begin");
        CommonResult<List<OpsMail>> commonResult =opsMailApi.selectMailByDb();
        if (ResultCode.SUCCESS.code().equals(commonResult.getCode())) {//有需要更新的数据
            List<OpsMail> list = commonResult.getData();
            if (list.isEmpty()) {
                XxlJobHelper.log("无可执行数据");
                return;
            }
            for (OpsMail opsMail: list){
                try {
                    CommonResult sendReust = opsMailApi.jobSendMail(opsMail);
                    if (ResultCode.SUCCESS.code().equals(sendReust.getCode())){
                        opsMail.setStatus(SendStatusEnum.SUCCESS.getType());//成功
                        opsMail.setSendDate(new Date());
                    }else{
                        opsMail.setStatus(SendStatusEnum.FAIR.getType());//失败
                        opsMail.setSendDate(new Date());
                        opsMail.setErrorMsg(sendReust.getMessage());
                    }
                    opsMailApi.updateMailDb(opsMail);
                } catch (Exception e) {
                    //更新表失败;
                    opsMail.setStatus(SendStatusEnum.FAIR.getType());//发送失败
                    opsMail.setSendDate(new Date());
                    opsMail.setErrorMsg(e.getMessage());
                    opsMailApi.updateMailDb(opsMail);
                    XxlJobHelper.handleFail(e.getMessage());
                }
            }
        }else{
            XxlJobHelper.log(commonResult.getMessage());
        }
    }
}
