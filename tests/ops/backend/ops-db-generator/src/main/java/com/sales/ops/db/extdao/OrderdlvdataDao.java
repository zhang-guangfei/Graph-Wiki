package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.Orderdlvdata;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author C12961
 * @date 2022/5/17 10:15
 */
public interface OrderdlvdataDao {

    @Insert("<script> insert into OrderDlvData" +
            "    <trim prefix='(' suffix=')' suffixOverrides=','>" +
            "      <if test='orderno != null '> OrderNo,</if> " +
            "      <if test='itemno != null '> ItemNo,</if> " +
            "      <if test='customerno != null '> CustomerNo,</if> " +
            "      <if test='cstmname != null '> CstmName,</if> " +
            "      <if test='dlvaddress != null '> DlvAddress,</if> " +
            "      <if test='contactpsn != null '> ContactPsn,</if> " +
            "      <if test='telno != null '> TelNo,</if> " +
            "      <if test='postcode != null '> PostCode,</if> " +
            "      <if test='idcard != null '> IDCard,</if> " +
            "      <if test='dlvflag != null '> DlvFlag,</if> " +
            "      <if test='province != null '> Province,</if> " +
            "      <if test='city != null '> City,</if> " +
            "      <if test='district != null '> District,</if> " +
            "      <if test='carrierid != null '> CarrierId,</if> " +
            "       CreateTime,CreateUser" +
            "    </trim> " +
            "     values " +
            "    <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "      <if test='orderno != null '> #{OrderNo},</if> " +
            "      <if test='itemno != null '> #{ItemNo},</if> " +
            "      <if test='customerno != null '> #{CustomerNo},</if> " +
            "      <if test='cstmname != null '> #{CstmName},</if> " +
            "      <if test='dlvaddress != null '> #{DlvAddress},</if> " +
            "      <if test='contactpsn != null '> #{ContactPsn},</if> " +
            "      <if test='telno != null '> #{TelNo},</if> " +
            "      <if test='postcode != null '> #{PostCode},</if> " +
            "      <if test='idcard != null '> #{IDCard},</if> " +
            "      <if test='dlvflag != null '> #{DlvFlag},</if> " +
            "      <if test='province != null '> #{Province},</if> " +
            "      <if test='city != null '> #{City},</if> " +
            "      <if test='district != null '> #{District},</if> " +
            "      <if test='carrierid != null '> #{CarrierId},</if> " +
            "       #{CreateTime},#{CreateTime}" +
            "    </trim> " +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertDlvData(Orderdlvdata dlvData);
}
