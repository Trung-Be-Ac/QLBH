package com.example.Bai1SpringBoot.Model.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Users {

    private int IDUser;
    private String ten;
    private int tuoi;
    private int sdt;
    private String email;
    private String CCCD;
    private String diaChi;
    private String roleUser;
    private String UserName;
    private String PassWords;
    public Users(String ten, String roleUser, String userName, String passWords) {
        this.ten = ten;
        this.roleUser = roleUser;
        this.UserName = userName;
        this.PassWords = passWords;
    }
}
