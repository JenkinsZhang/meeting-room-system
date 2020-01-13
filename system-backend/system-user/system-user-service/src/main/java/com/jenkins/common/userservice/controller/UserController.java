package com.jenkins.common.userservice.controller;


import com.jenkins.common.userinterface.model.User;
import com.jenkins.common.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("query")
    public User queryUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userService.queryUser(email, password);
    }

    @GetMapping("")
    public List<User> selectAllUsers() {
        return userService.selectAllUsers();
    }

    @PostMapping("/registry")
    public int insert(@RequestBody User user) {
        return userService.insert(user);
    }

    @PutMapping("")
    public int updateByEmail(@RequestBody User user) {
        return userService.updateByEmail(user);
    }

    @DeleteMapping("/{email}")
    public int deleteByEmail(@PathVariable("email") String email) {
        return userService.deleteByEmail(email);
    }
}
