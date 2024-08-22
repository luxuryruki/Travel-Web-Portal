package com.example.travewebportal.user.service;

import com.example.travewebportal.user.entity.Admin;
import com.example.travewebportal.user.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin createMember(Admin admin){
//        member.setPassword(encoder.encode(member.getPassword()));
        Admin saveAdmin = adminRepository.save(admin);

        return saveAdmin;
    }
}
