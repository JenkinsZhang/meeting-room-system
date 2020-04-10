package com.jenkins.common.userservice.service;

import com.jenkins.common.userinterface.entity.UserRole;
import com.jenkins.common.userservice.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    public int addUserRole(UserRole userRole){
        return userRoleMapper.insertUserRole(userRole);
    }

    public int alterUserRole(int userId,int roleId)
    {
        UserRole userRole = new UserRole();
        userRole.setUser_id(userId);
        userRole.setRole_id(roleId);
        return userRoleMapper.updateById(userRole);
    }

    public List<Integer> getUserRole(int user_id)
    {
        List<Integer> userRoles = userRoleMapper.getUserRoles(user_id);
        return userRoles;
    }
}
