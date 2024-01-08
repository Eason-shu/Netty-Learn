package com.shu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 基础bean
 * @author: shu
 * @createDate: 2024/1/5 9:36
 * @version: 1.0
 */
@Data
public abstract class BaseBean implements Serializable {
    /**
     * 协议标识：ox66
     */
    private Integer tag=0x66;

    /**
     * 业务指令
     *
     * @return
     */
    public abstract Byte code();

}
