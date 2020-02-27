package com.jenkins.common.authservice.mapper;

import com.jenkins.common.authinterface.entity.AuthUser;
import io.lettuce.core.dynamic.annotation.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/2/27
 * @description
 */

@Component
@Mapper
public interface AuthUserMapper {

    AuthUser queryUser(@Param("email") String email,@Param("password") String password);

    String getSaltByEmail(@Param("email") String email);

    List<Integer> getUserRoles(@Param("user_id") int user_id);

    String getRoleName(@Param("role_id") int role_id);
}
