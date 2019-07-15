import exception.CategoryAlreadyExistsException;
import exception.NoSuchCategoryException;
import exception.QuestionAlreadyExistsException;
import model.Category;
import model.Question;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.CategoryService;
import service.QuestionService;
import spring.EntryPoint;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { EntryPoint.class })
public class CategoryServiceTest
{
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuestionService questionService;

    private Category category;

    @Test(expected = NoSuchCategoryException.class)
    public void testNoSuchCategoryException() throws Exception
    {
        categoryService.delete(category);
        categoryService.delete(category.getId());
    }

    @Test(expected = QuestionAlreadyExistsException.class)
    public void testAddQuestionAlreadyExisted() throws Exception
    {
        Question question = questionService.createQuestion("testQuestion", null);
        categoryService.addQuestion(category, question);
        categoryService.addQuestion(category, question);
    }

    @Test(expected = CategoryAlreadyExistsException.class)
    public void testAddCategoryAlreadyExisted() throws Exception
    {
            category = categoryService.createCategory("testCategory");
            categoryService.createCategory(category);
            categoryService.createCategory(category);
    }

    @Test
    public void tesAll() throws Exception
    {
        testAddCategoryAlreadyExisted();
        testAddQuestionAlreadyExisted();
        testNoSuchCategoryException();
    }

}
