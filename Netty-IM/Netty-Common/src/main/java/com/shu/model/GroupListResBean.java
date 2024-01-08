package com.shu.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/8 18:19
 * @version: 1.0
 */
@Data
public class GroupListResBean extends BaseBean implements Serializable {

    private List<GroupInfo> groupInfoList;//群组信息


    /**
     * 业务指令
     *
     * @return
     */
    @Override
    public Byte code() {
        return 6;
    }
}
