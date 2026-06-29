package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.borrowstock.BrwDetailVO;
import com.smc.smccloud.model.borrowstock.BrwDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BrwDetailMapper extends BaseMapper<BrwDetailDO> {


    @Select("SELECT a.opt_status,a.quantity,a.return_qty,a.model_no,a.remark,a.ship_date,a.exp_qty,a.item_id,a.warehouse_code FROM brw_detail a" +
            " inner join brw_master b ON a.brw_id = b.id WHERE b.id=#{id}")
    List<BrwDetailVO> listBrwDetailData(@Param("id") Integer id);
}
