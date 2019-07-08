package service;

import exception.NoSuchQuestionException;
import exception.QuestionAlreadyExistsException;
import model.Category;
import model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.QualifiedIdentifier;
import org.springframework.stereotype.Service;
import repository.QuestionRepository;

import java.util.ArrayList;
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

    public List<Long> getNextQuestionsById(long id)
    {
        Question g = getQuestionById(id);
        List<Long> ids = new ArrayList<>();
        g.getNextQuestionsOnPositive().forEach(x -> ids.add(x.getId()));
        return ids;
    }

    public Question createQuestion(String message, Long prevQuestion) throws Exception
    {
        Question pQuest = null;
        if(prevQuestion != null)
        {
            pQuest = getQuestionById(prevQuestion);
            if (pQuest == null) throw new NoSuchQuestionException();
        }
        return new Question(message, pQuest, null);
    }

    public void delete(Question question)
    {
        questionRepository.delete(question);
    }
}
