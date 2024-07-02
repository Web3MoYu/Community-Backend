package com.shixi3.communitybackend.auth.util;

import com.shixi3.communitybackend.common.model.DMSUserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtil {
    public static Long getUserID() {
        DMSUserDetail userDetail = getUserDetail();
        return userDetail.getID();
    }

    public static DMSUserDetail getUserDetail() {
        return (DMSUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // 将字符串转换为GrantedAuthority
    public static List<GrantedAuthority> convertStringToGrantedAuthority(List<String> auths) {
        ArrayList<GrantedAuthority> result = new ArrayList<>();
        for (String auth : auths) {
            result.add(new SimpleGrantedAuthority(auth));
        }
        return result;
    }
}
