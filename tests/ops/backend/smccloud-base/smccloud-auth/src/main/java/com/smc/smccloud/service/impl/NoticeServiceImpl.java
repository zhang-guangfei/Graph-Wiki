package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.NoticeMapper;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.notice.Notice;
import com.smc.smccloud.model.notice.NoticeCondition;
import com.smc.smccloud.service.notice.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> selectLastestNotice() {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        // 指定某些字段不查询
        queryWrapper.select(Notice.class, info -> !info.getColumn().equals("readers")
                && !info.getColumn().equals("read_status"));
        queryWrapper.eq("1","1");
        queryWrapper.orderByDesc("NEWSDATE");
        List<Notice> notices = noticeMapper.selectList(queryWrapper);
        if (PublicUtil.isEmpty(notices)) {
            return null;
        }
        return notices;
    }

    @Override
    public PageInfo<Notice> queryByReadPower(NoticeCondition noticeCondition, Page page) {
        PageInfo<Notice> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> noticeMapper.queryByReadPower(noticeCondition.getUsername()));
        return pageInfo;
    }

    @Override
    public PageInfo<Notice> query(NoticeCondition noticeCondition, Page page) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1",1);
        queryWrapper.eq(PublicUtil.isNotEmpty(noticeCondition.getCreateName()),"CREATE_NAME",noticeCondition.getCreateName());
        queryWrapper.eq(PublicUtil.isNotEmpty(noticeCondition.getEditStatus()),"EDITSTATUS",noticeCondition.getEditStatus());
        queryWrapper.eq(PublicUtil.isNotEmpty(noticeCondition.getHeading()),"HEADING",noticeCondition.getHeading());
        if (PublicUtil.isNotEmpty(noticeCondition.getStartDate())) {
            queryWrapper.ge("newsDate", DateUtil.dateToString(noticeCondition.getStartDate()));
        }
        if (PublicUtil.isNotEmpty(noticeCondition.getEndDate())) {
            queryWrapper.le("newsDate",DateUtil.dateToString(noticeCondition.getEndDate()));
        }
        PageInfo<Notice> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> noticeMapper.selectList(queryWrapper));

        List<String> readerList= noticeMapper.getReaderNum();
        List<Notice> noticeList=pageInfo.getList();
        log.info(readerList.toString());
        for(Notice notice:noticeList) {
            int num=0;
            for (String noticeId : readerList) {
                if(notice.getId().equals(noticeId))
                    num++;
            }
            notice.setReadedNum(num);
        }
        return pageInfo;
    }
}
