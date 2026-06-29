package com.smc.smccloud.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Author: Denghui
 * Date: 2021-10-25 11:03
 * Description: 自动填充createTime,updateTime.
 * <p> 使用方法: createTime字段配置@TableField(... fill = FieldFill.INSERT),
 * updateTime字段配置@TableField(... fill = FieldFill.INSERT_UPDATE).</p>
 * <p> 注意事项: 如果使用update(@Param("et") T entity, @Param("ew") Wrapper<T> updateWrapper)方法更新数据,
 * entity参数必须不为空,至少传入一个new Entity().</p>
 */
@Slf4j
@Component
public class DefaultMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // log.info("===> start insert fill.");
        Date now = new Date();
        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // log.info("===> start update fill.");
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
