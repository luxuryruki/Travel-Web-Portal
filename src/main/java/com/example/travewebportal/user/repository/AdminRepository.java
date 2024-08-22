package com.example.travewebportal.user.repository;

import com.example.travewebportal.user.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{
}
