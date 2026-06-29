package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.mapper.DataTypeMapper;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeDO;
import com.smc.smccloud.service.DataTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/8/20 14:26
 */

@Service
public class DataTypeServiceImpl implements DataTypeService {
    @Autowired
    private DataTypeMapper dataTypeMapper;

    @Override
    public ResultVo<List<DataCodeVO>> getDataCodes(String classCode) {
        List<DataTypeDO> listDO = getValidDataCodesDO(classCode);
        List<DataCodeVO> codes = BeanCopyUtil.copyList(listDO, DataCodeVO.class);
        return ResultVo.success(codes);
    }

    /**
     * 根据分类编码查询有效代码
     */
    private List<DataTypeDO> getValidDataCodesDO(String classCode) {
        QueryWrapper<DataTypeDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(DataTypeDO::getClassCode, classCode)
                .eq(DataTypeDO::getStatus, 1)
                .eq(DataTypeDO::getParentCode, "")
                .orderByAsc(DataTypeDO::getSort, DataTypeDO::getCode);
        return dataTypeMapper.selectList(query);
    }
}
