package com.smc.smccloud.model.login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserSecurity extends User {
    /**
     *
     */
    private static final long serialVersionUID = -5113547361322169478L;

    private Set<String> resourcesUrls = new HashSet<String>();

    private Set<String> authorityCodes = new HashSet<String>();

    private String psnName;

    private String deptNo;

    public Set<String> getResourcesUrls() {
        return resourcesUrls;
    }

    public void setResourcesUrls(Set<String> resourcesUrls) {
        this.resourcesUrls = resourcesUrls;
    }

    public Set<String> getAuthorityCodes() {
        return authorityCodes;
    }

    public void setAuthorityCodes(Set<String> authorityCodes) {
        this.authorityCodes = authorityCodes;
    }

    public UserSecurity(String username, String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public UserSecurity(String username, String password, boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getPsnName() {
        return psnName;
    }

    public void setPsnName(String psnName) {
        this.psnName = psnName;
    }
}
