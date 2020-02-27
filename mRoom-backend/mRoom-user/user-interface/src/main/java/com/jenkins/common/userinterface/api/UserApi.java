package com.jenkins.common.userinterface.api;


import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.userinterface.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserApi {

    @GetMapping("query")
    public User queryUser(@RequestParam("email") String email, @RequestParam("password") String password);

    @GetMapping("")
    public List<User> selectAllUsers();

    @PostMapping("/registry")
    public ResultVo registry(@RequestBody User user);

    @PutMapping("")
    public int updateByEmail(@RequestBody User user);

    @PutMapping("activate")
    public int activateUser(@RequestParam("email") String email);

    @DeleteMapping("/{email}")
    public int deleteByEmail(@PathVariable("email") String email);

    @GetMapping("/getRole/{id}")
    public String getRoleName(@PathVariable("id") int id);

    @GetMapping("/getUserRoleId/{user_id}")
    public List<Integer> getUserRoleId(@PathVariable("user_id") int user_id);


//    @GetMapping("getSalt")
//    public String getSalt(@RequestParam("email") String email);
}
