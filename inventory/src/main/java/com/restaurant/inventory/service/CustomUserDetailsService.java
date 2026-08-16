package com.restaurant.inventory.service;

import java.util.ArrayList;
import java.util.List;


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.inventory.model.Permission;
import com.restaurant.inventory.model.Roles;
import com.restaurant.inventory.model.User;
import com.restaurant.inventory.repository.UserRepository;

@Service
@Transactional

public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
       
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        List<GrantedAuthority> authorities = new ArrayList<>();

        Roles role = user.getRole();
        if(role != null){
            for(Permission permission : role.getPermissions()){
                authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
            
        }
    }

        return new org.springframework.security.core.userdetails.User(
            user.getUserName(),
            user.getPassword(),
            authorities
        );
    }


}
