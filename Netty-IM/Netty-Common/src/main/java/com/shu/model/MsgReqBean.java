package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 9:47
 * @version: 1.0
 */
@Data
public class MsgReqBean extends BaseBean implements Serializable {
    private Integer fromuserid;//发送人ID
    private Integer touserid;//接受人ID
    private String msg;//发送消息

    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 3;
    }
}
