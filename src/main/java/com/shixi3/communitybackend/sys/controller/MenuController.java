package com.shixi3.communitybackend.sys.controller;

import com.shixi3.communitybackend.common.entity.Menu;
import com.shixi3.communitybackend.common.entity.MenuTree;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.sys.service.MenuService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sys/menu")
public class MenuController {
    @Resource
    MenuService menuService;

    /**
     * 查询当前用户的权限信息并返回
     *
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public CommonResult<List<MenuTree>> getMenuTree() {
        return CommonResult.success(menuService.allTreeMenu());
    }

    /**
     * 获取改角色的菜单的id用于回显菜单数据
     *
     * @param id
     * @return
     */
    @GetMapping("/list/{id}")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public CommonResult<List<Long>> getMenuTreeById(@PathVariable Long id) {
        return CommonResult.success(menuService.getTreeMenuByRoleId(id));
    }

    /**
     * 得到所有的非按钮id
     *
     * @return
     */
    @GetMapping("/getContent")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public CommonResult<List<MenuTree>> getContent() {
        List<MenuTree> treeMenu = menuService.getTreeMenu(null);
        return CommonResult.success(treeMenu);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public CommonResult<String> addMenus(@RequestBody Menu menu) {
        System.out.println(menu);
        if (menu.getParentId() == null){
            menu.setParentId(0L);
        }
        boolean save = menuService.save(menu);
        if (!save) {
            return CommonResult.success("添加失败");
        }
        return CommonResult.success("添加成功");
    }
}
