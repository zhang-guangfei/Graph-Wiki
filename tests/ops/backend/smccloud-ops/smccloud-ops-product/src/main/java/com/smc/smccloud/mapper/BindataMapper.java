package com.smc.smccloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.dynamic.datasource.annotation.DS;

@DS("opsdb")
@Mapper
public interface BindataMapper {

	@Select("<script>" + " select ModelNo from Bindata where ModelNo in "
			+ " <foreach collection='modelNoList' item='item' index='index' open='(' separator=',' close=')'> "
			+ "   #{item} " + " </foreach> " + " and (StockType=1) and delFlag=0" + "</script>")
	List<String> listBinModelTY(@Param("modelNoList") List<String> modelNoList, @Param("customerno") String customerno);

	@Select("<script>" + " select ModelNo from Bindata where ModelNo in "
			+ " <foreach collection='modelNoList' item='item' index='index' open='(' separator=',' close=')'> "
			+ "   #{item} "
            + " </foreach> "
            + " and ((StockType=4 and CustomerNo=#{customerno})) and delFlag=0"
			+ "</script>")
	List<String> listBinModelGK(@Param("modelNoList") List<String> modelNoList, @Param("customerno") String customerno);


    @Select("<script>"
            + "select DISTINCT modelNo from ( "
            + " SELECT ModelNo FROM Bindata where StockType = 1 and delFlag = 0 \n"
            + " union all\n"
            + " SELECT ModelNo FROM Bindata where StockType = 4 and delFlag = 0 and CustomerNo =#{ customerno } \n"
            + ") t "
            + "where t.ModelNo in "
            + " <foreach collection='modelNoList' item='item' index='index' open='(' separator=',' close=')'> "
            + "   #{item} "
            + " </foreach> "
            + "</script>")
    List<String> listBinModel(@Param("modelNoList") List<String> modelNoList, @Param("customerno") String customerno);
}
