package com.example.demo3_bs.entity;

import javax.persistence.*;

// 定义联系人实体类，用于映射数据库表
@Entity
@Table(name = "person") // 定义表名
public class Contact {

    @Id // 定义主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 定义主键生成策略为自增
    private Long id; // 定义ID字段

    @Column(name = "name", nullable = false) // 定义列名和非空约束
    private String name; // 定义姓名字段

    @Column(name = "address") // 定义列名
    private String address; // 定义住址字段

    @Column(name = "phone", unique = true, nullable = false) // 定义列名和唯一约束和非空约束
    private String phone; // 定义电话字段

    // 省略构造方法、getter和setter方法、toString方法等


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
