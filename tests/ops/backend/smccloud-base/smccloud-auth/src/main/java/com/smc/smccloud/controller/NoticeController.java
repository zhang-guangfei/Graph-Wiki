package com.smc.smccloud.controller;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.notice.Notice;
import com.smc.smccloud.model.notice.NoticeAttachedFile;
import com.smc.smccloud.model.notice.NoticeCondition;
import com.smc.smccloud.model.notice.NoticeReader;
import com.smc.smccloud.service.notice.NoticeAttachedFileService;
import com.smc.smccloud.service.notice.NoticeReaderService;
import com.smc.smccloud.service.notice.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class NoticeController {
    @Resource
    private NoticeService noticeService;
    @Resource
    private NoticeAttachedFileService noticeAttachedFileService;
    @Resource
    private NoticeReaderService noticeReaderService;

    /**
     * 查询最新的公告
     *
     */
    @RequestMapping(value = "/coreNotice/findLatestNotice")
    public Notice findLatestNotice() {
        List<Notice> list = noticeService.selectLastestNotice();
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 根据最新公告id查询附件notice
     * @return
     */
    @RequestMapping(value = "/coreNotice/findLatestAttachedfileNotice")
    public List<NoticeAttachedFile> findLatestNoticeAttachedfile() {
        List<Notice> list = noticeService.selectLastestNotice();
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        return noticeAttachedFileService.getByNoticeId(list.get(0).getId());
    }


    @RequestMapping(value = "/coreNotice/findNoticeReader",method = RequestMethod.GET)
    public NoticeReader getNoticeReader(@RequestParam("noticeId") String noticeid, @RequestParam("username") String userName) {
        log.info(noticeid+" "+userName);
        return noticeReaderService.getNoticeReader(noticeid, userName);
    }

    /**
     * 按用户查询-普通用户
     *
     */
    @RequestMapping(value = "/coreNotice/queryByReadPower")
    public PageInfo<Notice> queryByReadPower(@RequestBody NoticeCondition noticeCondition, Page page) {
        return noticeService.queryByReadPower(noticeCondition, page);
    }

    /**
     * 按条件查询
     *
     */
    @RequestMapping(value = "/coreNotice/query")
    public PageInfo<Notice> query(@RequestBody NoticeCondition noticeCondition, Page page) {
        return noticeService.query(noticeCondition, page);
    }

    /**
     * 查询公告id对应的附件
     */
    @RequestMapping(value = "/coreNotice/findAttachedfileNoticeByID",method = RequestMethod.POST)
    public List<NoticeAttachedFile> findNoticeAttachedfileById(@RequestParam("noticeId") String noticeid) {
        return noticeAttachedFileService.getByNoticeId(noticeid);
    }



}
