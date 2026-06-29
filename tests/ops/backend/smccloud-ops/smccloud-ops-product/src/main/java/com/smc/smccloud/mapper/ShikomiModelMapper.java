package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.shikomi.ShikomiModelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author edp04
 * @title: ShikomiModelMapper
 * @date 2022/04/28 17:01
 */
@DS("opsdb")
@Mapper
public interface ShikomiModelMapper extends BaseMapper<ShikomiModelDO> {


    @Select("<script>" +
            "SELECT id, shikomino,modelno,serialModel, UpdateTime, CreateTime, CreateUser, UpdateUser, modelType, mainModelFlag FROM shikomi_model" +
            " WHERE  " +
            " <foreach collection='list' item='item' index='index' open='(' separator=') or (' close=')'> " +
            " modelno = #{item.modelno} and shikomino = #{item.shikomino} " +
            " </foreach>" +
            "</script>")
    List<ShikomiModelDO> listShikomiModelData(@Param("list") List<ShikomiModelDO> list);

    @Update("<script>" +
            " <foreach collection='modelData' item='item' index='index' separator=';' > " +
            " update shikomi_model set serialModel = #{item.serialModel}, UpdateUser='JP', UpdateTime=GETDATE() " +
            " where shikomino = #{item.shikomino} and modelno = #{item.modelno} " +
            " </foreach>" +
            "</script>")
    int updateShikomiModelByList(@Param("modelData") List<ShikomiModelDO> modelData);

    @Update("UPDATE shikomi_model SET mainModelFlag = 0;" +
            "UPDATE shikomi_model SET mainModelFlag = 1 FROM shikomi_model b INNER JOIN shikomi_total a ON a.ModelNo = b.modelno and a.ShikomiNo = b.shikomino")
    int updateMainFlag();

}
