package com.smc.smccloud;

import com.smc.smccloud.mapper.OpsInventoryLogBakMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
@Slf4j
public class OpsInventoryLogBakTest extends OpsOrderApplicationTest{
    @Resource
    private OpsInventoryLogBakMapper opsInventoryLogBakMapper;
    @Test
    public void test1(){
        Long id = opsInventoryLogBakMapper.getOutInventoryIdByOrderFromBak("13119203", 2, "CKZ3N63TF-90DPF-X2742A");
        log.info("id:{}",id);
    }
}
