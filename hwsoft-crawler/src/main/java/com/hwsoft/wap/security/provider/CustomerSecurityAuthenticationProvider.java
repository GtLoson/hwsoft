package com.hwsoft.wap.security.provider;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomerSecurityAuthenticationProvider extends
        DaoAuthenticationProvider {

    private PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
//		super.additionalAuthenticationChecks(userDetails, authentication);
        Object salt = null;
        String presentedPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
//            logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
