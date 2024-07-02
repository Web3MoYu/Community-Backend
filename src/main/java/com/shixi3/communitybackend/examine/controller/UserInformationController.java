package com.shixi3.communitybackend.examine.controller;


import com.shixi3.communitybackend.examine.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/examine")
public class UserInformationController {

    @Autowired
    private UserInformationService userInformationService;




}
