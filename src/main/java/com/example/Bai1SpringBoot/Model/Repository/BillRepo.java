package com.example.Bai1SpringBoot.Model.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.Bill;
import com.example.Bai1SpringBoot.Model.Entity.Product;
import com.example.Bai1SpringBoot.Model.Entity.Users;

@Repository
public class BillRepo {
    @Autowired
     ProductRepo ProductRepo = new ProductRepo();
    @Autowired
     UsersRepo userRepo = new UsersRepo();

    public ArrayList<Bill> getAllBill() throws Exception {
        ArrayList<Bill> allBill = new ArrayList<>();
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from BILL");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int IDBill = rs.getInt("IDBill");
            Product product = ProductRepo.getProductByID(rs.getInt("IDProduct"));
            Users users = userRepo.getUserByID(rs.getInt("IDUser"));
            int soluong = rs.getInt("soLuong");
            double price = rs.getDouble("tongGiaTiem");
            Bill bill = new Bill(IDBill, product, users, soluong, price);
            allBill.add(bill);
        }
        con.close();
        ps.close();
        rs.close();
        return allBill;
    }

    public ArrayList<Bill> getBillbyUserID(int id) throws Exception {
        ArrayList<Bill> allBill = new ArrayList<>();
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from BILL where IDUser = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int IDBill = rs.getInt("IDBill");
            Product product = ProductRepo.getProductByID(rs.getInt("IDProduct"));
            Users users = userRepo.getUserByID(rs.getInt("IDUser"));
            int soluong = rs.getInt("soLuong");
            double price = rs.getDouble("tongGiaTiem");
            Bill bill = new Bill(IDBill, product, users, soluong, price);
            allBill.add(bill);
        }
        con.close();
        ps.close();
        rs.close();
        return allBill;
    }

    // Search by ID
    public static Bill getBillByID(int ID) throws Exception {
        ProductRepo productRepo = new ProductRepo();
        UsersRepo userRepo = new UsersRepo();
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from BILL where IDBill = ?;");
        ps.setInt(1, ID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int IDBill = rs.getInt("IDBILL");
        Product product = productRepo.getProductByID(rs.getInt("IDProduct"));
        Users users = userRepo.getUserByID(rs.getInt("IDUser"));
        int soluong = rs.getInt("soLuong");
        double tongiatien = rs.getDouble("tongGiaTiem");
        Bill bill = new Bill(IDBill, product, users, soluong, tongiatien);
        return bill;
    }

    // Add
    public static void AddBill(Bill bill) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "insert into BILL (IDProduct,IDUser,soLuong,tongGiaTiem) values (?,?,?,?)");
        ps.setInt(1, bill.getProduct().getIDProduct());
        ps.setInt(2, bill.getUser().getIDUser());
        ps.setInt(3, bill.getSoLuong());
        ps.setDouble(4, bill.getTongGiaTiem());
        ps.executeUpdate();
        con.close();
        ps.close();
    }

    // Delete
    public static void DeleteBillByID(int ID) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "delete from bill where IDBill = ?");
        ps.setInt(1, ID);
        ps.executeUpdate();
        con.close();
        ps.close();
    }

    // Update
    public static void UpdateBill(int soluong, double tonggiatien, int IDBill) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update BILL set soLuong=?,tongGiaTiem=? where IDBill=?");
        ps.setInt(1, soluong);
        ps.setDouble(2, tonggiatien);
        ps.setInt(3, IDBill);
        ps.executeUpdate();
        con.close();
        ;
    }
}
