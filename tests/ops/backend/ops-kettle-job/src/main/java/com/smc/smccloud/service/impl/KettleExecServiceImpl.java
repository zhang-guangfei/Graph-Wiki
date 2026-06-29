package com.smc.smccloud.service.impl;


import com.smc.smccloud.service.KettleExecService;
import com.smc.smccloud.util.ResultVo;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author lyc
 * @Date 2025/6/25 16:10
 * @Descripton TODO
 */
@Service
public class KettleExecServiceImpl implements KettleExecService {

    @Value("${kettle.transPathPrefix}")
    private String transPathPrefix;


    // 初始化Kettle环境
    @PostConstruct
    public void init() {
        try {
            if (!KettleEnvironment.isInitialized()) {
                // 获取JNDI配置文件的实际路径
//                ClassPathResource jndiResource = new ClassPathResource("jndi");
//                File jndiDir = jndiResource.getFile();
                // 正确设置JNDI配置目录
                System.setProperty("org.osjava.sj.root", transPathPrefix + File.separator + "jndi");
                System.setProperty("org.osjava.sj.delimiter", "/");
                KettleEnvironment.init();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Kettle environment", e);
        }
    }

    public ResultVo<String> execKettleJob(String jobPath) {
        Job job = null;
        try {
            JobMeta jobMeta = new JobMeta(jobPath, null);
            job = new Job(null, jobMeta);
            job.setLogLevel(org.pentaho.di.core.logging.LogLevel.BASIC);

            job.start();
            job.waitUntilFinished();

            if (job.getErrors() > 0) {
                String log = getJobLog(job);
                return ResultVo.failure(jobPath + "作业执行失败: " + log);
            }
        } catch (Exception e) {
            return ResultVo.failure("作业执行失败: " + e.getMessage());
        } finally {
            if (job != null) {
                try {
                    // 新版本使用 dispose()
                    Method disposeMethod = job.getClass().getMethod("dispose");
                    disposeMethod.invoke(job);
                } catch (NoSuchMethodException e) {
                    //try {
                    //    //旧版本使用 cleanup()
                    //    Method cleanupMethod = job.getClass().getMethod("cleanup");
                    //    cleanupMethod.invoke(job);
                    //} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    //    throw new RuntimeException(ex);
                    //}
                } catch (IllegalAccessException | InvocationTargetException e) {
                    //throw new RuntimeException(e);
                }
            }
        }
        return ResultVo.success(jobPath + "作业执行成功");
    }


    @Override
    public ResultVo<String> execKettleTrans(String transPath, Map<String, String> parameters) {
        Trans trans = null;
        try {
            TransMeta transMeta = new TransMeta(transPath);
            // 设置参数到转换元数据
            setParameters(transMeta, parameters);
            // 创建转换实例
            trans = new Trans(transMeta);

            //设置JNDI模式（关键步骤！）
            trans.getTransMeta().setVariable("KETTLE_JNDI_ROOT", "java:comp/env");

            // 设置变量到转换实例
            setVariables(trans, parameters);

            trans.execute(null);
            trans.waitUntilFinished();

            if (trans.getErrors() > 0) {
                String log = getTransLog(trans);
                return ResultVo.failure(transPath + "转换执行失败: " + log);
            }
        } catch (Exception e) {
            return ResultVo.failure("转换执行失败: " + e.getMessage());
        } finally {
            if (trans != null) {
                trans.cleanup();
            }
        }
        return ResultVo.success(transPath + "转换执行成功");
    }


    @Override
    public ResultVo<String> syncTransWithPath(String transName) {
        transName = transName + ".ktr";
        return execKettleTrans(transPathPrefix + File.separator + transName, null);
    }

    @Override
    public ResultVo<String> syncCrmCustomerLimitAll() {

        return execKettleTrans(transPathPrefix + File.separator + "ops_customer_limit.ktr", null);

    }

    @Override
    public ResultVo<String> syncCrmCustomerBaseAll() {
        return execKettleTrans(transPathPrefix + File.separator + "ops_customer_base.ktr", null);
    }

    @Override
    public ResultVo<String> syncProductLimitAll() {
        return execKettleTrans(transPathPrefix + File.separator + "product_limit.ktr", null);
    }


    @Override
    public ResultVo<String> syncWorkDay() {
        return execKettleTrans(transPathPrefix+ File.separator+"work_day_year.ktr",null);
    }

    /**
     * 数据库日常归档任务作业
     * @return
     */

    @Override
    public ResultVo<String> syncArchiveOpsTable_bin() {
        return execKettleJob(transPathPrefix + File.separator + "ops_archive_job" + File.separator + "archive_job_1_bin.kjb");
    }

    @Override
    public ResultVo<String> syncArchiveOpsTable_order() {
        return execKettleJob(transPathPrefix + File.separator + "ops_archive_job" + File.separator + "archive_job_2_order.kjb");
    }

    @Override
    public ResultVo<String> syncArchiveOpsTable_exp() {
        return execKettleJob(transPathPrefix + File.separator + "ops_archive_job" + File.separator + "archive_job_3_exp.kjb");
    }

    @Override
    public ResultVo<String> syncArchiveOpsTable_wm() {
        return execKettleJob(transPathPrefix + File.separator + "ops_archive_job" + File.separator + "archive_job_4_wm.kjb");
    }

    @Override
    public ResultVo<String> syncArchiveOpsTable_event() {
        return execKettleJob(transPathPrefix + File.separator + "ops_archive_job" + File.separator + "archive_job_5_event.kjb");
    }

    @Override
    public ResultVo<String> syncArchiveOpsTable_log() {
        return execKettleJob(transPathPrefix + File.separator + "ops_archive_job" + File.separator + "archive_job_6_log.kjb");
    }

    @Override
    public ResultVo<String> syncArchiveOpsTable_test() {
        return execKettleJob(transPathPrefix + File.separator + "ops_archive_job" + File.separator + "archive_job_0_test.kjb");
    }

    /**
     * psi项目测试时，从生产环境拉取中国订单各表数据
     */
    @Override
    public ResultVo<String> syncPullCnOrderSrcInfo() {
        return  execKettleTrans(transPathPrefix+ File.separator+"pullCNOrderSrcInfo.ktr",null);
    }

    private void setParameters(TransMeta transMeta, Map<String, String> parameters) throws Exception {
        if (parameters == null) {
            return;
        }
        // 设置转换参数（在转换中定义为命名参数）
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            transMeta.setParameterValue(entry.getKey(), entry.getValue());
        }
    }

    private void setVariables(VariableSpace space, Map<String, String> parameters) {
        if (parameters == null) {
            return;
        }
        // 设置环境变量（在转换中通过 ${VAR_NAME} 使用）
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            space.setVariable(entry.getKey(), entry.getValue());
        }
    }

    private String getJobLog(Job job) {
        String logChannelId = job.getLogChannelId();
        return KettleLogStore.getAppender().getBuffer(logChannelId, false).toString();
    }

    private String getTransLog(Trans trans) {
        String logChannelId = trans.getLogChannelId();
        return KettleLogStore.getAppender().getBuffer(logChannelId, false).toString();
    }
}
