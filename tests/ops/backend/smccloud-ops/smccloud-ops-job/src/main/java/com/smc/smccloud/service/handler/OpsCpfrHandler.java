package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.binorder.BinOrderCalcRequestVO;
import com.smc.smccloud.model.binorder.BinOrderCalcVO;
import com.smc.smccloud.service.BinServiceFeignApi;
import com.smc.smccloud.service.BinorderServiceFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author edp02 @Date 2021/12/24 14:20
 */
@Slf4j
@Component
public class OpsCpfrHandler {
    @Resource
    private BinorderServiceFeignApi binorderServiceFeignApi;

    @Resource
    private BinServiceFeignApi binServiceFeignApi;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    /**
     * 自动计算型号订货频率
     */
    @XxlJob(value = "calcmodelExpFreq", init = "init", destroy = "destroy")
    public void calcmodelExpFreq() throws Exception {
        try {
            XxlJobHelper.log("==> 自动计算型号订货频率。");
            String param = XxlJobHelper.getJobParam();
            int type;
            if (StringUtils.isEmpty(param)) {
                type = 2;
            } else {
                type = Integer.parseInt(param);
            }
            XxlJobHelper.log("==> 计算参数：" + type);
            ResultVo<String> resultVo = binorderServiceFeignApi.calcmodelExpFreq(type);
            if (resultVo.isSuccess()) {
                String msg = "";
                if (StringUtils.isNotBlank(resultVo.getData())) {
                    msg = resultVo.getData();
                }
                XxlJobHelper.log("自动计算型号订货频率成功！" + msg);
                XxlJobHelper.handleSuccess("自动计算型号订货频率成功！" + msg);
            } else {
                XxlJobHelper.log("自动计算型号订货频率失败" + resultVo.getMessage());
                XxlJobHelper.handleFail("自动计算型号订货频率失败");
            }
        } catch (Exception ex) {
            XxlJobHelper.log("自动计算型号订货频率失败：" + ex.getMessage());
            XxlJobHelper.handleFail("自动计算型号订货频率失败。");
            log.error(" XxlJob：calcmodelExpFreq 执行失败：", ex);
        }
    }

    /**
     * 自动更新产品信息
     */
    @XxlJob(value = "updateProductInfo", init = "init", destroy = "destroy")
    public void updateProductInfo() throws Exception {
        try {
            XxlJobHelper.log("==> 进入定期自动更新产品信息");
            binServiceFeignApi.updateProductInfo();

        } catch (Exception ex) {
            XxlJobHelper.log("自动更新产品信息失败：" + ex.getMessage());
            XxlJobHelper.handleFail("自动更新产品信息失败。");
            log.error(" XxlJob：updateProductInfo 执行失败：", ex);
        }
    }

    /**
     * 自定义计算Bin数据
     */
    @XxlJob(value = "runBinTrialJob", init = "init", destroy = "destroy")
    public void runBinTrialJob() throws Exception {
        try {
            XxlJobHelper.log("==> 自定义计算Bin数据");
            ResultVo<String> resultVo = binorderServiceFeignApi.runBinTrialJob();
            if (resultVo.isSuccess()) {
                XxlJobHelper.log("自定义计算Bin数据成功：" + resultVo.getData());
                XxlJobHelper.handleSuccess("自定义计算Bin数据成功：" + resultVo.getData());
            } else {
                XxlJobHelper.log("自定义计算Bin数据失败" + resultVo.getMessage());
                XxlJobHelper.handleFail("自定义计算Bin数据失败");
            }
        } catch (Exception ex) {
            XxlJobHelper.log("自定义计算Bin数据失败：" + ex.getMessage());
            XxlJobHelper.handleFail("自定义计算Bin数据失败");
            log.error(" XxlJob：runBinTrialJob 执行失败：", ex); //    <!--add by WuWeiDong 20221121   -->
        }

    }
    @Resource
    private RedisManager redisUtil;
    String REDIS_KEY_BIN_CALC_ING="ops:cpfr:binord:ing";

    @XxlJob(value = "runBindetaCalculateJob", init = "init", destroy = "destroy")
    public void runBindetaCalculateJob() throws Exception {
        String param = XxlJobHelper.getJobParam();
        List<String> warehouseList = new ArrayList<>();
        try {

            if (StringUtils.isNotBlank(param)) {
                if (param.contains(",")) {
                    String[] split = param.split(",");
                    warehouseList = Arrays.asList(split);
                } else {
                    warehouseList.add(param);
                }
            } else {
                warehouseList = Arrays.asList("KBJ", "KSH", "KGZ", "SCZ");
            }
            //开始计算
            for (String warehouse : warehouseList) {
                try {
                    //先结束掉未完成的计算
                    ResultVo<List<BinOrderCalcVO>> resultVo = binorderServiceFeignApi.findBinOrderCalcByWarehouseCode(warehouse);
                    if (resultVo.isSuccess()) {
                        List<BinOrderCalcVO> data = resultVo.getData();
                        if (CollectionUtils.isNotEmpty(data)) {
                            for (BinOrderCalcVO datum : data) {
                                XxlJobHelper.log("结束未完成计算：" + warehouse + "，计算ID：" + datum.getId());
                                binorderServiceFeignApi.finishbinordercalc(datum.getId());
                            }
                        }
                    }
                    //初始化新的计算
                    BinOrderCalcRequestVO vo = new BinOrderCalcRequestVO();
                    vo.setWarehouseCode(warehouse);
                    vo.setCalcType(1);
                    vo.setOnlyCaculate(false);
                    vo.setOptUser("job");
                    ResultVo<BinOrderCalcVO> result = binorderServiceFeignApi.newBinOrderCalcId(vo);
                    if (result.isSuccess()) {
                        BinOrderCalcVO data = result.getData();
                        if (data.getId() != null) {
                            vo.setCalcId(data.getId());
                            XxlJobHelper.log("开始计算bin补库数据：" + warehouse);
                            ResultVo<BinOrderCalcVO> binOrderCalcVOResultVo = binorderServiceFeignApi.calcBinOrder(vo);
                            if (binOrderCalcVOResultVo.isSuccess()) {
                                XxlJobHelper.log("bin补库数据计算成功：" + warehouse);
                            } else {
                                XxlJobHelper.log("bin补库数据计算失败：" + warehouse + "，失败原因：" + binOrderCalcVOResultVo.getMessage());
                            }
                        }
                    }
                } catch (Exception ex) {
                    XxlJobHelper.log("自动计算bin补库数据失败：" + warehouse + "\n" + ex.getMessage());
                    XxlJobHelper.handleFail("自动计算bin补库数据失败");
                }
            }
        } catch (Exception ex) {
            XxlJobHelper.log("自动计算bin补库数据失败：" + ex.getMessage());
            XxlJobHelper.handleFail("自动计算bin补库数据失败");
        }
    }
}
