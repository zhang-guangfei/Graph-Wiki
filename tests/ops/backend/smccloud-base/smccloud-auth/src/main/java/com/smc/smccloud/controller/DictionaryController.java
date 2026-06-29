package com.smc.smccloud.controller;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.dict.Dictionary;
import com.smc.smccloud.service.dict.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value="/queryAll")
    private List<Dictionary> queryAll() {
        return dictionaryService.queryAll();
    }

    @RequestMapping(value="/addRecord")
    private Dictionary addRecord(@RequestBody Dictionary addData) {
        return dictionaryService.addRecord(addData);
    }

    @RequestMapping(value="/findByPid")
    public List<Dictionary> findAllType(String pid) {
        return dictionaryService.getChildsByPid(pid);
    }

    @GetMapping(value="/findChildsByPid")
    public ResultVo<List<Dictionary>> findChildsByPid(@RequestParam("pid") String pid) {
        return dictionaryService.findChildsByPid(pid);
    }

}
