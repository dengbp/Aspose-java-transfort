package com.yrnet.viewweb.monitor.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.controller.BaseController;
import com.yrnet.viewweb.common.entity.QueryRequestPage;
import com.yrnet.viewweb.common.exception.YinXXException;
import com.yrnet.viewweb.monitor.entity.SysLog;
import com.yrnet.viewweb.monitor.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author dengbp
 */
@Slf4j
@RestController
@RequestMapping("log")
public class LogController extends BaseController {

    private String message;

    @Autowired
    private ILogService logService;

    @GetMapping
    @RequiresPermissions("log:view")
    public Map<String, Object> logList(QueryRequestPage request, SysLog sysLog) {
        return getDataTable(logService.findLogs(request, sysLog));
    }

    @Log("删除系统日志")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("log:delete")
    public void deleteLogss(@NotBlank(message = "{required}") @PathVariable String ids) throws YinXXException {
        try {
            String[] logIds = ids.split(StringPool.COMMA);
            this.logService.deleteLogs(logIds);
        } catch (Exception e) {
            message = "删除日志失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("log:export")
    public void export(QueryRequestPage request, SysLog sysLog, HttpServletResponse response) throws YinXXException {
        try {
            List<SysLog> sysLogs = this.logService.findLogs(request, sysLog).getRecords();
            ExcelKit.$Export(SysLog.class, response).downXlsx(sysLogs, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }
}
