package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.mapper.NoticeAttachedFileMapper;
import com.smc.smccloud.model.notice.NoticeAttachedFile;
import com.smc.smccloud.service.notice.NoticeAttachedFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoticeAttachedFileServiceImpl implements NoticeAttachedFileService
{
    @Resource
    private NoticeAttachedFileMapper noticeAttachedFileMapper;

    @Override
    public List<NoticeAttachedFile> getByNoticeId(String noticeId) {
        QueryWrapper<NoticeAttachedFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID",noticeId);
        return noticeAttachedFileMapper.selectList(queryWrapper);
    }
}
