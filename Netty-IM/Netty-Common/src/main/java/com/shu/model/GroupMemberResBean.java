package com.shu.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/8 18:28
 * @version: 1.0
 */
@Data
public class GroupMemberResBean extends BaseBean implements Serializable {

    private Integer code;

    private String msg;

    private List<Integer> list;





    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 12;
    }
}
