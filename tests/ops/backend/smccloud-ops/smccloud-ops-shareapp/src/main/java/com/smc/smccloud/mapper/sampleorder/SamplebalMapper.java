package com.smc.smccloud.mapper.sampleorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.sampleorder.SamplebalDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-04
 */
@Mapper
@DS("opsdb")
public interface SamplebalMapper extends BaseMapper<SamplebalDO> {

    @Select("select * from ( " +
            " select ROrderNo as rorderNo,ModelNo as modelNo ,sum(Quantity) quantity,isnull(rcvdeptno,deptno) as deptNo from ops_core.dbo.sample_bal where balType = '201' and optCode in ('6','7')  " +
            " and inDate >= #{startExpDate} and inDate <= #{endExpDate}   " +
            " group by ROrderNo,ModelNo,isnull(rcvdeptno,deptno) " +
            " ) o where quantity <> 0 ")
    List<SamplebalDO> findZlzsOrderBalanceByExpDate(@Param("startExpDate") String startExpDate,@Param("endExpDate") String endExpDate);


//    @Select("select * from ( " +
//            " select ROrderNo as rorderNo,ModelNo as modelNo ,sum(Quantity) quantity,isnull(rcvdeptno,deptno) as rcvDeptNo, id, inDate, expDate " +
//            " from ops_core.dbo.sample_bal where balType = '201' and optCode in ('6','7')  " +
//            " and inDate >= #{startInDate} and inDate <= #{endInDate}   " +
//            " group by ROrderNo,ModelNo,isnull(rcvdeptno,deptno), id, inDate, expDate" +
//            " ) o where quantity <> 0 ")
    @Select(" select ROrderNo as rorderNo,ModelNo as modelNo ,Quantity as quantity,rcvDeptNo, id, inDate, expDate from (\n" +
            " select * from (\n" +
            " select ROrderNo as rorderNo,ModelNo as modelNo ,Quantity as quantity,isnull(rcvdeptno,deptno) as rcvDeptNo, id, inDate, expDate  \n" +
            " from ops_core.dbo.sample_bal where balType = '201' and optCode in ('6','7')   \n" +
            " and inDate >= #{startInDate} and inDate <= #{endInDate}  \n" +
            " ) o where quantity <> 0 \n" +
            " union all \n" +
            " select ROrderNo as rorderNo,ModelNo as modelNo ,Quantity as quantity,isnull(rcvdeptno,deptno) as rcvDeptNo, id, inDate, expDate  \n" +
            " from ops_core.dbo.sample_bal where balType = '401' and optCode in ('6','7')  and AppType = '3' and quantity < 0\n" +
            " and inDate >= #{startInDate} and inDate <= #{endInDate}  \n" +
            " ) t ")
    List<SamplebalDO> findZlzsOrderIntoSampleOrderManageByinDate(@Param("startInDate") String startInDate,@Param("endInDate") String endInDate);


    @Select("select * from ( " +
            " select ROrderNo as rorderNo,ModelNo as modelNo,sum(Quantity) quantity,deptNo from ops_core.dbo.sample_bal where balType = '201' and optCode in ('6','7')  " +
            " and RorderNo = #{rorderNo} and ModelNo = #{modelNo}   " +
            " group by ROrderNo,ModelNo,deptNo " +
            " ) o where quantity <> 0 ")
    List<SamplebalDO> findZlzsOrderBalanceByRorderNoAndModelNo(@Param("rorderNo") String rorderNo,@Param("modelNo") String modelNo);

}
