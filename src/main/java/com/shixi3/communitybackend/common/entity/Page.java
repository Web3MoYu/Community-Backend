package com.shixi3.communitybackend.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private Integer page;
    private Integer pageSize;
    private Integer total;
    private List<T> records;
}
