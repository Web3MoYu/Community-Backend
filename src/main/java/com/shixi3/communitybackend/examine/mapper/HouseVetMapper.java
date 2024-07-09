package com.shixi3.communitybackend.examine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.examine.entity.TenantExamineRecord;
import com.shixi3.communitybackend.examine.vo.HouseVetVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseVetMapper extends BaseMapper<TenantExamineRecord> {
    Page<HouseVetVo> page(@Param("page") Page<HouseVetVo> page,@Param("status") Integer status);

    HouseVetVo getHouseVetVoById(@Param("id") Long id);
}
