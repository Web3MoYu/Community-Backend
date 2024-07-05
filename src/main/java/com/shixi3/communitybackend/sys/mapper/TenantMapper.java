package com.shixi3.communitybackend.sys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.sys.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface TenantMapper extends BaseMapper<Tenant> {

    @Select("select * from wx_user where name = #{name}")
    List<Tenant> getIdCardByName(String name);

    @Select("select distinct(name) from wx_user")
    List<String> getAllName();

    @Select("select * from wx_user where id_card = #{idCard}")
    Tenant getUserByIdCard(String idCard);
}
