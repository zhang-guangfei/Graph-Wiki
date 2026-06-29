package com.sales.ops.utils;

import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.dto.util.CreateInfoDto;
import com.sales.ops.enums.SendStatusEnum;
import com.sales.ops.enums.WmOrderTaskEnum;

import java.util.Arrays;
import java.util.List;

/**
 * @author C12961
 * @date 2023/3/2 9:27
 */
public class WmTaskFactory {


    public static List<OpsWmOrderTask> TasksForRoAndBarCode(String roId, SendStatusEnum flag, CreateInfoDto createInfo) {
        OpsWmOrderTask roTask = RoTask(roId, flag, createInfo);
        OpsWmOrderTask barTask = BarTask(roId, flag, createInfo);
        return Arrays.asList(roTask, barTask);
    }


    public static List<OpsWmOrderTask> TasksForDoAndRo(String doId, String roId, SendStatusEnum flag, CreateInfoDto createInfo) {
        OpsWmOrderTask doTask = DoTask(doId, flag, createInfo);
        OpsWmOrderTask roTask = RoTask(roId, flag, createInfo);
        return Arrays.asList(doTask, roTask);
    }


    //创建BarCodeTask
    public static OpsWmOrderTask BarTask(String roId, SendStatusEnum flag, CreateInfoDto createInfo) {
        return createWmTask(roId, WmOrderTaskEnum.RO, WmOrderTaskEnum.BARCODE, flag, createInfo);
    }

    //创建RoTask
    public static OpsWmOrderTask RoTask(String roId, SendStatusEnum flag, CreateInfoDto createInfo) {
        return createWmTask(roId, WmOrderTaskEnum.RO, WmOrderTaskEnum.ORDER, flag, createInfo);
    }

    //创建DoTask
    public static OpsWmOrderTask DoTask(String doId, SendStatusEnum flag, CreateInfoDto createInfo) {
        return createWmTask(doId, WmOrderTaskEnum.DO, WmOrderTaskEnum.ORDER, flag, createInfo);
    }


    public static OpsWmOrderTask createWmTask(String wmOrderId, WmOrderTaskEnum orderType, WmOrderTaskEnum taskType, SendStatusEnum flag, CreateInfoDto createInfo) {
        OpsWmOrderTask task = new OpsWmOrderTask();
        task.setWmOrderId(wmOrderId);
        task.setWmOrderType(orderType.getType());
        task.setTaskType(taskType.getType());
        task.setFlag(Integer.valueOf(flag.getType()));
        task.setCreator(createInfo.getCreateUser());
        task.setCreTime(createInfo.getCreateTime());
        return task;
    }

}
