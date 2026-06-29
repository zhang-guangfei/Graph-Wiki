package com.smc.smccloud.core.model.constants;

/**
 * GlobalConstant
 */
public class GlobalConstant {

  public static final String TOKEN = "Authorization";
  public static final String GRANT_TYPE = "grant_type";
  public static final String CLIENT_ID = "client_id";
  public static final String CLIENT_SECRET = "client_secret";
  public static final String SERVICE_NAME = "service_name";


  public static class RedisKey {
    public static final String TOEKN_KEY = "tk:";
  }

  public static class Sys {

    public static final String LOGIN_USER_DTO = "CURRENT_USER_DTO";
  }

  public static class GrantType {

    public static final String CLIANT_CREDENTIALS = "client_credentials";

    public static final String PASSWORD = "password";

  }
}