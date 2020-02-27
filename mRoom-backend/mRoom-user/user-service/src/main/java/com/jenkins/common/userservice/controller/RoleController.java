package com.jenkins.common.userservice.controller;


import com.jenkins.common.userinterface.entity.Role;
import com.jenkins.common.userinterface.entity.UserRole;
import com.jenkins.common.userservice.service.RoleService;
import com.jenkins.common.userservice.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/createRole")
    public int createRole(Role role){
        return roleService.addRole(role);
    }

    @GetMapping("/getRole/{id}")
    public String getRoleName(@PathVariable("id") int id)
    {
        Role role = roleService.getRole(id);
        return role.getRole_name();
    }

    @GetMapping("/getUserRoleId/{user_id}")
    public List<Integer> getUserRoleId(@PathVariable("user_id") int user_id)
    {
        List<Integer> userRole = userRoleService.getUserRole(user_id);
        return userRole;
    }

    @GetMapping("/addUserRole")
    public int addUserRole(@RequestBody UserRole userRole)
    {
        return userRoleService.addUserRole(userRole);
    }
}
