package com.talentpath.budgetmanager.security;

import com.talentpath.budgetmanager.daos.UserRepository;
import com.talentpath.budgetmanager.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User matching = userRepo
                .findByUsername(username)
                .orElseThrow(
                        ()-> new UsernameNotFoundException("Could not find user with username: " + username));
        return new UserDetailImpl(matching);
    }
}
