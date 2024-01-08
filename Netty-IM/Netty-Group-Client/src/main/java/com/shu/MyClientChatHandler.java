package com.shu;

import com.shu.model.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.util.Scanner;

/**
 * @description: 客户端处理器，用于处理客户端的请求和响应信息，继承ChannelInboundHandlerAdapter，重写channelRead方法，用于处理服务端响应的信息
 * @author: shu
 * @createDate: 2024/1/5 10:57
 * @version: 1.0
 */
public class MyClientChatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>客户端通道就绪: 开始登录>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //通道就绪时，发起登录请求
        login(ctx.channel());
    }

    /**
     * 收到消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>客户端收到消息>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (msg instanceof LoginResBean) {
            //1.登录结果响应
            LoginResBean res = (LoginResBean) msg;
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>登录响应：" + res.getMsg());
            deal(ctx.channel());
        } else if (msg instanceof GroupCreateResBean) {
            //2.创建群组响应
            GroupCreateResBean res = (GroupCreateResBean) msg;
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>创建群组响应：" + res.getMsg());

        } else if (msg instanceof GroupListResBean) {
            GroupListResBean res = (GroupListResBean) msg;
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>获取群组列表响应：" + res.getGroupInfoList());
        }

    }

    /**
     * 发送登录请求
     */
    public void login(Channel channel) {
        //1.创建登录请求对象
        LoginReqBean req = new LoginReqBean();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户id：");
        req.setUserId(scanner.nextInt());
        System.out.println("请输入用户名：");
        req.setUserName(scanner.next());
        //2.发送登录请求
        channel.writeAndFlush(req);
    }


    private void deal(final Channel channel) {
        final Scanner scanner = new Scanner(System.in);
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println("请选择类型：0创建群组，1查看群组，2加入群组，3退出群组，4查看群成员，5群发消息");
                    int type = scanner.nextInt();
                    switch (type) {
                        case 0:
                            createGroup(scanner, channel);
                            break;
                        case 1:
                            listGroup(scanner, channel);
                            break;
                        case 2:
                            addGroup(scanner,channel);
                            break;
                        case 3:
                            quitGroup(scanner,channel);
                            break;
                        case 4:
                            listMembers(scanner,channel);
                            break;
                        case 5:
                            sendMsgToGroup(scanner,channel);
                            break;
                        default:
                            System.out.println("输入的类型不存在!");
                    }
                }
            }
        }).start();
    }





    /**
     * 群发消息
     * @param scanner
     * @param channel
     */
    private void sendMsgToGroup(Scanner scanner,Channel channel){
        System.out.println("请输入群组ID：");
        int groupId=scanner.nextInt();
        System.out.println("请输入发送消息内容：");
        String msg=scanner.next();
        Integer userId=(Integer) channel.attr(AttributeKey.valueOf("userid")).get();
        GroupSendMsgReqBean bean=new GroupSendMsgReqBean();
        bean.setFromuserid(userId);
        bean.setTogroupid(groupId);
        bean.setMsg(msg);
        channel.writeAndFlush(bean);
    }



    /**
     * 查看群成员
     * @param scanner
     * @param channel
     */
    private void listMembers(Scanner scanner,Channel channel){
        System.out.println("请输入群组ID：");
        int groupId=scanner.nextInt();
        GroupMemberReqBean bean=new GroupMemberReqBean();
        bean.setGroupId(groupId);
        channel.writeAndFlush(bean);
    }


    /**
     * 退出群组
     * @param scanner
     * @param channel
     */
    private void quitGroup(Scanner scanner, Channel channel) {
        System.out.println("请输入退出的群组ID");
        int groupId=scanner.nextInt();
        Integer userId=(Integer) channel.attr(AttributeKey.valueOf("userid")).get();
        GroupQuitReqBean bean=new GroupQuitReqBean();
        bean.setUserId(userId);
        bean.setGroupId(groupId);
        channel.writeAndFlush(bean);
    }


    /**
     * 添加群组
     * @param scanner
     * @param channel
     */
    private void addGroup(Scanner scanner, Channel channel) {
        System.out.println("请输入加入的群组ID");
        int groupId=scanner.nextInt();
        Integer userId=(Integer) channel.attr(AttributeKey.valueOf("userid")).get();
        GroupAddReqBean bean=new GroupAddReqBean();
        bean.setUserId(userId);
        bean.setGroupId(groupId);
        channel.writeAndFlush(bean);
    }



    /**
     * 查看群组
     *
     * @param scanner
     * @param channel
     */
    private void listGroup(Scanner scanner, Channel channel) {
        GroupListReqBean bean = new GroupListReqBean();
        bean.setType("list");
        channel.writeAndFlush(bean);
    }


    /**
     * 创建群组
     *
     * @param scanner
     * @param channel
     */
    private void createGroup(Scanner scanner, Channel channel) {
        GroupCreateReqBean req = new GroupCreateReqBean();
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("请输入ID编号：");
        req.setGroupId(scanner1.nextInt());
        System.out.println("请输入群组名称：");
        req.setGroupName(scanner1.next());
        System.out.println("请输入群组描述：");
        req.setGruopDesc(scanner1.next());
        channel.writeAndFlush(req);
    }

}
