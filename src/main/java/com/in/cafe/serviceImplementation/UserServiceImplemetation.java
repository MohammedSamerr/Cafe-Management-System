package com.in.cafe.serviceImplementation;

import com.in.cafe.Model.User;
import com.in.cafe.constants.CafeConstant;
import com.in.cafe.dao.UserDao;
import com.in.cafe.service.UserService;
import com.in.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImplemetation implements UserService {


    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside SignUp{}",requestMap);
        try {
            if (validateSignUpMap(requestMap)){
                User user = userDao.findByEmailId(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userDao.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity("User Saved",HttpStatus.OK);
                }else {
                    return CafeUtils.getResponseEntity("Email Already Exit",HttpStatus.BAD_REQUEST);
                }
            }else {
                return CafeUtils.getResponseEntity(CafeConstant.Invalid_Data, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.Something_Went_Wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String,String> requestMap){
        if (requestMap.containsKey("name")&& requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email")&& requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    private  User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;

    }
}
