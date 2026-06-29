package com.smc.smccloud.service;

import com.smc.smccloud.model.notice.Notice;

import java.util.List;

public interface NoticeService {

    List<Notice> selectLastestNotice();
}
