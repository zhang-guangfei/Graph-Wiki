package com.sales.ops.job.handler.eta;

import com.sales.ops.feign.eta.EtaFeign;
import com.sales.ops.job.util.CommonUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/12/5 13:48
 */
@Component
public class EtaHandler {

    @Autowired
    private EtaFeign etaFeign;


    /**
     * 定时任务存同步通用库存 bugid:18776 c14717 20240826
     */
    @XxlJob("jobSynTyInv")
    public void jobSynTyInv(){
        ResultVo<String> resultVo = etaFeign.jobSynTyInv();
        XxlJobHelper.log("执行："+resultVo.isSuccess());
        XxlJobHelper.log("执行："+resultVo.getMessage());
    }


    /**
     * 保存库存到缓存
     */
    @XxlJob("saveInvCache")
    public void saveInvCache(){
        String cost = etaFeign.saveInvCache();
        XxlJobHelper.log("执行："+cost);
    }

    /**
     * 清理每日变更数据
     */
    @XxlJob("cleanEveryDayCache")
    public void cleanEveryDayCache(){
        String cost = etaFeign.cleanEveryDayCache();
        XxlJobHelper.log("执行："+cost);
    }

    /**
     * 清理全部缓存
     */
    @XxlJob("cleanAllMapCache")
    public void cleanAllMapCache(){
        String cost = etaFeign.cleanAllCache();
        XxlJobHelper.log("执行："+cost);
    }

    /**
     * 执行计算A
     */
    @XxlJob("etaDoDataA")
    public void etaDoDataA(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        Long limit = 100l;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Long.parseLong(param);
        }
        String cost = etaFeign.doData(limit, "A");
        XxlJobHelper.log("执行完成耗时："+cost);
    }

    /**
     * 执行计算B
     */
    @XxlJob("etaDoDataB")
    public void etaDoDataB(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        Long limit = 100l;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Long.parseLong(param);
        }
        String cost = etaFeign.doData(limit, "B");
        XxlJobHelper.log("执行完成耗时："+cost);
    }

    /**
     * 执行计算C
     */
    @XxlJob("etaDoDataC")
    public void etaDoDataC(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        Long limit = 100l;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Long.parseLong(param);
        }
        String cost = etaFeign.doData(limit, "C");
        XxlJobHelper.log("执行完成耗时："+cost);
    }

    /**
     * 执行计算D
     */
    @XxlJob("etaDoDataD")
    public void etaDoDataD(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        Long limit = 100l;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Long.parseLong(param);
        }
        String cost = etaFeign.doData(limit, "D");
        XxlJobHelper.log("执行完成耗时："+cost);
    }

    /**
     * 执行计算E
     */
    @XxlJob("etaDoDataE")
    public void etaDoDataE(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        Long limit = 100l;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Long.parseLong(param);
        }
        String cost = etaFeign.doData(limit, "E");
        XxlJobHelper.log("执行完成耗时："+cost);
    }

    /**
     * 执行计算F
     */
    @XxlJob("etaDoDataF")
    public void etaDoDataF(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        Long limit = 100l;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Long.parseLong(param);
        }
        String cost = etaFeign.doData(limit, "F");
        XxlJobHelper.log("执行完成耗时："+cost);
    }

    /**
     * 执行计算G
     */
    @XxlJob("etaDoDataG")
    public void etaDoDataG(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        Long limit = 100l;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Long.parseLong(param);
        }
        String cost = etaFeign.doData(limit, "G");
        XxlJobHelper.log("执行完成耗时："+cost);
    }

    /**
     * 执行计算H
     */
    @XxlJob("etaDoDataH")
    public void etaDoDataH(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        Long limit = 100l;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Long.parseLong(param);
        }
        String cost = etaFeign.doData(limit, "H");
        XxlJobHelper.log("执行完成耗时："+cost);
    }

    /**
     * 执行计算I
     */
    @XxlJob("etaDoDataI")
    public void etaDoDataI(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        Long limit = 100l;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Long.parseLong(param);
        }
        String cost = etaFeign.doData(limit, "I");
        XxlJobHelper.log("执行完成耗时："+cost);
    }

    /**
     * 执行计算J
     */
    @XxlJob("etaDoDataJ")
    public void etaDoDataJ(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        Long limit = 100l;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Long.parseLong(param);
        }
        String cost = etaFeign.doData(limit, "J");
        XxlJobHelper.log("执行完成耗时："+cost);
    }

    /**
     * 执行计算通用
     */
    @XxlJob("etaDoDataParam")
    public void etaDoData(){
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        long limit = 100L;
        String tableFlag = "";
        if (param != null && param.trim().length() != 0 ) {
            String[] split = param.split(";");
            if(split.length == 2){
                if(CommonUtil.isInteger(split[0])){
                    limit = Long.parseLong(split[0]);
                }
                if(StringUtils.isNotBlank(split[1])){
                    tableFlag = split[1];
                }
                String cost = etaFeign.doData(limit, tableFlag);
                XxlJobHelper.log("执行完成耗时："+cost);
            }else {
                XxlJobHelper.log("参数不合法:param;tableFlag");
            }
        }else {
            XxlJobHelper.log("参数不合法:param;tableFlag");
        }

    }

}
