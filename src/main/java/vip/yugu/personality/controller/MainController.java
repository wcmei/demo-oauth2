package vip.yugu.personality.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yugu
 * @CreateDate: 2019/9/7
 * @Description:
 */
@RestController
public class MainController {
    @RequestMapping("/current-info")
    public Object getUser(Authentication authentication) {
        return authentication;
    }
}
