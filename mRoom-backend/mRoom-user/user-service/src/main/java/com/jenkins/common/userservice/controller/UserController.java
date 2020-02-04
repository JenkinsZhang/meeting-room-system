package com.jenkins.common.userservice.controller;


import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.userinterface.model.UserRole;
import com.jenkins.common.userservice.service.RoleService;
import com.jenkins.common.userservice.service.UserRoleService;
import com.jenkins.common.userservice.service.UserService;
import com.jenkins.common.userinterface.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("query")
    public User queryUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        String salt = userService.getSaltByEmail(email);
        try {
            password = BCrypt.hashpw(password, salt);
            return userService.queryUser(email, password);
        } catch (Exception e) {
            return null;
        }
    }
//
//    @GetMapping("getSalt")
//    public String getSalt(@RequestParam("email") String email) {
//        String saltByEmail = userService.getSaltByEmail(email);
//        return saltByEmail;
//    }

    @GetMapping("")
    public List<User> selectAllUsers() {
        return userService.selectAllUsers();
    }

    @PostMapping("/registry")
    public ResultVo registry(@RequestBody User user) {
        int addCode = userService.addUser(user);
        if(addCode == -1)
        {
            return ResultVo.error(15001,"registry failed, The email has been taken!");
        }
        if(addCode == -2)
        {
            return ResultVo.error(15002,"System error, please contact administrator!");
        }
        int emailCode = userService.sendVerificationEmail(user);
        if(emailCode != 1)
        {
            return ResultVo.error(17001,"Failed to send verification email,please contact administrator!");
        }
        return ResultVo.ok("Registry success! Return to login page!");
    }

    @PutMapping("")
    public int updateByEmail(@RequestBody User user) {
        return userService.updateByEmail(user);
    }

    @PutMapping("activate")
    public int activateUser(@RequestParam("email") String email){
        return userService.activateUser(email);
    }

    @DeleteMapping("/{email}")
    public int deleteByEmail(@PathVariable("email") String email) {
        return userService.deleteByEmail(email);
    }


}
