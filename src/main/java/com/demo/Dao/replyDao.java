package com.demo.Dao;


import grpc.demo.login.*;

/**
 * Created by xjl on 2017/10/25.
 */
public interface  replyDao {
    public RegisterReply register(RegisterRequest request);
    public LoginReply login(LoginRequest request);
    public SearchFriendReply searchFriend(SearchFriendRequest request);
}
