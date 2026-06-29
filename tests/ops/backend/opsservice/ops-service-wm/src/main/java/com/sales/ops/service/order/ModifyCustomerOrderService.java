package com.sales.ops.service.order;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.Orderdlvdata;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author C12961
 * @date 2023/3/10 14:41
 */
public interface ModifyCustomerOrderService {
    int modifyPurchaseNo(String orderNo, String purchaseNo, UserDto userDto) throws OpsException;

    int modifyDlvEntireAndType(String orderNo, String dlvEntire, String dlvType, UserDto userDto) throws OpsException;

    CommonResult calMaxUpdateDlvDateLimit2(String orderNo, Integer orderItem);

    int modifyDlvDate(String orderNo, Integer orderItem, Date dlvDate, Date wmsDlvDate, UserDto userDto) throws OpsException;

    int modifyCproductNo(String orderNo, Integer orderItem, String cproductNo, UserDto userDto) throws OpsException;

    int modifyDlvAddress(String orderNo, Integer orderItem, Orderdlvdata dlvdata, Boolean specialFlag, UserDto userDto) throws OpsException;

    int modifyCarrier(String orderNo, Integer orderItem, String carrier, Boolean specialFlag, UserDto userDto) throws OpsException;

    int modifyDlvSite(String orderNo, Integer orderItem, Orderdlvdata dlvdata, Boolean specialFlag, UserDto userDto) throws OpsException;

    int modifyDlvSpecial(String orderNo, Integer orderItem, boolean special, UserDto userDto) throws OpsException;

    int modifyDlvSplit(String orderNo, Integer orderItem, UserDto userDto) throws OpsException;

    List<String> getRorderFnoByCredit() throws OpsException;

    int beforeWmStart(Rcvdetail rcvdetail) throws OpsException;

    int beforeWmPackage(Rcvdetail rcvdetail) throws OpsException;

    boolean successModifyDo(Map<String, String> resultMap);

    void modifyRcvDetail(Rcvdetail update, UserDto userDto) throws OpsException;
}
