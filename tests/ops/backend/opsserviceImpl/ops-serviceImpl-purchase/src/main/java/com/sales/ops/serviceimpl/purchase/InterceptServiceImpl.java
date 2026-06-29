package com.sales.ops.serviceimpl.purchase;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.sales.ops.dto.purchase.OpsRequestPurchaseInterceptConfigVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsRequestpurchaseInterceptConfigMapper;
import com.sales.ops.db.dao.OpsWarehouseMapper;
import com.sales.ops.db.entity.OpsRequestpurchaseInterceptConfig;
import com.sales.ops.db.entity.OpsRequestpurchaseInterceptConfigExample;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.purchase.InterceptService;

/**
 * @author B91717
 * @date 2021/12/24
 * @apiNote
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InterceptServiceImpl implements InterceptService {

	@Autowired
	private OpsRequestpurchaseInterceptConfigMapper opsRequestpurchaseInterceptConfigMapper;

	@Autowired
	private OpsWarehouseMapper opsWarehouseMapper;

	/**
	 * 条件查询，自定义拦截清单查询
	 *
	 * @param pageModel
	 */
	@Override
	public PageInfo<OpsRequestpurchaseInterceptConfig> findAll(PageModel<OpsRequestPurchaseInterceptConfigVO> pageModel) {

		PageInfo<OpsRequestpurchaseInterceptConfig> pageInfo = new PageInfo<OpsRequestpurchaseInterceptConfig>();
		OpsRequestpurchaseInterceptConfigExample example = new OpsRequestpurchaseInterceptConfigExample();
		OpsRequestpurchaseInterceptConfigExample.Criteria criteria = example.createCriteria();
		// 筛选初始列表状态
		if (pageModel.getCondition() == null) {
			pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
					.doSelectPageInfo(() -> opsRequestpurchaseInterceptConfigMapper.selectByExample(null));
			return pageInfo;
		}
        OpsRequestPurchaseInterceptConfigVO condition = pageModel.getCondition();

		if (StringUtils.isNotBlank(condition.getTypeid())) {
			criteria.andTypeidEqualTo(condition.getTypeid());
		}

        if (CollectionUtils.isNotEmpty(condition.getRulekeys())) {
            criteria.andRulekeyIn(condition.getRulekeys());
        }

		if (StringUtils.isNotBlank(condition.getReason())) {
			String reason = "%" + condition.getReason() + "%";
			criteria.andReasonLike(reason);
		}

		if (StringUtils.isNotBlank(condition.getOperator())) {
			criteria.andOperatorEqualTo(condition.getOperator());
		}

        if (condition.getEnable() != null) {
            criteria.andEnableEqualTo(condition.getEnable());
        }

		pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
				.doSelectPageInfo(() -> opsRequestpurchaseInterceptConfigMapper.selectByExample(example));
		return pageInfo;
	}

	@Override
	public Integer updateData(OpsRequestpurchaseInterceptConfig opsRequestpurchaseInterceptConfig) throws OpsException {
		// bug14634 用户输入星号表示任意字符匹配时，替换为正确的正则格式
		if (opsRequestpurchaseInterceptConfig.getRulekey().contains("*")
				&& !opsRequestpurchaseInterceptConfig.getRulekey().contains("(.*)")) {
			opsRequestpurchaseInterceptConfig
					.setRulekey(opsRequestpurchaseInterceptConfig.getRulekey().replace("*", "(.*)"));
		}
		//bugid:17646 c14717 20250522
		if(!opsRequestpurchaseInterceptConfig.getTypeid().equals("2")){
			opsRequestpurchaseInterceptConfig.setRuleKey2(null);
			opsRequestpurchaseInterceptConfig.setRuleKey1(null);
		}
		// 更新
		opsRequestpurchaseInterceptConfig.setUpdatetime(new Date());
		int result = opsRequestpurchaseInterceptConfigMapper.updateByPrimaryKey(opsRequestpurchaseInterceptConfig);
		return result;
	}

	/**
	 * 插入
	 * 
	 * @param opsRequestpurchaseInterceptConfig
	 * @return
	 * @throws OpsException
	 */
	@Override
	public Integer insertData(OpsRequestpurchaseInterceptConfig opsRequestpurchaseInterceptConfig) throws OpsException {
		// bug14634 用户输入星号表示任意字符匹配时，替换为正确的正则格式
		if (opsRequestpurchaseInterceptConfig.getRulekey().contains("*")
				&& !opsRequestpurchaseInterceptConfig.getRulekey().contains("(.*)")) {
			opsRequestpurchaseInterceptConfig
					.setRulekey(opsRequestpurchaseInterceptConfig.getRulekey().replace("*", "(.*)"));
		}
		int result = opsRequestpurchaseInterceptConfigMapper.insertSelective(opsRequestpurchaseInterceptConfig);
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param opsRequestpurchaseInterceptConfig
	 * @throws OpsException
	 */
	@Override
	public void deleteData(OpsRequestpurchaseInterceptConfig opsRequestpurchaseInterceptConfig) throws OpsException {
        OpsRequestpurchaseInterceptConfig config = new OpsRequestpurchaseInterceptConfig();
        config.setEnable(false);
		OpsRequestpurchaseInterceptConfigExample example = new OpsRequestpurchaseInterceptConfigExample();
		example.createCriteria().andIdEqualTo(opsRequestpurchaseInterceptConfig.getId());
		opsRequestpurchaseInterceptConfigMapper.updateByExampleSelective(config,example);
	}

	@Override
	public List<OpsWarehouse> findWarehouse() {
		return opsWarehouseMapper.selectByExample(null);
	}

	// bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
	@Override
	public Integer updateDataBatch(List<OpsRequestpurchaseInterceptConfig> list) throws OpsException {
		if (CollectionUtils.isEmpty(list)) {
			throw Exceptions.OpsException("勾选清单为空，请重新刷新页面后重试");
		}
		// 批量更新
		// 获取批量id集合
		List<Integer> idList = list.stream().map(a -> a.getId()).collect(Collectors.toList());
		OpsRequestpurchaseInterceptConfig request = new OpsRequestpurchaseInterceptConfig();
		request.setReason(list.get(0).getReason());
		request.setEnable(list.get(0).getEnable());
		request.setOperator(list.get(0).getOperator());
		request.setUpdatetime(new Date());
		// 获取id集合
		OpsRequestpurchaseInterceptConfigExample example = new OpsRequestpurchaseInterceptConfigExample();
		example.createCriteria().andIdIn(idList);
		return opsRequestpurchaseInterceptConfigMapper.updateByExampleSelective(request, example);
	}

	/**
	 * 批量删除功能 bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
	 * 
	 * @param list
	 * @return
	 * @throws OpsException
	 */
	@Override
	public Integer deleteBatch(List<OpsRequestpurchaseInterceptConfig> list) throws OpsException {
		// 获取批量id集合
		List<Integer> idList = list.stream().map(a -> a.getId()).collect(Collectors.toList());

        OpsRequestpurchaseInterceptConfig request = new OpsRequestpurchaseInterceptConfig();
        request.setEnable(false);

		OpsRequestpurchaseInterceptConfigExample example = new OpsRequestpurchaseInterceptConfigExample();
		example.createCriteria().andIdIn(idList);
		return opsRequestpurchaseInterceptConfigMapper.updateByExampleSelective(request,example);
	}
}
