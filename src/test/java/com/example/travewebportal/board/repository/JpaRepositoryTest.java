package com.example.travewebportal.board.repository;

import com.example.travewebportal.board.config.JpaConfig;
import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.domain.UserAccount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//@ActiveProfiles("testDb") // 테스트 프로필 설정
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("JPA Connection Test")
@Import(JpaConfig.class) //auditing 설정 넣어줘야함
@DataJpaTest
class JpaRepositoryTest {


    private final ArticleRepository articleRepository;

    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,@Autowired ArticleCommentRepository articleCommentRepository, @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("Select Test")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        // Given

        // When
        List<Article> articles = articleRepository.findAll();
        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(300);
    }



    @DisplayName("Insert Test")
    @Test
    void givenTestData_whenInserting_thenWorksFine(){
        // Given
        long previousCount = articleRepository.count();

        UserAccount userAccount = userAccountRepository.save(UserAccount.of("Johnny","pw",null,null,null));
        Article article = Article.of(userAccount,"New Article","New Content", "#Test");
        // When
        articleRepository.save(article);
        // Then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount+1);
    }

    @DisplayName("Update Test")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){
        // Given

        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#updated Hashtag";
        article.setHashtag(updatedHashtag);


        // When
        Article savedArticle =  articleRepository.save(article);
        articleRepository.flush(); // Junit 테스트는 트랜잭션의 기본값이 Roll back 이다. 따라서 업데이트 이후 아무 동작을 하지 않으면 어차피 롤백될것이기 때문에 update 작동안하게된다. 따라서 업데이트 테스트를 위해선 flush해서 업데이트 쿼리를 실행해준다. 단, 다시 롤백된기  때문에 바뀐 데이터는 적용은 안된다.
        // Then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("Delete Test")
    @Test
    void givenTestData_whenDelete_thenWorksFine(){
        // Given

        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        long deletedCommentsSize = article.getArticleComments().size();


        // When
        articleRepository.delete(article);
        // Then
        assertThat(articleRepository.count())
                .isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count())
                .isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }
}