package com.sales.ops.serviceimpl.wmOrder;

import cn.hutool.core.util.ObjectUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OrderdlvdataMapper;
import com.sales.ops.db.entity.Orderdlvdata;
import com.sales.ops.db.entity.OrderdlvdataExample;
import com.sales.ops.db.entity.OrderdlvdataKey;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.service.wmOrder.OrderDlvDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2022/5/21 11:33
 */
@Service
public class OrderDlvDataServiceImpl implements OrderDlvDataService {

    @Resource
    private OrderdlvdataMapper orderdlvdataMapper;

    @Override
    public Orderdlvdata getOrderDlvDataByOrder(String orderNo, Integer orderItem) throws OpsException {
        OrderdlvdataKey orderdlvdataKey = new OrderdlvdataKey();
        orderdlvdataKey.setOrderno(orderNo);
        orderdlvdataKey.setItemno(orderItem);
        return orderdlvdataMapper.selectByPrimaryKey(orderdlvdataKey);
    }

    @Override
    public List<Orderdlvdata> getOrderDlvDatasByOrder(String orderNo, List<Integer> orderItems) throws OpsException {
        OrderdlvdataExample example = new OrderdlvdataExample();
        example.createCriteria().andOrdernoEqualTo(orderNo).andItemnoIn(orderItems);
        return orderdlvdataMapper.selectByExample(example);
    }

    @Override
    public List<Orderdlvdata> getOrderDlvDataByOrderNo(String orderNo) throws OpsException {
        OrderdlvdataExample example = new OrderdlvdataExample();
        example.createCriteria().andOrdernoEqualTo(orderNo);
        return orderdlvdataMapper.selectByExample(example);
    }

    @Override
    public void updateOrderDlvData(Orderdlvdata update, UserDto userDto) throws OpsException {
        // 查询rcvdetail的地址
        Orderdlvdata orderdlvdata = getOrderDlvDataByOrder(update.getOrderno(), update.getItemno());
        // 如果有拆分项的地址，则直接修改
        if (ObjectUtil.isNotNull(orderdlvdata)) {
            update.setUpdateuser(userDto.getUserName());
            update.setUpdatetime(new Date());
            orderdlvdataMapper.updateByPrimaryKeySelective(update);
        } else {
            //如果没有拆分项的地址，则先查主单的地址
            Orderdlvdata masterdlvdata = getOrderDlvDataByOrder(update.getOrderno(), 0);
            if (ObjectUtil.isNotNull(masterdlvdata)) {
                //修改主单的单号为拆分项
                masterdlvdata.setId(null);
                masterdlvdata.setItemno(update.getItemno());
                masterdlvdata.setCreateuser(userDto.getUserName());
                masterdlvdata.setCreatetime(new Date());
                //插入拆分项的地址
                orderdlvdataMapper.insertSelective(masterdlvdata);
                //再修改拆分项的地址
                updateOrderDlvData(update,userDto);
            } else {
                throw Exceptions.OpsException("查询不到orderDlvData");
            }
        }
    }


}
