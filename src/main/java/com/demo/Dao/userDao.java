package com.demo.Dao;


import grpc.demo.login.User;

import java.util.List;

/**
 * Created by xjl on 2017/10/24.
 */
public interface userDao {
    public boolean addUser(User user);
    public boolean delUser(int id);
    public boolean upUser();
    public List<User> QuerryAllUser();
    public int userCount();
    public boolean isExistUser(User user);
    public User getmUser();
    public void setmUser(User mUser);
    public User findUserByUsername(String username);
}
