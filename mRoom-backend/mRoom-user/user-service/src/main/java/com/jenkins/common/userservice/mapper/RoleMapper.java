package com.jenkins.common.userservice.mapper;

import com.jenkins.common.userinterface.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper {

    int insertRole(@Param("role") Role role);

    int deleteById(@Param("id") int id);

    int updateById(@Param("role") Role role);

    Role getRoleById(@Param("id") int id );

    List<Role> getAllRole();
}
