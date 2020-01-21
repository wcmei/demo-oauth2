package vip.yugu.personality.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: yugu
 * @CreateDate: 2019/9/6
 * @Description: 响应实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    //是否成功
    private boolean success;

    //返回码
    private int code;

    //返回信息
    private String msg;

    //返回数据
    private Object data;

    public static Result build() {
        return build(null);
    }

    public static Result build(Object data) {
        return new Result(true, 200, "操作成功",data);
    }

    public static Result buildFail() {
        return buildFail("操作失败");
    }

    public static Result buildFail(String msg) {
        return buildFail(200, msg);
    }

    public static Result buildFail(Integer code, String msg) {
        return new Result(false, code, msg, null);
    }

}
