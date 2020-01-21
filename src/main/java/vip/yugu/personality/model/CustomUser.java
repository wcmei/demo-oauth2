package vip.yugu.personality.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author: yugu
 * @CreateDate: 2019/9/6
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomUser implements Serializable {

    //昵称
    private String nickname;

    //手机号
    private String mobile;

    //头像
    private String avatar;

    private Collection<? extends GrantedAuthority> authorities;

}
