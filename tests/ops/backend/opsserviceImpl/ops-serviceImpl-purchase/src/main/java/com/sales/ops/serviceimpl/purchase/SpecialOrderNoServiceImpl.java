package com.sales.ops.serviceimpl.purchase;

import java.util.List;
import java.util.Objects;

import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.common.until.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.sales.ops.service.purchase.SpecialOrderNoService;
import com.sales.ops.serviceimpl.utils.OrderNoUtils;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;

@Service
public class SpecialOrderNoServiceImpl implements SpecialOrderNoService {

	private final static Logger logger = LoggerFactory.getLogger(SpecialOrderNoServiceImpl.class);

	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;

	@Autowired
	private OPSRedisUtils opsRedisUtils;

	/**
	 * 验证一天内单号是否重复
	 * @param orderNo
	 * @return true 重复 false 不重复
	 */
	@Override
	public Boolean poNoRepeatCheck(String type, String orderNo){

        if (StringUtils.isBlank(orderNo)) {
            return true;
        }
		try {
			String key = "ops:po:generate:"+type+":"+orderNo;
			Object obj = opsRedisUtils.get(key);
			if(Objects.isNull(obj)){
				opsRedisUtils.set(key,1);
				//1天失效
				opsRedisUtils.expire(key,  60 * 60 * 24);
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("redis连接失败");
			return false;
		}
	}

    @Override
    public String generateOrderNo(String type) {

        String maxno = SnowFlake.generateId(8,null);
        // 验证单号是否重复，验证200次
        for(int i=0; i < 200; i ++){
            if(poNoRepeatCheck(type,maxno)){
                maxno = SnowFlake.generateId(8,null);
            }else {
                break;
            }
        }
        // 更新字典中存储处理到的id值
        return maxno;
    }



	public String generateOrderNoOld(String type) {
		ResultVo<List<DataCodeVO>> result = dictDataServiceFeignApi.getDataCodes("3012");
		if (!result.isSuccess() || CollectionUtils.isEmpty(result.getData())
				|| StringUtils.isBlank(result.getData().get(0).getExtNote1())
				|| StringUtils.isBlank(result.getData().get(1).getExtNote1())) {
			logger.error("字典3012无数据，请补充字典数据！！");
			return null;
		}
		String maxno;
		if (StringUtils.equals("AP", type)) {
			maxno = result.getData().get(0).getExtNote1();
		} else if (StringUtils.equals("MR", type)) {
			maxno = result.getData().get(1).getExtNote1();
		} else {
			return null;
		}
		if (StringUtils.isBlank(maxno)) {
			return null;
		}
		maxno = OrderNoUtils.hexAdd(maxno, 1);
		// 验证单号是否重复，验证10次
		for(int i=0; i < 10; i ++){
			if(poNoRepeatCheck(type,maxno)){
				maxno = OrderNoUtils.hexAdd(maxno, 1);
			}else {
				break;
			}
		}

		// 更新字典中存储处理到的id值
		DataTypeVO dataTypeVO = new DataTypeVO();
		dataTypeVO.setClassCode("3012");
		dataTypeVO.setCode(type);
		dataTypeVO.setExtNote1(maxno);

		ResultVo<Integer> i = dictDataServiceFeignApi.updateDataParam(dataTypeVO);
		if (i.isSuccess()) {
			return maxno;
		} else {
			return null;
		}
	}

}
