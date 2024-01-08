package com.shu;

import com.shu.model.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 11:02
 * @version: 1.0
 */
public class MyServerChatHandler extends ChannelInboundHandlerAdapter {
    //1.定义一个Map（key是用户ID，value是连接通道）
    private static Map<Integer, Channel> map=new HashMap<Integer, Channel>();

    private static Logger logger = Logger.getLogger(MyServerChatHandler.class.getName());


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        if(msg instanceof LoginReqBean){
            //1.登录请求
            login((LoginReqBean) msg,ctx.channel());
        }else if(msg instanceof GroupCreateReqBean){
            //2.创建群组请求
            sendMsg((MsgReqBean)msg,ctx.channel());
        }
    }



    //登录处理方法
    private void login(LoginReqBean bean, Channel channel){
        LoginResBean res=new LoginResBean();
        //从map里面根据用户ID获取连接通道
        Channel c=map.get(bean.getUserId());
        if(c==null){
            //通道为空，证明该用户没有在线
          logger.info("[用户]："+bean.getUserId()+"登录成功");
            //1.添加到map
            map.put(bean.getUserId(),channel);
            //2.给通道赋值
            channel.attr(AttributeKey.valueOf("userid")).set(bean.getUserId());
            //3.响应
            res.setStatus(0);
            res.setMsg("登录成功");
            res.setUserid(bean.getUserId());
            channel.writeAndFlush(res);
        }else{
            //通道不为空，证明该用户已经在线了
            res.setStatus(1);
            res.setMsg("该账户目前在线");
            channel.writeAndFlush(res);
        }
    }



    //消息发送处理方法
    private void sendMsg(MsgReqBean bean,Channel channel){
        Integer touserid=bean.getTouserid();
        Channel c=map.get(touserid);
        if(c==null){
            MsgResBean res=new MsgResBean();
            res.setStatus(1);
            res.setMsg(touserid+",不在线");
            channel.writeAndFlush(res);

        }else{
            MsgRecBean res=new MsgRecBean();
            res.setFromuserid(bean.getFromuserid());
            res.setMsg(bean.getMsg());
            c.writeAndFlush(res);
        }
    }
}