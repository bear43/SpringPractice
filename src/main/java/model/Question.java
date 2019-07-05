package model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Question
{
    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Question prevQuestion;

    @OneToMany(mappedBy = "prevQuestion")
    private List<Question> nextQuestionsOnPositive;

    public Question()
    {
    }

    public Question(String message, Question prevQuestion, List<Question> nextQuestionsOnPositive)
    {
        this.message = message;
        this.prevQuestion = prevQuestion;
        this.nextQuestionsOnPositive = nextQuestionsOnPositive;
    }

    public Question(String message)
    {
        this(message, null, null);
    }

    public boolean isPrevAvailable()
    {
        return prevQuestion != null;
    }

    public boolean isNextOnPositiveAvailable()
    {
        return nextQuestionsOnPositive != null && nextQuestionsOnPositive.size() != 0;
    }

    public List<Question> getSiblingPositiveQuestions()
    {
        return prevQuestion.nextQuestionsOnPositive;
    }

    @Override
    public String toString()
    {
        return message;
    }
}
