package com.shixi3.communitybackend.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.sys.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    /**
     * 分配权限
     * @param permissions
     * @return
     */
    boolean allotPermissions(List<Long> permissions, Long roleId);

}
