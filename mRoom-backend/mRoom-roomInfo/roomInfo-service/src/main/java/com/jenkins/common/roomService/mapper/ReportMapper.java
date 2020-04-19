package com.jenkins.common.roomService.mapper;

import com.jenkins.common.roomInterface.entity.Report;
import com.jenkins.common.roomInterface.model.ReportOverview;
import com.jenkins.common.roomInterface.model.ReportSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/4/16
 */

@Mapper
@Component
public interface ReportMapper {

    int insertReport(@Param("report") Report report);

    List<Report> selectAllReports();

    int updateReport(int reportId, int status, Date completeTime);

    List<ReportOverview> selectAllReportOverviews(int page, int size, @Param("reportSearch")ReportSearch reportSearch);

    int getFinishedCount();

    int getUnfinishedCount();
}
