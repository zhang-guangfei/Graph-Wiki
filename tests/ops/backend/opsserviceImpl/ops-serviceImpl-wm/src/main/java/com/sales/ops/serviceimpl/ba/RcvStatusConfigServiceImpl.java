package com.sales.ops.serviceimpl.ba;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.RcvStatusConfigMapper;
import com.sales.ops.db.entity.RcvStatusConfig;
import com.sales.ops.db.entity.RcvStatusConfigExample;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.service.ba.RcvStatusConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author C12961
 * @date 2022/11/8 10:10
 */
@Slf4j
@Service
@Transactional(rollbackFor= Exception.class)
public class RcvStatusConfigServiceImpl implements RcvStatusConfigService {


    @Autowired
    private OPSRedisUtils opsRedisUtils;
    @Autowired
    private RcvStatusConfigMapper rcvStatusConfigMapper;


    @Override
    public Boolean findConfigByKey(RcvOrderStatusEnum fromStatus, RcvOrderStatusEnum toStatus) throws OpsException {
        //先查缓存
        String key ="ops:rcv:enable:"+ fromStatus.getType() + "-" + toStatus.getType();
        Object obj = opsRedisUtils.get(key);
        if (obj != null) {
            return (Boolean) obj;
        } else {
            RcvStatusConfigExample example = new RcvStatusConfigExample();
            example.createCriteria().andFromStatusEqualTo(Byte.valueOf(Short.toString(fromStatus.getType())))
                    .andTargetStatusEqualTo(Byte.valueOf(Short.toString(toStatus.getType())));
            List<RcvStatusConfig> configs = rcvStatusConfigMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(configs)) {
                RcvStatusConfig config = configs.get(0);
                opsRedisUtils.setKeyAndTimeout(key, config.getEnable(), 1, TimeUnit.DAYS);
                return config.getEnable();
            } else {
                throw Exceptions.OpsException(String.format("状态变更顺序异常：%s和%s不在配置表中", fromStatus.getType(), toStatus.getType()));
            }
        }
    }


}
