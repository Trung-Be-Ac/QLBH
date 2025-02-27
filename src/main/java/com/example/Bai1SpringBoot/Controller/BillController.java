package com.example.Bai1SpringBoot.Controller;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1SpringBoot.Model.Entity.Bill;
import com.example.Bai1SpringBoot.Model.Entity.Product;
import com.example.Bai1SpringBoot.Model.Entity.Users;
import com.example.Bai1SpringBoot.Model.Repository.BillRepo;
import com.example.Bai1SpringBoot.Model.Repository.ProductRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class BillController {
    @Autowired
    BillRepo billRepo = new BillRepo();
    ProductRepo productRepo = new ProductRepo();
    ArrayList<Product> cartList = new ArrayList<>();
// VIEW 
    @GetMapping("/showOrder/{IDProduct}")
    public String showOrder(@PathVariable("IDProduct") int IDProduct, Model model) throws Exception {
        Product product = productRepo.getProductByID(IDProduct);
        model.addAttribute("BillOrder", product);
        return "Order/showOrder";
    }
// BUY
    @PostMapping("/OrderProduct")
    public String orderProduct(@RequestParam("id") int id, @RequestParam("Quantity") int quantity,
            HttpSession httpSession) throws Exception {
        Users user = (Users) httpSession.getAttribute("AfterLogin"); // ép kiểu Users sau khi có được giá trị từ session
                                                                     // sau khi đăng nhập
        Product product = productRepo.getProductByID(id);
        double totalprice = product.getGiaTien() * quantity; // Giá tiền khi người dùng mua
        int newQuantity = product.getSoLuong() - quantity; // Số lượng người dùng mua
        Bill bill = new Bill(0, product, user, quantity, totalprice); // Tạo thêm Bill
        productRepo.UpdateProductAfterBuy(id, newQuantity);
        billRepo.AddBill(bill);
        return ("redirect:/");
    }

    @GetMapping("/ShowOrderUser")
    public String showOrderByIDUser(HttpSession httpSession, Model model) throws Exception {
         Users users = (Users) httpSession.getAttribute("AfterLogin"); // Gắn giá trị session vào trong biến user
         ArrayList <Bill> BillList = billRepo.getBillbyUserID(users.getIDUser());
          model.addAttribute("OrderList", BillList);
          return "Order/showOrderUserID";
    }
    // ADD TO CART
    @GetMapping("/addToCart/{IDProduct}")
    public String addToCart( @PathVariable("IDProduct") int id,HttpSession httpSession) throws Exception{
        Product product = productRepo.getProductByID(id); // Lấy product theo id
        for (Product productcheck : cartList) { // vòng lặp lấy 1 product đã có trong cartlist dùng để check với product vừa mới add vào trong cartlist
            if (productcheck.getIDProduct() == product.getIDProduct()) { // nếu product add vào trùng id mà đã có trong cartList trước đó
                productcheck.setSoLuong(productcheck.getSoLuong()+1);  // thì nó sẽ tự động set lại giá trị quantity cộng lên 1
                return "redirect:/";
            }
        }
        product.setSoLuong(1); //đặt giá trị số lượng mặc đinh khi add luôn là 1
        cartList.add(product);// Mang Array Dùng để chứa sản phâm trong cart
        httpSession.setAttribute("CartList", cartList); // Lưu giá trị product đã add vào trong cart
        return "redirect:/"; //*
    }
    
    // Show CART List
    @GetMapping("/ShowCart")
        public String showCart (HttpSession httpSession,Model model) throws Exception{
            ArrayList <Product> productsList = (ArrayList<Product>) httpSession.getAttribute("CartList");// Lấy thông tin của giỏ hàng (Cartlist) đã lưu trong session và gán nó vào một biến productsList
            if(productsList==null){
                productsList = new ArrayList<>();
                model.addAttribute("CartListModel", null);
            }else{
                model.addAttribute("CartListModel", productsList); // lưu biến productsList 
            }
            
            double totalPriceAll = 0; // Tổng tiền hóa đơn trong cart
            for (Product product : productsList) {
                totalPriceAll = totalPriceAll + (product.getSoLuong() * product.getGiaTien());
            }
            httpSession.setAttribute("totalPriceAll", totalPriceAll);
            return "Order/showCart";
        }
    // Reduce in cart
    @GetMapping("/reduce/{IDProduct}")
        public String reduce (@PathVariable("IDProduct") int IDProduct,HttpSession httpSession) throws Exception{
            ArrayList <Product> productsList = (ArrayList<Product>) httpSession.getAttribute("CartList");
        for (Product product : productsList) {// duyệt qua từng phần tử trong danh sách productsList
            if (product.getIDProduct()==IDProduct) {// /kiểm tra product có trùng với sản phẩm người mua muốn giảm số lượng không
             if (product.getSoLuong()==1) {//nếu số lượng sản phẩm là 1, xóa sản phẩm khỏi giỏ hàng
                product.setSoLuong(product.getSoLuong()+1);
                productsList.remove(product);
                return "redirect:/ShowCart";
            }else{ // nếu số lượng sản phẩm lớn hơn 1, giảm số lượng sản phẩm
                product.setSoLuong(product.getSoLuong()-1);
                return "redirect:/ShowCart";
            }
        }
        }
        return "redirect:/ShowCart";
    }
    // Increase in cart
    @GetMapping("/increase/{IDProduct}")
    public String increase (@PathVariable("IDProduct") int IDProduct,HttpSession httpSession) throws Exception{
        ArrayList <Product> productsList = (ArrayList<Product>) httpSession.getAttribute("CartList");
        for (Product product : productsList) {// duyệt qua từng phần tử trong danh sách productsList, mỗi lần lặp, biến product sẽ chứa thông tin của một sản phẩm từ danh sách
        if (product.getIDProduct()== IDProduct) { //kiểm tra product có trùng với sản phẩm người mua muốn tăng số lượng không
            product.setSoLuong(product.getSoLuong()+1);
            return "redirect:/ShowCart";
        }
    }
    return "redirect:/ShowCart";
    }
    // Buy product in cart
    @GetMapping("/buyProductInCart")
    public String buyProductInCart (HttpSession httpSession) throws Exception{
        ArrayList <Product> productsList = (ArrayList<Product>) httpSession.getAttribute("CartList");
        Users users = (Users) httpSession.getAttribute("AfterLogin"); // Lấy thông tin người dùng sau đăng nhập
        for (Product product : productsList) { // duyệt từng sản phẩm trong giỏ hàng
            Product oldProduct = productRepo.getProductByID(product.getIDProduct());//lấy thông tin chi tiết của sản phẩm từ cơ sở dữ liệu dựa vào IDProduct
            double totalprice= product.getGiaTien()*product.getSoLuong();//tính tổng giá
            int newQuantity = oldProduct.getSoLuong()-product.getSoLuong();//số lượng còn lại của sản phẩm trong kho sau khi người dùng mua 
            Bill bill = new Bill(0, product, users, product.getSoLuong(), totalprice);//tạo hóa đơn
            productRepo.UpdateProductAfterBuy(oldProduct.getIDProduct(), newQuantity);//cập nhật số lượng sản phẩm trong kho
            billRepo.AddBill(bill);// lưu hóa đơn xuống thẳng csdl
        }
        productsList.clear();// xóa product vừa mua khỏi cart
        return "redirect:/";
    }
}
