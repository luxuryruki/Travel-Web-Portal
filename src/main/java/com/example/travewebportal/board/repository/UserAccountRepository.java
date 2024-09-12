package com.example.travewebportal.board.repository;

import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
