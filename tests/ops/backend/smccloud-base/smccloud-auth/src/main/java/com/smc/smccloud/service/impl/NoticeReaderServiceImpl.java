package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.mapper.NoticeReaderMapper;
import com.smc.smccloud.model.notice.NoticeReader;
import com.smc.smccloud.service.notice.NoticeReaderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NoticeReaderServiceImpl implements NoticeReaderService {
    @Resource
    private NoticeReaderMapper noticeReaderMapper;
    @Override
    public NoticeReader getNoticeReader(String noticeId, String username) {
        QueryWrapper<NoticeReader> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(NoticeReader::getNoticeId,noticeId)
                .eq(NoticeReader::getUsername,username);
        return noticeReaderMapper.selectOne(queryWrapper);
    }
}
