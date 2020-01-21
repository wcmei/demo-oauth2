package vip.yugu.personality.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import vip.yugu.personality.granter.MobilePasswordCustomTokenGranter;
import vip.yugu.personality.granter.MobileSmsCustomTokenGranter;
import vip.yugu.personality.model.CustomUser;
import vip.yugu.personality.service.CustomUserDetailsService;

import javax.sql.DataSource;
import java.util.*;

/**
 * @Author: yugu
 * @CreateDate: 2019/9/6
 * @Description: 授权服务配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public ClientDetailsService jdbcClientDetails() {
        //基于JDBC实现，需要事先在数据库配置客户端信息
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public TokenStore tokenStore() {
//        基于JDBC实现，令牌保存到数据（oauth_access_token）
//        JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
//        return jdbcTokenStore;
        //基于Redis实现，令牌保存到数据
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        return redisTokenStore;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("personality")
//                .secret(passwordEncoder().encode("personality"))
//                .scopes("all")
//                .authorizedGrantTypes("pwd", "sms", "refresh_token");
        clients.withClientDetails(jdbcClientDetails());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //开启认证路径/oauth/token
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }

    /**
     *
     * 这里面可以通过enhance自定义token
     * 也可以自定义tokenservice设置token的有效时长，但是要注意加入到AuthorizationServerEndpointsConfigurer当中
     *
     * AuthorizationServerEndpointsConfigurer个人觉得可以看作是一个token的大管家，任何设置都需要注入到其中，然后其他的交给spring就好了
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        List<TokenGranter> tokenGranters = getTokenGranters(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory());
//        tokenGranters.add(endpoints.getTokenGranter());
        endpoints.tokenGranter(new CompositeTokenGranter(tokenGranters));
        endpoints.exceptionTranslator(webResponseExceptionTranslator);
        endpoints.tokenStore(tokenStore());
        endpoints.tokenEnhancer(new TokenEnhancer() {
            @Override
            //程序在调用此方法使用了动态代理
            public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
                System.out.println(oAuth2Authentication.getPrincipal());
                DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;
                CustomUser user = (CustomUser) oAuth2Authentication.getDetails();
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("nickname", user.getNickname());
                map.put("mobile", user.getMobile());
                map.put("avatar",user.getAvatar());
                token.setAdditionalInformation(map);
                ((DefaultOAuth2AccessToken) oAuth2AccessToken).setValue("111");
                return oAuth2AccessToken;
            }
        });
    }

    private List<TokenGranter> getTokenGranters(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        return new ArrayList<>(Arrays.asList(
                new MobilePasswordCustomTokenGranter(userDetailsService, tokenServices, clientDetailsService, requestFactory),
                new MobileSmsCustomTokenGranter(userDetailsService, tokenServices, clientDetailsService, requestFactory)
        ));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
