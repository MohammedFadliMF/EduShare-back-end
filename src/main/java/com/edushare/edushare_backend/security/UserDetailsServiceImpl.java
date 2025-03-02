package com.edushare.edushare_backend.security;

import com.edushare.edushare_backend.entities.User;
import com.edushare.edushare_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("No user named " + username);
        }else {
            return new  UserDetailsImpl(user.get());
        }
    }
}
