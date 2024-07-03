package com.shixi3.communitybackend.sys.controller;


import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/tenant")
public class TenantController {

    @Resource
    private UserInformationService userInformationService;




}
