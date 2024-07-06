package com.shixi3.communitybackend.Family.entity;

import com.shixi3.communitybackend.Family.vo.WxUserVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxUserTree implements Serializable {
    private WxUserVo wxUserVo;
    private List<WxUserVo> children;
}
