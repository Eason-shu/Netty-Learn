package com.shu;

import com.shu.model.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 11:02
 * @version: 1.0
 */
public class MyServerChatHandler extends ChannelInboundHandlerAdapter {
    private static Map<Integer, Channel> map = new HashMap<Integer, Channel>();
    private static Map<Integer, Group> groups = new HashMap<Integer, Group>();

    private static Logger logger = Logger.getLogger(MyServerChatHandler.class.getName());


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof LoginReqBean) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收到登录请求>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //1.登录请求
            login((LoginReqBean) msg, ctx.channel());
        } else if (msg instanceof GroupCreateReqBean) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收到创建群组请求>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //2.创建群组请求
            createGroup((GroupCreateReqBean) msg, ctx.channel());
        } else if (msg instanceof GroupListReqBean) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收到获取群组列表请求>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //3.获取群组列表请求
            listGroup((GroupListReqBean) msg, ctx.channel());
        } else if (msg instanceof GroupAddReqBean) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收到加入群组请求>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //4.加入群组请求
            addGroup((GroupAddReqBean) msg, ctx.channel());
        } else if (msg instanceof GroupQuitReqBean) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收到退出群组请求>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //5.退出群组请求
            quitGroup((GroupQuitReqBean) msg, ctx.channel());
        } else if (msg instanceof GroupMemberReqBean) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收到查看群组成员请求>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //6.查看群组成员请求
            listMember((GroupMemberReqBean) msg, ctx.channel());

        } else if (msg instanceof GroupSendMsgReqBean) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收到群发消息请求>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //7.群发消息请求
            sendMsg((GroupSendMsgReqBean) msg, ctx.channel());
        }


    }


    private void sendMsg(GroupSendMsgReqBean bean, Channel channel) {
        GroupSendMsgResBean res = new GroupSendMsgResBean();
        //1.根据“群组ID”获取对应的“组信息”
        Group group = groups.get(bean.getTogroupid());
        //2.给“发送人”响应，通知其发送的消息是否成功
        if (group == null) {
            res.setCode(1);
            res.setMsg("groupId=" + bean.getTogroupid() + ",不存在!");
            channel.writeAndFlush(res);
            return;
        } else {
            res.setCode(0);
            res.setMsg("群发消息成功");
            channel.writeAndFlush(res);
        }
        //3.根据“组”下面的“成员”，变量并且逐个推送消息
        List<GroupMember> members = group.getMembers();
        for (GroupMember gm : members) {
            GroupRecMsgBean rec = new GroupRecMsgBean();
            rec.setFromuserid(bean.getFromuserid());
            rec.setMsg(bean.getMsg());
            gm.getChannel().writeAndFlush(rec);
        }
    }

    /**
     * 查看群组成员
     *
     * @param bean
     * @param channel
     */
    private void listMember(GroupMemberReqBean bean, Channel channel) {
        GroupMemberResBean res = new GroupMemberResBean();
        List<Integer> lists = new ArrayList<Integer>();
        //1.根据“群组ID”获取对应的“组信息”
        Group group = groups.get(bean.getGroupId());
        if (group == null) {
            //2.查询的群组不存在
            res.setCode(1);
            res.setMsg("groupId=" + bean.getGroupId() + ",不存在!");
            channel.writeAndFlush(res);
        } else {
            //3.群组存在，则变量其底层的成员
            for (Map.Entry<Integer, Group> entry : groups.entrySet()) {
                Group g = entry.getValue();
                List<GroupMember> members = g.getMembers();
                for (GroupMember gm : members) {
                    lists.add(gm.getUserid());
                }
            }
            res.setCode(0);
            res.setMsg("查询成功");
            res.setList(lists);
            channel.writeAndFlush(res);
        }
    }


    /**
     * 退出群组
     *
     * @param bean
     * @param channel
     */
    private void quitGroup(GroupQuitReqBean bean, Channel channel) {
        GroupQuitResBean res = new GroupQuitResBean();
        //1.根据“群组ID”获取对应的“组信息”
        Group group = groups.get(bean.getGroupId());
        if (group == null) {
            //2.群组不存在
            res.setCode(1);
            res.setMsg("groupId=" + bean.getGroupId() + ",不存在!");
            channel.writeAndFlush(res);
            return;
        }
        //3.群组存在，则获取其底下“成员集合”
        List<GroupMember> members = group.getMembers();
        //4.遍历集合，找到“当前用户”在集合的序号
        int index = -1;
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getUserid() == bean.getUserId()) {
                index = i;
                break;
            }
        }
        //5.如果序号等于-1，则表示“当前用户”不存在集合里面
        if (index == -1) {
            res.setCode(1);
            res.setMsg("userid=" + bean.getUserId() + ",不存在该群组里面!");
            channel.writeAndFlush(res);
            return;
        }
        //6.从集合里面删除“当前用户”
        members.remove(index);
        //7.给“群组”的“成员列表”重新赋值
        group.setMembers(members);
        res.setCode(0);
        res.setMsg("退出群组成功");
        channel.writeAndFlush(res);
    }


    /**
     * 加入群组
     *
     * @param bean
     * @param channel
     */
    private void addGroup(GroupAddReqBean bean, Channel channel) {
        GroupAddResBean res = new GroupAddResBean();
        //1.根据“群组ID”获取对应的“组信息”
        Group group = groups.get(bean.getGroupId());
        //2.“群组”不存在
        if (group == null) {
            res.setCode(1);
            res.setMsg("groupId=" + bean.getGroupId() + ",不存在!");
            channel.writeAndFlush(res);
            return;
        }
        //3.“群组”存在，则获取其底下的“成员集合”
        List<GroupMember> members = group.getMembers();
        boolean flag = false;
        //4.遍历集合，判断“用户”是否已经存在了
        for (GroupMember gm : members) {
            if (gm.getUserid().equals(bean.getUserId())) {
                flag = true;
                break;
            }
        }
        if (flag) {
            res.setCode(1);
            res.setMsg("已经在群组里面,无法再次加入!");
        } else {
            //1.用户信息
            GroupMember gm = new GroupMember();
            gm.setUserid(bean.getUserId());
            gm.setChannel(channel);
            //2.添加到集合里面
            members.add(gm);
            //3.给“群组”重新赋值
            group.setMembers(members);
            res.setCode(0);
            res.setMsg("加入群组成功");
        }
        channel.writeAndFlush(res);
    }


    /**
     * 获取群组列表
     *
     * @param bean
     * @param channel
     */
    private void listGroup(GroupListReqBean bean, Channel channel) {
        if ("list".equals(bean.getType())) {
            //定义一个响应实体
            GroupListResBean res = new GroupListResBean();
            //定义一个集合
            List<GroupInfo> lists = new ArrayList<GroupInfo>();
            //变量groups Map集合
            for (Map.Entry<Integer, Group> entry : groups.entrySet()) {
                Integer mapKey = entry.getKey();
                Group mapValue = entry.getValue();
                GroupInfo gi = new GroupInfo();
                gi.setGroupId(mapKey);
                gi.setGroupName(mapValue.getGroupName());
                lists.add(gi);
            }
            //把集合添加到响应实体里面
            res.setGroupInfoList(lists);
            //开始写到客户端
            channel.writeAndFlush(res);
        }
    }


    /**
     * 登录请求
     *
     * @param bean
     * @param channel
     */
    private void login(LoginReqBean bean, Channel channel) {
        LoginResBean res = new LoginResBean();
        Channel c = map.get(bean.getUserId());
        if (c == null) {
            //通道为空，证明该用户没有在线
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>用户登录成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>用户id：" + bean.getUserId() + ",用户名：" + bean.getUserName() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //1.添加到map
            map.put(bean.getUserId(), channel);
            //2.给通道赋值
            channel.attr(AttributeKey.valueOf("userid")).set(bean.getUserId());
            //3.响应
            res.setStatus(0);
            res.setMsg("登录成功");
            res.setUserid(bean.getUserId());
            channel.writeAndFlush(res);
        } else {
            //通道不为空，证明该用户已经在线了
            res.setStatus(1);
            res.setMsg("该账户目前在线");
            channel.writeAndFlush(res);
        }
    }

    /**
     * 创建群组： 1.判断群组是否已经存在 2.不存在，创建群组，响应创建成功 3.存在，响应已经存在
     * ：维护一个Map，key是群组ID，value是群组成员信息
     *
     * @param bean
     * @param channel
     */
    private void createGroup(GroupCreateReqBean bean, Channel channel) {
        //定义一个响应实体
        GroupCreateResBean res = new GroupCreateResBean();
        //查询groups是否已经存在
        Group group = groups.get(bean.getGroupId());
        //判断是否已经存在
        if (group == null) {
            //定义群组实体
            Group g = new Group();
            //定义一个集合，专门存储成员
            List<GroupMember> members = new ArrayList<GroupMember>();
            //属性赋值
            g.setGroupName(bean.getGroupName());
            g.setMembers(members);
            //添加到Map里面
            groups.put(bean.getGroupId(), g);
            //响应信息
            res.setGroupName(bean.getGroupName());
            res.setCode(0);
            res.setMsg("创建群组成功");
        } else {
            res.setCode(1);
            res.setMsg("该群组已经存在!");
        }
        channel.writeAndFlush(res);
    }


    /**
     * 通道断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>客户端断开连接>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //1.获取通道中的userid
        Integer userid = (Integer) ctx.channel().attr(AttributeKey.valueOf("userid")).get();
        //2.移除map中的通道
        map.remove(userid);
    }
}