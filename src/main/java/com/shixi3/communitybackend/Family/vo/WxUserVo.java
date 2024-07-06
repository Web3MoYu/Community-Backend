package com.shixi3.communitybackend.Family.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shixi3.communitybackend.Family.entity.WxUser;
import lombok.Data;

@Data
@TableName("wx_user")
public class WxUserVo extends WxUser {

    private Long houseId;

    private Integer belongFlag;
}