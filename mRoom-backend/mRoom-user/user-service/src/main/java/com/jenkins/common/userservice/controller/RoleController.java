package com.jenkins.common.userservice.controller;


import com.jenkins.common.components.model.ResultVo;
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

    @GetMapping("/admin/roles")
    public ResultVo getRoles()
    {
        List<Role> allRoles = roleService.getAllRoles();
        return ResultVo.ok("Success!",allRoles);
    }

    @GetMapping("/getRole/{id}")
    public String getRoleName(@PathVariable("id") int id)
    {
        Role role = roleService.getRole(id);
        return role.getRole_name();
    }

    @GetMapping("/getUserRoleId/{userId}")
    public List<Integer> getUserRoleId(@PathVariable("user_id") int userId)
    {
        List<Integer> userRole = userRoleService.getUserRole(userId);
        return userRole;
    }

    @PostMapping("/addUserRole")
    public int addUserRole(@RequestBody UserRole userRole)
    {
        return userRoleService.addUserRole(userRole);
    }

    @PutMapping("/admin/changeRole/{userId}/{roleId}")
    public ResultVo changeUserRole(@PathVariable("userId") int userId,@PathVariable("roleId") int roleId)
    {
        int i = userRoleService.alterUserRole(userId, roleId);
        return i == 1? ResultVo.ok("Success!") : ResultVo.error("Failed!");
    }

}
