package com.jenkins.common.authservice.client;


import com.jenkins.common.userinterface.api.UserApi;
import com.jenkins.common.userinterface.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "user-service")
public interface UserClient {

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
