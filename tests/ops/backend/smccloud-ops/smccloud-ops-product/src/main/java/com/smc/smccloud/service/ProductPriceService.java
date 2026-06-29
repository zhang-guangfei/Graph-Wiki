package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.product.OrderModelInfoVO;
import com.smc.smccloud.model.product.ProductPriceDTO;
import com.smc.smccloud.model.product.ProductPriceVO;
import com.smc.smccloud.model.productPrice.ProductPriceDO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author smc
 * @since 2021-11-04
 */
public interface ProductPriceService extends IService<ProductPriceDO> {

    /**
     * 查询产品E价
     *
     * @param modelNo 型号
     * @return E价
     */
    ResultVo<String> getModelEPrice(String modelNo);

    /**
     * 批量查询
     * @param modelNos
     * @return
     */
    ResultVo<List<ProductPriceVO>> listProductPrice(List<String> modelNos);

    /**
     * 查询产品批量E价
     *
     * @param modelNo  型号
     * @param quantity 数量
     * @return 批量E价
     */
    ResultVo<String> getModelLotPrice(String modelNo, Integer quantity);

    /**
     * 查询产品信息for下单
     *
     * @param modelNo
     * @return
     */
    ResultVo<OrderModelInfoVO> getModelInfoForOrder(String modelNo);

    /**
     * 判断bin是否lot价
     */
    ResultVo<Boolean> isLotPrice(String modelNo);

}
