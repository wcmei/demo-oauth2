package vip.yugu.personality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.yugu.personality.model.Result;

/**
 * @Author: yugu
 * @CreateDate: 2019/9/6
 * @Description: 认证中心启动类
 */
@SpringBootApplication
@ControllerAdvice
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

//    @ResponseBody
//    @ExceptionHandler(Exception.class)
//    public Result exceptionHandler(Exception e) {
//        return Result.buildFail(e.getMessage());
//    }

}
