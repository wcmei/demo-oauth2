package vip.yugu.personality.configurer;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * 不写这个类的情况下，Authentication得不到初始化，即使用Authentication authentication = SecurityContextHolder.getContext().getAuthentication()为空
 */
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Order(-1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    //AuthenticationManager必须
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 使用自定义认证与授权
        auth.userDetailsService(userDetailsService());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/oauth/**");
    }

    //如果没有重写该方法，则/oauth/token访问没有任何相应，且访问/oauth/info在带与不带token下都是一样的401，Unauthorized
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);




        //这个相当于开启了认证这个功能，即没有这个任何用户都可以访问程序，且用户都被认定为anonymousUser
        //相当于开启springSecurityFilterChain
        //设置路径无效，即设置/user/info和不设置/user/info的效果是一样的。只要有下面这行代码则所有的请求都必须认证
        //必须设置路径包含/oauth/token，不然任何用户请求都可以访问程序，且用户都被认定为anonymousUser
        //必须设置HttpMethod，不然访问/oauth/token时会报
                                                    // "error": "unauthorized",
                                                    //    "error_description": "Full authentication is required to access this resource"，
                    //不过设置get、post好像无效，因此设置为options
        http.requestMatchers().antMatchers(HttpMethod.OPTIONS,"/oauth/token");
//                .and()
//                .cors()
//                .and()
//                .csrf().disable();
    }

}
