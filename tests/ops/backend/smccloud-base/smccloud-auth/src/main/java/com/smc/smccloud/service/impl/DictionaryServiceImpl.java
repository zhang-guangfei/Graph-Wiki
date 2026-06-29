package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.utils.BaseException;
import com.smc.smccloud.mapper.DictionaryMapper;
import com.smc.smccloud.model.dict.Dictionary;
import com.smc.smccloud.core.model.constants.BaseExceptionCode;
import com.smc.smccloud.service.dict.DictionaryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Override
    public List<Dictionary> queryAll() {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1",1);
        queryWrapper.orderByAsc("SORT");
        return dictionaryMapper.selectList(queryWrapper);
    }

    @Override
    public Dictionary addRecord(Dictionary addData) {
        if(addData != null){
            /**
             * id和code一致，由编码规则生成
             */
            String codeData = code(addData.getPid());
            addData.setId(codeData);
            addData.setName(addData.getName());
            addData.setCode(codeData);
            addData.setStatus("有效");
            addData.setPid(addData.getPid());
            addData.setCreateTime(new Date());
            addData.setDescription(addData.getDescription());
            addData.setSort(addData.getSort());
            dictionaryMapper.insert(addData);
        }
        return addData;
    }

    /**
     * 生成编码规则
     * @param pid
     * @return
     */
    private String code(String pid){
        /**
         * 查询父节点下的所有子节点
         * 查询父节点下所有编码不为空的子节点,按编码倒叙排列
         */
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PID",pid);
        queryWrapper.orderByDesc("CODE");
        List<Dictionary> childs = dictionaryMapper.selectList(queryWrapper);

        Dictionary parent = null;
        if(PublicUtil.isNotEmpty(pid)){
            parent = detail(pid);
        }

        /**
         * String类的format()方法用于创建格式化的字符串以及连接多个字符串对象
         * %d	整数类型（十进制）
         * 数字前面补0(加密常用)
         */
        if(CollectionUtils.isNotEmpty(childs)){
            String code = childs.get(0).getCode();
            int length = code.length();
            /**
             * %0 补0 比如 log.info("%03d",7) => 007   3: 长度（位数） 7: 显示数字
             *
             */
            return String.format("%0" + length + "d", Long.valueOf(code)+ 1);
        }else {
            return parent == null ? String.format("%0" + Constants.CODE_DIGIT + "d",0) : parent.getCode() + String.format("%0" + Constants.CODE_DIGIT + "d",0);
        }
    }


    @Override
    public Dictionary detail(String id) {
        return findById(id, Constants.AUTHORITY);
    }

    private Dictionary findById(String id, String message) {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PID",id);
        Dictionary t = dictionaryMapper.selectList(queryWrapper).get(0);

        if (t == null) {
            throw new BaseException(BaseExceptionCode.实体不存在, new Object[]{message});
        } else {
            return t;
        }
    }

    @Override
    public List<Dictionary> getChildsByPid(String pid) {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PID",pid);
        queryWrapper.orderByDesc("CODE");
        List<Dictionary> childs = dictionaryMapper.selectList(queryWrapper);
        return childs;
    }

    @Override
    public ResultVo<List<Dictionary>> findChildsByPid(String pid) {
        if (StringUtils.isEmpty(pid)) {
            return ResultVo.success();
        }
        LambdaQueryWrapper<Dictionary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dictionary::getPid,pid).orderByDesc(Dictionary::getCode);
        List<Dictionary> dictionaries = dictionaryMapper.selectList(queryWrapper);
        return ResultVo.success(dictionaries);
    }
}
