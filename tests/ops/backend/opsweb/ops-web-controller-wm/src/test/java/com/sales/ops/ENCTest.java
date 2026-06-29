package com.sales.ops;

import cn.hutool.json.JSONUtil;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.service.ba.OpsWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author C12961
 * @date 2023/1/18 11:37
 */
@Slf4j
public class ENCTest extends BaseTest {

    @Autowired
    private StringEncryptor encryptor;

    @Autowired
    private ApplicationContext context;


    @Autowired
    private OpsWarehouseService opsWarehouseService;

    @Test
    public void encode() {
        Environment env = context.getBean(Environment.class);
        String username = env.getProperty("spring.datasource.username");
        String password = "123456";//env.getProperty("spring.datasource.password");
        String encrypt = encryptor.encrypt(password);
        log.info("数据库用户名：{}", username);
        log.info("未加密的密码：{}", password);
        log.info("加密的密码：ENC({})", encrypt);
        //123456
        //ENC(JRnzkDlIu5fUTxup3KwxUAiGtb/uZDoRMyBBt3GmuO3awcYpZU+hsVtBVdGfpuCo)

        //ffw3t365gerjthR656hgf
        //ENC(HXeMLQch19rm79KMIlNdkInRonJkQTeyChdWU5c4e4zvdd/TGC9B4LRvClp1+mb98xzIvANMg2Z7bbPJvL6uUw==)
    }


    @Test
    public void decode() {
        Environment env = context.getBean(Environment.class);
        String username = env.getProperty("spring.datasource.username");
        log.info("数据库用户名：{}", username);
        String encrypt = env.getProperty("spring.datasource.password");
        log.info("解密的密码：{}", encrypt);
        OpsWarehouse warehouse = opsWarehouseService.findById("KBJ");
        log.info(JSONUtil.toJsonPrettyStr(warehouse));
    }




}
