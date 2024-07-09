package com.shixi3.communitybackend.wxapp.service;

import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.examine.entity.TenantExamineRecord;

public interface HouseExamineService {

    CommonResult<String> changeInfo(TenantExamineRecord record);

}
