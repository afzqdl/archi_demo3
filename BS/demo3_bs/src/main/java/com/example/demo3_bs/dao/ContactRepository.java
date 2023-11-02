package com.example.demo3_bs.dao;

import com.example.demo3_bs.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 定义联系人仓库接口，用于操作数据库
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // 继承JpaRepository接口，提供基本的增删改查方法
}

