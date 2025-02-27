package com.example.Bai1SpringBoot.config;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.example.Bai1SpringBoot.Model.Entity.Users;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component // Component sẽ được chèn vào bất kì đâu
public class AuthFilter implements Filter { // Filter này là 1 interface

    // chain là bộ lọc
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request; // ép về kiểu http
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String uri = httpServletRequest.getRequestURI(); // Chỉ có httpServletRequest có được getRequestURI dùng để lấy
                                                         // được đường link vì đang implements 1 interface
        HttpSession httpSession = httpServletRequest.getSession();
        Users users = (Users) httpSession.getAttribute("AfterLogin");// Lấy thông tin người dùng từ session
        // áp dụng cho tất cả các role
        // kiểm tra các URL không yêu cầu xác thực
        if ((uri.equals("/login") || uri.equals("/") || uri.equals("/inLogin") || uri.startsWith("/ViewAllDetail/")
                || uri.equals("/showOrder/") || uri.equals("/logout") || uri.equals("/searchProduct") || uri.equals("/showsignup") || uri.equals("/signup"))) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        // Khách vãng lai
        if (users == null) {
            httpServletResponse.sendRedirect("/login");
        }
        // Áp dụng cho Admin
        else if (users.getRoleUser().equals("Admin")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
        // Áp dung cho role Seller
        else if (users.getRoleUser().equals("Seller") && uri.equals("/showaddProduct") || uri.equals("/addProduct")
                || uri.startsWith("/ShowEdit/") || uri.equals("/editProduct")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
        // Áp dụng cho role User
        else if (users.getRoleUser().equals("User") && uri.equals("/OrderProduct") || uri.startsWith("/showOrder/")
                || uri.equals("/ShowOrderUser") || uri.equals("/ShowCart") || uri.startsWith("/addToCart")
                || uri.startsWith("/reduce/") || uri.startsWith("/increase/")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }
}
