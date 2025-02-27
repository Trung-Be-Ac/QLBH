package com.example.Bai1SpringBoot.Controller;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bai1SpringBoot.Model.Entity.Product;
import com.example.Bai1SpringBoot.Model.Entity.Users;
import com.example.Bai1SpringBoot.Model.Repository.LoginRepo;
import com.example.Bai1SpringBoot.Model.Repository.ProductRepo;
import com.example.Bai1SpringBoot.Model.Repository.UsersRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    UsersRepo usersRepo = new UsersRepo();
    @Autowired
    LoginRepo loginRepo = new LoginRepo();

    // LOG IN
    @GetMapping("/login") // đây là link của trang web được có trên web đặt tên bên trong ngoặc dùng để xem 
    public String Login() {
        return "Public/login";
    }

    @PostMapping("/inLogin") // dùng để nhập
    public String inputLogin(@RequestParam("username1") String userName, @RequestParam("password1") String passWord,
            HttpSession httpSession)
            throws Exception {
        Users user = loginRepo.CheckLogin(userName, passWord);
        if (user == null) {
            return "Public/login";
        } else {
            httpSession.setAttribute("AfterLogin", user); // Luu user vao session
            return ("redirect:/"); // redirect là gọi trực tiếp về get và post chứ không phải trang html
        }
    }

    // SIGN UP
    @GetMapping("/showsignup")
    public String showSignUp() {
        return "Public/signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam("name") String name, @RequestParam("username") String userName,
            @RequestParam("password") String passWord,
            @RequestParam("cpassword") String cPassword, @RequestParam("role") String role) throws Exception {
        if (passWord.equals(cPassword)) {
            Users users = new Users(passWord, role, userName,name);
            usersRepo.AddUsers(users);
            return ("redirect:/");
        }else{
            return "Public/signup";
        }
       
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logOut(HttpSession httpSession) {
        httpSession.removeAttribute("AfterLogin");
        return ("redirect:/");
    }
}
