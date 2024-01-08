package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 创建群组响应实体类，用于存储创建群组响应信息，包括群组名称和群组成员信息（用户ID和通道信息）等
 * @author: shu
 * @createDate: 2024/1/8 18:22
 * @version: 1.0
 */
@Data
public class GroupCreateResBean   extends BaseBean implements Serializable {
    private String groupName;//群组名称
    private int code;
    private String msg;


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
