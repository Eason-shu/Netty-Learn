package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/8 18:29
 * @version: 1.0
 */
@Data
public class GroupSendMsgReqBean extends BaseBean implements Serializable {

    private Integer Fromuserid;
    private Integer Togroupid;
    private String Msg;




    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 13;
    }
}
