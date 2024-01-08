package com.shu;

import com.alibaba.fastjson.JSON;
import com.shu.model.BaseBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.logging.Logger;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 10:32
 * @version: 1.0
 */
public class MyEncoder extends MessageToByteEncoder<BaseBean> {


    private static Logger logger = Logger.getLogger(MyEncoder.class.getName());


    /**
     * 编码器
     * @param channelHandlerContext
     * @param baseBean
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BaseBean baseBean, ByteBuf byteBuf) throws Exception {
            logger.info("发送消息，开始编码");
            //1.把实体序列化成字节数字
            byte[] bytes= JSON.toJSONBytes(baseBean);
            //2.根据协议组装数据
            byteBuf.writeInt(baseBean.getTag());//标识（4个字节）
            byteBuf.writeByte(baseBean.code());//指令（1个字节）
            byteBuf.writeInt(bytes.length);//长度（4个字节）
            byteBuf.writeBytes(bytes);//数据
    }
}
