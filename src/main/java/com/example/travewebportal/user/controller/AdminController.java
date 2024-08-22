package com.example.travewebportal.user.controller;

import com.example.travewebportal.user.dto.AdminDto;
import com.example.travewebportal.user.entity.Admin;
import com.example.travewebportal.user.enums.Role;
import com.example.travewebportal.user.mapper.AdminMapper;
import com.example.travewebportal.user.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/admins")
public class AdminController {


    private final AdminMapper mapper;
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody AdminDto.Post responseBody){

        Admin admin = mapper.adminPostToAdmin(responseBody);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.Admin);
        admin.setRoles(roles);

        Admin createAdmin = adminService.createMember(admin);


        return new ResponseEntity(mapper.adminToAdminResponse(admin), HttpStatus.CREATED);
    }
}
