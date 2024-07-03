package com.shixi3.communitybackend.sys.controller;


import com.shixi3.communitybackend.sys.service.TenantService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/tenant")
public class TenantController {

    @Resource
    private TenantService tenantService;




}
