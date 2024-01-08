package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 群组列表请求实体类，用于存储群组列表请求信息，包括群组名称和群组成员信息（用户ID和通道信息）等
 * @author: shu
 * @createDate: 2024/1/8 18:23
 * @version: 1.0
 */
@Data
public class GroupListReqBean extends BaseBean implements Serializable {

    private String type;


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
