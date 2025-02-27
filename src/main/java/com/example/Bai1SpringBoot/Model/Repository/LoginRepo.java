package com.example.Bai1SpringBoot.Model.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.Users;

@Repository
public class LoginRepo {
    // Login
    public static Users CheckLogin(String UserName, String Password) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "select * from Users where UserName = ? and PassWords= ?  ");
        ps.setString(1, UserName);
        ps.setString(2, Password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int IDUser = rs.getInt("IDUser");
            String Name = rs.getString("ten");
            int Age = rs.getInt("tuoi");
            int SDT = rs.getInt("sdt"); 
            String Email = rs.getString("email");
            String CCCD = rs.getString("CCCD");
            String diachi = rs.getString("Diachi");
            String role = rs.getString("roleUser");
            Users users = new Users(IDUser, Name, Age, SDT, Email, CCCD, diachi, role, UserName, Password);
            con.close();
            ps.close();
            rs.close();
            return users;
        }
        return null;

    }
}
