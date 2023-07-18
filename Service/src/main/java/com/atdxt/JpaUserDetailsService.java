package com.atdxt;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserEncryptRepository userEncryptRepository;

    public JpaUserDetailsService(UserEncryptRepository userEncryptRepository) {
        this.userEncryptRepository = userEncryptRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEncrypt userEncrypt = userEncryptRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String password = userEncrypt.getPassword(); // Get the password from UserEncrypt entity

        return User.withUsername(username)
                .password(password)
                .build();
    }
}
