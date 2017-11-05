package com.demo.Dao.DaoImp;

import com.demo.Dao.userDao;
import com.demo.demoserver.DBhelper;
import grpc.demo.login.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by xjl on 2017/10/24.
 */
public class userDaoImp implements userDao {
    private DBhelper db=null;
    private Connection con=null;
    private PreparedStatement sta=null;
    private ResultSet rs=null;
    private User mUser=null;
    @Override
    public boolean addUser(User user) {
        db=new DBhelper();
        String sql="INSERT INTO USER (id,username,password,tel) VALUES (?,?,?,? )";
        con=db.getConn();
        int rows=0;
        try {
            sta=con.prepareStatement(sql);
            sta.setInt(1,user.getId());
            sta.setString(2,user.getUsername());
            sta.setString(3,user.getPassword());
            sta.setString(4,user.getTel());
            rows=sta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(rs!=null){
                    rs.close();
                }
                if(sta!=null){
                    sta.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.Close();
        }
        if(rows==0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean delUser(int id) {
        return false;
    }

    @Override
    public boolean upUser() {
        return false;
    }

    @Override
    public List<User> QuerryAllUser() {
        return null;
    }

    @Override
    public int userCount() {
        db=new DBhelper();
        String sql="SELECT COUNT(*) FROM USER";
        con=db.getConn();
        int Count=0;
        try {
            sta=con.prepareStatement(sql);
            rs=sta.executeQuery();
            while (rs.next()){
                Count=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(rs!=null){
                    rs.close();
                }
                if(sta!=null){
                    sta.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.Close();
        }

        return Count;
    }

    @Override
    public boolean isExistUser(User user) {
        db=new DBhelper();
        String sql="SELECT * FROM USER WHERE username=? and password=? ";
        con=db.getConn();
        boolean result=false;
        try {
            sta=con.prepareStatement(sql);
            sta.setString(1,user.getUsername());
            sta.setString(2,user.getPassword());
            rs=sta.executeQuery();
            while(rs.next()){
                mUser=User.newBuilder().setId(rs.getInt(1))
                        .setUsername(rs.getString(2))
                        .setPassword(rs.getString(3))
                        .setTel(rs.getString(4)).build();
                result=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(rs!=null){
                    rs.close();
                }
                if(sta!=null){
                    sta.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.Close();
        }

        return result;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    @Override
    public User findUserByUsername(String username) {
        db=new DBhelper();
        String sql="SELECT * FROM USER WHERE username=?";
        con=db.getConn();
        try{
            sta=con.prepareStatement(sql);
            sta.setString(1,username);
            rs=sta.executeQuery();
            while (rs.next()){
                mUser=User.newBuilder()
                        .setId(rs.getInt(1))
                        .setUsername(rs.getString(2))
                        .setTel(rs.getString(4)).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                sta.close();
                db.Close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return mUser;
    }
}
