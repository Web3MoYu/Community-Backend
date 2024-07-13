package com.shixi3.communitybackend.sys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.sys.entity.Tenant;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface TenantMapper extends BaseMapper<Tenant> {

    @Select("select * from wx_user where name = #{name}")
    List<Tenant> getIdCardByName(String name);

    @Select("select distinct(name) from wx_user")
    List<String> getAllName();

    @Select("select * from wx_user where id_card = #{idCard}")
    Tenant getUserByIdCard(String idCard);

    @Select("select * from wx_user where id = #{id}")
    Tenant getOneById(Long id);

    /**
     * 删除所有与houseId有关的信息
     * @param houseId
     */
    @Delete("delete from user_house where house_id = #{houseId}")
    void deleteAll(Long houseId);

    /**
     * 删除user_house中关于一个用户的数据
     * @param id
     */
    @Delete("delete from user_house where wx_user_id = #{id}")
    void deleteOne(Long id);

    /**
     *
     * @param id
     */
    @Update("update wx_user set user_type = 3 where id = #{id}")
    void changeUser(Long id);


    @Update("update house set owner_id = 0 where  1")
    void changeHouse(Long id);
}
