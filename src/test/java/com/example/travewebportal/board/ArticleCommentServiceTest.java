package com.example.travewebportal.board;

import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.domain.ArticleComment;
import com.example.travewebportal.board.domain.UserAccount;
import com.example.travewebportal.board.dto.ArticleCommentDto;
import com.example.travewebportal.board.dto.UserAccountDto;
import com.example.travewebportal.board.repository.ArticleCommentRepository;
import com.example.travewebportal.board.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("business logic - Article Comments")
@ExtendWith(MockitoExtension.class) // Mock 테스트를 위한 어노테이션
class ArticleCommentServiceTest {
    @InjectMocks
    private ArticleCommentService articleCommentService;

    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("get article comments")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnArticleComments(){
        //given
        Long articleId = 1L;
//        given(articleRepository.findById(articleId)).willReturn(Optional.of(
//                Article.of("title","content","hashTag")
//        ));
        ArticleComment expected = createArticleComment("content");
        given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));
        //when
        List<ArticleCommentDto> actual = articleCommentService.searchArticleComment(articleId);
        //then
        assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(articleCommentRepository).should().findByArticle_Id(articleId);
    }

    @DisplayName("create article comments")
    @Test
    void given_when_then(){
        //given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);
        //when
        articleCommentService.saveArticleComment(dto);
        //then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    private ArticleCommentDto createArticleCommentDto(String comment){
        return ArticleCommentDto.of(
                1L,
                LocalDateTime.now(),
                "Johnny",
                LocalDateTime.now(),
                "Johnny",
                comment
        );
    }

    private UserAccountDto createUserAccountDto(){
        return UserAccountDto.of(
                1L,
                "Johnny",
                "pw",
                "johnny@google.com",
                "Johnny",
                null,
                LocalDateTime.now(),
                "Johnny",
                LocalDateTime.now(),
                "Johnny"
        );
    }

    private Article createArticle(){
        return Article.of(
                createUserAccount(),
                "title",
                "cotent",
                "hashTag"
        );
    }

    private ArticleComment createArticleComment(String content){
        return ArticleComment.of(
                createUserAccount(),
                Article.of(
                        createUserAccount(), "title", "content", "hashTag"),
                content
        );
    }

    private UserAccount createUserAccount(){
        return UserAccount.of(
                "Johnny",
                "pw",
                "johnny@google.com",
                "Johnny",
                null
        );
    }

}