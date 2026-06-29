package com.smc.smccloud.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.smc.smccloud.model.RegionBeanTree;

/**
 * Tree
 */
public  class TreeUtils {
    //把一个List转成树
    public static List<RegionBeanTree> buildTree(List<RegionBeanTree> list, String classCode){
        List<RegionBeanTree> tree=new ArrayList<>();
        for(RegionBeanTree node:list){
            if(Objects.equals(node.getClassCode(),classCode)){
                tree.add(findChild(node,list));
            }
        }
        return tree;
    }

    public static RegionBeanTree findChild(RegionBeanTree node, List<RegionBeanTree> list){
        for(RegionBeanTree n:list){
            if(Objects.equals(n.getClassCode(),node.getCode())){
                if(node.getChildren() == null){
                    node.setChildren(new ArrayList<RegionBeanTree>());
                }
                node.getChildren().add(findChild(n,list));
            }
        }
        return node;
    }
}
