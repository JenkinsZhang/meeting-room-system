package com.jenkins.common.userservice;

import com.jenkins.common.userinterface.model.Role;
import com.jenkins.common.userservice.mapper.RoleMapper;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleMapperTest {
    @Autowired
    RoleMapper roleMapper;

    @Test
    public void testInsert(){
        Role role = new Role();
        role.setRole_name("test");
        role.setRole_nameZh("这是测试");
        roleMapper.insertRole(role);
    }

    @Test
    public void testDelete(){
        roleMapper.deleteById(5);
    }

    @Test
    public void selectRoles(){
        List<Role> allRole = roleMapper.getAllRole();
        System.out.println(allRole);
    }

    @Test
    public void selectRoleById(){
        Role roleById = roleMapper.getRoleById(1);
        System.out.println(roleById);

    }
}
