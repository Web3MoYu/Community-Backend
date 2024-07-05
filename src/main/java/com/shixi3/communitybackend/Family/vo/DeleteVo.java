package com.shixi3.communitybackend.Family.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteVo {
    private List<Long> ids;//id数组用于批量操作
}
