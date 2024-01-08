package com.shu.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 分组
 * @author: shu
 * @createDate: 2024/1/8 17:33
 * @version: 1.0
 */
@Data
public class Group {
    private String groupName;
    private List<GroupMember> members=new ArrayList<GroupMember>();
}
