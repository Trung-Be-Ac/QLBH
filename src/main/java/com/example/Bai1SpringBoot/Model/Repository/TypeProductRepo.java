package com.example.Bai1SpringBoot.Model.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.TypeProduct;

@Repository
public class TypeProductRepo {
    // Show tat ca
    public static ArrayList<TypeProduct> getAllTypeProduct() throws Exception {
        ArrayList<TypeProduct> allTypeProduct = new ArrayList<>();
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from TYPEPRODUCT");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int IDTP = rs.getInt("IDTypeProduct");
            String NameTP = rs.getString("NameTypeProduct");
            TypeProduct typeProduct = new TypeProduct(IDTP, NameTP);
            allTypeProduct.add(typeProduct);
        }
        con.close();
        ps.close();
        rs.close();
        return allTypeProduct;
    }

    // Tìm Theo ID
    public static TypeProduct getTypeProductByID(int ID) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from TYPEPRODUCT where IDTypeProduct = ?");
        ps.setInt(1, ID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int IDTP = rs.getInt("IDTypeProduct");
        String NameTP = rs.getString("NameTypeProduct");
        TypeProduct typeProduct = new TypeProduct(IDTP, NameTP);
        con.close();
        ps.close();
        rs.close();
        return typeProduct;
    }

    // Add
    public static void AddTypeProduct(TypeProduct typeProduct) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("insert into TYPEPRODUCT(NameTypeProduct) values (?);");
        ps.setString(1, typeProduct.getNameTypeProduct());
        con.close();
        ps.close();
    }

    // Delete
    public static void DeleteTypeProductByID(int ID) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("delete from TYPEPRODUCT where IDTypeProduct = ? ");
        ps.setInt(1, ID);
        con.close();
        ps.executeUpdate();
        ps.close();
    }

    // Update
    public static void UpdateTypeProduct(int IDTP, String NameTP) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update TYPEPRODUCT set NameTypeProduct=? where IDTypeProduct= ?");
        ps.setString(1, NameTP);
        ps.setInt(2, IDTP);
        ps.executeUpdate();
        con.close();
        ps.close();
    }
}
