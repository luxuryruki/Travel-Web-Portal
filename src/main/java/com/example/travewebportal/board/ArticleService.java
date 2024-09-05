package com.example.travewebportal.board;

import com.example.travewebportal.board.dto.ArticleDto;
import com.example.travewebportal.board.enums.SearchType;
import com.example.travewebportal.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository repository;

    @Transactional(readOnly = true)
    public List<ArticleDto> searchArticles(SearchType title, String searchKeyword) {
        return List.of();

    }
}
