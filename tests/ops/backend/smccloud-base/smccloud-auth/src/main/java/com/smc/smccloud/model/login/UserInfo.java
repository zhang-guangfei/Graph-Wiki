package com.smc.smccloud.model.login;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserInfo implements Serializable
{
    private static final long serialVersionUID = -4578854084883929304L;
    private RemoteUser remoteUser;
    private Set<String> roles = new HashSet();
    private Set<String> authorityCodes = new HashSet();

    public UserInfo(RemoteUser remoteUser, Set<String> roles) {
        this.remoteUser = remoteUser;
        this.roles = roles;
    }
}
