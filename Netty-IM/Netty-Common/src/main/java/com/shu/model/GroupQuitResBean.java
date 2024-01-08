package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/8 18:26
 * @version: 1.0
 */
@Data
public class GroupQuitResBean extends BaseBean implements Serializable {

    private Integer code;
    private String msg;//消息

    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 10;
    }
}
