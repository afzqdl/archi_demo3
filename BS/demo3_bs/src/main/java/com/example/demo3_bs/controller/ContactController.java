package com.example.demo3_bs.controller;

import com.example.demo3_bs.service.ContactService;
import com.example.demo3_bs.dao.ContactAlreadyExistsException;
import com.example.demo3_bs.dao.ContactNotFoundException;
import com.example.demo3_bs.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 使用@RestController注解标记这是一个REST控制器
@RestController
@RequestMapping("/contacts") // 定义请求路径的前缀
public class ContactController {

    // 使用@Autowired注解自动注入ContactService对象
    @Autowired
    private ContactService contactService;

    // 使用@GetMapping注解标记这是一个GET请求的映射方法，路径为/
    @GetMapping("/")
    public ResponseEntity<List<Contact>> getContacts() {
        // 调用ContactService的方法获取所有联系人列表
        List<Contact> contacts = contactService.findAll();
        // 返回响应实体，包含状态码和数据
        return ResponseEntity.ok(contacts);
    }

    // 使用@GetMapping注解标记这是一个GET请求的映射方法，路径为/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable("id") Long id) {
        // 调用ContactService的方法根据ID获取联系人对象，如果不存在则抛出异常
        Contact contact = contactService.findById(id).orElseThrow(() -> new ContactNotFoundException(id));
        // 返回响应实体，包含状态码和数据
        return ResponseEntity.ok(contact);
    }

    // 使用@PostMapping注解标记这是一个POST请求的映射方法，路径为/
    @PostMapping("/")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        try {
            // 调用ContactService的方法添加联系人对象，如果电话已存在则抛出异常
            Contact newContact = contactService.save(contact);
            // 返回响应实体，包含状态码和数据
            return ResponseEntity.status(HttpStatus.CREATED).body(newContact);
        } catch (DataIntegrityViolationException e) {
            // 捕获异常并返回错误信息
            throw new ContactAlreadyExistsException(contact.getPhone());
        }
    }

    // 使用@PutMapping注解标记这是一个PUT请求的映射方法，路径为/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable("id") Long id, @RequestBody Contact contact) {
        try {
            // 调用ContactService的方法更新联系人对象，如果不存在则抛出异常，如果电话已存在则抛出异常
            Contact updatedContact = contactService.update(id, contact);
            // 返回响应实体，包含状态码和数据
            return ResponseEntity.ok(updatedContact);
        } catch (DataIntegrityViolationException e) {
            // 捕获异常并返回错误信息
            throw new ContactAlreadyExistsException(contact.getPhone());
        }
    }

    // 使用@DeleteMapping注解标记这是一个DELETE请求的映射方法，路径为/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable("id") Long id) {
        // 调用ContactService的方法删除联系人对象，如果不存在则抛出异常
        Contact deletedContact = contactService.delete(id);
        // 返回响应实体，包含状态码和数据
        return ResponseEntity.ok(deletedContact);
    }
}

