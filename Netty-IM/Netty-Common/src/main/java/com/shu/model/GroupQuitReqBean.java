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
public class GroupQuitReqBean extends BaseBean implements Serializable {

    private Integer groupId;//群主Id
    private Integer userId;//用户Id


    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 9;
    }
}
