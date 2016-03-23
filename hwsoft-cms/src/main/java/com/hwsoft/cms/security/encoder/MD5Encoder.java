package com.hwsoft.cms.security.encoder;

import com.hwsoft.util.password.MD5Util;
import org.springframework.security.authentication.encoding.PasswordEncoder;


public class MD5Encoder implements PasswordEncoder {

    @Override
    public String encodePassword(String rawPass, Object salt) {
        return MD5Util.digest(rawPass);
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        return encPass.equals(encodePassword(rawPass, salt));
    }
}
