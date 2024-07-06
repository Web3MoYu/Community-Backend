package com.shixi3.communitybackend.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.sys.entity.Tenant;

import java.util.List;


public interface TenantService extends IService<Tenant> {

    /**
     * 通过名字拿到身份证信息
     * @param name
     * @return
     */
    List<Tenant> getIdCardByName(String name);

    /**
     * 拿到所有用户名字
     * @return
     */
    List<String> getAllName();

    /**
     * 通过身份证号拿到具体用户信息
     * @param idCard
     * @return
     */
    Tenant getUserByIdCard(String idCard);

    /**
     * 根据id拿到具体用户信息
     * @param id
     * @return
     */
    Tenant getOneById(Long id);

    /**
     * 在删除用户时对house表操作
     * @param id
     * @param houseId
     * @param userType
     */
    void deleteWxUser(Long id,Long houseId,Integer userType);

    /**
     * 在删除用户时对user_house表进行操作
     * @param wxUserId
     */
    void deleteUserHouse(Long wxUserId);

    /**
     * 根据id把用户改为游客（针对wx_user操作）
     * @param id
     */
    void changeUser(Long id);

}
