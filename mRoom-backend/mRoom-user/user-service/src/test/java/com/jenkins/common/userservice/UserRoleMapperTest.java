package com.jenkins.common.userservice;

import com.jenkins.common.userinterface.entity.UserRole;
import com.jenkins.common.userservice.mapper.UserRoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRoleMapperTest {


    @Autowired
    UserRoleMapper userRoleMapper;


    @org.junit.Test
    public void testAddUserRole(){
        UserRole userRole = new UserRole();
        userRole.setRole_id(4);
        userRole.setUser_id(2);
        int i = userRoleMapper.insertUserRole(userRole);
        System.out.println(i);
    }

    @org.junit.Test
    public void testGetAllUserRoles(){
        List<UserRole> userRoles = userRoleMapper.getAllUserRole();
        System.out.println(userRoles);
    }

    @org.junit.Test
    public void testGetUserRole()
    {
        List<Integer> userRoles = userRoleMapper.getUserRoles(1);
        System.out.println(userRoles);
    }

    @Test
    public void testDeleteUserRole(){
        UserRole userRole = new UserRole();
        userRole.setUser_id(2);
        userRole.setRole_id(4);
        int i = userRoleMapper.deleteUserRoleById(userRole);

    }

}
