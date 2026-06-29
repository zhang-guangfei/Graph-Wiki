package com.smc.smccloud.controller;

import com.smc.smccloud.model.login.ResourceDO;
import com.smc.smccloud.service.resource.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    /**
     * 获取全部节点
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<ResourceDO> findAll() {
        return resourceService.findAll();
    }

    /**
     * 添加
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResourceDO add(@RequestBody ResourceDO resource) {
        return resourceService.saveResource(resource);
    }

    /**
     * 更新
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResourceDO update(@RequestBody ResourceDO form) {
        return resourceService.saveResource(form);
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/deleteByCode/{code}", method = RequestMethod.POST)
    public void deleteByCode(@PathVariable(value = "code") String code) {
        resourceService.deleteByCode(code);
    }
}
