package engine.Repositories;

import engine.Entities.CompletedQuizInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompletedQuizRepository extends JpaRepository<CompletedQuizInfo, Long> {
    @Query("select s from CompletedQuizInfo s where username like %?1%")
    Page<CompletedQuizInfo> findByUsername(String username, Pageable pageable);
    Page<CompletedQuizInfo> findAll(Pageable pageable);
}
