package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/8 18:27
 * @version: 1.0
 */
@Data
public class GroupMemberReqBean extends BaseBean implements Serializable {

    private Integer groupId;//群主Id




    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 11;
    }
}
