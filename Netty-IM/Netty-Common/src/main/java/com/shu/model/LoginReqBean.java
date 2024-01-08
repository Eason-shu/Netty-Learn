package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 9:38
 * @version: 1.0
 */
@Data
public class LoginReqBean extends BaseBean implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;



    /**
     * 业务抽象方法
     *
     * @return
     */
    @Override
    public Byte code() {
        return 1;
    }




}
