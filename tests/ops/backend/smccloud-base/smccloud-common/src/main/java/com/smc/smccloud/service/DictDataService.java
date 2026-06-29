package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.*;
import com.smc.smccloud.model.Employee.EmployeeVO;
import com.smc.smccloud.model.warehouse.WarehouseQueryDTO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 数据字典服务
 */
public interface DictDataService {
    // 新增
    ResultVo<String> saveDict(DataTypeDO dataTypeDO);

    ResultVo<PageInfo<DataTypeDO>> queryByClassCode(DataTypeRequest dataTypeRequest, Page page);

    // 查某分类代码下所有有效的数据
    ResultVo<PageInfo<DataTypeDO>> queryByClassCodeToActive(DataTypeRequest dataTypeRequest, Page page);

    // 根据条件查询 分页（ 当所有条件为空时 查所有 ）
    List<DataTypeDO> listDict(DataTypeRequest dataTypeRequest);

    // 通过id 查询 dict信息
    DataTypeDO getDictById(Long dictId);

    // 删除dict
    ResultVo<String> deleteDict(DataTypeDO dataTypeDO);

    List<RegionBeanTree> queryChildrenForOne(DataTypeRequest dataTypeRequest);

    List<RegionBeanTree> queryAll();

    ResultVo<List<DataCodeVO>> getDataCodes(String classCode);

    ResultVo<List<DataCodeVO>> getDataCodesTree(String classCode);

    List<DataTypeDO> listByClassCode(DataTypeRequest dataTypeRequest);

    DataTypeVO getDataCodesInfo(String classCode, String code);

    /**
     * 更新流水号
     * @param id
     * @param extNote2
     * @return
     */
    public ResultVo<Boolean> updateExtNote2(Long id ,String extNote2,String curExtNote2);


    String getRandomOrderNoGenerator(String classCode, String code);

    ResultVo<Integer> updateDataType(String classCode, String code, String extNote2);

    ResultVo<Integer> updateDataParam(DataTypeVO dataTypeVO);

    ResultVo<List<DataCodeVO>> getSampleBalTypeByApplyType(String applyType);

    ResultVo<List<DataCodeVO>> getDataTypeByParentCode(String parentCode);


}

