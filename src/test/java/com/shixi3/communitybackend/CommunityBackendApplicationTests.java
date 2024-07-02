package com.shixi3.communitybackend;

import com.shixi3.communitybackend.sys.service.MenuService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityBackendApplicationTests {

    @Resource
    MenuService menuService;

    @Test
    void contextLoads() {
        System.out.println(menuService.getTreeMenu(1L));
    }

}
