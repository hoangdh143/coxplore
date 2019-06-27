package com.coxplore.service;


import com.coxplore.model.User;
import com.coxplore.model.UserSecurity;
import com.coxplore.repository.AuthenticationTokenRepository;
import com.coxplore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private AuthenticationTokenRepository authenticationTokenRepository;

    public UserDetailsServiceImpl(@Qualifier("userRepository") UserRepository applicationUserRepository, AuthenticationTokenRepository authenticationTokenRepository) {
        this.userRepository = applicationUserRepository;
        this.authenticationTokenRepository = authenticationTokenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        boolean enabled = user.getActivated() == 0 ? false : true;

        return new UserSecurity(email, user.getPassword(), user, enabled, Collections.emptyList());
    }
}
