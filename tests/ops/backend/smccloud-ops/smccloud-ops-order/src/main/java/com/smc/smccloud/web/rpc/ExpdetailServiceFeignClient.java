package com.smc.smccloud.web.rpc;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.expdetail.ExpdetailRequest;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import com.smc.smccloud.service.ExpdetailService;
import com.smc.smccloud.service.ExpdetailServiceFeignApi;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/1/21 10:17
 * @Descripton
 */
@RestController
public class ExpdetailServiceFeignClient implements ExpdetailServiceFeignApi {

    @Resource
    private ExpdetailService expdetailService;


    @Override
    public ResultVo<PageInfo<ExpdetailVO>> listExpdetail(ExpdetailRequest request) {
        // return expdetailService.listExpdetail(request);
        return null;
    }

    @Override
    public ResultVo<ExpdetailVO> findOne(Long id) {
        return expdetailService.findOne(id);
    }

    @Override
    public ResultVo<String> updateExpdetail(ExpdetailVO expdetailVO) {
        return expdetailService.updateExpDetail(expdetailVO);
    }

    @Override
    public ResultVo<List<ExpdetailVO>> findExpDetailByOrderType(ExpdetailRequest request) {
        return expdetailService.findExpDetailByOrderType(request);
    }

    @Override
    public ResultVo<ExpdetailVO> listExpdetailByOrderNo(String orderNo) {
        return expdetailService.listExpdetailByOrderNo(orderNo);
    }

    @Override
    public ResultVo<String> callExpExpdetailForGZ(){
        return expdetailService.callExpExpdetailForGZ();
    }

    @Override
    public ResultVo<String> updateExpdetailOptCodeById(Long id, String optCode) {
        return expdetailService.updateExpdetailOptCodeById(id,optCode);
    }
}
