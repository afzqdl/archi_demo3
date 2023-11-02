package com.example.demo3_bs.service;

import com.example.demo3_bs.dao.ContactNotFoundException;
import com.example.demo3_bs.dao.ContactRepository;
import com.example.demo3_bs.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// 定义联系人服务类，用于封装业务逻辑
@Service
public class ContactService {

    // 使用@Autowired注解自动注入ContactRepository对象
    @Autowired
    private ContactRepository contactRepository;

    // 定义一个方法，用于获取所有联系人列表
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    // 定义一个方法，用于根据ID获取联系人对象
        public Optional<Contact> findById(Long id) {
        return contactRepository.findById(id);
    }

    // 定义一个方法，用于添加联系人对象
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    // 定义一个方法，用于更新联系人对象
    public Contact update(Long id, Contact contact) {
        // 根据ID获取联系人对象，如果不存在则抛出异常
        Contact existingContact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(id));
        // 更新姓名、住址和电话信息，如果有则更新，如果没有则保持不变
        existingContact.setName(contact.getName() != null ? contact.getName() : existingContact.getName());
        existingContact.setAddress(contact.getAddress() != null ? contact.getAddress() : existingContact.getAddress());
        existingContact.setPhone(contact.getPhone() != null ? contact.getPhone() : existingContact.getPhone());
        // 保存更新后的对象到数据库中
        return contactRepository.save(existingContact);
    }

    // 定义一个方法，用于删除联系人对象
    public Contact delete(Long id) {
        // 根据ID获取联系人对象，如果不存在则抛出异常
        Contact existingContact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(id));
        // 从数据库中删除对象
        contactRepository.delete(existingContact);
        // 返回删除的对象
        return existingContact;
    }
}
