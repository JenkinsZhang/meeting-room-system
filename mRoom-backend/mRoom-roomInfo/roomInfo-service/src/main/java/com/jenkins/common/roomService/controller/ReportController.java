package com.jenkins.common.roomService.controller;

import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.roomInterface.entity.Report;
import com.jenkins.common.roomInterface.model.ReportOverview;
import com.jenkins.common.roomInterface.model.ReportSearch;
import com.jenkins.common.roomService.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author JenkinsZhang
 * @date 2020/4/16
 */
@RestController
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/admin/reportOverview/{page}/{size}")
    public ResultVo getReportOverview(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody ReportSearch reportSearch)
    {
        List<ReportOverview> reportOverview = reportService.getReportOverview(page,size,reportSearch);
        return ResultVo.ok("OK!",reportOverview);
    }

    @PutMapping("/admin/report/{reportId}/{status}")
    public ResultVo completeReport(@PathVariable("reportId") int reportId,@PathVariable("status") int status)
    {
        int code = reportService.completeReport(reportId, status);
        return code == 1? ResultVo.ok("OK!") : ResultVo.error("Failed!");
    }

    @PostMapping("/report")
    public ResultVo addReport(@RequestBody Report report){

        int code = reportService.addReport(report);
        return code == 1? ResultVo.ok("Report Successfully!") : ResultVo.error("Failed!");
    }

    @GetMapping("/admin/statusCount")
    public ResultVo getStatusCount()
    {
        Map<String, Integer> statusCount = reportService.getStatusCount();
        return ResultVo.ok("OK!",statusCount);
    }
}
