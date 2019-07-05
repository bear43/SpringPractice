package repository;

import model.Category;
import model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>
{
    List<Question> findAllByCategory(Category category);
    Question findByMessage(String message);
    boolean existsByCategory(Category category);
    boolean existsByMessage(String message);
}
