package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.preaccount.HandlePreOrderAccountParam;
import com.smc.smccloud.model.prestock.PreOrderAccountDetailDO;
import com.smc.smccloud.model.prestock.PreOrderAccountDetailDTO;
import com.smc.smccloud.model.prestock.PreOrderAccountDetailVO;
import com.smc.smccloud.model.prestock.PreOrderAccountRequest;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author lyc
 * @Date 2024/6/17 13:40
 * @Descripton TODO
 */
public interface PreOrderAccountHandService {


    /**
     * 通过库存id 库存项号 批次号更新preorder_account_detail表
     */
    ResultVo<String> updatePreAccountDetail(PreOrderAccountDetailVO info);


    /**
     * 先行在库预决算 UI 处理按钮
     * @param param
     * @return
     */
    ResultVo<String> handlePreOrderAccountByIds(HandlePreOrderAccountParam param);

    /**
     * 先行在库预决算处理(不推送门户的数据 待处理状态)
     * @param param
     * @return
     */
    ResultVo<String> handNotPushSalePreAccount(HandlePreOrderAccountParam param);

    /**
     * 生成先行在库预决算数据
     */
    ResultVo<String> crePreOrderAccountDataNew();

    /**
     * 记录决算申请明细数据
     * @param info
     * @return
     */
    ResultVo<String> insertPreOrderAccountApplyDetailData(PreOrderAccountDetailVO info);


    /**
     * 决算申请明细查询
     */
    ResultVo<PageInfo<PreOrderAccountDetailDTO>> listPreOrderApplyDetail(PreOrderAccountRequest request);


    /**
     * 导出详细申请表
     * @param request
     */
    void exportPreOrderApplyDetail(PreOrderAccountRequest request, HttpServletResponse response);


    /**
     * 提前决算
     * @param param
     * @return
     */
    ResultVo<String> advancedHand(HandlePreOrderAccountParam param);

    /**
     * 自动作业 => 延期中变延期待决算
     */
    ResultVo<String> autoHandleYqzPre();

    ResultVo<String> calPreOrderAccountQty(Long inventoryId);

    // 下载延期配置模板
    void downYQPZTemplate(HttpServletResponse response);


    void insertPreOrderAccountRecords(Long logId, int accountId,String orderNo);

}
