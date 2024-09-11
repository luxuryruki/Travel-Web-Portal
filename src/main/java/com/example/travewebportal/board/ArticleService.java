package com.example.travewebportal.board;

import com.example.travewebportal.board.dto.ArticleCommentDto;
import com.example.travewebportal.board.dto.ArticleDto;
import com.example.travewebportal.board.dto.ArticleUpdateDto;
import com.example.travewebportal.board.enums.SearchType;
import com.example.travewebportal.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Page<ArticleDto> searchArticles(SearchType title, String searchKeyword) {
        return Page.empty();

    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long l) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(long articleId,ArticleUpdateDto dto) {
    }

    public void deleteArticle(long articleId) {
    }


}
