package com.shixi3.communitybackend.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.common.entity.Menu;
import com.shixi3.communitybackend.sys.vo.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenuByUserId(@Param("userId") Long userId);

    List<Menu> getMenus();

    List<UserRoleVo> getMenusByRoleId(@Param("roleId") Long roleId);
}