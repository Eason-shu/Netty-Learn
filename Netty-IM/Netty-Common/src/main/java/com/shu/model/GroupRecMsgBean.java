package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/8 18:31
 * @version: 1.0
 */
@Data
public class GroupRecMsgBean extends BaseBean implements Serializable {

    private Integer Fromuserid;
    private String Msg;




    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 15;
    }
}
