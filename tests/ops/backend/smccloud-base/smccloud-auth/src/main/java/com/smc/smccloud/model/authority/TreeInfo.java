package com.smc.smccloud.model.authority;

import com.smc.smccloud.model.login.TreeImpl;
import lombok.Data;

import java.io.Serializable;

@Data
public class TreeInfo extends TreeImpl<TreeInfo> implements Serializable
{
    private static final long serialVersionUID = 2692159646212282815L;
    private String level;
    private String k3Code;
}
