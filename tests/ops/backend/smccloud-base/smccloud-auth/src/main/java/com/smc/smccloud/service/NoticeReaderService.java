package com.smc.smccloud.service;

import com.smc.smccloud.model.notice.NoticeReader;


public interface NoticeReaderService
{
    NoticeReader getNoticeReader(String noticeId, String username);
}
