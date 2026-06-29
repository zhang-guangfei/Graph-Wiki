package com.smc.smccloud.mapper.sampleorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.sampleorder.SampleOrderManageDO;
import com.smc.smccloud.model.sampleorder.SampleOrderManageQuery;
import com.smc.smccloud.model.sampleorder.SampleOrderManageVO;
import com.smc.smccloud.model.sampleorder.SampleOrderRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-08-01
 */
@Mapper
@DS("sharedb")
public interface SampleOrderManageMapper extends BaseMapper<SampleOrderManageDO> {

    @Select("select orderNo,model_no as modelNo,sum(impQty) as impQty, parentDeptNo, remark \n" +
            "from SampleOrderManage where status = 1 and impQty <> 0 and outTime >= #{outTimeStart} and outTime <= #{outTimeEnd} \n" +
            "GROUP BY orderNo,model_no, parentDeptNo, remark")
    List<SampleOrderManageVO> getSampleOrderManageNeZero(@Param("outTimeStart") String outTimeStart,@Param("outTimeEnd") String outTimeEnd);

    @Select("select top(1)outTime from SampleOrderManage where impQty > 0 order by outTime desc")
    String findSampleOrderManageLastOutTime();

    @Select("select orderNo,model_no as modelNo,sum(impQty) as impQty, deptNo\n" +
            "from SampleOrderManage where orderNo = #{orderNo} and model_no = #{modelNo} and status = 1 and impQty <> 0\n" +
            "GROUP BY orderNo,model_no, deptNo")
    List<SampleOrderManageVO> getSampleOrderManageByOrderNo(@Param("orderNo") String orderNo, @Param("modelNo") String modelNo);

    @Select("select ISNULL(sum(impQty) , 0) from SampleOrderManage where orderNo = #{orderNo} and deptNo = #{deptNo} ")
    int getSumQtyByOrderNo(@Param("orderNo") String orderNo, @Param("deptNo") String deptNo);
}
