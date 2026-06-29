
package com.smc.smccloud.controller;
import com.smc.smccloud.model.menu.Menu;
import com.smc.smccloud.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/menu")
// @CrossOrigin
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * 获取全部节点
	 *
	 * @return
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Menu> findAll() {
		return menuService.findAll();
	}

	/**
	 * 详情
	 *
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Menu detail(@PathVariable(value = "id") String id) {
		return menuService.detail(id);
	}

	/**
	 * 添加
	 *
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Menu add(@RequestBody Menu menu) {
		return menuService.saveMenu(menu);
	}

	/**
	 * 更新
	 *
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Menu update(@RequestBody Menu form) {
		return menuService.saveMenu(form);
	}

	/**
	 * 删除
	 *
	 * @return
	 */
	@RequestMapping(value = "/deleteByCode/{code}", method = RequestMethod.POST)
	public void deleteByCode(@PathVariable(value = "code") String code) {
		menuService.deleteByCode(code);
	}

	/**
	 * 根据用户ID获取用户对应的菜单
	 *
	 * @return
	 */
	@RequestMapping(value = "/findByUserId/{userId}", method = RequestMethod.POST)
	public List<Menu> findByUserId(@PathVariable("userId") String userId, @RequestParam(value = "groupIds") List<String> groupIds) {
		return menuService.findByUserId(userId, groupIds);
	}
}
  

