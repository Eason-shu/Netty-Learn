package com.shu;

import com.shu.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 10:49
 * @version: 1.0
 */
public class MapUtils {


    //1. 自定义指令
    private static Byte codeLoginReq=1;
    private static Byte codeLoginRes=2;
    private static Byte codeMsgReq=3;
    private static Byte codeMsgRes=4;
    private static Byte codeMsgRec=5;

    //2. 自定义一个Map，专门管理指令和实体的关系
    private static Map<Byte, Class<? extends BaseBean>> map=new HashMap<Byte,Class<? extends BaseBean>>();


    //3. 初始化
    static {
        map.put(codeLoginReq, LoginReqBean.class);
        map.put(codeLoginRes, LoginResBean.class);
        map.put(codeMsgReq, MsgReqBean.class);
        map.put(codeMsgRes, MsgResBean.class);
        map.put(codeMsgRec, MsgRecBean.class);
    }

    private static Map<Byte, Class<? extends BaseBean>> map1=new HashMap<Byte,Class<? extends BaseBean>>();
    static {
        //登录的请求和响应实体
        map1.put((byte) 1, LoginReqBean.class);
        map1.put((byte) 2, LoginResBean.class);

        //创建群组的请求和响应实体
        map1.put((byte) 3, GroupCreateReqBean.class);
        map1.put((byte) 4, GroupCreateResBean.class);

        //查看群组的请求和响应实体
        map1.put((byte) 5, GroupListReqBean.class);
        map1.put((byte) 6, GroupListResBean.class);

        //加入群组的请求和响应实体
        map1.put((byte) 7,GroupAddReqBean.class);
        map1.put((byte) 8,GroupAddResBean.class);

        //退出群组的请求和响应实体
        map1.put((byte) 9,GroupQuitReqBean.class);
        map1.put((byte) 10,GroupQuitResBean.class);

        //查看成员列表的请求和响应实体
        map1.put((byte) 11,GroupMemberReqBean.class);
        map1.put((byte) 12,GroupMemberResBean.class);

        //发送响应的实体（发送消息、发送响应、接受消息）
        map1.put((byte) 13,GroupSendMsgReqBean.class);
        map1.put((byte) 14,GroupSendMsgResBean.class);
        map1.put((byte) 15,GroupRecMsgBean.class);
    }



    //4. 根据指令获取对应的实体
    public static Class<? extends BaseBean> getBean(Byte code){
        try{
            return map.get(code);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    //5. 根据实体获取对应的指令

    public static Byte getCode(Class<? extends BaseBean> clazz){
        try{
            for(Map.Entry<Byte, Class<? extends BaseBean>> entry:map.entrySet()){
                if(entry.getValue()==clazz){
                    return entry.getKey();
                }
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //6. 根据指令获取对应的实体
    public static Class<? extends BaseBean> getBean1(Byte code){
        try{
            return map1.get(code);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    //7. 根据实体获取对应的指令

    public static Byte getCode1(Class<? extends BaseBean> clazz){
        try{
            for(Map.Entry<Byte, Class<? extends BaseBean>> entry:map1.entrySet()){
                if(entry.getValue()==clazz){
                    return entry.getKey();
                }
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
