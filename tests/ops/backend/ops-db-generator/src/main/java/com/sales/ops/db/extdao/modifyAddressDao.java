package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Select;

/**
 * @author C12961
 * @date 2022/8/29 8:07
 */
public interface modifyAddressDao {


    @Select("select * from ops_do where do_id=#{doId}")
    void selectDo(String doId);
}
