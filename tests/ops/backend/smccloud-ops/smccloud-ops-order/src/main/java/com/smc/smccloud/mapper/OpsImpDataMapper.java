package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.order.OpsImpDataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author edp04
 * @title: OpsImpDataMapper
 * @date 2022/07/13 17:57
 */
@Mapper
@DS("opsdb")
public interface OpsImpDataMapper extends BaseMapper<OpsImpDataDO> {
    //    <!--add by WuWeiDong 20221027 bug 2084 -->
    //发票与入库型号有差异
    @Select(" select invoiceId,invoiceNo ,poNo,lineItem,splitItemNo ,orderno,itemno,splitItemNo,modelNo,receiveWarehouseId,  sum(quantity) as quantity \n" +
            "            from ops_impdata   where operate=1 \n" +
            "        group by invoiceId,invoiceNo ,poNo,lineItem,splitItemNo,orderno,itemno,splitItemNo ,modelNo,receiveWarehouseId  ")
    List<OpsImpDataDO> selectInvoiceErrorModel();
}
