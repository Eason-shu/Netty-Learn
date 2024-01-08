package com.shu;

import com.alibaba.fastjson.JSON;
import com.shu.model.BaseBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 10:48
 * @version: 1.0
 */
public class MyDecoder extends ByteToMessageDecoder {

    /**
     * 解码器
     * @param channelHandlerContext
     * @param byteBuf
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //1.根据协议取出数据
        int tag=byteBuf.readInt();//标识符
        byte code=byteBuf.readByte();//获取指令
        int len=byteBuf.readInt();//获取数据长度
        byte[] bytes=new byte[len];
        byteBuf.readBytes(bytes);
        //2.根据code获取类型
//        Class<? extends BaseBean> c= MapUtils.getBean(code);
        Class<? extends BaseBean> c= MapUtils.getBean1(code);

        //3.反序列化
        BaseBean baseBean= JSON.parseObject(bytes,c);
        list.add(baseBean);
    }
}
