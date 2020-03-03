package com.jenkins.common.userservice.controller;


import com.jenkins.common.authinterface.model.UserInfo;
import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.userinterface.entity.User;
import com.jenkins.common.userservice.client.AuthClient;
import com.jenkins.common.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private HttpServletRequest request;

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


    /**
     * TODO
     * @param token
     * @return
     */
    @GetMapping("")
    public ResultVo selectAllUsers(@RequestHeader("access-token") String token) {
        ResultVo resultVo = authClient.verifyToken(token);
        UserInfo userInfo = (UserInfo) resultVo.getData();
        List<String> roles = userInfo.getRoles();
        if(roles.size()<2)
        {
            return ResultVo.error("You're not authorized!");
        }
        List<User> users = userService.selectAllUsers();
        return ResultVo.ok("Select successful!",users);
    }

//    @GetMapping("/testverify")
//    public ResultVo testverify(@RequestHeader("access-token") String token) {
//        ResultVo resultVo = authClient.verifyToken(token);
//        return resultVo;
//    }
//
//    @GetMapping("/testrefresh")
//    public ResultVo testrefresh(@RequestHeader("access-token") String token)
//    {
//        ResultVo resultVo = authClient.refreshToken(token);
//        return resultVo;
//    }

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

    /**
     * TODO
     * @param email
     * @param password
     * @param newPassword
     * @return
     */
    @PutMapping("/changePassword")
    public ResultVo changePassword(@RequestParam("email") String email,
                                   @RequestParam("oldPassword") String password,
                                   @RequestParam("newPassword") String newPassword)
    {
        return null;
    }


    @PutMapping("activate")
    public ResultVo activateUser(@RequestParam("token") String token){
        int code = userService.activateUser(token);
        if(code !=1)
        {
            return ResultVo.error(18001,"Activation failed! Please try to get a new activation email!");
        }
        return ResultVo.ok("Activation success!");
    }

    @DeleteMapping("/{email}")
    public int deleteByEmail(@PathVariable("email") String email) {
        return userService.deleteByEmail(email);
    }

    @PostMapping("check/{email}")
    public ResultVo checkEmail(@PathVariable("email") String email)
    {
        if(userService.checkEmail(email))
        {
            return ResultVo.ok("You can use this email!");
        }
        else {
            return ResultVo.error("This email has been registered!");
        }
    }

}
