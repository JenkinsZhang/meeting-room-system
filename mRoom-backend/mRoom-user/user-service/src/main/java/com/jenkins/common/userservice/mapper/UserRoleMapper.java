package com.jenkins.common.userservice.mapper;

import com.jenkins.common.userinterface.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRoleMapper {


    int insertUserRole(@Param("userRole")UserRole userRole);

    int deleteUserRoleById(@Param("userRole") UserRole userRole);

    int updateById(@Param("userRole") UserRole userRole);

    List<Integer> getUserRoles(@Param("user_id") int user_id);

    List<UserRole> getAllUserRole();
}
