package com.smc.smccloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sales.ops.dto.purchase.PoReplyInfoDto;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.mapper.PurchaseReplyPushJobMapper;
import com.smc.smccloud.model.PoReplyDelayExcelVO;
import com.smc.smccloud.model.PoReplyInfoExcelVO;
import com.smc.smccloud.model.PurchaseReplyPushJobDO;
import com.smc.smccloud.service.PurchaseReplyPushJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class PurchaseReplyPushJobServiceImpl implements PurchaseReplyPushJobService {

    @Autowired
    private PurchaseReplyPushJobMapper purchaseReplyPushJobMapper;

    @Override
    public void handle() {
        LambdaQueryWrapper<PurchaseReplyPushJobDO> qw = new LambdaQueryWrapper<>();
        qw.eq(PurchaseReplyPushJobDO::getHandleStatus, 0);
        List<PurchaseReplyPushJobDO> list = purchaseReplyPushJobMapper.selectList(qw);
        //货期延期
        List<PoReplyDelayExcelVO> DelayDateList = new ArrayList<>();
        List<Long> DelayDateIds = new ArrayList<>();
        //返信变更
        List<PoReplyInfoExcelVO> ReplyDateList = new ArrayList<>();
        List<Long> ReplyDateIds = new ArrayList<>();

        for (PurchaseReplyPushJobDO dto : list) {
            String parameter = dto.getParameter();
            PoReplyInfoDto data = JSONUtil.toBean(parameter, PoReplyInfoDto.class);
            if ("M".equals(dto.getBusinessCode())) {
                PoReplyDelayExcelVO excelVO = new PoReplyDelayExcelVO();
                BeanUtil.copyProperties(data, excelVO);
                DelayDateList.add(excelVO);
                DelayDateIds.add(dto.getId());
            }
            if ("N".equals(dto.getBusinessCode())) {
                PoReplyInfoExcelVO excelVO = new PoReplyInfoExcelVO();
                BeanUtil.copyProperties(data, excelVO);
                ReplyDateList.add(excelVO);
                ReplyDateIds.add(dto.getId());
            }
        }

        //生成excel文件
        //String basePathFile = "D:\\export";
        String basePathFile = "/ops/shareFileServer/";
        String fileName1 = basePathFile + File.separator + "制造返信延期清单" + File.separator + "返信延期清单_" + DateUtil.dateToString(new Date()) + ".xlsx";
        EasyExcel.write(fileName1, PoReplyDelayExcelVO.class).sheet("返信延期清单").doWrite(DelayDateList);
        log.info("文件保存成功：{}", fileName1);

        String fileName2 = basePathFile + File.separator + "制造返信变更清单" + File.separator + "返信变更清单" + DateUtil.dateToString(new Date()) + ".xlsx";
        EasyExcel.write(fileName2, PoReplyInfoExcelVO.class).sheet("返信变更清单").doWrite(ReplyDateList);
        log.info("文件保存成功：{}", fileName2);

        //写入数据库

        PurchaseReplyPushJobDO update = new PurchaseReplyPushJobDO();
        update.setHandleStatus(1);
        update.setHandleTime(new Date());

        List<List<Long>> partition = ListUtil.partition(DelayDateIds, 1000);
        for (List<Long> ids : partition) {
            qw.clear();
            qw.in(PurchaseReplyPushJobDO::getId, ids);
            purchaseReplyPushJobMapper.update(update, qw);
        }

        partition = ListUtil.partition(ReplyDateIds, 1000);
        for (List<Long> ids : partition) {
            qw.clear();
            qw.in(PurchaseReplyPushJobDO::getId, ids);
            purchaseReplyPushJobMapper.update(update, qw);
        }

    }
}
