package com.jenkins.common.roomService.service;

import com.jenkins.common.roomInterface.entity.Report;
import com.jenkins.common.roomInterface.model.ReportOverview;
import com.jenkins.common.roomInterface.model.ReportSearch;
import com.jenkins.common.roomService.mapper.ReportMapper;
import com.jenkins.common.roomService.mapper.RoomMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author JenkinsZhang
 * @date 2020/4/16
 */

@Service
public class ReportService {

    private ReportMapper reportMapper;
    private RoomMapper roomMapper;

    @Autowired
    public ReportService(ReportMapper reportMapper, RoomMapper roomMapper) {
        this.reportMapper = reportMapper;
        this.roomMapper = roomMapper;
    }

    public int addReport(Report report)
    {
        report.setStatus(0);
        report.setCreateTime(DateTime.now().toDate());
        return reportMapper.insertReport(report);
    }

    public List<ReportOverview> getReportOverview (int page, int size, ReportSearch reportSearch)
    {
        Date startDate = reportSearch.getStartDate();

        if(startDate != null)
        {
            DateTime newStartDate = new DateTime(startDate.getTime());
            Date endDate = newStartDate.plusDays(1).toDate();
            reportSearch.setEndDate(endDate);
        }
        List<ReportOverview> reportOverviews = reportMapper.selectAllReportOverviews(page - 1, size, reportSearch);
        for (ReportOverview reportOverview : reportOverviews) {
            String status = reportOverview.getStatus();
            reportOverview.setStatus(status.equals("0") ?"Uncompleted" :"Completed");
        }
        return  reportOverviews;
    }


    public int completeReport(int reportId, int status)
    {
        Date now = DateTime.now().toDate();
        return reportMapper.updateReport(reportId,status,now);
    }

    public Map<String,Integer> getStatusCount()
    {
        int finishedCount = reportMapper.getFinishedCount();
        int unfinishedCount = reportMapper.getUnfinishedCount();

        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("Finished",finishedCount);
        countMap.put("Unfinished",unfinishedCount);
        return countMap;
    }
}
