package com.example.travewebportal.board;

import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.domain.ArticleComment;
import com.example.travewebportal.board.dto.ArticleCommentDto;
import com.example.travewebportal.board.repository.ArticleCommentRepository;
import com.example.travewebportal.board.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("business logic - Article Comments")
@ExtendWith(MockitoExtension.class) // Mock 테스트를 위한 어노테이션
class ArticleCommentServiceTest {
    @InjectMocks
    private ArticleCommentService sut;

    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("get article comments")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnArticleComments(){
        //given
        Long articleId = 1L;
        given(articleRepository.findById(articleId)).willReturn(Optional.of(
                Article.of("title","content","hashTag")
        ));
        //when
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);
        //then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("create article comments")
    @Test
    void given_when_then(){
        //given
        //when
        //then
    }
}