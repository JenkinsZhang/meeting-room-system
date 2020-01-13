package com.jenkins.common.userinterface.api;


import com.jenkins.common.userinterface.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserApi {

    @GetMapping("query")
    public User queryUser(@RequestParam("email") String email, @RequestParam("password") String password);

    @GetMapping("user")
    public List<User> selectAllUsers();

    @PostMapping("user")
    public int insert(@RequestBody User user);

    @PutMapping("user")
    public int updateByEmail(@RequestBody User user);

    @DeleteMapping("user/{email}")
    public int deleteByEmail(@PathVariable("email") String email);
}
