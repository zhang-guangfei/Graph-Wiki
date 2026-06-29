package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.notice.NoticeAttachedFile;
import org.apache.ibatis.annotations.Mapper;

@DS("opscmm")
@Mapper
public interface NoticeAttachedFileMapper extends BaseMapper<NoticeAttachedFile> {
}
