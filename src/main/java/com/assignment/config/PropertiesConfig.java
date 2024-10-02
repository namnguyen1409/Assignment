package com.assignment.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {
    public final String APP_TITLE = "Assignment";
    public final String APP_LOGO = "demo-logo.png";
    public final String COMPANY_NAME = "Demo Company";
    public final String COMPANY_ADDRESS = "123 Street, City, Country";
    public final String JWT_SECRET = "DeDuB9695l2RiYlBSZo1MxiA/bRPGcHmMkLfyOFggs8=";
    public final int JWT_EXPIRATION = 24 * 60 * 60; // 1 day
    public final int REFRESH_TOKEN_EXPIRATION = 4 * 30 * 24 * 60 *60; // 4 months
    public final String GOOGLE_OAUTH2_URL = "/login/oauth2/code/google";
    public final String SECRET_KEY = "mpROhWzzhMzdV3Yi7uViTScmRDUKROjHTbOppzf8Gck=";
    public final String COOKIE_DEVICE_ID = "device_id";

}
