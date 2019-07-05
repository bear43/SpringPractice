package service;

import exception.QuestionAlreadyExistsException;
import model.Category;
import model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.QuestionRepository;

import java.util.List;

@Service
public class QuestionService
{
    @Autowired
    private QuestionRepository questionRepository;

    public boolean exists(String message)
    {
        return questionRepository.existsByMessage(message);
    }

    public List<Question> getCategoryQuestions(Category category)
    {
        return questionRepository.findAllByCategory(category);
    }

    public Question getQuestionById(long id)
    {
        return questionRepository.findById(id).orElse(null);
    }

    public void createQuestion(Question question) throws Exception
    {
        if(exists(question.getMessage())) throw new QuestionAlreadyExistsException();
        questionRepository.saveAndFlush(question);
    }

    public void delete(Question question)
    {
        questionRepository.delete(question);
    }
}
