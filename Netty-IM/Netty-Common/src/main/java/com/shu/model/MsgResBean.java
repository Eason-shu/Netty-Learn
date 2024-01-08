package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 消息响应bean
 * @author: shu
 * @createDate: 2024/1/5 10:53
 * @version: 1.0
 */
@Data
public class MsgResBean extends BaseBean implements Serializable {

    private Integer status;//响应状态，0发送成功，1发送失败
    private String msg;//响应信息

    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 4;
    }
}
