package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TDocOrderTrackingOps;
import com.sales.ops.db.entity.TDocOrderTrackingOpsExample;
import com.sales.ops.db.entity.TDocOrderTrackingOpsWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TDocOrderTrackingOpsMapper {
    long countByExample(TDocOrderTrackingOpsExample example);

    int deleteByExample(TDocOrderTrackingOpsExample example);

    int deleteByPrimaryKey(String trackingseqno);

    int insert(TDocOrderTrackingOpsWithBLOBs record);

    int insertSelective(TDocOrderTrackingOpsWithBLOBs record);

    List<TDocOrderTrackingOpsWithBLOBs> selectByExampleWithBLOBs(TDocOrderTrackingOpsExample example);

    List<TDocOrderTrackingOps> selectByExample(TDocOrderTrackingOpsExample example);

    TDocOrderTrackingOpsWithBLOBs selectByPrimaryKey(String trackingseqno);

    int updateByExampleSelective(@Param("record") TDocOrderTrackingOpsWithBLOBs record, @Param("example") TDocOrderTrackingOpsExample example);

    int updateByExampleWithBLOBs(@Param("record") TDocOrderTrackingOpsWithBLOBs record, @Param("example") TDocOrderTrackingOpsExample example);

    int updateByExample(@Param("record") TDocOrderTrackingOps record, @Param("example") TDocOrderTrackingOpsExample example);

    int updateByPrimaryKeySelective(TDocOrderTrackingOpsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TDocOrderTrackingOpsWithBLOBs record);

    int updateByPrimaryKey(TDocOrderTrackingOps record);
}