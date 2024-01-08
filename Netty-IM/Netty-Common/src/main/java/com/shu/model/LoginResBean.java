package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 9:45
 * @version: 1.0
 */
@Data
public class LoginResBean extends BaseBean implements Serializable {

    private Integer status;//响应状态，0登录成功，1登录失败
    private String msg;//响应信息
    private Integer userid;//用户ID


    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 2;
    }
}
