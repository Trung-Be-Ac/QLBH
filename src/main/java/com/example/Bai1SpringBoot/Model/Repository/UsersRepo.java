package com.example.Bai1SpringBoot.Model.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.Users;

@Repository
public class UsersRepo {
    // Show All
    public ArrayList<Users> getAllUsers() throws Exception {
        ArrayList<Users> allUsers = new ArrayList<>();
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from Users");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int IDUser = rs.getInt("IDUser");
            String Ten = rs.getString("ten");
            int Tuoi = rs.getInt("tuoi");
            int Sdt = rs.getInt("sdt");
            String EMail = rs.getString("email");
            String CCCD = rs.getString("CCCD");
            String DiaChi = rs.getString("diachi");
            String RoleUser = rs.getString("roleUser");
            String UserName = rs.getString("UserName");
            String Password = rs.getString("PassWords");
            Users user = new Users(IDUser, Ten, Tuoi, Sdt, EMail, CCCD, DiaChi, RoleUser, UserName,
                    Password);
            allUsers.add(user);
        }
        con.close();
        rs.close();
        ps.close();
        return allUsers;
    }

    // Tìm Theo ID
    public static Users getUserByID(int ID) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from Users where IDUser = ?");
        ps.setInt(1, ID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int IDU = rs.getInt("IDUser");
        String NameU = rs.getString("ten");
        int AgeU = rs.getInt("tuoi");
        int SDT = rs.getInt("sdt");
        String Email = rs.getString("email");
        String CCCD = rs.getString("CCCD");
        String DiaChi = rs.getString("diaChi");
        String RoleU = rs.getString("roleUser");
        String UserNameu = rs.getString("UserName");
        String PasswordsU = rs.getString("Passwords");
        Users users = new Users(IDU, NameU, AgeU, SDT, Email, CCCD, DiaChi, RoleU, UserNameu, PasswordsU);
        con.close();
        rs.close();
        ps.close();
        return users;

    }

    // Add
    public static void AddUsers(Users users) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "insert into USERS(ten,tuoi,sdt,email,CCCD,diaChi,roleUser,UserName,PassWords) values (?,?,?,?,?,?,?,?,?)");
        ps.setString(1, users.getTen());
        ps.setInt(2, users.getTuoi());
        ps.setInt(3, users.getSdt());
        ps.setString(4, users.getEmail());
        ps.setString(5, users.getCCCD());
        ps.setString(6, users.getDiaChi());
        ps.setString(7, users.getRoleUser());
        ps.setString(8, users.getUserName());
        ps.setString(9, users.getPassWords());
        ps.executeUpdate();
        con.close();
        ps.close();
    }

    // Delete
    public static void DeleteUserByID(int ID) throws Exception { 
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "delete from Users where IDUser = ?;");
        ps.setInt(1, ID);
        ps.executeUpdate();
        con.close();
        ps.close();
    }

    // Update
    public static void UpdateUsers(int ID, String ten, int tuoi, int sdt, String email, String CCCD, String diachi)
            throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update USERS set ten=?,tuoi=?,sdt=?,email=?,CCCD=?,diaChi=? where IDUser = ?");
        ps.setString(1, ten);
        ps.setInt(2, tuoi);
        ps.setInt(3, sdt);
        ps.setString(4, email);
        ps.setString(5, CCCD);
        ps.setString(6, diachi);
        ps.setInt(7, ID);
        ps.executeUpdate();
        con.close();
        ps.close();
    }

    

}
