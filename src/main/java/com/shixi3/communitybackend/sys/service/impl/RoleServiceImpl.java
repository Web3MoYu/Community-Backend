package com.shixi3.communitybackend.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.common.entity.Menu;
import com.shixi3.communitybackend.sys.entity.MenuRole;
import com.shixi3.communitybackend.sys.entity.Role;
import com.shixi3.communitybackend.sys.entity.UserRole;
import com.shixi3.communitybackend.sys.mapper.MenuMapper;
import com.shixi3.communitybackend.sys.mapper.RoleMapper;
import com.shixi3.communitybackend.sys.mapper.UserRoleMapper;
import com.shixi3.communitybackend.sys.service.MenuRoleService;
import com.shixi3.communitybackend.sys.service.RoleService;
import com.shixi3.communitybackend.sys.tools.AuthTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {
    @Resource
    UserRoleMapper userRoleMapper;

    @Resource
    MenuRoleService menuRoleService;

    @Resource
    MenuMapper menuMapper;

    @Resource
    AuthTools authTools;

    @Override
    public boolean allotPermissions(List<Long> permissions, Long roleId) {
        // 删除当前用户的所有权限
        LambdaUpdateWrapper<MenuRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MenuRole::getRoleId, roleId);
        menuRoleService.remove(wrapper);

        // 添加当前的用户权限
        // 查询当前权限的父id
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Menu::getParentId)
                .in(Menu::getMenuId, permissions);
        List<Long> collect = menuMapper.selectList(queryWrapper).stream().map(Menu::getParentId).toList();

        // 将父id放到permissions中
        Set<Long> per = new HashSet<>(permissions);
        Set<Long> parent = new HashSet<>(collect);
        for (Long item : parent) {
            if (!per.contains(item)) {
                permissions.add(item);
            }
        }
        // 添加权限
        List<MenuRole> menuRoles = new ArrayList<>();
        for (Long menId : permissions) {
            MenuRole menuRole = new MenuRole();
            menuRole.setRoleId(roleId);
            menuRole.setMenuId(menId);
            menuRoles.add(menuRole);
        }

        // 清除token
        // 获取当前角色的所有用户
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getRoleId, roleId);
        List<Long> roleList = userRoleMapper.selectList(userRoleLambdaQueryWrapper).stream().map(UserRole::getUserId).toList();
        authTools.deleteByUserId(roleList);
        return menuRoleService.saveBatch(menuRoles);
    }

}
