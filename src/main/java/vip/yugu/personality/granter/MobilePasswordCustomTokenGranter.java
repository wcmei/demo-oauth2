package vip.yugu.personality.granter;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import vip.yugu.personality.model.CustomUser;
import vip.yugu.personality.service.CustomUserDetailsService;

import java.util.Map;

/**
 * @Author: yugu
 * @CreateDate: 2019/9/6
 * @Description:
 */
public class MobilePasswordCustomTokenGranter extends AbstractCustomTokenGranter {

    protected CustomUserDetailsService userDetailsService;

    public MobilePasswordCustomTokenGranter(CustomUserDetailsService userDetailsService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, "pwd");
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected CustomUser getCustomUser(Map<String, String> parameters) {
        String mobile = parameters.get("mobile");
        String password = parameters.get("password");
        return userDetailsService.loadUserByMobileAndPassword(mobile, password);
    }

}
