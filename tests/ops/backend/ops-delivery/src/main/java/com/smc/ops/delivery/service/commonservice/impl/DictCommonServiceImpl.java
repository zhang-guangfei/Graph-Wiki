package com.smc.ops.delivery.service.commonservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smc.ops.delivery.mapper.DictDataMapper;
import com.smc.ops.delivery.model.DataCodeVO;
import com.smc.ops.delivery.model.DataTypeDO;
import com.smc.ops.delivery.model.DataTypeVO;
import com.smc.ops.delivery.model.PoAdapterConstants;
import com.smc.ops.delivery.service.commonservice.DictCommonService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author lyc
 * @Date 2024/2/27 11:53
 * @Descripton TODO
 */
@Service
@Slf4j
public class DictCommonServiceImpl implements DictCommonService {

    @Resource
    private DictDataMapper dictDataMapper;

    @Resource
    private RedisManager redisManager;


    @Override
    public ResultVo<List<DataCodeVO>> getDataCodes(String classCode) {
        String key = PoAdapterConstants.DICT_CLASSCODE+classCode;
        List<DataTypeDO> dataTypeDOS = new ArrayList<>();
        if (redisManager.hasKey(key)) {
            Object object = redisManager.get(key);
            if (object != null) {
                dataTypeDOS = JSONArray.parseArray(object.toString(), DataTypeDO.class);
            } else {
                dataTypeDOS = getValidDataCodesDO(classCode);
            }
        } else {
            dataTypeDOS = getValidDataCodesDO(classCode);
        }
        List<DataCodeVO> codes = BeanCopyUtil.copyList(dataTypeDOS, DataCodeVO.class);
        return ResultVo.success(codes);
    }

    @Override
    public String getWarehouseCodeBySMCCode(String smcCode, int extNote) {
        String key = "ops:datacode:2048";
        Object obj = redisManager.hGet(key, smcCode);
        DataCodeVO codeInfo;
        if (obj == null) {
            String classCode = "2048";
            ResultVo<List<DataCodeVO>> listResult = getDataCodes(classCode);
            if (!listResult.isSuccess()) {
                log.error("getSMCCodeToWarehouseMap error: {}", listResult.getMessage());
                return null;
            }

            Map<String, Object> map = new HashMap<>(listResult.getData().size());
            for (DataCodeVO codeVO : listResult.getData()) {
                map.put(codeVO.getCode(), JSON.toJSONString(codeVO));
            }
            redisManager.hPutAll(key, map);
            redisManager.expire(key, 60 * 60 * 8);
            obj = map.get(smcCode);
        }

        if (obj != null) {
            codeInfo = JSON.parseObject(obj.toString(), DataCodeVO.class);
            if (extNote == 2) {
                if (StringUtils.isNotBlank(codeInfo.getExtNote2())) {
                    return codeInfo.getExtNote2();
                }
            } else if (extNote == 3) {
                if (StringUtils.isNotBlank(codeInfo.getExtNote3())) {
                    return codeInfo.getExtNote3();
                }
            }
            return codeInfo.getExtNote1();
        }

        return null;
    }

    @Override
    public ResultVo<String> generatorBillNo(String billType) {

        String classCode = "9001";
        DataTypeVO codeInfo = getDataCodesInfo(classCode, billType);
        if (codeInfo ==null) {
            return ResultVo.failure("单据类型不存在" + billType);
        }
        // 单号前缀
        String prefixNo = codeInfo.getExtNote1();
        // 单号长度
        String lenth = codeInfo.getExtNote3();
        String serialNo;
        String toUpdExtNo2;
        int index = prefixNo.indexOf("{");

        // 带自定义格式
        if(index!=-1) {
            String format = prefixNo.substring(index + 1, prefixNo.indexOf("}", index));
            String datePrefixNo = DateUtil.getFormatDate(new Date(), format);
            // prefixNo = prefixNo.replace("{" + format + "}", datePrefixNo);
            String[] arrs = codeInfo.getExtNote2().split("-", 2);
            if (arrs.length == 2)
            {
                //不同前缀时从1开始
                if(arrs[0].equalsIgnoreCase(datePrefixNo))
                {
                    serialNo = String.format("%0"+lenth+"d",Integer.parseInt(arrs[1])+1);
                }
                else {
                    serialNo = String.format("%0"+lenth+"d",1);
                }
            }
            else
            {
                serialNo = String.format("%0"+lenth+"d",1);
            }
            toUpdExtNo2 = datePrefixNo+  "-" + serialNo;
            prefixNo = prefixNo.replace("{" + format + "}", datePrefixNo);;
        }
        else
        {
            serialNo = String.format("%0"+lenth+"d",Integer.parseInt(codeInfo.getExtNote2())+1);
            toUpdExtNo2 = Integer.parseInt(serialNo)+"";
        }
        ResultVo<Boolean> result = updateExtNote2(codeInfo.getId(), toUpdExtNo2,codeInfo.getExtNote2());
        if (!result.isSuccess()) {
            return  ResultVo.failure(null, "生成单据号失败");
        }
        return  ResultVo.success(prefixNo+serialNo);

    }

    @Override
    public DataTypeVO getDataCodesInfo(String classCode, String code) {
        LambdaQueryWrapper<DataTypeDO> query = new LambdaQueryWrapper<>();
        query.eq(DataTypeDO::getClassCode,classCode).eq(DataTypeDO::getCode,code);
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
    public ResultVo<Boolean> updateExtNote2(Long id, String extNote2, String curExtNote2) {
        LambdaUpdateWrapper<DataTypeDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DataTypeDO::getExtNote2, extNote2);
        updateWrapper.eq(DataTypeDO::getId, id).eq(DataTypeDO::getExtNote2, curExtNote2);

        if (1 == dictDataMapper.update(null, updateWrapper)) {
            return ResultVo.success(true);
        }
        return ResultVo.failure("更新失败");
    }

    @Override
    public ResultVo<Boolean> updateExtNote1(Long id, String extNote1, String curExtNote1) {
        LambdaUpdateWrapper<DataTypeDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DataTypeDO::getExtNote1, extNote1);
        updateWrapper.eq(DataTypeDO::getId, id).eq(DataTypeDO::getExtNote1, curExtNote1);

        if (1 == dictDataMapper.update(null, updateWrapper)) {
            return ResultVo.success(true);
        }
        return ResultVo.failure("更新失败");
    }





    /**
     * 根据分类编码查询有效代码
     */
    private List<DataTypeDO> getValidDataCodesDO(String classCode) {
        LambdaQueryWrapper<DataTypeDO> query = new LambdaQueryWrapper<>();
        query.eq(DataTypeDO::getClassCode, classCode)
                .eq(DataTypeDO::getStatus, 1)
                .eq(DataTypeDO::getParentCode, "")
                .orderByAsc(DataTypeDO::getSort, DataTypeDO::getCode);
        return dictDataMapper.selectList(query);
    }
}
