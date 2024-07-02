package com.shixi3.communitybackend.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.common.entity.Menu;
import com.shixi3.communitybackend.common.entity.MenuTree;

import java.util.List;

public interface MenuService extends IService<Menu> {
    /**
     * 根据用户的id获取到当前用户的树形菜单
     *
     * @param userId
     * @return
     */
    List<MenuTree> getTreeMenu(Long userId);
}
