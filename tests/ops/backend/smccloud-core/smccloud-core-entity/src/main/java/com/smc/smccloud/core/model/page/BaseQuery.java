/*
 * @Descripttion: 
 * @version: 
 * @Author: zhang rui
 * @Date: 2019-11-12 09:23:21
 * @LastEditors: zhang rui
 * @LastEditTime: 2019-11-19 16:42:08
 */

package com.smc.smccloud.core.model.page;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseQuery implements Serializable {

	private static final long serialVersionUID = 3319698607712846427L;

	/**
	 * 当前页
	 */
	private Integer pageNum = 1;

	/**
	 * 每页条数
	 */
	private Integer pageSize = 10;

	/**
	 * 排序
	 */
	private String orderBy;
}