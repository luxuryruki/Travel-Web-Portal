package com.example.travewebportal.board;

import com.example.travewebportal.board.dto.ArticleDto;
import com.example.travewebportal.board.enums.SearchType;
import com.example.travewebportal.board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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


}