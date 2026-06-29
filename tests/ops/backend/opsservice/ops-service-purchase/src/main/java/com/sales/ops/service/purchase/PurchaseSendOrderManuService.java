package com.sales.ops.service.purchase;

import com.sales.ops.common.opsexception.OpsException;

/**
 * @author B91717
 */
public interface PurchaseSendOrderManuService {

	// bug11473 马腾 自动发单将具体操作独立一个新函数，以便后续调用
	public Integer operateManufacture(String versionLike) throws Exception;

	/**
	 * 工厂订单发送，写关务接口
	 */
	public Integer sendOrderToManuInterface() throws Exception;

	/**
	 * CTC订单状态更新，已完成/删除时，更新ctc订单状态
	 * 
	 * @return
	 */
	public void updateCtcFinish() throws OpsException;

	/**
	 * CTC订单状态更新，已完成/删除时，更新ctc订单状态
	 * 
	 * @return
	 */
	public void updateCtcDel() throws OpsException;
}
