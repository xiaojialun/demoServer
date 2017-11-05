package com.demo.Dao.DaoImp;

import com.demo.Dao.replyDao;
import com.demo.Dao.userDao;
import grpc.demo.login.*;

/**
 * Created by xjl on 2017/10/25.
 */
public class replyDaoImp implements replyDao {
    private User user=null;
    private userDao userDao=new userDaoImp();
    @Override
    public RegisterReply register(RegisterRequest request) {
        String username=request.getUsername();
        String password=request.getPassword();
        String tel=request.getTel();
        int id=userDao.userCount()+1;
        user=User.newBuilder().setId(id)
                .setUsername(username)
                .setPassword(password)
                .setTel(tel).build();

        if(userDao.addUser(user)){
            return RegisterReply.newBuilder()
                    .setUser(user)
                    .setResgister(true)
                    .setMessage("注册成功！！！！！！！！！！！！！！！！！！！！！！")
                    .build();
        }else {
            return RegisterReply.newBuilder().setResgister(false)
                    .setMessage("注册失败！！！！！！！！！！！！！！！！！！！！！！")
                    .build();
        }

    }

    @Override
    public LoginReply login(LoginRequest request) {
        user=User.newBuilder()
                .setUsername(request.getUsername())
                .setPassword(request.getPassword()).build();
        if(userDao.isExistUser(user)){
          return LoginReply.newBuilder()
                  .setUser(userDao.getmUser())
                  .setMessage("登录成功！！！！！！！！！")
                  .setLogin(true).build();
        }else {
            return LoginReply.newBuilder()
                    .setMessage("账号或者密码错误")
                    .setLogin(false).build();
        }

    }

    @Override
    public SearchFriendReply searchFriend(SearchFriendRequest request) {
        user=userDao.findUserByUsername(request.getUsername());
        SearchFriendReply reply;
        if(user==null){
            System.out.print("user is null");
            reply=null;
        }else {
            System.out.print("user is not null");
            reply=SearchFriendReply.newBuilder().setUser(user).build();
        }
        return reply;
    }
}
