package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataService;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DictDataServiceFeignClient implements DictDataServiceFeignApi {

    @Resource
    private DictDataService dictDataService;

    @Override
    public ResultVo<List<DataCodeVO>> getDataCodes(String classCode) {
        return dictDataService.getDataCodes(classCode);
    }

    @Override
    public ResultVo<List<DataCodeVO>> getDataCodesTree(String classCode) {
        return dictDataService.getDataCodesTree(classCode);
    }

    @Override
    public ResultVo<DataTypeVO> getDataTypeCodesInfo(String classCode, String code) {
        DataTypeVO dataCodesInfo = dictDataService.getDataCodesInfo(classCode, code);
        if (PublicUtil.isEmpty(dataCodesInfo)) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(dataCodesInfo);
    }

    @Override
    public ResultVo<Integer> updateDataType(String classCode, String code, String extNote2) {
        return dictDataService.updateDataType(classCode, code, extNote2);
    }

    @Override
    public ResultVo<String> getRandomOrderNoGenerator(String classCode, String code) {
        String randomOrderNoGenerator = dictDataService.getRandomOrderNoGenerator(classCode, code);
        if (PublicUtil.isEmpty(randomOrderNoGenerator)) {
            return ResultVo.failure("生成失败");
        }
        return ResultVo.success(randomOrderNoGenerator);
    }

    @Override
    public ResultVo<Integer> updateDataParam(DataTypeVO dataTypeVO) {
        return dictDataService.updateDataParam(dataTypeVO);
    }

    @Override
    public ResultVo<List<DataCodeVO>> getDataTypeByParentCode(String parentCode) {
        return dictDataService.getDataTypeByParentCode(parentCode);
    }

}
