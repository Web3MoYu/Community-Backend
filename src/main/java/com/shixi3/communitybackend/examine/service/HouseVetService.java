package com.shixi3.communitybackend.examine.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.examine.vo.HouseVetVo;

public interface HouseVetService extends IService<HouseVetVo> {
    Page<HouseVetVo> page(Integer page, Integer pageSize, Integer status);

    HouseVetVo getHouseVetVoById(Long id);

    void auditHouseWithUser(HouseVetVo houseVetVo);
}
