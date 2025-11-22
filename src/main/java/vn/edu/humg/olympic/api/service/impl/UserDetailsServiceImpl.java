package vn.edu.humg.olympic.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.humg.olympic.api.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                                 .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

        return User.withUsername(user.getEmail())
                   .password(user.getPasswordHash())
                   .authorities(user.getRole()
                                    .getAuthority())
                   .build();
    }
}
