package com.in.cafe.restImplementation;

import com.in.cafe.constants.CafeConstant;
import com.in.cafe.rest.UserRest;
import com.in.cafe.service.UserService;
import com.in.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImplementation implements UserRest {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.Something_Went_Wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
