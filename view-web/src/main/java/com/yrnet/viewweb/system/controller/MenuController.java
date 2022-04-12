package com.yrnet.viewweb.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.controller.BaseController;
import com.yrnet.viewweb.common.exception.YinXXException;
import com.yrnet.viewweb.common.router.VueRouter;
import com.yrnet.viewweb.system.entity.Menu;
import com.yrnet.viewweb.system.manager.UserManager;
import com.yrnet.viewweb.system.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private String message;

    @Autowired
    private UserManager userManager;
    @Autowired
    private IMenuService menuService;

    @GetMapping("/{username}")
    public ArrayList<VueRouter<Menu>> getUserRouters(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userManager.getUserRouters(username);
    }

    @GetMapping
    @RequiresPermissions("menu:view")
    public Map<String, Object> menuList(Menu menu) {
        return this.menuService.findMenus(menu);
    }

    @Log("新增菜单/按钮")
    @PostMapping
    @RequiresPermissions("menu:add")
    public void addMenu(@Valid Menu menu) throws YinXXException {
        try {
            this.menuService.createMenu(menu);
        } catch (Exception e) {
            message = "新增菜单/按钮失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @Log("删除菜单/按钮")
    @DeleteMapping("/{menuIds}")
    @RequiresPermissions("menu:delete")
    public void deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) throws YinXXException {
        try {
            String[] ids = menuIds.split(StringPool.COMMA);
            this.menuService.deleteMeuns(ids);
        } catch (Exception e) {
            message = "删除菜单/按钮失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @Log("修改菜单/按钮")
    @PutMapping
    @RequiresPermissions("menu:update")
    public void updateMenu(@Valid Menu menu) throws YinXXException {
        try {
            this.menuService.updateMenu(menu);
        } catch (Exception e) {
            message = "修改菜单/按钮失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("menu:export")
    public void export(Menu menu, HttpServletResponse response) throws YinXXException {
        try {
            List<Menu> menus = this.menuService.findMenuList(menu);
            ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }
}
