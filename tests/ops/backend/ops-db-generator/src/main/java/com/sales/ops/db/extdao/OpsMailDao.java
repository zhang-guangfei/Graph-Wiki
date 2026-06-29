package com.sales.ops.db.extdao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.sales.ops.db.entity.OpsMail;

/**
 * @author ：C14717
 * @version: $ @description：
 * @date ：Created in 2023/7/12 14:03
 */
public interface OpsMailDao {

	@Select("select top 10 * from ops_mail where  status = 0")
	List<OpsMail> selectMailByDbTop10();

	@Select("select top 2 * from ops_mail where  status = 0")
	List<OpsMail> selectMailByDbTop2();

	@Insert("INSERT INTO [dbo].[ops_mail]([mail_from],[mail_to],[subject],[context],[send_date],[cc],"
			+ "[bcc],[status],[error_msg],[file_urls],[nick_name]) VALUES(#{mailFrom},#{mailTo}"
			+ ",#{subject},#{context},GETDATE(),#{cc},#{bcc},#{status},#{errorMsg},#{fileUrls},#{nickName})")
	@Options(useGeneratedKeys = true, keyProperty = "mailId")
	Long insertMail(OpsMail opsMail);

	@Select("select top 10 * from ops_mail where status =#{status} ")
	List<OpsMail> selectPurchaseMailWait(String status);
}
