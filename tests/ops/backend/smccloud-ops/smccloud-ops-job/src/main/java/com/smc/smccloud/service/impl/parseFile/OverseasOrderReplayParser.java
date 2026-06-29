package com.smc.smccloud.service.impl.parseFile;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.EmailInfo;
import com.smc.smccloud.model.FileParseType;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.EmailFileParser;
import com.smc.smccloud.service.OrderStateServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OverseasOrderReplayParser implements EmailFileParser {

    @Resource
    private OrderStateServiceFeignApi orderStateServiceFeignApi;

    @Override
    public String keyword() {
        return FileParseType.OVERSEASORdERREPLAY.keyword();
    }

    /**
     * 解析 .xlsx文件
     * @param file
     * @return
     */
    @Override
    public ResultVo<String> parserFile(File file) {

        log.info("文件 => {}, 解析器: {}", file.getName(), "OverseasOrderReplayParser");
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            ExcelHelper excel = new ExcelHelper(inputStream);
            Sheet sheet = excel.getSheet();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            List<OrderStateVO> list = new ArrayList<>();
//            int row = 1;
            int cel = 0;
            int lastRowNum = sheet.getLastRowNum();
            for (int row = 1; row <= lastRowNum;row ++) {
//            while (true) {
                Row rows = sheet.getRow(row);
                if (rows == null) {
                    break;
                }
                cel = 0;
                OrderStateVO stateVO = new OrderStateVO();
                stateVO.setSupplierCode(excel.getCellString(rows.getCell(cel++)).trim());
                stateVO.setOrderNo(excel.getCellString(rows.getCell(cel++)).trim());
                stateVO.setModelNo(excel.getCellString(rows.getCell(cel++)).trim());
                stateVO.setQuantity(Integer.valueOf(excel.getCellString(rows.getCell(cel++)).trim()));
                String orderDate = excel.getCellString(rows.getCell(cel++)).trim();
                if (PublicUtil.isNotEmpty(orderDate)) {
                    Date date = DateUtil.stringToDate(orderDate);
                    stateVO.setOrderDate(date);
                }
                String rcvTime = excel.getCellString(rows.getCell(cel++)).trim();
                if (PublicUtil.isNotEmpty(rcvTime)) {
                    Date date = DateUtil.stringToDate(rcvTime);
                    stateVO.setSupplierRcvTime(date);
                }
                cel++;
                String poReplyDate = excel.getCellString(rows.getCell(cel)).trim();
                if (PublicUtil.isNotEmpty(poReplyDate)) {
                    Date date = DateUtil.stringToDate(poReplyDate);
                    stateVO.setPoReplyDate(date);
                }
                list.add(stateVO);
                row++;
            }
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            ResultVo<String> reply = orderStateServiceFeignApi.importOverseaOrderReply(list);
            if (!reply.isSuccess()) {
                return ResultVo.failure(reply.getMessage());
            }
            return ResultVo.success(reply.getMessage());

        } catch (Exception e) {
            log.error("{}文件解析失败", keyword(), e);
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                boolean delete = file.delete();
                log.info("删除OverseasOrderReplayParser解析邮件附件结果: {}",delete);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }

        return ResultVo.success();
    }

    @Override
    public void setSender(String sender) {

    }

    @Override
    public boolean validEmail(String title) {
        return false;
    }

    @Override
    public void setEmailInfo(EmailInfo info) {

    }


}
