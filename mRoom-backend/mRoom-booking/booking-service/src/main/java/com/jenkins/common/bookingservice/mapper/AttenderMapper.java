package com.jenkins.common.bookingservice.mapper;

import com.jenkins.common.bookinginterface.entity.Attender;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author JenkinsZhang
 * @date 29/04/2020
 */
@Component
@Mapper
public interface AttenderMapper {

    int insertAttender(@Param("attender")Attender attender);

    int updateAttender(@Param("attender")Attender attender);

    Attender getAttender(int recordId);
}
