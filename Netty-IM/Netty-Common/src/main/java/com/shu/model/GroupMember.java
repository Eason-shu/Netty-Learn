package com.shu.model;

import io.netty.channel.Channel;
import lombok.Data;

/**
 * @description:  分组成员实体类，用于存储分组成员信息，包括用户ID和通道信息
 * @author: shu
 * @createDate: 2024/1/8 17:34
 * @version: 1.0
 */
@Data
public class GroupMember {
    private Integer userid;
    private Channel channel;
}
