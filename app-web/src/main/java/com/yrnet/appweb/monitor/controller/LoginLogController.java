package com.yrnet.appweb.monitor.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import com.yrnet.appweb.common.annotation.ControllerEndpoint;
import com.yrnet.appweb.common.controller.BaseController;
import com.yrnet.appweb.common.entity.LicenseResponse;
import com.yrnet.appweb.common.entity.QueryRequestPage;
import com.yrnet.appweb.monitor.entity.LoginLog;
import com.yrnet.appweb.monitor.service.ILoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequestMapping("loginLog")
public class LoginLogController extends BaseController {

    @Autowired
    private ILoginLogService loginLogService;

    @GetMapping("list")
    @RequiresPermissions("loginlog:view")
    public LicenseResponse loginLogList(LoginLog loginLog, QueryRequestPage request) {
        Map<String, Object> dataTable = getDataTable(this.loginLogService.findLoginLogs(loginLog, request));
        return new LicenseResponse().success().data(dataTable);
    }

    @GetMapping("delete/{ids}")
    @RequiresPermissions("loginlog:delete")
    @ControllerEndpoint(exceptionMessage = "删除日志失败")
    public LicenseResponse deleteLogss(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] loginLogIds = ids.split(StringPool.COMMA);
        this.loginLogService.deleteLoginLogs(loginLogIds);
        return new LicenseResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("loginlog:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequestPage request, LoginLog loginLog, HttpServletResponse response) {
        List<LoginLog> loginLogs = this.loginLogService.findLoginLogs(loginLog, request).getRecords();
        ExcelKit.$Export(LoginLog.class, response).downXlsx(loginLogs, false);
    }
}
