package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.ordermodify.PurchaseModifyVO;
import com.smc.smccloud.model.purchase.PurchaseModifyApproveVo;
import com.smc.smccloud.model.purchase.PurchaseModifyDO;
import com.smc.smccloud.model.purchase.PurchaseModifyRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2023-10-24
 */
public interface PurchaseModifyApplyService extends IService<PurchaseModifyDO> {

    ResultVo<PageInfo<PurchaseModifyDO>> findAll(PurchaseModifyRequest purchaseModifyRequest, Page page);



    /**
     * 1.类别为：变更运输方式时，点击处理弹出选择框，输入回复内容，变更处理状态为 完成
     * 初始进入表中，为待处理的状态
     * 2. 暂不处理：稍后还可以重新处理
     * 3. 不可变更：意思为否决 或者 处理失败， 不可以重新再处理
     */

    /**
     * 变更运输方式时，点击处理弹出选择框，输入回复内容，变更处理状态为 完成
     * @param info
     * @return
     */
    ResultVo<String> transTypeDeal(PurchaseModifyApproveVo info);

    /**
     * 供应商，指定出荷日的处理
     * @param info
     * @return
     */
    ResultVo<String> suppilyDateDeal(PurchaseModifyApproveVo info);

    /**
     * 采购删单的操作，采购单的状态的校验，需要提前做
     */
    ResultVo<String> deleteDeal(PurchaseModifyApproveVo info);

    /**
     * 否决和暂不处理操作
     * @param info
     * @return
     */
    ResultVo<String> layasideData(PurchaseModifyApproveVo info);


    /**
     * 批量导入数据
     * @param file
     * @param loginUser
     * @return
     * @throws Exception
     */
    ResultVo<String> importFile(MultipartFile file, String loginUser) throws Exception;


    /**
     * 定时执行 待处理的清单
     */
    ResultVo<String> handlePurchaseModify() throws Exception;


    ResultVo<String> insertPurchaseModify(PurchaseModifyVO vo);
}
