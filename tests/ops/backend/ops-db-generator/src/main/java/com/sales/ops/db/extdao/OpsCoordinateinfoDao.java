package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Select;

public interface OpsCoordinateinfoDao {

	

	@Select("select max(id) as messageid from ops_coordinateInfo ")
	Long  getmessageid();


}