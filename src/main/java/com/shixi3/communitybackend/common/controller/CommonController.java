package com.shixi3.communitybackend.common.controller;

import com.shixi3.communitybackend.common.util.ImgUtils;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {


    @Resource
    ImgUtils imgUtils;

    @GetMapping("/img/download/{name}")
    public void download(@PathVariable String name, HttpServletResponse response) {
        imgUtils.download(name, response);
    }
}
