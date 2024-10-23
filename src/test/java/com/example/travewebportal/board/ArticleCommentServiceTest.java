package com.example.travewebportal.board;

import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.domain.ArticleComment;
import com.example.travewebportal.board.domain.UserAccount;
import com.example.travewebportal.board.dto.ArticleCommentDto;
import com.example.travewebportal.board.dto.UserAccountDto;
import com.example.travewebportal.board.repository.ArticleCommentRepository;
import com.example.travewebportal.board.repository.ArticleRepository;
import com.example.travewebportal.board.service.ArticleCommentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

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
    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment(){
        //given
        ArticleCommentDto dto = createArticleCommentDto("comment");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);
        //when
        articleCommentService.saveArticleComment(dto);
        //then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("create article comments fail")
    @Test
    void givenNonexistentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing(){
        //given
        ArticleCommentDto dto = createArticleCommentDto("comment");
        given(articleRepository.getReferenceById(dto.articleId())).willThrow(EntityNotFoundException.class);

        //when
        articleCommentService.saveArticleComment(dto);
        //then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).shouldHaveNoInteractions();
    }

    @DisplayName("update article comments")
    @Test
    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment(){
        //given
        String oldComment = "comment";
        String updatedComment = "updated comment";
        ArticleComment articleComment =createArticleComment(oldComment);
        ArticleCommentDto dto =createArticleCommentDto(updatedComment);
        given(articleCommentRepository.getReferenceById(dto.id())).willReturn(articleComment);

        //when
        articleCommentService.updateArticleComment(dto);

        //then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldComment)
                .isEqualTo(updatedComment);
        then(articleCommentRepository).should().getReferenceById(dto.id());

    }

    @DisplayName("update article comment fail")
    @Test
    void givenNonexistentArticleComment_whenUpdatingArticleComment_thenLogsWarningAndDoesNothing(){
        //given
        ArticleCommentDto dto = createArticleCommentDto("comment");
        given(articleCommentRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);
        //when
        articleCommentService.updateArticleComment(dto);
        //then
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("delete article")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment(){
        //given
        Long id = 1L;
        ArticleCommentDto dto = createArticleCommentDto("comment");
        willDoNothing().given(articleRepository).deleteById(id);
        //when
        articleCommentService.deleteArticleComment(id);
        //then
        then(articleCommentRepository).should().deleteById(id);
    }

    private ArticleCommentDto createArticleCommentDto(String comment){
        return ArticleCommentDto.of(
                1L,
                1L,
                createUserAccountDto(),
                LocalDateTime.now(),
                "Johnny",
                LocalDateTime.now(),
                "Johnny",
                comment
        );
    }

    private UserAccountDto createUserAccountDto(){
        return UserAccountDto.of(
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