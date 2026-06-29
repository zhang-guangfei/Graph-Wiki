package com.smc.smccloud.model.Organ;

import com.smc.smccloud.Model.DeptTreeNode;
import com.smc.smccloud.core.utils.PublicUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeptTreeUtil {
    /**
     * 转化树形结构
     *  1. 把list转成map  pid作为key，List<TreeNode>作为value
     * 2、递归转化Tree结构
     * @param list
     * @return
     */
    public static List<DeptTreeNode> listToTree(List<DeptTreeNode> list) {
        Map<String, List<DeptTreeNode>> map = new HashMap<String, List<DeptTreeNode>>();
        // list 转成 map
        for (DeptTreeNode tn : list) {
            // 当pid为null 时 手动进行赋值为404 避免getPid空指针
            if (PublicUtil.isEmpty(tn.getPid())) {
                tn.setPid("404");
            }
            String key = tn.getPid();
            if(map.containsKey(key)) {
                map.get(key).add(tn);
            } else {
                List<DeptTreeNode> mapValue = new ArrayList<DeptTreeNode>();
                mapValue.add(tn);
                map.put(tn.getPid(), mapValue);
            }
        }
        // 404 代表根节点
        List<DeptTreeNode> tree = map.get("200000");
        recursionToTree(tree, map);
        return tree;
    }

    /**
     * 递归转化Tree结构
     * @param list，map
     * @param map
     */
    public static void recursionToTree(List<DeptTreeNode> list, Map<String, List<DeptTreeNode>> map){
        for(DeptTreeNode tn : list){
            String key = tn.getId();
            if(map.containsKey(key)) {
                List<DeptTreeNode> children = map.get(key);
                tn.setChildren(children);
                recursionToTree(children, map);
            }
        }
    }



}
