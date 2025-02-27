package com.example.Bai1SpringBoot.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1SpringBoot.Model.Entity.Product;
import com.example.Bai1SpringBoot.Model.Entity.TypeProduct;
import com.example.Bai1SpringBoot.Model.Entity.Users;
import com.example.Bai1SpringBoot.Model.Repository.ProductRepo;
import com.example.Bai1SpringBoot.Model.Repository.TypeProductRepo;
import com.example.Bai1SpringBoot.Model.Repository.UsersRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
    @Autowired
    ProductRepo productRepo = new ProductRepo();
    @Autowired
    TypeProductRepo typeProductRepo = new TypeProductRepo();
    @Autowired
    UsersRepo usersRepo = new UsersRepo();

    // SẢN PHẨM
    // Xem Trang Main Có Sản Phẩm
    @GetMapping("/") // Đường link http aka đường link trên web
    public String showIndex(Model model) throws Exception {
        ArrayList<Product> productList = productRepo.getAllProduct(); // Hàm trả về kiểu dữ liệu ArrayList
        model.addAttribute("ProductList", productList); // Lưu giá trị tất cả Product được gắn vào thông qua key tên là
                                                        // "ProductList"
        return "Public/index"; // Trả về file html trong folder Public
    } 

    // Xen chi tiết sản phẩm
    @GetMapping("/ViewAllDetail/{IDProduct}")
    public String ViewAllDetail(@PathVariable("IDProduct") int IDProduct, Model model) throws Exception {
        Product product = productRepo.getProductByID(IDProduct);
        model.addAttribute("ProductDetail", product);
        return "Public/productDetail";
    }

    // Trang xem add Sản Phẩm
    @GetMapping("/showaddProduct")
    public String showAddProduct(Model model) throws Exception {
        ArrayList<TypeProduct> typeList = typeProductRepo.getAllTypeProduct();
        model.addAttribute("TypeList", typeList);   
        return "Product/addProduct";
    }

    // Trang add sản phẩm
    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("nameproduct2") String tenSanpham,
            @RequestParam("Type") int typePID, @RequestParam("price2") double giaTien,
            @RequestParam("quantity2") int soLuong,
            @RequestParam("img2") String img, HttpSession httpSession) throws Exception {
        Users users = (Users) httpSession.getAttribute("AfterLogin");
        TypeProduct typeProduct = typeProductRepo.getTypeProductByID(typePID);
        Product product = new Product(0, users, tenSanpham, typeProduct, giaTien, soLuong, img);
        productRepo.AddProduct(product);
        return "redirect:/"; // redirect là gọi trực tiếp về get và post chứ không phải trang html
    }

    // EDIT
    @GetMapping("/ShowEdit/{IDProduct}")
    public String ViewEdit(Model model, @PathVariable("IDProduct") int IDProduct) throws Exception {
        ArrayList<TypeProduct> typeList = typeProductRepo.getAllTypeProduct();
        model.addAttribute("TypeList", typeList);
        Product product = productRepo.getProductByID(IDProduct);
        model.addAttribute("Product", product);
        return ("Product/edit");
    }

    @PostMapping("/editProduct")
    public String editProduct(@RequestParam("idProduct3") int IDProduct,
            @RequestParam("nameproduct3") String tenSanpham,
            @RequestParam("Type3") int typePID, @RequestParam("price3") double giaTien,
            @RequestParam("quantity3") int soLuong,
            @RequestParam("img3") String img, HttpSession httpSession) throws Exception {
        Users users = (Users) httpSession.getAttribute("AfterLogin");
        TypeProduct typeProduct = typeProductRepo.getTypeProductByID(typePID);
        Product product = new Product(IDProduct, users, tenSanpham, typeProduct, giaTien, soLuong, img);
        productRepo.UpdateProductByID(product);
        return ("redirect:/");
    }

    // SEARCH
    @PostMapping("/searchProduct")
    public String searchProduct(@RequestParam("search") String search, Model model, HttpSession httpSession)
            throws Exception {
        ArrayList<Product> productsList = productRepo.getAllProduct(); // get toàn bộ thông tin Product
        ArrayList<Product> finProducts = new ArrayList<>(); // tạo ra 1 al mới để khi điều kiện ở dưới đúng nó sẽ add
                                                            // vào trong này
        for (Product product : productsList) {
            // Sử dụng vong lập để check điều kiện toàn bô phần tử
            if (product.getTenSanPham().toLowerCase().contains(search.toLowerCase())) {// Có thể sử dụng equal thay cho
                                                                                       // contains
                finProducts.add(product);
            }
        }
        model.addAttribute("ProductList", finProducts); // Tận dụng lại model đã sử dụng trong index
        return "Public/index";
    }
}
