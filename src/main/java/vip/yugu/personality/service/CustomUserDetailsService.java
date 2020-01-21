package vip.yugu.personality.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vip.yugu.personality.model.CustomUser;

/**
 * @Author: yugu
 * @CreateDate: 2019/9/6
 * @Description: 用户细节服务实现
 */
@Service
public class CustomUserDetailsService {

    public CustomUser loadUserByMobileAndPassword(String mobile, String password) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new InvalidGrantException("您输入的手机号或密码不正确");
        }
        // 判断成功后返回用户细节
        return new CustomUser("密码匿名者",mobile,"https://www.google.cn/favicon.ico", AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user,root"));
    }

    public CustomUser loadUserByMobileAndSmscode(String mobile, String smscode) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smscode)) {
            throw new InvalidGrantException("您输入的手机号或短信验证码不正确");
        }
        // 判断成功后返回用户细节
        return new CustomUser("验证码匿名者",mobile,"https://www.google.cn/favicon.ico", AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user,root"));
    }

}
