package com.jenkins.common.roomService;

import com.jenkins.common.roomInterface.entity.Report;
import com.jenkins.common.roomInterface.model.ReportSearch;
import com.jenkins.common.roomService.service.ReportService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

/**
 * @author JenkinsZhang
 * @date 2020/4/16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Test
    public void addReportTest()
    {
        Report report = new Report();
        report.setDetail("hajaja");
        report.setRoomId(2);
        report.setReporter("614758656@qq.com");
        report.setStatus(0);
        report.setCreateTime(DateTime.now().toDate());
        reportService.addReport(report);
    }

    @Test
    public void getReportOverviewTest()
    {
        ReportSearch reportSearch = new ReportSearch();
        reportSearch.setRoomId(1);
        reportSearch.setReporter("1111");
        System.out.println(reportService.getReportOverview(1,10,reportSearch));
    }

    @Test
    public void completeReportTest()
    {
        int reportId = 1;
        int status = 1;
        reportService.completeReport(reportId,status);
    }
}
