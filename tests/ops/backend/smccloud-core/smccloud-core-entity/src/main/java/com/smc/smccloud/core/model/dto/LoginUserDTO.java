
package com.smc.smccloud.core.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserDTO implements Serializable {

  public LoginUserDTO() {

  }

  public LoginUserDTO(String userName) {
    this.userName = userName;
  }

  public LoginUserDTO(String userName, String userNo, String realName) {
    this.userName = userName;
    this.userNo = userNo;
    this.realName = realName;
  }

  /**
   *
   */
  private static final long serialVersionUID = 16738928330129286L;
  /** 用户名 */
  private String userName;
  /**
   * 内部员工为工号 或客户为客户代码
   */
  private String userNo;
  /**
   * 姓名
   */
  private String realName;

  private String token;

  /**
   * 部门代码
   */
  private String deptNo;

  /**
   * 角色
   */
  private String[] roles;

}