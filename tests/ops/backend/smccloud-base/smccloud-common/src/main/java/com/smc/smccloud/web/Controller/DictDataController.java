package com.smc.smccloud.web.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.model.page.PageUtil;
import com.smc.smccloud.core.model.wrapper.PageWrapMapper;
import com.smc.smccloud.core.model.wrapper.PageWrapper;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeDO;
import com.smc.smccloud.model.DataTypeRequest;
import com.smc.smccloud.model.WarehouseDO;
import com.smc.smccloud.service.DictDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/common/dict")
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    @PostMapping(value = "/queryByClassCode")
    public ResultVo<PageInfo<DataTypeDO>> queryByClassCode(@RequestBody DataTypeRequest dataTypeRequest, Page page) {
        return dictDataService.queryByClassCode(dataTypeRequest, page);
    }

    @PostMapping(value = "/queryByClassCodeToActive")
    public ResultVo<PageInfo<DataTypeDO>> queryByClassCodeToActive(@RequestBody DataTypeRequest dataTypeRequest, Page page) {
        return dictDataService.queryByClassCode(dataTypeRequest, page);
    }

    @PostMapping(value = "/saveDict")
    public ResultVo<String> saveDict(@RequestBody DataTypeDO dataTypeDO) {
        return dictDataService.saveDict(dataTypeDO);
    }

    @RequestMapping(value = "/deleteDict", method = RequestMethod.POST)
    public ResultVo<String> deleteDict(@RequestBody DataTypeDO dataTypeDO) {
        return dictDataService.deleteDict(dataTypeDO);
    }

    @PostMapping(value = "/listDict")
    public PageWrapper<List<DataTypeDO>> listDict(@RequestBody DataTypeRequest dataTypeRequest) {
        PageHelper.startPage(dataTypeRequest.getPageNum(), dataTypeRequest.getPageSize());
        List<DataTypeDO> dataTypeDOList = dictDataService.listDict(dataTypeRequest);
        if (PublicUtil.isEmpty(dataTypeDOList)) {
            return PageWrapMapper.error("无符合查询条件的记录");
        }
        PageInfo<DataTypeDO> pageInfo = new PageInfo<>(dataTypeDOList);
        PageUtil pageUtil = new PageUtil(dataTypeRequest.getPageNum(), dataTypeRequest.getPageSize(), (int) pageInfo.getTotal(),
                pageInfo.getPages());
        pageUtil.setCurPageSize(dataTypeRequest.getPageSize());
        return PageWrapMapper.wrap(dataTypeDOList, pageUtil);
    }

    @PostMapping(value = "/listByClassCode")
    public List<DataTypeDO> listByClassCode(@RequestBody DataTypeRequest dataTypeRequest) {
        return dictDataService.listByClassCode(dataTypeRequest);
    }

    /**
     * 根据不同的申请类型来显示对应的结转方式
     */
    @GetMapping(value = "/getSampleBalTypeByApplyType")
    public ResultVo<List<DataCodeVO>> getSampleBalTypeByApplyType(@RequestParam("applyType") String applyType) {
        return dictDataService.getSampleBalTypeByApplyType(applyType);
    }

}
