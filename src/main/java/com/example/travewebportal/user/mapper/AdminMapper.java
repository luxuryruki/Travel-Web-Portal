package com.example.travewebportal.user.mapper;

import com.example.travewebportal.user.dto.AdminDto;
import com.example.travewebportal.user.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {
    Admin adminPostToAdmin(AdminDto.Post requestBody);

    AdminDto.Response adminToAdminResponse(Admin admin);

}
