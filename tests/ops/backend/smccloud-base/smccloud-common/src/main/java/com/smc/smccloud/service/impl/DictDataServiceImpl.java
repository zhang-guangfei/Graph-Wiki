package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.DictDataMapper;
import com.smc.smccloud.model.*;
import com.smc.smccloud.service.DictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictDataServiceImpl implements DictDataService {

    @Resource
    private DictDataMapper dictDataMapper;
    @Resource
    private RedisManager redisManager;
    // 根据classCode获取其配置列表
    public static final String OPS_DICT_CLASS_CODE = "ops:dict:classCode:";
    public static final String OPS_CAN_DEL_CONFIG = "ops:canDelCodes:";
    public static final String OPS_CAN_DEL_CODE = "1001";

    @Override
    public ResultVo<String> saveDict(DataTypeDO dataTypeDO) {
        Date nowDate = DateUtil.getNow();
        if (PublicUtil.isEmpty(dataTypeDO.getId())) {
            dataTypeDO.setCreateTime(nowDate);
            int add = dictDataMapper.insert(dataTypeDO);
            return add == 1 ? ResultVo.success("新增成功") : ResultVo.failure("新增失败");
        } else {
            dataTypeDO.setUpdateTime(nowDate);
            int update = dictDataMapper.updateById(dataTypeDO);
            if (update == 1) {
                String classCode = dataTypeDO.getClassCode();
                String key = OPS_DICT_CLASS_CODE + classCode;
                if (redisManager.hasKey(key)) {
                    redisManager.delete(key);
                }
                if (classCode.equals(OPS_CAN_DEL_CODE)) {
                    key = OPS_CAN_DEL_CONFIG + classCode;
                    redisManager.delete(key);
                }
            }
            return update == 1 ? ResultVo.success("更新成功") : ResultVo.failure("更新失败");
        }
    }

    @Override
    public DataTypeDO getDictById(Long dictId) {
        return dictDataMapper.selectById(dictId);
    }

    @Override
    public ResultVo<String> deleteDict(DataTypeDO dataTypeDO) {
        if (dataTypeDO.getId() == null) {
            return ResultVo.failure("删除的id为空");
        }

        DataTypeDO dictById = getDictById(dataTypeDO.getId());

        int delete = dictDataMapper.deleteById(dataTypeDO.getId());
        if (delete != 0) {
            if(dictById != null) {
                String classCode = dictById.getClassCode();
                String key = OPS_DICT_CLASS_CODE + classCode;
                if (redisManager.hasKey(key)) {
                    redisManager.delete(key);
                }
                if (classCode.equals(OPS_CAN_DEL_CODE)) {
                    key = OPS_CAN_DEL_CONFIG + classCode;
                    redisManager.delete(key);
                }
            }
        }
        return delete != 0 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
    }

    @Override
    public List<RegionBeanTree> queryChildrenForOne(DataTypeRequest dataTypeRequest) {

        return dictDataMapper.queryChildrenForOne(dataTypeRequest.getClassCode());
    }

    @Override
    public List<RegionBeanTree> queryAll() {
        return dictDataMapper.queryAll();
    }

    @Override
    public ResultVo<PageInfo<DataTypeDO>> queryByClassCode(DataTypeRequest dataTypeRequest, Page page) {
        QueryWrapper<DataTypeDO> query = new QueryWrapper<>();
        query.eq("class_code", dataTypeRequest.getClassCode());
        query.orderByAsc("sort", "code");
        PageInfo<DataTypeDO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> dictDataMapper.selectList(query));
        if (PublicUtil.isEmpty(pageInfo)) {
            return null;
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public List<DataTypeDO> listDict(DataTypeRequest request) {
        QueryWrapper<DataTypeDO> query = new QueryWrapper<>();
        // 按照条件查询 若不为空 则进行条件拼接
        query.eq("1", 1);
        query.eq(PublicUtil.isNotEmpty(request.getClassCode()), "class_code", request.getClassCode());
        query.eq(PublicUtil.isNotEmpty(request.getCode()), "code", request.getCode());
        query.orderByAsc("sort", "code");
        return dictDataMapper.selectList(query);
    }

    @Override
    public ResultVo<PageInfo<DataTypeDO>> queryByClassCodeToActive(DataTypeRequest dataTypeRequest, Page page) {
        QueryWrapper<DataTypeDO> query = new QueryWrapper<>();
        query.eq("class_code", dataTypeRequest.getClassCode());
        query.eq("status", 1);
        query.orderByDesc("sort");
        PageInfo<DataTypeDO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> dictDataMapper.selectList(query));
        if (PublicUtil.isEmpty(pageInfo)) {
            return null;
        }
        return ResultVo.success(pageInfo);
    }


    @Override
    public List<DataTypeDO> listByClassCode(DataTypeRequest dataTypeRequest) {
        QueryWrapper<DataTypeDO> query = new QueryWrapper<>();
        query.eq("1", 1);
        query.eq("class_code", dataTypeRequest.getClassCode());
        query.orderByAsc("sort", "code");
        return dictDataMapper.selectList(query);

    }

    @Override
    @DS("opscmm")
    public DataTypeVO getDataCodesInfo(String classCode, String code) {
        QueryWrapper<DataTypeDO> query = new QueryWrapper<>();
        query.eq("class_code", classCode);
        query.eq("code", code);
        DataTypeDO dataTypeDO = dictDataMapper.selectOne(query);
        if (PublicUtil.isEmpty(dataTypeDO)) {
            return null;
        }
        try {
            return BeanCopyUtil.copy(dataTypeDO, DataTypeVO.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResultVo<Integer> updateDataParam(DataTypeVO dataTypeVO) {

        try{
            if (dataTypeVO==null) {
                return ResultVo.failure("数据不可为空。");
            }
            DataTypeDO dataTypeDO = BeanCopyUtil.copy(dataTypeVO, DataTypeDO.class);
            int i=0;

            if(PublicUtil.isNotEmpty(dataTypeDO.getId())){
                dataTypeDO.setUpdateTime(DateUtil.getNow());
                dataTypeDO.setUpdateUser(SMCApp.getLoginAuthDtoForSysUser().getUserNo());
                  i = dictDataMapper.updateById(dataTypeDO);
            }
            else {
                if (StringUtils.isBlank(dataTypeDO.getClassCode()) || StringUtils.isBlank(dataTypeDO.getCode())) {
                    return ResultVo.failure("classCode & code 不可为空.");
                }
                //    <!--add by WuWeiDong 20230327 bug 10113 bin补库增加修改控制的最大月数 -->
                LambdaUpdateWrapper<DataTypeDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(DataTypeDO::getClassCode, dataTypeDO.getClassCode())
                        .eq(DataTypeDO::getCode, dataTypeDO.getCode())
                        .set(PublicUtil.isNotEmpty(dataTypeDO.getExtNote1()), DataTypeDO::getExtNote1, dataTypeDO.getExtNote1())
                        .set(PublicUtil.isNotEmpty(dataTypeDO.getExtNote2()), DataTypeDO::getExtNote2, dataTypeDO.getExtNote2())
                        .set(PublicUtil.isNotEmpty(dataTypeDO.getExtNote3()), DataTypeDO::getExtNote3, dataTypeDO.getExtNote3())
                        .set(DataTypeDO::getUpdateTime, DateUtil.getNow())
                       .set(DataTypeDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo());
                i = dictDataMapper.update(null, updateWrapper);
            }
            if(i==0)
            {
                return ResultVo.failure("更新字典错误。");
            }
            return ResultVo.success(i);
        }catch (Exception ex){

            return ResultVo.failure("更新字典错误。"+ex);
        }
    }

    @Override
    public ResultVo<List<DataCodeVO>> getSampleBalTypeByApplyType(String applyType) {
        if (StringUtils.isBlank(applyType)) {
           return getDataCodes("1007");
        }
        applyType = applyType.trim();
        List<String> codes = new ArrayList<>();
        switch (applyType) {
            case "1": // 试用品免费试用
                codes.add("101"); // 赠品
                codes.add("201"); // 展览展示品
                codes.add("401"); // 良品返回
                codes.add("301"); // 转销售开票
                return getApplyTypeByCodes(codes);
            case "2": // 试用品待转销售
                codes.add("101");
                codes.add("201");
                codes.add("401");
                codes.add("301");
                return getApplyTypeByCodes(codes);
            case "3": // 展览展示品
                codes.add("102"); // 样品
                return getApplyTypeByCodes(codes);
            case "4": // 盘亏报损
                codes.add("103"); // 报废处理
                return getApplyTypeByCodes(codes);
            case "5": // CTC实验品
                codes.add("201");
                codes.add("401");
                return getApplyTypeByCodes(codes);
            case "6": // 故障替代品
                codes.add("201");
                codes.add("401"); // 良品返回
                codes.add("301"); // 转销售开票
                codes.add("103"); // 报废处理
                codes.add("104");
                return getApplyTypeByCodes(codes);
            case "7": // 故障替代品
                codes.add("104"); // 维修品
                return getApplyTypeByCodes(codes);
        }
        return null;
    }

    @Override
    public ResultVo<List<DataCodeVO>> getDataTypeByParentCode(String parentCode) {
        LambdaUpdateWrapper<DataTypeDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper
                .eq(DataTypeDO::getStatus,1)
                .eq(DataTypeDO::getParentCode,parentCode)
                .orderByAsc(DataTypeDO::getCode);
        List<DataTypeDO> dataTypeDOList = dictDataMapper.selectList(lambdaUpdateWrapper);
        return ResultVo.success(BeanCopyUtil.copyList(dataTypeDOList, DataCodeVO.class));
    }

    private  ResultVo<List<DataCodeVO>> getApplyTypeByCodes(List<String> codes) {
        QueryWrapper<DataTypeDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(DataTypeDO::getClassCode, "1007")
                .eq(DataTypeDO::getStatus, 1)
                .eq(DataTypeDO::getParentCode, "")
                .in(DataTypeDO::getCode,codes)
                .orderByAsc(DataTypeDO::getSort, DataTypeDO::getCode);
        List<DataTypeDO> dataTypeDOList = dictDataMapper.selectList(query);
        return ResultVo.success(BeanCopyUtil.copyList(dataTypeDOList, DataCodeVO.class));
    }

    @Override
    public ResultVo<Integer> updateDataType(String classCode, String code, String extNote2) {
        DataTypeVO dataCodesInfo = this.getDataCodesInfo(classCode, code);

        DataTypeDO dataTypeDO = BeanCopyUtil.copy(dataCodesInfo, DataTypeDO.class);
        dataTypeDO.setExtNote2(extNote2);
        int i = dictDataMapper.updateById(dataTypeDO);
        if (i > 0) {
            return ResultVo.success(i);
        } else {
            return ResultVo.failure("修改失败");
        }
    }

    @Override
    public String getRandomOrderNoGenerator(String classCode, String code) {
        DataTypeVO dataCodesInfo = getDataCodesInfo(classCode, code);
        if (PublicUtil.isEmpty(dataCodesInfo)) {
            return null;
        }
        String extNote1Str = dataCodesInfo.getExtNote1(); // 获取节点1
        String extNote2Str = String.format("%0" + dataCodesInfo.getExtNote3() + "d", Integer.parseInt(dataCodesInfo.getExtNote2()) + 1); // 节点二根据节点三的数自动填充零
        String str = Integer.parseInt(extNote2Str) + "";
        // 修改节点二的数据
        DataTypeDO dataTypeDO = BeanCopyUtil.copy(dataCodesInfo, DataTypeDO.class);
        dataTypeDO.setExtNote2(str);
        int i = dictDataMapper.updateById(dataTypeDO);
        if (i <= 0) {
            return null;
        }
        return extNote1Str + extNote2Str;
    }

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
        return dictDataMapper.selectList(query);
    }


    @Override
    public ResultVo<List<DataCodeVO>> getDataCodesTree(String classCode) {
        QueryWrapper<DataTypeDO> query = new QueryWrapper<>();
        query.lambda().eq(DataTypeDO::getClassCode, classCode).eq(DataTypeDO::getStatus, 1).orderByAsc(DataTypeDO::getSort, DataTypeDO::getCode);
        List<DataTypeDO> listDO = dictDataMapper.selectList(query);

        List<DataCodeVO> listParents = listDO.stream().filter(i -> i.getParentCode().equals(""))
                .map(p -> {
                    DataCodeVO parentVo = new DataCodeVO(p.getCode(), p.getCodeName(),p.getSort());
                    parentVo.setChildren(listDO.stream().filter(item -> item.getParentCode().equals(p.getCode())).map(child -> {
                        return new DataCodeVO(child.getCode(), child.getCodeName(),child.getSort());
                    }).collect(Collectors.toList()));
                    return parentVo;
                }).collect(Collectors.toList());

        return ResultVo.success(listParents);


    }

    @Override
    public ResultVo<Boolean> updateExtNote2(Long id, String extNote2, String curExtNote2) {

        LambdaUpdateWrapper<DataTypeDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DataTypeDO::getExtNote2, extNote2);
        updateWrapper.eq(DataTypeDO::getId, id).eq(DataTypeDO::getExtNote2, curExtNote2);

        if (1 == dictDataMapper.update(null, updateWrapper)) {
            return ResultVo.success(true);
        }
        return ResultVo.failure("更新失败");
    }


}
