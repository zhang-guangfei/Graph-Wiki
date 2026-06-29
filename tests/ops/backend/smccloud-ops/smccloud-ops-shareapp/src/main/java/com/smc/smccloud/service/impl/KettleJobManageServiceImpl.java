package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.log.annotation.SysLog;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.kettle.KettleJobManageMapper;
import com.smc.smccloud.mapper.kettle.KettleNoticeTaskMapper;
import com.smc.smccloud.model.kettle.KettleJobManageDO;
import com.smc.smccloud.model.kettle.KettleNoticeTaskDO;
import com.smc.smccloud.model.kettle.KettleNoticeTaskVO;
import com.smc.smccloud.model.kettle.MdmNoticeVO;
import com.smc.smccloud.service.KettleJobManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2023/4/21 15:33
 * @Descripton TODO
 */
@Slf4j
@Service
public class KettleJobManageServiceImpl implements KettleJobManageService {

    @Resource
    private KettleJobManageMapper kettleJobManageMapper;

    @Resource
    private KettleNoticeTaskMapper kettleNoticeTaskMapper;

    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private KettleJobManageService kettleJobManageService;

    @SysLog("mdm通知任务接口")
    @Override
    @DS("kettledb")
    public ResultVo<String> mdmNoticeWithOpsBaseData(String jsonStr) {

        log.info("mdm通知任务接口入参: {}",jsonStr);

        if(StringUtils.isBlank(jsonStr)) {
            return ResultVo.failure("40001","入参不可为空");
        }
        List<MdmNoticeVO> list = null;
        try {
            list = JSONArray.parseArray(jsonStr, MdmNoticeVO.class);
        } catch (Exception e) {
            return ResultVo.failure("40001","入参格式不正确,解析失败.");
        }

        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure("40001","参数不可为空");
        }

        KettleJobManageDO kettleJobManageDO;
        List<KettleNoticeTaskDO> taskDOArrayList = new ArrayList<>();
        KettleNoticeTaskDO kettleNoticeTaskDO;
        LambdaQueryWrapper<KettleJobManageDO> queryWrapper = new LambdaQueryWrapper<>();
        Date date = new Date();
        StringBuilder errMsg = new StringBuilder();

        for (MdmNoticeVO item : list) {
           if (item == null) {
               return ResultVo.failure("40001","入参数为空");
           }
           if (StringUtils.isBlank(item.getTableName())) {
               return ResultVo.failure("40001","变更表名称不可存在空值");
           }
           if (PublicUtil.getWordByteLen(item.getTableName()) > 50) {
               return ResultVo.failure("40001","变更表名称字节长度超过最大长度50");
           }
           if (item.getTableLastUpdatedDate() == null) {
               return ResultVo.failure("40001",item.getTableName()+"表变更时间不可为空");
           }
           kettleNoticeTaskDO = new KettleNoticeTaskDO();
           queryWrapper.clear();
           queryWrapper.eq(KettleJobManageDO::getMdmTableName,item.getTableName());
           kettleJobManageDO  = kettleJobManageMapper.selectOne(queryWrapper);
           if (kettleJobManageDO == null) {
               kettleNoticeTaskDO.setOptStatus("2");
               kettleNoticeTaskDO.setErrorMsg(item.getTableName()+"不进行响应式的增量处理");
               kettleNoticeTaskDO.setJobName("NAN");
           } else {
               kettleNoticeTaskDO.setOptStatus("0");
               kettleNoticeTaskDO.setJobName(kettleJobManageDO.getJobName());
           }
            kettleNoticeTaskDO.setMdmTableName(item.getTableName());
            kettleNoticeTaskDO.setCreateTime(date);
            kettleNoticeTaskDO.setUpdateTime(date);
            kettleNoticeTaskDO.setMdmBatchNo(item.getBatchNumber());
            kettleNoticeTaskDO.setLastUpdateTime(item.getTableLastUpdatedDate());
            taskDOArrayList.add(kettleNoticeTaskDO);
        }
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.execute(transactionStatus -> {
            try {
                for (KettleNoticeTaskDO item : taskDOArrayList) {
                    kettleNoticeTaskMapper.insertTask(item);
                }
            } catch (Exception e) {
                log.error("写入到任务通知表发生异常:"+e.getMessage());
                errMsg.append("系统内部异常:"+e.getMessage()+";");
                transactionStatus.setRollbackOnly(); // 手动回滚
                return false;
            }
            return true;
        });
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure("-1",errMsg.toString());
        }
        return ResultVo.successByCode("0","成功推送"+taskDOArrayList.size()+"条数据");
    }

    @SysLog("保存日志测试")
    @Override
    public ResultVo<String> testSaveLog(String str) {
        System.out.println("str = " + str);
        kettleJobManageService.tetsSaveLog2("1212");
        return ResultVo.success();
    }

    @SysLog("保存接口里调接口测试")
    @Override
    public ResultVo<String> tetsSaveLog2(String str2) {
        System.out.println("保存接口里调接口测试 = " + str2);
        return ResultVo.success();
    }
}
