package com.example.travewebportal.board;

import com.example.travewebportal.board.dto.ArticleCommentDto;
import com.example.travewebportal.board.dto.ArticleDto;
import com.example.travewebportal.board.dto.ArticleUpdateDto;
import com.example.travewebportal.board.dto.ArticleWithCommentDto;
import com.example.travewebportal.board.enums.SearchType;
import com.example.travewebportal.board.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if(searchKeyword == null || searchKeyword.isBlank()){
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType){
            case  TITLE -> articleRepository.findByTitleContaining(searchKeyword,pageable).map(ArticleDto::from);
            case  CONTENT -> articleRepository.findByContentContaining(searchKeyword,pageable).map(ArticleDto::from);
            case  ID -> articleRepository.findByUserAccount_UserId(searchKeyword,pageable).map(ArticleDto::from);
            case  NAME -> articleRepository.findByNameContaining(searchKeyword,pageable).map(ArticleDto::from);
            case  HASHTAG -> articleRepository.findByHashTagContaining( searchKeyword,pageable).map(ArticleDto::from);
        };

    }

    @Transactional(readOnly = true)
    public ArticleWithCommentDto getArticle(long id) {
        return articleRepository.findById(id)
                .map(ArticleWithCommentDto::from)
                .orElseThrow(()->new EntityNotFoundException("not found an article" + id));
    }

    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(long articleId,ArticleUpdateDto dto) {
    }

    public void deleteArticle(long articleId) {
    }

    public ArticleWithCommentDto getArticle(Long id){
        return null;
    };
}
