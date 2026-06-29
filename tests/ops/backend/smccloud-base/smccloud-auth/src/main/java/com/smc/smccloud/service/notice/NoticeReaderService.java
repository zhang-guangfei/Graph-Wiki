package com.smc.smccloud.service.notice;

import com.smc.smccloud.model.notice.NoticeReader;


public interface NoticeReaderService
{
    NoticeReader getNoticeReader(String noticeId, String username);
}
