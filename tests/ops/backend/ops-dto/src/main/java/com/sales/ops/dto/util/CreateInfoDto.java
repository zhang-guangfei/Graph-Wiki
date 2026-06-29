package com.sales.ops.dto.util;

import java.util.Date;

/**
 * @author C12961
 * @date 2023/2/17 9:28
 */
public class CreateInfoDto {

    private Date createTime;
    private UserDto createUser;


    public CreateInfoDto(Date createTime, UserDto creator) {
        this.createTime = createTime;
        this.createUser = creator;
    }

    public CreateInfoDto(UserDto creator) {
        this.createTime = new Date();
        this.createUser = creator;
    }

    public CreateInfoDto(Date createTime, String creator) {
        this.createTime = createTime;
        this.createUser = new UserDto().setUserName(creator);
    }

    public CreateInfoDto(String creator) {
        this.createTime = new Date();
        this.createUser = new UserDto().setUserName(creator);
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser.getUserName();
    }

    public void setCreateUser(UserDto createUser) {
        this.createUser = createUser;
    }


}
