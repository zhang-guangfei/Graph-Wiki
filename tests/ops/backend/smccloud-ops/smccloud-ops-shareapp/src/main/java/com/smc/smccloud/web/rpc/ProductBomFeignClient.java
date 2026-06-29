package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.model.prestock.ProductBomDetailDO;
import com.smc.smccloud.model.prestock.ProductBomDetailVO;
import com.smc.smccloud.model.prestock.ProductBomVO;
import com.smc.smccloud.service.ProductBomFeignApi;
import com.smc.smccloud.service.ProductBomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author edp04
 * @title: ProductBomFeignClient
 * @date 2022/06/10 17:18
 */
@Slf4j
@RestController
public class ProductBomFeignClient implements ProductBomFeignApi {

    @Resource
    private ProductBomService productBomService;


    @Override
    public ResultVo<List<ProductBomDetailVO>> findSplittableDetailByModelNo(String modelNo) {
        List<ProductBomDetailDO> list = productBomService.findSplittableDetailByModelNo(modelNo);
        List<ProductBomDetailVO> voList = BeanCopyUtil.copyList(list, ProductBomDetailVO.class);
        return ResultVo.success(voList);
    }

    @Override
    public ResultVo<Boolean> isCanSplit(String modelNo) {
        return ResultVo.success(productBomService.isCanSplit(modelNo));
    }
}
