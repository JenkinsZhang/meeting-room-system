package com.jenkins.common.userservice.service;


import com.jenkins.common.userinterface.entity.Role;
import com.jenkins.common.userservice.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public int addRole(Role role)
    {
        return roleMapper.insertRole(role);
    }

    public Role getRole(int id)
    {
        String a = new String("123123");
        return roleMapper.getRoleById(id);
    }
}
