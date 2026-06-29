package com.smc.smccloud.service;

import com.smc.smccloud.model.prestock.ProductBomDetailDO;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-01-11 11:09
 * Description:
 */
public interface ProductBomService {

    /**
     * 根据型号查出该型号的可拆分型号信息
     *
     * @param modelNo 型号
     * @return List
     */
    List<ProductBomDetailDO> findSplittableDetailByModelNo(String modelNo);

    /**
     * 根据型号查出该型号的先行在库可拆分型号信息
     *
     * @param modelNo 型号
     * @return List
     */
    List<ProductBomDetailDO> listPreStockSplitDetailByModelNo(String modelNo);

    /**
     * bin是否可拆分
     */
    Boolean isCanSplit(String modelNo);

    /**
     * 先行在库是否可拆分
     *
     * @param modelNo 型号
     * @return boolean
     */
    Boolean isPreStockCanSplit(String modelNo);
}
