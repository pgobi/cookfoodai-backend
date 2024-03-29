package com.pgobi.cookfood.ai.repository;

import com.pgobi.cookfood.ai.entities.Token;
import com.pgobi.cookfood.ai.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :userId and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Integer userId);

    Optional<Token> findByRefreshToken(String refreshToken);

    Optional<Token> findByAccessToken(String accessToken);

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId")
    List<Token> findAllByUser(Integer userId);

}
