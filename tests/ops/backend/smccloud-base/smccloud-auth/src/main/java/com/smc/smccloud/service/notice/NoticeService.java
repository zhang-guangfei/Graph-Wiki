package com.smc.smccloud.service.notice;

import com.github.pagehelper.PageInfo;

import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.notice.Notice;
import com.smc.smccloud.model.notice.NoticeCondition;

import java.util.List;

public interface NoticeService {

    List<Notice> selectLastestNotice();

    /**
     * 用户查询
     */
    public PageInfo<Notice> queryByReadPower(NoticeCondition noticeCondition, Page page);

    /**
     * 条件查询公告
     *
     */
    public PageInfo<Notice> query(NoticeCondition noticeCondition, Page page);
}
