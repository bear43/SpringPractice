package service;

import exception.CategoryAlreadyExistsException;
import exception.NoSuchCategoryException;
import exception.QuestionAlreadyExistsException;
import model.Category;
import model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    public boolean exists(String title)
    {
        return categoryRepository.existsByTitle(title);
    }

    public Category findByTitle(String title)
    {
        return categoryRepository.findByTitle(title);
    }

    public List<Category> findAll()
    {
        return categoryRepository.findAll();
    }

    public Category findById(long id)
    {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Question> getFirstQuestionsList(long id)
    {
        Category cat = findById(id);
        if(cat != null)
            return cat.getStartQuestions();
        return null;
    }

    public List<Long> getFirstQuestionsListIds(long id)
    {
        List<Question> g = getFirstQuestionsList(id);
        List<Long> ids = new ArrayList<>();
        g.forEach(x -> ids.add(x.getId()));
        return ids;
    }

    public void createCategory(Category category) throws Exception
    {
        if(exists(category.getTitle())) throw new CategoryAlreadyExistsException();
        categoryRepository.saveAndFlush(category);
    }

    public Category createCategory(String title) throws Exception
    {
        return new Category(title);
    }

    public void addQuestion(Category category, Question question) throws Exception
    {
        if(category.getStartQuestions().stream().
                anyMatch(x -> x.getMessage().equals(question.getMessage()))) throw new QuestionAlreadyExistsException();
        category.getStartQuestions().add(question);
        categoryRepository.saveAndFlush(category);
    }

    public void addQuestion(Long categoryId, Question question) throws Exception
    {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchCategoryException::new);
        addQuestion(category, question);
    }

    public void delete(Category category)
    {
        categoryRepository.delete(category);
    }

    public void delete(String title)
    {
        Category category = findByTitle(title);
        if(category != null) categoryRepository.delete(category);
    }
}
