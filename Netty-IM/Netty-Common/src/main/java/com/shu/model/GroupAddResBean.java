package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/8 18:25
 * @version: 1.0
 */
@Data
public class GroupAddResBean extends BaseBean implements Serializable {

    private Integer code;//群组名称
    private String msg;//群组名称



    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 8;
    }
}
