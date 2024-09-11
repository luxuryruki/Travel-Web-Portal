package com.example.travewebportal.board;

import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.dto.ArticleDto;
import com.example.travewebportal.board.dto.ArticleUpdateDto;
import com.example.travewebportal.board.enums.SearchType;
import com.example.travewebportal.board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("business logic - Articles")
@ExtendWith(MockitoExtension.class) // Mock 테스트를 위한 어노테이션
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("Search articles, then return articles")
    @Test
    void givenSearchParam_whenSearchingArticles_thenReturnArticles(){

        //Given
//        SearchParameters param = SearchParameters.of(SearchType.TITLE,"search keyword");
        //When
        Page<ArticleDto> articles =  sut.searchArticles(SearchType.TITLE,"search keyword"); // Title, Content, Id, Name, Hashtag
        //Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("Read an article, then return the article")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnArticle(){

        //Given
//        SearchParameters param = SearchParameters.of(SearchType.TITLE,"search keyword");
        //When
        ArticleDto article =  sut.searchArticle(1L);
        //Then
        assertThat(article).isNotNull();
    }

    @DisplayName("create article")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle(){
        //Given
        ArticleDto dto = ArticleDto.of(LocalDateTime.now(),"Johnny","title","content","hashTag");
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //When
        sut.saveArticle(dto);

        //Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("update article")
    @Test
    void givenArticleModifiedInfo_whenUpdatingArticle_thenUpdatesArticle(){
        //Given
        ArticleUpdateDto dto = ArticleUpdateDto.of("title","content","hashTag");
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //When
        sut.updateArticle(1L, dto);

        //Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("delete article")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
        //Given

        willDoNothing().given(articleRepository).delete(any(Article.class));
        //When
        sut.deleteArticle(1L);

        //Then
        then(articleRepository).should().delete(any(Article.class));
    }
}