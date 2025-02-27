package com.example.Bai1SpringBoot.Model.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bill {
    private int IDBill;
    private Product product;
    private Users user;
    private int soLuong;
    private double tongGiaTiem;
}
