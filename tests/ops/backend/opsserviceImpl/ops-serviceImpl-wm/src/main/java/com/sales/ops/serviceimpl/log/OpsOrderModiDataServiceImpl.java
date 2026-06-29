package com.sales.ops.serviceimpl.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.dao.OpsOrderModidataMapper;
import com.sales.ops.db.dao.RcvdetailMapper;
import com.sales.ops.db.entity.OpsOrderModidata;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.RcvdetailExample;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.OrderModiDataTypeEnum;
import com.sales.ops.service.log.OpsOrderModiDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2022/4/13 8:46
 */
@Service
public class OpsOrderModiDataServiceImpl implements OpsOrderModiDataService {


    @Resource
    private OpsOrderModidataMapper opsOrderModidataMapper;
    @Resource
    private RcvdetailMapper rcvdetailMapper;

    @Override
    public int insertOpsOrderModiData(OpsOrderModidata modidata) throws OpsException {
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andRorderNoEqualTo(modidata.getOrderId()).andRorderItemEqualTo(modidata.getOrderItem());
        List<Rcvdetail> rcvdetailList = rcvdetailMapper.selectByExample(example);
        Rcvdetail rcv = rcvdetailList.get(0);

        if (StringUtils.isBlank(modidata.getnModelno())) {
            modidata.setoModelno(rcv.getModelNo());
            modidata.setnModelno(rcv.getModelNo());
        }
        if (modidata.getnQuantity() == null) {
            modidata.setoQuantity(rcv.getQuantity());
            modidata.setnQuantity(rcv.getQuantity());
        }
        if (modidata.getnPrice() == null) {
            modidata.setoPrice(rcv.getPrice());
            modidata.setnPrice(rcv.getPrice());
        }
        if (modidata.getnDlvdate() == null) {
            modidata.setoDlvdate(rcv.getDlvDate());
            modidata.setnDlvdate(rcv.getDlvDate());
        }
        modidata.setCreateTime(new Date());
        if (modidata.getCreateUser() == null) {
            modidata.setCreateUser(UserDto.ADMIN.getUserName());
        }
        modidata.setUpdateTime(new Date());
        return opsOrderModidataMapper.insertSelective(modidata);
    }

    @Override
    public int insertModiDataForCancelPo(String orderId, Integer orderItem, String orderFullNo, String remark) throws OpsException {
        OpsOrderModidata modidata = new OpsOrderModidata();
        modidata.setOrderId(orderId);
        modidata.setOrderItem(orderItem);
        modidata.setRorderFno(orderFullNo);
        modidata.setTypeCode(OrderModiDataTypeEnum.CANCELPO.getType());
        modidata.setRemark(remark);
        return insertOpsOrderModiData(modidata);
    }

    @Override
    public int insertModiDataForResetInit(String orderId, Integer orderItem, String orderFullNo, String remark) throws OpsException {
        OpsOrderModidata modidata = new OpsOrderModidata();
        modidata.setOrderId(orderId);
        modidata.setOrderItem(orderItem);
        modidata.setRorderFno(orderFullNo);
        modidata.setTypeCode(OrderModiDataTypeEnum.REDISPATCH.getType());
        modidata.setRemark(remark);
        return insertOpsOrderModiData(modidata);
    }

    @Override
    public int insertModiDataForCancelOrder(String orderId, Integer orderItem, String orderFullNo, String reason, String duty, String origin) throws OpsException {
        OpsOrderModidata modidata = new OpsOrderModidata();
        modidata.setOrderId(orderId);
        modidata.setOrderItem(orderItem);
        modidata.setRorderFno(orderFullNo);
        modidata.setTypeCode(OrderModiDataTypeEnum.CANCEL.getType());
        modidata.setRemark(reason);
        modidata.setDutyName(duty);
        modidata.setCreateUser(origin);
        return insertOpsOrderModiData(modidata);
    }

    @Override
    public int insertModiDataForModifyOrder(String orderId, Integer orderItem, String orderFullNo, Date oDlvdate, Date nDlvdate, String creator) throws OpsException {
        OpsOrderModidata modidata = new OpsOrderModidata();
        modidata.setOrderId(orderId);
        modidata.setOrderItem(orderItem);
        modidata.setRorderFno(orderFullNo);
        modidata.setTypeCode(OrderModiDataTypeEnum.UPDATE.getType());
        modidata.setoDlvdate(oDlvdate);
        modidata.setnDlvdate(nDlvdate);
        modidata.setCreateUser(creator);
        return insertOpsOrderModiData(modidata);
    }


    /**
     * 强制完纳 bugid:11758 20230814 c14717
     * @param newRcv
     * @param oldRcv
     * @param userName
     * @return
     * @throws OpsException
     */
    @Override
    public int insertModiDataForFinishOrder(Rcvdetail newRcv, Rcvdetail oldRcv, String userName) throws OpsException {
        OpsOrderModidata modidata = new OpsOrderModidata();
        //变更字段
        modidata.setoQuantity(oldRcv.getQuantity());
        modidata.setnQuantity(newRcv.getQuantity());
        modidata.setoPrice(oldRcv.getPrice());
        modidata.setnPrice(newRcv.getPrice());
        //默认字段
        modidata.setoModelno(oldRcv.getModelNo());
        modidata.setnModelno(oldRcv.getModelNo());
        modidata.setoDlvdate(oldRcv.getDlvDate());
        modidata.setnDlvdate(oldRcv.getDlvDate());
        //默认字段
        modidata.setOrderId(oldRcv.getRorderNo());
        //bugid:12917 20231220 c14717
        modidata.setOrderItem(oldRcv.getRorderItem());
        modidata.setRorderFno(oldRcv.getRorderFno());
        modidata.setTypeCode(OrderModiDataTypeEnum.CANCEL.getType());
        modidata.setCreateUser(userName);
        modidata.setCreateTime(DateUtil.getNow());
        modidata.setUpdateTime(DateUtil.getNow());
        return opsOrderModidataMapper.insertSelective(modidata);
    }

}
