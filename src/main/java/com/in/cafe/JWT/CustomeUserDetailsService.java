package com.in.cafe.JWT;

import com.in.cafe.Model.User;
import com.in.cafe.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;


    private com.in.cafe.Model.User userDetails;

    public CustomeUserDetailsService(UserDao userDao){
        this.userDao =userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername");
        userDetails = userDao.findByEmailId(username);
        if (!Objects.isNull(userDetails)) {
            return new org.springframework.security.core.userdetails.User(userDetails.getEmail()
                    , userDetails.getPassword(), new ArrayList<>());
        } else{
            throw new UsernameNotFoundException("User Not Found");
    }

    }

    public User getUserDetails(){
        return  userDetails;
    }
}
