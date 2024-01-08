package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 10:08
 * @version: 1.0
 */
@Data
public class MsgRecBean extends BaseBean implements Serializable {
    private Integer fromuserid;//发送人ID
    private String msg;//消息

    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 5;
    }
}
