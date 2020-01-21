package vip.yugu.personality.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @Author: yugu
 * @CreateDate: 2019/9/7
 * @Description:
 */

/**
 * 在有WebSecurityConfiguration情况下，有无此类没有影响
 */

//@Configuration
//@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated();
//    }

}
