package com.shu.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 创建群组请求实体类，用于存储创建群组请求信息，包括群组名称和群组成员信息（用户ID和通道信息）等
 * @author: shu
 * @createDate: 2024/1/8 18:15
 * @version: 1.0
 */
@Data
public class GroupCreateReqBean  extends BaseBean implements Serializable {
    private String groupName;//群组名称
    private Integer groupId;//群Id
    private String gruopDesc;//群组描述
    private Integer userId;//用户ID


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
