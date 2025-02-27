package com.example.Bai1SpringBoot.Model.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.Product;
import com.example.Bai1SpringBoot.Model.Entity.TypeProduct;
import com.example.Bai1SpringBoot.Model.Entity.Users;

@Repository
public class ProductRepo {
    @Autowired
     UsersRepo userRepo = new UsersRepo();
    @Autowired
     TypeProductRepo typeProductRepo = new TypeProductRepo();

    // Show all product
    public ArrayList<Product> getAllProduct() throws Exception {
        ArrayList<Product> allProduct = new ArrayList<>();
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from PRODUCT");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("IDProduct");
            Users user = userRepo.getUserByID(rs.getInt("IDUser"));
            String name = rs.getString("tenSanPham");
            TypeProduct type = typeProductRepo.getTypeProductByID(rs.getInt("IDTypeProduct"));
            double price = rs.getDouble("giaTien");
            int quantity = rs.getInt("soLuong");
            String img = rs.getString("img");
            Product product = new Product(id, user, name, type, price, quantity, img);
            allProduct.add(product);
        }
        con.close();
        ps.close();
        rs.close();
        return allProduct;
    }

    // Tìm Product theo ID:
    public  Product getProductByID(int ID) throws Exception {
        TypeProductRepo typeProductRepo = new TypeProductRepo();
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from PRODUCT where IDProduct = ?");
        ps.setInt(1, ID);
        ResultSet rs = ps.executeQuery();
        Product product = null;
        if (rs.next()) {
            int IDP = rs.getInt("IDProduct");
            Users user = userRepo.getUserByID(rs.getInt("IDUser"));
            String NameP = rs.getString("tenSanPham");
            TypeProduct type = typeProductRepo.getTypeProductByID(rs.getInt("IDTypeProduct"));
            double price = rs.getDouble("giaTien");
            int quantity = rs.getInt("soLuong");
            String img = rs.getString("img");
            product = new Product(IDP, user, NameP, type, price, quantity, img);
        }
        con.close();
        ps.close();
        rs.close();
        return product;
    }

    // Add Product
    public static void AddProduct(Product product) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "insert into PRODUCT (IDUser,tenSanPham,IDTypeProduct,giaTien,soLuong,img) values (?,?,?,?,?,?);");
        ps.setInt(1, product.getUser().getIDUser());
        ps.setString(2, product.getTenSanPham());
        ps.setInt(3, product.getTypeProduct().getIDTypeProduct());
        ps.setDouble(4, product.getGiaTien());
        ps.setInt(5, product.getSoLuong());
        ps.setString(6, product.getImg());
        ps.executeUpdate();
        con.close();
        ps.close();
    }

    // Delete theo ID
    public static void DeleteProdcutByID(int ID) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "delete from PRODUCT where IDProduct =?");
        ps.setInt(1, ID);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    // Update
    public static void UpdateProductByID(Product product)
            throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update PRODUCT set tenSanPham=?,IDTypeProduct=?,giaTien=?,soLuong=?,img=? where IDProduct=?");
        ps.setString(1, product.getTenSanPham());
        ps.setInt(2, product.getTypeProduct().getIDTypeProduct());
        ps.setDouble(3, product.getGiaTien());
        ps.setInt(4, product.getSoLuong());
        ps.setString(5,product.getImg());
        ps.setInt(6, product.getIDProduct());
        ps.executeUpdate();
        con.close();
        ps.close();
    }

    // Update After Buy
    public static void UpdateProductAfterBuy(int IDProduct, int soluong)
            throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update PRODUCT set soLuong=? where IDProduct=? ");
        ps.setInt(1, soluong);
        ps.setInt(2, IDProduct);
        ps.executeUpdate();
        con.close();
        ps.close();
    }
}
