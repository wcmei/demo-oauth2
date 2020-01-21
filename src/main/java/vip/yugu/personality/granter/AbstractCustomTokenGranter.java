package vip.yugu.personality.granter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import vip.yugu.personality.model.CustomUser;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: yugu
 * @CreateDate: 2019/9/6
 * @Description:
 */
public abstract class AbstractCustomTokenGranter extends AbstractTokenGranter {

    @Autowired
    OAuth2RequestFactory requestFactory;

    protected AbstractCustomTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        CustomUser customUser = getCustomUser(parameters);
        if (customUser == null) {
            throw new InvalidGrantException("无法获取用户信息");
        }
        OAuth2Authentication authentication = super.getOAuth2Authentication(client, tokenRequest);
        authentication.setDetails(customUser);
        authentication.setAuthenticated(true);
        return authentication;
    }



    protected abstract CustomUser getCustomUser(Map<String, String> parameters);

}
