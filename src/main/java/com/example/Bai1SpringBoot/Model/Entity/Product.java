package com.example.Bai1SpringBoot.Model.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private int IDProduct;
    private Users User;
    private String tenSanPham;
    private TypeProduct typeProduct;
    private double giaTien;
    private int soLuong;
    private String img;
}
