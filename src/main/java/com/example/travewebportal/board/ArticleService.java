package com.example.travewebportal.board;

import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.domain.UserAccount;
import com.example.travewebportal.board.dto.ArticleCommentDto;
import com.example.travewebportal.board.dto.ArticleDto;
import com.example.travewebportal.board.dto.ArticleUpdateDto;
import com.example.travewebportal.board.dto.ArticleWithCommentDto;
import com.example.travewebportal.board.enums.SearchType;
import com.example.travewebportal.board.repository.ArticleRepository;
import com.example.travewebportal.board.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;
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
    public ArticleDto searchArticle(long l) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            if (dto.title() != null) article.setTitle(dto.title());
            if (dto.content() != null) article.setContent(dto.content());
            article.setHashTag(dto.hashTag());
        }catch (EntityNotFoundException e){
            log.warn("게시글 업데이트 실패, 게시글을 찾을 수 없습니다. - ㅇ새: {}", dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    public ArticleWithCommentDto getArticle(Long id){
        return articleRepository.findById(id)
                .map(ArticleWithCommentDto::from)
                .orElseThrow(()->new EntityNotFoundException("not found an article : " + id));
    };
}
