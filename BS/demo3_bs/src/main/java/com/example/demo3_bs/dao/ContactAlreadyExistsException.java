package com.example.demo3_bs.dao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 定义联系人已存在异常类，用于处理电话已存在的情况
@ResponseStatus(HttpStatus.CONFLICT) // 定义响应状态码为409
public class ContactAlreadyExistsException extends RuntimeException {

    public ContactAlreadyExistsException(String phone) {
        super("Contact with phone " + phone + " already exists");
    }
}
