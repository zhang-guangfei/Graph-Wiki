package com.smc.smccloud.mapper.csstock;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.csstock.CsApplyDO;
import com.smc.smccloud.model.csstock.CsReturnApplyDO;
import com.smc.smccloud.model.csstock.CsReturnCalcVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * <p>
 *  退货 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Mapper
public interface CsReturnApplyMapper extends BaseMapper<CsReturnApplyDO> {

    /**
     * 查询需退货（归还大库）
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    @Select("{call ops_sharedb.dbo.csReturn_calcQty(#{agentNo,jdbcType=VARCHAR,mode=IN},#{warehouseCode,jdbcType=VARCHAR,mode=IN})}")
    @Options(statementType = StatementType.CALLABLE)
    //@ResultType(CsReturnCalcVo.class)
    List<CsReturnApplyDO> CalcCsReturn(  @Param("agentNo") String agentNo,@Param("warehouseCode") String warehouseCode);


    @Select("{call ops_sharedb.dbo.csReturn_calcQtyAll}")
    @Options(statementType = StatementType.CALLABLE)
    //@ResultType(CsReturnCalcVo.class)
    List<CsReturnApplyDO> CalcCsReturnAll();
}
