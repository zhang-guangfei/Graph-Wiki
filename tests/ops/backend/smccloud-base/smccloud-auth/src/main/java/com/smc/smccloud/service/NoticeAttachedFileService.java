package com.smc.smccloud.service;

import com.smc.smccloud.model.notice.NoticeAttachedFile;

import java.util.List;

public interface NoticeAttachedFileService
{
    /**
     * 按照公告ID查找公告附件
     *
     * @Author B82561
     * @return
     */
    List<NoticeAttachedFile> getByNoticeId(String noticeId);
}
