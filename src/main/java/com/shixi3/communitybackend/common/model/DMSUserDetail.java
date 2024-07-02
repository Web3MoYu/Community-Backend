package com.shixi3.communitybackend.common.model;

import lombok.Data;

import java.util.Set;

@Data
public class DMSUserDetail {
    private Long ID;
    private Set<String> authorities;
}