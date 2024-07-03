package com.shixi3.communitybackend.Family.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHouse {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;//所属关系id
    private Long userId;//用户id
    private Long houseId;//房屋id
    private Integer belongFlag;//所属关系，1表示用户为成员，0表示用户为户主
}
