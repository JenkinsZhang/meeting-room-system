package com.jenkins.common.userservice.controller;


import com.alibaba.fastjson.JSON;
import com.jenkins.common.authinterface.model.UserInfo;
import com.jenkins.common.components.model.AccountVo;
import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.userinterface.entity.User;
import com.jenkins.common.userinterface.model.Account;
import com.jenkins.common.userservice.client.AuthClient;
import com.jenkins.common.userservice.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author jenkinszhang
 *
 */
@RestController
public class UserController {

    private UserService userService;

    private AuthClient authClient;

    @Autowired
    public UserController(UserService userService, AuthClient authClient) {
        this.userService = userService;
        this.authClient = authClient;
    }

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

    @GetMapping("/general")
    public ResultVo getGeneralUsers()
    {
        return ResultVo.ok("OK",userService.getGeneralUsers());
    }

    @GetMapping("admin/accounts")
    public ResultVo selectAccountsByPage(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         User user) {


        AccountVo accounts = userService.getAccounts(page, size, user);
        System.out.println(accounts);
        return ResultVo.ok("OK",accounts);
    }

    @PostMapping("admin/addAccount")
    public ResultVo addAccount(@RequestBody  User user)
    {
        int code = userService.addUser(user);
        return code == 1 ? ResultVo.ok("User added!") : ResultVo.error("Failed!");
    }

    @DeleteMapping("admin/{email}")
    public ResultVo deleteByEmail(@PathVariable("email") String email) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("access-token");
        int code = userService.deleteUser(email,token);
        return code == 1 ? ResultVo.ok("Account closed!") : ResultVo.error("Failed! Access denied!");
    }

    @PutMapping("admin/{email}")
    public ResultVo activateByEmail(@PathVariable("email") String email)
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("access-token");
        int i = userService.activateByEmail(email,token);
        return i == 1? ResultVo.ok("User activated") : ResultVo.error("Failed! Access denied!");
    }

    @PutMapping("admin")
    public ResultVo editUser(User user)
    {
        int code = userService.updateUser(user);
        if(code == -1)
        {
            return ResultVo.error("Email address already exists");
        }
        if(code == 0)
        {
            return ResultVo.error("Failed! Please check all fields!");
        }
        if(code == 1)
        {
            return ResultVo.ok("Success!");
        }
        return ResultVo.error("Unexpected Error!");
    }
    /**
     * TODO Email Validation
     * @param user
     * @return
     */
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


    /**
     *
     * @param email
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping("password/changePassword")
    public ResultVo changePassword(@RequestParam("email") String email,
                                   @RequestParam("oldPassword") String oldPassword,
                                   @RequestParam("newPassword") String newPassword)
    {
        int i = userService.changePassword(email, oldPassword, newPassword);
        return i == 1? ResultVo.ok("OK!") : ResultVo.error("Failed!");
    }

    @PostMapping("password/validate")
    public ResultVo oldPasswordValidation(@RequestParam("email") String email,
                                          @RequestParam("password") String password)
    {
        String salt = userService.getSaltByEmail(email);
        try {
            password = BCrypt.hashpw(password, salt);
            User user = userService.queryUser(email, password);
            if(user != null)
            {
                return ResultVo.ok("OK!");
            }
            else
            {
                return ResultVo.error("Failed!");
            }
        } catch (Exception e) {
            return ResultVo.error("Error!");
        }
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

    @GetMapping("/email")
    public String getEmail(@RequestParam("id") int id)
    {
        return userService.getEmail(id);
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

    @GetMapping("phone/{email}")
    public ResultVo getPhone(@PathVariable("email") String email,
                             @RequestHeader("access-token") String token)
    {
        ResultVo resultVo = authClient.verifyToken(token);
        if(resultVo.getCode() != 200)
        {
            return ResultVo.error(401,"Invalid token!");
        }
        Object data = resultVo.getData();
        UserInfo userInfo = JSON.parseObject(JSON.toJSONString(data), UserInfo.class);
        if(! userInfo.getEmail().equals(email))
        {
            return ResultVo.error(401,"The token information doesn't match! ");
        }
        else
        {
            String phone = userService.getPhone(email);
            return ResultVo.ok("OK",phone);
        }
    }

    @PutMapping("/username/{email}/{username}")
    public ResultVo changeUsername(@PathVariable("email") String email,
                                   @PathVariable("username") String username) {
        int i = userService.changeUsername(email, username);
        return i == 0
                ? ResultVo.error("Cannot find user!")
                : ResultVo.ok("OK!");
    }

    @PutMapping("/phone/{email}/{phone}")
    public ResultVo changePhone(@PathVariable("email") String email,
                                @PathVariable("phone") String phone) {
        int i = userService.changePhone(email,phone);
        return i == 0
                ? ResultVo.error("Cannot find user!")
                : ResultVo.ok("OK!");
    }

    @PostMapping("/password/forget/sendCode")
    public ResultVo sendEmailCode(@RequestParam("email") String email)
    {
        int code = userService.sendVerificationCode(email);
        return code == 0
                ? ResultVo.error("Email Server error!")
                : ResultVo.ok("OK!");

    }

    @PostMapping("/password/forget/verifyCode")
    public ResultVo verifyCode(@RequestParam("code") String code, @RequestParam("email") String email)
    {
        boolean b = userService.verifyCode(email, code);
        return b ? ResultVo.ok("OK!") : ResultVo.error("Failed!");
    }

    @PostMapping("/password/forget/forgetPassword")
    public ResultVo forgetPassword(@RequestParam("email") String email, @RequestParam("newPassword") String newPassword)
    {
        int code = userService.forgetPassword(email, newPassword);
        return code == 0
                ? ResultVo.error("Change Success!")
                : ResultVo.ok("Failed!");
    }
}
