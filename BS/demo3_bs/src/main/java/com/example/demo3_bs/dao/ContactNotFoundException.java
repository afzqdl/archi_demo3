package com.example.demo3_bs.dao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 定义联系人未找到异常类，用于处理找不到联系人的情况
@ResponseStatus(HttpStatus.NOT_FOUND) // 定义响应状态码为404
public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException(Long id) {
        super("Contact with id " + id + " not found");
    }
}

